@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.ui.managed

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import org.koin.java.KoinJavaComponent
import tv.superawesome.sdk.publisher.common.components.ImageProviderType
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.components.TimeProviderType
import tv.superawesome.sdk.publisher.common.extensions.toPx
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.models.VoidBlock
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.common.state.CloseButtonState
import tv.superawesome.sdk.publisher.common.ui.banner.CustomWebView
import tv.superawesome.sdk.publisher.common.ui.common.AdControllerType
import tv.superawesome.sdk.publisher.common.ui.common.Config
import tv.superawesome.sdk.publisher.common.ui.common.ViewableDetectorType
import tv.superawesome.sdk.publisher.common.ui.common.videoMaxTickCount

@SuppressLint("AddJavascriptInterface")
public class ManagedAdView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val controller: AdControllerType by KoinJavaComponent.inject(AdControllerType::class.java)
    private val imageProvider: ImageProviderType by KoinJavaComponent.inject(ImageProviderType::class.java)
    private val timeProvider: TimeProviderType by KoinJavaComponent.inject(TimeProviderType::class.java)
    private val logger: Logger by KoinJavaComponent.inject(Logger::class.java)
    private val environment: Environment by KoinJavaComponent.inject(Environment::class.java)

    private var placementId: Int = 0
    private var webView: CustomWebView? = null
    private var padlockButton: ImageButton? = null
    private val viewableDetector: ViewableDetectorType by KoinJavaComponent.inject(
        ViewableDetectorType::class.java
    )
    private var hasBeenVisible: VoidBlock? = null

    init {
        setColor(Constants.defaultBackgroundColorEnabled)
        isSaveEnabled = true
        addWebView()
    }

    public fun load(placementId: Int, html: String, listener: AdViewJavaScriptBridge.Listener) {
        logger.info("load($placementId)")
        this.placementId = placementId
        showPadlockIfNeeded()

        webView?.removeJavascriptInterface(JS_BRIDGE_NAME)
        webView?.addJavascriptInterface(AdViewJavaScriptBridge(listener), JS_BRIDGE_NAME)
        val updatedHTML = html.replace("_TIMESTAMP_", timeProvider.millis().toString())
        webView?.loadDataWithBaseURL(environment.baseUrl, updatedHTML, MIME_TYPE, ENCODING, null)
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

    public fun setListener(delegate: SAInterface) {
        controller.delegate = delegate
    }

    public fun setConfig(config: Config) {
        controller.config = config
    }

    /**
     * Method that gets called in order to close the banner ad, remove any fragments, etc
     */
    public fun close() {
        hasBeenVisible = null
        viewableDetector.cancel()
        removeWebView()
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

    public fun setBackButton(value: Boolean) {
        controller.config.isBackButtonEnabled = value
    }

    public fun enableBackButton() {
        setBackButton(true)
    }

    public fun disableBackButton() {
        setBackButton(false)
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

    public fun setCloseButton(value: Boolean) {
        controller.config.closeButtonState =
            if (value) CloseButtonState.VisibleWithDelay else CloseButtonState.Hidden
    }

    public fun enableCloseButton() {
        setCloseButton(true)
    }

    public fun disableCloseButton() {
        setCloseButton(false)
    }

    public fun setColor(value: Boolean) {
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

        padlockButton.setOnClickListener { controller.handleSafeAdTap(context) }

        webView?.addView(padlockButton)

        this.padlockButton = padlockButton
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun addWebView() {
        removeWebView()

        val webView = CustomWebView(context)
        webView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false
        webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webView.isFocusableInTouchMode = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webView.settings.mediaPlaybackRequiresUserGesture = false
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        webView.settings.javaScriptEnabled = true

        webView.listener = object : CustomWebView.Listener {
            override fun webViewOnStart() {
                controller.adShown()
                viewableDetector.cancel()
                controller.triggerImpressionEvent(placementId)
                viewableDetector.start(this@ManagedAdView, videoMaxTickCount) {
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

    internal fun configure(placementId: Int, delegate: SAInterface?, hasBeenVisible: VoidBlock) {
        this.placementId = placementId
        delegate?.let { setListener(it) }
        this.hasBeenVisible = hasBeenVisible
    }
}

private const val JS_BRIDGE_NAME = "SA_AD_JS_BRIDGE"
private const val MIME_TYPE = ""
private const val ENCODING = ""
