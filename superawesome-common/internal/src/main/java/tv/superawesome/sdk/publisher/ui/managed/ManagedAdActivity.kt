@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.ui.managed

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import org.koin.android.ext.android.inject
import tv.superawesome.sdk.publisher.models.CloseButtonState
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.models.SAEvent
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.network.Environment
import tv.superawesome.sdk.publisher.ui.common.AdControllerType
import tv.superawesome.sdk.publisher.ui.common.Config
import tv.superawesome.sdk.publisher.ui.common.VIDEO_MAX_TICK_COUNT
import tv.superawesome.sdk.publisher.ui.common.ViewableDetectorType
import tv.superawesome.sdk.publisher.ui.dialog.CloseWarningDialog
import tv.superawesome.sdk.publisher.ui.fullscreen.FullScreenActivity
import java.lang.ref.WeakReference

@Suppress("TooManyFunctions")
class ManagedAdActivity :
    FullScreenActivity(),
    AdViewJavaScriptBridge.Listener,
    AdControllerType.VideoPlayerListener
{
    private var listener: SAInterface? = null
    private var timeOutRunnable: Runnable? = null
    private var timeOutHandler = Handler(Looper.getMainLooper())
    private var completed: Boolean = false

    private val controller: AdControllerType by inject()
    private val environment: Environment by inject()
    private val viewableDetector: ViewableDetectorType by inject()

    private val html by lazy {
        intent.getStringExtra(Constants.Keys.html) ?: ""
    }

    private val baseUrl by lazy {
        intent.getStringExtra(Constants.Keys.baseUrl) ?: environment.baseUrl
    }

    private lateinit var adView: ManagedAdView

    override fun initChildUI() {
        adView = ManagedAdView(this)
        adView.controller.videoListener = this
        adView.id = numberGenerator.nextIntForCache()
        adView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        adView.setConfig(config)
        adView.setColor(false)
        adView.setTestMode(config.testEnabled)
        adView.setBumperPage(config.isBumperPageEnabled)
        adView.setParentalGate(config.isParentalGateEnabled)

        parentLayout.addView(adView)

        when (config.closeButtonState) {
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
        listener = controller.delegate
        adView.configure(placementId, listener)
        adView.load(placementId, html, baseUrl, this)
    }

    public override fun onCloseButtonPressed() = controller.trackCloseButtonPressed()

    public override fun close() {
        if (config.shouldShowCloseWarning && !completed) {
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
        controller.trackDwellTime()
        if (!isFinishing) {
            listener?.onEvent(this.placementId, SAEvent.adClosed)
        }
        super.close()
        viewableDetector.cancel()
        adView.close()
    }

    private fun showCloseButton() {
        closeButton.visibility = View.VISIBLE
        controller.startTimingForCloseButtonPressed()
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
        controller.startTimingForDwellTime()
        val weak = WeakReference(this)
        viewableDetector.cancel()
        viewableDetector.start(adView, VIDEO_MAX_TICK_COUNT) {
            val weakThis = weak.get()
            weakThis?.cancelCloseButtonTimeoutRunnable()
            weakThis?.controller?.triggerViewableImpression(placementId)
            if (weakThis?.config?.closeButtonState == CloseButtonState.VisibleWithDelay) {
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
        if (config.shouldCloseAtEnd) {
            close()
        } else if(config.closeButtonState == CloseButtonState.Hidden) {
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

    override fun webSDKReady() = runOnUiThread {
        listener?.onEvent(placementId, SAEvent.webSDKReady)
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
            config: Config,
            html: String,
            baseUrl: String?): Intent =
            Intent(context, ManagedAdActivity::class.java).apply {
                putExtra(Constants.Keys.placementId, placementId)
                putExtra(Constants.Keys.config, config)
                putExtra(Constants.Keys.html, html)
                baseUrl?.let {
                    putExtra(Constants.Keys.baseUrl, it)
                }
            }
    }
}
