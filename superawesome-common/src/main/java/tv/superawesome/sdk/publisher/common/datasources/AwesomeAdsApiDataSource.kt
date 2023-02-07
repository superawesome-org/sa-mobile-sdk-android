package tv.superawesome.sdk.publisher.common.datasources

import tv.superawesome.sdk.publisher.common.models.*
import tv.superawesome.sdk.publisher.common.network.DataResult

interface AwesomeAdsApiDataSourceType {

    suspend fun getAd(placementId: Int, query: AdQueryBundle): DataResult<Ad>

    suspend fun getAd(placementId: Int, lineItemId: Int, creativeId: Int, query: AdQueryBundle): DataResult<Ad>

    suspend fun impression(query: EventQueryBundle): DataResult<Void>

    suspend fun click(query: EventQueryBundle): DataResult<Void>

    suspend fun videoClick(query: EventQueryBundle): DataResult<Void>

    suspend fun event(query: EventQueryBundle): DataResult<Void>
}