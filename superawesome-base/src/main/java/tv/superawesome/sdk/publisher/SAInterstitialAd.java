/*
 * @Copyright:   SADefaults Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.sdk.publisher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sametrics.SAPerformanceMetrics;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACreativeFormat;
import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.lib.sasession.defines.SARTBInstl;
import tv.superawesome.lib.sasession.defines.SARTBPlaybackMethod;
import tv.superawesome.lib.sasession.defines.SARTBPosition;
import tv.superawesome.lib.sasession.defines.SARTBSkip;
import tv.superawesome.lib.sasession.defines.SARTBStartDelay;
import tv.superawesome.lib.sasession.session.SASession;
import tv.superawesome.lib.satiming.SACountDownTimer;
import tv.superawesome.lib.sautils.SAImageUtils;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.publisher.state.CloseButtonState;

/**
 * Class that abstracts away the process of loading & displaying an
 * interstitial / fullscreen type Ad.
 * A subclass of the Android "Activity" class.
 */
public class SAInterstitialAd extends Activity implements SABannerAd.SABannerAdListener {

    private SABannerAd              interstitialBanner = null;
    private ImageButton             closeButton = null;
    private static CloseButtonState closeButtonState = SADefaults.defaultCloseButtonStateInterstitial();

    // the ad
    private SAAd ad = null;

    // static session
    private static SASession session = null;

    // fully private variables
    private static final HashMap<Integer, Object> ads = new HashMap<>();

    // private vars w/ exposed setters & getters (state vars)
    private static SAInterface      listener = (placementId, event) -> {};

    private static boolean          isParentalGateEnabled = SADefaults.defaultParentalGate();
    private static boolean          isBumperPageEnabled = SADefaults.defaultBumperPage();
    private static boolean          isTestingEnabled = SADefaults.defaultTestMode();
    private static boolean          isBackButtonEnabled = SADefaults.defaultBackButton();
    private static SAOrientation    orientation = SADefaults.defaultOrientation();
    private static SAConfiguration  configuration = SADefaults.defaultConfiguration();
    private static final SAPerformanceMetrics performanceMetrics = new SAPerformanceMetrics();
    private final SACountDownTimer failSafeTimer = new SACountDownTimer();
    private SACountDownTimer closeButtonTimer;
    private static long closeButtonDelayTimer;

    /**
     * Overridden "onCreate" method, part of the Activity standard set of methods.
     * Here is the part where the activity / interstitial ad gets configured
     *
     * @param savedInstanceState previous saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // call super
        super.onCreate(savedInstanceState);

        // local vars
        boolean isParentalGateEnabledL = getIsParentalGateEnabled();
        boolean isBumperPageEnabledL = getIsBumperPageEnabled();
        SAOrientation orientationL = getOrientation();
        SAInterface listenerL = getListener();
        Bundle bundle = getIntent().getExtras();
        String adStr = bundle.getString("ad");
        ad = new SAAd(SAJsonParser.newObject(adStr));

        int closeButtonValue = bundle.getInt("closeButton", SADefaults.defaultCloseButtonStateInterstitial().getValue());
        long closeButtonTimer = bundle.getLong("closeButtonTimer", SADefaults.defaultCloseButtonDelayTimerInterstitial());
        CloseButtonState closeButtonState = CloseButtonState.Companion.fromInt(closeButtonValue, (double)closeButtonTimer);

        // make sure direction is locked
        switch (orientationL) {
            case ANY:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                break;
            case PORTRAIT:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case LANDSCAPE:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
        }

        // create the parent relative layout
        // subviews
        RelativeLayout parent = new RelativeLayout(this);
        parent.setId(SAUtils.randomNumberBetween(1000000, 1500000));
        parent.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        // create the interstitial banner
        interstitialBanner = new SABannerAd(this);
        interstitialBanner.setBannerListener(this);
        interstitialBanner.setId(SAUtils.randomNumberBetween(1000000, 1500000));
        interstitialBanner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        interstitialBanner.setColor(false);
        interstitialBanner.setAd(ad);
        interstitialBanner.setTestMode(isTestingEnabled);
        interstitialBanner.setConfiguration(configuration);
        interstitialBanner.setListener(listenerL);
        interstitialBanner.setBumperPage(isBumperPageEnabledL);
        interstitialBanner.setParentalGate(isParentalGateEnabledL);
        interstitialBanner.setContentDescription("Ad content");

        // create the close button
        float fp = SAUtils.getScaleFactor(this);
        closeButton = new ImageButton(this);
        closeButton.setVisibility(closeButtonState == CloseButtonState.VisibleImmediately.INSTANCE ? View.VISIBLE :View.GONE);
        closeButton.setImageBitmap(SAImageUtils.createCloseButtonBitmap());
        closeButton.setBackgroundColor(Color.TRANSPARENT);
        closeButton.setPadding(0, 0, 0, 0);
        closeButton.setScaleType(ImageView.ScaleType.FIT_XY);
        RelativeLayout.LayoutParams buttonLayout = new RelativeLayout.LayoutParams((int) (30 * fp), (int) (30* fp));
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        closeButton.setLayoutParams(buttonLayout);
        closeButton.setOnClickListener(v -> close());
        closeButton.setContentDescription("Close");

        // set the view hierarchy
        parent.addView(interstitialBanner);
        parent.addView(closeButton);
        setContentView(parent);

        failSafeTimer.setListener(() -> {
            // Override the close button click behaviour when showing the close button as
            // a fail safe
            closeButton.setOnClickListener(v -> failSafeClose());
            closeButton.setVisibility(View.VISIBLE);
            performanceMetrics.trackCloseButtonFallbackShown(ad);
        });

        if (closeButtonState instanceof CloseButtonState.Custom) {
            this.closeButtonTimer = new SACountDownTimer(closeButtonState.getTimeInMillis());
            this.closeButtonTimer.setListener(() -> {
                closeButton.setVisibility(View.VISIBLE);
            });
        }

        // finally play!
        interstitialBanner.play(this);
    }

    /**
     * Overridden "onBackPressed" method of the activity
     * Depending on how the ad is customised, this will lock the back button or it will allow it.
     * If it allows it, it's going to also send an "adClosed" event back to the SDK user
     */
    @Override
    public void onBackPressed () {
        boolean isBackButtonEnabledL = getIsBackButtonEnabled();
        if (isBackButtonEnabledL) {
            close();
            super.onBackPressed();
        }
    }

    /**
     * Method that closes the ad via the fail safe timer
     */
    private void failSafeClose() {
        listener.onEvent(ad.placementId, SAEvent.adEnded);
        close();
    }

    /**
     * Method that closes the interstitial ad
     */
    private void close() {
        failSafeTimer.stop();
        if (closeButtonTimer != null) {
            closeButtonTimer.stop();
        }
        // close the banner as well
        interstitialBanner.close();
        interstitialBanner.setAd(null);

        // remove the ad from the "ads" hash map once it's been played
        ads.remove(ad.placementId);

        // close & resume previous activity
        this.finish();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    /**
     * Static method that loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId   the Ad placement id to load data for
     * @param context       the current context
     */
    public static void load(final int placementId, Context context) {
        load(placementId, context, null, Collections.emptyMap());
    }

    /**
     * Static method that loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId   the Ad placement id to load data for
     * @param context       the current context
     * @param openRtbPartnerId OpenRTB Partner ID parameter to be sent with all requests.
     */
    public static void load(final int placementId,
                            Context context,
                            @NonNull final String openRtbPartnerId) {
        load(placementId, context, openRtbPartnerId, Collections.emptyMap());
    }

    public static void load(final int placementId,
                            Context context,
                            final Map<String, Object> options) {
        load(placementId, context, null, options);
    }

    /**
     * Static method that loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId   the Ad placement id to load data for
     * @param context       the current context
     * @param openRtbPartnerId OpenRTB Partner ID parameter to be sent with all requests.
     * @param options       a dictionary of data to send with an ad's requests and events.
     *                      Supports String or Int values.
     */
    public static void load(final int placementId,
                            Context context,
                            @Nullable final String openRtbPartnerId,
                            final Map<String, Object> options) {

        // very late init of the AwesomeAds SDK
        try {
            AwesomeAds.init(((Activity)context).getApplication(), false);
        } catch (Exception e) {
            Log.d("SuperAwesome", "Error initing AwesomeAds in SAInterstitialAd " + e.getMessage());
        }

        // if the ad data for the placement id doesn't existing in the "ads" hash map, then
        // proceed with loading it
        if (!ads.containsKey(placementId)) {

            // set a placeholder
            ads.put(placementId, new Object());

            // create the loader
            final SALoader loader = new SALoader(context);

            // create a current session
            session = new SASession (context);
            session.setTestMode(isTestingEnabled);
            session.setConfiguration(configuration);
            session.setPos(SARTBPosition.FULLSCREEN);
            session.setPlaybackMethod(SARTBPlaybackMethod.WITH_SOUND_ON_SCREEN);
            session.setInstl(SARTBInstl.FULLSCREEN);
            session.setSkip(SARTBSkip.SKIP);
            session.setStartDelay(SARTBStartDelay.PRE_ROLL);
            session.setPublisherConfiguration(isParentalGateEnabled,
                    isBumperPageEnabled,
                    orientation,
                    closeButtonState);

            try {
                SAUtils.SASize size = SAUtils.getRealScreenSize((Activity) context, false);
                session.setWidth(size.width);
                session.setHeight(size.height);
            } catch (Exception e) {
                // do nothing
            }

            session.prepareSession(() -> {

                performanceMetrics.setSession(session);
                performanceMetrics.startTimingForLoadTime();

                // after session is prepared, start loading
                loader.loadAd(placementId, session, options, openRtbPartnerId, response -> {

                    if (response.status != 200) {
                        //
                        // remove from here
                        ads.remove(placementId);

                        //
                        // send callback
                        if (listener != null) {
                            listener.onEvent(placementId, SAEvent.adFailedToLoad);
                        } else {
                            Log.w("AwesomeAds", "Interstitial Ad listener not implemented. Event would have been adFailedToLoad");
                        }
                    }
                    else {
                        // put the correct value
                        if (response.isValid()) {
                            SAAd adResponse = response.ads.get(0);
                            adResponse.openRtbPartnerId = openRtbPartnerId;

                            performanceMetrics.trackLoadTime(adResponse);
                            ads.put(placementId, adResponse);
                        }
                        // remove existing
                        else {
                            ads.remove(placementId);
                        }

                        // call listener
                        if (listener != null) {
                            SAEvent eventToSend = response.isValid() ? SAEvent.adLoaded : SAEvent.adEmpty;
                            listener.onEvent(placementId, eventToSend);
                            Log.d("SAInterstitialAd", "Event callback: " + eventToSend);
                        } else {
                            Log.w("AwesomeAds", "Interstitial Ad listener not implemented. Event would have been either adLoaded or adEmpty");
                        }
                    }
                });
            });

        }
        // else if the ad data for the placement exists in the "ads" hash map, then notify the
        // user that it already exists and he should just play it
        else {
            if (listener != null) {
                listener.onEvent(placementId, SAEvent.adAlreadyLoaded);
            } else {
                Log.w("AwesomeAds", "Interstitial Ad listener not implemented. Event would have been adAlreadyLoaded");
            }
        }
    }

    /**
     * Static method that loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId   the Ad placement id to load data for
     * @param lineItemId    The id of the lineItem
     * @param creativeId    The id of the creative
     * @param context       the current context
     */
    public static void load(final int placementId,
                            final int lineItemId,
                            final int creativeId,
                            Context context) {
        load(placementId, lineItemId, creativeId, context, null, Collections.emptyMap());
    }

    /**
     * Static method that loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId   the Ad placement id to load data for
     * @param lineItemId    The id of the lineItem
     * @param creativeId    The id of the creative
     * @param context       the current context.
     * @param openRtbPartnerId OpenRTB Partner ID parameter to be sent with all requests.
     */
    public static void load(final int placementId,
                            final int lineItemId,
                            final int creativeId,
                            Context context,
                            @NonNull final String openRtbPartnerId) {
        load(placementId, lineItemId, creativeId, context, openRtbPartnerId, Collections.emptyMap());
    }

    /**
     * Static method that loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId   the Ad placement id to load data for
     * @param lineItemId    The id of the lineItem
     * @param creativeId    The id of the creative
     * @param context       the current context
     * @param options       a dictionary of data to send with an ad's requests and events.
     *                      Supports String or Int values.
     * @param openRtbPartnerId OpenRTB Partner ID parameter to be sent with all requests.
     */
    public static void load(final int placementId,
                            final int lineItemId,
                            final int creativeId,
                            Context context,
                            @Nullable final String openRtbPartnerId,
                            final Map<String, Object> options) {

        // very late init of the AwesomeAds SDK
        try {
            AwesomeAds.init(((Activity)context).getApplication(), false);
        } catch (Exception e) {
            Log.d("SuperAwesome", "Error initing AwesomeAds in SAInterstitialAd " + e.getMessage());
        }

        // if the ad data for the placement id doesn't existing in the "ads" hash map, then
        // proceed with loading it
        if (!ads.containsKey(placementId)) {

            // set a placeholder
            ads.put(placementId, new Object());

            // create the loader
            final SALoader loader = new SALoader(context);

            // create a current session
            session = new SASession (context);
            session.setTestMode(isTestingEnabled);
            session.setConfiguration(configuration);
            session.setPos(SARTBPosition.FULLSCREEN);
            session.setPlaybackMethod(SARTBPlaybackMethod.WITH_SOUND_ON_SCREEN);
            session.setInstl(SARTBInstl.FULLSCREEN);
            session.setSkip(SARTBSkip.SKIP);
            session.setStartDelay(SARTBStartDelay.PRE_ROLL);
            session.setPublisherConfiguration(isParentalGateEnabled,
                    isBumperPageEnabled,
                    orientation,
                    closeButtonState);

            try {
                SAUtils.SASize size = SAUtils.getRealScreenSize((Activity) context, false);
                session.setWidth(size.width);
                session.setHeight(size.height);
            } catch (Exception e) {
                // do nothing
            }

            session.prepareSession(() -> {

                performanceMetrics.setSession(session);
                performanceMetrics.startTimingForLoadTime();

                // after session is prepared, start loading
                loader.loadAd(placementId, lineItemId, creativeId, session, options, openRtbPartnerId, response -> {

                    if (response.status != 200) {
                        //
                        // remove from here
                        ads.remove(placementId);

                        //
                        // send callback
                        if (listener != null) {
                            listener.onEvent(placementId, SAEvent.adFailedToLoad);
                        } else {
                            Log.w("AwesomeAds", "Interstitial Ad listener not implemented. Event would have been adFailedToLoad");
                        }
                    }
                    else {
                        // put the correct value
                        if (response.isValid()) {
                            SAAd adResponse = response.ads.get(0);
                            adResponse.openRtbPartnerId = openRtbPartnerId;

                            performanceMetrics.trackLoadTime(adResponse);
                            ads.put(placementId, adResponse);
                        }
                        // remove existing
                        else {
                            ads.remove(placementId);
                        }

                        // call listener
                        if (listener != null) {
                            SAEvent eventToSend = response.isValid() ? SAEvent.adLoaded : SAEvent.adEmpty;
                            listener.onEvent(placementId, eventToSend);
                            Log.d("SAInterstitialAd", "Event callback: " + eventToSend);
                        } else {
                            Log.w("AwesomeAds", "Interstitial Ad listener not implemented. Event would have been either adLoaded or adEmpty");
                        }
                    }
                });
            });

        }
        // else if the ad data for the placement exists in the "ads" hash map, then notify the
        // user that it already exists and he should just play it
        else {
            if (listener != null) {
                listener.onEvent(placementId, SAEvent.adAlreadyLoaded);
            } else {
                Log.w("AwesomeAds", "Interstitial Ad listener not implemented. Event would have been adAlreadyLoaded");
            }
        }
    }

    /**
     * Static method that returns whether ad data for a certain placement has already been loaded
     *
     * @param placementId   the Ad placement id to check for
     * @return              true or false
     */
    public static boolean hasAdAvailable(int placementId) {
        Object object = ads.get(placementId);
        return object instanceof SAAd;
    }

    public static SAAd getAd (int placementId) {
        if (ads.containsKey(placementId)) {
            Object object = ads.get(placementId);
            if (object instanceof SAAd) {
                return (SAAd) object;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Static method that, if an ad data is loaded, will play the content for the user
     *
     * @param placementId   the Ad placement id to play an ad for
     * @param context       the current context (activity or fragment)
     */
    public static void play(int placementId, Context context) {

        // get the generic Object
        Object generic = ads.get(placementId);

        // if notnull & instance of SAAd
        if (generic instanceof SAAd) {

            // try to get the ad that fits the placement id
            SAAd adL = (SAAd) generic;

            // try to start the activity
            if (adL.creative.format != SACreativeFormat.video && context != null) {

                if (adL.creative.format == SACreativeFormat.rich || adL.creative.format == SACreativeFormat.tag) {
                    // Start tracking render time for Rich Media
                    performanceMetrics.startTimerForRenderTime();
                }

                // create intent
                Intent intent = new Intent(context, SAInterstitialAd.class);
                intent.putExtra("ad", adL.writeToJson().toString());
                intent.putExtra("closeButton", closeButtonState.getValue());
                intent.putExtra("closeButtonTimer", closeButtonDelayTimer);

                // clear ad - meaning that it's been played
                ads.remove(placementId);

                // start new activity
                context.startActivity(intent);
            } else {
                if (listener != null) {
                    listener.onEvent(placementId, SAEvent.adFailedToShow);
                } else {
                    Log.w("AwesomeAds", "Interstitial Ad listener not implemented. Event would have been adFailedToShow");
                }
            }
        }
        else {
            if (listener != null) {
                listener.onEvent(placementId, SAEvent.adFailedToShow);
            } else {
                Log.w("AwesomeAds", "Interstitial Ad listener not implemented. Event would have been adFailedToShow");
            }
        }
    }

    /**
     * Method used for testing purposes (and the AwesomeApp) to manually put an ad in the
     * interstitial ads map
     *
     * @param ad an instance of SAAd
     */
    public static void setAd (SAAd ad) {
        if (ad != null && ad.isValid()) {
            ads.put(ad.placementId, ad);
        }
    }

    /**
     * Method that enables the close button to display immediately without a delay.
     * WARNING: this will allow users to close the ad before the viewable tracking event is fired
     * and should only be used if you explicitly want this behaviour over consistent tracking.
     */
    public static void enableCloseButtonNoDelay() {
        closeButtonState = CloseButtonState.VisibleImmediately.INSTANCE;
    }

    /**
     * Method that enables the close button to display with a delay.
     */
    public static void enableCloseButton() {
        closeButtonState = CloseButtonState.VisibleWithDelay.INSTANCE;
    }

    /**
     * Enables showing the close button after a set delay. This overrides any close button configuration
     * that might have been called before.
     * @param delay the amount of delay in seconds.
     */
    public static void enableCloseButtonWithDelay(double delay) {
        closeButtonState = new CloseButtonState.Custom(delay);
        // Set in seconds and converted to milliseconds before playback
        closeButtonDelayTimer = (long) closeButtonState.getTime();
    }

    /**********************************************************************************************
     * Setters and getters
     **********************************************************************************************/

    public static void setListener(SAInterface value) {
        listener = value != null ? value : listener;
    }

    public static void enableParentalGate () {
        setParentalGate(true);
    }

    public static void disableParentalGate () {
        setParentalGate(false);
    }

    public static void enableBumperPage () {
        setBumperPage(true);
    }

    public static void disableBumperPage () {
        setBumperPage(false);
    }

    public static void enableTestMode () {
        setTestMode(true);
    }

    public static void disableTestMode () {
        setTestMode(false);
    }

    public static void enableBackButton () {
        setBackButton(true);
    }

    public static void disableBackButton () {
        setBackButton(false);
    }

    public static void setConfigurationProduction () {
        setConfiguration(SAConfiguration.PRODUCTION);
    }

    public static void setConfigurationDev () {
        setConfiguration(SAConfiguration.DEV);
    }

    public static void setConfigurationStaging () {
        setConfiguration(SAConfiguration.STAGING);
    }

    public static void setOrientationAny () {
        setOrientation(SAOrientation.ANY);
    }

    public static void setOrientationPortrait () {
        setOrientation(SAOrientation.PORTRAIT);
    }

    public static void setOrientationLandscape () {
        setOrientation(SAOrientation.LANDSCAPE);
    }

    private static SAInterface getListener () {
        return listener;
    }

    private static boolean getIsParentalGateEnabled () {
        return isParentalGateEnabled;
    }

    public static boolean getIsBumperPageEnabled () {
        return isBumperPageEnabled;
    }

    private static SAOrientation getOrientation () {
        return orientation;
    }

    private static boolean getIsBackButtonEnabled () {
        return isBackButtonEnabled;
    }

    public static void setParentalGate (boolean value) {
        isParentalGateEnabled = value;
    }

    public static void setBumperPage (boolean value) {
        isBumperPageEnabled = value;
    }

    public static void setTestMode (boolean value) {
        isTestingEnabled = value;
    }

    public static void setBackButton (boolean value) {
        isBackButtonEnabled = value;
    }

    public static void setConfiguration (SAConfiguration value) {
        configuration = value;
    }

    public static void setOrientation (SAOrientation value) {
        orientation = value;
    }

    /**
     // Lifecycle Methods.
     */

    @Override
    public void onStart() {
        super.onStart();
        failSafeTimer.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        failSafeTimer.pause();
        if (closeButtonTimer != null) {
            closeButtonTimer.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        failSafeTimer.stop();
        if (closeButtonTimer != null) {
            closeButtonTimer.stop();
        }
    }

    /**
     * SABannerAdListener Methods.
     */

    @Override
    public void hasBeenVisible() {
        if (!(closeButtonState instanceof CloseButtonState.Custom)) {
            closeButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hasShown() {
        failSafeTimer.stop();
        if (closeButtonTimer != null) {
            closeButtonTimer.start();
        }
    }

    @Override
    public void failedToShow() {
        close();
    }

    @VisibleForTesting
    private static void clearCache() {
        ads.clear();
    }

    public static SAPerformanceMetrics getPerformanceMetrics() {
        return performanceMetrics;
    }
}
