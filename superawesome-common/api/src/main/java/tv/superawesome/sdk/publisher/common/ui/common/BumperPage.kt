package tv.superawesome.sdk.publisher.common.ui.common

import android.content.Context
import android.graphics.drawable.Drawable

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
public interface BumperPage {

    /** Callback function to be called when it's closed. */
    public var onFinish: (() -> Unit)?

    /**
     * Shows the bumper page.
     *
     * @param context the parent activity context.
     */
    public fun show(context: Context)

    /**
     * Dismisses the bumper page.
     */
    public fun stop()

    public companion object {

        /** The overridden application name. */
        public var appName: String? = null
            private set

        /** The overridden application icon. */
        public var appIcon: Drawable? = null
            private set

        /**
         * Overrides the application name.
         */
        @JvmStatic
        public fun overrideName(name: String?) {
            appName = name
        }

        /**
         * Overrides the application icon/logo.
         */
        @JvmStatic
        public fun overrideLogo(drawable: Drawable?) {
            appIcon = drawable
        }
    }
}
