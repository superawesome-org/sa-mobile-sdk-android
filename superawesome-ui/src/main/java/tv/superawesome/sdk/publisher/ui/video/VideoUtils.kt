package tv.superawesome.sdk.publisher.ui.video

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

object VideoUtils {
    /**
     * @return the current scale
     */
    fun getScale(context: Context?): Float {
        val metrics = DisplayMetrics()
        return if (context is Activity) {
            val display = context.windowManager.defaultDisplay
            display.getMetrics(metrics)
            metrics.densityDpi.toFloat() / 160.0f
        } else {
            1.0f
        }
    }
}