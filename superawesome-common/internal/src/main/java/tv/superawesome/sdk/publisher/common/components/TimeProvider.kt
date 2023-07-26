package tv.superawesome.sdk.publisher.common.components

import java.util.*

interface TimeProviderType {
    /**
     * Returns the current time in milliseconds.
     */
    fun millis(): Long
}

class TimeProvider : TimeProviderType {
    override fun millis(): Long = Calendar.getInstance().timeInMillis
}
