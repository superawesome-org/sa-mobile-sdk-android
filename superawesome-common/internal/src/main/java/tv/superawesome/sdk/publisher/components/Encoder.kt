package tv.superawesome.sdk.publisher.components

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class Encoder(private val charset: String = "UTF-8"): EncoderType {
    @Suppress("SwallowedException")
    override fun encodeUri(string: String?): String =
        if (string.isNullOrEmpty()) {
            ""
        } else {
            try {
                URLEncoder.encode(string, charset)
            } catch (e: UnsupportedEncodingException) {
                ""
            }
        }

    override fun encodeUrlParamsFromObject(map: Map<String, Any?>): String {
        val params = map.entries
            .map { entry -> "${entry.key}=${entry.value}" }
            .joinToString(separator = "&") { element -> element }
        return encodeUri(params)
    }
}
