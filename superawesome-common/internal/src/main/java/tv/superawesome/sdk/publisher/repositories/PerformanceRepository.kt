package tv.superawesome.sdk.publisher.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tv.superawesome.sdk.publisher.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.PerformanceMetric
import tv.superawesome.sdk.publisher.models.PerformanceMetricName
import tv.superawesome.sdk.publisher.models.PerformanceMetricType
import tv.superawesome.sdk.publisher.network.datasources.AwesomeAdsApiDataSourceType

interface PerformanceRepositoryType {
    suspend fun trackLoadTime(duration: Long, adResponse: AdResponse)
    suspend fun trackDwellTime(duration: Long, adResponse: AdResponse)
    suspend fun trackCloseButtonPressed(duration: Long, adResponse: AdResponse)

    /**
     * Tracks the render time of an Ad.
     *
     * @param duration how much time has passed.
     * @param adResponse the ad being tracked.
     */
    suspend fun trackRenderTime(duration: Long, adResponse: AdResponse)
    suspend fun sendMetric(metric: PerformanceMetric): Result<Unit>
}

class PerformanceRepository(
    private val dataSource: AwesomeAdsApiDataSourceType,
    private val adQueryMaker: AdQueryMakerType,
) : PerformanceRepositoryType {

    override suspend fun trackLoadTime(duration: Long, adResponse: AdResponse) {
        val metric = PerformanceMetric(
            duration,
            PerformanceMetricName.LoadTime,
            PerformanceMetricType.Gauge,
            adQueryMaker.makePerformanceTags(adResponse),
        )
        sendMetric(metric)
    }

    override suspend fun trackDwellTime(duration: Long, adResponse: AdResponse) {
        val metric = PerformanceMetric(
            duration,
            PerformanceMetricName.DwellTime,
            PerformanceMetricType.Gauge,
            adQueryMaker.makePerformanceTags(adResponse),
        )
        sendMetric(metric)
    }

    override suspend fun trackCloseButtonPressed(duration: Long, adResponse: AdResponse) {
        val metric = PerformanceMetric(
            duration,
            PerformanceMetricName.CloseButtonPressTime,
            PerformanceMetricType.Gauge,
            adQueryMaker.makePerformanceTags(adResponse),
        )
        sendMetric(metric)
    }

    override suspend fun trackRenderTime(duration: Long, adResponse: AdResponse) {
        val metric = PerformanceMetric(
            duration,
            PerformanceMetricName.RenderTime,
            PerformanceMetricType.Gauge,
            adQueryMaker.makePerformanceTags(adResponse),
        )
        sendMetric(metric)
    }

    override suspend fun sendMetric(metric: PerformanceMetric): Result<Unit> =
        withContext(Dispatchers.IO) {
            dataSource.performance(metric)
        }
}
