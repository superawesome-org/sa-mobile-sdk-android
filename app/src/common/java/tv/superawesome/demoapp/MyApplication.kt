package tv.superawesome.demoapp

import androidx.multidex.MultiDexApplication
import tv.superawesome.sdk.publisher.models.DefaultConfiguration
import tv.superawesome.sdk.publisher.network.Environment
import tv.superawesome.sdk.publisher.AwesomeAds
import tv.superawesome.sdk.publisher.ui.common.BumperPage

class MyApplication : MultiDexApplication() {
    var environment: Environment = Environment.Production
        private set

    override fun onCreate() {
        super.onCreate()

        BumperPage.overrideName("AwesomeAds Demo")

        AwesomeAds.init(
            this,
            DefaultConfiguration(logging = true, environment = environment),
        )
    }

    fun setupUITest() {
        environment = Environment.UITesting
    }
}
