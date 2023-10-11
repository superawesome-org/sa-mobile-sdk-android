package tv.superawesome.sdk.publisher.ui.common

import android.os.SystemClock
import tv.superawesome.sdk.publisher.models.Constants

/**
 * Throttling mechanism for view clicking, used for callbacks instead of `onClickListeners`.
 */
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
