@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.properties.Properties
import tv.superawesome.sdk.publisher.common.extensions.mergeToMap

/**
 * TODO documentation.
 */
const val CPI_CAMPAIGN_ID = 1

@Serializable
internal data class Ad(
    @SerialName("campaign_type") val campaignType: Int,
    @SerialName("publisherId") val publisherId: Int,
    @SerialName("campaign_id") val campaignId: Int,
    @SerialName("show_padlock") val showPadlock: Boolean,
    @SerialName("line_item_id") val lineItemId: Int,
    val test: Boolean,
    val creative: Creative,
    @SerialName("is_vpaid") val isVpaid: Boolean = false,
    @SerialName("rnd") val random: String?,
) {
    fun isCPICampaign(): Boolean = campaignType == CPI_CAMPAIGN_ID
    fun shouldShowPadlock(): Boolean = showPadlock && !creative.isKSF
}
internal data class AdQueryBundle(
    val parameters: AdQuery,
    val options: Map<String, Any>?) {
    @ExperimentalSerializationApi
    fun build(): Map<String, Any> = Properties.mergeToMap(parameters, options)
}

@Serializable
internal data class AdQuery(
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

internal data class AdResponse(
    val placementId: Int,
    val ad: Ad,
    val requestOptions: Map<String, Any>? = null,
    var html: String? = null,
    var vast: VastAd? = null,
    var baseUrl: String? = null,
    var filePath: String? = null,
    var referral: String? = null,
) {
    /**
     * Returns whether the ad is VPAID or not.
     */
    fun isVpaid(): Boolean = ad.isVpaid

    /**
     * Returns whether the ad is a video or not.
     */
    fun isVideo(): Boolean = ad.creative.format == CreativeFormatType.Video

    /**
     * Returns whether the ad should show a Padlock or not.
     */
    fun shouldShowPadlock(): Boolean = ad.shouldShowPadlock()

    /**
     * Returns `baseUrl` and `html` data to use in `WebView`.
     */
    fun getDataPair(): Pair<String, String>? {
        val base = baseUrl ?: return null
        val html = html ?: return null
        return Pair(base, html)
    }
}

/**
 * Defines an Ad request.
 *
 * @property test whether this is a test or not.
 * @property pos position of the ad, as given by [Position].
 * @property skip whether the ad can be skipped or not. as given by [Skip].
 * @property playbackMethod
 * @property startDelay the start delay as given by [StartDelay].
 * @property install
 * @property w width of the ad, in pixels.
 * @property h height of the ad, in pixels.
 * @property options any extra options.
 */
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
    @Transient
    val options: Map<String, Any>? = null
) {

    /**
     * Map of request properties and values.
     */
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
        /** The playback sound is on at the start. */
        const val PlaybackSoundOnScreen = 5

        /** The playback sound is off at the start. */
        const val PlaybackSoundOffScreen = 2
    }

    /**
     * Specify if the ad is in full screen or not.
     *
     * @property value flag value.
     */
    enum class FullScreen(val value: Int) {
        On(1), Off(0)
    }

    /**
     * Start delay cases.
     *
     * @property value flag value.
     */
    public enum class StartDelay(val value: Int) {
        PostRoll(-2),
        GenericMidRoll(-1),
        PreRoll(0),
        MidRoll(1);

        companion object {

            /**
             * Returns the [StartDelay] from a given integer [value] or null if not found.
             */
            @JvmStatic
            fun fromValue(value: Int) = entries.firstOrNull { it.value == value }
        }
    }

    /**
     * Specify the position of the ad.
     *
     * @property value flag value.
     */
    @Suppress("MagicNumber")
    enum class Position(val value: Int) {
        AboveTheFold(1),
        BelowTheFold(3),
        FullScreen(7),
    }

    /**
     * Specify if the ad can be skipped.
     *
     * @property value value representing the skip state.
     */
    enum class Skip(val value: Int) {
        /**
         * Can't be skipped.
         */
        No(0),

        /**
         * Can be skipped.
         */
        Yes(1),
    }
}
