package tv.superawesome.plugins.publisher.admob;

import android.os.Bundle;

import tv.superawesome.sdk.publisher.common.models.AdRequest;
import tv.superawesome.sdk.publisher.common.models.Constants;
import tv.superawesome.sdk.publisher.common.models.Orientation;

public class SAAdMobExtras {
    static final String kKEY_TEST = "SA_TEST_MODE";
    static final String kKEY_TRANSPARENT = "SA_TRANSPARENT";
    static final String kKEY_ORIENTATION = "SA_ORIENTATION";
    static final String kKEY_PARENTAL_GATE = "SA_PG";
    static final String kKEY_BUMPER_PAGE = "SA_BUMPER";
    static final String kKEY_BACK_BUTTON = "SA_BACK_BUTTON";
    static final String kKEY_CLOSE_BUTTON = "SA_CLOSE_BUTTON";
    static final String kKEY_CLOSE_AT_END = "SA_CLOSE_AT_END";
    static final String kKEY_SMALL_CLICK = "SA_SMALL_CLICK";
    static final String kKEY_PLAYBACK_MODE = "SA_PLAYBACK_MODE";
    static final String PARAMETER = "parameter";

    private boolean transparent = Constants.defaultBackgroundColorEnabled;
    private boolean testMode = Constants.defaultTestMode;
    private Orientation orientation = Constants.INSTANCE.getDefaultOrientation();
    private AdRequest.StartDelay playback = Constants.INSTANCE.getDefaultStartDelay();
    private boolean parentalGate = Constants.defaultParentalGate;
    private boolean bumperPage = Constants.defaultBumperPage;
    private boolean backButton = Constants.defaultBackButtonEnabled;
    private boolean closeButton = false;
    private boolean closeAtEnd = Constants.defaultCloseAtEnd;
    private boolean smallClick = Constants.defaultSmallClick;

    private SAAdMobExtras() {
        //
    }

    public static SAAdMobExtras extras() {
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

    public SAAdMobExtras setOrientation(Orientation value) {
        orientation = value;
        return this;
    }

    public SAAdMobExtras setParentalGate(boolean value) {
        parentalGate = value;
        return this;
    }

    public SAAdMobExtras setBumperPage(boolean value) {
        bumperPage = value;
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

    public SAAdMobExtras setPlaybackMode(AdRequest.StartDelay mode) {
        playback = mode;
        return this;
    }

    public Bundle build() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(kKEY_TEST, testMode);
        bundle.putBoolean(kKEY_TRANSPARENT, transparent);
        bundle.putInt(kKEY_ORIENTATION, orientation.ordinal());
        bundle.putInt(kKEY_PLAYBACK_MODE, playback.ordinal());
        bundle.putBoolean(kKEY_PARENTAL_GATE, parentalGate);
        bundle.putBoolean(kKEY_BUMPER_PAGE, bumperPage);
        bundle.putBoolean(kKEY_BACK_BUTTON, backButton);
        bundle.putBoolean(kKEY_CLOSE_BUTTON, closeButton);
        bundle.putBoolean(kKEY_CLOSE_AT_END, closeAtEnd);
        bundle.putBoolean(kKEY_SMALL_CLICK, smallClick);
        return bundle;
    }

}
