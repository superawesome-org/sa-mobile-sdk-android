package tv.superawesome.sdk.publisher.network.datasources

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.internal.closeQuietly
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import retrofit2.Retrofit
import tv.superawesome.sdk.publisher.models.Ad
import tv.superawesome.sdk.publisher.models.AdQuery
import tv.superawesome.sdk.publisher.models.AdQueryBundle
import tv.superawesome.sdk.publisher.models.ConnectionType
import tv.superawesome.sdk.publisher.models.EventQuery
import tv.superawesome.sdk.publisher.models.EventQueryBundle
import tv.superawesome.sdk.publisher.models.EventType
import tv.superawesome.sdk.publisher.models.PerformanceMetric
import tv.superawesome.sdk.publisher.models.PerformanceMetricName
import tv.superawesome.sdk.publisher.models.PerformanceMetricType
import tv.superawesome.sdk.publisher.network.AwesomeAdsApi
import tv.superawesome.sdk.publisher.network.DataResult
import tv.superawesome.sdk.publisher.network.enqueueResponse
import tv.superawesome.sdk.publisher.testutil.ResourceReader
import java.util.concurrent.TimeUnit
import kotlin.test.assertIs

@OptIn(ExperimentalSerializationApi::class, ExperimentalCoroutinesApi::class)
class AwesomeAdsApiDataSourceTest : MockServerTest() {

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
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


    val sut = AwesomeAdsApiDataSource(api)

    @Test
    fun `when fetching ads it should return Success on 200`() = runTest {
        // Given
        mockServer.enqueueResponse("mock_ad_response_1.json", 200)
        val adQueryBundle = AdQueryBundle(getAdQuery(), null)

        // When
        val result = sut.getAd(1234, adQueryBundle)

        // Then
        assertIs<DataResult.Success<Ad>>(result)
    }

    @Test
    fun `when fetching ads (all params) it should return Success on 200`() = runTest {
        // Given
        mockServer.enqueueResponse("mock_ad_response_1.json", 200)
        val adQueryBundle = AdQueryBundle(getAdQuery(), null)

        // When
        val result = sut.getAd(1234, 1234, 1234, adQueryBundle)

        // Then
        assertIs<DataResult.Success<Ad>>(result)
    }

    @Test
    fun `when fetching ads it (all params) should return Failure on malformed response`() = runTest {
        // Given
        mockServer.enqueueResponse("mock_ad_malformed_response.json", 200)
        val adQueryBundle = AdQueryBundle(getAdQuery(), null)

        // When
        val result = sut.getAd(1234, 1234, 1234, adQueryBundle)

        // Then
        assertIs<DataResult.Failure>(result)
    }

    @Test
    fun `when fetching ads, it should return Failure if network connection times out`() = runTest {
        // Given
        mockServer.enqueue(
            MockResponse()
                .setBodyDelay(2L, TimeUnit.SECONDS)
                .setBody(ResourceReader.readResource("mock_ad_response_1.json"))
                .setResponseCode(200)
        )
        val adQueryBundle = AdQueryBundle(getAdQuery(), null)

        // When
        val result = sut.getAd(1234, adQueryBundle)

        // Then
        println(result)
        assertIs<DataResult.Failure>(result)

        // Finally
        mockServer.closeQuietly()
    }

    @Test
    fun `when sending an impression, it should return success`() = runTest {
        testSuccess { sut.impression(it) }
    }

    @Test
    fun `when sending an impression, it should return failure if connection fails`() = runTest {
        testFailure { sut.impression(it) }
    }

    @Test
    fun `when sending a click, it should return success`() = runTest {
        testSuccess { sut.click(it) }
    }

    @Test
    fun `when sending a click, it should return failure if connection fails`() = runTest {
        testFailure { sut.click(it) }
    }

    @Test
    fun `when sending a video click, it should return success`() = runTest {
        testSuccess { sut.videoClick(it) }
    }

    @Test
    fun `when sending a video click, it should return failure if connection fails`() = runTest {
        testFailure { sut.videoClick(it) }
    }

    @Test
    fun `when sending an event, it should return success`() = runTest {
        testSuccess { sut.event(it) }
    }

    @Test
    fun `when sending an event, it should return failure if connection fails`() = runTest {
        testFailure { sut.event(it) }
    }

    @Test
    fun `when sending a performance metric, it should return success`() = runTest {
        // When
        mockServer.enqueue(MockResponse().setResponseCode(200))
        val metric = PerformanceMetric(0, PerformanceMetricName.LoadTime, PerformanceMetricType.Timing)

        // When
        val result = sut.performance(metric)

        // Then
        assertIs<DataResult.Success<Unit>>(result)
    }

    @Test
    fun `when sending a performance metric, it should return failure if connection fails`() = runTest {
        // When
        mockServer.enqueue(MockResponse().setResponseCode(500))
        val metric = PerformanceMetric(0, PerformanceMetricName.LoadTime, PerformanceMetricType.Timing)

        // When
        val result = sut.performance(metric)

        // Then
        assertIs<DataResult.Failure>(result)
    }


    private suspend fun <T : Any> testSuccess(block: suspend (EventQueryBundle) -> DataResult<T>) {
        // Given
        mockServer.enqueue(MockResponse().setResponseCode(200))
        val eventBundle = EventQueryBundle(getEventQuery(), null)

        // When
        val result = block(eventBundle)

        // Then
        assertIs<DataResult.Success<T>>(result)
    }

    private suspend fun <T : Any> testFailure(block: suspend (EventQueryBundle) -> DataResult<T>) {
        // Given
        mockServer.enqueue(MockResponse().setResponseCode(500))
        val eventBundle = EventQueryBundle(getEventQuery(), null)

        // When
        val result = block(eventBundle)

        // Then
        assertIs<DataResult.Failure>(result)
    }

    private fun getAdQuery() = AdQuery(
        test = false,
        sdkVersion = "1.0.0",
        rnd = 0,
        bundle = "",
        name = "",
        dauId = 0,
        ct = ConnectionType.Ethernet.ordinal,
        device = "",
        lang = "",
        pos = 0,
        skip = 0,
        playbackMethod = 0,
        startDelay = 0,
        install = 0,
        w = 0,
        h = 0,
        timestamp = 0L,
    )

    private fun getEventQuery() = EventQuery(
        placement = 0,
        bundle = "",
        creative = 0,
        lineItem = 0,
        ct = ConnectionType.Wifi.ordinal,
        sdkVersion = "",
        rnd = "",
        type = EventType.ImpressionDownloaded,
        noImage = null,
        data = null,
    )
}
