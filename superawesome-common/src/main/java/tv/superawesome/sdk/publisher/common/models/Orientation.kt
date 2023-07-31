package tv.superawesome.sdk.publisher.common.models

import androidx.annotation.NonNull

enum class Orientation {
    Any, Portrait, Landscape;

    companion object {
        private val values = values()

        @JvmStatic
        fun fromValue(@NonNull value: Int) = values.firstOrNull { it.ordinal == value }
    }
}