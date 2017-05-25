package tv.superawesome.plugins.admob;

import android.os.Bundle;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.views.SAOrientation;

public class SAAdMobExtras {

    static final String kKEY_TEST           = "SA_TEST_MODE";
    static final String kKEY_TRANSPARENT    = "SA_TRANSPARENT";
    static final String kKEY_ORIENTATION    = "SA_ORIENTATION";
    static final String kKEY_CONFIGURATION  = "SA_CONFIGURATION";
    static final String kKEY_PARENTAL_GATE  = "SA_PG";
    static final String kKEY_BACK_BUTTON    = "SA_BACK_BUTTON";
    static final String kKEY_CLOSE_BUTTON   = "SA_CLOSE_BUTTON";
    static final String kKEY_CLOSE_AT_END   = "SA_CLOSE_AT_END";
    static final String kKEY_SMALL_CLICK    = "SA_SMALL_CLICK";

    private boolean transparent             = SuperAwesome.getInstance().defaultBgColor();
    private boolean testMode                = SuperAwesome.getInstance().defaultTestMode();
    private SAOrientation orientation       = SuperAwesome.getInstance().defaultOrientation();
    private SAConfiguration configuration   = SuperAwesome.getInstance().defaultConfiguration();
    private boolean parentalGate            = SuperAwesome.getInstance().defaultParentalGate();
    private boolean backButton              = SuperAwesome.getInstance().defaultBackButton();
    private boolean closeButton             = SuperAwesome.getInstance().defaultCloseButton();
    private boolean closeAtEnd              = SuperAwesome.getInstance().defaultCloseAtEnd();
    private boolean smallClick              = SuperAwesome.getInstance().defaultSmallClick();

    private SAAdMobExtras() {
        //
    }

    public static SAAdMobExtras extras () {
        return new SAAdMobExtras();
    }

    public SAAdMobExtras setTransparent(boolean value) {
        transparent = value;
        return this;
    }

    public SAAdMobExtras setTestMode(boolean value) {
        testMode = value;
        return this;
    }

    public SAAdMobExtras setOrientation(SAOrientation value) {
        orientation = value;
        return this;
    }

    public SAAdMobExtras setConfiguration(SAConfiguration value) {
        configuration = value;
        return this;
    }

    public SAAdMobExtras setParentalGate(boolean value) {
        parentalGate = value;
        return this;
    }

    public SAAdMobExtras setBackButton(boolean value) {
        backButton = value;
        return this;
    }

    public SAAdMobExtras setCloseButton(boolean value) {
        closeButton = value;
        return this;
    }

    public SAAdMobExtras setCloseAtEnd(boolean value) {
        closeAtEnd = value;
        return this;
    }

    public SAAdMobExtras setSmallClick(boolean value) {
        smallClick = value;
        return this;
    }

    public Bundle build () {
        Bundle bundle = new Bundle();
        bundle.putBoolean(kKEY_TEST, testMode);
        bundle.putBoolean(kKEY_TRANSPARENT, transparent);
        bundle.putInt(kKEY_ORIENTATION, orientation.ordinal());
        bundle.putInt(kKEY_CONFIGURATION, configuration.ordinal());
        bundle.putBoolean(kKEY_PARENTAL_GATE, parentalGate);
        bundle.putBoolean(kKEY_BACK_BUTTON, backButton);
        bundle.putBoolean(kKEY_CLOSE_BUTTON, closeButton);
        bundle.putBoolean(kKEY_CLOSE_AT_END, closeAtEnd);
        bundle.putBoolean(kKEY_SMALL_CLICK, smallClick);
        return bundle;
    }

}
