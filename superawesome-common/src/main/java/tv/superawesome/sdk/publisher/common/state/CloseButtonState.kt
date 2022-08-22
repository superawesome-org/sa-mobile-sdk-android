package tv.superawesome.sdk.publisher.common.state

enum class CloseButtonState(val value: Int) {
    Hidden(0), VisibleWithDelay(1), VisibleImmediately(2);

    fun isVisible(): Boolean =
        this == VisibleWithDelay || this == VisibleImmediately

    companion object {
        fun fromInt(value: Int): CloseButtonState =
            values().firstOrNull { it.value == value } ?: Hidden
    }
}