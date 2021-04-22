package tv.superawesome.sdk.publisher.common.ui.banner

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import tv.superawesome.sdk.publisher.common.models.AdRequest




@SuppressLint("SetJavaScriptEnabled")
class WebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {
    interface Listener {
        fun webViewOnStart()
        fun webViewOnError()
        fun webViewOnClick(url: String)
    }

    var listener: Listener? = null

    // boolean holding whether the web view has finished loading or not
    private var finishedLoading = false

    init {
        setBackgroundColor(Color.TRANSPARENT)
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
        scrollBarStyle = SCROLLBARS_OUTSIDE_OVERLAY
        isFocusableInTouchMode = false
        settings.mediaPlaybackRequiresUserGesture = false
        setWebContentsDebuggingEnabled(true)
        settings.javaScriptEnabled = true
        webViewClient = object : WebViewClient() {

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                listener?.webViewOnError()
            }

            @Suppress("DEPRECATION")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (finishedLoading) {
                    val fullUrl = url ?: return false
                    if (fullUrl.contains("sa-beta-ads-uploads-superawesome.netdna-ssl.com") &&
                        fullUrl.contains("/iframes")
                    ) {
                        return false
                    }
                    listener?.webViewOnClick(fullUrl)
                    return true
                } else {
                    return false
                }
            }

            @Suppress("DEPRECATION")
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                if (shouldOverrideUrlLoading(view, url)) {
                    view?.stopLoading()
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                finishedLoading = true
                listener?.webViewOnStart()
            }
        }
    }

    override fun destroy() {
        super.destroy()
        listener = null
        Log.i("WebView", "WebView destroy()")
    }

    fun loadHTML(base: String, html: String) {
        val baseHtml =
            "<html><header><meta name='viewport' content='width=device-width'/><style>html, body, div { margin: 0px; padding: 0px; } html, body { width: 100%; height: 100%; }</style></header><body>$html</body></html>"
        loadDataWithBaseURL(base, baseHtml, "text/html", "UTF-8", null)
    }

    /***
     *
     * This function uses a js script to load the advert
     *
     * @param placementId
     * @param adRequest
     */
    fun loadAdViaJs(placementId: Int, adRequest: AdRequest) {
        val queryParams = adRequest.propertyString.map { "&${it.key}=${it.value}" }.joinToString(separator = "")
        val html = """
        <html>
          <header>
           <meta name='viewport' content='width=device-width'/>
           <style>html, body, div { margin: 0px; padding: 0px; } html, body { width: 100%; height: 100%; }</style>
          </header>
          <body>
            <script type="text/javascript"
            src="https://ads.superawesome.tv/v2/ad.js?placement=$placementId$queryParams">
            </script>
          </body>
        </html>
        """
        loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
    }
}
