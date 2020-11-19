/**
 * @Copyright:   SADefaults Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.publisher.unity

import tv.superawesome.sdk.publisher.common.components.SdkInfo

/**
 * Class that holds a number of static methods used to communicate with Unity
 */
object SAUnityVersion {
    /**
     * Method that sets the version
     *
     * @param context current context
     * @param version current version string
     * @param sdk     current sdk string
     */
    fun setVersion(version: String?, sdk: String?) {
        SdkInfo.overrideVersion = version
        SdkInfo.overridePlatform = sdk
    }
}