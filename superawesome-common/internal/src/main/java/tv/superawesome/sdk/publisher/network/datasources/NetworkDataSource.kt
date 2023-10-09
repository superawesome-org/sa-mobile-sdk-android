package tv.superawesome.sdk.publisher.network.datasources

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.sink
import tv.superawesome.sdk.publisher.components.Logger
import tv.superawesome.sdk.publisher.models.UrlFileItem
import java.io.File

interface NetworkDataSourceType {
    /**
     * Returns the response body from the network request from the given [url].
     * @param [url] The URL address of the requested resource
     */
    suspend fun getData(url: String): Result<String>

    /**
     * Downloads a file from the given [url].
     * @param [url] The URL address of the requested resource
     * @return the local file path to the downloaded file
     */
    suspend fun downloadFile(url: String): Result<String>
}


class OkHttpNetworkDataSource(
    private val client: OkHttpClient,
    private val cacheDir: File,
    private val logger: Logger,
) : NetworkDataSourceType {
    override suspend fun getData(url: String): Result<String> {
        logger.info("getData:url: $url")
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        return if (response.isSuccessful) {
            // Safe to assume that body is non-null since it's
            // from a Response returned from `execute()`.
            Result.success(response.body!!.string())
        } else {
            Result.failure(Error("Could not GET data from $url"))
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override suspend fun downloadFile(url: String): Result<String> {
        logger.info("downloadFile")
        return try {
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            val urlFileItem = UrlFileItem(url)
            val downloadedFile = File(cacheDir, urlFileItem.fileName)


            val sink = downloadedFile.sink().buffer()
            sink.writeAll(response.body!!.source())
            withContext(Dispatchers.IO) {
                sink.close()
            }

            logger.success("File download successful with path: ${downloadedFile.absolutePath}")
            Result.success(downloadedFile.absolutePath)
        } catch (e: Exception) {
            logger.error("File download error", e)
            Result.failure(Error(e.localizedMessage))
        }
    }
}
