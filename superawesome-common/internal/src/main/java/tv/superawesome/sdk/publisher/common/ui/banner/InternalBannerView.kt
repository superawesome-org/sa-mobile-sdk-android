@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.ui.banner

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tv.superawesome.sdk.publisher.common.components.ImageProviderType
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.components.TimeProviderType
import tv.superawesome.sdk.publisher.common.extensions.toPx
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.models.DefaultAdRequest
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.models.VoidBlock
import tv.superawesome.sdk.publisher.common.ui.AdView
import tv.superawesome.sdk.publisher.common.ui.common.AdControllerType
import tv.superawesome.sdk.publisher.common.ui.common.ViewableDetectorType
import tv.superawesome.sdk.publisher.common.ui.common.INTERSTITIAL_MAX_TICK_COUNT

/**
 * View that shows banner ads.
 */
@Suppress("TooManyFunctions")
public class InternalBannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), AdView, KoinComponent {

    internal val controller: AdControllerType by inject()
    private val imageProvider: ImageProviderType by inject()
    private val logger: Logger by inject()
    private val timeProvider: TimeProviderType by inject()
    private val viewableDetector: ViewableDetectorType by inject()

    private var placementId: Int = 0
    private var webView: CustomWebView? = null
    private var padlockButton: ImageButton? = null
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

    public override fun load(placementId: Int, options: Map<String, Any>?) {
        logger.info("load($placementId)")
        this.placementId = placementId
        if (isAdPlayedBefore()) {
            close()
        }
        controller.load(placementId, makeAdRequest(options))
    }

    public open fun load(placementId: Int) {
        load(placementId, options = null)
    }

    public override fun load(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        options: Map<String, Any>?,
    ) {
        logger.info("load($placementId, $lineItemId, $creativeId)")
        this.placementId = placementId
        if (isAdPlayedBefore()) {
            close()
        }
        controller.load(placementId, lineItemId, creativeId, makeAdRequest(options))
    }

    public open fun load(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
    ) { load(placementId, lineItemId, creativeId, options = null) }

    /**
     * Plays an already existing loaded ad, or fail.
     */
    public override fun play() {
        logger.info("play($placementId)")
        val adResponse = controller.play(placementId)
        val data = adResponse?.getDataPair()

        if (data == null) {
            controller.adFailedToShow()
            return
        }

        addWebView()
        showPadlockIfNeeded()

        val bodyHtml = data.second
            .replace("_TIMESTAMP_", timeProvider.millis().toString())
        webView?.loadHTML(data.first, bodyHtml)
    }

    /**
     * Registers a callback to be called for certain events.
     *
     * @param delegate the callback delegate.
     */
    public override fun setListener(delegate: SAInterface) {
        controller.delegate = delegate
    }

    /**
     * Gets called in order to close the banner ad, remove any fragments, etc.
     */
    public override fun close() {
        hasBeenVisible = null
        viewableDetector.cancel()
        removeWebView()
        controller.close()
    }

    /**
     * Returns whether an ad is available.
     */
    public override fun hasAdAvailable(): Boolean = controller.hasAdAvailable(placementId)

    /**
     * Returns whether the ad is closed.
     */
    public override fun isClosed(): Boolean = controller.closed

    /**
     * Sets parental gate enabled.
     */
    public override fun setParentalGate(value: Boolean) {
        controller.config.isParentalGateEnabled = value
    }

    /**
     * Sets bumper page enabled.
     */
    public override fun setBumperPage(value: Boolean) {
        controller.config.isBumperPageEnabled = value
    }

    /**
     * Sets the test mode.
     */
    public override fun setTestMode(value: Boolean) {
        controller.config.testEnabled = value
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
                viewableDetector.start(this@InternalBannerView, INTERSTITIAL_MAX_TICK_COUNT) {
                    controller.triggerViewableImpression(placementId)
                    hasBeenVisible?.let { it() }
                }
            }

            override fun webViewOnError() = controller.adFailedToShow()
            override fun webViewOnClick(url: String) = controller.handleAdTap(url, context)
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

    private fun makeAdRequest(options: Map<String, Any>?): AdRequest = DefaultAdRequest(
        test = controller.config.testEnabled,
        pos = AdRequest.Position.AboveTheFold.value,
        skip = AdRequest.Skip.No.value,
        playbackMethod = DefaultAdRequest.PlaybackSoundOnScreen,
        startDelay = AdRequest.StartDelay.PreRoll.value,
        install = AdRequest.FullScreen.Off.value,
        w = width,
        h = height,
        options = options
    )

    internal fun configure(placementId: Int, delegate: SAInterface?, hasBeenVisible: VoidBlock) {
        this.placementId = placementId
        delegate?.let { setListener(it) }
        this.hasBeenVisible = hasBeenVisible
    }
}
