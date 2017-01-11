package tv.superawesome.plugins.unity;

import android.content.Context;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.sdk.views.SAAppWall;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;

public class SAUnityAppWall {

    private static final String unityName = "SAAppWall";

    public static void SuperAwesomeUnitySAAppWallCreate (Context context) {
        SAAppWall.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: SAUnityCallback.sendAdCallback(unityName, placementId, "adLoaded"); break;
                    case adFailedToLoad: SAUnityCallback.sendAdCallback(unityName, placementId, "adFailedToLoad"); break;
                    case adShown: SAUnityCallback.sendAdCallback(unityName, placementId, "adShown"); break;
                    case adFailedToShow: SAUnityCallback.sendAdCallback(unityName, placementId, "adFailedToShow"); break;
                    case adClicked: SAUnityCallback.sendAdCallback(unityName, placementId, "adClicked"); break;
                    case adClosed: SAUnityCallback.sendAdCallback(unityName, placementId, "adClosed"); break;
                }
            }
        });
    }

    public static void SuperAwesomeUnitySAAppWallLoad (Context context, int placementId, int configuration, boolean test) {
        SAAppWall.setTestMode(test);
        SAAppWall.setConfiguration(SAConfiguration.fromValue(configuration));
        SAAppWall.load(placementId, context);
    }

    public static boolean SuperAwesomeUnitySAAppWallHasAdAvailable (Context context, int placementId) {
        return SAAppWall.hasAdAvailable(placementId);
    }

    public static void SuperAwesomeUnitySAAppWallPlay (Context context, int placementId, boolean isParentalGateEnabled, boolean isBackButtonEnabled) {
        SAAppWall.setParentalGate(isParentalGateEnabled);
        SAAppWall.setBackButton(isBackButtonEnabled);
        SAAppWall.play(placementId, context);
    }

}
