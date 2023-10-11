package tv.superawesome.sdk.publisher.ui.common

import android.os.SystemClock
import android.view.View
import tv.superawesome.sdk.publisher.models.Constants

/**
 * Extension for view that creates a [View.OnClickListener] with throttling mechanism.
 */
fun View.clickWithThrottling(
    throttleTime: Long = Constants.defaultClickThresholdInMs,
    action: () -> Unit
) {
    setOnClickListener(object : View.OnClickListener {
        private var lastClickTime = 0L

        override fun onClick(v: View?) {
            val time = SystemClock.elapsedRealtime()
            if (time - lastClickTime < throttleTime) {
                return
            }

            action()
            lastClickTime = time
        }
    })
}