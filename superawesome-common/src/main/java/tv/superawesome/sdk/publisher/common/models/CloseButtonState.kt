package tv.superawesome.sdk.publisher.common.models

/** Represents the close button states */
enum class CloseButtonState(val value: Int) {
    /** Close button becomes visible after a delay */
    VisibleWithDelay(0),

    /** Close button becomes visible immediately */
    VisibleImmediately(1),

    /** Close button is hidden until the ad ends */
    Hidden(2);

    fun isVisible(): Boolean =
        this == VisibleWithDelay || this == VisibleImmediately

    companion object {
        @JvmStatic
        fun fromInt(value: Int): CloseButtonState =
            values().firstOrNull { it.value == value } ?: Hidden
    }
}
