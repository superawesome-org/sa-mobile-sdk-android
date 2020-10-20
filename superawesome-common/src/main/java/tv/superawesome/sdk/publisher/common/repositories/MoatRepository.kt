package tv.superawesome.sdk.publisher.common.repositories

import android.webkit.WebView
import android.widget.VideoView

interface MoatRepositoryType {
    fun startMoatTrackingForDisplay(webView: WebView): String {
        return ""
    }

    fun stopMoatTrackingForDisplay(): Boolean {
        return false
    }

    fun startMoatTrackingForVideoPlayer(videoView: VideoView?, duration: Int): Boolean {
        return false
    }

    fun sendPlayingEvent(position: Int): Boolean {
        return false
    }

    fun sendStartEvent(position: Int): Boolean {
        return false
    }

    fun sendFirstQuartileEvent(position: Int): Boolean {
        return false
    }

    fun sendMidpointEvent(position: Int): Boolean {
        return false
    }

    fun sendThirdQuartileEvent(position: Int): Boolean {
        return false
    }

    fun sendCompleteEvent(position: Int): Boolean {
        return false
    }

    fun stopMoatTrackingForVideoPlayer(): Boolean {
        return false
    }
}