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
 * @property rewardGivenAfterErrorDelay after how many millis of video playback will the reward be given
 * if the video playback errors out. Defaults to max Long value (disabled).
 */

data class FeatureFlags(
    val isAdResponseVASTEnabled: FeatureFlag<Unit> = DEFAULT_AD_RESPONSE_VAST_ENABLED,
    val isExoPlayerEnabled: FeatureFlag<Unit> = DEFAULT_IS_EXO_PLAYER_ENABLED,
    val videoStabilityFailsafeTimeout: FeatureFlag<Long> = DEFAULT_VIDEO_STABILITY_FAILSAFE,
    val rewardGivenAfterErrorDelay: FeatureFlag<Long> = DEFAULT_REWARD_GIVEN_AFTER_ERROR_DELAY,
) {
    companion object {

        /**
         * Default value for isAdResponseVASTEnabled.
         */
        val DEFAULT_AD_RESPONSE_VAST_ENABLED = FeatureFlag<Unit>(
            on = false,
            value = null,
            conditions = emptyList(),
        )

        /**
         * Default value for isExoPlayerEnabled.
         */
        val DEFAULT_IS_EXO_PLAYER_ENABLED = FeatureFlag<Unit>(
            on = false,
            value = null,
            conditions = emptyList(),
        )

        /**
         * Default value for videoStabilityFailsafeTimeout.
         */
        val DEFAULT_VIDEO_STABILITY_FAILSAFE = FeatureFlag(
            on = true,
            value = 2_500L,
            conditions = emptyList(),
        )

        /**
         * Default value for rewardGivenAfterErrorDelay.
         */
        val DEFAULT_REWARD_GIVEN_AFTER_ERROR_DELAY = FeatureFlag(
            on = true,
            value = Long.MAX_VALUE,
            conditions = emptyList(),
        )

        /**
         * Get the global feature flags from the json object.
         *
         * @param json the json object containing the feature flags.
         *
         * @return the FeatureFlags object.
         */
        fun getFlagsFromJSON(json: JSONObject): FeatureFlags =
            FeatureFlags(
                isAdResponseVASTEnabled = try {
                    FeatureFlag.fromJson(json, "isAdResponseVASTEnabled")
                } catch (e: JSONException) {
                    logException(e)
                    DEFAULT_AD_RESPONSE_VAST_ENABLED
                },
                isExoPlayerEnabled = try {
                    FeatureFlag.fromJson(json, "isExoPlayerEnabled")
                } catch (e: JSONException) {
                    logException(e)
                    DEFAULT_IS_EXO_PLAYER_ENABLED
                },
                videoStabilityFailsafeTimeout = try {
                    FeatureFlag.fromJson(json, "videoStabilityFailsafeTimeout")
                } catch (e: JSONException) {
                    logException(e)
                    DEFAULT_VIDEO_STABILITY_FAILSAFE
                },
                rewardGivenAfterErrorDelay = try {
                    FeatureFlag.fromJson(json, "rewardGivenAfterErrorDelay")
                } catch (e: JSONException) {
                    logException(e)
                    DEFAULT_REWARD_GIVEN_AFTER_ERROR_DELAY
                },
            )

        private fun logException(exception: Exception) {
            Log.w("SuperAwesome", "JSON Parsing error: ${exception.message}")
            println("JSON Parsing error: ${exception.message}")
        }
    }
}
