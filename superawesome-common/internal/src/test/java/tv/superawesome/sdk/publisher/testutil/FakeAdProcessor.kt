package tv.superawesome.sdk.publisher.testutil

import tv.superawesome.sdk.publisher.components.AdProcessorType
import tv.superawesome.sdk.publisher.models.Ad
import tv.superawesome.sdk.publisher.models.AdResponse

class FakeAdProcessor : AdProcessorType {

    override suspend fun process(
        placementId: Int,
        ad: Ad,
        requestOptions: Map<String, Any>?,
        openRtbPartnerId: String?
    ): AdResponse = AdResponse(
        placementId = placementId,
        ad = ad,
        requestOptions = requestOptions,
    )
}
