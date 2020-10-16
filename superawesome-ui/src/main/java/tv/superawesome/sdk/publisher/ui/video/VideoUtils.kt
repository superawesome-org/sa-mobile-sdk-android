package tv.superawesome.sdk.publisher.ui.video

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.DisplayMetrics
import android.widget.RelativeLayout
import java.io.File

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

    /**
     * Method that gets the video view proper layout params so it maintains aspect ratio
     *
     * @param sourceW   the video width
     * @param sourceH   the video height
     * @param boundingW the container width
     * @param boundingH the container height
     * @return          the FrameLayout.LayoutParams needed by the video
     */
    fun getVideoViewLayoutParams(sourceW: Float, sourceH: Float, boundingW: Float, boundingH: Float): RelativeLayout.LayoutParams {
        val sourceRatio = sourceW / sourceH
        val boundingRatio = boundingW / boundingH
        val X: Float
        val Y: Float
        val W: Float
        val H: Float
        if (sourceRatio > boundingRatio) {
            W = boundingW
            H = W / sourceRatio
            X = 0.0f
            Y = (boundingH - H) / 2.0f
        } else {
            H = boundingH
            W = sourceRatio * H
            Y = 0.0f
            X = (boundingW - W) / 2.0f
        }
        val returnParams = RelativeLayout.LayoutParams(W.toInt(), H.toInt())
        returnParams.setMargins(X.toInt(), Y.toInt(), 0, 0)
        return returnParams
    }

    @Throws(Exception::class)
    fun getUriFromFile(context: Context?, path: String): Uri {
        return if (context == null) {
            throw Exception("Fragment not prepared yet! Await the 'Video_Prepared' event in order to play.")
        } else {
            val file = File(context.filesDir, path)
            if (file.exists()) {
                val videoURL = file.toString()
                Uri.parse(videoURL)
            } else {
                throw Exception("File $path does not exist on disk. Will not play!")
            }
        }
    }
}