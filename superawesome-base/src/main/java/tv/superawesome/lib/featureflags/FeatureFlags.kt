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
 * @property userValue the value that determines flag rollout.
 */

data class FeatureFlags(
    val isAdResponseVASTEnabled: FeatureFlag<Boolean> = DEFAULT_AD_RESPONSE_VAST_ENABLED,
    val isExoPlayerEnabled: FeatureFlag<Boolean> = DEFAULT_IS_EXO_PLAYER_ENABLED,
    val videoStabilityFailsafeTimeout: FeatureFlag<Long> = DEFAULT_VIDEO_STABILITY_FAILSAFE,
    val rewardGivenAfterErrorDelay: FeatureFlag<Long> = DEFAULT_REWARD_GIVEN_AFTER_ERROR_DELAY,
    val userValue: Int = 100,
) {
    companion object {

        /**
         * Default value for isAdResponseVASTEnabled.
         */
        val DEFAULT_AD_RESPONSE_VAST_ENABLED = FeatureFlag(
            value = false,
            conditions = FlagConditions(),
            defaultValue = false,
        )

        /**
         * Default value for isExoPlayerEnabled.
         */
        val DEFAULT_IS_EXO_PLAYER_ENABLED = FeatureFlag(
            value = false,
            conditions = FlagConditions(),
            defaultValue = false,
        )

        /**
         * Default value for videoStabilityFailsafeTimeout.
         */
        val DEFAULT_VIDEO_STABILITY_FAILSAFE = FeatureFlag(
            value = 2_500L,
            conditions = FlagConditions(),
            defaultValue = 2_500L,
        )

        /**
         * Default value for rewardGivenAfterErrorDelay.
         */
        val DEFAULT_REWARD_GIVEN_AFTER_ERROR_DELAY = FeatureFlag(
            value = Long.MAX_VALUE,
            conditions = FlagConditions(),
            defaultValue = Long.MAX_VALUE,
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
                isAdResponseVASTEnabled = tryToGetFeatureFlag(
                    json,
                    "isAdResponseVASTEnabled",
                    DEFAULT_AD_RESPONSE_VAST_ENABLED,
                ),
                isExoPlayerEnabled = tryToGetFeatureFlag(
                    json,
                    "isExoPlayerEnabled",
                    DEFAULT_IS_EXO_PLAYER_ENABLED,
                ),
                videoStabilityFailsafeTimeout = tryToGetFeatureFlag(
                    json,
                    "videoStabilityFailsafeTimeout",
                    DEFAULT_VIDEO_STABILITY_FAILSAFE,
                ),
                rewardGivenAfterErrorDelay = tryToGetFeatureFlag(
                    json,
                    "rewardGivenAfterErrorDelay",
                    DEFAULT_REWARD_GIVEN_AFTER_ERROR_DELAY,
                ),
                userValue = FeatureFlagsManager.userValue,
            )

        private fun logException(exception: Exception) {
            Log.w("SuperAwesome", "JSON Parsing error: ${exception.message}")
        }

        private inline fun <reified T> tryToGetFeatureFlag(
            json: JSONObject,
            key: String,
            defaultValue: FeatureFlag<T>
        ): FeatureFlag<T> =
            try {
                FeatureFlag.fromJson<T>(json, key, defaultValue.value)
            } catch (e: JSONException) {
                logException(e)
                defaultValue
            }
    }
}
