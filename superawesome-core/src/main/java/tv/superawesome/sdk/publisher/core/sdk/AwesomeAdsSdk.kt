package tv.superawesome.sdk.publisher.core.sdk

import android.content.Context
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.KoinComponent
import org.koin.dsl.koinApplication
import tv.superawesome.sdk.publisher.common.di.createCommonModule
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.networking.retrofit.createNetworkModule


object AwesomeAdsSdk {
    private var app: KoinApplication? = null

    @JvmStatic
    fun initSdk(applicationContext: Context, configuration: Configuration) {
        app = buildKoinApplication(applicationContext, configuration)
    }

    @JvmStatic
    fun get(): KoinApplication = app ?: error("AwesomeAdsSdk has not been started")

    private fun buildKoinApplication(applicationContext: Context, configuration: Configuration): KoinApplication =
            koinApplication {
                //androidContext(applicationContext)
                modules(createCommonModule(configuration.environment, applicationContext),
                        createNetworkModule(configuration.environment))
            }

    data class Configuration(val environment: Environment = Environment.production,
                             val logging: Boolean = false)
}

interface AwesomeAdsKoinComponent : KoinComponent {
    override fun getKoin(): Koin = AwesomeAdsSdk.get().koin
}