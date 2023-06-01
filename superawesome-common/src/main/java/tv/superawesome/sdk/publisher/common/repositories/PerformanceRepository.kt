package tv.superawesome.sdk.publisher.common.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tv.superawesome.sdk.publisher.common.models.PerformanceMetric
import tv.superawesome.sdk.publisher.common.network.DataResult
import tv.superawesome.sdk.publisher.common.network.datasources.AwesomeAdsApiDataSourceType

internal interface PerformanceRepositoryType {
  suspend fun sendMetric(metric: PerformanceMetric): DataResult<Unit>
}

internal class PerformanceRepository(
    private val dataSource: AwesomeAdsApiDataSourceType
) : PerformanceRepositoryType {

    override suspend fun sendMetric(metric: PerformanceMetric): DataResult<Unit> =
        withContext(Dispatchers.IO) {
            dataSource.performance(metric)
        }
}