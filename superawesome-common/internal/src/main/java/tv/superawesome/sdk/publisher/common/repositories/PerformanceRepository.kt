package tv.superawesome.sdk.publisher.common.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tv.superawesome.sdk.publisher.common.models.PerformanceMetric
import tv.superawesome.sdk.publisher.common.models.PerformanceMetricName
import tv.superawesome.sdk.publisher.common.models.PerformanceMetricType
import tv.superawesome.sdk.publisher.common.network.DataResult
import tv.superawesome.sdk.publisher.common.network.datasources.AwesomeAdsApiDataSourceType

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
