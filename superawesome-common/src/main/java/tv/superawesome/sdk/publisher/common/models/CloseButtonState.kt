package tv.superawesome.sdk.publisher.common.models

/**
 * Represents the close button states.
 *
 * @property value integer value representing the state.
 */
enum class CloseButtonState(val value: Int) {
    /** Close button becomes visible after a delay. */
    VisibleWithDelay(0),

    /** Close button becomes visible immediately. */
    VisibleImmediately(1),

    /** Close button is hidden until the ad ends. */
    Hidden(2);

    /**
     * Whether the button is visible or not.
     */
    fun isVisible(): Boolean =
        this == VisibleWithDelay || this == VisibleImmediately

    companion object {

        /**
         * Finds the [CloseButtonState] from a given integer [value] or returns [Hidden]
         * if not found.
         */
        @JvmStatic
        fun fromInt(value: Int): CloseButtonState =
            entries.firstOrNull { it.value == value } ?: Hidden
    }
}
