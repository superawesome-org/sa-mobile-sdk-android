package tv.superawesome.demoapp

import androidx.multidex.MultiDexApplication
import tv.superawesome.lib.sabumperpage.SABumperPage
import tv.superawesome.sdk.publisher.AwesomeAds

class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        SABumperPage.overrideName("AwesomeAds Demo")

        AwesomeAds.init(this, true)
    }

    @Suppress("unused")
    fun switchEnvironment(environment: SDKEnvironment) {
        /* no-op */
    }

    fun setupUITest() {
        environment = SDKEnvironment.UITesting
    }

    companion object {
        var environment: SDKEnvironment = SDKEnvironment.Production
            private set

        val flavor = SDKFlavor.BASE
    }
}
