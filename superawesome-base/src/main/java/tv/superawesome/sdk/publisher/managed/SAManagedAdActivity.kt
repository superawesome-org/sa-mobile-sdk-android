package tv.superawesome.sdk.publisher.managed

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import tv.superawesome.lib.saclosewarning.SACloseWarning
import tv.superawesome.lib.saevents.SAEvents
import tv.superawesome.lib.samodelspace.saad.SAAd
import tv.superawesome.lib.sautils.SAImageUtils
import tv.superawesome.lib.sautils.SAUtils
import tv.superawesome.lib.sautils.SAViewableDetector
import tv.superawesome.lib.sautils.videoMaxTickCount
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterface
import tv.superawesome.sdk.publisher.SAVideoAd
import tv.superawesome.sdk.publisher.SAVideoClick
import tv.superawesome.sdk.publisher.state.CloseButtonState
import java.lang.ref.WeakReference

class SAManagedAdActivity : Activity(),
    AdViewJavaScriptBridge.Listener,
    SACustomWebView.Listener
{
    private var listener: SAInterface? = null
    private var config: ManagedAdConfig? = null
    private var timeOutRunnable: Runnable? = null
    private var shownRunnable: Runnable? = null
    private var timeOutHandler = Handler(Looper.getMainLooper())
    private var shownHandler = Handler(Looper.getMainLooper())
    private var videoClick: SAVideoClick? = null
    private var completed: Boolean = false
    private lateinit var events: SAEvents
    private lateinit var viewableDetector: SAViewableDetector

    private val placementId by lazy {
        intent.getIntExtra(PLACEMENT_ID_KEY, 0)
    }

    private val html by lazy {
        intent.getStringExtra(HTML_KEY) ?: ""
    }

    private val adView by lazy {
        SAManagedAdView(this).apply {
            contentDescription = "Ad content"
            listener = this@SAManagedAdActivity
        }
    }

    private val closeButton by lazy {
        val size = (SAUtils.getScaleFactor(this) * 30).toInt()

        val buttonLayout = RelativeLayout.LayoutParams(size, size)
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP)

        val closeButton = ImageButton(this)
        closeButton.visibility =
            if (config?.closeButtonState == CloseButtonState.VisibleImmediately)
                View.VISIBLE else View.GONE
        closeButton.setImageBitmap(SAImageUtils.createCloseButtonBitmap())
        closeButton.setBackgroundColor(Color.TRANSPARENT)
        closeButton.setPadding(0, 0, 0, 0)
        closeButton.scaleType = ImageView.ScaleType.FIT_XY
        closeButton.layoutParams = buttonLayout
        closeButton.setOnClickListener {
            onCloseAction()
        }
        closeButton.contentDescription = "Close"

        return@lazy closeButton
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        events = SAVideoAd.getEvents()

        // get values from the intent
        config = intent.getParcelableExtra(CONFIG_KEY)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        viewableDetector = SAViewableDetector()
        setContentView(adView)
        adView.load(placementId, html, this)
        listener = SAVideoAd.getListener()

        adView.addView(closeButton)
        setUpCloseButtonTimeoutRunnable()

        val ad: SAAd = intent.getParcelableExtra(AD_KEY) ?: return

        videoClick = SAVideoClick(
            ad,
            config?.isParentalGateEnabled ?: false,
            config?.isBumperPageEnabled ?: false,
            events,
        )
    }

    override fun onStop() {
        super.onStop()
        cancelCloseButtonTimeoutRunnable()
        cancelCloseButtonShownRunnable()
    }

    private fun setUpCloseButtonTimeoutRunnable() {
        cancelCloseButtonTimeoutRunnable()
        val weak = WeakReference(this)
        timeOutRunnable = Runnable {
            val weakThis = weak.get() ?: return@Runnable
            weakThis.hasBeenVisibleForRequiredTimeoutTime()
        }
        timeOutRunnable?.let { timeOutHandler.postDelayed(it, CLOSE_BUTTON_TIMEOUT_TIME_INTERVAL) }
    }

    private fun setUpCloseButtonShownRunnable() {
        cancelCloseButtonShownRunnable()
        val weak = WeakReference(this)
        shownRunnable = Runnable {
            val weakThis = weak.get() ?: return@Runnable
            weakThis.hasBeenVisibleForRequiredTime()
        }
        shownRunnable?.let { shownHandler.postDelayed(it, CLOSE_BUTTON_SHOWN_TIME_INTERVAL) }
    }

    private fun cancelCloseButtonTimeoutRunnable() {
        timeOutRunnable?.let { timeOutHandler.removeCallbacks(it) }
        timeOutRunnable = null
    }

    private fun cancelCloseButtonShownRunnable() {
        timeOutRunnable?.let { shownHandler.removeCallbacks(it) }
        timeOutRunnable = null
    }

    // AdViewJavaScriptBridge.Listener

    override fun adLoaded() {
        listener?.onEvent(this.placementId, SAEvent.adLoaded)
    }

    override fun adEmpty() {
        listener?.onEvent(this.placementId, SAEvent.adEmpty)
        close()
    }

    override fun adFailedToLoad() {
        listener?.onEvent(this.placementId, SAEvent.adFailedToLoad)
        close()
    }

    override fun adAlreadyLoaded() {
        listener?.onEvent(this.placementId, SAEvent.adAlreadyLoaded)
    }

    override fun adShown() {
        cancelCloseButtonTimeoutRunnable()
        if (config?.closeButtonState == CloseButtonState.VisibleWithDelay) {
            setUpCloseButtonShownRunnable()
        }
        listener?.onEvent(this.placementId, SAEvent.adShown)
    }

    override fun adFailedToShow() {
        listener?.onEvent(this.placementId, SAEvent.adFailedToShow)
        close()
    }

    override fun adClicked() {
        listener?.onEvent(this.placementId, SAEvent.adClicked)
    }

    override fun adEnded() {
        completed = true
        listener?.onEvent(this.placementId, SAEvent.adEnded)
        if (config?.autoCloseAtEnd == true) {
            close()
        }
    }

    override fun adClosed() = close()

    override fun adPlaying() {
        listener?.onEvent(this.placementId, SAEvent.adPlaying)
    }

    override fun adPaused() {
        listener?.onEvent(this.placementId, SAEvent.adPaused)
    }

    fun onCloseAction() {
        if (config?.shouldShowCloseWarning == true && !completed) {
            adView.pauseVideo()
            SACloseWarning.setListener(object : SACloseWarning.Interface {
                override fun onResumeSelected() {
                    adView.playVideo()
                }
                override fun onCloseSelected() {
                    close()
                }
            })
            SACloseWarning.show(this)
        } else {
            close()
        }
    }

    private fun close() {
        if (!isFinishing) {
            listener?.onEvent(this.placementId, SAEvent.adClosed)
            finish()
        }
    }

    private fun hasBeenVisibleForRequiredTime() {
        closeButton.visibility =
            if (config?.closeButtonState?.isVisible() == true) View.VISIBLE else closeButton.visibility
    }

    private fun hasBeenVisibleForRequiredTimeoutTime() {
        closeButton.visibility = View.VISIBLE
    }

    /**
     * Overridden "onBackPressed" method of the activity
     * Depending on how the ad is customised, this will lock the back button or it will allow it.
     * If it allows it, it's going to also send an "adClosed" event back to the SDK user
     */
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (config?.isBackButtonEnabled == true) {
            close()
        }
    }

    // CustomWebView Listener

    override fun webViewOnStart(view: SACustomWebView) {
        viewableDetector.cancel()
        events.triggerImpressionEvent()

        viewableDetector.start(view, videoMaxTickCount) {
            events.triggerViewableImpressionEvent()
        }
    }

    override fun webViewOnError() = adFailedToShow()

    override fun webViewOnClick(view: SACustomWebView, url: String) {
        videoClick?.handleAdClick(view, url)
    }

    companion object {
        private const val PLACEMENT_ID_KEY = "PLACEMENT_ID"
        private const val HTML_KEY = "HTML"
        private const val AD_KEY = "AD"
        const val CONFIG_KEY = "CONFIG"

        private const val CLOSE_BUTTON_SHOWN_TIME_INTERVAL = 2000L
        private const val CLOSE_BUTTON_TIMEOUT_TIME_INTERVAL = 12000L

        @JvmStatic
        fun newInstance(context: Context, placementId: Int, ad: SAAd, html: String): Intent =
            Intent(context, SAManagedAdActivity::class.java).apply {
                putExtra(PLACEMENT_ID_KEY, placementId)
                putExtra(AD_KEY, ad)
                putExtra(HTML_KEY, html)
            }
    }
}
