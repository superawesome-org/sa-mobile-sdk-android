package tv.superawesome.lib.sasession.defines;

/**
 * Created by gabriel.coman on 03/05/2018.
 */
public enum SARTBSkip {
    NO_SKIP(0),
    SKIP(1);

    private final int value;

    SARTBSkip(int value) {
        this.value = value;
    }

    public static SARTBSkip fromValue (int skip) {
        switch (skip) {
            case 1: return SKIP;
            default: return NO_SKIP;
        }
    }

    public int getValue() {
        return value;
    }
}
