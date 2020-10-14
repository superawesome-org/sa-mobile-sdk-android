package tv.superawesome.sdk.publisher.core

import android.content.Context
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication
import tv.superawesome.sdk.publisher.common.di.KoinInstanceProvider
import tv.superawesome.sdk.publisher.common.di.createCommonModule
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.networking.retrofit.di.createNetworkModule
import tv.superawesome.sdk.publisher.ui.di.createUiModule

object AwesomeAdsSdk {
    private var app: KoinApplication? = null

    @JvmStatic
    fun initSdk(applicationContext: Context, configuration: Configuration) {
        app = buildKoinApplication(applicationContext, configuration)
        KoinInstanceProvider.register(get().koin)
    }

    @JvmStatic
    fun get(): KoinApplication = app ?: error("AwesomeAdsSdk has not been started")

    private fun buildKoinApplication(applicationContext: Context, configuration: Configuration): KoinApplication =
            koinApplication {
                //androidContext(applicationContext)
                modules(createCommonModule(configuration.environment, configuration.logging, applicationContext),
                        createNetworkModule(configuration.environment),
                        createUiModule())
            }

    data class Configuration(val environment: Environment = Environment.production,
                             val logging: Boolean = false)
}