@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.ui.banner

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import org.koin.java.KoinJavaComponent.inject
import tv.superawesome.sdk.publisher.common.components.ImageProviderType
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.components.TimeProviderType
import tv.superawesome.sdk.publisher.common.extensions.toPx
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.models.VoidBlock
import tv.superawesome.sdk.publisher.common.openmeasurement.FriendlyObstructionType
import tv.superawesome.sdk.publisher.common.openmeasurement.OpenMeasurementSessionManagerType
import tv.superawesome.sdk.publisher.common.ui.common.AdControllerType
import tv.superawesome.sdk.publisher.common.ui.common.ViewableDetectorType
import tv.superawesome.sdk.publisher.common.ui.common.INTERSTITIAL_MAX_TICK_COUNT

/**
 * View that shows banner ads.
 */
@Suppress("TooManyFunctions")
public class BannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    internal val controller: AdControllerType by inject(AdControllerType::class.java)
    private val imageProvider: ImageProviderType by inject(ImageProviderType::class.java)
    private val logger: Logger by inject(Logger::class.java)
    private val timeProvider: TimeProviderType by inject(TimeProviderType::class.java)
    internal val omSessionManager: OpenMeasurementSessionManagerType
        by inject(OpenMeasurementSessionManagerType::class.java)

    private var placementId: Int = 0
    private var webViewWrapperStore: MutableMap<String, WebViewWrapper> = mutableMapOf()
    private var currentWebViewWrapper: WebViewWrapper? = null
    private var padlockButton: ImageButton? = null
    private val viewableDetector: ViewableDetectorType by inject(ViewableDetectorType::class.java)
    private var hasBeenVisible: VoidBlock? = null

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

    /**
     * Loads a new SAAd object corresponding to a given placement Id.
     *
     * @param placementId Awesome Ads ID for ad data to be loaded
     * @param options: an optional dictionary of data to send with an ad's requests and events.
     * Supports String or Int values.
     */
    @JvmOverloads
    public fun load(placementId: Int, options: Map<String, Any>? = null) {
        logger.info("load($placementId)")
        this.placementId = placementId
        if (isAdPlayedBefore()) {
            close()
        }
        controller.load(placementId, makeAdRequest(options))
    }

    /**
     * Loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId the Ad placement id to load data for
     * @param lineItemId
     * @param creativeId id of the Creative
     * @param options: an optional dictionary of data to send with an ad's requests and events.
     * Supports String or Int values.
     */
    @JvmOverloads
    public fun load(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        options: Map<String, Any>? = null,
    ) {
        logger.info("load($placementId, $lineItemId, $creativeId)")
        this.placementId = placementId
        if (isAdPlayedBefore()) {
            close()
        }
        controller.load(placementId, lineItemId, creativeId, makeAdRequest(options))
    }

    /**
     * Plays an already existing loaded ad, or fail.
     */
    public fun play() {
        logger.info("play($placementId)")
        val adResponse = controller.play(placementId)
        val data = adResponse?.getDataPair()

        if (data == null) {
            controller.adFailedToShow()
            return
        }

        addWebView()
        showPadlockIfNeeded()
        setupWebViewListener()
        val bodyHtml = data.second
            .replace("_TIMESTAMP_", timeProvider.millis().toString())

        // load the HTML
        currentWebViewWrapper?.webView?.loadHTML(data.first, bodyHtml)
    }

    /**
     * Registers a callback to be called for certain events.
     *
     * @param delegate the callback delegate.
     */
    public fun setListener(delegate: SAInterface) {
        controller.delegate = delegate
    }

    /**
     * Gets called in order to close the banner ad, remove any fragments, etc.
     */
    public fun close() {
        hasBeenVisible = null
        viewableDetector.cancel()
        omSessionManager.finish()
        removeWebViewsWithDelay()
        controller.close()
    }

    /**
     * Returns whether an ad is available.
     */
    public fun hasAdAvailable(): Boolean = controller.hasAdAvailable(placementId)

    /**
     * Returns whether the ad is closed.
     */
    public fun isClosed(): Boolean = controller.closed

    /**
     * Enables parental gate.
     */
    public fun enableParentalGate() {
        setParentalGate(true)
    }

    /**
     * Disables parental gate.
     */
    public fun disableParentalGate() {
        setParentalGate(false)
    }

    /**
     * Enables bumper page.
     */
    public fun enableBumperPage() {
        setBumperPage(true)
    }

    /**
     * Disables bumper page.
     */
    public fun disableBumperPage() {
        setBumperPage(false)
    }

    /**
     * Enables test mode.
     */
    public fun enableTestMode() {
        setTestMode(true)
    }

    /**
     * Disables test mode.
     */
    public fun disableTestMode() {
        setTestMode(false)
    }

    /**
     * Sets transparent color background.
     */
    public fun setColorTransparent() {
        setColor(true)
    }

    /**
     * Sets gray color background.
     */
    public fun setColorGray() {
        setColor(false)
    }

    /**
     * Sets parental gate enabled.
     */
    public fun setParentalGate(value: Boolean) {
        controller.config.isParentalGateEnabled = value
    }

    /**
     * Sets bumper page enabled.
     */
    public fun setBumperPage(value: Boolean) {
        controller.config.isBumperPageEnabled = value
    }

    /**
     * Sets the test mode.
     */
    public fun setTestMode(value: Boolean) {
        controller.config.testEnabled = value
    }

    /**
     * Sets the transparency of the banner.
     *
     * @param value `true` makes the banner transparent, `false` makes it gray.
     */
    public fun setColor(value: Boolean) {
        if (value) {
            setBackgroundColor(Color.TRANSPARENT)
        } else {
            setBackgroundColor(Constants.backgroundColorGray)
        }
    }

    private fun showPadlockIfNeeded() {
        if (!controller.shouldShowPadlock) return

        currentWebViewWrapper?.let { webView ->
            padlockButton?.let {
                currentWebViewWrapper?.removeView(it)
                padlockButton = null
            }

            val cPadlockButton = ImageButton(context)
            cPadlockButton.setImageBitmap(imageProvider.padlockImage())
            cPadlockButton.setBackgroundColor(Color.TRANSPARENT)
            cPadlockButton.scaleType = ImageView.ScaleType.FIT_XY
            cPadlockButton.setPadding(0, 2.toPx, 0, 0)
            cPadlockButton.layoutParams = ViewGroup.LayoutParams(77.toPx, 31.toPx)
            cPadlockButton.contentDescription = "Safe Ad Logo"

            cPadlockButton.setOnClickListener { controller.handleSafeAdTap(context) }

            webView.addView(cPadlockButton)

            cPadlockButton.translationX = webView.translationX
            cPadlockButton.translationY = webView.translationY

            padlockButton = cPadlockButton
        }
    }

    private fun addWebView() {
        removeWebViewsWithDelay()

        val webViewWrapper = WebViewWrapper(context)
        webViewWrapperStore[controller.currentAdResponse?.ad?.random ?: placementId.toString()] =
            webViewWrapper
        webViewWrapper.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )

        addView(webViewWrapper)

        this.currentWebViewWrapper = webViewWrapper
    }

    private fun addSafeAdToOM() {
        padlockButton?.let {
            if (it.visibility == View.VISIBLE) {
                omSessionManager.addFriendlyObstruction(
                    view = it,
                    purpose = FriendlyObstructionType.Other,
                    reason = "SafeAdPadlock",
                )
            }
        }
    }

    private fun setupWebViewListener() {
        currentWebViewWrapper?.let { webViewWrapper ->
            webViewWrapper.setListener(object : CustomWebView.Listener {
                override fun webViewOnStart() {
                    omSessionManager.setup(context, webViewWrapper.webView)
                    addSafeAdToOM()
                    controller.adShown()
                    omSessionManager.start()
                    viewableDetector.cancel()
                    controller.triggerImpressionEvent(placementId)
                    viewableDetector.start(this@BannerView, INTERSTITIAL_MAX_TICK_COUNT) {
                        controller.triggerViewableImpression(placementId)
                        hasBeenVisible?.let { it() }
                        omSessionManager.sendAdImpression()
                    }
                    omSessionManager.sendAdLoaded()
                }

                override fun webViewOnError() = controller.adFailedToShow()
                override fun webViewOnClick(url: String) = controller.handleAdTap(url, context)
            })
        }
    }

    private fun removeWebViewsWithDelay() {
        webViewWrapperStore.forEach {
            removeView(it.value)
            webViewWrapperStore.remove(it.key)
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                it.value.destroy()
            }, 1000)
        }
    }

    private fun isAdPlayedBefore(): Boolean = currentWebViewWrapper != null

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        logger.info("BannerView.onDetachedFromWindow")
    }

    private fun makeAdRequest(options: Map<String, Any>?): AdRequest = AdRequest(
        test = controller.config.testEnabled,
        pos = AdRequest.Position.AboveTheFold.value,
        skip = AdRequest.Skip.No.value,
        playbackMethod = AdRequest.PlaybackSoundOnScreen,
        startDelay = AdRequest.StartDelay.PreRoll.value,
        install = AdRequest.FullScreen.Off.value,
        w = width,
        h = height,
        options = options,
    )

    internal fun configure(placementId: Int, delegate: SAInterface?, hasBeenVisible: VoidBlock) {
        this.placementId = placementId
        delegate?.let { setListener(it) }
        this.hasBeenVisible = hasBeenVisible
    }
}
