package tv.superawesome.lib.sasession.defines;

/**
 * Created by gabriel.coman on 03/05/2018.
 */

public enum SARTBPlaybackMethod {
    WITH_SOUND_ON_SCREEN(5);

    private final int value;

    SARTBPlaybackMethod(int value) {
        this.value = value;
    }

    public static SARTBPlaybackMethod fromValue (int skip) {
        return WITH_SOUND_ON_SCREEN;
    }

    public int getValue() {
        return value;
    }
}
