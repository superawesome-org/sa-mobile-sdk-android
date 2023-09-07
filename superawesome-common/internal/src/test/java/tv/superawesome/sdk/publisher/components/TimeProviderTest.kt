package tv.superawesome.sdk.publisher.components

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

internal class TimeProviderTest {
    private val timeProvider = TimeProvider()

    @Test
    fun test_millis() {
        // Given
        val calendar = mockk<Calendar>()
        mockkStatic(Calendar::class)
        every { Calendar.getInstance() } returns calendar
        every { calendar.timeInMillis } returns 12345

        // When
        val millis = timeProvider.millis()

        // Then
        assertEquals(millis, 12345)
    }
}