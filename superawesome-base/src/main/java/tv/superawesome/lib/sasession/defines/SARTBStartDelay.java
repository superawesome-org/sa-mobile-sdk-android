package tv.superawesome.lib.sasession.defines;

/**
 * Created by gabriel.coman on 03/05/2018.
 */
public enum SARTBStartDelay {
    POST_ROLL(-2),
    GENERIC_MID_ROLL(-1),
    PRE_ROLL(0),
    MID_ROLL(1);

    private final int value;

    SARTBStartDelay(int value) {
        this.value = value;
    }

    public static SARTBStartDelay fromValue (int skip) {
        switch (skip) {
            case -2: return POST_ROLL;
            case -1: return GENERIC_MID_ROLL;
            case 0: return PRE_ROLL;
            default: return MID_ROLL;
        }
    }

    public int getValue() {
        return value;
    }
}
