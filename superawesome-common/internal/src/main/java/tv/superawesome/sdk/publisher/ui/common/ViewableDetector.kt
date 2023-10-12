package tv.superawesome.sdk.publisher.ui.common

import android.content.res.Resources
import android.graphics.Rect
import android.view.View

/**
 * Detector that detects the visibility of a view.
 */
interface ViewableDetector {
    /**
     * Starts the detector for a given [view].
     *
     * @param view view being detected.
     * @param targetTickCount how many ticks the view must be visible before notifying. Default 1.
     * @param isVisible callback to notify of view visibility.
     */
    fun start(view: View, targetTickCount: Int = 1, isVisible: () -> Unit)

    /**
     * Cancels the detector.
     */
    fun cancel()

    /**
     * Obtain current view visibility status.
     *
     * @param view view being tested.
     * @return `true` if view is currently visible, `false` otherwise.
     */
    fun isViewVisible(view: View): Boolean {
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

    companion object {
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
        internal const val DELAY_MILLIS: Long = 1000
    }
}
