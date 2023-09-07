package com.superawesome.composeexample

import android.app.Application
import tv.superawesome.sdk.publisher.models.DefaultConfiguration
import tv.superawesome.sdk.publisher.network.Environment
import tv.superawesome.sdk.publisher.sdk.AwesomeAds
import tv.superawesome.sdk.publisher.ui.common.BumperPage

class ComposeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AwesomeAds.init(
            applicationContext = this,
            configuration = DefaultConfiguration(environment = Environment.Production, logging = true),
        )
        BumperPage.overrideName(resources.getString(R.string.app_name))
        BumperPage.overrideLogo(resources.getDrawable(R.mipmap.ic_launcher))
    }
}
