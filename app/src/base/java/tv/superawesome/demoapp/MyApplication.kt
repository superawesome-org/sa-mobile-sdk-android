package tv.superawesome.demoapp

import androidx.multidex.MultiDexApplication
import tv.superawesome.lib.sabumperpage.SABumperPage
import tv.superawesome.sdk.publisher.AwesomeAds

class MyApplication : MultiDexApplication() {

    var environment: Environment = Environment.Production
        private set

    override fun onCreate() {
        super.onCreate()

        SABumperPage.overrideName("AwesomeAds Demo")

        AwesomeAds.init(this, true)
    }

    fun setupUITest() {
        environment = Environment.UITesting
    }
}
