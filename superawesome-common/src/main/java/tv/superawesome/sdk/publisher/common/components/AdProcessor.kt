package tv.superawesome.sdk.publisher.common.components

import tv.superawesome.sdk.publisher.common.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.models.CreativeFormatType
import tv.superawesome.sdk.publisher.common.models.VastAd

interface AdProcessorType {
    suspend fun process(placementId: Int, ad: Ad): AdResponse
}

class AdProcessor(private val htmlFormatter: HtmlFormatterType,
                  private val vastParser: VastParserType,
                  private val networkDataSource: NetworkDataSourceType) : AdProcessorType {
    override suspend fun process(placementId: Int, ad: Ad): AdResponse {
        val response = AdResponse(ad)

        when (ad.creative.format) {
            CreativeFormatType.image_with_link ->
                response.html = htmlFormatter.formatImageIntoHtml(ad)
            CreativeFormatType.rich_media ->
                response.html = htmlFormatter.formatRichMediaIntoHtml(placementId, ad)
            CreativeFormatType.tag ->
                response.html = htmlFormatter.formatTagIntoHtml(ad)
            CreativeFormatType.video -> {
                ad.creative.details.vast?.let { url ->
                    response.vast = handleVast(url, null)
                }
            }
        }

        return response
    }

    private suspend fun handleVast(url: String, initialVast: VastAd?): VastAd? {
        val result = networkDataSource.getData(url)
        if (result.isSuccess) {
            val vast = vastParser.parse(result.getOrNull() ?: "")

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