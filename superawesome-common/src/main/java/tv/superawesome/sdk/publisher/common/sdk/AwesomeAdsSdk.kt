package tv.superawesome.sdk.publisher.common.sdk

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import tv.superawesome.sdk.publisher.common.models.QueryAdditionalOptions
import tv.superawesome.sdk.publisher.common.di.createCommonModule
import tv.superawesome.sdk.publisher.common.models.Configuration

object AwesomeAdsSdk {
    private var app: KoinApplication? = null

    @JvmStatic
    fun init(applicationContext: Context, configuration: Configuration) {
        if (app == null) {
            app = buildKoinApplication(applicationContext, configuration)
        }
    }

    @JvmStatic
    fun init(applicationContext: Context, configuration: Configuration, options: Map<String, String>) {
        if (app == null) {
            QueryAdditionalOptions.instance = QueryAdditionalOptions(options)
            app = buildKoinApplication(applicationContext, configuration)
        }
    }

    private fun buildKoinApplication(
        applicationContext: Context,
        configuration: Configuration
    ): KoinApplication =
        startKoin {
            androidContext(applicationContext)
            modules(
                createCommonModule(
                    configuration.environment,
                    configuration.logging
                )
            )
        }
}