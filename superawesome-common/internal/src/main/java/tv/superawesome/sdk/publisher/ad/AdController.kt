package tv.superawesome.sdk.publisher.ad

import android.content.Context
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.ui.video.player.VideoPlayerListener

/**
 * Provides facilities for loaded ads, such as closing the ad, handling clicks and sending
 * events and metrics.
 *
 * **Note:** The [AdController] shouldn't outlive the ad, once the ad has been closed and/or
 * isn't visible to the user anymore, the controller should be disposed of so a new controller can
 * be instantiated for a new loaded ad.
 */
interface AdController : AdEventHandler, AdPerformanceTrackerHandler {

    /**
     * The loaded ad that's tied to this controller.
     */
    override val adResponse: AdResponse

    /**
     * The listener attached to this ad. Should be set in [AdManager].
     */
    val listener: SAInterface?

    /**
     * The listener for video player callbacks, need to be attached once the video player
     * is up.
     */
    var videoPlayerListener: VideoPlayerListener?

    /**
     * Whether the ad has been closed.
     */
    var isAdClosed: Boolean

    /**
     * Whether this ad should present the padlock icon.
     */
    val shouldShowPadlock: Boolean
        get() = adResponse.shouldShowPadlock()

    /**
     * Closes the ad.
     */
    fun close()

    /**
     * Handles user clicking on the ad.
     *
     * @param url url to be opened in the device's browser.
     * @param context context of the activity where the ad lives.
     */
    fun handleAdClick(url: String, context: Context)

    /**
     * Handles user clicking on the padlock.
     *
     * @param context context of the activity where the ad lives.
     */
    fun handleSafeAdClick(context: Context)

    /**
     * Handles user clicking on a VAST video.
     *
     * @param context context of the activity where the ad lives.
     */
    fun handleVastAdClick(context: Context)
}
