package tv.superawesome.lib.featureflags

import android.util.Log
import tv.superawesome.lib.sasession.session.ISASession

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

    /**
     * Get the global feature flags from the API.
     */
    fun getFeatureFlags(session: ISASession) =
        ffApi.getGlobalFlags(this, session)

    override fun didLoadFeatureFlags(featureFlags: FeatureFlags) {
        this.featureFlags = featureFlags
    }

    override fun didFailToLoadFeatureFlags(error: Throwable) {
        Log.w("SuperAwesome", "Error loading feature flags ${error.message}")
    }
}
