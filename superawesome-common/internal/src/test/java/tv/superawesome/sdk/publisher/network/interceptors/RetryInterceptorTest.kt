package tv.superawesome.sdk.publisher.network.interceptors

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import tv.superawesome.sdk.publisher.network.AwesomeAdsApi
import tv.superawesome.sdk.publisher.network.enqueueResponse
import tv.superawesome.sdk.publisher.testutil.TestLogger
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalSerializationApi::class, ExperimentalCoroutinesApi::class)
class RetryInterceptorTest {

    private val mockServer = MockWebServer()
    private val client = OkHttpClient.Builder()
        .addInterceptor(RetryInterceptor(2, TestLogger()))
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

    @Before
    fun setup() {
        mockServer.start()
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun `when intercepting, if the response is not successful, retry`() = runTest {
        // Given
        mockServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))
        mockServer.enqueueResponse("mock_ad_response_1.json", 200)

        // When
        val ad = api.ad(placementId = 1234, query = emptyMap())

        // Then
        assertEquals(4906, ad.creative.id)
    }

    @Test
    fun `when intercepting, if the response fails more than max retries, fail`() = runTest {
        // Given
        mockServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))
        mockServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))
        mockServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))

        // Then
        assertFailsWith<SocketTimeoutException> {
            // When
            api.ad(placementId = 1234, query = emptyMap())
        }
    }
}
