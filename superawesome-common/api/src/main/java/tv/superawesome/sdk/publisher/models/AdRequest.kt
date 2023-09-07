package tv.superawesome.sdk.publisher.models

/**
 * Defines a network request for an Ad.
 */
public interface AdRequest {
    /** Test mode. */
    public val test: Boolean
    /** Position. */
    public val pos: Int
    /** Can be skipped. */
    public val skip: Int
    /** Playback method. */
    public val playbackMethod: Int
    /** Start delay in seconds. */
    public val startDelay: Int
    /** ?. */
    public val install: Int
    /** Width in pixels. */
    public val w: Int
    /** Height in pixels. */
    public val h: Int
    /** Extra options. */
    public val options: Map<String, Any>?

    /**
     * Specify if the ad is in full screen or not.
     *
     * @property value flag value.
     */
    public enum class FullScreen(public val value: Int) {
        On(1), Off(0)
    }

    /**
     * Start delay cases.
     *
     * @property value flag value.
     */
    public enum class StartDelay(public val value: Int) {
        PostRoll(-2),
        GenericMidRoll(-1),
        PreRoll(0),
        MidRoll(1);

        public companion object {

            /**
             * Returns the [StartDelay] from a given integer [value] or null if not found.
             */
            @JvmStatic
            public fun fromValue(value: Int): StartDelay? =
                entries.firstOrNull { it.value == value }
        }
    }

    /**
     * Specify the position of the ad.
     *
     * @property value flag value.
     */
    @Suppress("MagicNumber")
    public enum class Position(public val value: Int) {
        AboveTheFold(1),
        BelowTheFold(3),
        FullScreen(7),
    }

    /**
     * Specify if the ad can be skipped.
     *
     * @property value value representing the skip state.
     */
    public enum class Skip(public val value: Int) {
        /**
         * Can't be skipped.
         */
        No(0),

        /**
         * Can be skipped.
         */
        Yes(1),
    }
}
