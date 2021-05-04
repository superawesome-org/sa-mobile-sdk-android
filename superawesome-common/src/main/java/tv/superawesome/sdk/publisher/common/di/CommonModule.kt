package tv.superawesome.sdk.publisher.common.di

import MoatRepository
import android.content.res.Resources
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import tv.superawesome.sdk.publisher.common.BuildConfig
import tv.superawesome.sdk.publisher.common.components.AdProcessor
import tv.superawesome.sdk.publisher.common.components.AdProcessorType
import tv.superawesome.sdk.publisher.common.components.AdQueryMaker
import tv.superawesome.sdk.publisher.common.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.common.components.AdStore
import tv.superawesome.sdk.publisher.common.components.AdStoreType
import tv.superawesome.sdk.publisher.common.components.ConnectionProvider
import tv.superawesome.sdk.publisher.common.components.ConnectionProviderType
import tv.superawesome.sdk.publisher.common.components.DefaultLogger
import tv.superawesome.sdk.publisher.common.components.Device
import tv.superawesome.sdk.publisher.common.components.DeviceType
import tv.superawesome.sdk.publisher.common.components.DispatcherProvider
import tv.superawesome.sdk.publisher.common.components.DispatcherProviderType
import tv.superawesome.sdk.publisher.common.components.Encoder
import tv.superawesome.sdk.publisher.common.components.EncoderType
import tv.superawesome.sdk.publisher.common.components.GoogleAdvertisingProxy
import tv.superawesome.sdk.publisher.common.components.GoogleAdvertisingProxyType
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
import tv.superawesome.sdk.publisher.common.components.UserAgentProvider
import tv.superawesome.sdk.publisher.common.components.UserAgentProviderType
import tv.superawesome.sdk.publisher.common.components.VastParser
import tv.superawesome.sdk.publisher.common.components.VastParserType
import tv.superawesome.sdk.publisher.common.components.XmlParser
import tv.superawesome.sdk.publisher.common.components.XmlParserType
import tv.superawesome.sdk.publisher.common.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.common.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.models.VastAd
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.common.networking.OkHttpNetworkDataSource
import tv.superawesome.sdk.publisher.common.networking.RetrofitAdDataSource
import tv.superawesome.sdk.publisher.common.networking.RetrofitAwesomeAdsApi
import tv.superawesome.sdk.publisher.common.networking.RetrofitHeaderInterceptor
import tv.superawesome.sdk.publisher.common.repositories.AdRepository
import tv.superawesome.sdk.publisher.common.repositories.AdRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.EventRepository
import tv.superawesome.sdk.publisher.common.repositories.EventRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.MoatRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.PreferencesRepository
import tv.superawesome.sdk.publisher.common.repositories.PreferencesRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.VastEventRepository
import tv.superawesome.sdk.publisher.common.repositories.VastEventRepositoryType
import tv.superawesome.sdk.publisher.common.ui.common.AdController
import tv.superawesome.sdk.publisher.common.ui.common.AdControllerType
import tv.superawesome.sdk.publisher.common.ui.common.ParentalGate
import tv.superawesome.sdk.publisher.common.ui.common.ViewableDetector
import tv.superawesome.sdk.publisher.common.ui.common.ViewableDetectorType
import tv.superawesome.sdk.publisher.common.ui.video.VideoComponentFactory
import tv.superawesome.sdk.publisher.common.ui.video.VideoEvents
import tv.superawesome.sdk.publisher.common.ui.video.player.IVideoPlayerController
import tv.superawesome.sdk.publisher.common.ui.video.player.VideoPlayerController
import java.util.Locale

@OptIn(ExperimentalSerializationApi::class)
fun createCommonModule(environment: Environment, loggingEnabled: Boolean) = module {
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
    single<AdQueryMakerType> {
        AdQueryMaker(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    factory<AdControllerType> { AdController(get(), get(), get(), get(), get()) }
    factory { ParentalGate(get()) }
    factory<ViewableDetectorType> { ViewableDetector(get()) }
    factory<IVideoPlayerController> { VideoPlayerController() }
    factory { VideoComponentFactory() }
    factory { (adResponse: AdResponse, moatLimiting: Boolean) ->
        VideoEvents(
            adResponse,
            moatLimiting,
            get(),
            get()
        )
    }

    single<MoatRepositoryType> { MoatRepository(true, get(), get()) }
    single<AdRepositoryType> { AdRepository(get(), get(), get(), get()) }
    single<EventRepositoryType> { EventRepository(get(), get(), get()) }
    factory<VastEventRepositoryType> { (vastAd: VastAd) ->
        VastEventRepository(
            vastAd,
            get(),
            get()
        )
    }
    single<PreferencesRepositoryType> { PreferencesRepository(get()) }

    single<AwesomeAdsApiDataSourceType> { RetrofitAdDataSource(get()) }
    single { RetrofitHeaderInterceptor(get()) }
    single {
        val interceptor: RetrofitHeaderInterceptor = get()
        val readerInterceptor = HttpLoggingInterceptor()
        readerInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(interceptor).build()
        OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .addInterceptor(
                ChuckerInterceptor.Builder(androidContext())
                    .collector(ChuckerCollector(androidContext()))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .addInterceptor(readerInterceptor)
            .build()
    }
    single {
        Json {
            allowStructuredMapKeys = true
            ignoreUnknownKeys = true
        }
    }
    single {
        val contentType: MediaType = MediaType.get("application/json")
        val json: Json = get()
        Retrofit.Builder()
            .baseUrl(environment.baseUrl)
            .client(get())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
    single {
        val retrofit: Retrofit = get()
        retrofit.create(RetrofitAwesomeAdsApi::class.java)
    }
    single<NetworkDataSourceType> { OkHttpNetworkDataSource(get(), get(), get()) }

    single<HtmlFormatterType> { HtmlFormatter(get(), get()) }
    single<AdProcessorType> { AdProcessor(get(), get(), get(), get()) }
    single<ImageProviderType> { ImageProvider() }
    single<DispatcherProviderType> { DispatcherProvider() }
    single<Logger> { DefaultLogger(loggingEnabled) }
    single<AdStoreType> { AdStore() }

    // Vast
    single<VastParserType> { VastParser(get(), get()) }
    single<XmlParserType> { XmlParser() }
}
