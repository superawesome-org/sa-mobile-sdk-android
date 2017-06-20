/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.unity;

import android.content.Context;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.sdk.views.SAAppWall;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;

/**
 * Class that holds a number of static methods used to communicate with Unity
 */
public class SAUnityAppWall {

    // unity name to know what to call back
    private static final String unityName = "SAAppWall";

    /**
     * Method that creates a new App Wall (from Unity)
     */
    public static void SuperAwesomeUnitySAAppWallCreate (Context context) {
        SAAppWall.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adLoaded.toString()); break;
                    case adEmpty: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adEmpty.toString()); break;
                    case adFailedToLoad: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adFailedToLoad.toString()); break;
                    case adAlreadyLoaded: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adAlreadyLoaded.toString()); break;
                    case adShown: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adShown.toString()); break;
                    case adFailedToShow: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adFailedToShow.toString()); break;
                    case adClicked: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adClicked.toString()); break;
                    case adEnded: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adEnded.toString()); break;
                    case adClosed: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adClosed.toString()); break;
                }
            }
        });
    }

    /**
     * Method that loads a new App Wall (from Unity)
     */
    public static void SuperAwesomeUnitySAAppWallLoad (Context context, int placementId, int configuration, boolean test) {
        SAAppWall.setTestMode(test);
        SAAppWall.setConfiguration(SAConfiguration.fromValue(configuration));
        SAAppWall.load(placementId, context);
    }

    /**
     * Method that checks to see if an ad is available for an App Wall (from Unity)
     */
    public static boolean SuperAwesomeUnitySAAppWallHasAdAvailable (Context context, int placementId) {
        return SAAppWall.hasAdAvailable(placementId);
    }

    /**
     * Method that plays a new App Wall (from Unity)
     */
    public static void SuperAwesomeUnitySAAppWallPlay (Context context, int placementId, boolean isParentalGateEnabled, boolean isBackButtonEnabled) {
        SAAppWall.setParentalGate(isParentalGateEnabled);
        SAAppWall.setBackButton(isBackButtonEnabled);
        SAAppWall.play(placementId, context);
    }

}
