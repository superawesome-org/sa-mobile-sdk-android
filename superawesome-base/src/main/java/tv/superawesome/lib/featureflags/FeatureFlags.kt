package tv.superawesome.lib.featureflags

import android.util.Log
import org.json.JSONException
import org.json.JSONObject

/**
 * Model holding all feature flags.
 *
 * @property isAdResponseVASTEnabled whether the VAST tag in ad response feature is enabled. Defaults to `false`.
 * @property isExoPlayerEnabled whether the Exo Player video player is enabled. Defaults to `false`.
 * @property videoStabilityFailsafeTimeout the timeout for the video stability failsafe. Defaults to `2500`.
 */

data class FeatureFlags(
    val isAdResponseVASTEnabled: Boolean = DEFAULT_AD_RESPONSE_VAST_ENABLED,
    val isExoPlayerEnabled: Boolean = DEFAULT_IS_EXO_PLAYER_ENABLED,
    val videoStabilityFailsafeTimeout: Long = DEFAULT_VIDEO_STABILITY_FAILSAFE,
) {
    companion object {

        /**
         * Default value for isAdResponseVASTEnabled.
         */
        const val DEFAULT_AD_RESPONSE_VAST_ENABLED = false

        /**
         * Default value for isExoPlayerEnabled.
         */
        const val DEFAULT_IS_EXO_PLAYER_ENABLED = false

        /**
         * Default value for videoStabilityFailsafeTimeout.
         */
        const val DEFAULT_VIDEO_STABILITY_FAILSAFE: Long = 2_500

        /**
         * Get the global feature flags from the json object.
         *
         * @param json the json object containing the feature flags.
         *
         * @return the FeatureFlags object.
         */
        fun getFlagsFromJSON(json: JSONObject): FeatureFlags {
            val isAdResponseVASTEnabled: Boolean = try {
                json.getBoolean("isAdResponseVASTEnabled")
            } catch(e: JSONException) {
                logException(e)
                DEFAULT_AD_RESPONSE_VAST_ENABLED
            }
            val isExoPlayerEnabled = try {
                json.getBoolean("isExoPlayerEnabled")
            } catch(e: JSONException) {
                logException(e)
                DEFAULT_IS_EXO_PLAYER_ENABLED
            }
            val videoStabilityFailsafeTimeout = try {
                json.getLong("videoStabilityFailsafeTimeout")
            } catch(e: JSONException) {
                logException(e)
                DEFAULT_VIDEO_STABILITY_FAILSAFE
            }

            return FeatureFlags(
                isAdResponseVASTEnabled = isAdResponseVASTEnabled,
                isExoPlayerEnabled = isExoPlayerEnabled,
                videoStabilityFailsafeTimeout = videoStabilityFailsafeTimeout,
            )
        }

        private fun logException(exception: Exception) {
            Log.w("SuperAwesome", "JSON Parsing error: ${exception.message}")
        }
    }
}
