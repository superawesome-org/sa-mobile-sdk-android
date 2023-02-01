/*
 * @Copyright: SADefaults Trading Limited 2017
 * @Author: Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.publisher.unity;

import android.content.Context;

import tv.superawesome.sdk.publisher.common.models.Orientation;
import tv.superawesome.sdk.publisher.common.models.SAEvent;
import tv.superawesome.sdk.publisher.common.ui.interstitial.SAInterstitialAd;

/**
 * Class that holds a number of static methods used to communicate with Unity
 */
public class SAUnityInterstitialAd {

    private static final String unityName = "SAInterstitialAd";

    /**
     * Method that creates a new Interstitial Ad (from Unity)
     */
    public static void SuperAwesomeUnitySAInterstitialAdCreate(Context context) {
        SAInterstitialAd.INSTANCE.setListener((placementId, event) -> {
            switch (event) {
                case AdLoaded:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdLoaded.getValue());
                    break;
                case AdEmpty:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdEmpty.getValue());
                    break;
                case AdFailedToLoad:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdFailedToLoad.getValue());
                    break;
                case AdAlreadyLoaded:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdAlreadyLoaded.getValue());
                    break;
                case AdShown:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdShown.getValue());
                    break;
                case AdFailedToShow:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdFailedToShow.getValue());
                    break;
                case AdClicked:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdClicked.getValue());
                    break;
                case AdEnded:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdEnded.getValue());
                    break;
                case AdClosed:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdClosed.getValue());
                    break;
            }
        });

    }

    /**
     * Method that loads a new Interstitial AD (from Unity)
     */
    public static void SuperAwesomeUnitySAInterstitialAdLoad(Context context, int placementId, int configuration, boolean test) {
        SAInterstitialAd.INSTANCE.setTestMode(test);
        SAInterstitialAd.INSTANCE.load(placementId, context);
    }

    /**
     * Method that checks to see if an ad is available for an interstitial ad (from Unity)
     */
    public static boolean SuperAwesomeUnitySAInterstitialAdHasAdAvailable(Context context, int placementId) {
        return SAInterstitialAd.INSTANCE.hasAdAvailable(placementId);
    }

    /**
     * Method that plays a new Interstitial Ad (from Unity)
     */
    public static void SuperAwesomeUnitySAInterstitialAdPlay(Context context,
                                                             int placementId,
                                                             boolean isParentalGateEnabled,
                                                             boolean isBumperPageEnabled,
                                                             int orientation,
                                                             boolean isBackButtonEnabled) {
        SAInterstitialAd.INSTANCE.setParentalGate(isParentalGateEnabled);
        SAInterstitialAd.INSTANCE.setBumperPage(isBumperPageEnabled);
        setOrientation(orientation);
        SAInterstitialAd.INSTANCE.setBackButton(isBackButtonEnabled);
        SAInterstitialAd.INSTANCE.play(placementId, context);
    }

    /**
     * Method that apply settings (from Unity)
     */
    public static void SuperAwesomeUnitySAInterstitialAdApplySettings(
            boolean isParentalGateEnabled,
            boolean isBumperPageEnabled,
            int orientation,
            boolean isBackButtonEnabled,
            boolean testModeEnabled) {
        SAInterstitialAd.INSTANCE.setParentalGate(isParentalGateEnabled);
        SAInterstitialAd.INSTANCE.setBumperPage(isBumperPageEnabled);
        SAInterstitialAd.INSTANCE.setBackButton(isBackButtonEnabled);
        setOrientation(orientation);
        SAInterstitialAd.INSTANCE.setTestMode(testModeEnabled);
    }

    private static void setOrientation(int orientation) {
        Orientation orientationInstance = Orientation.Companion.fromValue(orientation);
        if (orientationInstance != null) {
            SAInterstitialAd.INSTANCE.setOrientation(orientationInstance);
        }
    }
}
