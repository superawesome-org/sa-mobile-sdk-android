package tv.superawesome.sdk.publisher.common.extensions

import java.net.URL
import java.security.MessageDigest

val String.baseUrl: String?
    get() {
        return try {
            val url = URL(this)
            "${url.protocol}://${url.authority}"
        } catch (exception: Exception) {
            null
        }
    }

fun String.toMD5(): String = MessageDigest.getInstance("MD5").digest(toByteArray()).toHex()

fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }
