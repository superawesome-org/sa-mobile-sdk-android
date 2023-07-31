package tv.superawesome.sdk.publisher.common.di

import android.content.res.Resources
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import tv.superawesome.sdk.publisher.common.components.*
import tv.superawesome.sdk.publisher.common.network.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.common.network.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.models.VastAd
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.common.network.datasources.OkHttpNetworkDataSource
import tv.superawesome.sdk.publisher.common.network.datasources.AwesomeAdsApiDataSource
import tv.superawesome.sdk.publisher.common.network.AwesomeAdsApi
import tv.superawesome.sdk.publisher.common.network.interceptors.HeaderInterceptor
import tv.superawesome.sdk.publisher.common.network.interceptors.RetryInterceptor
import tv.superawesome.sdk.publisher.common.openmeasurement.OpenMeasurementAdSessionBuilder
import tv.superawesome.sdk.publisher.common.openmeasurement.OpenMeasurementAdSessionBuilderType
import tv.superawesome.sdk.publisher.common.openmeasurement.OpenMeasurementContextBuilder
import tv.superawesome.sdk.publisher.common.openmeasurement.OpenMeasurementContextBuilderType
import tv.superawesome.sdk.publisher.common.openmeasurement.OpenMeasurementJSInjector
import tv.superawesome.sdk.publisher.common.openmeasurement.OpenMeasurementJSInjectorType
import tv.superawesome.sdk.publisher.common.openmeasurement.OpenMeasurementJSLoader
import tv.superawesome.sdk.publisher.common.openmeasurement.OpenMeasurementJSLoaderType
import tv.superawesome.sdk.publisher.common.openmeasurement.OpenMeasurementSessionManager
import tv.superawesome.sdk.publisher.common.openmeasurement.OpenMeasurementSessionManagerType
import tv.superawesome.sdk.publisher.common.openmeasurement.SAOpenMeasurementModule
import tv.superawesome.sdk.publisher.common.repositories.*
import tv.superawesome.sdk.publisher.common.ui.common.*
import tv.superawesome.sdk.publisher.common.ui.video.VideoComponentFactory
import tv.superawesome.sdk.publisher.common.ui.video.VideoEvents
import tv.superawesome.sdk.publisher.common.ui.video.player.IVideoPlayerController
import tv.superawesome.sdk.publisher.common.ui.video.player.VideoPlayerController
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
internal fun createCommonModule(environment: Environment, loggingEnabled: Boolean) = module {
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

    factory<AdControllerType> { AdController(get(), get(), get(), get(), get(), get()) }
    factory { ParentalGate(get()) }
    factory { BumperPage() }
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
    single { HeaderInterceptor(get()) }
    single { RetryInterceptor(maxRetries = 5, get()) }
    single {
        val retryInterceptor: RetryInterceptor = get()
        val headerInterceptor: HeaderInterceptor = get()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient().newBuilder()
            .addNetworkInterceptor(retryInterceptor)
            .addInterceptor(headerInterceptor)
            .addInterceptor(
                ChuckerInterceptor.Builder(androidContext())
                    .collector(ChuckerCollector(androidContext()))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
    single {
        Json {
            allowStructuredMapKeys = true
            ignoreUnknownKeys = true
        }
    }
    single {
        val json: Json = get()
        Retrofit.Builder()
            .baseUrl(environment.baseUrl)
            .client(get())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    single {
        val retrofit: Retrofit = get()
        retrofit.create(AwesomeAdsApi::class.java)
    }
    single<NetworkDataSourceType> { OkHttpNetworkDataSource(get(), get(), get()) }

    single<HtmlFormatterType> { HtmlFormatter(get(), get()) }
    single<AdProcessorType> { AdProcessor(get(), get(), get(), get()) }
    single<ImageProviderType> { ImageProvider() }
    single<Logger> { DefaultLogger(loggingEnabled) }
    single<AdStoreType> { AdStore() }

    // Open Measurement
    single<OpenMeasurementJSLoaderType> { OpenMeasurementJSLoader(get()) }
    single<OpenMeasurementJSInjectorType> { OpenMeasurementJSInjector(get(), get()) }
    single<OpenMeasurementContextBuilderType> { OpenMeasurementContextBuilder(get()) }
    single<OpenMeasurementAdSessionBuilderType> { OpenMeasurementAdSessionBuilder(get(), get()) }
    factory<OpenMeasurementSessionManagerType> { OpenMeasurementSessionManager(get(), get(), get()) }

    // Vast
    single<VastParserType> { VastParser(get(), get()) }
    single<XmlParserType> { XmlParser() }
}
