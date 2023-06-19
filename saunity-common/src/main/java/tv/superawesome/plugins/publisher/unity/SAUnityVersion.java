package tv.superawesome.plugins.publisher.unity;

import android.content.Context;

import tv.superawesome.sdk.publisher.common.components.SdkInfo;
import tv.superawesome.sdk.publisher.common.models.Platform;

/**
 * Class that holds a number of static methods used to communicate with Unity
 */
public class SAUnityVersion {

    /**
     * Method that sets the version
     *
     * @param context current context
     * @param version current version string
     * @param sdk     current sdk string
     */
    public static void SuperAwesomeUnityVersionSetVersion(Context context, String version, String sdk) {
        SdkInfo.overrideVersionPlatform(version, Platform.Unity);
    }
}
