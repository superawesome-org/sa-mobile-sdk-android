@file:Suppress("unused")

package tv.superawesome.sdk.publisher.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

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
 * @property openRtbPartnerId an optional openRTB partner id to be included in the request.
 */
@Serializable
data class DefaultAdRequest(
    override val test: Boolean,
    override val pos: Int,
    override val skip: Int,
    @SerialName("playbackmethod") override val playbackMethod: Int,
    @SerialName("startdelay") override val startDelay: Int,
    @SerialName("instl") override val install: Int,
    override val w: Int,
    override val h: Int,
    @Transient override val options: Map<String, Any>? = null,
    override val openRtbPartnerId: String? = null,
) : AdRequest {

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
}
