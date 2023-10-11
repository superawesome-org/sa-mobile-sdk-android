package tv.superawesome.sdk.publisher.di

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
import tv.superawesome.sdk.publisher.network.AwesomeAdsApi
import tv.superawesome.sdk.publisher.network.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.network.datasources.OkHttpNetworkDataSource
import tv.superawesome.sdk.publisher.network.interceptors.HeaderInterceptor
import tv.superawesome.sdk.publisher.network.interceptors.RetryInterceptor

@OptIn(ExperimentalSerializationApi::class)
fun networkModule(baseUrl: String) = module {

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
                    .maxContentLength(length = 250_000L)
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
            .baseUrl(baseUrl)
            .client(get())
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
            .build()
    }
    single {
        val retrofit: Retrofit = get()
        retrofit.create(AwesomeAdsApi::class.java)
    }
    single<NetworkDataSourceType> { OkHttpNetworkDataSource(get(), androidContext().cacheDir, get()) }
}
