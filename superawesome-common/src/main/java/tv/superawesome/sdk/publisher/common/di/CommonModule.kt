package tv.superawesome.sdk.publisher.common.di

import android.content.Context
import android.content.res.Resources
import org.koin.dsl.module
import tv.superawesome.sdk.publisher.common.BuildConfig
import tv.superawesome.sdk.publisher.common.components.*
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.common.repositories.*
import java.util.*

fun createCommonModule(environment: Environment, applicationContext: Context) = module {
    single { applicationContext }
    single { environment }
    single { Locale.getDefault() }
    single { Resources.getSystem().displayMetrics }
    factory<GoogleAdvertisingProxyType> { GoogleAdvertisingProxy(get()) }
    single<SdkInfoType> { SdkInfo(get(), get(), get(), BuildConfig.SDK_VERSION) }
    single<NumberGeneratorType> { NumberGenerator() }
    single<DeviceType> { Device(get()) }
    single<EncoderType> { Encoder() }
    single<IdGeneratorType> { IdGenerator(get(), get(), get(), get()) }
    single<UserAgentProviderType> { UserAgentProvider(get()) }
    single<ConnectionProviderType> { ConnectionProvider(get()) }
    single<AdQueryMakerType> { AdQueryMaker(get(), get(), get(), get(), get(), get(), get(), get()) }
    single<AdRepositoryType> { AdRepository(get(), get(), get(), get()) }
    single<EventRepositoryType> { EventRepository(get(), get(),get()) }
    single<PreferencesRepositoryType> { PreferencesRepository(get()) }
    single<HtmlFormatterType> { HtmlFormatter(get(), get()) }
    single<AdProcessorType> { AdProcessor(get(), get(), get(), get()) }
    single<ImageProviderType> { ImageProvider() }
    single<DispatcherProviderType> { DispatcherProvider() }
    single<StringProviderType> { StringProvider() }

    // Vast
    single<VastParserType> { VastParser(get(), get()) }
    single<XmlParserType> { XmlParser() }
}