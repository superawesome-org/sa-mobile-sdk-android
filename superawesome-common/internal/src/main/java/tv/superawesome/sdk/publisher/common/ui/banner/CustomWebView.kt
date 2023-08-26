@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.ui.banner

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

@SuppressLint("SetJavaScriptEnabled")
public class CustomWebView @JvmOverloads constructor(
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

    /* Flag to indicate error is only handled once to send single event in case multiple error occurs */
    private var errorHandled = false

    // boolean holding whether the web view has finished loading or not
    private var finishedLoading = false

    init {
        setBackgroundColor(Color.TRANSPARENT)
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
        scrollBarStyle = SCROLLBARS_OUTSIDE_OVERLAY
        isFocusableInTouchMode = false
        settings.mediaPlaybackRequiresUserGesture = false
        settings.javaScriptEnabled = true
        setWebContentsDebuggingEnabled(true)

        webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    handleError(error.errorCode)
                }
            }

            /**
             * Required to handle API version below 23(M). Could be removed if
             * we increase the minimum supported version.
             */
            @Deprecated("Deprecated in Java")
            @Suppress("DEPRECATION")
            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                handleError(errorCode)
            }

            @Deprecated("Deprecated in Java")
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
                errorHandled = false
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

    private fun handleError(errorCode: Int) {
        if (errorHandled) return
        if (errorCode == WebViewClient.ERROR_HOST_LOOKUP) {
            errorHandled = true
            listener?.webViewOnError()
        }
    }


    override fun destroy() {
        super.destroy()
        listener = null
        Log.i("WebView", "WebView destroy()")
    }

    @Suppress("MaxLineLength")
    public fun loadHTML(base: String, html: String) {
        val baseHtml =
            "<html><header><meta name='viewport' content='width=device-width'/><style>html, body, div { margin: 0px; padding: 0px; } html, body { width: 100%; height: 100%; }</style></header><body>$html</body></html>"
        loadDataWithBaseURL(base, baseHtml, "text/html", "UTF-8", null)
    }
}
