package tv.superawesome.sdk.publisher.common.components

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class DateProviderTest {
    private val dateProvider = DateProvider()

    private val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)

    @Test
    fun test_nowAsMonthYear() {
        // Given
        val calendar = Calendar.getInstance()
        calendar.time = formatter.parse("01-02-2023")!!

        // When
        val result = dateProvider.nowAsMonthYear()

        // Then
        assertEquals("022023", result)
    }
}