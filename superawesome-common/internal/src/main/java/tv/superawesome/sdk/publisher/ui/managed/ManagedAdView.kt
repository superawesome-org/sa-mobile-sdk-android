@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.ui.managed

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import tv.superawesome.sdk.publisher.components.ImageProviderType
import tv.superawesome.sdk.publisher.components.Logger
import tv.superawesome.sdk.publisher.components.TimeProviderType
import tv.superawesome.sdk.publisher.extensions.toPx
import tv.superawesome.sdk.publisher.models.CloseButtonState
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.ui.banner.CustomWebView
import tv.superawesome.sdk.publisher.ad.AdConfig
import tv.superawesome.sdk.publisher.ad.AdManager
import tv.superawesome.sdk.publisher.ad.AdController
import tv.superawesome.sdk.publisher.models.SAEvent
import tv.superawesome.sdk.publisher.ui.common.ClickThrottler
import tv.superawesome.sdk.publisher.ui.common.clickWithThrottling

/**
 * The view that displays the ad.
 */
@SuppressLint("AddJavascriptInterface")
@Suppress("TooManyFunctions")
public class ManagedAdView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), KoinComponent {

    internal val adManager: AdManager by inject()
    private val imageProvider: ImageProviderType by inject()
    private val timeProvider: TimeProviderType by inject()
    private val logger: Logger by inject()
    private val controller: AdController by inject {
        parametersOf(placementId)
    }

    private var placementId: Int = 0
    private var webView: CustomWebView? = null
    private var padlockButton: ImageButton? = null

    private val clickThrottler = ClickThrottler()
    private val scope = if (context is AppCompatActivity) {
        context.lifecycleScope
    } else {
        MainScope()
    }

    init {
        setColor(Constants.defaultBackgroundColorEnabled)
        isSaveEnabled = true
        addWebView()
    }

    internal fun load(
        placementId: Int,
        html: String,
        baseUrl: String?,
        listener: AdViewJavaScriptBridge.Listener,
    ) {
        logger.info("load($placementId)")
        this.placementId = placementId
        showPadlockIfNeeded()

        webView?.removeJavascriptInterface(JS_BRIDGE_NAME)
        webView?.addJavascriptInterface(AdViewJavaScriptBridge(listener, logger), JS_BRIDGE_NAME)
        val updatedHTML = html.replace("_TIMESTAMP_", timeProvider.millis().toString())
        webView?.loadDataWithBaseURL(baseUrl, updatedHTML, MIME_TYPE, ENCODING, null)
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
     * Sets the ad listener.
     *
     * @param delegate the ad listener.
     */
    public fun setListener(delegate: SAInterface) {
        adManager.listener = delegate
    }

    /**
     * Sets the ad configuration.
     *
     * @param adConfig the [AdConfig] object to configure the ad.
     */
    public fun setConfig(adConfig: AdConfig) {
        adManager.adConfig.copyFrom(adConfig)
    }

    /**
     * Closes the banner ad, remove any fragments, etc.
     */
    public fun close() {
        removeWebView()
        controller.close()
    }

    /**
     * Determines if an ad is available.
     *
     * @return true or false
     */
    public fun hasAdAvailable(): Boolean = adManager.hasAdAvailable(placementId)

    /**
     * Gets whether the ad is closed.
     *
     * @return `true` if the ad is closed, `false` otherwise.
     */
    public fun isClosed(): Boolean = controller.isAdClosed

    /**
     * Enables showing the parental gate.
     */
    public fun enableParentalGate() {
        setParentalGate(true)
    }

    /**
     * Disables showing the parental gate.
     */
    public fun disableParentalGate() {
        setParentalGate(false)
    }

    /**
     * Enables showing the bumper page.
     */
    public fun enableBumperPage() {
        setBumperPage(true)
    }

    /**
     * Disables showing the bumper page.
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
     * Sets the background color to transparent.
     */
    public fun setColorTransparent() {
        setColor(true)
    }

    /**
     * Sets the background color to gray.
     */
    public fun setColorGray() {
        setColor(false)
    }

    /**
     * Sets the back button state.
     *
     * @param value `true` to enable back button, `false` to disable it.
     */
    public fun setBackButton(value: Boolean) {
        controller.adConfig.isBackButtonEnabled = value
    }

    /**
     * Enables the back button.
     */
    public fun enableBackButton() {
        setBackButton(true)
    }

    /**
     * Disables the back button.
     */
    public fun disableBackButton() {
        setBackButton(false)
    }

    /**
     * Sets whether the parental gate should show.
     *
     * @param value `true` to show the parental gate, `false` otherwise.
     */
    public fun setParentalGate(value: Boolean) {
        controller.adConfig.isParentalGateEnabled = value
    }

    /**
     * Sets whether bumper page should show.
     *
     * @param value `true` to show the bumper page, `false` otherwise.
     */
    public fun setBumperPage(value: Boolean) {
        controller.adConfig.isBumperPageEnabled = value
    }

    /**
     * Sets the test mode.
     *
     * @param value `true` to enable test mode, `false` otherwise.
     */
    public fun setTestMode(value: Boolean) {
        controller.adConfig.testEnabled = value
    }

    /**
     * Sets the close button state.
     *
     * @param value `true` enables the close button, `false` disables it.
     */
    public fun setCloseButton(value: Boolean) {
        controller.adConfig.closeButtonState =
            if (value) CloseButtonState.VisibleWithDelay else CloseButtonState.Hidden
    }

    /**
     * Enables the close button.
     */
    public fun enableCloseButton() {
        setCloseButton(true)
    }

    /**
     * Disables the close button.
     */
    public fun disableCloseButton() {
        setCloseButton(false)
    }

    /**
     * Sets the background color.
     *
     * @param value `true` makes it transparent, while `false` makes it gray.
     */
    public fun setColor(value: Boolean) {
        if (value) {
            setBackgroundColor(Color.TRANSPARENT)
        } else {
            setBackgroundColor(Constants.backgroundColorGray)
        }
    }

    /**
     * Plays the video.
     */
    fun playVideo() {
        webView?.evaluateJavascript(
            "window.dispatchEvent(new Event('$JS_BRIDGE_NAME.appRequestedPlay'));",
            null,
        )
    }

    /**
     * Pauses the video.
     */
    fun pauseVideo() {
        webView?.evaluateJavascript(
            "window.dispatchEvent(new Event('$JS_BRIDGE_NAME.appRequestedPause'));",
            null,
        )
    }

    private fun showPadlockIfNeeded() {
        if (!controller.shouldShowPadlock || webView == null) return

        val padlockButton = ImageButton(context)
        padlockButton.setImageBitmap(imageProvider.padlockImage())
        padlockButton.setBackgroundColor(Color.TRANSPARENT)
        padlockButton.scaleType = ImageView.ScaleType.FIT_XY
        padlockButton.setPadding(0, 2.toPx, 0, 0)
        padlockButton.layoutParams = ViewGroup.LayoutParams(77.toPx, 31.toPx)

        padlockButton.clickWithThrottling {
            controller.handleSafeAdClick(context)
        }

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
        webView.settings.mediaPlaybackRequiresUserGesture = false
        WebView.setWebContentsDebuggingEnabled(true)
        webView.settings.javaScriptEnabled = true

        webView.listener = object : CustomWebView.Listener {
            private val clickThrottler = ClickThrottler()
            override fun webViewOnStart() {
                scope.launch {
                    controller.triggerImpressionEvent()
                }
            }
            override fun webViewOnError() {
                controller.listener?.onEvent(placementId, SAEvent.adFailedToShow)
            }
            override fun webViewOnClick(url: String) {
                clickThrottler.onClick { controller.handleAdClick(url, context) }
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

    internal fun configure(placementId: Int, delegate: SAInterface?) {
        this.placementId = placementId
        delegate?.let { setListener(it) }
    }
}

private const val JS_BRIDGE_NAME = "SA_AD_JS_BRIDGE"
private const val MIME_TYPE = ""
private const val ENCODING = ""
