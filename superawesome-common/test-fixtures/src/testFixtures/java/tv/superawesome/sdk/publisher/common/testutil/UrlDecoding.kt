package tv.superawesome.sdk.publisher.common.testutil

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import java.net.URLDecoder

suspend inline fun <reified T> decodeDataParams(data: String?): T? {
    val safeData = data ?: return null
    val decoded = withContext(Dispatchers.IO) { URLDecoder.decode(safeData, "UTF-8") }

    return Json.decodeFromString(decoded)
}

fun decodeParams(url: HttpUrl?): Map<String, String> {
    val safeUrl = url ?: return emptyMap()

    return safeUrl.queryParameterNames.associateWith { name ->
        safeUrl.queryParameter(name) ?: "null"
    }
}
