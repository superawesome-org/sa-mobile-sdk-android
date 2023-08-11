package tv.superawesome.sdk.publisher.common.components

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

/**
 * Describes an URI encoder.
 */
interface EncoderType {

    /**
     * Encodes a string into valid URI.
     *
     * @param string a string to be encoded.
     * @return an encoded string.
     */
    fun encodeUri(string: String?): String

    /**
     * Encodes URL parameters from a given object map.
     *
     * @param map objects to be encoded.
     * @return an encoded string.
     */
    fun encodeUrlParamsFromObject(map: Map<String, Any?>): String
}

internal class Encoder : EncoderType {
    @Suppress("SwallowedException")
    override fun encodeUri(string: String?): String =
        if (string.isNullOrEmpty()) {
            ""
        } else {
            try {
                URLEncoder.encode(string, "UTF-8")
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
