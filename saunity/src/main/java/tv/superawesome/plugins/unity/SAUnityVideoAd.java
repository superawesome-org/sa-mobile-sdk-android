package tv.superawesome.plugins.unity;

import android.content.Context;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAOrientation;
import tv.superawesome.sdk.views.SAVideoAd;

public class SAUnityVideoAd {

    private static final String unityName = "SAVideoAd";

    public static void SuperAwesomeUnitySAVideoAdCreate (Context context) {
        SAVideoAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: SAUnityCallback.sendToUnity(unityName, placementId, "adLoaded"); break;
                    case adFailedToLoad: SAUnityCallback.sendToUnity(unityName, placementId, "adFailedToShow"); break;
                    case adShown: SAUnityCallback.sendToUnity(unityName, placementId, "adShown"); break;
                    case adFailedToShow: SAUnityCallback.sendToUnity(unityName, placementId, "adFailedToShow"); break;
                    case adClicked: SAUnityCallback.sendToUnity(unityName, placementId, "adClicked"); break;
                    case adClosed: SAUnityCallback.sendToUnity(unityName, placementId, "adClosed"); break;
                }
            }
        });
    }

    public static void SuperAwesomeUnitySAVideoAdLoad(Context context, int placementId, int configuration, boolean test) {
        SAVideoAd.setTestMode(test);
        SAVideoAd.setConfiguration(SAConfiguration.fromValue(configuration));
        SAVideoAd.load(placementId, context);
    }

    public static boolean SuperAwesomeUnitySAVideoAdHasAdAvailable (Context context, int placementId) {
        return SAVideoAd.hasAdAvailable(placementId);
    }

    public static void SuperAwesomeUnitySAVideoAdPlay (Context context, int placementId, boolean isParentalGateEnabled, boolean shouldShowCloseButton, boolean shouldShowSmallClickButton, boolean shouldAutomaticallyCloseAtEnd, int orientation, boolean isBackButtonEnabled) {
        SAVideoAd.setParentalGate(isParentalGateEnabled);
        SAVideoAd.setCloseAtEnd(shouldAutomaticallyCloseAtEnd);
        SAVideoAd.setCloseButton(shouldShowCloseButton);
        SAVideoAd.setSmallClick(shouldShowSmallClickButton);
        SAVideoAd.setBackButton(isBackButtonEnabled);
        SAVideoAd.setOrientation(SAOrientation.fromValue(orientation));
        SAVideoAd.play(placementId, context);
    }

}
