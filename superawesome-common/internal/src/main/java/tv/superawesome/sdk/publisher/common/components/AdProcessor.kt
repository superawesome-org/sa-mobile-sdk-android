package tv.superawesome.sdk.publisher.common.components

import tv.superawesome.sdk.publisher.common.network.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.common.extensions.baseUrl
import tv.superawesome.sdk.publisher.common.extensions.extractURLs
import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.CreativeFormatType
import tv.superawesome.sdk.publisher.common.models.VastAd
import tv.superawesome.sdk.publisher.common.models.VastType
import tv.superawesome.sdk.publisher.common.network.DataResult
import java.io.File

interface AdProcessorType {
    suspend fun process(
        placementId: Int,
        ad: Ad,
        requestOptions: Map<String, Any>?,
    ): DataResult<AdResponse>
}

class AdProcessor(
    private val htmlFormatter: HtmlFormatterType,
    private val vastParser: VastParserType,
    private val networkDataSource: NetworkDataSourceType,
    private val encoder: EncoderType,
    private val videoDestDirectory: File,
) : AdProcessorType {
    @Suppress("NestedBlockDepth", "ReturnCount")
    override suspend fun process(
        placementId: Int,
        ad: Ad,
        requestOptions: Map<String, Any>?,
    ): DataResult<AdResponse> {
        val response = AdResponse(placementId, ad, requestOptions)

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
                            when (val downloadFileResult =
                                networkDataSource.downloadFile(it, videoDestDirectory)) {
                                is DataResult.Success -> response.filePath =
                                    downloadFileResult.value
                                is DataResult.Failure -> return downloadFileResult
                            }
                        } ?: return DataResult.Failure(Exception("empty url"))
                    }
                }
        }

        ad.creative.referral?.let {
            response.referral = encoder.encodeUrlParamsFromObject(it.toMap())
        }

        return DataResult.Success(response)
    }

    @Suppress("ReturnCount")
    private suspend fun handleVast(url: String, initialVast: VastAd?): VastAd? {
        val result = networkDataSource.getData(url)
        if (result is DataResult.Success) {
            val vast = vastParser.parse(result.value)
            if (vast?.type == VastType.Wrapper && vast.redirect != null) {
                val mergedVast = vast.merge(initialVast)
                return handleVast(vast.redirect, mergedVast)
            }
            return vast
        }
        return initialVast
    }
}
