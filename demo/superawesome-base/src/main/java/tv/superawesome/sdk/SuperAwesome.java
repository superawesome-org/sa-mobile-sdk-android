/**
 * @class: SuperAwesome.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk;

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
        return "5.2.9";
    }

    private String getSdk() {
        return "android";
    }

    public String getSDKVersion() {
        return getSdk() + "_" + getVersion();
    }
}