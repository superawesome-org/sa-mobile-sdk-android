package tv.superawesome.lib.featureflags

import android.util.Log
import java.util.Date
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Manager for obtaining global feature flags.
 */
class FeatureFlagsManager(
    private val ffApi: GlobalFeatureFlagsApi = GlobalFeatureFlagsApi()
): SAFeatureFlagLoaderListener {

    /**
     * Global feature flags.
     */
    var featureFlags = FeatureFlags()
        private set

    /**
     * Get the global feature flags from the API.
     */
    @Suppress("TooGenericExceptionCaught")
    fun fetchFeatureFlags() =
        try {
            ffApi.getGlobalFlags(this)
        } catch (e: Exception) {
            Log.w("SuperAwesome", "Failed to fetch feature flags", e)
        }

    override fun didLoadFeatureFlags(featureFlags: FeatureFlags) {
        this.featureFlags = featureFlags
    }

    override fun didFailToLoadFeatureFlags(error: Throwable) {
        Log.w("SuperAwesome", "Error loading feature flags ${error.message}")
    }

    companion object {
        /**
         * Upon initialization, rolls a number that will be used to determine if the flag will be
         * enabled or not for this given user, depending on the "percentage" rollout value in the
         * feature flag.
         */
        val userValue = Random(seed = Date().time).nextInt(0..100)
    }
}

