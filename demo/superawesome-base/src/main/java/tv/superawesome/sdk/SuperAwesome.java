/**
 * @class: SuperAwesome.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk;

import android.content.Context;

import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.sautils.SAApplication;
import tv.superawesome.sdk.capper.SACapper;
import tv.superawesome.sdk.capper.SACapperInterface;
import tv.superawesome.sdk.cpi.SACPI;

/**
 * This is a Singleton class through which SDK users setup their AwesomeAds instance
 */
public class SuperAwesome {

    // dau id
    private int dauid = 0;
    private SACapper capper = null;

    // variables
    private static SuperAwesome instance = new SuperAwesome();

    // constructors
    private SuperAwesome(){
        capper = new SACapper();
    }

    public void setApplicationContext(Context _appContext){
        SAApplication.setSAApplicationContext(_appContext);
        capper.enableCapping(_appContext, new SACapperInterface() {
            @Override
            public void didFindDAUId(int newDauId) {
                dauid = newDauId;
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static SuperAwesome getInstance(){
        return instance;
    }

    private String getVersion () {
        return "5.0.6";
    }

    private String getSdk() {
        return "android";
    }

    public String getSDKVersion() {
        return getSdk() + "_" + getVersion();
    }

    public int getDAUID() {
        return dauid;
    }
}
