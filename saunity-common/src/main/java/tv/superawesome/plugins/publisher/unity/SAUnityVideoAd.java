package tv.superawesome.plugins.publisher.unity;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.plugins.publisher.unity.util.OrientationExtensionsKt;
import tv.superawesome.plugins.publisher.unity.util.SAJsonUtil;
import tv.superawesome.sdk.publisher.models.AdRequest;
import tv.superawesome.sdk.publisher.models.CloseButtonState;
import tv.superawesome.sdk.publisher.models.Orientation;
import tv.superawesome.sdk.publisher.models.SAEvent;
import tv.superawesome.sdk.publisher.ui.video.SAVideoAd;

/**
 * Class that holds a number of static methods used to communicate with Unity
 */
public class SAUnityVideoAd {

    private static final String unityName = "SAVideoAd";

    /**
     * Method that creates a new Video Ad (from Unity)
     */
    public static void SuperAwesomeUnitySAVideoAdCreate(Context context) {
        SAVideoAd.setListener((placementId, event) -> {
            switch (event) {
                case adLoaded:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adLoaded.toString());
                    break;
                case adEmpty:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adEmpty.toString());
                    break;
                case adFailedToLoad:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adFailedToLoad.toString());
                    break;
                case adAlreadyLoaded:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adAlreadyLoaded.toString());
                    break;
                case adShown:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adShown.toString());
                    break;
                case adFailedToShow:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adFailedToShow.toString());
                    break;
                case adClicked:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adClicked.toString());
                    break;
                case adEnded:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adEnded.toString());
                    break;
                case adClosed:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adClosed.toString());
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
                                                      int playback,
                                                      String encodedOptions) {
        SAVideoAd.setTestMode(test);
        setPlayback(playback);

        if (encodedOptions != null && !encodedOptions.isEmpty()) {
            try {
                SAVideoAd.load(
                        placementId,
                        context,
                        SAJsonUtil.JSONtoMap(new JSONObject(encodedOptions))
                );
            } catch (JSONException e) {
                e.printStackTrace();
                // Fallback to loading without options
                SAVideoAd.load(placementId, context);
            }
        } else {
            SAVideoAd.load(placementId, context);
        }
    }

    /**
     * Method that checks to see if an ad is available for a video ad (from Unity)
     */
    public static boolean SuperAwesomeUnitySAVideoAdHasAdAvailable(Context context,
                                                                   int placementId) {
        return SAVideoAd.hasAdAvailable(placementId);
    }

    /**
     * Method that plays a new video ad (from Unity)
     */
    public static void SuperAwesomeUnitySAVideoAdPlay(Context context,
                                                      int placementId,
                                                      boolean isParentalGateEnabled,
                                                      boolean isBumperPageEnabled,
                                                      int closeButtonState,
                                                      boolean shouldShowSmallClickButton,
                                                      boolean shouldAutomaticallyCloseAtEnd,
                                                      int orientation,
                                                      boolean isBackButtonEnabled,
                                                      boolean shouldShowCloseWarning,
                                                      boolean muteOnStart) {
        SAVideoAd.setParentalGate(isParentalGateEnabled);
        SAVideoAd.setBumperPage(isBumperPageEnabled);
        SAVideoAd.setCloseAtEnd(shouldAutomaticallyCloseAtEnd);
        SAVideoAd.setSmallClick(shouldShowSmallClickButton);
        SAVideoAd.setBackButton(isBackButtonEnabled);
        setOrientation(orientation);
        SAVideoAd.setCloseButtonWarning(shouldShowCloseWarning);
        setCloseButtonState(closeButtonState);
        SAVideoAd.setMuteOnStart(muteOnStart);

        SAVideoAd.play(placementId, context);
    }

    /**
     * Method that apply settings (from Unity)
     */
    public static void SuperAwesomeUnitySAVideoAdApplySettings(
            boolean isParentalGateEnabled,
            boolean isBumperPageEnabled,
            int closeButtonState,
            boolean shouldShowSmallClickButton,
            boolean shouldAutomaticallyCloseAtEnd,
            int orientation,
            boolean isBackButtonEnabled,
            boolean shouldShowCloseWarning,
            boolean testModeEnabled,
            boolean muteOnStart) {
        SAVideoAd.setParentalGate(isParentalGateEnabled);
        SAVideoAd.setBumperPage(isBumperPageEnabled);
        SAVideoAd.setCloseAtEnd(shouldAutomaticallyCloseAtEnd);
        SAVideoAd.setSmallClick(shouldShowSmallClickButton);
        SAVideoAd.setBackButton(isBackButtonEnabled);
        setOrientation(orientation);
        SAVideoAd.setCloseButtonWarning(shouldShowCloseWarning);
        SAVideoAd.setTestMode(testModeEnabled);
        setCloseButtonState(closeButtonState);
        SAVideoAd.setMuteOnStart(muteOnStart);
    }

    private static void setPlayback(int playback) {
        AdRequest.StartDelay value = AdRequest.StartDelay.fromValue(playback);
        if (value != null) {
            SAVideoAd.setPlaybackMode(value);
        }
    }

    private static void setOrientation(int orientation) {
        Orientation value = OrientationExtensionsKt.getOrientationFromValue(orientation);
        if (value != null) {
            SAVideoAd.setOrientation(value);
        }
    }

    private static void setCloseButtonState(int closeButtonState) {
        switch (CloseButtonState.fromInt(closeButtonState)) {
            case Hidden:
                SAVideoAd.disableCloseButton();
                break;
            case VisibleImmediately:
                SAVideoAd.enableCloseButtonNoDelay();
                break;
            case VisibleWithDelay:
                SAVideoAd.enableCloseButton();
                break;
        }
    }
}
