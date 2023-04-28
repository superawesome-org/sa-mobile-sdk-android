package tv.superawesome.sdk.publisher.common.datasources

import tv.superawesome.sdk.publisher.common.network.DataResult

internal interface NetworkDataSourceType {
    /**
     * Returns the response body from the network request from the given [url]
     * @param [url] The URL address of the requested resource
     */
    suspend fun getData(url: String): DataResult<String>

    /**
     * Downloads a file from the given [url]
     * @param [url] The URL address of the requested resource
     * Returns the local file path to the downloaded file
     */
    suspend fun downloadFile(url: String): DataResult<String>
}