package tv.superawesome.sdk.publisher.common.extensions

import java.net.URL
import java.security.MessageDigest

internal val String.baseUrl: String?
    get() {
        return try {
            val url = URL(this)
            "${url.protocol}://${url.authority}"
        } catch (exception: Exception) {
            null
        }
    }

internal fun String.toMD5(): String = MessageDigest.getInstance("MD5").digest(toByteArray()).toHex()

/**
 * Converts String to Hexadecimal format
 */
private fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }