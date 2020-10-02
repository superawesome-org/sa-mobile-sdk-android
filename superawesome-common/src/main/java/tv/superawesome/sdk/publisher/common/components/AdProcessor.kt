package tv.superawesome.sdk.publisher.common.components

import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.models.CreativeFormatType

interface AdProcessorType {
    fun process(placementId: Int, ad: Ad): AdResponse
}

class AdProcessor : AdProcessorType {
    override fun process(placementId: Int, ad: Ad): AdResponse {
        var response = AdResponse(placementId, ad)
        when (ad.creative.format) {
            CreativeFormatType.image_with_link -> {
                response.html = ""
                response.baseUrl = ""
            }
            CreativeFormatType.tag -> {
                response.html = ""
                response.baseUrl = "https://ads.superawesome.tv"
            }
            CreativeFormatType.rich_media -> {

            }
            CreativeFormatType.video -> {
                response.baseUrl = ad.creative.details.video
            }
        }
        return response
    }
}