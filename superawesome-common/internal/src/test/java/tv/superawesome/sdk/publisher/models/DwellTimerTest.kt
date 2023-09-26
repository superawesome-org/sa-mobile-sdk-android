package tv.superawesome.sdk.publisher.models

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DwellTimerTest {

    @Test
    fun `when starting, execute callback after delay`() = runTest {
        // Given
        val sut = DwellTimer(2000L, this)
        var value = 0

        // When
        sut.start {
            value = 1
        }
        advanceTimeBy(2200L)
        sut.stop()

        // Then
        assertEquals(1, value)
    }
}
