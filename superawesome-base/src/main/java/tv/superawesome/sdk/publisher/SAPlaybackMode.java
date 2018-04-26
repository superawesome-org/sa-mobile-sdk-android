package tv.superawesome.sdk.publisher;

/**
 * Created by gabriel.coman on 26/04/2018.
 */

public enum SAPlaybackMode {
    POSTROLL(-2), // -2
    MIDROLL(-1), // -1
    PREROLL(0), // 0
    MIDROLL_WITH_DELAY(1); // > 0

    private int value;

    /**
     * Constructor needed to be able to assign an int value to each enum value
     *
     * @param value the int value
     */
    SAPlaybackMode(int value) {
        this.value = value;
    }

    /**
     * Static factory method that creates a new enum starting from an integer value
     *
     * @param value   the integer value
     * @return              an enum, based on the int value being passed
     */
    public static SAPlaybackMode fromValue (int value) {
        switch (value) {
            case -2: return POSTROLL;
            case -1: return MIDROLL;
            case 0: return PREROLL;
            default: {
                SAPlaybackMode playbackMode = MIDROLL_WITH_DELAY;
                playbackMode.value = value;
                return playbackMode;
            }
        }
    }

    /**
     * Method equivalent to myEnum.ordinal()
     *
     * @return int value associated with the enum
     */
    public int getValue() {
        return value;
    }
}
