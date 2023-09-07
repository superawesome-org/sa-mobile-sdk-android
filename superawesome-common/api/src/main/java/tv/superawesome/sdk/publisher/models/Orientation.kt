package tv.superawesome.sdk.publisher.models

/**
 * Screen orientation.
 */
public enum class Orientation {
    /** Any orientation. Portrait or Landscape. **/
    Any,
    /** Portrait only. */
    Portrait,
    /** Landscape only. */
    Landscape;

    public companion object {

        /**
         * Finds the [Orientation] from a given integer [value] or returns `null` if not found.
         */
        @JvmSynthetic
        public fun fromValue(value: Int): Orientation? =
            entries.firstOrNull { it.ordinal == value }
    }
}
