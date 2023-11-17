package tv.superawesome.sdk.publisher.di

import android.content.Context
import android.content.res.Resources
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import tv.superawesome.sdk.publisher.ad.AdControllerFactory
import tv.superawesome.sdk.publisher.ad.BannerAdManager
import tv.superawesome.sdk.publisher.ad.DefaultAdControllerFactory
import tv.superawesome.sdk.publisher.ad.InterstitialAdManager
import tv.superawesome.sdk.publisher.ad.VideoAdManager
import tv.superawesome.sdk.publisher.components.AdControllerStore
import tv.superawesome.sdk.publisher.components.AdProcessor
import tv.superawesome.sdk.publisher.components.AdProcessorType
import tv.superawesome.sdk.publisher.components.AdQueryMaker
import tv.superawesome.sdk.publisher.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.components.ConnectionProvider
import tv.superawesome.sdk.publisher.components.ConnectionProviderType
import tv.superawesome.sdk.publisher.components.DateProvider
import tv.superawesome.sdk.publisher.components.DateProviderType
import tv.superawesome.sdk.publisher.components.DefaultAdControllerStore
import tv.superawesome.sdk.publisher.components.DefaultLogger
import tv.superawesome.sdk.publisher.components.Device
import tv.superawesome.sdk.publisher.components.DeviceType
import tv.superawesome.sdk.publisher.components.Encoder
import tv.superawesome.sdk.publisher.components.EncoderType
import tv.superawesome.sdk.publisher.components.HtmlFormatter
import tv.superawesome.sdk.publisher.components.HtmlFormatterType
import tv.superawesome.sdk.publisher.components.IdGenerator
import tv.superawesome.sdk.publisher.components.IdGeneratorType
import tv.superawesome.sdk.publisher.components.ImageProvider
import tv.superawesome.sdk.publisher.components.ImageProviderType
import tv.superawesome.sdk.publisher.components.Logger
import tv.superawesome.sdk.publisher.components.NumberGenerator
import tv.superawesome.sdk.publisher.components.NumberGeneratorType
import tv.superawesome.sdk.publisher.components.SdkInfo
import tv.superawesome.sdk.publisher.components.SdkInfoType
import tv.superawesome.sdk.publisher.components.TimeProvider
import tv.superawesome.sdk.publisher.components.TimeProviderType
import tv.superawesome.sdk.publisher.components.UserAgentProvider
import tv.superawesome.sdk.publisher.components.UserAgentProviderType
import tv.superawesome.sdk.publisher.components.VastParser
import tv.superawesome.sdk.publisher.components.VastParserType
import tv.superawesome.sdk.publisher.components.VideoCache
import tv.superawesome.sdk.publisher.components.VideoCacheImpl
import tv.superawesome.sdk.publisher.components.XmlParser
import tv.superawesome.sdk.publisher.components.XmlParserType
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.VastAd
import tv.superawesome.sdk.publisher.network.Environment
import tv.superawesome.sdk.publisher.network.datasources.AwesomeAdsApiDataSource
import tv.superawesome.sdk.publisher.network.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.repositories.AdRepository
import tv.superawesome.sdk.publisher.repositories.AdRepositoryType
import tv.superawesome.sdk.publisher.repositories.EventRepository
import tv.superawesome.sdk.publisher.repositories.EventRepositoryType
import tv.superawesome.sdk.publisher.repositories.PerformanceRepository
import tv.superawesome.sdk.publisher.repositories.PerformanceRepositoryType
import tv.superawesome.sdk.publisher.repositories.PreferencesRepository
import tv.superawesome.sdk.publisher.repositories.PreferencesRepositoryType
import tv.superawesome.sdk.publisher.repositories.VastEventRepository
import tv.superawesome.sdk.publisher.repositories.VastEventRepositoryType
import tv.superawesome.sdk.publisher.ui.common.BumperPage
import tv.superawesome.sdk.publisher.ui.common.ContinuousViewableDetector
import tv.superawesome.sdk.publisher.ui.common.DefaultBumperPage
import tv.superawesome.sdk.publisher.ui.common.ParentalGate
import tv.superawesome.sdk.publisher.ui.common.SingleShotViewableDetector
import tv.superawesome.sdk.publisher.ui.common.ViewableDetector
import tv.superawesome.sdk.publisher.ui.video.VideoComponentFactory
import tv.superawesome.sdk.publisher.ui.video.VideoEvents
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalSerializationApi::class)
@Suppress("LongMethod", "UndocumentedPublicFunction")
@JvmSynthetic
internal fun createCommonModule(environment: Environment, loggingEnabled: Boolean) = module {
    includes(networkModule(environment.baseUrl))

    single { environment }
    single { Locale.getDefault() }
    single { Resources.getSystem().displayMetrics }
    single<SdkInfoType> { SdkInfo(get(), get(), get()) }
    single<NumberGeneratorType> { NumberGenerator() }
    single<DeviceType> { Device(get()) }
    single<EncoderType> { Encoder() }
    single<IdGeneratorType> { IdGenerator(get(), get(), get(), get()) }
    single<UserAgentProviderType> { UserAgentProvider(get()) }
    single<ConnectionProviderType> { ConnectionProvider(get()) }
    single<TimeProviderType> { TimeProvider() }
    single<DateProviderType> { DateProvider(Calendar.getInstance()) }
    single<AdQueryMakerType> {
        AdQueryMaker(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }

    factory { ParentalGate(get()) }
    factory<BumperPage> { DefaultBumperPage() }
    factory(named<SingleShotViewableDetector>()) { SingleShotViewableDetector() }.bind<ViewableDetector>()
    factory(named<ContinuousViewableDetector>()) { ContinuousViewableDetector() }.bind<ViewableDetector>()
    factory { VideoComponentFactory() }
    factory { (adResponse: AdResponse) ->
        VideoEvents(
            adResponse,
            get(),
        )
    }

    single<AdRepositoryType> { AdRepository(get(), get(), get()) }
    single<EventRepositoryType> { EventRepository(get(), get()) }
    factory<VastEventRepositoryType> { (vastAd: VastAd) ->
        VastEventRepository(
            vastAd,
            get(),
        )
    }
    single<PreferencesRepositoryType> { PreferencesRepository(get()) }
    single<PerformanceRepositoryType> { PerformanceRepository(get(), get()) }

    single<AwesomeAdsApiDataSourceType> { AwesomeAdsApiDataSource(get(), get()) }
    single<VideoCache> {
        val preferences = androidContext().getSharedPreferences("VideoCache", Context.MODE_PRIVATE)
        VideoCacheImpl(preferences, get(), get(), get())
    }
    single<HtmlFormatterType> { HtmlFormatter(get(), get()) }
    single<AdProcessorType> {
        AdProcessor(
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
    single<ImageProviderType> { ImageProvider() }
    single<Logger> { DefaultLogger(loggingEnabled) }
    single<AdControllerStore> { DefaultAdControllerStore() }

    // Vast
    single<VastParserType> { VastParser(get(), get()) }
    single<XmlParserType> { XmlParser() }

    // New ad controllers
    single { CoroutineScope(Dispatchers.Default) }
    singleOf(::DefaultAdControllerFactory) { bind<AdControllerFactory>() }

    factoryOf(::InterstitialAdManager)
    factoryOf(::VideoAdManager)
    factoryOf(::BannerAdManager)

    factory { (placementId: Int) ->
        val adStore = get<AdControllerStore>()
        adStore.peek(placementId) ?: error("Ad not found")
    }
}
