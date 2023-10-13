package tv.superawesome.sdk.publisher.ui.common

import android.os.Handler
import android.os.Looper
import android.view.View
import tv.superawesome.sdk.publisher.components.Logger
import java.lang.ref.WeakReference

/**
 * Single shot viewable detector, once it detects the view visibility it will cancel itself.
 */
class SingleShotViewableDetector(private val logger: Logger) : ViewableDetector {
    private var viewableCounter = 0
    private var runnable: Runnable? = null
    private var handler = Handler(Looper.getMainLooper())

    override fun start(view: View, targetTickCount: Int, isVisible: () -> Unit) {
        logger.info("start")
        val weak = WeakReference(view)
        viewableCounter = 0
        runnable = Runnable {
            weak.get()?.let { weakView ->
                if (isViewVisible(weakView)) {
                    logger.info("isViewVisible true")
                    viewableCounter += 1
                } else {
                    logger.info("isViewVisible false")
                }

                if (viewableCounter >= targetTickCount) {
                    logger.info("completed")
                    isVisible()
                    cancel()
                } else {
                    logger.info("Tick: $viewableCounter")
                    schedule()
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
}
