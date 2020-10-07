package tv.superawesome.sdk.publisher.networking.retrofit

import okhttp3.OkHttpClient
import tv.superawesome.sdk.publisher.common.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.common.network.DataResult

class OkHttpNetworkDataSource(val client: OkHttpClient) : NetworkDataSourceType {
    override suspend fun getData(url: String): DataResult<String> {
        return DataResult.Success("")
    }

    override suspend fun downloadFile(url: String): DataResult<String> {
        return DataResult.Success("")
    }

}