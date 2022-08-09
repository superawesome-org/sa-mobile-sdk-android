package tv.superawesome.demoapp

import androidx.multidex.MultiDexApplication
import tv.superawesome.demoapp.model.SettingsData
import tv.superawesome.sdk.publisher.AwesomeAds
import tv.superawesome.sdk.publisher.common.models.Configuration
import tv.superawesome.sdk.publisher.common.sdk.AwesomeAdsSdk

class MyApplication : MultiDexApplication() {
    var settings = SettingsData()

    override fun onCreate() {
        super.onCreate()
        AwesomeAds.init(this, true)
        AwesomeAdsSdk.init(this, Configuration(logging = true))
    }

    fun updateSettings(update: (current: SettingsData) -> SettingsData) {
        settings = update(settings)
    }
}