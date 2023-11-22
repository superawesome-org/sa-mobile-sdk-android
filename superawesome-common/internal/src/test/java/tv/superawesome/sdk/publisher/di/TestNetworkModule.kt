package tv.superawesome.sdk.publisher.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module
import retrofit2.Retrofit
import tv.superawesome.sdk.publisher.components.FakeUserAgentProvider
import tv.superawesome.sdk.publisher.featureflags.AdServerFeatureFlagsApi
import tv.superawesome.sdk.publisher.network.AwesomeAdsApi
import tv.superawesome.sdk.publisher.network.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.network.datasources.OkHttpNetworkDataSource
import tv.superawesome.sdk.publisher.network.interceptors.HeaderInterceptor
import tv.superawesome.sdk.publisher.network.interceptors.RetryInterceptor
import tv.superawesome.sdk.publisher.testutil.TestLogger
import java.io.File
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalSerializationApi::class)
fun networkModule(mockServer: MockWebServer) = module {

    val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.MILLISECONDS)
            .readTimeout(100, TimeUnit.MILLISECONDS)
            .writeTimeout(100, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(
                RetryInterceptor(maxRetries = 5, TestLogger())
            )
            .addInterceptor(
                HeaderInterceptor(FakeUserAgentProvider)
            )
            .build()

    single {
        Json {
            allowStructuredMapKeys = true
            ignoreUnknownKeys = true
        }
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .client(client)
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single<NetworkDataSourceType> {
        OkHttpNetworkDataSource(
            client = client,
            cacheDir = File.createTempFile("cacheDir", "test_file"),
            logger = TestLogger()
        )
    }

    single<AwesomeAdsApi> {
        get<Retrofit>().create(AwesomeAdsApi::class.java)
    }

    single<AdServerFeatureFlagsApi> {
        get<Retrofit>().create(AdServerFeatureFlagsApi::class.java)
    }
}
