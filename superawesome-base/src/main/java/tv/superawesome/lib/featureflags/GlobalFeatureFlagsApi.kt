package tv.superawesome.lib.featureflags

import org.json.JSONException
import org.json.JSONObject
import tv.superawesome.lib.sanetwork.request.SANetwork
import tv.superawesome.lib.sasession.session.ISASession
import java.io.IOException
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Class for fetching feature flags and serialising the object.
 */
class GlobalFeatureFlagsApi(
    private val executor: Executor = Executors.newSingleThreadExecutor(),
    private val timeout: Int = 15_000,
) {
    private fun getAwesomeAdsEndpoint(session: ISASession): String =
        session.s3Url + "/featureFlags/featureFlags.json"

    fun getGlobalFlags(listener: SAFeatureFlagLoaderListener, session: ISASession) {
        val endpoint = getAwesomeAdsEndpoint(session)
        val network = SANetwork(executor, timeout)

        network.sendGET(
            endpoint,
            JSONObject(),
            JSONObject(),
        ) { status: Int, data: String?, _: Boolean ->
            processFlags(data, status, listener)
        }
    }

    private fun processFlags(data: String?, status: Int, listener: SAFeatureFlagLoaderListener) {
        if (status != 200) {
            listener.didFailToLoadFeatureFlags(IOException("Response StatusCode: $status"))
        }

        data?.let {
            try {
                val jsonObject = JSONObject(it)
                listener.didLoadFeatureFlags(FeatureFlags.getFlagsFromJSON(jsonObject))
            } catch (e: JSONException) {
                listener.didFailToLoadFeatureFlags(e)
            }
        } ?: listener.didLoadFeatureFlags(FeatureFlags())
    }
}
