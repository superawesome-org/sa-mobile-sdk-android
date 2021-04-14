package tv.superawesome.sdk.publisher.common.models

import kotlinx.serialization.Serializable

const val CPI_CAMPAIGN_ID = 1

@Serializable
data class Ad(
    val advertiserId: Int? = null,
    val publisherId: Int,
    val moat: Float,
    val is_fill: Boolean,
    val is_fallback: Boolean,
    val campaign_type: Int,
    val campaign_id: Int,
    val is_house: Boolean,
    val safe_ad_approved: Boolean,
    val show_padlock: Boolean,
    val line_item_id: Int,
    val test: Boolean,
    val app: Int,
    val device: String,
    val creative: Creative
) {
    fun isCPICampaign(): Boolean = campaign_type == CPI_CAMPAIGN_ID
}

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
    val h: Int,
)

data class AdResponse(
    val placementId: Int,
    val ad: Ad,
    var html: String? = null,
    var vast: VastAd? = null,
    var baseUrl: String? = null,
    var filePath: String? = null,
    var referral: String? = null,
) {
    fun isVideo(): Boolean = ad.creative.format == CreativeFormatType.video

    /**
     * Returns `baseUrl` and `html` data to show in the `WebView`
     */
    fun getDataPair(): Pair<String, String>? {
        val base = baseUrl ?: return null
        val html = html ?: return null
        return Pair(base, html)
    }
}

@Serializable
data class AdRequest(
    val test: Boolean,
    val pos: Int,
    val skip: Int,
    val playbackmethod: Int,
    val startdelay: Int,
    val instl: Int,
    val w: Int,
    val h: Int,
) {
    companion object {
        // / The playback method
        const val PlaybackSoundOnScreen = 5
    }

    // / Specify if the ad is in full screen or not
    enum class FullScreen(val value: Int) {
        On(1), Off(0)
    }

    // / Start delay cases
    enum class StartDelay(val value: Int) {
        PostRoll(-2),
        GenericMidRoll(-1),
        PreRoll(0),
        MidRoll(1);

        companion object {
            private val values = values()
            fun fromValue(value: Int) = values.firstOrNull { it.value == value }
        }
    }

    // / Specify the position of the ad
    enum class Position(val value: Int) {
        AboveTheFold(1),
        BelowTheFold(3),
        FullScreen(7),
    }

    // / Specify if the ad can be skipped
    enum class Skip(val value: Int) {
        No(0),
        Yes(1),
    }
}

/**
 * This enum holds all the possible callback values that an ad sends during its lifetime
 *  - adLoaded:         ad was loaded successfully and is ready to be displayed
 *  - adEmpty:          ad was empty
 *  - adFailedToLoad:   ad was not loaded successfully and will not be able to play
 *  - adAlreadyLoaded   ad was previously loaded in an interstitial, video or app wall queue
 *  - adShown:          triggered once when the ad first displays
 *  - adFailedToShow:   for some reason the ad failed to show; technically this should
 *                      never happen nowadays
 *  - adClicked:        triggered every time the ad gets clicked
 *  - adClosed:         triggered once when the ad is closed;
 */

enum class SAEvent {
    /** ad was loaded successfully and is ready to be displayed */
    AdLoaded,
    AdEmpty,
    /** ad was not loaded successfully and will not be able to play */
    AdFailedToLoad,
    /** ad was previously loaded in an interstitial, video or app wall queue */
    AdAlreadyLoaded,
    /** triggered once when the ad first displays */
    AdShown,
    AdFailedToShow,
    /**  triggered every time the ad gets clicked */
    AdClicked,
    AdEnded,
    /** triggered once when the ad is closed */
    AdClosed
}

fun interface SAInterface {
    /**
     * Only method that needs to be implemented to be able to receive events back from the SDK
     *
     * @param placementId   the Ad Placement Id the event happened for
     * @param event         the type of the event, as an SAEvent enum variable
     */
    fun onEvent(placementId: Int, event: SAEvent)
}
