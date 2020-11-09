package tv.superawesome.sdk.publisher.moat.repositories

import android.webkit.WebView
import android.widget.VideoView
import com.moat.analytics.mobile.sup.*
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.components.NumberGeneratorType
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.repositories.MoatRepositoryType
import java.util.*

private const val MOAT_SERVER = "https://z.moatads.com"
private const val MOAT_URL = "moatad.js"
private const val MOAT_DISPLAY_PARTNER_CODE = "superawesomeinappdisplay731223424656"
private const val MOAT_VIDEO_PARTNER_CODE = "superawesomeinappvideo467548716573"

class MoatRepository(
        private val adResponse: AdResponse,
        private val moatLimiting: Boolean,
        private val logger: Logger,
        private val numberGenerator: NumberGeneratorType,
) : MoatRepositoryType, TrackerListener, VideoTrackerListener {
    private var factory: MoatFactory? = null
    private var webTracker: WebAdTracker? = null
    private var videoTracker: ReactiveVideoTracker? = null

    override fun startMoatTrackingForDisplay(webView: WebView): String {
        if (!isMoatAllowed()) {
            logger.error("Moat is not allowed")
            return ""
        }

        if (factory == null) {
            factory = MoatFactory.create()
        }

        webTracker = factory?.createWebAdTracker(webView)
        webTracker?.setListener(this)

        if (webTracker == null) {
            logger.error("Could not start tracking web view since webTracker is null")
            return ""
        }

        // form the proper moat data
        val ad = adResponse.ad
        var moatQuery = ""
        moatQuery += "moatClientLevel1=${ad.advertiserId}"
        moatQuery += "&moatClientLevel2=${ad.campaign_id}"
        moatQuery += "&moatClientLevel3=${ad.line_item_id}"
        moatQuery += "&moatClientLevel4=${ad.creative.id}"
        moatQuery += "&moatClientSlicer1=${ad.app}"
        moatQuery += "&moatClientSlicer2=${adResponse.placementId}"
        moatQuery += "&moatClientSlicer3=${ad.publisherId}"
        webTracker?.startTracking()

        logger.info("Trying to start tracking web with query $moatQuery")

        // and return the special moat javascript tag to be loaded in a web view
        return "<script src=\"$MOAT_SERVER/$MOAT_DISPLAY_PARTNER_CODE/$MOAT_URL?$moatQuery\" type=\"text/javascript\"></script>"
    }

    override fun stopMoatTrackingForDisplay(): Boolean {
        return if (webTracker != null) {
            webTracker?.stopTracking()
            webTracker = null
            true
        } else {
            logger.error("Could not stop tracking display since webTracker is null")
            false
        }
    }

    override fun startMoatTrackingForVideoPlayer(videoView: VideoView?, duration: Int): Boolean {
        if (!isMoatAllowed()) {
            logger.error("Moat is not allowed")
            return false
        }

        if (factory == null) {
            factory = MoatFactory.create()
        }

        videoTracker = factory?.createCustomTracker(ReactiveVideoTrackerPlugin(MOAT_VIDEO_PARTNER_CODE))
        videoTracker?.setListener(this)
        videoTracker?.setVideoListener(this)

        if (videoTracker == null) {
            logger.error("Could not start tracking video with duration $duration since videoTracker is null")
            return false
        }

        val ad = adResponse.ad
        val adIds = HashMap<String, String>()
        adIds["level1"] = "${ad.advertiserId}"
        adIds["level2"] = "${ad.campaign_id}"
        adIds["level3"] = "${ad.line_item_id}"
        adIds["level4"] = "${ad.creative.id}"
        adIds["slicer1"] = "${ad.app}"
        adIds["slicer2"] = "${adResponse.placementId}"
        adIds["slicer3"] = "${ad.publisherId}"
        logger.info("Trying to start tracking video for duration $duration and level/slicer info $adIds")

        return videoTracker?.trackVideoAd(adIds, duration, videoView) ?: false
    }

    override fun sendPlayingEvent(position: Int): Boolean {
        if (videoTracker == null) {
            logger.error("Could not send sendPlayingEvent to Moat since videoTracker is null")
            return false
        }
        videoTracker?.dispatchEvent(MoatAdEvent(MoatAdEventType.AD_EVT_PLAYING, position))
        return true
    }

    override fun sendStartEvent(position: Int): Boolean {
        if (videoTracker == null) {
            logger.error("Could not send sendStartEvent to Moat since videoTracker is null")
            return false
        }
        videoTracker?.dispatchEvent(MoatAdEvent(MoatAdEventType.AD_EVT_START, position))
        return true
    }

    override fun sendFirstQuartileEvent(position: Int): Boolean {
        if (videoTracker == null) {
            logger.error("Could not send sendFirstQuartileEvent to Moat since videoTracker is null")
            return false
        }
        videoTracker?.dispatchEvent(MoatAdEvent(MoatAdEventType.AD_EVT_FIRST_QUARTILE, position))
        return true
    }

    override fun sendMidpointEvent(position: Int): Boolean {
        if (videoTracker == null) {
            logger.error("Could not send sendMidpointEvent to Moat since videoTracker is null")
            return false
        }
        videoTracker?.dispatchEvent(MoatAdEvent(MoatAdEventType.AD_EVT_MID_POINT, position))
        return true
    }

    override fun sendThirdQuartileEvent(position: Int): Boolean {
        if (videoTracker == null) {
            logger.error("Could not send sendThirdQuartileEvent to Moat since videoTracker is null")
            return false
        }
        videoTracker?.dispatchEvent(MoatAdEvent(MoatAdEventType.AD_EVT_THIRD_QUARTILE, position))
        return true
    }

    override fun sendCompleteEvent(position: Int): Boolean {
        if (videoTracker == null) {
            logger.error("Could not send sendCompleteEvent to Moat since videoTracker is null")
            return false
        }
        videoTracker?.dispatchEvent(MoatAdEvent(MoatAdEventType.AD_EVT_COMPLETE, position))
        return true
    }

    override fun stopMoatTrackingForVideoPlayer(): Boolean {
        return if (videoTracker != null) {
            videoTracker?.stopTracking()
            true
        } else {
            logger.error("Could not stop tracking video since videoTracker is null")
            false
        }
    }

    override fun onTrackingStarted(s: String) {
        logger.info("Started tracking: $s")
    }

    override fun onTrackingFailedToStart(s: String) {
        logger.error("Failed to start tracking: $s")
    }

    override fun onTrackingStopped(s: String) {
        logger.info("Stopped tracking: $s")
    }

    override fun onVideoEventReported(moatAdEventType: MoatAdEventType) {
        logger.info("Video event $moatAdEventType")
    }

    private fun isMoatAllowed(): Boolean {
        val moatRand = numberGenerator.nextDoubleForMoat()
        val ad = adResponse.ad
        val response = moatRand < ad.moat && moatLimiting || !moatLimiting

        logger.info("Moat allowed? $response. moatRand: $moatRand ad.moat: ${ad.moat} moatLimiting: $moatLimiting")

        return response
    }
}