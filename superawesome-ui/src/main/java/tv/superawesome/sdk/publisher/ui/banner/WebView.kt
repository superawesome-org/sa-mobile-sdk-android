package tv.superawesome.sdk.publisher.ui.banner

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebView

@SuppressLint("SetJavaScriptEnabled")
class WebView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : WebView(context!!, attrs, defStyleAttr) {
    fun loadHTML(base: String?, html: String?) {
        val baseHtml = "<html><header><meta name='viewport' content='width=device-width'/><style>html, body, div { margin: 0px; padding: 0px; } html, body { width: 100%; height: 100%; }</style></header><body>_CONTENT_</body></html>"
        val fullHtml = baseHtml.replace("_CONTENT_", html!!)
        loadDataWithBaseURL(base, fullHtml, "text/html", "UTF-8", null)
    }

    init {
        setBackgroundColor(Color.TRANSPARENT)
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
        scrollBarStyle = SCROLLBARS_OUTSIDE_OVERLAY
        isFocusableInTouchMode = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            settings.mediaPlaybackRequiresUserGesture = false
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setWebContentsDebuggingEnabled(true)
        }
        settings.javaScriptEnabled = true
    }
}