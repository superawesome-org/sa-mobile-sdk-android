package tv.superawesome.sdk.publisher.common.models

/**
 * Measures the performance of a certain block of code.
 */
class PerformanceTimer {

    /**
     * Gets the timer's start time in ms.
     */
    var startTime = 0L
        private set

    /**
     * Starts the timer with the given [startTime] in ms.
     */
    fun start(startTime: Long) {
        this.startTime = startTime
    }

    /**
     * Returns the delta between the [startTime] and the [currentTime].
     */
    fun delta(currentTime: Long): Long {
        val delta = currentTime - startTime
        return if (delta > 0L) delta else 0L
    }
}
