package tv.superawesome.sdk.publisher.ad

import android.content.Context
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.ui.video.player.VideoPlayerListener

interface NewAdController : AdEventHandler, AdPerformanceTrackerHandler {

    override val adResponse: AdResponse

    val adConfig: AdConfig

    val listener: SAInterface?

    var videoPlayerListener: VideoPlayerListener?

    var isAdClosed: Boolean

    val shouldShowPadlock: Boolean
        get() = adResponse.shouldShowPadlock()

    fun close()

    fun handleAdClick(url: String, context: Context)
    fun handleSafeAdClick(context: Context)
    fun handleVastAdClick(context: Context)
}