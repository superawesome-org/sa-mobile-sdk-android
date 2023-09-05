package tv.superawesome.sdk.publisher.common.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import tv.superawesome.sdk.publisher.common.components.Logger
import java.net.SocketTimeoutException

class RetryInterceptor(
    private val maxRetries: Int,
    private val logger: Logger
) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var retryCount = 0
        val request = chain.request()

        fun innerRequest(): Response = try {
            chain.proceed(request)
        } catch (exception: SocketTimeoutException) {
            if (retryCount < maxRetries) {
                logger.error("Network timeout, retrying again")
                retryCount++
                innerRequest()
            } else {
                throw exception
            }
        }

        return innerRequest()
    }
}
