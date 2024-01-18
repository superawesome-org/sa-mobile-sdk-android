/*
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.sasession.session;

import android.content.Context;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.Locale;

import tv.superawesome.lib.sasession.capper.ISACapper;
import tv.superawesome.lib.sasession.capper.SACapper;
import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.lib.sasession.defines.SARTBInstl;
import tv.superawesome.lib.sasession.defines.SARTBPlaybackMethod;
import tv.superawesome.lib.sasession.defines.SARTBPosition;
import tv.superawesome.lib.sasession.defines.SARTBSkip;
import tv.superawesome.lib.sasession.defines.SARTBStartDelay;
import tv.superawesome.lib.sasession.publisher.PublisherConfiguration;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.publisher.SAOrientation;
import tv.superawesome.sdk.publisher.SAVersion;
import tv.superawesome.sdk.publisher.state.CloseButtonState;

/**
 * Class that manages an AwesomeAds session, containing all variables needed to setup loading for
 * a certain ad
 */
public class SASession implements ISASession {

    // constants
    private final static String DEV_URL = "https://ads.dev.superawesome.tv/v2";
    private final static String PRODUCTION_URL = "https://ads.superawesome.tv/v2";
    private final static String STAGING_URL = "https://ads.staging.superawesome.tv/v2";
    private final static String UITESTING_URL = "http://localhost:8080";
    private final static String DEVICE_PHONE = "phone";
    private final static String DEVICE_TABLET = "tablet";

    // the current frequency capper
    private final ISACapper capper;

    // private state members
    private final Context context;
    private String baseUrl;
    private boolean testEnabled;
    private int dauId;
    private String version;
    private final String packageName;
    private final String appName;
    private String lang;
    private final String device;
    private final String userAgent;
    private SAConfiguration configuration;
    private SARTBInstl instl;
    private SARTBPosition pos;
    private SARTBSkip skip;
    private SARTBStartDelay startDelay;
    private SARTBPlaybackMethod playbackMethod;
    private PublisherConfiguration publisherConfiguration;
    private int width;
    private int height;


    /**
     * Main constructor for the Session
     *
     * @param context current context (activity or fragment)
     */
    public SASession(Context context) {
        this.context = context;

        // create the capper
        capper = new SACapper(context);

        // set default configuration
        setConfigurationProduction();
        disableTestMode();
        setDauId(0);
        setVersion(SAVersion.getSDKVersion(SAUtils.getPluginName(context)));
        packageName = context != null ? context.getPackageName() : "unknown";
        appName = context != null ? SAUtils.getAppLabel(context) : "unknown";
        lang = Locale.getDefault().toString();
        device = SAUtils.getSystemSize() == SAUtils.SASystemSize.phone ? DEVICE_PHONE : DEVICE_TABLET;
        instl = SARTBInstl.FULLSCREEN;
        pos = SARTBPosition.FULLSCREEN;
        skip = SARTBSkip.NO_SKIP;
        startDelay = SARTBStartDelay.PRE_ROLL;
        playbackMethod = SARTBPlaybackMethod.WITH_SOUND_ON_SCREEN;
        width = 0;
        height = 0;

        // get user agent if the current thread is the main one (otherwise this will crash
        // in some scenarios)
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            userAgent = SAUtils.getUserAgent(context);
        } else {
            userAgent = SAUtils.getUserAgent(null);
        }

    }

    /**
     * Method that "prepares" the session. Mainly this means getting the DAU ID integer, which has
     * to be done on a secondary thread
     *
     * @param listener an instance of the SASessionInterface
     */
    @Override
    public void prepareSession (final SASessionInterface listener) {
        capper.getDauID(dauID -> {
            setDauId(dauID);

            if (listener != null) {
                listener.didFindSessionReady();
            }
        });
    }

    /**
     * Setters
     */
    public void setConfiguration(SAConfiguration configuration) {
        if (configuration == SAConfiguration.PRODUCTION) {
            this.configuration = SAConfiguration.PRODUCTION;
            baseUrl = PRODUCTION_URL;
        } else if (configuration == SAConfiguration.STAGING) {
            this.configuration = SAConfiguration.STAGING;
            baseUrl = STAGING_URL;
        } else if (configuration == SAConfiguration.UITESTING) {
            this.configuration = SAConfiguration.UITESTING;
            baseUrl = UITESTING_URL;
        } else {
            this.configuration = SAConfiguration.DEV;
            baseUrl = DEV_URL;
        }
    }

    public void setConfigurationProduction() {
        setConfiguration(SAConfiguration.PRODUCTION);
    }

    public void setConfigurationStaging() {
        setConfiguration(SAConfiguration.STAGING);
    }

    public void setConfigurationDev() {
        setConfiguration(SAConfiguration.DEV);
    }

    public void setTestMode(boolean testEnabled) {
        this.testEnabled = testEnabled;
    }

    public void enableTestMode () {
        setTestMode(true);
    }

    public void disableTestMode () {
        setTestMode(false);
    }

    public void setDauId(int dauId) {
        this.dauId = dauId;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setInstl(SARTBInstl instl) {
        this.instl = instl;
    }

    public void setPos(SARTBPosition pos) {
        this.pos = pos;
    }

    public void setSkip(SARTBSkip skip) {
        this.skip = skip;
    }

    public void setStartDelay(SARTBStartDelay startDelay) {
        this.startDelay = startDelay;
    }

    public void setPlaybackMethod(SARTBPlaybackMethod playbackMethod) {
        this.playbackMethod = playbackMethod;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLanguage (String lang) {
        this.lang = lang;
    }

    public void setLanguage (Locale locale) {
        if (locale != null) {
            this.lang = locale.toString();
        }
    }

    public void setPublisherConfiguration(boolean parentalGateOn,
                                          boolean bumperPageOn,
                                          boolean closeWarning,
                                          @NonNull SAOrientation orientation,
                                          boolean closeAtEnd,
                                          boolean muteOnStart,
                                          boolean showMore,
                                          @NonNull SARTBStartDelay startDelay,
                                          @NonNull CloseButtonState closeButtonState) {

        publisherConfiguration = new PublisherConfiguration(
                parentalGateOn,
                bumperPageOn,
                closeWarning,
                orientation.ordinal(),
                closeAtEnd,
                muteOnStart,
                showMore,
                startDelay.getValue(),
                closeButtonState.getValue()
        );
    }

    public void setPublisherConfiguration(boolean parentalGateOn,
                                          boolean bumperPageOn,
                                          @NonNull SAOrientation orientation,
                                          @NonNull CloseButtonState closeButtonState) {

        publisherConfiguration = new PublisherConfiguration(
                parentalGateOn,
                bumperPageOn,
                null,
                orientation.ordinal(),
                null,
                null,
                null,
                null,
                closeButtonState.getValue()
        );
    }

    public void setPublisherConfiguration(boolean parentalGateOn,
                                          boolean bumperPageOn) {

        publisherConfiguration = new PublisherConfiguration(
                parentalGateOn,
                bumperPageOn,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    /**
     * Getters
     */

    @Override
    public String getBaseUrl () {
        return baseUrl;
    }

    @Override
    public boolean getTestMode () {
        return testEnabled;
    }

    @Override
    public int getDauId () {
        return dauId;
    }

    @Override
    public String getVersion () {
        return version;
    }

    @Override
    public SAConfiguration getConfiguration () { return configuration; }

    @Override
    public int getCachebuster () {
        return SAUtils.getCacheBuster();
    }

    @Override
    public String getPackageName () {
        return packageName;
    }

    @Override
    public String getAppName () {
        return appName;
    }

    @Override
    public SAUtils.SAConnectionType getConnectionType () {
        return SAUtils.getNetworkConnectivity(context);
    }

    @Override
    public String getLang () {
        return lang;
    }

    @Override
    public String getDevice () {
        return device;
    }

    @Override
    public String getUserAgent () {
        return userAgent;
    }

    @Override
    public SARTBInstl getInstl() {
        return instl;
    }

    @Override
    public SARTBPosition getPos() {
        return pos;
    }

    @Override
    public SARTBSkip getSkip() {
        return skip;
    }

    @Override
    public SARTBStartDelay getStartDelay() {
        return startDelay;
    }

    @Override
    public SARTBPlaybackMethod getPlaybackMethod() {
        return playbackMethod;
    }

    @Override
    public PublisherConfiguration getPublisherConfiguration() {
        return publisherConfiguration;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
