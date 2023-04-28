package tv.superawesome.sdk.publisher.common.components

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

internal class DateProviderTest {
    private val calendar = Calendar.getInstance()
    private val dateProvider = DateProvider(calendar)

    private val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)

    @Test
    fun test_nowAsMonthYear() {
        // Given
        calendar.time = formatter.parse("01-02-2023")!!

        // When
        val result = dateProvider.nowAsMonthYear()

        // Then
        assertEquals("022023", result)
    }
}