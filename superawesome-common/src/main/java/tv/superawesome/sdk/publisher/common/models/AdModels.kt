@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

const val CPI_CAMPAIGN_ID = 1

@Serializable
data class Ad(
    @SerialName("campaign_type") val campaignType: Int,
    @SerialName("show_padlock") val showPadlock: Boolean,
    @SerialName("line_item_id") val lineItemId: Int,
    val test: Boolean,
    val creative: Creative,
    @SerialName("is_vpaid") val isVpaid: Boolean = false
) {
    fun isCPICampaign(): Boolean = campaignType == CPI_CAMPAIGN_ID
    fun shouldShowPadlock(): Boolean = showPadlock && !creative.isKSF
}

@Serializable
data class AdQuery(
    val test: Boolean,
    val sdkVersion: String,
    val rnd: Int,
    val bundle: String,
    val name: String,
    @SerialName("dauid") val dauId: Int,
    val ct: ConnectionType,
    val lang: String,
    val device: String,
    val pos: Int,
    val skip: Int,
    @SerialName("playbackmethod") val playbackMethod: Int,
    @SerialName("startdelay") val startDelay: Int,
    @SerialName("instl") val install: Int,
    val w: Int,
    val h: Int,
    val timestamp: Long
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
    fun isVideo(): Boolean = ad.creative.format == CreativeFormatType.Video
    fun shouldShowPadlock(): Boolean = ad.shouldShowPadlock()

    /**
     * Returns `baseUrl` and `html` data to use in `WebView`
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
    @SerialName("playbackmethod") val playbackMethod: Int,
    @SerialName("startdelay") val startDelay: Int,
    @SerialName("instl") val install: Int,
    val w: Int,
    val h: Int,
) {

    @Transient
    val propertyString = mapOf(
        "pos" to pos,
        "skip" to skip,
        "playbackmethod" to playbackMethod,
        "startdelay" to startDelay,
        "instl" to install,
        "w" to w,
        "h" to h
    )

    companion object {
        /** The playback sound is on at the start */
        const val PlaybackSoundOnScreen = 5

        /** The playback sound is off at the start */
        const val PlaybackSoundOffScreen = 2
    }

    /** Specify if the ad is in full screen or not */
    enum class FullScreen(val value: Int) {
        On(1), Off(0)
    }

    /** Start delay cases */
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

    /** Specify the position of the ad */
    enum class Position(val value: Int) {
        AboveTheFold(1),
        BelowTheFold(3),
        FullScreen(7),
    }

    /** Specify if the ad can be skipped */
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

enum class SAEvent(val value: String) {
    /** ad was loaded successfully and is ready to be displayed */
    AdLoaded("adLoaded"),
    AdEmpty("adEmpty"),

    /** ad was not loaded successfully and will not be able to play */
    AdFailedToLoad("adFailedToLoad"),

    /** ad was previously loaded in an interstitial, video or app wall queue */
    AdAlreadyLoaded("adAlreadyLoaded"),

    /** triggered once when the ad first displays */
    AdShown("adShown"),
    AdFailedToShow("adFailedToShow"),

    /**  triggered every time the ad gets clicked */
    AdClicked("adClicked"),
    AdEnded("adEnded"),

    /** triggered once when the ad is closed */
    AdClosed("adClosed")
}

public fun interface SAInterface {
    /**
     * Only method that needs to be implemented to be able to receive events back from the SDK
     *
     * @param placementId   the Ad Placement Id the event happened for
     * @param event         the type of the event, as an SAEvent enum variable
     */
    fun onEvent(placementId: Int, event: SAEvent)
}
