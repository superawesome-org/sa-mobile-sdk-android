package tv.superawesome.sdk.publisher.networking.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import tv.superawesome.sdk.publisher.common.datasources.AdDataSourceType
import tv.superawesome.sdk.publisher.common.network.Environment

fun createNetworkModule(environment: Environment): Module = module {
    single<AdDataSourceType> { RetrofitAdDataSource(get()) }
    single { RetrofitHeaderInterceptor(get()) }
    single<OkHttpClient> {
        val interceptor: RetrofitHeaderInterceptor = get()
        OkHttpClient().newBuilder().addInterceptor(interceptor).build()
    }
    single { Json(configuration = JsonConfiguration.Stable.copy(allowStructuredMapKeys = true)) }
    single {
        val contentType: MediaType = MediaType.get("application/json")
        val json: Json = get()

        Retrofit.Builder()
                .baseUrl(environment.baseUrl)
                .client(get())
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()
    }
    single<RetrofitAwesomeAdsApi> {
        val retrofit: Retrofit = get()
        retrofit.create(RetrofitAwesomeAdsApi::class.java)
    }
}