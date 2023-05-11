package tv.superawesome.demoapp

import androidx.multidex.MultiDexApplication
import tv.superawesome.demoapp.model.SettingsData
import tv.superawesome.sdk.publisher.common.models.Configuration
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.common.sdk.AwesomeAds

class MyApplication : MultiDexApplication() {
    var settings = SettingsData()
    private var environment: Environment = Environment.Production

    override fun onCreate() {
        super.onCreate()
        AwesomeAds.init(
            this,
            Configuration(logging = true, environment = environment)
        )
    }

    fun setupUITest() {
        environment = Environment.UITesting
    }

    fun resetSettings() {
        settings = SettingsData()
    }

    fun updateSettings(update: (current: SettingsData) -> SettingsData) {
        settings = update(settings)
    }
}
