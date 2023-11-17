package tv.superawesome.sdk.publisher.components

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import tv.superawesome.sdk.publisher.network.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.testutil.InMemoryPreferences
import tv.superawesome.sdk.publisher.testutil.TestLogger
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class VideoCacheTest {

    private val timeProvider = mockk<TimeProviderType> {
        every { millis() } returns 123L
    }
    private val preferences = InMemoryPreferences()
    private val logger = TestLogger()
    private val remoteDataSource = mockk<NetworkDataSourceType> {
        coEvery { downloadFile("success") } returns Result.success("path")
        coEvery { downloadFile("failure") } returns Result.failure(Exception())
    }

    private val sut = VideoCacheImpl(
        preferences,
        remoteDataSource,
        logger,
        timeProvider,
    )

    @After
    fun tearDown() {
        preferences.edit().clear()
    }

    @Test
    fun `test successfully obtaining file path, it is returned`() = runTest {
        // Act
        val path = sut.get("success")

        // Assert
        assertEquals("path", path)
    }

    @Test
    fun `test if file is cached, network isn't hit, path is returned`() = runTest {
        // Arrange
        preferences.edit().putString("failure", "{\"path\":\"failure\",\"timestamp\":123}").apply()

        // Act
        val path = sut.get("failure")

        // Assert
        assertEquals("failure", path)
    }

    @Test
    fun `test file path is being cached`() = runTest {
        // Act
        sut.get("success")

        // Assert
        val cached = preferences.getString("success", null)
        assertEquals("{\"path\":\"path\",\"timestamp\":123}", cached)
    }

    @Test
    fun `test path is not cached if policy is NoCaching`() = runTest {
        // Arrange
        val sut = VideoCacheImpl(preferences, remoteDataSource, logger, timeProvider, VideoCachePolicy.NoCaching)

        // Act
        sut.get("success")

        // Assert
        val cached = preferences.getString("success", null)
        assertNull(cached)
    }

    @Test
    fun `test videos are removed by cleanup if they expire`() = runTest {
        // Arrange
        val sut = VideoCacheImpl(preferences, remoteDataSource, logger, timeProvider, VideoCachePolicy.MaxAge(1000))
        preferences.edit().putString("success", "{\"path\":\"success\",\"timestamp\":123}").apply()
        val file = File("success").apply { createNewFile() }
        every { timeProvider.millis() } returns 1500

        // Act
        sut.cleanUp()

        // Assert
        val cached = preferences.getString("success", null)
        assertFalse(file.exists())
        assertNull(cached)
    }

    @Test
    fun `test videos are not removed by cleanup if they haven't expired`() = runTest {
        // Arrange
        val sut = VideoCacheImpl(preferences, remoteDataSource, logger, timeProvider, VideoCachePolicy.MaxAge(1000))
        preferences.edit()
            .putString("expired", "{\"path\":\"success_1\",\"timestamp\":123}")
            .putString("not_expired", "{\"path\":\"success_2\",\"timestamp\":1234}")
            .apply()
        val expired = File("success_1").apply { createNewFile() }
        val notExpired = File("success_2").apply { createNewFile() }

        every { timeProvider.millis() } returns 1500

        // Act
        sut.cleanUp()

        // Assert
        val cached = preferences.getString("not_expired", null)
        assertFalse(expired.exists())
        assertTrue(notExpired.exists())
        assertNotNull(cached)
    }
}
