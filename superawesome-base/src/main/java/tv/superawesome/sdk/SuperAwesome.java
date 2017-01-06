package tv.superawesome.sdk;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.sdk.views.SAOrientation;

/**
 * This is a Singleton class through which SDK users setup their AwesomeAds instance
 */
public class SuperAwesome {

    // variables
    private static SuperAwesome instance = new SuperAwesome();

    // version & sdk private vars
    private String version = null;
    private String sdk = null;

    // constructors
    private SuperAwesome() {
        version = "5.3.9";
        sdk = "android";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static SuperAwesome getInstance() {
        return instance;
    }

    private String getVersion() {
        return version;
    }

    private String getSdk() {
        return sdk;
    }

    public String getSDKVersion() {
        return getSdk() + "_" + getVersion();
    }

    // default state vars

    public int defaultPlacementId () {
        return 0;
    }
    public boolean defaultTestMode () {
        return false;
    }
    public boolean defaultParentalGate () {
        return true;
    }
    public SAConfiguration defaultConfiguration () {
        return SAConfiguration.PRODUCTION;
    }
    public SAOrientation defaultOrientation () {
        return SAOrientation.ANY;
    }
    public boolean defaultCloseButton () {
        return false;
    }
    public boolean defaultSmallClick () {
        return false;
    }
    public boolean defaultCloseAtEnd () {
        return true;
    }
    public boolean defaultBgColor () {
        return false;
    }
    public boolean defaultBackButton () {
        return false;
    }

    // override sdk & version methods

    public void overrideVersion (String version) {
        this.version = version;
    }
    public void overrideSdk (String sdk) {
        this.sdk = sdk;
    }
}