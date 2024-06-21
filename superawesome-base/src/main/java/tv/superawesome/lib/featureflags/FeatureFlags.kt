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
 * @property fireEventsOnceEnabled whether the flag to control if events are being fired just once is enabled.
 * Only works for videos. Defaults to `false`.
 */

data class FeatureFlags(
    val isAdResponseVASTEnabled: Boolean = DEFAULT_AD_RESPONSE_VAST_ENABLED,
    val isExoPlayerEnabled: Boolean = DEFAULT_IS_EXO_PLAYER_ENABLED,
    val videoStabilityFailsafeTimeout: Long = DEFAULT_VIDEO_STABILITY_FAILSAFE,
    val rewardGivenAfterErrorDelay: Long = DEFAULT_REWARD_GIVEN_AFTER_ERROR_DELAY,
    val fireEventsOnceEnabled: Boolean = DEFAULT_FIRE_EVENTS_ONCE_ENABLED,
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
        const val DEFAULT_VIDEO_STABILITY_FAILSAFE = 2_500L

        /**
         * Default value for rewardGivenAfterErrorDelay.
         */
        const val DEFAULT_REWARD_GIVEN_AFTER_ERROR_DELAY = Long.MAX_VALUE

        /**
         * Default values for fireEventsOnceEnabled.
         */
        const val DEFAULT_FIRE_EVENTS_ONCE_ENABLED = false

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
            val rewardGivenAfterErrorDelay = try {
                json.getLong("rewardGivenAfterErrorDelay")
            } catch (e: JSONException) {
                logException(e)
                DEFAULT_REWARD_GIVEN_AFTER_ERROR_DELAY
            }
            val fireEventsOnceEnabled = try {
                json.getBoolean("fireEventsOnceEnabled")
            } catch (e: JSONException) {
                logException(e)
                DEFAULT_FIRE_EVENTS_ONCE_ENABLED
            }

            return FeatureFlags(
                isAdResponseVASTEnabled = isAdResponseVASTEnabled,
                isExoPlayerEnabled = isExoPlayerEnabled,
                videoStabilityFailsafeTimeout = videoStabilityFailsafeTimeout,
                rewardGivenAfterErrorDelay = rewardGivenAfterErrorDelay,
                fireEventsOnceEnabled = fireEventsOnceEnabled,
            )
        }

        private fun logException(exception: Exception) {
            Log.w("SuperAwesome", "JSON Parsing error: ${exception.message}")
        }
    }
}
