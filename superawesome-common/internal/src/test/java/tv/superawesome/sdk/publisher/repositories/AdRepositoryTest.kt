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
import tv.superawesome.sdk.publisher.network.enqueueResponse
import tv.superawesome.sdk.publisher.testutil.FakeAdProcessor
import tv.superawesome.sdk.publisher.testutil.FakeAdQueryMaker
import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.network.AwesomeAdsApi
import tv.superawesome.sdk.publisher.network.datasources.AwesomeAdsApiDataSource
import tv.superawesome.sdk.publisher.network.datasources.MockServerTest
import tv.superawesome.sdk.publisher.testutil.fakeAdRequest
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalSerializationApi::class)
class AdRepositoryTest : MockServerTest() {

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

    private val sut = AdRepository(
        dataSource = AwesomeAdsApiDataSource(api, json),
        adQueryMaker = FakeAdQueryMaker(),
        adProcessor = FakeAdProcessor(),
    )

    @Test
    fun `when getAd is called, return the processed ad`() = runTest {
        // Given
        mockServer.enqueueResponse("mock_ad_response_1.json", 200)

        // When
        val result = sut.getAd(1234, fakeAdRequest())

        // Then
        assertTrue(result.isSuccess)
        assertEquals(1234, result.getOrNull()?.placementId)
    }

    @Test
    fun `when getAd multi id is called, return the processed ad`() = runTest {
        // Given
        mockServer.enqueueResponse("mock_ad_response_1.json", 200)

        // When
        val result = sut.getAd(1234, 9, 99, fakeAdRequest())

        // Then
        assertTrue(result.isSuccess)
        assertEquals(1234, result.getOrNull()?.placementId)
    }

    @Test
    fun `when getAd is called, given request failure, return the failure`() = runTest {
        // Given
        mockServer.enqueue(MockResponse().setResponseCode(404))

        // When
        val result = sut.getAd(1234, fakeAdRequest())

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `when getAd multi id is called, given request failure, return the failure`() = runTest {
        // Given
        mockServer.enqueue(MockResponse().setResponseCode(404))

        // When
        val result = sut.getAd(1234, 9, 99, fakeAdRequest())

        // Then
        assertTrue(result.isFailure)
    }
}
