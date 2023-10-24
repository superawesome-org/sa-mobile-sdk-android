package tv.superawesome.sdk.publisher.components

import tv.superawesome.sdk.publisher.extensions.baseUrl
import tv.superawesome.sdk.publisher.extensions.extractURLs
import tv.superawesome.sdk.publisher.models.Ad
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.models.CreativeFormatType
import tv.superawesome.sdk.publisher.models.VastAd
import tv.superawesome.sdk.publisher.models.VastType
import tv.superawesome.sdk.publisher.network.datasources.NetworkDataSourceType

interface AdProcessorType {
    suspend fun process(
        placementId: Int,
        ad: Ad,
        requestOptions: Map<String, Any>?,
        openRtbPartnerId: String? = null,
    ): AdResponse
}

class AdProcessor(
    private val htmlFormatter: HtmlFormatterType,
    private val vastParser: VastParserType,
    private val networkDataSource: NetworkDataSourceType,
    private val encoder: EncoderType,
) : AdProcessorType {
    @Suppress("NestedBlockDepth", "ReturnCount")
    override suspend fun process(
        placementId: Int,
        ad: Ad,
        requestOptions: Map<String, Any>?,
        openRtbPartnerId: String?,
    ): AdResponse {
        val response = AdResponse(
            placementId,
            ad.copy(openRtbPartnerId = openRtbPartnerId),
            requestOptions
        )

        when (ad.creative.format) {
            CreativeFormatType.ImageWithLink -> {
                response.html = htmlFormatter.formatImageIntoHtml(ad)
                response.baseUrl = ad.creative.details.image?.baseUrl
            }
            CreativeFormatType.RichMedia -> {
                response.html = htmlFormatter.formatRichMediaIntoHtml(placementId, ad)
                response.baseUrl = ad.creative.details.url?.baseUrl
            }
            CreativeFormatType.Tag -> {
                response.html = htmlFormatter.formatTagIntoHtml(ad)
                response.baseUrl = Constants.defaultSuperAwesomeUrl
            }
            CreativeFormatType.Video ->
                if (ad.isVpaid) {
                    ad.creative.details.tag?.let { tag ->
                        tag.extractURLs().firstOrNull()?.let {
                            response.baseUrl = it.baseUrl
                        }
                    }
                    response.html = ad.creative.details.tag
                } else {
                    ad.creative.details.vast?.let { url ->
                        response.vast = handleVast(url, null)
                        response.baseUrl = response.vast?.url?.baseUrl
                        response.vast?.url?.let {
                            networkDataSource.downloadFile(it).fold(
                                onSuccess = { filePath ->
                                    response.filePath = filePath
                                },
                                onFailure = { exception ->
                                    throw exception
                                }
                            )
                        } ?: throw NullPointerException("empty url")

                    }
                }
        }

        ad.creative.referral?.let {
            response.referral = encoder.encodeUrlParamsFromObject(it.toMap())
        }

        return response
    }

    @Suppress("ReturnCount")
    private suspend fun handleVast(url: String, initialVast: VastAd?): VastAd? =
        networkDataSource.getData(url).fold(
            onSuccess = {
                val vast = vastParser.parse(it)
                if (vast?.type == VastType.Wrapper && vast.redirect != null) {
                    val mergedVast = vast.merge(initialVast)
                    handleVast(vast.redirect, mergedVast)
                } else if (vast?.type == VastType.InLine && initialVast != null) {
                    vast.merge(initialVast)
                } else {
                    vast
                }
            },
            onFailure = {
                initialVast
            }
        )
}
