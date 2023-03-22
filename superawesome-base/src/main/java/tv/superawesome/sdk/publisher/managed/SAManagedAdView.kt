package tv.superawesome.sdk.publisher.managed

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.RelativeLayout
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.lib.sasession.session.SASession
import tv.superawesome.lib.sautils.SAClock
import tv.superawesome.sdk.publisher.SADefaults

@SuppressLint("AddJavascriptInterface")
class SAManagedAdView
@JvmOverloads
constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    private val clock: SAClock = SAClock()
) : RelativeLayout(ctx, attrs) {

    companion object {
        private const val MIME_TYPE = ""
        private const val ENCODING = ""
        private val HISTORY: String? = null
        private const val JS_BRIDGE_NAME = "SA_AD_JS_BRIDGE"
    }

    private val bannerBackgroundColor: Int = Color.rgb(224, 224, 224)
    private var session: SASession = SASession(ctx)
    private var placementId: Int = 0
    private var isParentalGateEnabled: Boolean = false
    private var isBumperPageEnabled: Boolean = false

    var listener: SACustomWebView.Listener? = null
        set(value) {
            webView.listener = value
            field = value
        }

    private val webView: SACustomWebView by lazy {
        SACustomWebView(ctx).apply {
            setupWebView()
            this@SAManagedAdView.addView(this)
        }
    }

    init {
        setColor(SADefaults.defaultBgColor())
        setParentalGate(SADefaults.defaultParentalGate())
        setBumperPage(SADefaults.defaultBumperPage())
        setConfiguration(SADefaults.defaultConfiguration())
        setTestMode(SADefaults.defaultTestMode())
    }

    fun load(placementId: Int, html: String, listener: AdViewJavaScriptBridge.Listener) {
        this.placementId = placementId
        webView.removeJavascriptInterface(JS_BRIDGE_NAME)
        webView.addJavascriptInterface(AdViewJavaScriptBridge(listener), JS_BRIDGE_NAME)
        val updatedHTML = html.replace("_TIMESTAMP_", clock.timestamp.toString())
        webView.loadDataWithBaseURL(session.baseUrl, updatedHTML, MIME_TYPE, ENCODING, HISTORY)
    }

    fun setColor(value: Boolean) {
        if (value) {
            setBackgroundColor(Color.TRANSPARENT)
        } else {
            setBackgroundColor(bannerBackgroundColor)
        }
    }

    fun setConfigurationProduction() {
        setConfiguration(SAConfiguration.PRODUCTION)
    }

    fun setConfigurationStaging() {
        setConfiguration(SAConfiguration.STAGING)
    }

    fun setConfigurationDev() {
        setConfiguration(SAConfiguration.DEV)
    }

    fun setConfiguration(value: SAConfiguration?) {
        session.configuration = value
    }

    fun setTestMode(value: Boolean) {
        session.testMode = value
    }

    fun enableParentalGate() {
        setParentalGate(true)
    }

    fun disableParentalGate() {
        setParentalGate(false)
    }

    fun enableBumperPage() {
        setBumperPage(true)
    }

    fun disableBumperPage() {
        setBumperPage(false)
    }

    fun enableTestMode() {
        session.testMode = true
    }

    fun disableTestMode() {
        session.testMode = false
    }

    fun setParentalGate(value: Boolean) {
        isParentalGateEnabled = value
    }

    fun setBumperPage(value: Boolean) {
        isBumperPageEnabled = value
    }
}

@SuppressLint("SetJavaScriptEnabled")
internal fun WebView.setupWebView() {
    layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
    setBackgroundColor(Color.TRANSPARENT)
    isVerticalScrollBarEnabled = false
    isHorizontalScrollBarEnabled = false
    scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
    isFocusableInTouchMode = false

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        settings.mediaPlaybackRequiresUserGesture = false
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        WebView.setWebContentsDebuggingEnabled(true)
    }
    settings.javaScriptEnabled = true
}
