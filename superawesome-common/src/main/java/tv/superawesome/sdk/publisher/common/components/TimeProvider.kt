package tv.superawesome.sdk.publisher.common.components

interface TimeProviderType {
    /**
     * Returns the current time in milliseconds.
     */
    fun millis(): Long
}

class TimeProvider : TimeProviderType {
    override fun millis(): Long = System.currentTimeMillis()
}