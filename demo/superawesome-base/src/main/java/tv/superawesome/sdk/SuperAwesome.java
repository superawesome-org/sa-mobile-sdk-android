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

/**
 * This is a Singleton class through which SDK users setup their AwesomeAds instance
 */
public class SuperAwesome {

    // dau id
    private int dauid = 0;

    // variables
    private static SuperAwesome instance = new SuperAwesome();

    // constructors
    private SuperAwesome(){

    }

    public void setApplicationContext(Context _appContext){
        SAApplication.setSAApplicationContext(_appContext);
        SACapper.enableCapping(_appContext, new SACapperInterface() {
            @Override
            public void didFindDAUId(int id) {
                dauid = id;
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
        return "4.1.10";
    }

    private String getSdk() {
        return "android";
    }

    public String getSDKVersion() {
        return getSdk() + "_" + getVersion();
    }

    public Context getApplicationContext(){
        return SAApplication.getSAApplicationContext();
    }

    public int getDAUID() {
        return dauid;
    }
}
