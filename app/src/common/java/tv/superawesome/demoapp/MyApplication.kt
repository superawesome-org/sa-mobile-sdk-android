package tv.superawesome.demoapp

import androidx.multidex.MultiDexApplication
import tv.superawesome.sdk.publisher.common.models.DefaultConfiguration
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.common.sdk.AwesomeAds
import tv.superawesome.sdk.publisher.common.ui.common.BumperPage

class MyApplication : MultiDexApplication() {
    var environment: Environment = Environment.Production
        private set

    override fun onCreate() {
        super.onCreate()

        BumperPage.overrideName("AwesomeAds Demo")

        AwesomeAds.init(
            this,
            DefaultConfiguration(logging = true, environment = environment)
        )
    }

    fun setupUITest() {
        environment = Environment.UITesting
    }
}
