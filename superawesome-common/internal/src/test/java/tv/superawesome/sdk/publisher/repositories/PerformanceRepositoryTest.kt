package tv.superawesome.sdk.publisher.repositories

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import tv.superawesome.sdk.publisher.di.testCommonModule
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.PerformanceMetric
import tv.superawesome.sdk.publisher.models.PerformanceMetricName
import tv.superawesome.sdk.publisher.models.PerformanceMetricType
import tv.superawesome.sdk.publisher.network.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.network.datasources.MockServerTest
import tv.superawesome.sdk.publisher.testutil.FakeAdQueryMaker
import tv.superawesome.sdk.publisher.testutil.FakeFactory
import tv.superawesome.sdk.publisher.testutil.decodeParams
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class PerformanceRepositoryTest : MockServerTest(), KoinTest {

    private val dataSource by inject<AwesomeAdsApiDataSourceType>()
    private val adQueryMaker = FakeAdQueryMaker()

    private val sut by lazy { PerformanceRepository(dataSource, adQueryMaker) }

    @Before
    override fun setup() {
        super.setup()
        startKoin { modules(testCommonModule(mockServer)) }
    }

    @After
    override fun tearDown() {
        super.tearDown()
        stopKoin()
    }

    @Test
    fun `when sending metrics, it should send and return success`() = runTest {
        // Given
        mockServer.enqueue(MockResponse().setResponseCode(200))
        val metric = PerformanceMetric(
            0L,
            PerformanceMetricName.LoadTime,
            PerformanceMetricType.Timing,
            adQueryMaker.makePerformanceTags(FakeFactory.makeFakeAdResponse())
        )

        // When
        val result = sut.sendMetric(metric)

        // Then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `when sending metrics, if it fails, should return failure`() = runTest {
        // Given
        mockServer.enqueue(MockResponse().setResponseCode(500))
        val metric = PerformanceMetric(
            0L,
            PerformanceMetricName.LoadTime,
            PerformanceMetricType.Timing,
            adQueryMaker.makePerformanceTags(FakeFactory.makeFakeAdResponse())
        )

        // When
        val result = sut.sendMetric(metric)

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `when sending track dwell time, it should send the correct params`() = runTest {
        testTracking(
            PerformanceMetricName.DwellTime,
            PerformanceMetricType.Gauge,
            100L,
            FakeFactory.makeFakeAdResponse(),
        ) { value, adResponse -> sut.trackDwellTime(value, adResponse) }
    }

    @Test
    fun `when sending track load time, it should send the correct params`() = runTest {
        testTracking(
            PerformanceMetricName.LoadTime,
            PerformanceMetricType.Gauge,
            100L,
            FakeFactory.makeFakeAdResponse(),
        ) { value, adResponse -> sut.trackLoadTime(value, adResponse) }
    }

    @Test
    fun `when sending track close button time, it should send the correct params`() = runTest {
        testTracking(
            PerformanceMetricName.CloseButtonPressTime,
            PerformanceMetricType.Gauge,
            100L,
            FakeFactory.makeFakeAdResponse(),
        ) { value, adResponse -> sut.trackCloseButtonPressed(value, adResponse) }
    }

    @Test
    fun `when sending track render time, it should send the correct params`() = runTest {
        testTracking(
            PerformanceMetricName.RenderTime,
            PerformanceMetricType.Gauge,
            100L,
            FakeFactory.makeFakeAdResponse(),
        ) { value, adResponse -> sut.trackRenderTime(value, adResponse) }
    }

    @Test
    fun `when sending close button fallback, it should send the correct params`() = runTest {
        testTracking(
            PerformanceMetricName.CloseButtonFallback,
            PerformanceMetricType.Increment,
            1,
            FakeFactory.makeFakeAdResponse(),
        ) { _, adResponse -> sut.trackCloseButtonFallbackShown(adResponse) }
    }

    private suspend fun testTracking(
        metricName: PerformanceMetricName,
        metricType: PerformanceMetricType,
        value: Long,
        adResponse: AdResponse,
        block: suspend (Long, AdResponse) -> Unit,
    ) {
        // Given
        mockServer.enqueue(MockResponse().setResponseCode(200))

        // When
        block(value, adResponse)

        // Then
        val request = mockServer.takeRequest()
        val params = decodeParams(request.requestUrl)

        // Then
        assertEquals(metricName.value, params["metricName"])
        assertEquals(metricType.value, params["metricType"])
        assertEquals(value, params["value"]?.toLong())
    }
}
