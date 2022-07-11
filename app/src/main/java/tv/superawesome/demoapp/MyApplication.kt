package tv.superawesome.demoapp

import androidx.multidex.MultiDexApplication
import tv.superawesome.sdk.publisher.AwesomeAds
import tv.superawesome.sdk.publisher.common.models.Configuration
import tv.superawesome.sdk.publisher.common.sdk.AwesomeAdsSdk

class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        AwesomeAds.init(this, true)
        AwesomeAdsSdk.init(this, Configuration(logging = true))
    }
}