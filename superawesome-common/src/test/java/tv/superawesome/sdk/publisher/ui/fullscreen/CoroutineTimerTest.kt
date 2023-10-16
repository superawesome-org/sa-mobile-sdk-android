package tv.superawesome.sdk.publisher.ui.fullscreen

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import tv.superawesome.sdk.publisher.components.CoroutineTimer
import tv.superawesome.sdk.publisher.components.TimeProviderType
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineTimerTest {

    private val timeProvider = mockk<TimeProviderType> {
        every { millis() } returns 0L
    }

    @Test
    fun `timer fires after 5s`() = runTest {
        // arrange
        var value = 0
        val timer = CoroutineTimer(5000L, timeProvider, this) {
            value++
        }

        // act
        timer.start()
        advanceTimeBy(5200)

        // assert
        assertEquals(1, value)
    }

    @Test
    fun `timer doesn't fire after being paused for longer than the timer`() = runTest {
        // arrange
        var value = 0
        val timer = CoroutineTimer(5000L, timeProvider, this) {
            value++
        }

        // act
        timer.start()
        advanceTimeBy(2000)
        timer.pause()
        advanceTimeBy(5000)

        // assert
        assertEquals(0, value)
    }

    @Test
    fun `timer doesn't fire after being stopped`() = runTest {
        // arrange
        var value = 0
        val timer = CoroutineTimer(5000L, timeProvider, this) {
            value++
        }

        // act
        timer.start()
        advanceTimeBy(2000)
        timer.stop()
        advanceTimeBy(5000)

        // assert
        assertEquals(0, value)
    }

    @Test
    fun `timer fires after being paused and resumed`() = runTest {
        // arrange
        every { timeProvider.millis() } returnsMany listOf(0L, 2000L, 7000L)
        var value = 0
        val timer = CoroutineTimer(5000L, timeProvider, this) {
            value++
        }

        // act
        timer.start()
        advanceTimeBy(2000)
        timer.pause()
        advanceTimeBy(5000)
        timer.resume()
        advanceTimeBy(3200)

        // assert
        assertEquals(1, value)
    }

    @Test
    fun `resuming timer doesn't do anything if hasn't been started`() = runTest {
        // arrange
        var value = 0
        val timer = CoroutineTimer(5000L, timeProvider, this) {
            value++
        }

        // act
        timer.resume()
        advanceTimeBy(8000)

        // assert
        assertEquals(0, value)
    }
}