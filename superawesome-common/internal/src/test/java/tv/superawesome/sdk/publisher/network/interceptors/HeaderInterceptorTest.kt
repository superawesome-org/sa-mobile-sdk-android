package tv.superawesome.sdk.publisher.network.interceptors

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import tv.superawesome.sdk.publisher.components.UserAgentProviderType
import tv.superawesome.sdk.publisher.network.AwesomeAdsApi
import tv.superawesome.sdk.publisher.network.enqueueResponse
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

@OptIn(ExperimentalSerializationApi::class, ExperimentalCoroutinesApi::class)
class HeaderInterceptorTest {

    private val userAgentProvider = mockk<UserAgentProviderType> {
        every { name } returns "UA"
    }

    private val mockServer = MockWebServer()
    private val client = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor(userAgentProvider))
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
    fun `when intercepting, add the 'User-Agent' header to the request`() = runTest {
        // Given
        mockServer.enqueueResponse("mock_ad_response_1.json", 200)

        // When
        api.click(query = emptyMap())
        val request = mockServer.takeRequest()
        val header = request.getHeader("User-Agent")

        // Then
        assertEquals("UA", header)
    }
}
