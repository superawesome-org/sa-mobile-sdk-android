package tv.superawesome.sdk.publisher.common.ui.common

import android.content.Context
import android.os.Handler
import android.os.Looper
import tv.superawesome.sdk.publisher.common.models.Constants

class DefaultBumperPage : BumperPage {
    private var dialog: BumperPageDialog? = null
    private var handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    /** Callback function to be called when it's closed. */
    override var onFinish: (() -> Unit)? = null

    /**
     * Shows the bumper page.
     *
     * @param context the parent activity context.
     */
    override fun show(context: Context) {
        dialog?.dismiss()
        dialog = BumperPageDialog(context)
        dialog?.show()
        setupTimer()
    }

    /**
     * Dismisses the bumper page.
     */
    override fun stop() {
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
                runnable?.let { handler.postDelayed(it, DELAY) }
            }
        }
        runnable?.let { handler.postDelayed(it, DELAY) }
    }

    private companion object {
        const val DELAY = 1_000L
    }
}
