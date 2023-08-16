package tv.superawesome.sdk.publisher.common.network.datasources

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.sink
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.models.UrlFileItem
import tv.superawesome.sdk.publisher.common.network.DataResult
import java.io.File

internal interface NetworkDataSourceType {
    /**
     * Returns the response body from the network request from the given [url].
     * @param [url] The URL address of the requested resource.
     */
    suspend fun getData(url: String): DataResult<String>

    /**
     * Downloads a file from the given [url].
     * @param [url] The URL address of the requested resource.
     * @param [destDirectory] The destination of the file on the device.
     * @return the local file path to the downloaded file.
     */
    suspend fun downloadFile(url: String, destDirectory: File): DataResult<String>
}

internal class OkHttpNetworkDataSource(
    private val client: OkHttpClient,
    private val logger: Logger,
) : NetworkDataSourceType {
    override suspend fun getData(url: String): DataResult<String> {
        logger.info("getData:url: $url")
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        return if (response.isSuccessful) {
            DataResult.Success(response.body?.string() ?: "")
        } else {
            DataResult.Failure(Error("Could not GET data from $url"))
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override suspend fun downloadFile(
        url: String,
        destDirectory: File,
    ): DataResult<String> {
        logger.info("downloadFile from: $url")
        return try {
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            val urlFileItem = UrlFileItem(url)
            val downloadedFile = File(destDirectory, urlFileItem.fileName)


            val sink = downloadedFile.sink().buffer()
            sink.writeAll(response.body!!.source())
            withContext(Dispatchers.IO) {
                sink.close()
            }

            logger.success(
                "File download successful for: $url path: ${downloadedFile.absolutePath}",
                )
            DataResult.Success(downloadedFile.absolutePath)
        } catch (e: Exception) {
            logger.error("File download error for: $url error: ${e.message}", e)
            DataResult.Failure(Error(e.localizedMessage))
        }
    }
}
