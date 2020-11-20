package tv.superawesome.sdk.publisher.networking.retrofit.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import tv.superawesome.sdk.publisher.common.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.common.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.networking.retrofit.OkHttpNetworkDataSource
import tv.superawesome.sdk.publisher.networking.retrofit.RetrofitAdDataSource
import tv.superawesome.sdk.publisher.networking.retrofit.RetrofitAwesomeAdsApi
import tv.superawesome.sdk.publisher.networking.retrofit.RetrofitHeaderInterceptor

fun createNetworkModule(environment: Environment): Module = module {
    single<AwesomeAdsApiDataSourceType> { RetrofitAdDataSource(get()) }
    single { RetrofitHeaderInterceptor(get()) }
    single {
        val interceptor: RetrofitHeaderInterceptor = get()
        OkHttpClient().newBuilder().addInterceptor(interceptor).build()
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
}