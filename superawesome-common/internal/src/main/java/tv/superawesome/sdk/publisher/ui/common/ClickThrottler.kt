package tv.superawesome.sdk.publisher.ui.common

import android.os.SystemClock
import android.util.Log
import tv.superawesome.sdk.publisher.models.Constants

class ClickThrottler(
    private val throttleTime: Long = Constants.defaultClickThresholdInMs,
) {
    private var lastTimeRecorded = 0L

    fun onClick(action: () -> Unit) {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastTimeRecorded < throttleTime) {
            return
        }

        action()
        lastTimeRecorded = currentTime
    }
}
