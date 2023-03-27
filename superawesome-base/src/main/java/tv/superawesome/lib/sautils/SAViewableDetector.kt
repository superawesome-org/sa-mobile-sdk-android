package tv.superawesome.lib.sautils

import android.content.res.Resources
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import java.lang.ref.WeakReference

interface SAViewableDetectorType {
    var isVisible: (() -> Unit)?
    fun start(view: View, targetTickCount: Int, hasBeenVisible: () -> Unit)
    fun cancel()
}

class SAViewableDetector : SAViewableDetectorType {
    override var isVisible: (() -> Unit)? = null
    private var viewableCounter = 0
    private var runnable: Runnable? = null
    private var handler = Handler(Looper.getMainLooper())

    override fun start(view: View, targetTickCount: Int, hasBeenVisible: () -> Unit) {
        Log.d("ViewDetector", "start")
        val weak = WeakReference(view)
        viewableCounter = 0
        runnable = Runnable {
            val weakView = weak.get() ?: return@Runnable
            if (isViewVisible(weakView)) {
                Log.d("ViewDetector", "isViewVisible true")
                viewableCounter += 1
                isVisible?.invoke()
            } else {
                Log.d("ViewDetector", "isViewVisible false")
            }

            if (viewableCounter >= targetTickCount) {
                Log.d("ViewDetector", "completed")
                hasBeenVisible()
                cancel()
            } else {
                Log.d("ViewDetector", "Tick: $viewableCounter")
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

/**
 * Number of ticks required for banner/interstitial to decide viewable status
 */
const val interstitialMaxTickCount = 1

/**
 * Number of ticks required for video to decide viewable status
 */
const val videoMaxTickCount = 2

/**
 * The delay between each tick to check viewable status
 */
private const val delayMillis: Long = 1000
