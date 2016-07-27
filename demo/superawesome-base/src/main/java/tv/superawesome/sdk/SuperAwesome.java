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

    public enum SAConfiguration {
        STAGING,
        DEVELOPMENT,
        PRODUCTION
    }
    private SAConfiguration config;

    /** the singleton SuperAwesome instance */
    private static SuperAwesome instance = new SuperAwesome();

    /** make the constructor private so that this class cannot be instantiated */
    private SuperAwesome(){
        this.setConfigurationProduction();
        this.disableTestMode();
        SAEvents.enableSATracking();
        SASession.getInstance().setVersion(this.getVersion());
    }

    /** Get the only object available */
    public static SuperAwesome getInstance(){
        return instance;
    }

    /** provide versionin */
    private String getVersion () {
        return "4.1.4";
    }

    private String getSdk() {
        return "android";
    }

    public String getSDKVersion() {
        return SuperAwesome.getInstance().getSdk() + "_" + SuperAwesome.getInstance().getVersion();
    }

    /**
     * Group of functions that encapsulate configuration / URL functionality
     */
    public void setConfigurationProduction() {
        this.config = SAConfiguration.PRODUCTION;
        SASession.getInstance().setConfigurationProduction();
    }

    public void setConfigurationStaging() {
        this.config = SAConfiguration.STAGING;
        SASession.getInstance().setConfigurationStaging();
    }

    public String getBaseURL() {
        return SASession.getInstance().getBaseUrl();
    }

    public SAConfiguration getConfiguration() { return this.config; }

    /**
     * Group of functions that encapsulate isTestEnabled functionality
     */
    public void enableTestMode() {
        SASession.getInstance().setTest(true);
    }

    public void disableTestMode() {
        SASession.getInstance().setTest(false);
    }

    public void setTestMode(boolean isTestEnabled) {
        SASession.getInstance().setTest(isTestEnabled);
    }

    public boolean isTestingEnabled() { return SASession.getInstance().isTestEnabled(); }

    /**
     * Group of functions that encapsulate the SAApplication functionality
     */
    public void setApplicationContext(Context _appContext){
        SAApplication.setSAApplicationContext(_appContext);
        SACapper.enableCapping(_appContext, new SACapperInterface() {
            @Override
            public void didFindDAUId(int id) {
                SASession.getInstance().setDauId(id);
            }
        });
    }

    public Context getApplicationContext(){
        return SAApplication.getSAApplicationContext();
    }

    public int getDAUID() {
        return SASession.getInstance().getDauId();
    }
}
