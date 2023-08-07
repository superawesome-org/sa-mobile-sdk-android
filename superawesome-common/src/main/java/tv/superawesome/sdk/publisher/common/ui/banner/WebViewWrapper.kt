package tv.superawesome.sdk.publisher.common.ui.banner

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout

internal class WebViewWrapper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    var webView: CustomWebView = CustomWebView(context, attrs, defStyleAttr)
    var holder: FrameLayout = FrameLayout(context).apply {
        clipChildren = false
        setBackgroundColor(Color.TRANSPARENT)
        clipToPadding = false
    }

    init {
        setBackgroundColor(Color.TRANSPARENT)
    }

    fun setup() {
        webView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        holder.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        holder.addView(webView)
        this.addView(holder)
    }

    fun setListener(listener: CustomWebView.Listener) {
        webView.listener = listener
    }

    fun destroy() {
        webView.let {
            holder.removeView(it)
            it.removeAllViews()
            it.destroy()
        }
    }
}
