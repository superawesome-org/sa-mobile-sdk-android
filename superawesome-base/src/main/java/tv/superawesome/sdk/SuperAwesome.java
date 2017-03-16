/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.sdk;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.sdk.views.SAOrientation;

/**
 * This is a Singleton class through which SDK users setup their AwesomeAds instance
 */
public class SuperAwesome {

    // Super Awesome instance variable that can be setup only once
    private static SuperAwesome instance = new SuperAwesome();

    // version & sdk private vars
    private String version = null;
    private String sdk = null;

    /**
     * Private constructor that is only called once
     */
    private SuperAwesome() {
        version = "5.5.3";
        sdk = "android";
    }

    /**
     * Singleton method to get the only existing instance
     *
     * @return an instance of the SuperAwesome class
     */
    public static SuperAwesome getInstance() {
        return instance;
    }

    /**
     * Getter for the current version
     *
     * @return string representing the current version
     */
    private String getVersion() {
        return version;
    }

    /**
     * Getter for the current SDK
     *
     * @return string representing the current SDK
     */
    private String getSdk() {
        return sdk;
    }

    /**
     * Getter for a string comprising of SDK & version bundled
     *
     * @return  a string
     */
    public String getSDKVersion() {
        return getSdk() + "_" + getVersion();
    }

    /**
     * Methods that return default values for each of the variables that can be used to
     * customize Video, Banner, Interstitial and AppWall ads
     *
     * @return whatever return value
     */
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

    /**
     * Method that overrides the current version string. It's used by the AIR & Unity SDKs
     *
     * @param version the new version
     */
    public void overrideVersion (String version) {
        this.version = version;
    }

    /**
     * Method that overrides the current sdk string. It's used by the AIR & Unity SDKs
     *
     * @param sdk the new sdk
     */
    public void overrideSdk (String sdk) {
        this.sdk = sdk;
    }
}