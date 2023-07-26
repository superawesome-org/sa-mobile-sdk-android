package tv.superawesome.sdk.publisher.common.network.datasources

import kotlinx.serialization.ExperimentalSerializationApi
import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.AdQueryBundle
import tv.superawesome.sdk.publisher.common.models.EventQueryBundle
import tv.superawesome.sdk.publisher.common.models.PerformanceMetric
import tv.superawesome.sdk.publisher.common.network.AwesomeAdsApi
import tv.superawesome.sdk.publisher.common.network.DataResult

interface AwesomeAdsApiDataSourceType {

    suspend fun getAd(placementId: Int, query: AdQueryBundle): DataResult<Ad>

    suspend fun getAd(
        placementId: Int, lineItemId: Int, creativeId: Int, query: AdQueryBundle
    ): DataResult<Ad>

    suspend fun impression(query: EventQueryBundle): DataResult<Unit>

    suspend fun click(query: EventQueryBundle): DataResult<Unit>

    suspend fun videoClick(query: EventQueryBundle): DataResult<Unit>

    suspend fun event(query: EventQueryBundle): DataResult<Unit>

    suspend fun performance(metric: PerformanceMetric): DataResult<Unit>
}

@ExperimentalSerializationApi
@Suppress("TooGenericExceptionCaught")
class AwesomeAdsApiDataSource(private val awesomeAdsApi: AwesomeAdsApi) :
    AwesomeAdsApiDataSourceType {

    override suspend fun getAd(placementId: Int, query: AdQueryBundle): DataResult<Ad> = try {
        DataResult.Success(
            awesomeAdsApi.ad(
                placementId, query.build()
            )
        )
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }

    override suspend fun getAd(
        placementId: Int, lineItemId: Int, creativeId: Int, query: AdQueryBundle
    ): DataResult<Ad> = try {
        DataResult.Success(
            awesomeAdsApi.ad(
                placementId, lineItemId, creativeId, query.build()
            )
        )
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }

    override suspend fun impression(query: EventQueryBundle): DataResult<Unit> = try {
        DataResult.Success(
            awesomeAdsApi.impression(
                query.build()
            )
        )
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }

    override suspend fun click(query: EventQueryBundle): DataResult<Unit> = try {
        DataResult.Success(
            awesomeAdsApi.click(
                query.build()
            )
        )
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }

    override suspend fun videoClick(query: EventQueryBundle): DataResult<Unit> = try {
        DataResult.Success(
            awesomeAdsApi.videoClick(
                query.build()
            )
        )
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }

    override suspend fun event(query: EventQueryBundle): DataResult<Unit> = try {
        DataResult.Success(
            awesomeAdsApi.event(
                query.build()
            )
        )
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }

    override suspend fun performance(metric: PerformanceMetric): DataResult<Unit> = try {
        DataResult.Success(
            awesomeAdsApi.performance(
                metric.build()
            )
        )
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }
}
