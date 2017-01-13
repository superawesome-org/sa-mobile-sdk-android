/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.unity;

import android.content.Context;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAOrientation;

/**
 * Class that holds a number of static methods used to communicate with Unity
 */
public class SAUnityInterstitialAd {

    private static final String unityName = "SAInterstitialAd";

    /**
     * Method that creates a new Interstitial Ad (from Unity)
     */
    public static void SuperAwesomeUnitySAInterstitialAdCreate (Context context) {
        SAInterstitialAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: SAUnityCallback.sendAdCallback(unityName, placementId, "adLoaded"); break;
                    case adFailedToLoad: SAUnityCallback.sendAdCallback(unityName, placementId, "adFailedToLoad"); break;
                    case adShown: SAUnityCallback.sendAdCallback(unityName, placementId, "adShown"); break;
                    case adFailedToShow: SAUnityCallback.sendAdCallback(unityName, placementId, "adFailedToShow"); break;
                    case adClicked: SAUnityCallback.sendAdCallback(unityName, placementId, "adClicked"); break;
                    case adClosed: SAUnityCallback.sendAdCallback(unityName, placementId, "adClosed");break;
                }
            }
        });

    }

    /**
     * Method that loads a new Interstitial AD (from Unity)
     */
    public static void SuperAwesomeUnitySAInterstitialAdLoad (Context context, int placementId, int configuration, boolean test) {
        SAInterstitialAd.setTestMode(test);
        SAInterstitialAd.setConfiguration(SAConfiguration.fromValue(configuration));
        SAInterstitialAd.load(placementId, context);
    }

    /**
     * Method that checks to see if an ad is available for an interstitial ad (from Unity)
     */
    public static boolean SuperAwesomeUnitySAInterstitialAdHasAdAvailable (Context context, int placementId) {
        return SAInterstitialAd.hasAdAvailable(placementId);
    }

    /**
     * Method that plays a new Interstitial Ad (from Unity)
     */
    public static void SuperAwesomeUnitySAInterstitialAdPlay (Context context, int placementId, boolean isParentalGateEnabled, int orientation, boolean isBackButtonEnabled) {
        SAInterstitialAd.setParentalGate(isParentalGateEnabled);
        SAInterstitialAd.setOrientation(SAOrientation.fromValue(orientation));
        SAInterstitialAd.setBackButton(isBackButtonEnabled);
        SAInterstitialAd.play(placementId, context);
    }

}
