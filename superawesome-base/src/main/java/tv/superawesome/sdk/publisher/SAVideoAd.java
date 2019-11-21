package tv.superawesome.sdk.publisher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.saadloader.SALoaderInterface;
import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACreativeFormat;
import tv.superawesome.lib.samodelspace.saad.SAResponse;
import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.lib.sasession.defines.SARTBInstl;
import tv.superawesome.lib.sasession.defines.SARTBPlaybackMethod;
import tv.superawesome.lib.sasession.defines.SARTBPosition;
import tv.superawesome.lib.sasession.defines.SARTBSkip;
import tv.superawesome.lib.sasession.defines.SARTBStartDelay;
import tv.superawesome.lib.sasession.session.SASession;
import tv.superawesome.lib.sasession.session.SASessionInterface;
import tv.superawesome.lib.sautils.SAUtils;

public class SAVideoAd {

    // private vars w/ a public interface
    private static SAEvents events = new SAEvents();
    public static HashMap<Integer, Object> ads = new HashMap<>();
    private static SAInterface listener = new SAInterface() { @Override public void onEvent(int placementId, SAEvent event) {} };

    private static boolean isParentalGateEnabled            = SADefaults.defaultParentalGate();
    private static boolean isBumperPageEnabled              = SADefaults.defaultBumperPage();
    private static boolean shouldShowCloseButton            = SADefaults.defaultCloseButton();
    private static boolean shouldAutomaticallyCloseAtEnd    = SADefaults.defaultCloseAtEnd();
    private static boolean shouldShowSmallClickButton       = SADefaults.defaultSmallClick();
    private static boolean isTestingEnabled                 = SADefaults.defaultTestMode();
    private static boolean isBackButtonEnabled              = SADefaults.defaultBackButton();
    private static boolean isMoatLimitingEnabled            = SADefaults.defaultMoatLimitingState();
    private static SAOrientation orientation                = SADefaults.defaultOrientation();
    private static SAConfiguration configuration            = SADefaults.defaultConfiguration();
    private static SARTBStartDelay playback                 = SADefaults.defaultPlaybackMode();

    public static void load (final int placementId, final Context context) {

        // very late init of the AwesomeAds SDK
        try {
            AwesomeAds.init(((Activity)context).getApplication(), false);
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

            session.prepareSession(new SASessionInterface() {
                @Override
                public void didFindSessionReady() {

                    // after session is OK - start loading
                    loader.loadAd(placementId, session, new SALoaderInterface() {
                        @Override
                        public void saDidLoadAd(SAResponse response) {

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
                            }
                            else {
                                // find out the real valid
                                boolean isValid = response.isValid();
                                SAAd first = isValid ? response.ads.get(0) : null;
                                isValid = first != null && first.creative.details.media.isDownloaded;

                                // put the correct value
                                if (isValid) {
                                    ads.put(placementId, first);
                                }
                                // remove existing
                                else {
                                    ads.remove(placementId);
                                }

                                // call listener(s)
                                if (listener != null) {
                                    listener.onEvent(placementId, isValid ? SAEvent.adLoaded : SAEvent.adEmpty);
                                } else {
                                    Log.w("AwesomeAds", "Video Ad listener not implemented. Event would have been either adLoaded or adEmpty");
                                }
                            }
                        }
                    });
                }
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
        SASession session = new SASession (context);
        session.setTestMode(isTestingEnabled);
        session.setConfiguration(configuration);
        session.setVersion(SAVersion.getSDKVersion());
        session.setPos(SARTBPosition.FULLSCREEN);
        session.setPlaybackMethod(SARTBPlaybackMethod.WITH_SOUND_ON_SCREEN);
        session.setInstl(SARTBInstl.FULLSCREEN);
        session.setSkip(shouldShowCloseButton ? SARTBSkip.SKIP : SARTBSkip.NO_SKIP);
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

    public static boolean hasAdAvailable (int placementId) {
        Object object = ads.get(placementId);
        return object != null && object instanceof SAAd;
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

    public static void play (final int placementId, final Context context) {

        // get the generic Object
        Object generic = ads.get(placementId);

        // if notnull & instance of SAAd
        if (generic != null && generic instanceof SAAd) {

            // try to get the ad that fits the placement id
            SAAd adL = (SAAd) generic;

            // try to start the activity
            if (adL.creative.format == SACreativeFormat.video && context != null) {

                // setup eventing
                SASession session = getNewSession(context);
                events.setAd(session, adL);
                if (!isMoatLimitingEnabled) {
                    events.disableMoatLimiting();
                }

                // create intent
                Intent intent = new Intent(context, SAVideoActivity.class);

                Config config = new Config(
                        adL.isPadlockVisible,
                        isParentalGateEnabled,
                        isBumperPageEnabled || adL.creative.bumper,
                        shouldShowSmallClickButton,
                        isBackButtonEnabled,
                        shouldAutomaticallyCloseAtEnd,
                        shouldShowCloseButton,
                        orientation);

                intent.putExtra("ad", adL);
                intent.putExtra("config", config);

                // clear ad - meaning that it's been played
                ads.remove(placementId);

                // start new activity
                context.startActivity(intent);

            } else {
                if (listener != null) {
                    listener.onEvent(placementId, SAEvent.adFailedToShow);
                } else {
                    Log.w("AwesomeAds", "Video Ad listener not implemented. Event would have been adFailedToShow");
                }
            }
        }
        else {
            if (listener != null) {
                listener.onEvent(placementId, SAEvent.adFailedToShow);
            } else {
                Log.w("AwesomeAds", "Video Ad listener not implemented. Event would have been adFailedToShow");
            }
        }
    }

    public static void setAd (SAAd ad) {
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

    public static void setConfigurationProduction () {
        setConfiguration(SAConfiguration.PRODUCTION);
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

    public static void setPlaybackMode (SARTBStartDelay mode) {
        playback = mode;
    }

    public static void enableBackButton () {
        setBackButton(true);
    }

    public static void disableBackButton () {
        setBackButton(false);
    }

    public static void enableCloseButton () {
        setCloseButton(true);
    }

    public static void disableCloseButton () {
        setCloseButton(false);
    }

    public static void enableCloseAtEnd () {
        setCloseAtEnd(true);
    }

    public static void disableCloseAtEnd () {
        setCloseAtEnd(false);
    }

    public static void enableSmallClickButton () {
        setSmallClick(true);
    }

    public static void disableSmallClickButton () {
        setSmallClick(false);
    }

    public static SAInterface getListener () {
        return listener;
    }

    public  static boolean getIsTestEnabled () {
        return isTestingEnabled;
    }

    public  static boolean getIsParentalGateEnabled () {
        return isParentalGateEnabled;
    }

    public  static boolean getIsBumperPageEnabled () {
        return isBumperPageEnabled;
    }

    public  static boolean getShouldShowCloseButton () {
        return shouldShowCloseButton;
    }

    public  static SARTBStartDelay getPlaybackMode () {
        return playback;
    }

    public  static boolean getShouldAutomaticallyCloseAtEnd () {
        return shouldAutomaticallyCloseAtEnd;
    }

    public  static boolean getMoatLimitingState () { return isMoatLimitingEnabled; }

    public  static boolean getShouldShowSmallClickButton () {
        return shouldShowSmallClickButton;
    }

    public  static SAOrientation getOrientation () {
        return orientation;
    }

    public  static SAConfiguration getConfiguration () {
        return configuration;
    }

    public  static boolean getIsBackButtonEnabled () {
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

    public static void setConfiguration (SAConfiguration value) {
        configuration = value;
    }

    public static void setOrientation (SAOrientation value) {
        orientation = value;
    }

    public static void setBackButton (boolean value) {
        isBackButtonEnabled = value;
    }

    public static void setCloseButton (boolean value) {
        shouldShowCloseButton = value;
    }

    public static void setCloseAtEnd (boolean value) {
        shouldAutomaticallyCloseAtEnd = value;
    }

    public static void setSmallClick (boolean value) {
        shouldShowSmallClickButton = value;
    }

    public static void disableMoatLimiting () {
        isMoatLimitingEnabled = false;
    }

    public static SAEvents getEvents() {
        return events;
    }
}
