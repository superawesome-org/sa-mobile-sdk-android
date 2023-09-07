package tv.superawesome.sdk.publisher.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tv.superawesome.sdk.publisher.models.PerformanceMetric
import tv.superawesome.sdk.publisher.models.PerformanceMetricName
import tv.superawesome.sdk.publisher.models.PerformanceMetricType
import tv.superawesome.sdk.publisher.network.DataResult
import tv.superawesome.sdk.publisher.network.datasources.AwesomeAdsApiDataSourceType

interface PerformanceRepositoryType {
    suspend fun trackLoadTime(duration: Long)
    suspend fun trackDwellTime(duration: Long)
    suspend fun trackCloseButtonPressed(duration: Long)
    suspend fun sendMetric(metric: PerformanceMetric): DataResult<Unit>
}

class PerformanceRepository(
    private val dataSource: AwesomeAdsApiDataSourceType
) : PerformanceRepositoryType {
    override suspend fun trackLoadTime(duration: Long) {
        val metric = PerformanceMetric(
            duration,
            PerformanceMetricName.LoadTime,
            PerformanceMetricType.Gauge,
        )
        sendMetric(metric)
    }

    override suspend fun trackDwellTime(duration: Long) {
        val metric = PerformanceMetric(
            duration,
            PerformanceMetricName.DwellTime,
            PerformanceMetricType.Gauge
        )
        sendMetric(metric)
    }

    override suspend fun trackCloseButtonPressed(duration: Long) {
        val metric = PerformanceMetric(
            duration,
            PerformanceMetricName.CloseButtonPressTime,
            PerformanceMetricType.Gauge
        )
        sendMetric(metric)
    }

    override suspend fun sendMetric(metric: PerformanceMetric): DataResult<Unit> =
        withContext(Dispatchers.IO) {
            dataSource.performance(metric)
        }
}
