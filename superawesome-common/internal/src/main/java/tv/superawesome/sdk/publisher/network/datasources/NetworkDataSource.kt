package tv.superawesome.sdk.publisher.network.datasources

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.sink
import tv.superawesome.sdk.publisher.components.Logger
import tv.superawesome.sdk.publisher.models.UrlFileItem
import tv.superawesome.sdk.publisher.network.DataResult
import java.io.File

interface NetworkDataSourceType {
    /**
     * Returns the response body from the network request from the given [url].
     * @param [url] The URL address of the requested resource
     */
    suspend fun getData(url: String): DataResult<String>

    /**
     * Downloads a file from the given [url].
     * @param [url] The URL address of the requested resource
     * @return the local file path to the downloaded file
     */
    suspend fun downloadFile(url: String): DataResult<String>
}


class OkHttpNetworkDataSource(
    private val client: OkHttpClient,
    private val applicationContext: Context,
    private val logger: Logger,
) : NetworkDataSourceType {
    override suspend fun getData(url: String): DataResult<String> {
        logger.info("getData:url: $url")
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        return if (response.isSuccessful) {
            // Safe to assume that body is non-null since it's
            // from a Response returned from `execute()`.
            DataResult.Success(response.body!!.string())
        } else {
            DataResult.Failure(Error("Could not GET data from $url"))
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override suspend fun downloadFile(url: String): DataResult<String> {
        logger.info("downloadFile")
        return try {
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            val urlFileItem = UrlFileItem(url)
            val downloadedFile = File(applicationContext.cacheDir, urlFileItem.fileName)


            val sink = downloadedFile.sink().buffer()
            sink.writeAll(response.body!!.source())
            withContext(Dispatchers.IO) {
                sink.close()
            }

            logger.success("File download successful with path: ${downloadedFile.absolutePath}")
            DataResult.Success(downloadedFile.absolutePath)
        } catch (e: Exception) {
            logger.error("File download error", e)
            DataResult.Failure(Error(e.localizedMessage))
        }
    }
}
