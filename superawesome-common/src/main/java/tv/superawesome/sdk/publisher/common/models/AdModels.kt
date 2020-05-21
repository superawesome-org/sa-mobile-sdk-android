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
)