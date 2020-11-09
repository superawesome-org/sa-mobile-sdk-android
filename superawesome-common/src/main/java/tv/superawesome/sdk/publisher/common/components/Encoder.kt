package tv.superawesome.sdk.publisher.common.components

import kotlinx.serialization.ExperimentalSerializationApi
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

interface EncoderType {
    fun encodeUri(string: String?): String
    fun encodeUrlParamsFromObject(map: Map<String, Any?>): String
}

class Encoder : EncoderType {
    companion object {
        const val utf8 = "UTF-8"
    }

    override fun encodeUri(string: String?): String =
            if (string.isNullOrEmpty()) {
                ""
            } else try {
                URLEncoder.encode(string, utf8)
            } catch (e: UnsupportedEncodingException) {
                ""
            }

    @ExperimentalSerializationApi
    override fun encodeUrlParamsFromObject(map: Map<String, Any?>): String {
        val params = map.entries
                .map { entry -> "${entry.key}=${entry.value}" }
                .joinToString(separator = "&") { element -> element }
        return encodeUri(params)
    }
}
