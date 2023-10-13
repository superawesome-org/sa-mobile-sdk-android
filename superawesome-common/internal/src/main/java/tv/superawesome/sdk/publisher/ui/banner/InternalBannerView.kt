@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.ui.banner

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import tv.superawesome.sdk.publisher.ad.AdController
import tv.superawesome.sdk.publisher.ad.AdManager
import tv.superawesome.sdk.publisher.common.R
import tv.superawesome.sdk.publisher.components.AdControllerStore
import tv.superawesome.sdk.publisher.components.ImageProviderType
import tv.superawesome.sdk.publisher.components.Logger
import tv.superawesome.sdk.publisher.components.TimeProviderType
import tv.superawesome.sdk.publisher.extensions.isRichMedia
import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.models.DefaultAdRequest
import tv.superawesome.sdk.publisher.models.DwellTimer
import tv.superawesome.sdk.publisher.models.SAEvent
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.models.VoidBlock
import tv.superawesome.sdk.publisher.ui.AdView
import tv.superawesome.sdk.publisher.ui.common.ClickThrottler
import tv.superawesome.sdk.publisher.ui.common.ContinuousViewableDetector
import tv.superawesome.sdk.publisher.ui.common.SingleShotViewableDetector
import tv.superawesome.sdk.publisher.ui.common.ViewableDetector
import tv.superawesome.sdk.publisher.ui.common.clickWithThrottling

/**
 * View that shows banner ads.
 */
@Suppress("TooManyFunctions")
public class InternalBannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), AdView, KoinComponent {

    internal val adManager: AdManager by inject()
    private val imageProvider: ImageProviderType by inject()
    private val logger: Logger by inject()
    private val timeProvider: TimeProviderType by inject()
    private val viewableDetector: ViewableDetector by inject(named<SingleShotViewableDetector>())
    private val dwellViewableDetector: ViewableDetector by inject(named<ContinuousViewableDetector>())
    private val adStore: AdControllerStore by inject()
    private val dwellTimer by lazy {
        DwellTimer(ticksNeeded = (DWELL_DELAY / ViewableDetector.DELAY_MILLIS).toInt())
    }

    private var placementId: Int = 0
    private var webView: CustomWebView? = null
    private var padlockButton: ImageButton? = null
    private var hasBeenVisible: VoidBlock? = null

    private var controller: AdController? = null
    private var loadJob: Job? = null

    private val scope = if (context is AppCompatActivity) {
        context.lifecycleScope
    } else {
        CoroutineScope(Dispatchers.Main)
    }

    private val coroutineContext = CoroutineExceptionHandler { _, throwable ->
        logger.error("Error occurred ${throwable.message}")
    }

    init {
        setColor(Constants.defaultBackgroundColorEnabled)
        isSaveEnabled = true
        contentDescription = "Ad content"
    }

    override fun onSaveInstanceState(): Parcelable = Bundle().apply {
        putParcelable("superState", super.onSaveInstanceState())
        putInt("placementId", placementId)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var restoreState = state
        if (restoreState is Bundle) {
            val bundle = restoreState
            this.placementId = bundle.getInt("placementId")
            restoreState = bundle.getParcelable("superState")
        }
        super.onRestoreInstanceState(restoreState)
    }

    public override fun load(
        placementId: Int,
        openRtbPartnerId: String?,
        options: Map<String, Any>?,
    ) {
        logger.info("load($placementId)")
        this.placementId = placementId
        if (isAdPlayedBefore()) {
            close()
        }

        loadJob = scope.launch(coroutineContext) {
            adManager.load(placementId, makeAdRequest(options, openRtbPartnerId))
        }
    }

    public fun load(placementId: Int) {
        load(placementId, options = null, openRtbPartnerId = null)
    }

    public override fun load(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        openRtbPartnerId: String?,
        options: Map<String, Any>?,
    ) {
        logger.info("load($placementId, $lineItemId, $creativeId)")
        this.placementId = placementId
        if (isAdPlayedBefore()) {
            close()
        }

        loadJob = scope.launch(coroutineContext) {
            adManager.load(
                placementId,
                lineItemId,
                creativeId,
                makeAdRequest(options, openRtbPartnerId),
            )
        }
    }

    public fun load(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
    ) {
        load(placementId, lineItemId, creativeId, options = null, openRtbPartnerId = null)
    }

    /**
     * Plays an already existing loaded ad, or fail.
     */
    public override fun play() {
        logger.info("play($placementId)")
        controller = try {
            getKoin().get { parametersOf(placementId) }
        } catch (e: IllegalStateException) {
            logger.error("Ad not loaded", e)
            null
        }
        val data = controller?.adResponse?.getDataPair()

        if (data == null) {
            val listener = controller?.listener ?: adManager.listener
            listener?.onEvent(placementId, SAEvent.adFailedToShow)
            return
        }

        addWebView()
        showPadlockIfNeeded()

        if (controller?.adResponse?.isRichMedia == true) {
            controller?.startTimerForRenderTime()
        }

        val bodyHtml = data.second
            .replace("_TIMESTAMP_", timeProvider.millis().toString())
        webView?.loadHTML(data.first, bodyHtml)

        adManager.removeController(placementId)
    }

    /**
     * Registers a callback to be called for certain events.
     *
     * @param delegate the callback delegate.
     */
    public override fun setListener(delegate: SAInterface) {
        adManager.listener = delegate
    }

    /**
     * Gets called in order to close the banner ad, remove any fragments, etc.
     */
    public override fun close() {
        adManager.removeController(placementId)
        loadJob?.cancel()
        loadJob = null
        hasBeenVisible = null
        viewableDetector.cancel()
        dwellViewableDetector.cancel()
        removeWebView()
        controller?.close()
    }

    /**
     * Returns whether an ad is available.
     */
    public override fun hasAdAvailable(): Boolean = adManager.hasAdAvailable(placementId)

    /**
     * Returns whether the ad is closed.
     */
    public override fun isClosed(): Boolean = controller?.isAdClosed ?: true

    /**
     * Sets parental gate enabled.
     */
    public override fun setParentalGate(value: Boolean) {
        adManager.adConfig.isParentalGateEnabled = value
    }

    /**
     * Sets bumper page enabled.
     */
    public override fun setBumperPage(value: Boolean) {
        adManager.adConfig.isBumperPageEnabled = value
    }

    /**
     * Sets the test mode.
     */
    public override fun setTestMode(value: Boolean) {
        adManager.adConfig.testEnabled = value
    }

    /**
     * Sets the transparency of the banner.
     *
     * @param value `true` makes the banner transparent, `false` makes it gray.
     */
    public override fun setColor(value: Boolean) {
        if (value) {
            setBackgroundColor(Color.TRANSPARENT)
        } else {
            setBackgroundColor(Constants.backgroundColorGray)
        }
    }

    private fun showPadlockIfNeeded() {
        if (controller?.shouldShowPadlock == false || webView == null) return

        val padlockButton = ImageButton(context)
        padlockButton.setImageBitmap(imageProvider.padlockImage())
        padlockButton.setBackgroundColor(Color.TRANSPARENT)
        padlockButton.scaleType = ImageView.ScaleType.FIT_XY
        val res = context.resources
        padlockButton.setPadding(
            res.getDimensionPixelOffset(R.dimen.safe_ad_logo_left_inset),
            res.getDimensionPixelOffset(R.dimen.safe_ad_logo_top_inset),
            res.getDimensionPixelOffset(R.dimen.safe_ad_logo_right_inset),
            res.getDimensionPixelOffset(R.dimen.safe_ad_logo_bottom_inset),
        )
        padlockButton.layoutParams = ViewGroup.LayoutParams(
            res.getDimensionPixelOffset(R.dimen.safe_ad_logo_width),
            res.getDimensionPixelOffset(R.dimen.safe_ad_logo_height) +
                    res.getDimensionPixelOffset(R.dimen.safe_ad_logo_top_inset),
        )
        padlockButton.contentDescription = "Safe Ad Logo"

        padlockButton.clickWithThrottling {
            controller?.handleSafeAdClick(context)
        }

        webView?.addView(padlockButton)

        this.padlockButton = padlockButton
    }

    private fun addWebView() {
        removeWebView()

        val webView = CustomWebView(context)
        webView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        webView.listener = object : CustomWebView.Listener {
            private val debouncer = ClickThrottler()
            override fun webViewOnStart() {
                controller?.listener?.onEvent(placementId, SAEvent.adShown)
                viewableDetector.cancel()
                scope.launch {
                    controller?.triggerImpressionEvent()
                    controller?.trackRenderTime()
                }
                viewableDetector.start(this@InternalBannerView, ViewableDetector.INTERSTITIAL_MAX_TICK_COUNT) {
                    scope.launch { controller?.triggerViewableImpression() }
                    hasBeenVisible?.let { it() }
                }
                startDwellTimer()
            }

            override fun webViewOnError() {
                controller?.listener?.onEvent(placementId, SAEvent.adFailedToShow)
            }

            override fun webViewOnClick(url: String) {
                debouncer.onClick {
                    controller?.handleAdClick(url, context)
                }
            }
        }

        addView(webView)

        this.webView = webView
    }

    private fun removeWebView() {
        if (webView != null) {
            webView?.destroy()
            removeView(webView)
            webView = null
        }
    }

    private fun isAdPlayedBefore(): Boolean = webView != null

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        logger.info("BannerView.onDetachedFromWindow")
    }

    private fun makeAdRequest(
        options: Map<String, Any>?,
        openRtbPartnerId: String?
    ): AdRequest =
        DefaultAdRequest(
            test = adManager.adConfig.testEnabled,
            pos = AdRequest.Position.AboveTheFold.value,
            skip = AdRequest.Skip.No.value,
            playbackMethod = DefaultAdRequest.PlaybackSoundOnScreen,
            startDelay = AdRequest.StartDelay.PreRoll.value,
            install = AdRequest.FullScreen.Off.value,
            w = width,
            h = height,
            openRtbPartnerId = openRtbPartnerId,
            options = options,
        )

    private fun startDwellTimer() {
        dwellViewableDetector.start(this@InternalBannerView, ViewableDetector.INTERSTITIAL_MAX_TICK_COUNT) {
            dwellTimer.tick {
                scope.launch { controller?.trackDwellTime() }
            }
        }
    }

    internal fun configure(placementId: Int, delegate: SAInterface?, hasBeenVisible: VoidBlock) {
        this.placementId = placementId
        delegate?.let { setListener(it) }
        this.hasBeenVisible = hasBeenVisible
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun getManager() = adManager

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun getController() = controller

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun getJob() = loadJob

    companion object {
        private const val DWELL_DELAY = 5000L
    }
}
