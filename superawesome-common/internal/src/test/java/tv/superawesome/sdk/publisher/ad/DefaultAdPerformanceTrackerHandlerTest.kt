package tv.superawesome.sdk.publisher.ad

import io.mockk.every
import io.mockk.mockk
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
import tv.superawesome.sdk.publisher.components.TimeProviderType
import tv.superawesome.sdk.publisher.di.testCommonModule
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.PerformanceMetricName
import tv.superawesome.sdk.publisher.network.datasources.MockServerTest
import tv.superawesome.sdk.publisher.repositories.PerformanceRepositoryType
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultAdPerformanceTrackerHandlerTest : MockServerTest(), KoinTest {

    private val performanceRepository by inject<PerformanceRepositoryType>()
    private val timeProvider = mockk<TimeProviderType>()
    private val adResponse = AdResponse(
        placementId = 12345,
        ad = mockk(relaxed = true),
    )

    private val sut by lazy {
        DefaultAdPerformanceTrackerHandler(performanceRepository, timeProvider, adResponse)
    }

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
    fun `tracking dwell time`() = runTest {
        checkTrackedTime(PerformanceMetricName.DwellTime) {
            sut.startTimerForDwellTime()
            sut.trackDwellTime()
        }
    }

    @Test
    fun `tracking load time`() = runTest {
        checkTrackedTime(PerformanceMetricName.LoadTime) {
            sut.startTimerForLoadTime()
            sut.trackLoadTime()
        }
    }

    @Test
    fun `tracking close button pressed time`() = runTest {
        checkTrackedTime(PerformanceMetricName.CloseButtonPressTime) {
            sut.startTimerForCloseButtonPressed()
            sut.trackCloseButtonPressed()
        }
    }

    @Test
    fun `tracking render time`() = runTest {
        checkTrackedTime(PerformanceMetricName.RenderTime) {
            sut.startTimerForRenderTime()
            sut.trackRenderTime()
        }
    }

    private suspend fun checkTrackedTime(metricName: PerformanceMetricName, tracker: suspend () -> Unit) {
        // arrange
        mockServer.enqueue(MockResponse().setResponseCode(200))
        every { timeProvider.millis() } returnsMany listOf(100, 200)

        // act
        tracker()

        // assert
        val request = mockServer.takeRequest()

        assertEquals(listOf("sdk", "performance"), request.requestUrl?.pathSegments)
        assertEquals("100", request.requestUrl?.queryParameter("value"))
        assertEquals(metricName.value, request.requestUrl?.queryParameter("metricName"))
    }
}