package tv.superawesome.sdk.publisher.components

import java.text.SimpleDateFormat
import java.util.*

interface DateProviderType {
    /** Returns the MMyyyy of the current date. */
    fun nowAsMonthYear(): String
}

class DateProvider(private val calendar: Calendar) : DateProviderType {
    override fun nowAsMonthYear(): String = MonthYearFormat.format(calendar.time)
}

private val MonthYearFormat = SimpleDateFormat("MMyyyy", Locale.UK)
