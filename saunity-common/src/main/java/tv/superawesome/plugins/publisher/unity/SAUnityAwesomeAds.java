package tv.superawesome.plugins.publisher.unity;

import android.app.Activity;

import tv.superawesome.sdk.publisher.sdk.AwesomeAds;

public class SAUnityAwesomeAds {

    public static void SuperAwesomeUnityAwesomeAdsInit(Activity activity, boolean loggingEnabled) {
        AwesomeAds.init(activity.getApplication(), loggingEnabled);
    }
}
