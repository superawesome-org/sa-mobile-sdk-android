package tv.superawesome.sdk.publisher.common.models

import kotlinx.serialization.Serializable

@Serializable
data class Ad(
        val advertiserId: Int,
        val publisherId: Int,
        val moat: Float,
        val is_fill: Boolean,
        val is_fallback: Boolean,
        val campaign_type: Int,
        val is_house: Boolean,
        val safe_ad_approved: Boolean,
        val show_padlock: Boolean,
        val line_item_id: Int,
        val test: Boolean,
        val app: Int,
        val device: String,
        val creative: Creative
)

@Serializable
data class AdQuery(
        val test: Boolean,
        val sdkVersion: String,
        val rnd: Int,
        val bundle: String,
        val name: String,
        val dauid: Int,
        val ct: ConnectionType,
        val lang: String,
        val device: String,
        val pos: Int,
        val skip: Int,
        val playbackmethod: Int,
        val startdelay: Int,
        val instl: Int,
        val w: Int,
        val h: Int
)

data class AdResponse(
        val placementId: Int,
        val ad: Ad,
        var html: String? = null,
        var vast: VastAd? = null,
        var baseUrl: String? = null,
        var filePath: String? = null
)

@Serializable
data class AdRequest(
        val test: Boolean,
        val pos: Int,
        val skip: Int,
        val playbackmethod: Int,
        val startdelay: Int,
        val instl: Int,
        val w: Int,
        val h: Int
) {
    companion object {
        /// The playback method
        val PlaybackSoundOnScreen = 5
    }

    /// Specify if the ad is in full screen or not
    enum class FullScreen(val value: Int) {
        On(1), Off(0)
    }

    /// Start delay cases
    enum class StartDelay(val value: Int) {
        PostRoll(-2),
        GenericMidRoll(-1),
        PreRoll(0),
        MidRoll(1),
    }

    /// Specify the position of the ad
    enum class Position(val value: Int) {
        AboveTheFold(1),
        BelowTheFold(3),
        FullScreen(7),
    }

    /// Specify if the ad can be skipped
    enum class Skip(val value: Int) {
        No(0),
        Yes(1),
    }
}