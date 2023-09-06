package tv.superawesome.sdk.publisher.common.repositories

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import retrofit2.Retrofit
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.models.EventData
import tv.superawesome.sdk.publisher.common.models.EventType
import tv.superawesome.sdk.publisher.common.network.AwesomeAdsApi
import tv.superawesome.sdk.publisher.common.network.DataResult
import tv.superawesome.sdk.publisher.common.network.datasources.AwesomeAdsApiDataSource
import tv.superawesome.sdk.publisher.common.network.datasources.MockServerTest
import tv.superawesome.sdk.publisher.common.testutil.FakeAdQueryMaker
import tv.superawesome.sdk.publisher.common.testutil.decodeDataParams
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalSerializationApi::class)
class EventRepositoryTest : MockServerTest() {

    private val client = OkHttpClient.Builder()
        .connectTimeout(100, TimeUnit.MILLISECONDS)
        .readTimeout(100, TimeUnit.MILLISECONDS)
        .writeTimeout(100, TimeUnit.MILLISECONDS)
        .build()

    private val json = Json {
        allowStructuredMapKeys = true
        ignoreUnknownKeys = true
    }

    private val api = Retrofit.Builder()
        .baseUrl(mockServer.url("/"))
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(AwesomeAdsApi::class.java)

    private val sut = EventRepository(
        dataSource = AwesomeAdsApiDataSource(api),
        adQueryMaker = FakeAdQueryMaker(),
    )

    @Test
    fun `when sending impression, it should return the request result`() = runTest {
        // Given
        mockServer.enqueue(MockResponse().setResponseCode(200))
        val expected = listOf("impression")

        // When
        val result = sut.impression(fakeAdResponse())

        // Then
        val request = mockServer.takeRequest()
        val endpoint = request.requestUrl?.pathSegments
        val type = request.requestUrl?.queryParameter("type")

        assertEquals("impressionDownloaded", type)
        assertEquals(expected, endpoint)
        assertIs<DataResult.Success<Unit>>(result)
    }

    @Test
    fun `when sending click, it should return the request result`() = runTest {
        // Given
        mockServer.enqueue(MockResponse().setResponseCode(200))
        val expected = listOf("click")

        // When
        val result = sut.click(fakeAdResponse())

        // Then
        val endpoint = mockServer.takeRequest().requestUrl?.pathSegments

        assertEquals(expected, endpoint)
        assertIs<DataResult.Success<Unit>>(result)
    }

    @Test
    fun `when sending video click, it should return the request result`() = runTest {
        // Given
        mockServer.enqueue(MockResponse().setResponseCode(200))
        val expected = listOf("video", "click")

        // When
        val result = sut.videoClick(fakeAdResponse())

        // Then
        val endpoint = mockServer.takeRequest().requestUrl?.pathSegments

        assertEquals(expected, endpoint)
        assertIs<DataResult.Success<Unit>>(result)
    }

    @Test
    fun `when sending parental gate open, it should return the request result`() = runTest {
        testEvent(EventType.ParentalGateOpen) { sut.parentalGateOpen(fakeAdResponse()) }
    }

    @Test
    fun `when sending parental gate close, it should return the request result`() = runTest {
        testEvent(EventType.ParentalGateClose) { sut.parentalGateClose(fakeAdResponse()) }
    }

    @Test
    fun `when sending parental gate success, it should return the request result`() = runTest {
        testEvent(EventType.ParentalGateSuccess) { sut.parentalGateSuccess(fakeAdResponse()) }
    }

    @Test
    fun `when sending parental gate fail, it should return the request result`() = runTest {
        testEvent(EventType.ParentalGateFail) { sut.parentalGateFail(fakeAdResponse()) }
    }

    @Test
    fun `when sending viewable impression, it should return the request result`() = runTest {
        testEvent(EventType.ViewableImpression) { sut.viewableImpression(fakeAdResponse()) }
    }

    @Test
    fun `when sending dwell time, it should return the request result`() = runTest {
        testEvent(EventType.DwellTime) { sut.oneSecondDwellTime(fakeAdResponse()) }
    }

    private suspend fun <T : Any> testEvent(
        eventType: EventType,
        block: suspend () -> DataResult<T>,
    ) {
        // Given
        mockServer.enqueue(MockResponse().setResponseCode(200))
        val expected = listOf("event")

        // When
        val result = block()

        // Then
        val requestUrl = mockServer.takeRequest().requestUrl
        val endpoint = requestUrl?.pathSegments
        val eventData = decodeDataParams<EventData>(requestUrl?.queryParameter("data"))

        assertEquals(expected, endpoint)
        assertEquals(eventType, eventData?.type)
        assertIs<DataResult.Success<Unit>>(result)
    }

    private fun fakeAdResponse() = AdResponse(
        placementId = 1234,
        ad = mockk(relaxed = true),
    )
}
