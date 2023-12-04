package tv.superawesome.sdk.publisher.state

/**
 * Close button configuration.
 */
sealed class CloseButtonState {
    /**
     * Server-side value.
     */
    abstract val value: Int

    /**
     * Delay, for [Custom].
     */
    abstract val time: Long

    /**
     * Whether the close button should be visible.
     */
    fun isVisible(): Boolean =
        this == VisibleWithDelay || this == VisibleImmediately

    /**
     * Close button will be visible after a short delay. (Not controlled by [time]).
     */
    data object VisibleWithDelay : CloseButtonState() {
        override val value: Int = 0
        override val time: Long = 0L
    }

    /**
     * Close button will be visible immediately.
     */
    data object VisibleImmediately : CloseButtonState() {
        override val value: Int = 1
        override val time: Long = 0L
    }

    /**
     * Close button will be hidden and will not show.
     */
    data object Hidden : CloseButtonState() {
        override val value: Int = 2
        override val time: Long = 0L
    }

    /**
     * Close button will show after a certain amount of time.
     * @property time amount of time until the close button shows, in millis.
     */
    data class Custom(override val time: Long) : CloseButtonState() {
        @Suppress("MagicNumber")
        override val value: Int = 3
    }

    companion object {

        /**
         * Gets the [CloseButtonState] from the [value], [time] is used for [Custom].
         */
        @JvmStatic
        @Suppress("MagicNumber")
        fun fromInt(value: Int, time: Long): CloseButtonState =
            when (value) {
                0 -> VisibleWithDelay
                1 -> VisibleImmediately
                2 -> Hidden
                3 -> Custom(time)
                else -> Hidden
            }
    }
}
