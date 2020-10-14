package tv.superawesome.sdk.publisher.common.components

import kotlinx.serialization.ExperimentalSerializationApi
import tv.superawesome.sdk.publisher.common.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.common.extensions.baseUrl
import tv.superawesome.sdk.publisher.common.models.*
import tv.superawesome.sdk.publisher.common.network.DataResult

interface AdProcessorType {
    suspend fun process(placementId: Int, ad: Ad): AdResponse
}

class AdProcessor(
        private val htmlFormatter: HtmlFormatterType,
        private val vastParser: VastParserType,
        private val networkDataSource: NetworkDataSourceType,
        private val encoder: EncoderType,
) : AdProcessorType {
    @ExperimentalSerializationApi
    override suspend fun process(placementId: Int, ad: Ad): AdResponse {
        val response = AdResponse(placementId, ad)

        when (ad.creative.format) {
            CreativeFormatType.image_with_link -> {
                response.html = htmlFormatter.formatImageIntoHtml(ad)
                response.baseUrl = ad.creative.details.image?.baseUrl
            }
            CreativeFormatType.rich_media -> {
                response.html = htmlFormatter.formatRichMediaIntoHtml(placementId, ad)
                response.baseUrl = ad.creative.details.url.baseUrl
            }
            CreativeFormatType.tag -> {
                response.html = htmlFormatter.formatTagIntoHtml(ad)
                response.baseUrl = Constants.defaultSuperAwesomeUrl
            }
            CreativeFormatType.video -> {
                ad.creative.details.vast?.let { url ->
                    response.vast = handleVast(url, null)
                    response.baseUrl = ad.creative.details.video.baseUrl
                }
            }
        }

        ad.creative.referral?.let {
            response.referral = encoder.encodeUrlParamsFromObject(it.toMap())
        }

        return response
    }

    private suspend fun handleVast(url: String, initialVast: VastAd?): VastAd? {
        val result = networkDataSource.getData(url)
        if (result is DataResult.Success) {
            val vast = vastParser.parse(result.value)
            vast.redirect?.let {
                val mergedVast = vast.merge(initialVast)
                handleVast(it, mergedVast)
            }.run {
                return vast
            }
        }
        return initialVast
    }
}