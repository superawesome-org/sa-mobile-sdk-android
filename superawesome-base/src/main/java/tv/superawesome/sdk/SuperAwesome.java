/**
 * @class: SuperAwesome.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.sdk.views.SAOrientation;

/**
 * This is a Singleton class through which SDK users setup their AwesomeAds instance
 */
public class SuperAwesome {

    // variables
    private static SuperAwesome instance = new SuperAwesome();

    // constructors
    private SuperAwesome() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static SuperAwesome getInstance() {
        return instance;
    }

    private String getVersion() {
        return "5.3.9";
    }

    private String getSdk() {
        return "android";
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
}