package tv.superawesome.sdk.publisher.common.network

import tv.superawesome.sdk.publisher.common.models.Ad

interface AwesomeAdsApi {
    suspend fun ad(placementId: Int): Ad
    suspend fun impression(placementId: Int)
    suspend fun click(placementId: Int)
    suspend fun videoClick(placementId: Int)
    suspend fun event(placementId: Int)
}