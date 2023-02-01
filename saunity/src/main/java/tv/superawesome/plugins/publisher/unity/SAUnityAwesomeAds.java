package tv.superawesome.plugins.publisher.unity;

import android.app.Activity;

import tv.superawesome.sdk.publisher.common.models.Configuration;
import tv.superawesome.sdk.publisher.common.network.Environment;
import tv.superawesome.sdk.publisher.common.sdk.AwesomeAdsSdk;

/**
 * Created by gabriel.coman on 13/05/2018.
 */

public class SAUnityAwesomeAds {

    public static void SuperAwesomeUnityAwesomeAdsInit(Activity activity, boolean loggingEnabled) {
        AwesomeAdsSdk.init(activity.getApplication(), new Configuration(Environment.Production, loggingEnabled));
    }
}
