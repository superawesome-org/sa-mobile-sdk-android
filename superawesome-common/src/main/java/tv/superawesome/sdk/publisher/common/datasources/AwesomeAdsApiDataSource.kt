package tv.superawesome.sdk.publisher.common.datasources

import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.AdQueryBundle
import tv.superawesome.sdk.publisher.common.models.EventQueryBundle
import tv.superawesome.sdk.publisher.common.network.DataResult

internal interface AwesomeAdsApiDataSourceType {

    suspend fun getAd(placementId: Int, query: AdQueryBundle): DataResult<Ad>

    suspend fun getAd(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        query: AdQueryBundle
    ): DataResult<Ad>

    suspend fun impression(query: EventQueryBundle): DataResult<Unit>

    suspend fun click(query: EventQueryBundle): DataResult<Unit>

    suspend fun videoClick(query: EventQueryBundle): DataResult<Unit>

    suspend fun event(query: EventQueryBundle): DataResult<Unit>
}