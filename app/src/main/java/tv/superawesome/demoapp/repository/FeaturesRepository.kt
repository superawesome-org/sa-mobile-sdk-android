package tv.superawesome.demoapp.repository

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.TlsVersion
import tv.superawesome.demoapp.model.FeatureItem
import tv.superawesome.demoapp.model.Features

class FeaturesRepository(
    private val connectionSpec: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
        .tlsVersions(TlsVersion.TLS_1_2)
        .build(),
    private val okHttpClient: OkHttpClient = OkHttpClient
        .Builder()
        .connectionSpecs(listOf(connectionSpec))
        .build(),
) {
    fun fetchAllFeatures(): DataResult<List<FeatureItem>> {
        val request = Request.Builder().url(FEATURES_JSON_URL).build()
        val response = okHttpClient.newCall(request).execute()

        return if (response.isSuccessful) {
            val features = Json.decodeFromString<Features>(response.body?.string() ?: "")
            DataResult.Success(features.features)
        } else {
            DataResult.Failure(Error("Could not GET data from $FEATURES_JSON_URL"))
        }
    }

    companion object {
        private const val FEATURES_JSON_URL =
            "https://aa-sdk.s3.eu-west-1.amazonaws.com/placements.json"
    }
}
