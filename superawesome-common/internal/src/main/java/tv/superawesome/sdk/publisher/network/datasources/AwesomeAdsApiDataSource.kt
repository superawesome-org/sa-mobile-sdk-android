package tv.superawesome.sdk.publisher.network.datasources

import kotlinx.coroutines.delay
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import tv.superawesome.sdk.publisher.models.Ad
import tv.superawesome.sdk.publisher.models.AdQueryBundle
import tv.superawesome.sdk.publisher.models.EventQueryBundle
import tv.superawesome.sdk.publisher.models.PerformanceMetric
import tv.superawesome.sdk.publisher.network.AwesomeAdsApi

interface AwesomeAdsApiDataSourceType {

    suspend fun getAd(placementId: Int, query: AdQueryBundle): Result<Ad>

    suspend fun getAd(
        placementId: Int, lineItemId: Int, creativeId: Int, query: AdQueryBundle
    ): Result<Ad>

    suspend fun impression(query: EventQueryBundle): Result<Unit>

    suspend fun click(query: EventQueryBundle): Result<Unit>

    suspend fun videoClick(query: EventQueryBundle): Result<Unit>

    suspend fun event(query: EventQueryBundle): Result<Unit>

    suspend fun performance(metric: PerformanceMetric): Result<Unit>
}

@ExperimentalSerializationApi
@Suppress("TooGenericExceptionCaught")
class AwesomeAdsApiDataSource(
    private val awesomeAdsApi: AwesomeAdsApi,
    private val json: Json,
): AwesomeAdsApiDataSourceType {

    override suspend fun getAd(placementId: Int, query: AdQueryBundle): Result<Ad> =
        runCatching {
            println("getAd($placementId)##")
            val a = awesomeAdsApi.ad(placementId, query.build())
            println("getAd($a)$$")
            delay(2000)
            a
        }.also {
            println(it.isFailure)
        }

    override suspend fun getAd(
        placementId: Int, lineItemId: Int, creativeId: Int, query: AdQueryBundle
    ): Result<Ad> = runCatching {
        awesomeAdsApi.ad(placementId, lineItemId, creativeId, query.build())
    }

    override suspend fun impression(query: EventQueryBundle): Result<Unit> = runCatching {
        awesomeAdsApi.impression(query.build())
    }

    override suspend fun click(query: EventQueryBundle): Result<Unit> = runCatching {
        awesomeAdsApi.click(query.build())
    }

    override suspend fun videoClick(query: EventQueryBundle): Result<Unit> = runCatching {
        awesomeAdsApi.videoClick(query.build())
    }

    override suspend fun event(query: EventQueryBundle): Result<Unit> = runCatching {
        awesomeAdsApi.event(query.build())
    }

    override suspend fun performance(metric: PerformanceMetric): Result<Unit> = runCatching {
        awesomeAdsApi.performance(metric.build(json))
    }
}
