package tv.superawesome.sdk.publisher.common.ui.video

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

internal object VideoUtils {
    /**
     * @return the current scale
     */
    @Suppress("MagicNumber")
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
