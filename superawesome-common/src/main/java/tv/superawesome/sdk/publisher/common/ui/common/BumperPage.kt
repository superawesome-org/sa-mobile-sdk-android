package tv.superawesome.sdk.publisher.common.ui.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.VoidBlock

class BumperPage {
    private var dialog: BumperPageDialog? = null
    private var handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null
    var onFinish: VoidBlock? = null

    fun show(context: Context) {
        dialog?.dismiss()
        dialog = BumperPageDialog(context)
        dialog?.show()
        setupTimer()
    }

    fun stop() {
        dialog?.dismiss()
        dialog = null
        runnable?.let { handler.removeCallbacks(it) }
        runnable = null
    }

    private fun setupTimer() {
        val countdown = intArrayOf(Constants.defaultBumperPageShowTimeInSec)
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            if (countdown[0] <= 0) {
                onFinish?.invoke()
                stop()
            } else {
                countdown[0]--
                dialog?.updateTimeLeft(countdown[0])
                runnable?.let { handler.postDelayed(it, 1000) }
            }
        }
        runnable?.let { handler.postDelayed(it, 1000) }
    }

    companion object {
        var appName: String? = null
            private set
        var appIcon: Drawable? = null
            private set

        @JvmStatic
        fun overrideName(name: String?) {
            appName = name
        }

        @JvmStatic
        fun overrideLogo(drawable: Drawable?) {
            appIcon = drawable
        }
    }
}