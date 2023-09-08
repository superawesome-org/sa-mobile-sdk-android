package tv.superawesome.sdk.publisher.common.testutil

import tv.superawesome.sdk.publisher.common.components.AdProcessorType
import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.network.DataResult

class FakeAdProcessor : AdProcessorType {

    override suspend fun process(
        placementId: Int,
        ad: Ad,
        requestOptions: Map<String, Any>?,
    ): DataResult<AdResponse> =
        DataResult.Success(
            value = AdResponse(
                placementId = placementId,
                ad = ad,
                requestOptions = requestOptions,
            )
        )
}
