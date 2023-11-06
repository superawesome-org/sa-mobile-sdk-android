package tv.superawesome.sdk.publisher.ui.common

import android.os.Handler
import android.os.Looper
import android.view.View
import java.lang.ref.WeakReference

/**
 * Single shot viewable detector, once it detects the view visibility it will cancel itself.
 */
class SingleShotViewableDetector : ViewableDetector {
    private var viewableCounter = 0
    private var runnable: Runnable? = null
    private var handler = Handler(Looper.getMainLooper())
    private var ticks = 0

    override fun start(view: View, targetTickCount: Int, isVisible: () -> Unit) {
        val weak = WeakReference(view)
        viewableCounter = 0
        ticks = 0
        runnable = Runnable {
            ticks++
            weak.get()?.let { weakView ->
                if (isViewVisible(weakView)) {
                    viewableCounter += 1
                }

                if (viewableCounter >= targetTickCount) {
                    isVisible()
                    cancel()
                } else {
                    if (ticks < TICK_LIMIT) {
                        schedule()
                    }
                }
            }
        }

        schedule()
    }

    private fun schedule() {
        runnable?.let { handler.postDelayed(it, ViewableDetector.DELAY_MILLIS) }
    }

    override fun cancel() {
        runnable?.let { handler.removeCallbacks(it) }
        runnable = null
    }

    companion object {
        private const val TICK_LIMIT = 5
    }
}
