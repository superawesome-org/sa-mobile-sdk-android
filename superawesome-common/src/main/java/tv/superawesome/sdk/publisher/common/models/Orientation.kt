package tv.superawesome.sdk.publisher.common.models

enum class Orientation(val value: Int) {
    Any(0), Portrait(1), Landscape(2);

    companion object {
        private val values = values()
        fun fromValue(value: Int) = values.firstOrNull { it.value == value }
    }
}