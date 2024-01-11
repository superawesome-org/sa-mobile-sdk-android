package tv.superawesome.lib.featureflags

import org.json.JSONException
import org.json.JSONObject

/**
 * Model holding all feature flags.
 *
 * @property isAdResponseVASTEnabled whether the VAST tag in ad response feature is enabled. Defaults to `false`.
 */

data class FeatureFlags(
    val isAdResponseVASTEnabled: Boolean = DEFAULT_AD_RESPONSE_VAST_ENABLED,
    val isExoPlayerEnabled: Boolean = DEFAULT_IS_EXO_PLAYER_ENABLED,
    val videoStabilityFailsafeTimeout: Long = DEFAULT_VIDEO_STABILITY_FAILSAFE,
) {
    companion object {

        const val DEFAULT_AD_RESPONSE_VAST_ENABLED = false
        const val DEFAULT_IS_EXO_PLAYER_ENABLED = false
        const val DEFAULT_VIDEO_STABILITY_FAILSAFE: Long = 2_500

        fun getFlagsFromJSON(json: JSONObject): FeatureFlags {
            val isAdResponseVASTEnabled: Boolean = try {
                json.getBoolean("isAdResponseVASTEnabled")
            } catch(e: JSONException) {
                DEFAULT_AD_RESPONSE_VAST_ENABLED
            }
            val isExoPlayerEnabled = try {
                json.getBoolean("isExoPlayerEnabled")
            } catch(e: JSONException) {
                DEFAULT_IS_EXO_PLAYER_ENABLED
            }
            val videoStabilityFailsafeTimeout = try {
                json.getLong("videoStabilityFailsafeTimeout")
            } catch(e: JSONException) {
                DEFAULT_VIDEO_STABILITY_FAILSAFE
            }

            return FeatureFlags(
                isAdResponseVASTEnabled = isAdResponseVASTEnabled,
                isExoPlayerEnabled = isExoPlayerEnabled,
                videoStabilityFailsafeTimeout = videoStabilityFailsafeTimeout,
            )
        }
    }
}
