package tv.superawesome.demoapp.repository

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import tv.superawesome.demoapp.model.FeatureItem
import tv.superawesome.demoapp.model.Features
import tv.superawesome.sdk.publisher.common.network.DataResult

private val okHttpClient = OkHttpClient.Builder().build()
private const val FEATURES_JSON_URL = "https://aa-sdk.s3.eu-west-1.amazonaws.com/placements.json"

class FeaturesRepository {

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
}
