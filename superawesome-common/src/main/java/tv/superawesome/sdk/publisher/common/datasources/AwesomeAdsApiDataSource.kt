package tv.superawesome.sdk.publisher.common.datasources

import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.AdQuery
import tv.superawesome.sdk.publisher.common.models.EventQuery
import tv.superawesome.sdk.publisher.common.network.DataResult

interface AwesomeAdsApiDataSourceType {

    suspend fun getAd(placementId: Int, query: AdQuery): DataResult<Ad>

    suspend fun getAd(lineItemId: Int, creativeId: Int, query: AdQuery): DataResult<Ad>

    suspend fun impression(query: EventQuery): DataResult<Void>

    suspend fun click(query: EventQuery): DataResult<Void>

    suspend fun videoClick(query: EventQuery): DataResult<Void>

    suspend fun event(query: EventQuery): DataResult<Void>
}