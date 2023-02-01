package tv.superawesome.sdk.publisher.common.ui.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.get
import tv.superawesome.sdk.publisher.common.components.AdStoreType
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.models.*
import tv.superawesome.sdk.publisher.common.network.DataResult
import tv.superawesome.sdk.publisher.common.repositories.AdRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.EventRepositoryType
import kotlin.math.abs

interface AdControllerType {
    var config: Config
    var closed: Boolean
    var currentAdResponse: AdResponse?
    var delegate: SAInterface?
    val shouldShowPadlock: Boolean

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
    fun adClicked()
    fun adEnded()
    fun adClosed()

    fun peekAdResponse(placementId: Int): AdResponse?
}

class AdController(
    private val adRepository: AdRepositoryType,
    private val eventRepository: EventRepositoryType,
    private val logger: Logger,
    private val adStore: AdStoreType
) : AdControllerType {
    override var config: Config = Config()
    override var closed: Boolean = false
    override var currentAdResponse: AdResponse? = null
    override var delegate: SAInterface? = null
    override val shouldShowPadlock: Boolean
        get() = currentAdResponse?.shouldShowPadlock() ?: false

    private val scope = CoroutineScope(Dispatchers.Main)
    private var parentalGate: ParentalGate? = null
    private var lastClickTime = 0L

    private val placementId: Int
        get() = currentAdResponse?.placementId ?: 0

    override fun handleAdTap(url: String, context: Context) {
        onAdClicked(url, context)
    }

    override fun handleSafeAdTap(context: Context) {
        showParentalGateIfNeeded(context) {
            onAdClicked(Constants.defaultSafeAdUrl, context)
        }
    }

    override fun handleAdTapForVast(context: Context) {
        val clickThroughUrl = currentAdResponse?.vast?.clickThroughUrl

        if (clickThroughUrl == null) {
            logger.info("Click through URL is not found")
            return
        }

        handleAdTap(clickThroughUrl, context)
    }

    private fun showParentalGateIfNeeded(context: Context, completion: () -> Unit) =
        if (config.isParentalGateEnabled) {
            parentalGate?.stop()
            parentalGate = get(ParentalGate::class.java)
            parentalGate?.apply {
                newQuestion()
                listener = object : ParentalGate.Listener {
                    override fun parentalGateOpen() {
                        scope.launch { currentAdResponse?.let { eventRepository.parentalGateOpen(it) } }
                    }

                    override fun parentalGateCancel() {
                        scope.launch { currentAdResponse?.let { eventRepository.parentalGateClose(it) } }
                    }

                    override fun parentalGateSuccess() {
                        scope.launch {
                            currentAdResponse?.let {
                                eventRepository.parentalGateSuccess(
                                    it
                                )
                            }
                        }
                        completion()
                    }

                    override fun parentalGateFail() {
                        scope.launch { currentAdResponse?.let { eventRepository.parentalGateFail(it) } }
                    }
                }
                parentalGate?.show(context)
            }
            Unit
        } else {
            completion()
        }

    private fun isClickTooFast(): Boolean {
        val currentTime = System.currentTimeMillis()
        val diff = abs(currentTime - lastClickTime)

        if (diff < Constants.defaultClickThresholdInMs) {
            logger.info("Click is too fast/Ignore")
            return true
        }

        lastClickTime = currentTime
        return false
    }

    private fun onAdClicked(url: String, context: Context) {
        logger.info("onAdClicked $url")

        if (isClickTooFast()) return

        if (config.isParentalGateEnabled) {
            showParentalGateIfNeeded(context, completion = {
                showBannerIfNeeded(url, context)
            })
        } else {
            showBannerIfNeeded(url, context)
        }
    }

    private fun showBannerIfNeeded(url: String, context: Context) {
        if (config.isBumperPageEnabled || currentAdResponse?.ad?.creative?.bumper == true) {
            playBumperPage(url, context)
        } else {
            navigateToUrl(url, context)
        }
    }

    private fun playBumperPage(url: String, context: Context) {
        if (context is Activity) {
            BumperPageActivity.setListener(object : BumperPageActivity.Interface {
                override fun didEndBumper() {
                    navigateToUrl(url, context)
                }
            })
            BumperPageActivity.play(context)
        }
    }

    private fun navigateToUrl(url: String, context: Context) {
        logger.info("navigateToUrl $url")

        if (currentAdResponse == null) {
            logger.info("adResponse is null")
            return
        }

        delegate?.onEvent(placementId, SAEvent.AdClicked)

        if (currentAdResponse?.isVideo() == true) {
            scope.launch { currentAdResponse?.let { eventRepository.videoClick(it) } }
        } else {
            scope.launch { currentAdResponse?.let { eventRepository.click(it) } }
        }

        // append CPI data to it
        val referrer =
            if (currentAdResponse?.ad?.isCPICampaign() == true) "&referrer=" + currentAdResponse?.referral else ""
        val destination = "$url$referrer"

        // start browser
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(destination)))
        } catch (exception: Exception) {
            logger.error("Exception while navigating to url", exception)
        }
    }

    override fun close() = try {
        closed = true
        parentalGate?.stop()
        scope.cancel()
    } catch (exception: IllegalStateException) {
        logger.error("Exception while closing the ad", exception)
    }

    override fun hasAdAvailable(placementId: Int): Boolean {
        return false
    }

    override fun adFailedToShow() {
        delegate?.onEvent(placementId, SAEvent.AdFailedToShow)
    }

    override fun adShown() {
        delegate?.onEvent(placementId, SAEvent.AdShown)
    }

    override fun adClicked() {
        delegate?.onEvent(placementId, SAEvent.AdClicked)
    }

    override fun adEnded() {
        delegate?.onEvent(placementId, SAEvent.AdEnded)
    }

    override fun adClosed() {
        delegate?.onEvent(placementId, SAEvent.AdClosed)
    }

    override fun peekAdResponse(placementId: Int): AdResponse? = adStore.peek(placementId)

    override fun load(placementId: Int, request: AdRequest) {
        logger.info("load($placementId) thread:${Thread.currentThread()}")
        scope.launch {
            when (val result = adRepository.getAd(placementId, request)) {
                is DataResult.Success -> onSuccess(result.value)
                is DataResult.Failure -> onFailure(placementId, result.error)
            }
        }
    }

    override fun load(placementId: Int, lineItemId: Int, creativeId: Int, request: AdRequest) {
        scope.launch {
            when (val result = adRepository.getAd(placementId, lineItemId, creativeId, request)) {
                is DataResult.Success -> onSuccess(result.value)
                is DataResult.Failure -> onFailure(placementId, result.error)
            }
        }
    }

    override fun play(placementId: Int): AdResponse? {
        currentAdResponse = adStore.consume(placementId)

        if (currentAdResponse == null) {
            adFailedToShow()
            return null
        }

        return currentAdResponse
    }

    override fun triggerImpressionEvent(placementId: Int) {
        scope.launch {
            currentAdResponse?.let {
                when (val result = eventRepository.impression(it)) {
                    is DataResult.Success -> logger.success("triggerImpressionEvent.Success")
                    is DataResult.Failure -> logger.error(
                        "triggerImpressionEvent.Success",
                        result.error
                    )
                }
            }
        }
    }

    override fun triggerViewableImpression(placementId: Int) {
        scope.launch { currentAdResponse?.let { eventRepository.viewableImpression(it) } }
    }

    private fun onSuccess(response: AdResponse) {
        logger.success("onSuccess thread:${Thread.currentThread()} adResponse:$response")
        adStore.put(response)
        delegate?.onEvent(response.placementId, SAEvent.AdLoaded)
    }

    private fun onFailure(placementId: Int, error: Throwable) {
        logger.error("onFailure for $placementId", error)
        delegate?.onEvent(placementId, SAEvent.AdFailedToLoad)
    }
}
