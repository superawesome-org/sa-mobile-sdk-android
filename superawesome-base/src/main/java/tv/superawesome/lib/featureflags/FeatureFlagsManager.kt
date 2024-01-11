package tv.superawesome.lib.featureflags

import android.util.Log
import tv.superawesome.lib.sasession.session.ISASession

class FeatureFlagsManager(
    private val ffApi: GlobalFeatureFlagsApi = GlobalFeatureFlagsApi()
): SAFeatureFlagLoaderListener {

    var featureFlags = FeatureFlags()

    fun getFeatureFlags(session: ISASession) =
        ffApi.getGlobalFlags(this, session)

    override fun didLoadFeatureFlags(featureFlags: FeatureFlags) {
        this.featureFlags = featureFlags
    }

    override fun didFailToLoadFeatureFlags(error: Throwable) {
        // TODO: Report to metrics
        Log.d("SUPERAWESOME", "Error loading feature flags ${error.message}")
    }
}
