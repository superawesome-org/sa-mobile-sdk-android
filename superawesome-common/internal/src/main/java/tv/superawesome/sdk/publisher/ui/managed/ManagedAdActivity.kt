@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.ui.managed

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import tv.superawesome.sdk.publisher.models.CloseButtonState
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.models.SAEvent
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.network.Environment
import tv.superawesome.sdk.publisher.ad.AdConfig
import tv.superawesome.sdk.publisher.ad.AdManager
import tv.superawesome.sdk.publisher.ad.AdController
import tv.superawesome.sdk.publisher.ui.common.SingleShotViewableDetector
import tv.superawesome.sdk.publisher.ui.common.ViewableDetector
import tv.superawesome.sdk.publisher.ui.dialog.CloseWarningDialog
import tv.superawesome.sdk.publisher.ui.fullscreen.FullScreenActivity
import tv.superawesome.sdk.publisher.ui.video.player.VideoPlayerListener
import java.lang.ref.WeakReference

@Suppress("TooManyFunctions")
class ManagedAdActivity :
    FullScreenActivity(),
    AdViewJavaScriptBridge.Listener,
    VideoPlayerListener {

    private var listener: SAInterface? = null
    private var timeOutRunnable: Runnable? = null
    private var timeOutHandler = Handler(Looper.getMainLooper())
    private var completed: Boolean = false

    private val adManager: AdManager by inject()
    private val environment: Environment by inject()
    private val viewableDetector: ViewableDetector by inject(named<SingleShotViewableDetector>())
    private val controller: AdController by inject {
        parametersOf(placementId)
    }

    private val html by lazy {
        intent.getStringExtra(Constants.Keys.html) ?: ""
    }

    private val baseUrl by lazy {
        intent.getStringExtra(Constants.Keys.baseUrl) ?: environment.baseUrl
    }

    private lateinit var adView: ManagedAdView

    override fun initChildUI() {
        adView = ManagedAdView(this)
        controller.videoPlayerListener = this
        adView.id = numberGenerator.nextIntForCache()
        adView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        parentLayout.addView(adView)

        when (adConfig.closeButtonState) {
            CloseButtonState.VisibleImmediately -> showCloseButton()
            CloseButtonState.VisibleWithDelay -> setUpCloseButtonTimeoutRunnable()
            CloseButtonState.Hidden -> Unit // Hidden by default
        }
    }

    override fun onRestart() {
        super.onRestart()
        adView.playVideo()
    }

    override fun onStop() {
        super.onStop()
        cancelCloseButtonTimeoutRunnable()
    }

    private fun setUpCloseButtonTimeoutRunnable() {
        cancelCloseButtonTimeoutRunnable()
        val weak = WeakReference(this)
        timeOutRunnable = Runnable { weak.get()?.showCloseButton() }
        timeOutRunnable?.let { timeOutHandler.postDelayed(it, CLOSE_BUTTON_TIMEOUT_TIME_INTERVAL) }
    }

    private fun cancelCloseButtonTimeoutRunnable() {
        timeOutRunnable?.let { timeOutHandler.removeCallbacks(it) }
        timeOutRunnable = null
    }

    public override fun playContent() {
        listener = adManager.listener
        adView.configure(placementId, listener)
        adView.load(placementId, html, baseUrl, this)
    }

    public override fun onCloseButtonPressed() {
        lifecycleScope.launch {
            controller.trackCloseButtonPressed()
        }
    }

    public override fun close() {
        adManager.removeController(placementId)
        if (adConfig.shouldShowCloseWarning && !completed) {
            adView.pauseVideo()
            CloseWarningDialog.setListener(object : CloseWarningDialog.Interface {
                override fun onResumeSelected() {
                    adView.playVideo()
                }

                override fun onCloseSelected() {
                    closeActivity()
                }
            })
            CloseWarningDialog.show(this)
        } else {
            closeActivity()
        }
    }

    private fun closeActivity() {
        lifecycleScope.launch { controller.trackDwellTime() }
        if (!isFinishing) {
            listener?.onEvent(this.placementId, SAEvent.adClosed)
        }
        super.close()
        viewableDetector.cancel()
        adView.close()
    }

    private fun showCloseButton() {
        closeButton.visibility = View.VISIBLE
        controller.startTimerForCloseButtonPressed()
    }

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
        controller.startTimerForDwellTime()
        val weak = WeakReference(this)
        viewableDetector.cancel()
        viewableDetector.start(adView, ViewableDetector.VIDEO_MAX_TICK_COUNT) {
            val weakThis = weak.get()
            weakThis?.cancelCloseButtonTimeoutRunnable()
            lifecycleScope.launch {
                weakThis?.controller?.triggerViewableImpression()
            }
            if (weakThis?.adConfig?.closeButtonState == CloseButtonState.VisibleWithDelay) {
                weakThis.showCloseButton()
            }
        }
        listener?.onEvent(this.placementId, SAEvent.adShown)
    }

    override fun adFailedToShow() = runOnUiThread {
        listener?.onEvent(this.placementId, SAEvent.adFailedToShow)
        close()
    }

    override fun adClicked() = runOnUiThread {
        adView.pauseVideo()
        listener?.onEvent(this.placementId, SAEvent.adClicked)
        adPaused()
    }

    override fun adEnded() = runOnUiThread {
        completed = true
        listener?.onEvent(this.placementId, SAEvent.adEnded)
        if (adConfig.shouldCloseAtEnd) {
            close()
        } else if(adConfig.closeButtonState == CloseButtonState.Hidden) {
            showCloseButton()
        }
    }

    override fun adClosed() = runOnUiThread {
        close()
    }

    override fun adPlaying() = runOnUiThread {
        listener?.onEvent(this.placementId, SAEvent.adPlaying)
    }

    override fun adPaused() = runOnUiThread {
        listener?.onEvent(this.placementId, SAEvent.adPaused)
    }

    override fun webSDKReady() {
        lifecycleScope.launch { controller.trackRenderTime() }
        runOnUiThread {
            listener?.onEvent(placementId, SAEvent.webSDKReady)
        }
    }

    // AdControllerType.VideoPlayerListener

    override fun didRequestVideoPause() {
        adView.pauseVideo()
    }

    override fun didRequestVideoPlay() {
        adView.playVideo()
    }

    companion object {
        private const val CLOSE_BUTTON_TIMEOUT_TIME_INTERVAL = 12_000L

        @JvmStatic
        fun newInstance(
            context: Context,
            placementId: Int,
            adConfig: AdConfig,
            html: String,
            baseUrl: String?
        ): Intent = Intent(context, ManagedAdActivity::class.java).apply {
            putExtra(Constants.Keys.placementId, placementId)
            putExtra(Constants.Keys.config, adConfig)
            putExtra(Constants.Keys.html, html)
            baseUrl?.let {
                putExtra(Constants.Keys.baseUrl, it)
            }
        }
    }
}
