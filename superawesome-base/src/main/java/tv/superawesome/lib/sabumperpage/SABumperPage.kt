package tv.superawesome.lib.sabumperpage

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper

class SABumperPage {
    private var dialog: BumperPageDialog? = null
    private var handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null
    var onFinish: (() -> Unit)? = null

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
        val countdown = intArrayOf(defaultBumperPageShowTimeInSec)
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
        internal var appName: String? = null
            private set
        internal var appIcon: Drawable? = null
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

internal const val defaultBumperPageShowTimeInSec = 3