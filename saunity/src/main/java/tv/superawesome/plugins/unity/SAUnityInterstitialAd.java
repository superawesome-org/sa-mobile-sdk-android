package tv.superawesome.plugins.unity;

import android.content.Context;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAOrientation;

public class SAUnityInterstitialAd {

    private static final String unityName = "SAInterstitialAd";

    public static void SuperAwesomeUnitySAInterstitialAdCreate (Context context) {
        SAInterstitialAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: SAUnityCallback.sendToUnity(unityName, placementId, "adLoaded"); break;
                    case adFailedToLoad: SAUnityCallback.sendToUnity(unityName, placementId, "adFailedToLoad"); break;
                    case adShown: SAUnityCallback.sendToUnity(unityName, placementId, "adShown"); break;
                    case adFailedToShow: SAUnityCallback.sendToUnity(unityName, placementId, "adFailedToShow"); break;
                    case adClicked: SAUnityCallback.sendToUnity(unityName, placementId, "adClicked"); break;
                    case adClosed: SAUnityCallback.sendToUnity(unityName, placementId, "adClosed");break;
                }
            }
        });

    }

    public static void SuperAwesomeUnitySAInterstitialAdLoad (Context context, int placementId, int configuration, boolean test) {
        SAInterstitialAd.setTestMode(test);
        SAInterstitialAd.setConfiguration(SAConfiguration.fromValue(configuration));
        SAInterstitialAd.load(placementId, context);
    }

    public static boolean SuperAwesomeUnitySAInterstitialAdHasAdAvailable (Context context, int placementId) {
        return SAInterstitialAd.hasAdAvailable(placementId);
    }

    public static void SuperAwesomeUnitySAInterstitialAdPlay (Context context, int placementId, boolean isParentalGateEnabled, int orientation, boolean isBackButtonEnabled) {
        SAInterstitialAd.setParentalGate(isParentalGateEnabled);
        SAInterstitialAd.setOrientation(SAOrientation.fromValue(orientation));
        SAInterstitialAd.setBackButton(isBackButtonEnabled);
        SAInterstitialAd.play(placementId, context);
    }

}
