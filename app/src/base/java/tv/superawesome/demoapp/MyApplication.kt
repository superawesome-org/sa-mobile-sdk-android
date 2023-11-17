package tv.superawesome.demoapp

import androidx.multidex.MultiDexApplication
import tv.superawesome.lib.sabumperpage.SABumperPage
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.lib.sasession.session.SASession
import tv.superawesome.sdk.publisher.AwesomeAds
import tv.superawesome.sdk.publisher.SABannerAd
import tv.superawesome.sdk.publisher.SAInterstitialAd
import tv.superawesome.sdk.publisher.SAVideoAd

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
        HasEnvironment.environment = SDKEnvironment.UITesting
        SAVideoAd.setConfiguration(SAConfiguration.UITESTING)
        SAInterstitialAd.setConfiguration(SAConfiguration.UITESTING)
    }

    companion object {
        val flavor = SDKFlavor.BASE
    }
}
