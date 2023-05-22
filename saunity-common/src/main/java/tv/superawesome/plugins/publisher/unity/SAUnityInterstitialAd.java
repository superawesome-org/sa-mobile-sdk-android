package tv.superawesome.plugins.publisher.unity;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.plugins.publisher.unity.util.SAJsonUtil;
import tv.superawesome.sdk.publisher.common.models.CloseButtonState;
import tv.superawesome.sdk.publisher.common.models.Orientation;
import tv.superawesome.sdk.publisher.common.models.SAEvent;
import tv.superawesome.sdk.publisher.common.models.SAInterface;
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
            }
        });
    }

    /**
     * Method that loads a new Interstitial AD (from Unity)
     */
    public static void SuperAwesomeUnitySAInterstitialAdLoad(
            Context context,
            int placementId,
            int configuration,
            boolean test,
            String encodedOptions) {
        SAInterstitialAd.setTestMode(test);

        if (encodedOptions != null && !encodedOptions.isEmpty()) {
            try {
                SAInterstitialAd.load(
                        placementId,
                        context,
                        SAJsonUtil.JSONtoMap(new JSONObject(encodedOptions))
                );
            } catch (JSONException e) {
                e.printStackTrace();
                // Fallback to loading without options
                SAInterstitialAd.load(placementId, context);
            }
        } else {
            SAInterstitialAd.load(placementId, context);
        }
    }

    /**
     * Method that checks to see if an ad is available for an interstitial ad (from Unity)
     */
    public static boolean SuperAwesomeUnitySAInterstitialAdHasAdAvailable(Context context, int placementId) {
        return SAInterstitialAd.hasAdAvailable(placementId);
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
        SAInterstitialAd.setParentalGate(isParentalGateEnabled);
        SAInterstitialAd.setBumperPage(isBumperPageEnabled);
        setOrientation(orientation);
        SAInterstitialAd.setBackButton(isBackButtonEnabled);
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
            int closeButtonState) {
        SAInterstitialAd.setParentalGate(isParentalGateEnabled);
        SAInterstitialAd.setBumperPage(isBumperPageEnabled);
        SAInterstitialAd.setBackButton(isBackButtonEnabled);
        setOrientation(orientation);
        SAInterstitialAd.setTestMode(testModeEnabled);
        setCloseButtonState(closeButtonState);
    }

    private static void setOrientation(int orientation) {
        Orientation value = Orientation.fromValue(orientation);
        if (value != null) {
            SAInterstitialAd.setOrientation(value);
        }
    }

    private static void setCloseButtonState(int closeButtonState) {
        switch (CloseButtonState.fromInt(closeButtonState)) {
            case Hidden:
                // Do nothing as Interstitial does not support hidden close button
                break;
            case VisibleImmediately:
                SAInterstitialAd.enableCloseButtonNoDelay();
                break;
            case VisibleWithDelay:
                SAInterstitialAd.enableCloseButton();
                break;
        }
    }
}
