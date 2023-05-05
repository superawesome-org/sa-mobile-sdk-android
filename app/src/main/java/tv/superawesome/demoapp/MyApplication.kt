package tv.superawesome.demoapp

import androidx.multidex.MultiDexApplication
import tv.superawesome.demoapp.model.SettingsData
import tv.superawesome.sdk.publisher.AwesomeAds
import tv.superawesome.sdk.publisher.common.models.Configuration

class MyApplication : MultiDexApplication() {
    var settings = SettingsData()

    override fun onCreate() {
        super.onCreate()
        AwesomeAds.init(this, true)
        tv.superawesome.sdk.publisher.common.sdk.AwesomeAds.init(
            this,
            Configuration(logging = true)
        )
    }

    fun resetSettings() {
        settings = SettingsData()
    }

    fun updateSettings(update: (current: SettingsData) -> SettingsData) {
        settings = update(settings)
    }
}
