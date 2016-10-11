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

    // variables
    private static SuperAwesome instance = new SuperAwesome();

    // constructors
    private SuperAwesome(){
        this.setConfigurationProduction();
        this.disableTestMode();
        SAEvents.enableSATracking();
        SASession.getInstance().setVersion(this.getVersion());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Setters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void setConfiguration(int configuration) {
        SASession.getInstance().setConfiguration(configuration);
    }
    public void setConfigurationProduction() {
        SASession.getInstance().setConfigurationProduction();
    }

    public void setConfigurationStaging() {
        SASession.getInstance().setConfigurationStaging();
    }

    public void enableTestMode() {
        SASession.getInstance().setTest(true);
    }

    public void disableTestMode() {
        SASession.getInstance().setTest(false);
    }

    public void setTestMode(boolean isTestEnabled) {
        SASession.getInstance().setTest(isTestEnabled);
    }

    public void setApplicationContext(Context _appContext){
        SAApplication.setSAApplicationContext(_appContext);
        SACapper.enableCapping(_appContext, new SACapperInterface() {
            @Override
            public void didFindDAUId(int id) {
                SASession.getInstance().setDauId(id);
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
        return "4.1.9.2";
    }

    private String getSdk() {
        return "android";
    }

    public String getSDKVersion() {
        return SuperAwesome.getInstance().getSdk() + "_" + SuperAwesome.getInstance().getVersion();
    }

    public int getConfiguration() { return SASession.getInstance().getConfiguration(); }

    public boolean isTestingEnabled() { return SASession.getInstance().isTestEnabled(); }

    public String getBaseURL() {
        return SASession.getInstance().getBaseUrl();
    }

    public Context getApplicationContext(){
        return SAApplication.getSAApplicationContext();
    }

    public int getDAUID() {
        return SASession.getInstance().getDauId();
    }
}
