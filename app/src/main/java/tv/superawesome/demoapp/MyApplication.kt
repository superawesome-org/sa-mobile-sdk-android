package tv.superawesome.demoapp

import androidx.multidex.MultiDexApplication
import tv.superawesome.sdk.publisher.AwesomeAds.init
import tv.superawesome.sdk.publisher.common.models.Configuration
import tv.superawesome.sdk.publisher.common.network.Environment

/**
 * Created by gabriel.coman on 30/04/2018.
 */
class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        init(this, Configuration(Environment.production, true))
    }
}