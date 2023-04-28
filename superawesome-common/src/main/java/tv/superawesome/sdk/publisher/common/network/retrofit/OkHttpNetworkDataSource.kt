package tv.superawesome.sdk.publisher.common.network.retrofit

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.sink
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.common.models.UrlFileItem
import tv.superawesome.sdk.publisher.common.network.DataResult
import java.io.File

internal class OkHttpNetworkDataSource(
    private val client: OkHttpClient,
    private val applicationContext: Context,
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

    override suspend fun downloadFile(url: String): DataResult<String> {
        logger.info("downloadFile")
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()

        val urlFileItem = UrlFileItem(url)
        val downloadedFile = File(applicationContext.cacheDir, urlFileItem.fileName)

        return try {
            val sink = downloadedFile.sink().buffer()
            sink.writeAll(response.body!!.source())
            sink.close()

            logger.success("File download successful with path: ${downloadedFile.absolutePath}")
            DataResult.Success(downloadedFile.absolutePath)
        } catch (e: Exception) {
            logger.error("File download error", e)
            DataResult.Failure(Error(e.localizedMessage))
        }
    }
}