package tv.superawesome.lib.featureflags

import android.util.Log

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
}

