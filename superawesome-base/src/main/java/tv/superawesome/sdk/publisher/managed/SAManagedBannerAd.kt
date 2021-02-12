package tv.superawesome.sdk.publisher.managed

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.FrameLayout
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterface


@SuppressLint("AddJavascriptInterface")
class SAManagedBannerAd
@JvmOverloads
constructor(ctx: Context, attrs: AttributeSet? = null): FrameLayout(ctx, attrs), WebViewJavaScriptInterface.Listener {

    companion object {
        private const val MIME_TYPE = ""
        private const val ENCODING = ""
        private val HISTORY: String? = null

        private const val JS_BRIDGE_NAME = "SA_AD_JS_BRIDGE"
    }

    private var placementId: Int = 0
    private var listener: SAInterface? = null

    private val webView: WebView by lazy {
        WebView(ctx).apply {
            this.setupWebView()
            this@SAManagedBannerAd.addView(this)
            this.addJavascriptInterface(WebViewJavaScriptInterface(this@SAManagedBannerAd), JS_BRIDGE_NAME)
        }
    }

    fun load(placementId: Int) {
        this.placementId = placementId
        val html = formHTML(placementId = placementId)
        webView.loadDataWithBaseURL("https://ads.superawesome.tv", html, MIME_TYPE, ENCODING, HISTORY)
    }

    private fun formHTML(placementId: Int): String {
        val scriptHtml = "<script type=\"text/javascript\" src=\"https://ads.superawesome.tv/v2/ad.js?placement=${placementId}\"></script>"
        return "<html><header><meta name='viewport' content='width=device-width'/><style>html, body, div { margin: 0px; padding: 0px; } html, body { width: 100%; height: 100%; }</style></header><body>${scriptHtml}</body></html>"
    }

    fun setListener(value: SAInterface? = null) {
        this.listener = value
    }

    // WebViewJavaScriptInterface.Listener

    override fun onClick(url: String) {
        listener?.onEvent(this.placementId, SAEvent.adClicked)

        // check parental gate
        // check bumper

        // make sure click is ok
        val destination = parseUrl(url = url) ?: return
        // start new browser
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