/*
 * @Copyright: SADefaults Trading Limited 2017
 * @Author: Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.publisher.unity;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.lib.sasession.defines.SARTBStartDelay;
import tv.superawesome.plugins.publisher.unity.util.SAJsonUtil;
import tv.superawesome.sdk.publisher.SAEvent;
import tv.superawesome.sdk.publisher.SAInterface;
import tv.superawesome.sdk.publisher.SAOrientation;
import tv.superawesome.sdk.publisher.SAVideoAd;
import tv.superawesome.sdk.publisher.state.CloseButtonState;

/**
 * Class that holds a number of static methods used to communicate with Unity
 */
public class SAUnityVideoAd {

    private static final String unityName = "SAVideoAd";

    /**
     * Method that creates a new Video Ad (from Unity)
     */
    public static void SuperAwesomeUnitySAVideoAdCreate() {
        SAVideoAd.setListener((SAInterface) (placementId, event) -> {
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
                case adPaused:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adPaused.toString());
                    break;
                case adPlaying:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adPlaying.toString());
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
        SuperAwesomeUnitySAVideoAdLoad(
                context,
                placementId,
                configuration,
                test,
                playback,
                null,
                encodedOptions
        );
    }

    /**
     * Method that loads a new Video Ad (from Unity)
     */
    public static void SuperAwesomeUnitySAVideoAdLoad(Context context,
                                                      int placementId,
                                                      int configuration,
                                                      boolean test,
                                                      int playback,
                                                      String openRtbPartnerId,
                                                      String encodedOptions) {
        SAVideoAd.setTestMode(test);
        SAVideoAd.setConfiguration(SAConfiguration.fromValue(configuration));
        SAVideoAd.setPlaybackMode(SARTBStartDelay.fromValue(playback));

        if (encodedOptions != null && !encodedOptions.isEmpty()) {
            try {
                SAVideoAd.load(
                        placementId,
                        context,
                        openRtbPartnerId,
                        SAJsonUtil.JSONtoMap(new JSONObject(encodedOptions))
                );
            } catch (JSONException e) {
                e.printStackTrace();
                // Fallback to loading without options
                SAVideoAd.load(placementId, context, openRtbPartnerId);
            }
        } else {
            SAVideoAd.load(placementId, context, openRtbPartnerId);
        }
    }

    /**
     * Method that checks to see if an ad is available for a video ad (from Unity)
     */
    public static boolean SuperAwesomeUnitySAVideoAdHasAdAvailable(int placementId) {
        return SAVideoAd.hasAdAvailable(placementId);
    }

    /**
     * Method that plays a new video ad (from Unity)
     */
    public static void SuperAwesomeUnitySAVideoAdPlay(Context context,
                                                      int placementId) {
        SAVideoAd.play(placementId, context);
    }

    /**
     * Method that apply settings (from Unity)
     */
    public static void SuperAwesomeUnitySAVideoAdApplySettings(
            boolean isParentalGateEnabled,
            boolean isBumperPageEnabled,
            int closeButtonState,
            double closeButtonDelay,
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
        SAVideoAd.setOrientation(SAOrientation.fromValue(orientation));
        SAVideoAd.setCloseButtonWarning(shouldShowCloseWarning);
        SAVideoAd.setTestMode(testModeEnabled);
        setCloseButtonState(closeButtonState, closeButtonDelay);
        SAVideoAd.setMuteOnStart(muteOnStart);
    }

    private static void setCloseButtonState(int closeButtonState, double delay) {

        CloseButtonState state = CloseButtonState.fromInt(closeButtonState, delay);

        if (state instanceof CloseButtonState.VisibleImmediately) {
            SAVideoAd.enableCloseButtonNoDelay();
        } else if (state instanceof CloseButtonState.VisibleWithDelay) {
            SAVideoAd.enableCloseButton();
        } else if (state instanceof CloseButtonState.Custom) {
            SAVideoAd.enableCloseButtonWithDelay(delay);
        } else if (state instanceof CloseButtonState.Hidden) {
            SAVideoAd.disableCloseButton();
        }
    }
}
