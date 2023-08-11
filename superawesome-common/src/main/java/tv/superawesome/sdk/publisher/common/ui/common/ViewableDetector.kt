package tv.superawesome.sdk.publisher.common.ui.common

import android.content.res.Resources
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.view.View
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.models.VoidBlock
import java.lang.ref.WeakReference

internal interface ViewableDetectorType {
    var isVisible: VoidBlock?
    fun start(view: View, targetTickCount: Int, hasBeenVisible: VoidBlock)
    fun cancel()
}

internal class ViewableDetector(private val logger: Logger) : ViewableDetectorType {
    override var isVisible: VoidBlock? = null
    private var viewableCounter = 0
    private var runnable: Runnable? = null
    private var handler = Handler(Looper.getMainLooper())

    override fun start(view: View, targetTickCount: Int, hasBeenVisible: VoidBlock) {
        logger.info("start")
        val weak = WeakReference(view)
        viewableCounter = 0
        runnable = Runnable {
            weak.get()?.let { weakView ->
                if (isViewVisible(weakView)) {
                    logger.info("isViewVisible true")
                    viewableCounter += 1
                    isVisible?.invoke()
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
        runnable?.let { handler.postDelayed(it, DELAY_MILLIS) }
    }

    override fun cancel() {
        runnable?.let { handler.removeCallbacks(it) }
        runnable = null
    }
}

/**
 * Number of ticks required for banner/interstitial to decide viewable status.
 */
const val INTERSTITIAL_MAX_TICK_COUNT = 1

/**
 * Number of ticks required for video to decide viewable status.
 */
const val VIDEO_MAX_TICK_COUNT = 2

/**
 * The delay between each tick to check viewable status.
 */
private const val DELAY_MILLIS: Long = 1000
