package tv.superawesome.sdk.publisher.core.sdk

import android.content.Context
import org.koin.dsl.module
import tv.superawesome.sdk.publisher.common.components.*
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.common.repositories.AdRepository
import tv.superawesome.sdk.publisher.common.repositories.EventRepository
import tv.superawesome.sdk.publisher.common.repositories.PreferencesRepository
import tv.superawesome.sdk.publisher.common.repositories.PreferencesRepositoryType

internal fun createSdkModule(environment: Environment, applicationContext: Context) = module {
    single { applicationContext }
    single { environment }
    factory<GoogleAdvertisingProxyType> { GoogleAdvertisingProxy(get()) }
    single { SdkInfo() }
    single { NumberGenerator() }
    single { Device() }
    single { Encoder() }
    single { IdGenerator(get(), get(), get(), get()) }
    single { UserAgentProvider(get()) }
    single<ConnectionProviderType> { ConnectionProvider(get()) }
    single<AdQueryMakerType> { AdQueryMaker(get(), get(), get(), get(), get(), get(), get()) }
    single { AdRepository(get(), get()) }
    single { EventRepository(get(), get()) }
    single<PreferencesRepositoryType> { PreferencesRepository(get()) }
}