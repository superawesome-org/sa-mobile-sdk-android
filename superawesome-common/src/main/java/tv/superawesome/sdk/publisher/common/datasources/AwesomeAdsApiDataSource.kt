package tv.superawesome.sdk.publisher.common.datasources

import kotlinx.serialization.ExperimentalSerializationApi
import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.AdQuery
import tv.superawesome.sdk.publisher.common.models.EventQuery
import tv.superawesome.sdk.publisher.common.network.DataResult

interface AwesomeAdsApiDataSourceType {

    @ExperimentalSerializationApi
    suspend fun getAd(placementId: Int, query: AdQuery): DataResult<Ad>

    @ExperimentalSerializationApi
    suspend fun getAd(lineItemId: Int, creativeId: Int, query: AdQuery): DataResult<Ad>

    @ExperimentalSerializationApi
    suspend fun impression(query: EventQuery): DataResult<Void>

    @ExperimentalSerializationApi
    suspend fun click(query: EventQuery): DataResult<Void>

    @ExperimentalSerializationApi
    suspend fun videoClick(query: EventQuery): DataResult<Void>

    @ExperimentalSerializationApi
    suspend fun event(query: EventQuery): DataResult<Void>
}