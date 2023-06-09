package tv.superawesome.sdk.publisher.common.ui.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.get
import tv.superawesome.sdk.publisher.common.components.AdStoreType
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.CreativeFormatType
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.network.DataResult
import tv.superawesome.sdk.publisher.common.repositories.AdRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.EventRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.PerformanceRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.VastEventRepositoryType
import kotlin.math.abs

internal interface AdControllerType {
    var config: Config
    var closed: Boolean
    var currentAdResponse: AdResponse?
    var delegate: SAInterface?
    var videoListener: VideoPlayerListener?
    val shouldShowPadlock: Boolean

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

internal class AdController(
    private val adRepository: AdRepositoryType,
    private val eventRepository: EventRepositoryType,
    private val performanceRepository: PerformanceRepositoryType,
    private val logger: Logger,
    private val adStore: AdStoreType
) : AdControllerType {
    override var config: Config = Config()
    override var closed: Boolean = false
    override var currentAdResponse: AdResponse? = null
    override var delegate: SAInterface? = null
    override var videoListener: AdControllerType.VideoPlayerListener? = null
    override val shouldShowPadlock: Boolean
        get() = currentAdResponse?.shouldShowPadlock() ?: false

    override fun startTimingForDwellTime() {
        performanceRepository.startTimingForDwellTime()
    }

    override fun trackDwellTime() {
        scope.launch {
            performanceRepository.trackDwellTime()
        }
    }

    override fun startTimingForCloseButtonPressed() {
        performanceRepository.startTimingForCloseButtonPressed()
    }

    override fun trackCloseButtonPressed() {
        scope.launch {
            performanceRepository.trackCloseButtonPressed()
        }
    }

    private val scope = CoroutineScope(Dispatchers.Main)
    private var parentalGate: ParentalGate? = null
    private var lastClickTime = 0L

    private val placementId: Int
        get() = currentAdResponse?.placementId ?: 0

    private var bumperPage: BumperPage? = null

    override fun handleAdTap(url: String, context: Context) {
        onAdClicked(url, context)
    }

    override fun handleSafeAdTap(context: Context) {
        showParentalGateIfNeeded(context) {
            onAdClicked(Constants.defaultSafeAdUrl, context)
        }
    }

    override fun handleAdTapForVast(context: Context) {
        // if the campaign is a CPI one, get the normal CPI url so that
        // we can append the "referrer data" to it (since most likely
        // "click_through" will have a redirect)
        val destinationUrl = if (currentAdResponse?.ad?.isCPICampaign() == true) {
            currentAdResponse?.ad?.creative?.clickUrl
        } else {
            currentAdResponse?.vast?.clickThroughUrl
        }

        if (destinationUrl == null) {
            logger.info("Destination URL is not found")
            return
        }

        handleAdTap(destinationUrl, context)
    }

    private fun showParentalGateIfNeeded(context: Context, completion: () -> Unit) =
        if (config.isParentalGateEnabled) {
            parentalGate?.stop()
            parentalGate = get(ParentalGate::class.java)
            parentalGate?.apply {
                newQuestion()
                listener = object : ParentalGate.Listener {
                    override fun parentalGateOpen() {
                        scope.launch {
                            videoListener?.didRequestVideoPause()
                            adPaused()
                            currentAdResponse?.let { eventRepository.parentalGateOpen(it) }
                        }
                    }

                    override fun parentalGateCancel() {
                        scope.launch {
                            videoListener?.didRequestVideoPlay()
                            adPlaying()
                            currentAdResponse?.let { eventRepository.parentalGateClose(it) }
                        }
                    }

                    override fun parentalGateSuccess() {
                        scope.launch {
                            currentAdResponse?.let {
                                eventRepository.parentalGateSuccess(it)
                            }
                        }
                        completion()
                    }

                    override fun parentalGateFail() {
                        scope.launch {
                            videoListener?.didRequestVideoPlay()
                            adPlaying()
                            currentAdResponse?.let { eventRepository.parentalGateFail(it) }
                        }
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

        showParentalGateIfNeeded(context, completion = {
            showBumperIfNeeded(url, context)
        })
    }

    private fun showBumperIfNeeded(url: String, context: Context) {
        if (config.isBumperPageEnabled || currentAdResponse?.ad?.creative?.bumper == true) {
            playBumperPage(url, context)
        } else {
            navigateToUrl(url, context)
        }
    }

    private fun playBumperPage(url: String, context: Context) {
        if (context is Activity) {
            videoListener?.didRequestVideoPause()
            adPaused()
            bumperPage?.stop()
            bumperPage = get(BumperPage::class.java)
            bumperPage?.onFinish = {
                navigateToUrl(url, context)
            }
            bumperPage?.show(context)
        }
    }

    private fun navigateToUrl(url: String, context: Context) {
        logger.info("navigateToUrl $url")

        if (currentAdResponse == null) {
            logger.info("adResponse is null")
            return
        }

        adClicked()

        if (currentAdResponse?.isVideo() == true) {
            scope.launch { currentAdResponse?.let { eventRepository.videoClick(it) } }
            triggerVastClickEvents()
        } else {
            scope.launch { currentAdResponse?.let { eventRepository.click(it) } }
        }

        // append CPI data to it
        val referrer = if (currentAdResponse?.ad?.isCPICampaign() == true) {
            "&referrer=" + currentAdResponse?.referral
        } else {
            ""
        }

        val destination = "$url$referrer"

        try {
            // start browser
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(destination)))
        } catch (exception: Exception) {
            logger.error("Exception while navigating to url", exception)
        }
    }

    private fun triggerVastClickEvents() {
        val vast = currentAdResponse?.vast ?: return
        val vastEventRepository: VastEventRepositoryType = get(
            clazz = VastEventRepositoryType::class.java,
            parameters = { parametersOf(vast) }
        )

        scope.launch {
            vastEventRepository.clickTracking()
        }
    }

    override fun close() = try {
        adClosed()
        closed = true
        currentAdResponse = null
        parentalGate?.stop()
        bumperPage?.stop()
        scope.coroutineContext.cancelChildren()
    } catch (exception: IllegalStateException) {
        logger.error("Exception while closing the ad", exception)
    }

    override fun hasAdAvailable(placementId: Int): Boolean = adStore.peek(placementId) != null

    private fun adAlreadyLoaded(placementId: Int) {
        delegate?.onEvent(placementId, SAEvent.adAlreadyLoaded)
    }

    override fun adFailedToShow() {
        delegate?.onEvent(placementId, SAEvent.adFailedToShow)
    }

    override fun adShown() {
        delegate?.onEvent(placementId, SAEvent.adShown)
    }

    fun adClicked() {
        delegate?.onEvent(placementId, SAEvent.adClicked)
    }

    override fun adEnded() {
        delegate?.onEvent(placementId, SAEvent.adEnded)
    }

    fun adClosed() {
        delegate?.onEvent(placementId, SAEvent.adClosed)
    }

    override fun adPlaying() {
        delegate?.onEvent(placementId, SAEvent.adPlaying)
    }

    override fun adPaused() {
        delegate?.onEvent(placementId, SAEvent.adPaused)
    }

    override fun peekAdResponse(placementId: Int): AdResponse? = adStore.peek(placementId)

    override fun load(placementId: Int, request: AdRequest) {
        logger.info("load($placementId) thread:${Thread.currentThread()}")

        if (hasAdAvailable(placementId)) {
            adAlreadyLoaded(placementId)
            return
        }

        scope.launch {
            when (val result = adRepository.getAd(placementId, request)) {
                is DataResult.Success -> onSuccess(result.value)
                is DataResult.Failure -> onFailure(placementId, result.error)
            }
        }
    }

    override fun load(placementId: Int, lineItemId: Int, creativeId: Int, request: AdRequest) {
        logger.info("load($placementId, $lineItemId, $creativeId) thread:${Thread.currentThread()}")

        if (hasAdAvailable(placementId)) {
            adAlreadyLoaded(placementId)
            return
        }

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

    override fun clearCache() {
        adStore.clear()
    }

    private fun onSuccess(response: AdResponse) {
        if (response.ad.creative.format == CreativeFormatType.Video &&
            response.ad.creative.details.tag == null &&
            response.ad.creative.details.vast == null)  {
            onFailure(placementId, MissingVastTagError())
            return
        }

        logger.success("onSuccess thread:${Thread.currentThread()} adResponse:$response")
        adStore.put(response)
        delegate?.onEvent(response.placementId, SAEvent.adLoaded)
    }

    private fun onFailure(placementId: Int, error: Throwable) {
        logger.error("onFailure for $placementId", error)
        delegate?.onEvent(placementId, SAEvent.adFailedToLoad)
    }
}
