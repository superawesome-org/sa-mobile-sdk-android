package tv.superawesome.sdk.publisher.common.models

class PerformanceTimer {
    var startTime = 0L
        private set

    fun start(startTime: Long) {
        this.startTime = startTime
    }

    fun delta(currentTime: Long): Long {
        val delta = currentTime - startTime
        return if (delta > 0L) delta else 0L
    }
}