package tv.superawesome.sdk.publisher.common.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tv.superawesome.sdk.publisher.common.components.TimeProviderType
import tv.superawesome.sdk.publisher.common.models.PerformanceMetric
import tv.superawesome.sdk.publisher.common.models.PerformanceMetricName
import tv.superawesome.sdk.publisher.common.models.PerformanceMetricType
import tv.superawesome.sdk.publisher.common.models.PerformanceTimer
import tv.superawesome.sdk.publisher.common.network.DataResult
import tv.superawesome.sdk.publisher.common.network.datasources.AwesomeAdsApiDataSourceType

internal interface PerformanceRepositoryType {
    fun startTimingForCloseButtonPressed()
    suspend fun trackCloseButtonPressed()
    suspend fun sendMetric(metric: PerformanceMetric): DataResult<Unit>
}

internal class PerformanceRepository(
    private val dataSource: AwesomeAdsApiDataSourceType,
    private val timeProvider: TimeProviderType
) : PerformanceRepositoryType {

    private val closeButtonPressedTimer = PerformanceTimer()

    override fun startTimingForCloseButtonPressed() {
        closeButtonPressedTimer.start(timeProvider.millis())
    }

    override suspend fun trackCloseButtonPressed() {
        if (closeButtonPressedTimer.startTime == 0L) { return }
        val delta = closeButtonPressedTimer.delta(timeProvider.millis())
        val metric = PerformanceMetric(
            delta,
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