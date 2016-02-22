/**
 * @class: SuperAwesome.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Random;

import tv.superawesome.lib.sanetwork.SAApplication;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.capper.SACapper;

/**
 * This is a Singleton class through which SDK users setup their AwesomeAds instance
 */
public class SuperAwesome {

    /** other variables */
    private final String BASE_URL_STAGING = "https://ads.staging.superawesome.tv/v2";
    private final String BASE_URL_DEVELOPMENT = "https://ads.dev.superawesome.tv/v2";
    private final String BASE_URL_PRODUCTION = "https://ads.superawesome.tv/v2";

    private String baseUrl;
    private boolean isTestEnabled;
    private int dauID;

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
    }

    /** Get the only object available */
    public static SuperAwesome getInstance(){
        return instance;
    }

    /** provide versionin */
    private String getVersion () {
        return "3.4.3";
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
        this.baseUrl = BASE_URL_PRODUCTION;
    }

    public void setConfigurationStaging() {
        this.config = SAConfiguration.STAGING;
        this.baseUrl = BASE_URL_STAGING;
    }

    public void setConfigurationDevelopment() {
        this.config = SAConfiguration.DEVELOPMENT;
        this.baseUrl = BASE_URL_DEVELOPMENT;
    }

    public String getBaseURL() {
        return this.baseUrl;
    }

    public SAConfiguration getConfiguration() { return this.config; }

    /**
     * Group of functions that encapsulate isTestEnabled functionality
     */
    public void enableTestMode() {
        this.isTestEnabled = true;
    }

    public void disableTestMode() {
        this.isTestEnabled = false;
    }

    public void setTestMode(boolean isTestEnabled) { this.isTestEnabled = isTestEnabled; }

    public boolean isTestingEnabled() { return this.isTestEnabled; }

    /**
     * Group of functions that encapsulate the SAApplication functionality
     */
    public void setApplicationContext(Context _appContext){
        SAApplication.setSAApplicationContext(_appContext);
        SACapper.enableCapping(_appContext, new SACapper.SACapperListener() {
            @Override
            public void didFindDAUId(int id) {
                dauID = id;
            }
        });
    }

    public Context getApplicationContext(){
        return SAApplication.getSAApplicationContext();
    }

    public int getDAUID() {
        return this.dauID;
    }
}
