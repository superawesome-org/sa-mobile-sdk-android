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
import com.iab.omid.library.superawesome.adsession.AdEvents
import com.iab.omid.library.superawesome.adsession.AdSession
import com.iab.omid.library.superawesome.adsession.FriendlyObstructionPurpose
import org.koin.java.KoinJavaComponent.inject
import tv.superawesome.sdk.publisher.common.components.ImageProviderType
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.components.TimeProviderType
import tv.superawesome.sdk.publisher.common.extensions.toPx
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.models.VoidBlock
import tv.superawesome.sdk.publisher.common.openmeasurement.OMAdSessionBuilderType
import tv.superawesome.sdk.publisher.common.openmeasurement.OMSDKJSInjectorType
import tv.superawesome.sdk.publisher.common.ui.common.AdControllerType
import tv.superawesome.sdk.publisher.common.ui.common.ViewableDetectorType
import tv.superawesome.sdk.publisher.common.ui.common.interstitialMaxTickCount
import java.lang.Exception

public class BannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    internal val controller: AdControllerType by inject(AdControllerType::class.java)
    private val imageProvider: ImageProviderType by inject(ImageProviderType::class.java)
    private val logger: Logger by inject(Logger::class.java)
    private val timeProvider: TimeProviderType by inject(TimeProviderType::class.java)
    private val sessionBuilder: OMAdSessionBuilderType by inject(OMAdSessionBuilderType::class.java)
    private val omsdkInjector: OMSDKJSInjectorType by inject(OMSDKJSInjectorType::class.java)

    private var placementId: Int = 0
    private var webView: CustomWebView? = null
    private var padlockButton: ImageButton? = null
    private val viewableDetector: ViewableDetectorType by inject(ViewableDetectorType::class.java)
    private var hasBeenVisible: VoidBlock? = null
    private var adSession: AdSession? = null
    private var adEvents: AdEvents? = null

    init {
        setColor(Constants.defaultBackgroundColorEnabled)
        isSaveEnabled = true
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
     * One of the main public methods of the SABannerAd class. This will load a new SAAd object
     * corresponding to a given placement Id.
     *
     * @param placementId Awesome Ads ID for ad data to be loaded
     * @param options: an optional dictionary of data to send with an ad's requests and events.
     * Supports String or Int values.
     */
    public fun load(placementId: Int, options: Map<String, Any>? = null) {
        logger.info("load($placementId)")
        this.placementId = placementId
        if (isAdPlayedBefore()) {
            close()
        }
        controller.load(placementId, makeAdRequest(options))
    }

    /**
     * Static method that loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId the Ad placement id to load data for
     * @param lineItemId
     * @param creativeId id of the Creative
     * @param options: an optional dictionary of data to send with an ad's requests and events.
     * Supports String or Int values.
     */
    public fun load(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        options: Map<String, Any>? = null
    ) {
        logger.info("load($placementId, $lineItemId, $creativeId)")
        this.placementId = placementId
        if (isAdPlayedBefore()) {
            close()
        }
        controller.load(placementId, lineItemId, creativeId, makeAdRequest(options))
    }

    /**
     * One of the main public methods of the SABannerAd class. This will play an already existing
     * loaded ad, or fail.
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

        var bodyHtml = data.second
            .replace("_TIMESTAMP_", timeProvider.millis().toString())

        // load the HTML
        try {
            bodyHtml = omsdkInjector.injectOmsdkJavascript(
                context,
                bodyHtml,
            ) ?: bodyHtml
        } catch (error: Exception) {
            logger.error("Failed to inject Open Measurement SDK", error)
        }
        webView?.loadHTML(data.first, bodyHtml)
    }

    public fun setListener(delegate: SAInterface) {
        controller.delegate = delegate
    }

    /**
     * Method that gets called in order to close the banner ad, remove any fragments, etc
     */
    public fun close() {
        hasBeenVisible = null
        viewableDetector.cancel()
        removeWebView(delayed = true)
        controller.close()
    }

    /**
     * Method that determines if an ad is available
     *
     * @return true or false
     */
    public fun hasAdAvailable(): Boolean = controller.hasAdAvailable(placementId)

    public fun isClosed(): Boolean = controller.closed

    public fun enableParentalGate() {
        setParentalGate(true)
    }

    public fun disableParentalGate() {
        setParentalGate(false)
    }

    public fun enableBumperPage() {
        setBumperPage(true)
    }

    public fun disableBumperPage() {
        setBumperPage(false)
    }

    public fun enableTestMode() {
        setTestMode(true)
    }

    public fun disableTestMode() {
        setTestMode(false)
    }

    public fun setColorTransparent() {
        setColor(true)
    }

    public fun setColorGray() {
        setColor(false)
    }

    public fun setParentalGate(value: Boolean) {
        controller.config.isParentalGateEnabled = value
    }

    public fun setBumperPage(value: Boolean) {
        controller.config.isBumperPageEnabled = value
    }

    public fun setTestMode(value: Boolean) {
        controller.config.testEnabled = value
    }

    public fun setColor(value: Boolean) {
        if (value) {
            setBackgroundColor(Color.TRANSPARENT)
        } else {
            setBackgroundColor(Constants.backgroundColorGray)
        }
    }

    fun setCloseButtonView(closeButton: View) {
        adSession?.addFriendlyObstruction(closeButton, FriendlyObstructionPurpose.CLOSE_AD, null)
    }

    private fun showPadlockIfNeeded() {
        if (!controller.shouldShowPadlock || webView == null) return

        val padlockButton = ImageButton(context)
        padlockButton.setImageBitmap(imageProvider.padlockImage())
        padlockButton.setBackgroundColor(Color.TRANSPARENT)
        padlockButton.scaleType = ImageView.ScaleType.FIT_XY
        padlockButton.setPadding(0, 2.toPx, 0, 0)
        padlockButton.layoutParams = ViewGroup.LayoutParams(77.toPx, 31.toPx)
        padlockButton.contentDescription = "Safe Ad Logo"

        padlockButton.setOnClickListener { controller.handleSafeAdTap(context) }

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
            override fun webViewOnStart() {
                controller.adShown()
                viewableDetector.cancel()
                controller.triggerImpressionEvent(placementId)
                viewableDetector.start(this@BannerView, interstitialMaxTickCount) {
                    controller.triggerViewableImpression(placementId)
                    hasBeenVisible?.let { it() }
                    try {
                        adEvents?.impressionOccurred()
                    } catch (error: Throwable) {
                        logger.error("Unable to log Open Measurement impression", error)
                    }
                }
                setupAdSession()
            }

            override fun webViewOnError() = controller.adFailedToShow()
            override fun webViewOnClick(url: String) = controller.handleAdTap(url, context)
        }

        addView(webView)

        this.webView = webView
    }

    private fun removeWebView(delayed: Boolean = false) {
        webView?.let {
            removeView(webView)
            if (delayed) {
                removeWebViewWithDelay()
            } else {
                webView?.destroy()
                webView = null
            }
        }
    }

    private fun removeWebViewWithDelay() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            webView?.loadUrl("about:blank")
            webView?.destroy()
            webView = null
        }, 1000)
    }

    private fun isAdPlayedBefore(): Boolean = webView != null

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

    private fun setupAdSession() {
        try {
            adSession = sessionBuilder.getHtmlAdSession(
                context,
                webView,
                null,
            )
            padlockButton?.let {
                adSession?.addFriendlyObstruction(it, FriendlyObstructionPurpose.OTHER, null)
            }
            adSession?.start()
        } catch (error: IllegalArgumentException) {
            logger.error("Failed to setup ad session for open measurement", error)
            return
        }
        try {
            adEvents = AdEvents.createAdEvents(adSession)
            adEvents?.loaded()
        } catch (error: IllegalArgumentException) {
            logger.error("Failed to setup ad events for open measurement", error)
            return
        }

    }

    internal fun configure(placementId: Int, delegate: SAInterface?, hasBeenVisible: VoidBlock) {
        this.placementId = placementId
        delegate?.let { setListener(it) }
        this.hasBeenVisible = hasBeenVisible
    }
}
