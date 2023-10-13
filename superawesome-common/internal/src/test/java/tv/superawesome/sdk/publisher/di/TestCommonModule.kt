package tv.superawesome.sdk.publisher.di

import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockWebServer
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import tv.superawesome.sdk.publisher.ad.AdControllerFactory
import tv.superawesome.sdk.publisher.ad.AdManager
import tv.superawesome.sdk.publisher.ad.DefaultAdManager
import tv.superawesome.sdk.publisher.components.AdControllerStore
import tv.superawesome.sdk.publisher.components.AdProcessor
import tv.superawesome.sdk.publisher.components.AdProcessorType
import tv.superawesome.sdk.publisher.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.components.ConnectionProviderType
import tv.superawesome.sdk.publisher.components.DefaultAdControllerStore
import tv.superawesome.sdk.publisher.components.Encoder
import tv.superawesome.sdk.publisher.components.EncoderType
import tv.superawesome.sdk.publisher.components.FakeUserAgentProvider
import tv.superawesome.sdk.publisher.components.HtmlFormatter
import tv.superawesome.sdk.publisher.components.HtmlFormatterType
import tv.superawesome.sdk.publisher.components.ImageProvider
import tv.superawesome.sdk.publisher.components.ImageProviderType
import tv.superawesome.sdk.publisher.components.Logger
import tv.superawesome.sdk.publisher.components.NumberGenerator
import tv.superawesome.sdk.publisher.components.NumberGeneratorType
import tv.superawesome.sdk.publisher.components.TimeProvider
import tv.superawesome.sdk.publisher.components.TimeProviderType
import tv.superawesome.sdk.publisher.components.UserAgentProviderType
import tv.superawesome.sdk.publisher.components.VastParser
import tv.superawesome.sdk.publisher.components.VastParserType
import tv.superawesome.sdk.publisher.components.XmlParser
import tv.superawesome.sdk.publisher.components.XmlParserType
import tv.superawesome.sdk.publisher.network.datasources.AwesomeAdsApiDataSource
import tv.superawesome.sdk.publisher.network.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.repositories.AdRepository
import tv.superawesome.sdk.publisher.repositories.AdRepositoryType
import tv.superawesome.sdk.publisher.repositories.EventRepository
import tv.superawesome.sdk.publisher.repositories.EventRepositoryType
import tv.superawesome.sdk.publisher.repositories.PerformanceRepository
import tv.superawesome.sdk.publisher.repositories.PerformanceRepositoryType
import tv.superawesome.sdk.publisher.testutil.FakeAdControllerFactory
import tv.superawesome.sdk.publisher.testutil.FakeAdQueryMaker
import tv.superawesome.sdk.publisher.testutil.FakeConnectionProvider
import tv.superawesome.sdk.publisher.testutil.TestLogger
import tv.superawesome.sdk.publisher.ui.common.ContinuousViewableDetector
import tv.superawesome.sdk.publisher.ui.common.SingleShotViewableDetector
import tv.superawesome.sdk.publisher.ui.common.ViewableDetector

@OptIn(ExperimentalSerializationApi::class)
fun testCommonModule(mockWebServer: MockWebServer) = module {
    includes(networkModule(mockWebServer))

    single<Logger> { TestLogger() }
    single<AdControllerStore> { DefaultAdControllerStore() }

    factory<NumberGeneratorType> { NumberGenerator() }
    factory<TimeProviderType> { TimeProvider() }
    factory<ImageProviderType> { ImageProvider() }
    factory<UserAgentProviderType> { FakeUserAgentProvider }
    factory<EncoderType> { Encoder() }
    factory<HtmlFormatterType> { HtmlFormatter(get(), get()) }
    factory<ConnectionProviderType> { FakeConnectionProvider() }
    factory<XmlParserType> { XmlParser() }
    factory<VastParserType> { VastParser(get(), get()) }
    factory<ViewableDetector>(named<SingleShotViewableDetector>()) { SingleShotViewableDetector(get()) }
    factory<ViewableDetector>(named<ContinuousViewableDetector>()) { ContinuousViewableDetector() }
    factory<AwesomeAdsApiDataSourceType> { AwesomeAdsApiDataSource(get(), get())}

    single<AdQueryMakerType> { FakeAdQueryMaker() }

    factoryOf(::AdProcessor) { bind<AdProcessorType>() }
    factoryOf(::EventRepository) { bind<EventRepositoryType>() }
    factoryOf(::PerformanceRepository) { bind<PerformanceRepositoryType>() }
    factoryOf(::AdRepository) { bind<AdRepositoryType>() }
    factoryOf(::FakeAdControllerFactory) { bind<AdControllerFactory>() }
    factoryOf(::DefaultAdManager) { bind<AdManager>() }

    factory { (placementId: Int) ->
        val adStore = get<AdControllerStore>()
        adStore.peek(placementId) ?: error("Ad not found")
    }
}
