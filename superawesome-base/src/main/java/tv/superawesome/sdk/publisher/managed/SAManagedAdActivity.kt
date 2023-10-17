package tv.superawesome.sdk.publisher.managed

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import tv.superawesome.lib.saclosewarning.SACloseWarning
import tv.superawesome.lib.saevents.SAEvents
import tv.superawesome.lib.sametrics.SAPerformanceMetrics
import tv.superawesome.lib.samodelspace.saad.SAAd
import tv.superawesome.lib.satiming.SAFailSafeTimer
import tv.superawesome.lib.satiming.SAFailSafeTimer.Listener
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
    private var ad: SAAd? = null
    private lateinit var events: SAEvents
    private lateinit var performanceMetrics: SAPerformanceMetrics
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
        closeButton.visibility = View.GONE
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

    private val failSafeTimer = SAFailSafeTimer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        events = SAVideoAd.getEvents()
        performanceMetrics = SAVideoAd.getPerformanceMetrics()

        // get values from the intent
        config = intent.getParcelableExtra(CONFIG_KEY)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        viewableDetector = SAViewableDetector()
        setContentView(adView)
        adView.load(placementId, html, this)

        adView.addView(closeButton)

        when (config?.closeButtonState) {
            CloseButtonState.VisibleImmediately -> showCloseButton()
            CloseButtonState.VisibleWithDelay -> Unit
            else -> Unit // Hidden by default
        }

        ad = intent.getParcelableExtra(AD_KEY)
        val ad = ad ?: return

        videoClick = SAVideoClick(
            ad,
            config?.isParentalGateEnabled ?: false,
            config?.isBumperPageEnabled ?: false,
            events,
        )

        videoClick?.apply {
            listener = object : SAVideoClick.Listener {
                override fun didRequestPlaybackPause() {
                    adView.pauseVideo()
                }

                override fun didRequestPlaybackResume() {
                    adView.playVideo()
                }
            }
        }

        failSafeTimer.listener = object: Listener {
            override fun failSafeDidTimeOut() {
                // Override the close button click behaviour when showing the close button as
                // a fail safe
                closeButton.setOnClickListener {
                    failSafeCloseAction()
                }
                showCloseButton()
            }
        }

        failSafeTimer.start()
    }

    override fun onStart() {
        super.onStart()
        listener = SAVideoAd.getListener()
        failSafeTimer.start()
    }

    override fun onRestart() {
        super.onRestart()
        adView.playVideo()
    }

    override fun onStop() {
        super.onStop()
        adView.pauseVideo()
        failSafeTimer.pause()
        listener = null
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelCloseButtonShownRunnable()
        viewableDetector.cancel()
        failSafeTimer.stop()
        config = null
        videoClick = null
    }

    private fun setUpCloseButtonShownRunnable() {
        cancelCloseButtonShownRunnable()
        val weak = WeakReference(this)
        shownRunnable = Runnable {
            val weakThis = weak.get() ?: return@Runnable
            weakThis.showCloseButton()
        }
        shownRunnable?.let { shownHandler.postDelayed(it, CLOSE_BUTTON_SHOWN_TIME_INTERVAL) }
    }

    private fun cancelCloseButtonShownRunnable() {
        timeOutRunnable?.let { shownHandler.removeCallbacks(it) }
        timeOutRunnable = null
    }

    // AdViewJavaScriptBridge.Listener

    override fun adLoaded() = runOnUiThread {
        listener?.onEvent(this.placementId, SAEvent.adLoaded)
    }

    override fun adEmpty() = runOnUiThread {
        listener?.onEvent(this.placementId, SAEvent.adEmpty)
        close()
    }

    override fun adFailedToLoad() = runOnUiThread {
        listener?.onEvent(this.placementId, SAEvent.adFailedToLoad)
        close()
    }

    override fun adAlreadyLoaded() = runOnUiThread {
        listener?.onEvent(this.placementId, SAEvent.adAlreadyLoaded)
    }

    override fun adShown() = runOnUiThread {
        performanceMetrics.startTimingForDwellTime()
        if (config?.closeButtonState == CloseButtonState.VisibleWithDelay) {
            setUpCloseButtonShownRunnable()
        }
        listener?.onEvent(this.placementId, SAEvent.adShown)
        failSafeTimer.stop()
        Log.d("VPAID FSTIMER ADSHOWN", this.placementId.toString())
    }

    override fun adFailedToShow() = runOnUiThread {
        listener?.onEvent(this.placementId, SAEvent.adFailedToShow)
        close()
    }

    override fun adClicked() = runOnUiThread {
        listener?.onEvent(this.placementId, SAEvent.adClicked)
    }

    override fun adEnded() = runOnUiThread {
        completed = true
        listener?.onEvent(this.placementId, SAEvent.adEnded)
        if (config?.autoCloseAtEnd == true) {
            close()
        } else if (config?.closeButtonState == CloseButtonState.Hidden) {
            showCloseButton()
        }
    }

    override fun adClosed() = runOnUiThread { close() }

    override fun adPlaying() = runOnUiThread {
        listener?.onEvent(this.placementId, SAEvent.adPlaying)
    }

    override fun adPaused() = runOnUiThread {
        listener?.onEvent(this.placementId, SAEvent.adPaused)
    }

    override fun webSDKReady() {
        listener?.onEvent(placementId, SAEvent.webSDKReady)
        ad?.run(performanceMetrics::trackRenderTime)
    }

    private fun showCloseButton() {
        closeButton.visibility = View.VISIBLE
        performanceMetrics.startTimingForCloseButtonPressed()
    }

    /**
     * Method that closes the ad via the fail safe timer
     */
    private fun failSafeCloseAction() {
        listener?.onEvent(placementId, SAEvent.adEnded)
        ad?.run(performanceMetrics::trackCloseButtonPressed)
        close()
    }

    private fun onCloseAction() {
        ad?.run(performanceMetrics::trackCloseButtonPressed)
        if (config?.shouldShowCloseWarning == true && !completed) {
            adView.pauseVideo()
            SACloseWarning.setListener(object : SACloseWarning.Interface {
                override fun onResumeSelected() {
                    adView.playVideo()
                }
                override fun onCloseSelected() {
                    this@SAManagedAdActivity.close()
                }
            })
            SACloseWarning.show(this)
        } else {
            close()
        }
    }

    private fun close() {
        if (!isFinishing) {
            ad?.run(performanceMetrics::trackDwellTime)
            listener?.onEvent(this.placementId, SAEvent.adClosed)
            finish()
        }
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

        @JvmStatic
        fun newInstance(context: Context, placementId: Int, ad: SAAd, html: String): Intent =
            Intent(context, SAManagedAdActivity::class.java).apply {
                putExtra(PLACEMENT_ID_KEY, placementId)
                putExtra(AD_KEY, ad)
                putExtra(HTML_KEY, html)
            }
    }
}
