package tv.superawesome.sdk.publisher.common.models

/**
 * Screen orientation.
 */
enum class Orientation {
    /** Any orientation. Portrait or Landscape. **/
    Any,
    /** Portrait only. */
    Portrait,
    /** Landscape only. */
    Landscape;

    companion object {

        /**
         * Finds the [Orientation] from a given integer [value] or returns `null` if not found.
         */
        @JvmStatic
        fun fromValue(value: Int) = entries.firstOrNull { it.ordinal == value }
    }
}
