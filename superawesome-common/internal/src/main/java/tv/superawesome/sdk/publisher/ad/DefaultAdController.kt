package tv.superawesome.sdk.publisher.ad

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import tv.superawesome.sdk.publisher.components.Logger
import tv.superawesome.sdk.publisher.components.NumberGeneratorType
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.models.SAEvent
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.ui.common.DefaultBumperPage
import tv.superawesome.sdk.publisher.ui.common.ParentalGate
import tv.superawesome.sdk.publisher.ui.video.player.VideoPlayerListener

class DefaultAdController(
    override val adResponse: AdResponse,
    override val adConfig: AdConfig,
    override val listener: SAInterface?,
    private val numberGenerator: NumberGeneratorType,
    private val logger: Logger,
    private val adEventHandler: AdEventHandler,
    private val adPerformanceTrackerHandler: AdPerformanceTrackerHandler,
) : AdController,
    AdEventHandler by adEventHandler,
    AdPerformanceTrackerHandler by adPerformanceTrackerHandler {

    override var videoPlayerListener: VideoPlayerListener? = null
    override var isAdClosed = false

    // TODO Inject scope
    private val scope = MainScope()

    private val bumperPage by lazy { DefaultBumperPage() }
    private val parentalGate by lazy { ParentalGate(numberGenerator) }

    private val placementId = adResponse.placementId

    override fun close() {
        try {
            listener?.onEvent(placementId, SAEvent.adClosed)
            parentalGate.stop()
            bumperPage.stop()
            scope.cancel()
            isAdClosed = true
        } catch (e: IllegalStateException) {
            logger.error("Exception while closing the ad", e)
        }
    }

    override fun handleAdClick(url: String, context: Context) = onAdClicked(url, context)

    override fun handleSafeAdClick(context: Context) =
        onAdClicked(Constants.defaultSafeAdUrl, context)

    override fun handleVastAdClick(context: Context) {
        // if the campaign is a CPI one, get the normal CPI url so that
        // we can append the "referrer data" to it (since most likely
        // "click_through" will have a redirect)
        val destinationUrl = if (adResponse.ad.isCPICampaign()) {
            adResponse.ad.creative.clickUrl
        } else {
            adResponse.vast?.clickThroughUrl
        }

        if (destinationUrl == null) {
            logger.info("Destination URL is not found")
            return
        }

        handleAdClick(destinationUrl, context)
    }


    private fun onAdClicked(url: String, context: Context) {
        logger.info("onAdClicked $url")

        showParentalGateIfNeeded(context) {
            if (adConfig.isBumperPageEnabled || adResponse.ad.creative.bumper == true) {
                showBumperPage(url, context)
            } else {
                navigateToUrl(url, context)
            }
        }
    }

    private fun showParentalGateIfNeeded(context: Context, completion: () -> Unit) {
        if (adConfig.isParentalGateEnabled) {
            parentalGate.newQuestion()
            parentalGate.listener = object : ParentalGate.Listener {
                override fun parentalGateOpen() {
                    listener?.onEvent(placementId, SAEvent.adPaused)
                    videoPlayerListener?.didRequestVideoPause()
                    scope.launch { adEventHandler.parentalGateOpen() }
                }

                override fun parentalGateCancel() {
                    listener?.onEvent(placementId, SAEvent.adPlaying)
                    videoPlayerListener?.didRequestVideoPlay()
                    scope.launch { adEventHandler.parentalGateClose() }
                }

                override fun parentalGateSuccess() {
                    scope.launch { adEventHandler.parentalGateSuccess() }
                    completion()
                }

                override fun parentalGateFail() {
                    listener?.onEvent(placementId, SAEvent.adPlaying)
                    videoPlayerListener?.didRequestVideoPlay()
                    scope.launch { adEventHandler.parentalGateFail() }
                }
            }
            parentalGate.show(context)
        } else {
            completion()
        }
    }

    private fun showBumperPage(url: String, context: Context) {
        require(context is Activity) { "Context must be from an Activity" }

        listener?.onEvent(placementId, SAEvent.adPaused)
        videoPlayerListener?.didRequestVideoPause()
        bumperPage.onFinish = {
            navigateToUrl(url, context)
        }
        bumperPage.show(context)
    }

    private fun navigateToUrl(url: String, context: Context) {
        logger.info("navigate to url: $url")

        listener?.onEvent(placementId, SAEvent.adClicked)
        scope.launch { adEventHandler.click() }

        // Append CPI data
        val referrer = adResponse.referral
            ?.takeIf { adResponse.ad.isCPICampaign() }
            ?.let { referral -> "&referrer=$referral" }
            ?: ""

        val destination = "$url$referrer"

        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(destination)))
        } catch (e: ActivityNotFoundException) {
            logger.error("Activity to open URL not found", e)
        }
    }
}