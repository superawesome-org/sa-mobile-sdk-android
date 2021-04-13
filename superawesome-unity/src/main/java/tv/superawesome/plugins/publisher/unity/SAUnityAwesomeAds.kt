package tv.superawesome.plugins.publisher.unity

import android.app.Activity
import tv.superawesome.sdk.publisher.common.awesomeAds.AwesomeAds
import tv.superawesome.sdk.publisher.common.models.Configuration
import tv.superawesome.sdk.publisher.common.network.Environment

/**
 * Created by gabriel.coman on 13/05/2018.
 */
object SAUnityAwesomeAds {
    fun SuperAwesomeUnityAwesomeAdsInit(activity: Activity, loggingEnabled: Boolean) {
        AwesomeAds.init(activity.application, Configuration(Environment.Production, loggingEnabled))
    }
}