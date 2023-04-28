package tv.superawesome.sdk.publisher.common.network.retrofit

import okhttp3.Interceptor
import okhttp3.Response
import tv.superawesome.sdk.publisher.common.components.UserAgentProviderType

internal class RetrofitHeaderInterceptor(userAgentProviderType: UserAgentProviderType) : Interceptor {
    private val userAgent: String = userAgentProviderType.name

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
                .header("User-Agent", userAgent)
        return chain.proceed(builder.build())
    }
}