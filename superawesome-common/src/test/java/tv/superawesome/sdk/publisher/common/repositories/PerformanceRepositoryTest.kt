package tv.superawesome.sdk.publisher.common.repositories

import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tv.superawesome.sdk.publisher.common.base.BaseTest
import tv.superawesome.sdk.publisher.common.models.*
import tv.superawesome.sdk.publisher.common.network.DataResult
import tv.superawesome.sdk.publisher.common.network.datasources.AwesomeAdsApiDataSourceType
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
internal class PerformanceRepositoryTest : BaseTest() {

  @MockK
  lateinit var adDataSourceType: AwesomeAdsApiDataSourceType

  @InjectMockKs
  lateinit var repository: PerformanceRepository

  @Test
  fun test_performance_metric_success() = runTest {
    // Given
    val metric = PerformanceMetric(
        3.0,
        PerformanceMetricName.LoadTime,
        PerformanceMetricType.Gauge
    )

    coEvery { adDataSourceType.performance(metric) } returns DataResult.Success(Unit)

    // When
    val result = repository.sendMetric(metric)

    // Then
    assertEquals(true, result.isSuccess)
  }
}