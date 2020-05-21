package tv.superawesome.sdk.publisher.common.components

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

interface EncoderType {
    fun encodeUri(string: String?): String
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
}
