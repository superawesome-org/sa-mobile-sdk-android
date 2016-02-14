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

/**
 * This is a Singleton class through which SDK users setup their AwesomeAds instance
 */
public class SuperAwesome {

    /** other variables */
    private final String BASE_URL_STAGING = "https://ads.staging.superawesome.tv/v2";
    private final String BASE_URL_DEVELOPMENT = "https://ads.dev.superawesome.tv/v2";
    private final String BASE_URL_PRODUCTION = "https://ads.superawesome.tv/v2";
    private final String SUPER_AWESOME_DAU_ID = "SUPER_AWESOME_DAU_ID";

    private String baseUrl;
    private boolean isTestEnabled;
    private String dauID;

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
        return "3.4.1";
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
        this.enableDeviceAppUserId(_appContext);
    }

    public Context getApplicationContext(){
        return SAApplication.getSAApplicationContext();
    }

    /** group of functions that relate to the Device-App-User ID */
    private String generateId () {
        /** constants */
        final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXZY0123456789";
        final int length = alphabet.length();
        final int dauLength = 32;

        /** generate the string */
        String s = "";
        for (int i = 0; i < dauLength; i++){
            int index = SAUtils.randomNumberBetween(0, length - 1);
            s += alphabet.charAt(index);
        }
        return s;
    }

    private void enableDeviceAppUserId (Context _appContext) {
        /** get the shared prefs */
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_appContext);
        String dauID = preferences.getString(SUPER_AWESOME_DAU_ID, null);

        /** if dauId is NOK, then generate it */
        if (dauID == null || dauID.equals("")) {
            SharedPreferences.Editor editor = preferences.edit();
            dauID = generateId();
            editor.putString(SUPER_AWESOME_DAU_ID, dauID);
            editor.apply();
            this.dauID = dauID;
        }
        /** else assign it */
        else {
            this.dauID = dauID;
        }
    }

    public String getDAUID() {
        return this.dauID;
    }
}
