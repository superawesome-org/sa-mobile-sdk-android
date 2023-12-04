package tv.superawesome.sdk.publisher;

import static tv.superawesome.lib.sasession.defines.SARTBPlaybackMethod.WITH_SOUND_OFF_SCREEN;
import static tv.superawesome.lib.sasession.defines.SARTBPlaybackMethod.WITH_SOUND_ON_SCREEN;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.sametrics.SAPerformanceMetrics;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACreativeFormat;
import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.lib.sasession.defines.SARTBInstl;
import tv.superawesome.lib.sasession.defines.SARTBPosition;
import tv.superawesome.lib.sasession.defines.SARTBSkip;
import tv.superawesome.lib.sasession.defines.SARTBStartDelay;
import tv.superawesome.lib.sasession.session.SASession;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.publisher.managed.ManagedAdConfig;
import tv.superawesome.sdk.publisher.managed.SAManagedAdActivity;
import tv.superawesome.sdk.publisher.state.CloseButtonState;

public class SAVideoAd {

    // private vars w/ a public interface
    private static final SAEvents events = new SAEvents();
    private static final SAPerformanceMetrics performanceMetrics = new SAPerformanceMetrics();
    public static final HashMap<Integer, Object> ads = new HashMap<>();
    private static SAInterface listener = (placementId, event) -> {};

    private static boolean shouldShowCloseWarning = SADefaults.defaultCloseWarning();
    private static boolean isParentalGateEnabled = SADefaults.defaultParentalGate();
    private static boolean isBumperPageEnabled = SADefaults.defaultBumperPage();
    private static CloseButtonState closeButtonState = SADefaults.defaultCloseButtonState();
    private static long closeButtonDelayTimer;
    private static boolean shouldAutomaticallyCloseAtEnd = SADefaults.defaultCloseAtEnd();
    private static boolean shouldShowSmallClickButton = SADefaults.defaultSmallClick();
    private static boolean isTestingEnabled = SADefaults.defaultTestMode();
    private static boolean isBackButtonEnabled = SADefaults.defaultBackButton();
    private static SAOrientation orientation = SADefaults.defaultOrientation();
    private static SAConfiguration configuration = SADefaults.defaultConfiguration();
    private static SARTBStartDelay playback = SADefaults.defaultPlaybackMode();
    private static boolean shouldMuteOnStart = SADefaults.defaultMuteOnStart();

    /**
     * Static method that loads an ad into the video queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId   the Ad placement id to load data for
     * @param context       the current context
     */
    public static void load(final int placementId, final Context context) {
        load(placementId, context, null, Collections.emptyMap());
    }

    /**
     * Static method that loads an ad into the video queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId   the Ad placement id to load data for
     * @param context       the current context
     * @param openRtbPartnerId OpenRTB Partner ID parameter to be sent with all requests.
     */
    public static void load(final int placementId,
                            final Context context,
                            @NonNull final String openRtbPartnerId) {
        load(placementId, context, openRtbPartnerId, Collections.emptyMap());
    }

    /**
     * Static method that loads an ad into the video queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId   the Ad placement id to load data for
     * @param context       the current context
     * @param options       a dictionary of data to send with an ad's requests and events.
     *                      Supports String or Int values.
     */
    public static void load(final int placementId,
                            final Context context,
                            final Map<String, Object> options) {
        load(placementId, context, null, options);
    }

    /**
     * Static method that loads an ad into the video queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId   the Ad placement id to load data for
     * @param context       the current context
     * @param openRtbPartnerId OpenRTB Partner ID parameter to be sent with all requests.
     * @param options       a dictionary of data to send with an ad's requests and events.
     *                      Supports String or Int values.
     */
    public static void load(final int placementId,
                            final Context context,
                            @Nullable final String openRtbPartnerId,
                            final Map<String, Object> options) {

        // very late init of the AwesomeAds SDK
        try {
            AwesomeAds.init(((Activity) context).getApplication(), false);
        } catch (Exception e) {
            Log.d("SuperAwesome", "Error initing AwesomeAds in SAVideoActivity " + e.getMessage());
        }

        // if the ad data for the placement id doesn't existing in the "ads" hash map, then
        // proceed with loading it
        if (!ads.containsKey(placementId)) {

            // set a placeholder
            ads.put(placementId, new Object());

            // create a loader
            final SALoader loader = new SALoader(context);

            // create a current session
            final SASession session = getNewSession(context);
            session.setPublisherConfiguration(isParentalGateEnabled,
                    isBumperPageEnabled,
                    shouldShowCloseWarning,
                    orientation,
                    shouldAutomaticallyCloseAtEnd,
                    shouldMuteOnStart,
                    shouldShowSmallClickButton,
                    playback,
                    closeButtonState);

            session.prepareSession(() -> {

                performanceMetrics.setSession(session);
                performanceMetrics.startTimingForLoadTime();

                // after session is OK - start loading
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
                            Log.w("AwesomeAds", "Video Ad listener not implemented. Event would have been adFailedToLoad");
                        }
                    } else {
                        // find out the real valid
                        boolean isValid = response.isValid();
                        SAAd first = isValid ? response.ads.get(0) : null;
                        isValid = first != null
                                && (first.creative.details.media.isDownloaded || first.isVpaid);

                        // put the correct value
                        if (isValid) {
                            first.openRtbPartnerId = openRtbPartnerId;

                            performanceMetrics.trackLoadTime(first);
                            ads.put(placementId, first);
                        }
                        // remove existing
                        else {
                            ads.remove(placementId);
                        }

                        // call listener(s)
                        if (listener != null) {
                            SAEvent eventToSend = response.isValid() ? SAEvent.adLoaded : SAEvent.adEmpty;
                            listener.onEvent(placementId, eventToSend);
                            Log.d("SAVideoAd", "Event callback: " + eventToSend);
                        } else {
                            Log.w("AwesomeAds", "Video Ad listener not implemented. Event would have been either adLoaded or adEmpty");
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
                Log.w("AwesomeAds", "Video Ad listener not implemented. Event would have been adAlreadyLoaded");
            }
        }
    }

    /**
     * Static method that loads an ad into the video queue.
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
                            final Context context) {
        load(placementId, lineItemId, creativeId, context, null, Collections.emptyMap());
    }


    /**
     * Static method that loads an ad into the video queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId   the Ad placement id to load data for
     * @param lineItemId    The id of the lineItem
     * @param creativeId    The id of the creative
     * @param context       the current context
     * @param openRtbPartnerId OpenRTB Partner ID parameter to be sent with all requests.
     */
    public static void load(final int placementId,
                            final int lineItemId,
                            final int creativeId,
                            final Context context,
                            @NonNull final String openRtbPartnerId) {
        load(placementId, lineItemId, creativeId, context, openRtbPartnerId, Collections.emptyMap());
    }

    /**
     * Static method that loads an ad into the video queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId   the Ad placement id to load data for
     * @param lineItemId    The id of the lineItem
     * @param creativeId    The id of the creative
     * @param context       the current context
     * @param openRtbPartnerId OpenRTB Partner ID parameter to be sent with all requests.
     * @param options       a dictionary of data to send with an ad's requests and events.
     *                      Supports String or Int values.
     */
    public static void load(final int placementId,
                            final int lineItemId,
                            final int creativeId,
                            final Context context,
                            @Nullable final String openRtbPartnerId,
                            final Map<String, Object> options) {

        // very late init of the AwesomeAds SDK
        try {
            AwesomeAds.init(((Activity) context).getApplication(), false);
        } catch (Exception e) {
            Log.d("SuperAwesome", "Error initing AwesomeAds in SAVideoActivity " + e.getMessage());
        }

        // if the ad data for the placement id doesn't existing in the "ads" hash map, then
        // proceed with loading it
        if (!ads.containsKey(placementId)) {

            // set a placeholder
            ads.put(placementId, new Object());

            // create a loader
            final SALoader loader = new SALoader(context);

            // create a current session
            final SASession session = getNewSession(context);
            session.setPublisherConfiguration(isParentalGateEnabled,
                    isBumperPageEnabled,
                    shouldShowCloseWarning,
                    orientation,
                    shouldAutomaticallyCloseAtEnd,
                    shouldMuteOnStart,
                    shouldShowSmallClickButton,
                    playback,
                    closeButtonState);

            session.prepareSession(() -> {

                performanceMetrics.setSession(session);
                performanceMetrics.startTimingForLoadTime();

                // after session is OK - start loading
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
                            Log.w("AwesomeAds", "Video Ad listener not implemented. Event would have been adFailedToLoad");
                        }
                    } else {
                        // find out the real valid
                        boolean isValid = response.isValid();
                        SAAd first = isValid ? response.ads.get(0) : null;
                        isValid = first != null
                                && (first.creative.details.media.isDownloaded || first.isVpaid);

                        // put the correct value
                        if (isValid) {
                            first.openRtbPartnerId = openRtbPartnerId;

                            performanceMetrics.trackLoadTime(first);
                            ads.put(placementId, first);
                        }
                        // remove existing
                        else {
                            ads.remove(placementId);
                        }

                        // call listener(s)
                        if (listener != null) {
                            SAEvent eventToSend = response.isValid() ? SAEvent.adLoaded : SAEvent.adEmpty;
                            listener.onEvent(placementId, eventToSend);
                            Log.d("SAVideoAd", "Event callback: " + eventToSend);
                        } else {
                            Log.w("AwesomeAds", "Video Ad listener not implemented. Event would have been either adLoaded or adEmpty");
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
                Log.w("AwesomeAds", "Video Ad listener not implemented. Event would have been adAlreadyLoaded");
            }
        }
    }

    private static SASession getNewSession(Context context) {
        SASession session = new SASession(context);
        session.setTestMode(isTestingEnabled);
        session.setConfiguration(configuration);
        session.setPos(SARTBPosition.FULLSCREEN);
        session.setPlaybackMethod(shouldMuteOnStart ? WITH_SOUND_OFF_SCREEN : WITH_SOUND_ON_SCREEN);
        session.setInstl(SARTBInstl.FULLSCREEN);
        session.setSkip(closeButtonState.isVisible() ? SARTBSkip.SKIP : SARTBSkip.NO_SKIP);
        session.setStartDelay(getPlaybackMode());

        try {
            SAUtils.SASize size = SAUtils.getRealScreenSize((Activity) context, false);
            session.setWidth(size.width);
            session.setHeight(size.height);
        } catch (Exception e) {
            // do nothing
        }

        return session;
    }

    public static boolean hasAdAvailable(int placementId) {
        Object object = ads.get(placementId);
        return object instanceof SAAd;
    }

    public static SAAd getAd(int placementId) {
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

    public static void play(final int placementId, final Context context) {

        // get the generic Object
        Object generic = ads.get(placementId);

        // if notnull & instance of SAAd
        if (generic instanceof SAAd) {

            // try to get the ad that fits the placement id
            SAAd adL = (SAAd) generic;

            // setup eventing
            SASession session = getNewSession(context);
            events.setAd(session, adL);

            // try to start the activity
            if (adL.creative.format == SACreativeFormat.video && context != null) {
                if (adL.isVpaid) {
                    // Start the timer
                    performanceMetrics.startTimerForRenderTime();
                    ads.remove(placementId);
                    Intent intent = SAManagedAdActivity.newInstance(context, placementId, adL, adL.creative.details.tag);
                    ManagedAdConfig config = new ManagedAdConfig(
                            isParentalGateEnabled,
                            isBumperPageEnabled || adL.creative.bumper,
                            shouldShowCloseWarning,
                            isBackButtonEnabled,
                            shouldAutomaticallyCloseAtEnd,
                            closeButtonState,
                            configuration);
                    intent.putExtra(SAManagedAdActivity.CONFIG_KEY, config);

                    context.startActivity(intent);
                } else {
                    if (adL.creative.details.media.path == null || !adL.creative.details.media.isDownloaded) {
                        sendAdFailedToShow(placementId);
                        ads.remove(placementId);
                        return;
                    }

                    try {
                        Uri.fromFile(new File(adL.creative.details.media.path));
                    } catch (Throwable error) {
                        sendAdFailedToShow(placementId);
                        return;
                    }

                    // create intent
                    Intent intent = new Intent(context, SAVideoActivity.class);

                    VideoConfig videoConfig = new VideoConfig(
                            adL.isPadlockVisible,
                            isParentalGateEnabled,
                            isBumperPageEnabled || adL.creative.bumper,
                            shouldShowSmallClickButton,
                            isBackButtonEnabled,
                            shouldAutomaticallyCloseAtEnd,
                            shouldMuteOnStart,
                            closeButtonState,
                            closeButtonDelayTimer,
                            shouldShowCloseWarning,
                            orientation);

                    intent.putExtra("ad", adL);
                    intent.putExtra("config", videoConfig);

                    // clear ad - meaning that it's been played
                    ads.remove(placementId);

                    // start new activity
                    context.startActivity(intent);
                }
            } else {
                sendAdFailedToShow(placementId);
            }
        } else {
            sendAdFailedToShow(placementId);
        }
    }

    private static void sendAdFailedToShow(int placementId) {
        if (listener != null) {
            listener.onEvent(placementId, SAEvent.adFailedToShow);
        } else {
            Log.w("AwesomeAds", "Video Ad listener not implemented. Event would have been adFailedToShow");
        }
    }

    public static void setAd(SAAd ad) {
        if (ad != null && ad.isValid()) {
            ads.put(ad.placementId, ad);
        }
    }

    public static void removeAd(int placementId) {
        ads.remove(placementId);
    }

    /**********************************************************************************************
     * Setters & Getters
     **********************************************************************************************/

    public static void setListener(SAInterface value) {
        listener = value; // != null ? value : listener;
    }

    public static void enableParentalGate() {
        setParentalGate(true);
    }

    public static void disableParentalGate() {
        setParentalGate(false);
    }

    public static void enableBumperPage() {
        setBumperPage(true);
    }

    public static void disableBumperPage() {
        setBumperPage(false);
    }

    public static void enableTestMode() {
        setTestMode(true);
    }

    public static void disableTestMode() {
        setTestMode(false);
    }

    public static void setConfigurationProduction() {
        setConfiguration(SAConfiguration.PRODUCTION);
    }

    public static void setConfigurationStaging() {
        setConfiguration(SAConfiguration.STAGING);
    }

    public static void setConfigurationDev() {
        setConfiguration(SAConfiguration.DEV);
    }

    public static void setOrientationAny() {
        setOrientation(SAOrientation.ANY);
    }

    public static void setOrientationPortrait() {
        setOrientation(SAOrientation.PORTRAIT);
    }

    public static void setOrientationLandscape() {
        setOrientation(SAOrientation.LANDSCAPE);
    }

    public static void setPlaybackMode(SARTBStartDelay mode) {
        playback = mode;
    }

    public static void enableBackButton() {
        setBackButton(true);
    }

    public static void disableBackButton() {
        setBackButton(false);
    }

    public static void enableCloseButton() {
        setCloseButton(true);
    }

    public static void disableCloseButton() {
        setCloseButton(false);
    }

    public static void enableCloseButtonWithWarning() {
        setCloseButton(true);
        setCloseButtonWarning(true);
    }

    /**
     * Method that enables the close button to display immediately without a delay.
     * WARNING: this will allow users to close the ad before the viewable tracking event is fired
     * and should only be used if you explicitly want this behaviour over consistent tracking.
     */
    public static void enableCloseButtonNoDelay() {
        closeButtonState = CloseButtonState.VisibleImmediately.INSTANCE;
    }

    public static void enableCloseAtEnd() {
        setCloseAtEnd(true);
    }

    public static void disableCloseAtEnd() {
        setCloseAtEnd(false);
    }

    public static void enableSmallClickButton() {
        setSmallClick(true);
    }

    public static void disableSmallClickButton() {
        setSmallClick(false);
    }

    public static SAInterface getListener() {
        return listener;
    }

    public static boolean getIsTestEnabled() {
        return isTestingEnabled;
    }

    public static boolean getIsParentalGateEnabled() {
        return isParentalGateEnabled;
    }

    public static boolean getIsBumperPageEnabled() {
        return isBumperPageEnabled;
    }

    public static boolean getShouldShowCloseButton() {
        return closeButtonState.isVisible();
    }

    public static SARTBStartDelay getPlaybackMode() {
        return playback;
    }

    public static boolean getShouldAutomaticallyCloseAtEnd() {
        return shouldAutomaticallyCloseAtEnd;
    }

    public static boolean getShouldShowSmallClickButton() {
        return shouldShowSmallClickButton;
    }

    public static SAOrientation getOrientation() {
        return orientation;
    }

    public static SAConfiguration getConfiguration() {
        return configuration;
    }

    public static boolean getIsBackButtonEnabled() {
        return isBackButtonEnabled;
    }

    public static void setParentalGate(boolean value) {
        isParentalGateEnabled = value;
    }

    public static void setBumperPage(boolean value) {
        isBumperPageEnabled = value;
    }

    public static void setTestMode(boolean value) {
        isTestingEnabled = value;
    }

    public static void setConfiguration(SAConfiguration value) {
        configuration = value;
    }

    public static void setOrientation(SAOrientation value) {
        orientation = value;
    }

    public static void setBackButton(boolean value) {
        isBackButtonEnabled = value;
    }

    public static void setCloseButton(boolean value) {
        closeButtonState = value ? CloseButtonState.VisibleWithDelay.INSTANCE : CloseButtonState.Hidden.INSTANCE;
    }

    /**
     * Enables showing the close button after a set delay. This overrides any close button configuration
     * that have been called before.
     * @param delay the amount of delay in milliseconds.
     */
    public static void setCloseButtonWithDelay(long delay) {
        closeButtonDelayTimer = delay;
        closeButtonState = new CloseButtonState.Custom(closeButtonDelayTimer);
    }

    public static void setCloseButtonWarning(boolean value) {
        shouldShowCloseWarning = value;
    }

    public static void setCloseAtEnd(boolean value) {
        shouldAutomaticallyCloseAtEnd = value;
    }

    public static void setSmallClick(boolean value) {
        shouldShowSmallClickButton = value;
    }

    public static SAEvents getEvents() {
        return events;
    }

    public static SAPerformanceMetrics getPerformanceMetrics() {
        return performanceMetrics;
    }

    public static void setMuteOnStart(boolean mute) {
        shouldMuteOnStart = mute;
    }

    public static void enableMuteOnStart() {
        setMuteOnStart(true);
    }

    public static void disableMuteOnStart() {
        setMuteOnStart(false);
    }

    @VisibleForTesting
    private static void clearCache() {
        ads.clear();
    }
}
