package tv.superawesome.plugins.publisher.unity;

import android.app.Activity;

import tv.superawesome.sdk.publisher.AwesomeAds;

/**
 * Created by gabriel.coman on 13/05/2018.
 */

public class SAUnityAwesomeAds {

    public static void SuperAwesomeUnityAwesomeAdsInit(Activity activity, boolean loggingEnabled) {
        AwesomeAds.init(activity.getApplication(), loggingEnabled);
    }
}
