/*
 * @Copyright: SADefaults Trading Limited 2017
 * @Author: Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.publisher.unity;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.plugins.publisher.unity.util.SAJsonUtil;
import tv.superawesome.sdk.publisher.SAEvent;
import tv.superawesome.sdk.publisher.SAInterface;
import tv.superawesome.sdk.publisher.SAInterstitialAd;
import tv.superawesome.sdk.publisher.SAOrientation;
import tv.superawesome.sdk.publisher.state.CloseButtonState;

/**
 * Class that holds a number of static methods used to communicate with Unity
 */
public class SAUnityInterstitialAd {

    private static final String unityName = "SAInterstitialAd";

    /**
     * Method that creates a new Interstitial Ad (from Unity)
     */
    public static void SuperAwesomeUnitySAInterstitialAdCreate() {
        SAInterstitialAd.setListener((SAInterface) (placementId, event) -> {
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
     * Method that loads a new Interstitial AD (from Unity)
     */
    public static void SuperAwesomeUnitySAInterstitialAdLoad(Context context,
                                                             int placementId,
                                                             int configuration,
                                                             boolean test,
                                                             String openRtbPartnerId,
                                                             String encodedOptions) {
        SAInterstitialAd.setTestMode(test);
        SAInterstitialAd.setConfiguration(SAConfiguration.fromValue(configuration));

        if (encodedOptions != null && !encodedOptions.isEmpty()) {
            try {
                SAInterstitialAd.load(
                        placementId,
                        context,
                        openRtbPartnerId,
                        SAJsonUtil.JSONtoMap(new JSONObject(encodedOptions))
                );
            } catch (JSONException e) {
                e.printStackTrace();
                // Fallback to loading without options
                SAInterstitialAd.load(placementId, context, openRtbPartnerId);
            }
        } else {
            SAInterstitialAd.load(placementId, context, openRtbPartnerId);
        }
    }

    /**
     * Method that loads a new Interstitial AD (from Unity)
     */
    public static void SuperAwesomeUnitySAInterstitialAdLoad(Context context,
                                                             int placementId,
                                                             int configuration,
                                                             boolean test,
                                                             String encodedOptions) {
        SuperAwesomeUnitySAInterstitialAdLoad(
                context,
                placementId,
                configuration,
                test,
                null,
                encodedOptions
        );
    }

    /**
     * Method that checks to see if an ad is available for an interstitial ad (from Unity)
     */
    public static boolean SuperAwesomeUnitySAInterstitialAdHasAdAvailable(int placementId) {
        return SAInterstitialAd.hasAdAvailable(placementId);
    }

    /**
     * Method that plays a new Interstitial Ad (from Unity)
     */
    public static void SuperAwesomeUnitySAInterstitialAdPlay(Context context,
                                                             int placementId) {
        SAInterstitialAd.play(placementId, context);
    }

    /**
     * Method that apply settings (from Unity)
     */
    public static void SuperAwesomeUnitySAInterstitialAdApplySettings(
            boolean isParentalGateEnabled,
            boolean isBumperPageEnabled,
            int orientation,
            boolean isBackButtonEnabled,
            boolean testModeEnabled,
            int closeButtonState,
            double closeButtonDelay) {
        SAInterstitialAd.setParentalGate(isParentalGateEnabled);
        SAInterstitialAd.setBumperPage(isBumperPageEnabled);
        SAInterstitialAd.setBackButton(isBackButtonEnabled);
        SAInterstitialAd.setOrientation(SAOrientation.fromValue(orientation));
        SAInterstitialAd.setTestMode(testModeEnabled);
        setCloseButtonState(closeButtonState, closeButtonDelay);
    }

    private static void setCloseButtonState(int closeButtonState, double delay) {

        CloseButtonState state = CloseButtonState.fromInt(closeButtonState, delay);

        if (state instanceof CloseButtonState.VisibleImmediately) {
            SAInterstitialAd.enableCloseButtonNoDelay();
        } else if (state instanceof CloseButtonState.VisibleWithDelay) {
            SAInterstitialAd.enableCloseButton();
        } else if (state instanceof CloseButtonState.Custom) {
            SAInterstitialAd.enableCloseButtonWithDelay(delay);
        } else if (state instanceof CloseButtonState.Hidden) {
            SAInterstitialAd.enableCloseButton();
        }
    }
}
