package tv.superawesome.sdk.publisher.common.ui.common

import android.content.res.Resources
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.view.View
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.models.VoidBlock
import java.lang.ref.WeakReference

interface ViewableDetectorType {
    fun start(view: View, hasBeenVisible: VoidBlock)
    fun cancel()
}

class ViewableDetector(private val logger: Logger) : ViewableDetectorType {
    private var viewableCounter = 0
    private val targetTickCount = 3
    private val delayMillis: Long = 1000
    private var runnable: Runnable? = null
    private var handler = Handler(Looper.getMainLooper())

    override fun start(view: View, hasBeenVisible: VoidBlock) {
        logger.info("start")
        val weak = WeakReference(view)
        runnable = Runnable {
            val weakView = weak.get() ?: return@Runnable
            if (isViewVisible(weakView)) {
                logger.info("isViewVisible true")
                viewableCounter += 1
            } else {
                logger.info("isViewVisible false")
            }

            if (viewableCounter >= targetTickCount) {
                logger.info("completed")
                hasBeenVisible()
                cancel()
            } else {
                logger.info("Tick: $viewableCounter")
                schedule()
            }
        }

        schedule()
    }

    private fun isViewVisible(view: View): Boolean {
        if (!view.isShown) {
            return false
        }

        val actualPosition = Rect()
        view.getGlobalVisibleRect(actualPosition)
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        val screen = Rect(0, 0, screenWidth, screenHeight)

        return actualPosition.intersect(screen)
    }

    private fun schedule() {
        runnable?.let { handler.postDelayed(it, delayMillis) }
    }

    override fun cancel() {
        runnable?.let { handler.removeCallbacks(it) }
        runnable = null
    }
}
