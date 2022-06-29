package tv.superawesome.sdk.publisher.common.models

enum class Orientation {
    Any, Portrait, Landscape;

    companion object {
        private val values = values()
        fun fromValue(value: Int) = values.firstOrNull { it.ordinal == value }
    }
}