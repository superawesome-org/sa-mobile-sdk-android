package com.superawesome.composeexample

import android.app.Application
import tv.superawesome.sdk.publisher.common.models.Configuration
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.common.sdk.AwesomeAdsSdk
import tv.superawesome.sdk.publisher.common.ui.common.BumperPage

class ComposeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AwesomeAdsSdk.init(
            applicationContext = this,
            configuration = Configuration(environment = Environment.Production, logging = true),
        )
        BumperPage.overrideName(resources.getString(R.string.app_name))
        BumperPage.overrideLogo(resources.getDrawable(R.mipmap.ic_launcher))
    }
}