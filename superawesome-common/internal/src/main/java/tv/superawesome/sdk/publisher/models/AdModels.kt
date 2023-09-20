@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.properties.Properties
import tv.superawesome.sdk.publisher.extensions.mergeToMap

/**
 * TODO documentation.
 */
const val CPI_CAMPAIGN_ID = 1

@Serializable
data class Ad(
    @SerialName("campaign_type") val campaignType: Int,
    @SerialName("show_padlock") val showPadlock: Boolean,
    @SerialName("line_item_id") val lineItemId: Int,
    val test: Boolean,
    val creative: Creative,
    @SerialName("is_vpaid") val isVpaid: Boolean = false,
    @SerialName("rnd") val random: String?,
    @SerialName("ad_request_id") val adRequestId: String? = null,
) {
    fun isCPICampaign(): Boolean = campaignType == CPI_CAMPAIGN_ID
}
data class AdQueryBundle(
    val parameters: AdQuery,
    val options: Map<String, Any>?) {
    @ExperimentalSerializationApi
    fun build(): Map<String, Any> = Properties.mergeToMap(parameters, options)
}

@Serializable
data class AdQuery(
    val test: Boolean,
    val sdkVersion: String,
    val rnd: Int,
    val bundle: String,
    val name: String,
    @SerialName("dauid") val dauId: Int,
    val ct: Int,
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
    fun shouldShowPadlock(): Boolean = ad.showPadlock

    /**
     * Returns `baseUrl` and `html` data to use in `WebView`.
     */
    fun getDataPair(): Pair<String, String>? {
        val base = baseUrl ?: return null
        val html = html ?: return null
        return Pair(base, html)
    }
}
