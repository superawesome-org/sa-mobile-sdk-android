@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.ui.managed

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tv.superawesome.sdk.publisher.common.components.ImageProviderType
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.components.SdkInfoType
import tv.superawesome.sdk.publisher.common.components.TimeProviderType
import tv.superawesome.sdk.publisher.common.extensions.toPx
import tv.superawesome.sdk.publisher.common.models.CloseButtonState
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.openmeasurement.FriendlyObstructionType
import tv.superawesome.sdk.publisher.common.openmeasurement.OpenMeasurementAdType
import tv.superawesome.sdk.publisher.common.openmeasurement.OpenMeasurementSessionManagerType
import tv.superawesome.sdk.publisher.common.openmeasurement.PARTNER_NAME
import tv.superawesome.sdk.publisher.common.ui.banner.CustomWebView
import tv.superawesome.sdk.publisher.common.ui.banner.WebViewWrapper
import tv.superawesome.sdk.publisher.common.ui.common.AdControllerType
import tv.superawesome.sdk.publisher.common.ui.common.Config

/**
 * The view that displays the ad.
 */
@SuppressLint("AddJavascriptInterface")
@Suppress("TooManyFunctions")
public class ManagedAdView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr), KoinComponent {

    internal val controller: AdControllerType by inject()
    private val imageProvider: ImageProviderType by inject()
    private val sdkInfo: SdkInfoType by inject()
    private val timeProvider: TimeProviderType by inject()
    private val omSessionManager: OpenMeasurementSessionManagerType by inject()
    private val logger: Logger by inject()

    private var placementId: Int = 0
    private var webViewWrapper: WebViewWrapper? = null
    private var padlockButton: ImageButton? = null

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
        webViewWrapper?.webView?.removeJavascriptInterface(JS_BRIDGE_NAME)
        webViewWrapper?.webView?.addJavascriptInterface(
            AdViewJavaScriptBridge(listener, logger),
            JS_BRIDGE_NAME,
        )

        val updatedHTML = html.replace("_TIMESTAMP_", timeProvider.millis().toString())
        val omUpdatedHTML = updatedHTML.replace(
            "<html><header>",
            "<html><header><script type='text/javascript'>" +
                    "var sessionClient;\n" +
                    "try {\n" +
                    "  sessionClient = OmidSessionClient[$SESSION_CLIENT_VERSION];\n" +
                    "} catch (e) {}\n" +
                    "if (!sessionClient) {\n" +
                    "  return;\n" +
                    "}\n" +
                    "const AdSession = sessionClient.AdSession;\n" +
                    "const Partner = sessionClient.Partner;\n" +
                    "const Context = sessionClient.Context;\n" +
                    "const VerificationScriptResource = sessionClient.VerificationScriptResource;\n" +
                    "const AdEvents = sessionClient.AdEvents;\n" +
                    "const MediaEvents = sessionClient.MediaEvents;" +
                    "var resources = [];\n" +

                    // Update these with actual values
                    "var vendorKey = \"123456\"; // parsed from \"vendor\" attribute\n" +
                    "var params = \"\"; // parsed from VerificationParameters as a string\n" +
                    "var url = \"http://www.google.com\"; // parsed from JavaScriptResource\n" +

                    "var resource = new VerificationScriptResource(url, vendorKey, params);\n" +
                    "resources.add(resource);\n" +
                    "var partner = new Partner($PARTNER_NAME, ${sdkInfo.versionNumber});\n" +
                    "var context = new Context(partner, resources);\n" +
                    "var adSession = new AdSession(context);\n" +
                    "// elementBounds is a rect {x, y, width, height} that indicates the" +
                    " position of the video element\n" +
                    "// inside the iframe.\n" +
                    "adSession.setElementBounds(elementBounds)\n" +
                    "const iframeElement = document.getElementById(\"result\");\n" +
                    "context.setSlotElement(iframeElement);\n" +
                    "adSession.setElementBounds(elementBounds)\n" +
                    "const adEvents = new AdEvents(adSession);\n" +
                    "const mediaEvents = new MediaEvents(adSession);\n" +
                    "adSession.start()\n" +
                    "adSession.registerSessionObserver((event) => {\n" +
                    "  if (event.type === \"sessionStart\") {\n" +
                    "    // setup code\n" +
                    "    // Set the impression/creative type here.\n" +
                    "    if (event.data.creativeType === 'definedByJavaScript') {\n" +
                    "      adSession.setCreativeType('video');\n" +
                    "    }\n" +
                    "    if (event.data.impressionType === 'definedByJavaScript') {\n" +
                    "      adSession.setImpressionType('beginToRender');\n" +
                    "    }\n" +
                    "    var skippable = false;\n" +
                    "    var offsetForSkip = 0;\n" +
                    "    var autoplay = true;\n" +
                    "    var position = 0;\n" +
                    "    // load event\n" +
                    "    adEvents.loaded({\n" +
                    "      isSkippable: skippable,\n" +
                    "      skipOffset: offsetForSkip,\n" +
                    "      isAutoPlay: autoplay,\n" +
                    "      position: position\n" +
                    "    });\n" +
                    "    // other event code\n" +
                    "    adEvents.impressionOccurred();\n" +
                    "  }\n" +
                    "  else if (event.type === \"sessionError\") {\n" +
                    "    // handle error\n" +
                    "  } else if (event.type === \"sessionFinish\") {\n" +
                    "    // clean up\n" +
                    "  }\n" +
                    "});" +
                    "</script>")
        webViewWrapper?.webView?.loadHTML(baseUrl, omUpdatedHTML)
        controller.play(placementId)
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
        controller.delegate = delegate
    }

    /**
     * Sets the ad configuration.
     *
     * @param config the [Config] object to configure the ad.
     */
    public fun setConfig(config: Config) {
        controller.config = config
    }

    /**
     * Closes the banner ad, remove any fragments, etc.
     */
    public fun close() {
        removeWebViewWithDelay()
        omSessionManager.finish()
        controller.close()
        controller.videoListener = null
        controller.delegate = null
    }

    /**
     * Determines if an ad is available.
     *
     * @return true or false
     */
    public fun hasAdAvailable(): Boolean = controller.hasAdAvailable(placementId)

    /**
     * Gets whether the ad is closed.
     *
     * @return `true` if the ad is closed, `false` otherwise.
     */
    public fun isClosed(): Boolean = controller.closed

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
        controller.config.isBackButtonEnabled = value
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
        controller.config.isParentalGateEnabled = value
    }

    /**
     * Sets whether bumper page should show.
     *
     * @param value `true` to show the bumper page, `false` otherwise.
     */
    public fun setBumperPage(value: Boolean) {
        controller.config.isBumperPageEnabled = value
    }

    /**
     * Sets the test mode.
     *
     * @param value `true` to enable test mode, `false` otherwise.
     */
    public fun setTestMode(value: Boolean) {
        controller.config.testEnabled = value
    }

    /**
     * Sets the close button state.
     *
     * @param value `true` enables the close button, `false` disables it.
     */
    public fun setCloseButton(value: Boolean) {
        controller.config.closeButtonState =
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
        webViewWrapper?.webView?.evaluateJavascript(
            "window.dispatchEvent(new Event('$JS_BRIDGE_NAME.appRequestedPlay'));",
            null,
        )
    }

    /**
     * Pauses the video.
     */
    fun pauseVideo() {
        webViewWrapper?.webView?.evaluateJavascript(
            "window.dispatchEvent(new Event('$JS_BRIDGE_NAME.appRequestedPause'));",
            null,
        )
    }

    private fun showPadlockIfNeeded() {
        if (!controller.shouldShowPadlock) return

        webViewWrapper?.let { webView ->
            padlockButton?.let {
                webView.removeView(it)
                padlockButton = null
            }

            val padlockButton = ImageButton(context)
            padlockButton.setImageBitmap(imageProvider.padlockImage())
            padlockButton.setBackgroundColor(Color.TRANSPARENT)
            padlockButton.scaleType = ImageView.ScaleType.FIT_XY
            padlockButton.setPadding(0, 2.toPx, 0, 0)
            padlockButton.layoutParams = ViewGroup.LayoutParams(77.toPx, 31.toPx)
            padlockButton.contentDescription = "Safe Ad Logo"

            padlockButton.setOnClickListener { controller.handleSafeAdTap(context) }

            webView.addView(padlockButton)

            this.padlockButton = padlockButton
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun addWebView() {
        removeWebViewWithDelay()

        val webViewWrapper = WebViewWrapper(context)
        webViewWrapper.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        webViewWrapper.webView.setBackgroundColor(Color.TRANSPARENT)
        webViewWrapper.webView.isVerticalScrollBarEnabled = false
        webViewWrapper.webView.isHorizontalScrollBarEnabled = false
        webViewWrapper.webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webViewWrapper.webView.isFocusableInTouchMode = false
        webViewWrapper.webView.settings.mediaPlaybackRequiresUserGesture = false
        WebView.setWebContentsDebuggingEnabled(true)
        webViewWrapper.webView.settings.javaScriptEnabled = true

        webViewWrapper.webView.listener = object : CustomWebView.Listener {
            override fun webViewOnStart() {
                omSessionManager.setup(webViewWrapper.webView, OpenMeasurementAdType.HTMLVideo)
                controller.triggerImpressionEvent(placementId)
                addSafeAdToOM()
                omSessionManager.start()
            }
            override fun webViewOnError() = controller.adFailedToShow()
            override fun webViewOnClick(url: String) = controller.handleAdTap(url, context)
        }

        addView(webViewWrapper)

        this.webViewWrapper = webViewWrapper
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

    private fun removeWebViewWithDelay() {
        if (webViewWrapper != null) {
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                webViewWrapper?.destroy()
                removeView(webViewWrapper)
                webViewWrapper = null
            }, WEB_VIEW_REMOVAL_TIME)
        }
    }

    internal fun configure(placementId: Int, delegate: SAInterface?) {
        this.placementId = placementId
        delegate?.let { setListener(it) }
    }

    companion object {
        private const val WEB_VIEW_REMOVAL_TIME = 1000L
        private const val SESSION_CLIENT_VERSION = "1.4.7"
    }
}

private const val JS_BRIDGE_NAME = "SA_AD_JS_BRIDGE"
