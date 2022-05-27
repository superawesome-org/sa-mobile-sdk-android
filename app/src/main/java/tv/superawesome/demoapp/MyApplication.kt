package tv.superawesome.demoapp

import androidx.multidex.MultiDexApplication
import tv.superawesome.sdk.publisher.AwesomeAds

class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        AwesomeAds.init(this, true, mapOf("openRtbPartnerId" to "xyz123"))
    }
}