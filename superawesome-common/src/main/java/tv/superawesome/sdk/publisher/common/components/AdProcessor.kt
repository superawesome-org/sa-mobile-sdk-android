package tv.superawesome.sdk.publisher.common.components

import tv.superawesome.sdk.publisher.common.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.common.extensions.baseUrl
import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.CreativeFormatType
import tv.superawesome.sdk.publisher.common.models.VastAd
import tv.superawesome.sdk.publisher.common.network.DataResult

interface AdProcessorType {
    suspend fun process(placementId: Int, ad: Ad): DataResult<AdResponse>
}

class AdProcessor(
    private val htmlFormatter: HtmlFormatterType,
    private val vastParser: VastParserType,
    private val networkDataSource: NetworkDataSourceType,
    private val encoder: EncoderType,
) : AdProcessorType {
    override suspend fun process(placementId: Int, ad: Ad): DataResult<AdResponse> {
        val response = AdResponse(placementId, ad)

        when (ad.creative.format) {
            CreativeFormatType.ImageWithLink -> {
                response.html = htmlFormatter.formatImageIntoHtml(ad)
                response.baseUrl = ad.creative.details.image?.baseUrl
            }
            CreativeFormatType.RichMedia -> {
                response.html = htmlFormatter.formatRichMediaIntoHtml(placementId, ad)
                response.baseUrl = ad.creative.details.url.baseUrl
            }
            CreativeFormatType.Tag -> {
                response.html = htmlFormatter.formatTagIntoHtml(ad)
                response.baseUrl = Constants.defaultSuperAwesomeUrl
            }
            CreativeFormatType.Video -> {
                ad.creative.details.vast?.let { url ->
                    response.vast = handleVast(url, null)
                    response.baseUrl = response.vast?.url?.baseUrl
                    response.vast?.url?.let {
                        when (val downloadFileResult = networkDataSource.downloadFile(it)) {
                            is DataResult.Success -> response.filePath = downloadFileResult.value
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

    suspend fun handleVast(url: String, initialVast: VastAd?, isRedirect: Boolean = false): VastAd? {
        val result = networkDataSource.getData(url)
        if (result is DataResult.Success) {
            val vast = vastParser.parse(result.value)
            vast?.redirect?.takeIf { initialVast != null && !isRedirect }?.also {
                val mergedVast = vast.merge(initialVast!!)
                handleVast(it, mergedVast, true)
            }
            return vast
        }
        return initialVast
    }
}
