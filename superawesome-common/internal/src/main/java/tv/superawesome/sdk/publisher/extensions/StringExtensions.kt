package tv.superawesome.sdk.publisher.extensions

import java.net.MalformedURLException
import java.net.URL
import java.security.MessageDigest
import java.util.regex.Pattern

@Suppress("StringShouldBeRawString")
private val urlPattern: Pattern
    get() {
        return Pattern.compile(
            "(?:^|\\W)((ht|f)tp(s?)://|www\\.)"
                    + "(([\\w\\-]+\\.)+?([\\w\\-.~]+/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
        )
    }
val String.baseUrl: String?
    get() = try {
        val url = URL(this)
        "${url.protocol}://${url.authority}"
    } catch (exception: MalformedURLException) {
        null
    }

fun String.extractURLs(): List<String> {
    val urls: MutableList<String> = mutableListOf()

    val matcher = urlPattern.matcher(this)
    while (matcher.find()) {
        val matchStart = matcher.start(1)
        val matchEnd = matcher.end()
        urls.add(this.substring(matchStart, matchEnd))
    }

    return urls
}

fun String.toMD5(): String = MessageDigest.getInstance("MD5").digest(toByteArray()).toHex()

/**
 * Converts String to Hexadecimal format.
 */
private fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }
