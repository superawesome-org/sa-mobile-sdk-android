package tv.superawesome.sdk.publisher.network.datasources

import android.content.Context
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import tv.superawesome.sdk.publisher.extensions.toMD5
import tv.superawesome.sdk.publisher.testutil.TestLogger
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class OkHttpNetworkDataSourceTest : MockServerTest() {

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val file = File.createTempFile("file", null).parentFile

    val sut = OkHttpNetworkDataSource(client, file, TestLogger())

    @Test
    fun `when calling getData, it should return the response body`() = runTest {
        // Given
        mockServer.enqueue(MockResponse().setBody("1234"))

        // When
        val result = sut.getData(mockServer.url("/").toString())

        // Then
        assertTrue(result.isSuccess)
        assertEquals("1234", result.getOrNull())
    }

    @Test
    fun `when calling getData, if response isn't successful, return failure`() = runTest {
        // Given
        mockServer.enqueue(MockResponse().setResponseCode(404))

        // When
        val result = sut.getData(mockServer.url("/").toString())

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `when downloading file, it a file must be written and it should return the file path`() = runTest {
        // Given
        mockServer.enqueue(MockResponse().setBody("1234"))
        val url = mockServer.url("/file.txt").toString()

        // When
        val result = sut.downloadFile(url)

        // Then
        assertTrue(result.isSuccess)
        if (file != null) {
            assertEquals("${file.absolutePath}/${url.toMD5()}.txt", result.getOrNull())
        } else {
            fail("Temp file is null")
        }
        assertEquals("1234", File(result.getOrThrow()).readText())
    }

    @Test
    fun `when downloading a file, given a network failure, then it should fail`() = runTest {
        // Given
        mockServer.enqueue(MockResponse().setResponseCode(404))
        val url = mockServer.url("/file.txt").toString()

        // When
        val result = sut.downloadFile(url)

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `when downloading a file, given a file exception, then it should fail`() = runTest {
        // Given
        mockServer.enqueue(MockResponse().setBody("1234"))
        val url = mockServer.url("/file").toString()
        val file = File(".")

        val sut = OkHttpNetworkDataSource(client, file, TestLogger())
        // When
        val result = sut.downloadFile(url)

        // Then
        assertTrue(result.isFailure)
    }
}
