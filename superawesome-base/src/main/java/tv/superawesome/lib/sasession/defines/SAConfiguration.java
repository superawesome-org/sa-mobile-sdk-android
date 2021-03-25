/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.sasession.defines;

/**
 * This enum holds the two possible ad server configurations the SDK should respond to:
 * - PRODUCTION
 * - STAGING
 */
public enum SAConfiguration {
    DEV(-1),
    PRODUCTION(0),
    STAGING(1);

    private final int value;

    /**
     * Constructor needed for init from value
     *
     * @param value an integer value
     */
    SAConfiguration(int value) {
        this.value = value;
    }

    /**
     * Factory creation method from an integer value
     *
     * @param configuration integer value (should be either 0 or 1)
     * @return              a new SAConfiguration enum
     */
    public static SAConfiguration fromValue (int configuration) {
        switch (configuration) {
            case 0:
                return PRODUCTION;
            case 1:
                return STAGING;
            default:
                return DEV;
        }
    }

    /**
     * Get the current value (as integer); equivalent with myconfig.ordinal ()
     *
     * @return the associated int value of the enum variable
     */
    public int getValue() {
        return value;
    }
}
