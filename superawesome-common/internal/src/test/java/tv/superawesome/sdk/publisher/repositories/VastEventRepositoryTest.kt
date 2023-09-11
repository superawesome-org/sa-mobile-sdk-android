package tv.superawesome.sdk.publisher.repositories

import android.content.Context
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import tv.superawesome.sdk.publisher.network.datasources.MockServerTest
import tv.superawesome.sdk.publisher.network.datasources.OkHttpNetworkDataSource
import tv.superawesome.sdk.publisher.testutil.FakeFactory
import tv.superawesome.sdk.publisher.testutil.TestLogger
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class VastEventRepositoryTest : MockServerTest() {

    private val client = OkHttpClient.Builder()
        .connectTimeout(100, TimeUnit.MILLISECONDS)
        .readTimeout(100, TimeUnit.MILLISECONDS)
        .writeTimeout(100, TimeUnit.MILLISECONDS)
        .build()

    private val context = mockk<Context>()

    private val datasource = OkHttpNetworkDataSource(client, context, TestLogger())

    private val vastAd = FakeFactory.makeVastAd(mockServer.url("vast").toString())

    private val sut = VastEventRepository(vastAd, datasource)

    @Test
    fun `when sending complete events, all should be sent to the correct address`() = runTest {
        testVastEvent(vastAd.completeEvents) { sut.complete() }
    }

    @Test
    fun `when sending start events, all should be sent to the correct address`() = runTest {
        testVastEvent(vastAd.startEvents) { sut.start() }
    }

    @Test
    fun `when sending first quartile events, all should be sent to the correct address`() = runTest {
        testVastEvent(vastAd.firstQuartileEvents) { sut.firstQuartile() }
    }

    @Test
    fun `when sending midpoint events, all should be sent to the correct address`() = runTest {
        testVastEvent(vastAd.midPointEvents) { sut.midPoint() }
    }

    @Test
    fun `when sending third quartile events, all should be sent to the correct address`() = runTest {
        testVastEvent(vastAd.thirdQuartileEvents) { sut.thirdQuartile() }
    }

    @Test
    fun `when sending error events, all should be sent to the correct address`() = runTest {
        testVastEvent(vastAd.errorEvents) { sut.error() }
    }

    @Test
    fun `when sending impression events, all should be sent to the correct address`() = runTest {
        testVastEvent(vastAd.impressionEvents) { sut.impression() }
    }

    @Test
    fun `when sending click tracking events, all should be sent to the correct address`() = runTest {
        testVastEvent(vastAd.clickTrackingEvents) { sut.clickTracking() }
    }

    @Test
    fun `when sending creative view events, all should be sent to the correct address`() = runTest {
        testVastEvent(vastAd.creativeViewEvents) { sut.creativeView() }
    }

    @Test
    fun `when sending click though event, it should be sent to the correct address`() = runTest {
        val clickThroughUrl = vastAd.clickThroughUrl ?: fail()
        testVastEvent(listOf(clickThroughUrl)) { sut.clickThrough() }
    }

    @Test
    fun `when sending click though event, given it's null, then it should not be sent`() = runTest {
        // Given
        val vastAd = vastAd.copy(clickThroughUrl = null)
        val newSut = VastEventRepository(vastAd, datasource)

        // When
        newSut.clickThrough()

        // Then
        val url = mockServer.takeRequest(timeout = 100L, unit = TimeUnit.MILLISECONDS)
        assertNull(url)
    }

    private suspend fun testVastEvent(urls: List<String>, block: suspend () -> Unit) {
        // Given
        repeat(urls.size) {
            mockServer.enqueue(MockResponse().setResponseCode(200))
        }

        // When
        block()

        // Then
        for (url in urls) {
            val requestUrl = mockServer.takeRequest().requestUrl?.toString()
            assertEquals(url, requestUrl)
        }
    }
}
