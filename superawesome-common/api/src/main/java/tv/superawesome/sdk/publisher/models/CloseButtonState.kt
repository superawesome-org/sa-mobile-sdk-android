@file:Suppress("MagicNumber")

package tv.superawesome.sdk.publisher.models

/**
 * Represents the close button states.
 */
public sealed class CloseButtonState {
    /** integer value representing the state. */
    public abstract val value: Int

    /** Close button becomes visible after a delay. */
    public data object VisibleWithDelay : CloseButtonState() {
        override val value: Int = 0
    }

    /** Close button becomes visible immediately. */
    public data object VisibleImmediately : CloseButtonState() {
        override val value: Int = 1
    }

    /** Close button is hidden until the ad ends. */
    public data object Hidden : CloseButtonState() {
        override val value: Int = 2
    }

    /** Close button shows after a set [delay], in ms. */
    public data class Custom(val delay: Long) : CloseButtonState() {
        override val value: Int = 3
    }

    /**
     * Whether the button is visible or not.
     */
    public fun isVisible(): Boolean =
        this == VisibleWithDelay || this == VisibleImmediately

    public companion object {

        /**
         * Finds the [CloseButtonState] from a given integer [value] or returns [Hidden]
         * if not found.
         */
        @JvmStatic
        public fun fromInt(value: Int, delay: Double): CloseButtonState =
            when (value) {
                0 -> VisibleWithDelay
                1 -> VisibleImmediately
                2 -> Hidden
                3 -> Custom(delay.toLong() * 1000)
                else -> Hidden
            }
    }
}
