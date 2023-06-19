package tv.superawesome.demoapp

import androidx.multidex.MultiDexApplication
import tv.superawesome.lib.sabumperpage.SABumperPage
import tv.superawesome.sdk.publisher.common.models.Configuration
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.common.sdk.AwesomeAds
import tv.superawesome.sdk.publisher.common.ui.common.BumperPage

class MyApplication : MultiDexApplication() {
    var environment: Environment = Environment.Production
        private set

    override fun onCreate() {
        super.onCreate()

        BumperPage.overrideName("AwesomeAds Demo")
        SABumperPage.overrideName("AwesomeAds Demo")

        AwesomeAds.init(
            this,
            Configuration(logging = true, environment = environment)
        )
        tv.superawesome.sdk.publisher.AwesomeAds.init(this, true)
    }

    fun setupUITest() {
        environment = Environment.UITesting
    }
}
