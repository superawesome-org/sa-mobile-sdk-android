package tv.superawesome.sdk.publisher.common.sdk

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import tv.superawesome.sdk.publisher.common.components.SdkInfoType
import tv.superawesome.sdk.publisher.common.di.createCommonModule
import tv.superawesome.sdk.publisher.common.models.Configuration
import tv.superawesome.sdk.publisher.common.models.QueryAdditionalOptions

/**
 * The AwesomeAds Publisher SDK (Software Development Kit) enables you to add COPPA-compliant
 * banner, interstitial, and video advertisements easily to your apps.
 */
object AwesomeAds {
    private var app: KoinApplication? = null

    /**
     * Initialisation of AwesomeAds SDK, you can provide [Configuration] to enable/disable settings
     * */
    @JvmStatic
    fun init(applicationContext: Context, configuration: Configuration) {
        if (app == null) {
            app = buildKoinApplication(applicationContext, configuration)
        }
    }

    /**
     * Initialisation of AwesomeAds SDK, you can provide [Configuration] to enable/disable settings
     * @param options The options dictionary is used to set additional tracking information in the
     * form of key-value pairs. This information is sent when events are fired from the SDK
     * */
    @JvmStatic
    fun init(
        applicationContext: Context,
        configuration: Configuration,
        options: Map<String, Any>
    ) {
        if (app == null) {
            QueryAdditionalOptions.instance = QueryAdditionalOptions(options)
            app = buildKoinApplication(applicationContext, configuration)
        }
    }

    /** Information about the SDK e.g. version number
     * @return SDK info or `null` if the SDK is not initialised
     * */
    @JvmStatic
    fun info(): SdkInfoType? = app?.koin?.get()

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
