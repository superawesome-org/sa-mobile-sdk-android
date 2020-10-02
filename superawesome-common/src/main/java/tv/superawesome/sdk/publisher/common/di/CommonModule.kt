package tv.superawesome.sdk.publisher.common.di

import android.content.Context
import android.content.res.Resources
import org.koin.dsl.module
import tv.superawesome.sdk.publisher.common.BuildConfig
import tv.superawesome.sdk.publisher.common.components.*
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.common.repositories.AdRepository
import tv.superawesome.sdk.publisher.common.repositories.EventRepository
import tv.superawesome.sdk.publisher.common.repositories.PreferencesRepository
import tv.superawesome.sdk.publisher.common.repositories.PreferencesRepositoryType
import java.util.*

fun createCommonModule(environment: Environment, applicationContext: Context) = module {
    single { applicationContext }
    single { environment }
    single { Locale.getDefault() }
    single { Resources.getSystem().displayMetrics }
    factory<GoogleAdvertisingProxyType> { GoogleAdvertisingProxy(get()) }
    single { SdkInfo(get(), get(), get(), BuildConfig.SDK_VERSION) }
    single { NumberGenerator() }
    single { Device(get()) }
    single { Encoder() }
    single { IdGenerator(get(), get(), get(), get()) }
    single { UserAgentProvider(get()) }
    single<ConnectionProviderType> { ConnectionProvider(get()) }
    single<AdQueryMakerType> { AdQueryMaker(get(), get(), get(), get(), get(), get(), get(), get()) }
    single { AdRepository(get(), get()) }
    single { EventRepository(get(), get()) }
    single<PreferencesRepositoryType> { PreferencesRepository(get()) }
}