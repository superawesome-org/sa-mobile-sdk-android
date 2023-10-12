package tv.superawesome.sdk.publisher.models

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DwellTimerTest {

    @Test
    fun `given 3 ticks needed, when ticking, trigger callback after 3 ticks`() = runTest {
        // Given
        val sut = DwellTimer(3)
        var value = 0
        val action: () -> Unit = {
            value++
        }

        // When
        sut.tick(action)
        sut.tick(action)
        sut.tick(action)

        // Then
        assertEquals(1, value)
    }

    @Test
    fun `given 3 ticks needed, when ticking, trigger callback once after 5 ticks`() = runTest {
        // Given
        val sut = DwellTimer(3)
        var value = 0
        val action: () -> Unit = {
            value++
        }

        // When
        sut.tick(action)
        sut.tick(action)
        sut.tick(action)
        sut.tick(action)
        sut.tick(action)

        // Then
        assertEquals(1, value)
    }

    @Test
    fun `given 3 ticks needed, when ticking, trigger callback twice after 6 ticks`() = runTest {
        // Given
        val sut = DwellTimer(3)
        var value = 0
        val action: () -> Unit = {
            value++
        }

        // When
        sut.tick(action)
        sut.tick(action)
        sut.tick(action)
        sut.tick(action)
        sut.tick(action)
        sut.tick(action)

        // Then
        assertEquals(2, value)
    }
}
