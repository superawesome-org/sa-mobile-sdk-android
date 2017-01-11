package tv.superawesome.sdk;

import android.content.Context;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.sdk.cpi.SACPI;
import tv.superawesome.sdk.cpi.SAInstallEventInterface;
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

    // CPI implementation
    private SACPI sacpi;

    // constructors
    private SuperAwesome() {
        version = "5.3.11";
        sdk = "android";
        sacpi = new SACPI();
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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // CPI Part
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void handleCPI (Context context, SAInstallEventInterface listener) {
        SASession session = new SASession(context);
        session.setConfigurationProduction();
        sacpi.sendInstallEvent(context, session, listener);
    }

    public void handleStagingCPI (Context context, SAInstallEventInterface listener) {
        SASession session = new SASession(context);
        session.setConfigurationStaging();
        sacpi.sendInstallEvent(context, session, listener);
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