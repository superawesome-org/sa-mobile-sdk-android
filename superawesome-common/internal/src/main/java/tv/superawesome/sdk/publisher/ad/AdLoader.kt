package tv.superawesome.sdk.publisher.ad

import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.models.AdResponse

interface AdLoader {

    suspend fun load(placementId: Int, request: AdRequest) : Result<AdResponse>

    suspend fun load(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        request: AdRequest
    ) : Result<AdResponse>
}
