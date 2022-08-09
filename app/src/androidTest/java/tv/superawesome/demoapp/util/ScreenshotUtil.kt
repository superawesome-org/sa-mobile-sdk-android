package tv.superawesome.demoapp.util

import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import androidx.test.runner.screenshot.Screenshot

object ScreenshotUtil {
    private fun capture(view: View): Bitmap? {
        val capture = Screenshot.capture(view)
        capture.format = Bitmap.CompressFormat.PNG
        return capture.bitmap
    }

    private fun capture(): Bitmap? {
        val capture = Screenshot.capture()
        capture.format = Bitmap.CompressFormat.PNG
        return capture.bitmap
    }

    fun captureColorInCenter(): Color? {
        val bitmap = capture() ?: return null
        val x = bitmap.width / 2
        val y = bitmap.height / 2
        if (x == 0 || y == 0) return null
        return bitmap.getColor(x, y)
    }

    fun captureColorInCenter(view: View): Color? {
        val bitmap = capture(view) ?: return null
        val x = bitmap.width / 2
        val y = bitmap.height / 2
        if (x == 0 || y == 0) return null
        return bitmap.getColor(x, y)
    }
}