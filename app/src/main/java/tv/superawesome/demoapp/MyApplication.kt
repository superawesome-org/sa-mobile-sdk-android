package tv.superawesome.demoapp

import androidx.multidex.MultiDexApplication
import tv.superawesome.sdk.publisher.common.awesomeAds.AwesomeAds
import tv.superawesome.sdk.publisher.common.models.Configuration
import tv.superawesome.sdk.publisher.common.network.Environment

/**
 * Created by gabriel.coman on 30/04/2018.
 */
class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        AwesomeAds.init(this, Configuration(Environment.Production, true))
    }
}