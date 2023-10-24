package tv.superawesome.sdk.publisher.ui.common

import android.os.SystemClock
import android.view.View
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.ui.video.AdVideoPlayerControllerView

/**
 * Extension for view that creates a [View.OnClickListener] with throttling mechanism.
 */
fun View.clickWithThrottling(
    throttleTime: Long = Constants.defaultClickThresholdInMs,
    action: (View?) -> Unit
) {
    setOnClickListener(object : View.OnClickListener {
        private var lastClickTime = 0L

        override fun onClick(v: View?) {
            val time = SystemClock.elapsedRealtime()
            if (time - lastClickTime < throttleTime) {
                return
            }

            action(v)
            lastClickTime = time
        }
    })
}

internal fun AdVideoPlayerControllerView.clickWithThrottling(
    throttleTime: Long = Constants.defaultClickThresholdInMs,
    action: (View?) -> Unit,
) {
    setClickListener(object: View.OnClickListener {
        private var lastClickTime = 0L

        override fun onClick(v: View?) {
            val time = SystemClock.elapsedRealtime()
            if (time - lastClickTime < throttleTime) {
                return
            }

            action(v)
            lastClickTime = time
        }
    })
}
