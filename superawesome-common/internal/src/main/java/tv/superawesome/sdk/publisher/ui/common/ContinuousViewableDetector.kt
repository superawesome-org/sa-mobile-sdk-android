package tv.superawesome.sdk.publisher.ui.common

import android.os.Handler
import android.os.Looper
import android.view.View
import java.lang.ref.WeakReference

class ContinuousViewableDetector(
    private val delay: Long = ViewableDetector.DELAY_MILLIS,
) : ViewableDetector {

    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null
    private var count = 0

    override fun start(view: View, targetTickCount: Int, isVisible: () -> Unit) {
        val weakRef = WeakReference(view)
        count = 0

        runnable = Runnable {
            val viewRef = weakRef.get()
            if (viewRef != null) {
                if (isViewVisible(viewRef)) {
                    count++
                    if (count >= targetTickCount) {
                        isVisible()
                    }
                } else {
                    count = 0
                }
                schedule()
            } else {
                cancel()
            }
        }

        schedule()
    }

    override fun cancel() {
        runnable?.let { handler.removeCallbacks(it) }
    }

    private fun schedule() {
        runnable?.let { handler.postDelayed(it, delay) }
    }
}
