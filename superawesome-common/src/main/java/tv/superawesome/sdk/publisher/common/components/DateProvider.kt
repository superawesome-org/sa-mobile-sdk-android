package tv.superawesome.sdk.publisher.common.components

import java.text.SimpleDateFormat
import java.util.*

interface DateProviderType {
    /** Returns the MMyyyy of the current date */
    fun nowAsMonthYear(): String
}

class DateProvider : DateProviderType {
    override fun nowAsMonthYear(): String = MonthYearFormat.format(Calendar.getInstance().time)
}

private val MonthYearFormat = SimpleDateFormat("MMyyyy", Locale.UK)