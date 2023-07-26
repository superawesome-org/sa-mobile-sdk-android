package tv.superawesome.sdk.publisher.common.ui.common

import android.content.Context
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.models.SAInterface

@Suppress("TooManyFunctions", "ComplexInterface")
interface AdControllerType {
    var config: Config
    var closed: Boolean
    var currentAdResponse: AdResponse?
    var delegate: SAInterface?
    var videoListener: VideoPlayerListener?
    val shouldShowPadlock: Boolean

    fun startTimingForLoadTime()
    fun trackLoadTime()
    fun startTimingForDwellTime()
    fun trackDwellTime()
    fun startTimingForCloseButtonPressed()
    fun trackCloseButtonPressed()
    fun triggerImpressionEvent(placementId: Int)
    fun triggerViewableImpression(placementId: Int)

    fun load(placementId: Int, request: AdRequest)
    fun load(placementId: Int, lineItemId: Int, creativeId: Int, request: AdRequest)
    fun play(placementId: Int): AdResponse?

    fun handleAdTap(url: String, context: Context)
    fun handleSafeAdTap(context: Context)
    fun handleAdTapForVast(context: Context)
    fun close()
    fun hasAdAvailable(placementId: Int): Boolean
    fun adFailedToShow()
    fun adShown()
    fun adEnded()
    fun adPlaying()
    fun adPaused()

    fun peekAdResponse(placementId: Int): AdResponse?

    fun clearCache()

    interface VideoPlayerListener {
        fun didRequestVideoPause()
        fun didRequestVideoPlay()
    }
}
