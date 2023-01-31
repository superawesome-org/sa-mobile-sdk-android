/*
 * @Copyright: SADefaults Trading Limited 2017
 * @Author: Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.publisher.unity;

import android.content.Context;

import tv.superawesome.sdk.publisher.common.models.AdRequest;
import tv.superawesome.sdk.publisher.common.models.Orientation;
import tv.superawesome.sdk.publisher.common.models.SAEvent;
import tv.superawesome.sdk.publisher.common.ui.video.SAVideoAd;

/**
 * Class that holds a number of static methods used to communicate with Unity
 */
public class SAUnityVideoAd {

    private static final String unityName = "SAVideoAd";

    /**
     * Method that creates a new Video Ad (from Unity)
     */
    public static void SuperAwesomeUnitySAVideoAdCreate(Context context) {
        SAVideoAd.INSTANCE.setListener((placementId, event) -> {
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
     * Method that loads a new Video Ad (from Unity)
     */
    public static void SuperAwesomeUnitySAVideoAdLoad(Context context,
                                                      int placementId,
                                                      int configuration,
                                                      boolean test,
                                                      int playback) {
        SAVideoAd.INSTANCE.setTestMode(test);
        setStartDelay(playback);
        SAVideoAd.INSTANCE.load(placementId, context);
    }

    /**
     * Method that checks to see if an ad is available for a video ad (from Unity)
     */
    public static boolean SuperAwesomeUnitySAVideoAdHasAdAvailable(Context context,
                                                                   int placementId) {
        return SAVideoAd.INSTANCE.hasAdAvailable(placementId);
    }

    /**
     * Method that plays a new video ad (from Unity)
     */
    public static void SuperAwesomeUnitySAVideoAdPlay(Context context,
                                                      int placementId,
                                                      boolean isParentalGateEnabled,
                                                      boolean isBumperPageEnabled,
                                                      boolean shouldShowCloseButton,
                                                      boolean shouldShowSmallClickButton,
                                                      boolean shouldAutomaticallyCloseAtEnd,
                                                      int orientation,
                                                      boolean isBackButtonEnabled,
                                                      boolean shouldShowCloseWarning) {
        SAVideoAd.INSTANCE.setParentalGate(isParentalGateEnabled);
        SAVideoAd.INSTANCE.setBumperPage(isBumperPageEnabled);
        SAVideoAd.INSTANCE.setCloseAtEnd(shouldAutomaticallyCloseAtEnd);
        SAVideoAd.INSTANCE.setCloseButton(shouldShowCloseButton);
        SAVideoAd.INSTANCE.setSmallClick(shouldShowSmallClickButton);
        SAVideoAd.INSTANCE.setBackButton(isBackButtonEnabled);
        setOrientation(orientation);
        SAVideoAd.INSTANCE.setCloseButtonWarning(shouldShowCloseWarning);
        SAVideoAd.INSTANCE.play(placementId, context);
    }

    /**
     * Method that apply settings (from Unity)
     */
    public static void SuperAwesomeUnitySAVideoAdApplySettings(
            boolean isParentalGateEnabled,
            boolean isBumperPageEnabled,
            boolean shouldShowCloseButton,
            boolean shouldShowSmallClickButton,
            boolean shouldAutomaticallyCloseAtEnd,
            int orientation,
            boolean isBackButtonEnabled,
            boolean shouldShowCloseWarning,
            boolean testModeEnabled) {
        SAVideoAd.INSTANCE.setParentalGate(isParentalGateEnabled);
        SAVideoAd.INSTANCE.setBumperPage(isBumperPageEnabled);
        SAVideoAd.INSTANCE.setCloseAtEnd(shouldAutomaticallyCloseAtEnd);
        SAVideoAd.INSTANCE.setCloseButton(shouldShowCloseButton);
        SAVideoAd.INSTANCE.setSmallClick(shouldShowSmallClickButton);
        SAVideoAd.INSTANCE.setBackButton(isBackButtonEnabled);
        setOrientation(orientation);
        SAVideoAd.INSTANCE.setCloseButtonWarning(shouldShowCloseWarning);
        SAVideoAd.INSTANCE.setTestMode(testModeEnabled);
    }

    private static void setOrientation(int orientation) {
        Orientation orientationInstance = Orientation.Companion.fromValue(orientation);
        if (orientationInstance != null) {
            SAVideoAd.INSTANCE.setOrientation(orientationInstance);
        }
    }

    private static void setStartDelay(int startDelay) {
        AdRequest.StartDelay delay = AdRequest.StartDelay.Companion.fromValue(startDelay);
        if (delay != null) {
            SAVideoAd.INSTANCE.setPlaybackMode(delay);
        }
    }
}
