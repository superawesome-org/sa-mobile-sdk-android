package tv.superawesome.sdk.publisher.common.datasources

import tv.superawesome.sdk.publisher.common.network.DataResult

interface NetworkDataSourceType {
    suspend fun getData(url: String): DataResult<String>
    suspend fun downloadFile(url: String): DataResult<String>
}
