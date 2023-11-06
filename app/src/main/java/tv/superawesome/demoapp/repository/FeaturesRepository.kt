package tv.superawesome.demoapp.repository

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.TlsVersion
import tv.superawesome.demoapp.SDKEnvironment
import tv.superawesome.demoapp.model.FeatureItem
import tv.superawesome.demoapp.model.Features

class FeaturesRepository(
    private val environment: SDKEnvironment,
    private val connectionSpec: ConnectionSpec,
    private val okHttpClient: OkHttpClient = OkHttpClient
        .Builder()
        .connectionSpecs(listOf(connectionSpec))
        .build(),
) {
    fun fetchAllFeatures(): DataResult<List<FeatureItem>> {
        val url = if (environment == SDKEnvironment.UITesting) {
            LOCALHOST_URL + FEATURES_JSON_URL
        } else {
            S3_URL + FEATURES_JSON_URL
        }

        val request = Request.Builder().url(url).build()
        val response = okHttpClient.newCall(request).execute()

        return if (response.isSuccessful) {
            val features = Json.decodeFromString<Features>(response.body()?.string() ?: "")
            DataResult.Success(features.features)
        } else {
            DataResult.Failure(Error("Could not GET data from $url"))
        }
    }

    companion object {
        private const val S3_URL = "https://aa-sdk.s3.eu-west-1.amazonaws.com"
        private const val LOCALHOST_URL = "http://localhost:8080"
        private const val FEATURES_JSON_URL = "/placements.json"
    }
}
