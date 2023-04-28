package tv.superawesome.sdk.publisher.common.models

enum class CloseButtonState(val value: Int) {
    /*

     */
    VisibleWithDelay(0),
    VisibleImmediately(1),
    Hidden(2);

    fun isVisible(): Boolean =
        this == VisibleWithDelay || this == VisibleImmediately

    companion object {
        fun fromInt(value: Int): CloseButtonState =
            values().firstOrNull { it.value == value } ?: Hidden
    }
}