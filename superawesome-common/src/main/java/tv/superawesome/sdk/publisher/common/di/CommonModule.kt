package tv.superawesome.sdk.publisher.common.di

import android.content.res.Resources
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.dsl.module
import tv.superawesome.sdk.publisher.common.components.AdProcessor
import tv.superawesome.sdk.publisher.common.components.AdProcessorType
import tv.superawesome.sdk.publisher.common.components.AdQueryMaker
import tv.superawesome.sdk.publisher.common.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.common.components.AdStore
import tv.superawesome.sdk.publisher.common.components.AdStoreType
import tv.superawesome.sdk.publisher.common.components.ConnectionProvider
import tv.superawesome.sdk.publisher.common.components.ConnectionProviderType
import tv.superawesome.sdk.publisher.common.components.DateProvider
import tv.superawesome.sdk.publisher.common.components.DateProviderType
import tv.superawesome.sdk.publisher.common.components.DefaultLogger
import tv.superawesome.sdk.publisher.common.components.Device
import tv.superawesome.sdk.publisher.common.components.DeviceType
import tv.superawesome.sdk.publisher.common.components.Encoder
import tv.superawesome.sdk.publisher.common.components.EncoderType
import tv.superawesome.sdk.publisher.common.components.HtmlFormatter
import tv.superawesome.sdk.publisher.common.components.HtmlFormatterType
import tv.superawesome.sdk.publisher.common.components.IdGenerator
import tv.superawesome.sdk.publisher.common.components.IdGeneratorType
import tv.superawesome.sdk.publisher.common.components.ImageProvider
import tv.superawesome.sdk.publisher.common.components.ImageProviderType
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.components.NumberGenerator
import tv.superawesome.sdk.publisher.common.components.NumberGeneratorType
import tv.superawesome.sdk.publisher.common.components.SdkInfo
import tv.superawesome.sdk.publisher.common.components.SdkInfoType
import tv.superawesome.sdk.publisher.common.components.TimeProvider
import tv.superawesome.sdk.publisher.common.components.TimeProviderType
import tv.superawesome.sdk.publisher.common.components.UserAgentProvider
import tv.superawesome.sdk.publisher.common.components.UserAgentProviderType
import tv.superawesome.sdk.publisher.common.components.VastParser
import tv.superawesome.sdk.publisher.common.components.VastParserType
import tv.superawesome.sdk.publisher.common.components.XmlParser
import tv.superawesome.sdk.publisher.common.components.XmlParserType
import tv.superawesome.sdk.publisher.common.network.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.models.VastAd
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.common.network.datasources.AwesomeAdsApiDataSource
import tv.superawesome.sdk.publisher.common.repositories.AdRepository
import tv.superawesome.sdk.publisher.common.repositories.AdRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.EventRepository
import tv.superawesome.sdk.publisher.common.repositories.EventRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.PerformanceRepository
import tv.superawesome.sdk.publisher.common.repositories.PerformanceRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.PreferencesRepository
import tv.superawesome.sdk.publisher.common.repositories.PreferencesRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.VastEventRepository
import tv.superawesome.sdk.publisher.common.repositories.VastEventRepositoryType
import tv.superawesome.sdk.publisher.common.ui.common.AdController
import tv.superawesome.sdk.publisher.common.ui.common.AdControllerType
import tv.superawesome.sdk.publisher.common.ui.common.BumperPage
import tv.superawesome.sdk.publisher.common.ui.common.DefaultBumperPage
import tv.superawesome.sdk.publisher.common.ui.common.ParentalGate
import tv.superawesome.sdk.publisher.common.ui.common.ViewableDetector
import tv.superawesome.sdk.publisher.common.ui.common.ViewableDetectorType
import tv.superawesome.sdk.publisher.common.ui.video.VideoComponentFactory
import tv.superawesome.sdk.publisher.common.ui.video.VideoEvents
import tv.superawesome.sdk.publisher.common.ui.video.player.IVideoPlayerController
import tv.superawesome.sdk.publisher.common.ui.video.player.VideoPlayerController
import java.util.*

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
            get(),
        )
    }

    factory<AdControllerType> { AdController(get(), get(), get(), get(), get(), get()) }
    factory { ParentalGate(get()) }
    factory<BumperPage> { DefaultBumperPage() }
    factory<ViewableDetectorType> { ViewableDetector(get()) }
    factory<IVideoPlayerController> { VideoPlayerController() }
    factory { VideoComponentFactory() }
    factory { (adResponse: AdResponse) ->
        VideoEvents(
            adResponse,
            get()
        )
    }

    single<AdRepositoryType> { AdRepository(get(), get(), get()) }
    single<EventRepositoryType> { EventRepository(get(), get()) }
    factory<VastEventRepositoryType> { (vastAd: VastAd) ->
        VastEventRepository(
            vastAd,
            get()
        )
    }
    single<PreferencesRepositoryType> { PreferencesRepository(get()) }
    single<PerformanceRepositoryType> { PerformanceRepository(get()) }

    single<AwesomeAdsApiDataSourceType> { AwesomeAdsApiDataSource(get()) }

    single<HtmlFormatterType> { HtmlFormatter(get(), get()) }
    single<AdProcessorType> { AdProcessor(get(), get(), get(), get()) }
    single<ImageProviderType> { ImageProvider() }
    single<Logger> { DefaultLogger(loggingEnabled) }
    single<AdStoreType> { AdStore() }

    // Vast
    single<VastParserType> { VastParser(get(), get()) }
    single<XmlParserType> { XmlParser() }
}
