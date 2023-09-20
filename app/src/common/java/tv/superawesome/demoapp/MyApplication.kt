package tv.superawesome.demoapp

import android.app.Application
import android.content.Intent
import androidx.multidex.MultiDexApplication
import tv.superawesome.sdk.publisher.models.DefaultConfiguration
import tv.superawesome.sdk.publisher.network.Environment
import tv.superawesome.sdk.publisher.AwesomeAds
import tv.superawesome.sdk.publisher.ui.common.BumperPage

class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        BumperPage.overrideName("AwesomeAds Demo")
    }

    fun switchEnvironment(environment: SDKEnvironment) {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        val restartIntent = Intent.makeRestartActivityTask(intent?.component).apply {
            putExtra("environment", environment.name)
        }
        startActivity(restartIntent)
        Runtime.getRuntime().exit(0)
    }

    fun setupUITest() {
        environment = SDKEnvironment.UITesting
    }

    companion object {
        var environment: SDKEnvironment = SDKEnvironment.Production
            private set

        val flavor = SDKFlavor.COMMON

        fun initSDK(application: Application, environment: SDKEnvironment) {
            this.environment = environment

            val env = when (environment) {
                SDKEnvironment.Production -> Environment.Production
                SDKEnvironment.Staging -> Environment.Staging
                SDKEnvironment.UITesting -> Environment.UITesting
            }

            AwesomeAds.init(
                application,
                DefaultConfiguration(logging = true, environment = env),
            )
        }
    }
}
