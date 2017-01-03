package tv.superawesome.sdk.views;

public enum SAOrientation {
    ANY(0),
    PORTRAIT(1),
    LANDSCAPE(2);

    private final int value;
    SAOrientation(int value) {
        this.value = value;
    }

    public static SAOrientation fromValue (int value) {
        if (value == 2) return LANDSCAPE;
        if (value == 1) return PORTRAIT;
        return ANY;
    }

    public int getValue() {
        return value;
    }
}
