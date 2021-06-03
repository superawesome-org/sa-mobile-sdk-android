package tv.superawesome.sdk.publisher.managed

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.RelativeLayout
import org.json.JSONObject
import tv.superawesome.lib.saadloader.SALoader
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.lib.sasession.session.SASession
import tv.superawesome.sdk.publisher.SADefaults
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterface
import java.net.URLEncoder


@SuppressLint("AddJavascriptInterface")
class SAManagedBannerAd
@JvmOverloads
constructor(ctx: Context, attrs: AttributeSet? = null): RelativeLayout(ctx, attrs), WebViewJavaScriptInterface.Listener {

    companion object {
        private const val MIME_TYPE = ""
        private const val ENCODING = ""
        private val HISTORY: String? = null
        private const val JS_BRIDGE_NAME = "SA_AD_JS_BRIDGE"
    }

    private val bannerBackgroundColor: Int = Color.rgb(224, 224, 224)

    private var session: SASession = SASession(ctx)
    private var loader: SALoader = SALoader(ctx)
    private var placementId: Int = 0
    private var listener: SAInterface? = null
    private var isParentalGateEnabled: Boolean = false
    private var isBumperPageEnabled: Boolean = false

    private val webView: WebView by lazy {
        WebView(ctx).apply {
            this.setupWebView()
            this@SAManagedBannerAd.addView(this)
            this.addJavascriptInterface(WebViewJavaScriptInterface(this@SAManagedBannerAd), JS_BRIDGE_NAME)
        }
    }

    init {
        setColor(SADefaults.defaultBgColor())
        setParentalGate(SADefaults.defaultParentalGate())
        setBumperPage(SADefaults.defaultBumperPage())
        setConfiguration(SADefaults.defaultConfiguration())
        setTestMode(SADefaults.defaultTestMode())
    }

    fun load(placementId: Int) {
        val baseUrl = session.baseUrl

        this.placementId = placementId
        val html = formHTML(placementId = placementId, baseUrl = baseUrl)
        webView.loadDataWithBaseURL(baseUrl, html, MIME_TYPE, ENCODING, HISTORY)
    }

    private fun formHTML(placementId: Int, baseUrl: String): String {
        val queryObject: JSONObject = loader.getAwesomeAdsQuery(session)
        val queryParams = queryObject.toQueryParams()
        Log.d("AwesomeAds", "Test: $queryParams")
        val scriptHtml = "<script type=\"text/javascript\" src=\"${baseUrl}/ad.js?placement=${placementId}${queryParams}\"></script>"
        return "<html><header><meta name='viewport' content='width=device-width'/><style>html, body, div { margin: 0px; padding: 0px; } html, body { width: 100%; height: 100%; }</style></header><body>${scriptHtml}</body></html>"
    }

    fun JSONObject.toQueryParams(): String {
        var queryParams = ""

        val looper = this.keys()
        while(looper.hasNext()) {
            val key = looper.next()
            if (key.equals("test") && this.get(key).toString() == "false") {
                continue
            }
            queryParams += "&${key}=${URLEncoder.encode(this.get(key).toString())}"
        }

        return queryParams
    }

    fun setListener(value: SAInterface? = null) {
        this.listener = value
    }

    // WebViewJavaScriptInterface.Listener

    override fun onClick(url: String) {
        listener?.onEvent(this.placementId, SAEvent.adClicked)

        val destination = parseUrl(url = url) ?: return
        context.startActivity(Intent(Intent.ACTION_VIEW, destination))
    }

    override fun onError() {
        listener?.onEvent(this.placementId, SAEvent.adFailedToLoad)
    }

    override fun onEmpty() {
        listener?.onEvent(this.placementId, SAEvent.adEmpty)
    }

    private fun parseUrl(url: String): Uri? = try {
        Uri.parse(url)
    } catch (e: Throwable) {
        null
    }

    // Setters and Getters

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

class WebViewJavaScriptInterface(private val listener: Listener) {

    @JavascriptInterface
    fun onClick(url: String) = listener.onClick(url = url)

    @JavascriptInterface
    fun onError() = listener.onError()

    @JavascriptInterface
    fun onEmpty() = listener.onEmpty()

    interface Listener {
        fun onClick(url: String)
        fun onError()
        fun onEmpty()
    }
}