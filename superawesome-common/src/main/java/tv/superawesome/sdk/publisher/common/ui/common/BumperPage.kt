package tv.superawesome.sdk.publisher.common.ui.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.VoidBlock

/**
 * The Bumper is an optional UI dialog that informs the user that they are about to leave
 * a kid-safe place and proceed to an external website.
 *
 * In technical terms when a user clicks on an ad placement a bumper popup will be presented
 * for a duration of 3 seconds informing that the user will be redirected to a external source.
 *
 * SuperAwesome’s kid-safe review team will always configure the bumper when:
 *
 * - An ad links to a social media site, eg YouTube, Facebook, etc.
 * - An ad links to a retailer or online shop.
 */
class BumperPage {
    private var dialog: BumperPageDialog? = null
    private var handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    /** Callback function to be called when it's closed. */
    var onFinish: VoidBlock? = null

    /**
     * Shows the bumper page.
     *
     * @param context the parent activity context.
     */
    fun show(context: Context) {
        dialog?.dismiss()
        dialog = BumperPageDialog(context)
        dialog?.show()
        setupTimer()
    }

    /**
     * Dismisses the bumper page.
     */
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
                runnable?.let { handler.postDelayed(it, DELAY) }
            }
        }
        runnable?.let { handler.postDelayed(it, DELAY) }
    }

    companion object {
        private const val DELAY = 1_000L

        /** The overridden application name. */
        var appName: String? = null
            private set

        /** The overridden application icon. */
        var appIcon: Drawable? = null
            private set

        /**
         * Overrides the application name.
         */
        @JvmStatic
        fun overrideName(name: String?) {
            appName = name
        }

        /**
         * Overrides the application icon/logo.
         */
        @JvmStatic
        fun overrideLogo(drawable: Drawable?) {
            appIcon = drawable
        }
    }
}
