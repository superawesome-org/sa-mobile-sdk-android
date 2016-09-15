package tv.superawesome.sdk.views;

/**
 * Created by gabriel.coman on 15/09/16.
 */
public enum SAOrientation {
    ANY(0),
    PORTRAIT(1),
    LANDSCAPE(2);

    private final int value;
    private SAOrientation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
