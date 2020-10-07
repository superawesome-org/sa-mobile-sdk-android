package tv.superawesome.sdk.publisher.common.extensions

import java.net.URL

val String.baseUrl: String?
    get() {
        return try {
            val url = URL(this)
            "${url.protocol}://${url.authority}"
        } catch (exception: Exception) {
            null
        }
    }