package tv.superawesome.sdk.publisher.ui.banner

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import org.koin.core.inject
import tv.superawesome.sdk.publisher.common.di.Injectable
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.ui.common.AdControllerType

private val BANNER_BACKGROUND = Color.rgb(224, 224, 224)

public class BannerView : FrameLayout, Injectable {
    val controller: AdControllerType by inject()

    private var webView: WebView? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    /**
     * One of the main public methods of the SABannerAd class. This will load a new SAAd object
     * corresponding to a given placement Id.
     *
     * @param placementId Awesome Ads ID for ad data to be loaded
     */
    public fun load(placementId: Int) {
        Log.i("gunhan", "BannerView.load(${placementId})")
        controller.load(placementId, makeAdRequest())
    }

    /**
     * One of the main public methods of the SABannerAd class. This will play an already existing
     * loaded ad, or fail.
     *
     * @param context current context (activity or fragment)
     */
    public fun play(context: Context) {
        addWebView()
    }

    private fun addWebView() {
        removeWebView()

        webView = WebView(context)
        webView?.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
    }

    private fun removeWebView() {
        if (webView != null) {
            removeView(webView)
        }
        webView = null
    }

    private fun makeAdRequest(): AdRequest {
        return AdRequest(test = controller.testEnabled,
                pos = AdRequest.Position.AboveTheFold.value,
                skip = AdRequest.Skip.No.value,
                playbackmethod = AdRequest.PlaybackSoundOnScreen,
                startdelay = AdRequest.StartDelay.PreRoll.value,
                instl = AdRequest.FullScreen.Off.value,
                w = width,
                h = height)
    }
}