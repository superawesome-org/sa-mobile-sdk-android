package tv.superawesome.sdk.publisher.common.models

import tv.superawesome.sdk.publisher.common.extensions.toMD5

class UrlFileItem(val url: String) {
    val fileName: String = composeFileName()

    private fun composeFileName(): String = "${url.toMD5()}.${findExtension()}"
    private fun findExtension(): String {
        return url.split(".").lastOrNull() ?: ""
    }
}