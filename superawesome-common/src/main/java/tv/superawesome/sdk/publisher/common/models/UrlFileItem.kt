package tv.superawesome.sdk.publisher.common.models

import tv.superawesome.sdk.publisher.common.extensions.toMD5

internal class UrlFileItem(val url: String) {
    val fileName: String = composeFileName()

    private fun composeFileName(): String = "${url.toMD5()}.${findExtension()}"
    private fun findExtension(): String = url.split(".").lastOrNull() ?: ""
}
