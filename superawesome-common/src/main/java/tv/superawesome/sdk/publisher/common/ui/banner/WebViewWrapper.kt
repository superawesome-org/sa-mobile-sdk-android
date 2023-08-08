package tv.superawesome.sdk.publisher.common.ui.banner

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.RelativeLayout

/**
 * This Class is used to wrap around the web view so that additional Friendly Obstruction
 * views can be added so that Open Measurement (OM) can see them in the view hierarchy,
 * otherwise they are not seen and sent in the OM events.
 */
internal class WebViewWrapper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr) {

    var webView: CustomWebView = CustomWebView(context)

    init {
        setBackgroundColor(Color.TRANSPARENT)
        setup()
    }

    fun setup() {
        webView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )

        addView(webView)
    }

    fun setListener(listener: CustomWebView.Listener) {
        webView.listener = listener
    }

    fun destroy() {
        webView.let {
            it.removeAllViews()
            it.destroy()
        }
    }
}
