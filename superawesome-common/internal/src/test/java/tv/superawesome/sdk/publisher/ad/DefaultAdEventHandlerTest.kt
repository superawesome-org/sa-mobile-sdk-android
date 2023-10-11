package tv.superawesome.sdk.publisher.ad

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
import tv.superawesome.sdk.publisher.di.testCommonModule
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.EventData
import tv.superawesome.sdk.publisher.models.EventType
import tv.superawesome.sdk.publisher.network.datasources.MockServerTest
import tv.superawesome.sdk.publisher.repositories.EventRepositoryType
import tv.superawesome.sdk.publisher.testutil.decodeDataParams
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultAdEventHandlerTest : MockServerTest(), KoinTest {

    private val eventRepository by inject<EventRepositoryType>()
    private val adResponse = AdResponse(
        placementId = 12345,
        ad = mockk(relaxed = true),
    )

    private val sut by lazy {
        DefaultAdEventHandler(eventRepository, adResponse)
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
    fun `click event is sent on click`() = runTest {
        // arrange
        mockServer.enqueue(MockResponse().setResponseCode(200))
        val expected = listOf("click")

        // act
        sut.click()

        // assert
        val endpoint = mockServer.takeRequest().requestUrl?.pathSegments

        assertEquals(expected, endpoint)
    }

    @Test
    fun `video click event is sent on videoClick`() = runTest {
        // arrange
        mockServer.enqueue(MockResponse().setResponseCode(200))
        val expected = listOf("video", "click")

        // act
        sut.videoClick()

        // assert
        val endpoint = mockServer.takeRequest().requestUrl?.pathSegments

        assertEquals(expected, endpoint)
    }

    @Test
    fun `impression event is sent on triggerImpressionEvent`() = runTest {
        // arrange
        mockServer.enqueue(MockResponse().setResponseCode(200))
        val expected = listOf("impression")

        // act
        sut.triggerImpressionEvent()

        // assert
        val request = mockServer.takeRequest()
        val endpoint = request.requestUrl?.pathSegments
        val type = request.requestUrl?.queryParameter("type")

        assertEquals("impressionDownloaded", type)
        assertEquals(expected, endpoint)
    }

    @Test
    fun `dwell time event is sent on triggerDwellEvent`() = runTest {
        testEvent(EventType.DwellTime) { sut.triggerDwellTime() }
    }

    @Test
    fun `viewable impression event is sent on triggerViewableImpression`() = runTest {
        testEvent(EventType.ViewableImpression) { sut.triggerViewableImpression() }
    }

    @Test
    fun `parental gate open event is sent on parentalGateOpen`() = runTest {
        testEvent(EventType.ParentalGateOpen) { sut.parentalGateOpen() }
    }

    @Test
    fun `parental gate close event is sent on parentalGateClose`() = runTest {
        testEvent(EventType.ParentalGateClose) { sut.parentalGateClose() }
    }

    @Test
    fun `parental gate success event is sent on parentalGateSuccess`() = runTest {
        testEvent(EventType.ParentalGateSuccess) { sut.parentalGateSuccess() }
    }

    @Test
    fun `parental gate fail event is sent on parentalGateFail`() = runTest {
        testEvent(EventType.ParentalGateFail) { sut.parentalGateFail() }
    }

    private suspend fun testEvent(
        eventType: EventType,
        block: suspend () -> Unit,
    ) {
        // act
        mockServer.enqueue(MockResponse().setResponseCode(200))
        val expected = listOf("event")

        // arrange
        block()

        // assert
        val requestUrl = mockServer.takeRequest().requestUrl
        val endpoint = requestUrl?.pathSegments
        val eventData = decodeDataParams<EventData>(requestUrl?.queryParameter("data"))

        assertEquals(expected, endpoint)
        assertEquals(eventType, eventData?.type)
    }
}
