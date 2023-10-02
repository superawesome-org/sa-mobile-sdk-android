package tv.superawesome.sdk.publisher.repositories

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import retrofit2.Retrofit
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.PerformanceMetric
import tv.superawesome.sdk.publisher.models.PerformanceMetricName
import tv.superawesome.sdk.publisher.models.PerformanceMetricType
import tv.superawesome.sdk.publisher.network.AwesomeAdsApi
import tv.superawesome.sdk.publisher.network.DataResult
import tv.superawesome.sdk.publisher.network.datasources.AwesomeAdsApiDataSource
import tv.superawesome.sdk.publisher.network.datasources.MockServerTest
import tv.superawesome.sdk.publisher.testutil.FakeAdQueryMaker
import tv.superawesome.sdk.publisher.testutil.FakeFactory
import tv.superawesome.sdk.publisher.testutil.decodeParams
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalSerializationApi::class, ExperimentalCoroutinesApi::class)
class PerformanceRepositoryTest : MockServerTest() {

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

    private val datasource = AwesomeAdsApiDataSource(api, json)

    private val adQueryMaker = FakeAdQueryMaker()

    private val sut = PerformanceRepository(datasource, adQueryMaker)
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
        assertIs<DataResult.Success<Unit>>(result)
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
        assertIs<DataResult.Failure>(result)
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
