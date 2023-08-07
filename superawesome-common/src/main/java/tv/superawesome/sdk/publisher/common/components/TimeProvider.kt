package tv.superawesome.sdk.publisher.common.components

import java.util.*

internal interface TimeProviderType {
    /**
     * Returns the current time in milliseconds.
     */
    fun millis(): Long
}

internal class TimeProvider : TimeProviderType {
    override fun millis(): Long = Calendar.getInstance().timeInMillis
}
