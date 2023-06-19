package tv.superawesome.sdk.publisher.common.extensions

import java.net.URL
import java.security.MessageDigest
import java.util.regex.Pattern

private val urlPattern: Pattern
    get() {
        return Pattern.compile(
            "(?:^|\\W)((ht|f)tp(s?)://|www\\.)"
                    + "(([\\w\\-]+\\.)+?([\\w\\-.~]+/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
        )
    }
internal val String.baseUrl: String?
    get() {
        return try {
            val url = URL(this)
            "${url.protocol}://${url.authority}"
        } catch (exception: Exception) {
            null
        }
    }

internal fun String.extractURLs(): List<String> {
    val urls: MutableList<String> = mutableListOf()

    val matcher = urlPattern.matcher(this)
    while (matcher.find()) {
        val matchStart = matcher.start(1)
        val matchEnd = matcher.end()
        urls.add(this.substring(matchStart, matchEnd))
    }

    return urls
}

internal fun String.toMD5(): String = MessageDigest.getInstance("MD5").digest(toByteArray()).toHex()

/**
 * Converts String to Hexadecimal format
 */
private fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }
