package tv.superawesome.sdk.publisher.ui.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.get
import tv.superawesome.sdk.publisher.common.components.DispatcherProviderType
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.di.Injectable
import tv.superawesome.sdk.publisher.common.models.*
import tv.superawesome.sdk.publisher.common.network.DataResult
import tv.superawesome.sdk.publisher.common.repositories.AdRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.EventRepositoryType
import kotlin.math.abs

interface AdControllerType {
    var testEnabled: Boolean
    var showPadlock: Boolean
    var parentalGateEnabled: Boolean
    var bumperPageEnabled: Boolean
    var closed: Boolean
    var moatLimiting: Boolean
    val adAvailable: Boolean
    var adResponse: AdResponse?
    var delegate: SAInterface?

    fun triggerImpressionEvent()
    fun triggerViewableImpression()

    /**
     * Returns `baseUrl` and `html` data to show in the `WebView`
     */
    fun getDataPair(): Pair<String, String>?
    fun load(placementId: Int, request: AdRequest)
    fun handleSafeAdTap()
    fun handleAdTap(url: String, context: Context)
    fun close()

    fun adFailedToShow()
    fun adShown()
}

class AdController(
        private val adRepository: AdRepositoryType,
        private val eventRepository: EventRepositoryType,
        private val logger: Logger,
        dispatcherProvider: DispatcherProviderType,
) : AdControllerType, Injectable {
    override var testEnabled: Boolean = Constants.defaultTestMode
    override var showPadlock: Boolean = false
    override var bumperPageEnabled: Boolean = Constants.defaultBumperPage
    override var parentalGateEnabled: Boolean = Constants.defaultParentalGate
    override var closed: Boolean = false
    override var moatLimiting: Boolean = Constants.defaultMoatLimitingState

    override val adAvailable: Boolean
        get() = adResponse != null

    override var adResponse: AdResponse? = null
    override var delegate: SAInterface? = null

    private val scope = CoroutineScope(dispatcherProvider.main)
    private var parentalGate: ParentalGate? = null
    private var lastClickTime = 0L

    private val placementId: Int
        get() = adResponse?.placementId ?: 0

    override fun getDataPair(): Pair<String, String>? {
        val base = adResponse?.baseUrl ?: return null
        val html = adResponse?.html ?: return null
        return Pair(base, html)
    }

    override fun handleSafeAdTap() {

    }

    override fun handleAdTap(url: String, context: Context) {
        showParentalGateIfNeeded(context) {
            onAdClicked(url, context)
        }
    }

    private fun showParentalGateIfNeeded(context: Context, completion: () -> Unit) = if (parentalGateEnabled) {
        parentalGate?.stop()
        parentalGate = get()
        parentalGate?.listener = object : ParentalGate.Listener {
            override fun parentalGateOpen() {
                scope.launch { adResponse?.let { eventRepository.parentalGateOpen(it) } }
            }

            override fun parentalGateCancel() {
                scope.launch { adResponse?.let { eventRepository.parentalGateClose(it) } }
            }

            override fun parentalGateSuccess() {
                scope.launch { adResponse?.let { eventRepository.parentalGateSuccess(it) } }
                completion()
            }

            override fun parentalGateFail() {
                scope.launch { adResponse?.let { eventRepository.parentalGateFail(it) } }
            }
        }
        parentalGate?.show(context)
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

        if (bumperPageEnabled || adResponse?.ad?.creative?.bumper == true) {
            if (context is Activity) {
                BumperPageActivity.setListener(object : BumperPageActivity.Interface {
                    override fun didEndBumper() {
                        navigateToUrl(url, context)
                    }
                })
                BumperPageActivity.play(context)
            }
        } else {
            navigateToUrl(url, context)
        }
    }

    private fun navigateToUrl(url: String, context: Context) {
        logger.info("navigateToUrl $url")

        if (adResponse == null) {
            logger.info("adResponse is null")
            return
        }

        delegate?.onEvent(placementId, SAEvent.adClicked)

        if (adResponse?.isVideo() == true) {
            scope.launch { adResponse?.let { eventRepository.videoClick(it) } }
        } else {
            scope.launch { adResponse?.let { eventRepository.click(it) } }
        }

        // append CPI data to it
        val referrer = if (adResponse?.ad?.isCPICampaign() == true) "&referrer=" + adResponse?.referral else ""
        val destination = "$url${referrer}"

        // start browser
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(destination)))
        } catch (exception: Exception) {
            logger.error("Exception while navigating to url", exception)
        }
    }

    override fun close() = try {
        closed = true
        scope.cancel()
    } catch (exception: IllegalStateException) {
        logger.error("Exception while closing the ad", exception)
    }

    override fun adFailedToShow() {
        delegate?.onEvent(placementId, SAEvent.adFailedToShow)
    }

    override fun adShown() {
        delegate?.onEvent(placementId, SAEvent.adShown)
    }

    override fun load(placementId: Int, request: AdRequest) {
        logger.info("load(${placementId}) thread:${Thread.currentThread()}")
        scope.launch {
            when (val result = adRepository.getAd(placementId, request)) {
                is DataResult.Success -> onSuccess(result.value)
                is DataResult.Failure -> onFailure(result.error)
            }
        }
    }

    override fun triggerImpressionEvent() {
        scope.launch { adResponse?.let { eventRepository.impression(it) } }
    }

    override fun triggerViewableImpression() {
        scope.launch { adResponse?.let { eventRepository.viewableImpression(it) } }
    }

    private fun onSuccess(response: AdResponse) {
        logger.success("onSuccess thread:${Thread.currentThread()}")
        this.adResponse = response
        delegate?.onEvent(placementId, SAEvent.adLoaded)
    }

    private fun onFailure(error: Throwable) {
        logger.error("onFailure", error)
        delegate?.onEvent(placementId, SAEvent.adFailedToLoad)
    }
}