package tv.superawesome.plugins.publisher.unity

import android.app.Activity
import tv.superawesome.sdk.publisher.AwesomeAds.init
import tv.superawesome.sdk.publisher.common.models.Configuration
import tv.superawesome.sdk.publisher.common.network.Environment

/**
 * Created by gabriel.coman on 13/05/2018.
 */
object SAUnityAwesomeAds {
    fun init(activity: Activity, loggingEnabled: Boolean) {
        init(activity.application, Configuration(Environment.production, loggingEnabled))
    }
}