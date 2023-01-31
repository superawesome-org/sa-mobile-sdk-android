/*
 * @Copyright:   SADefaults Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
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
     */
    public static void SuperAwesomeUnityVersionSetVersion(Context context, String version) {
        SdkInfo.Companion.overrideVersionPlatform(version, Platform.Unity);
    }
}
