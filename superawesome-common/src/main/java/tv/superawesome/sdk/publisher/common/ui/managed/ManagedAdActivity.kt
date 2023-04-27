@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.ui.managed

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import org.koin.java.KoinJavaComponent
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.state.CloseButtonState
import tv.superawesome.sdk.publisher.common.ui.common.AdControllerType
import tv.superawesome.sdk.publisher.common.ui.common.Config
import tv.superawesome.sdk.publisher.common.ui.common.ViewableDetectorType
import tv.superawesome.sdk.publisher.common.ui.common.videoMaxTickCount
import tv.superawesome.sdk.publisher.common.ui.dialog.CloseWarning
import tv.superawesome.sdk.publisher.common.ui.fullscreen.FullScreenActivity
import tv.superawesome.sdk.publisher.common.ui.video.SAVideoAd
import java.lang.ref.WeakReference

public class ManagedAdActivity :
    FullScreenActivity(),
    AdViewJavaScriptBridge.Listener,
    AdControllerType.VideoPlayerListener
{
    private var listener: SAInterface? = null
    private var timeOutRunnable: Runnable? = null
    private var timeOutHandler = Handler(Looper.getMainLooper())
    private var completed: Boolean = false

    private val controller: AdControllerType by KoinJavaComponent.inject(AdControllerType::class.java)

    private val viewableDetector: ViewableDetectorType by KoinJavaComponent.inject(
        ViewableDetectorType::class.java
    )

    private val html by lazy {
        intent.getStringExtra(Constants.Keys.html) ?: ""
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

        closeButton.visibility =
            if (config.closeButtonState == CloseButtonState.VisibleImmediately)
                View.VISIBLE else View.GONE

        setUpCloseButtonTimeoutRunnable()
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
        timeOutRunnable = Runnable {
            val weakThis = weak.get() ?: return@Runnable
            weakThis.hasBeenVisibleForRequiredTimeoutTime()
        }
        timeOutRunnable?.let { timeOutHandler.postDelayed(it, CLOSE_BUTTON_TIMEOUT_TIME_INTERVAL) }
    }

    private fun cancelCloseButtonTimeoutRunnable() {
        timeOutRunnable?.let { timeOutHandler.removeCallbacks(it) }
        timeOutRunnable = null
    }

    public override fun playContent() {
        listener = SAVideoAd.getDelegate()
        adView.configure(placementId, listener)
        adView.load(placementId, html, this)
    }

    public override fun close() {
        if (config.shouldShowCloseWarning && !completed) {
            adView.pauseVideo()
            CloseWarning.setListener(object : CloseWarning.Interface {
                override fun onResumeSelected() {
                    adView.playVideo()
                }

                override fun onCloseSelected() {
                    closeActivity()
                }
            })
            CloseWarning.show(this)
        } else {
            closeActivity()
        }
    }

    private fun closeActivity() {
        if (!isFinishing) {
            listener?.onEvent(this.placementId, SAEvent.AdClosed)
        }
        super.close()
        viewableDetector.cancel()
        adView.close()
    }

    override fun adLoaded() {
        listener?.onEvent(this.placementId, SAEvent.AdLoaded)
    }

    override fun adEmpty() {
        listener?.onEvent(this.placementId, SAEvent.AdEmpty)
        close()
    }

    override fun adFailedToLoad() {
        listener?.onEvent(this.placementId, SAEvent.AdFailedToLoad)
        close()
    }

    override fun adAlreadyLoaded() {
        listener?.onEvent(this.placementId, SAEvent.AdAlreadyLoaded)
    }

    override fun adShown() {
        val weak = WeakReference(this)
        viewableDetector.cancel()
        viewableDetector.start(adView, videoMaxTickCount) {
            val weakThis = weak.get()
            weakThis?.cancelCloseButtonTimeoutRunnable()
            weakThis?.controller?.triggerViewableImpression(placementId)
            if (weakThis?.config?.closeButtonState == CloseButtonState.VisibleWithDelay)
                weakThis?.closeButton?.visibility = View.VISIBLE
        }
        listener?.onEvent(this.placementId, SAEvent.AdShown)
    }

    override fun adFailedToShow() {
        listener?.onEvent(this.placementId, SAEvent.AdFailedToShow)
        close()
    }

    override fun adClicked() {
        adView.pauseVideo()
        listener?.onEvent(this.placementId, SAEvent.AdClicked)
        adPaused()
    }

    override fun adEnded() {
        completed = true
        listener?.onEvent(this.placementId, SAEvent.AdEnded)
        close()
    }

    override fun adClosed() {
        close()
    }

    override fun adPlaying() {
        listener?.onEvent(this.placementId, SAEvent.AdPlaying)
    }

    override fun adPaused() {
        listener?.onEvent(this.placementId, SAEvent.AdPaused)
    }

    private fun hasBeenVisibleForRequiredTimeoutTime() {
        closeButton.visibility = View.VISIBLE
    }

    // AdControllerType.VideoPlayerListener

    override fun didRequestVideoPause() {
        adView.pauseVideo()
    }

    override fun didRequestVideoPlay() {
        adView.playVideo()
    }

    companion object {
        private const val CLOSE_BUTTON_TIMEOUT_TIME_INTERVAL = 12000L

        @JvmStatic
        fun newInstance(context: Context, placementId: Int, config: Config, html: String): Intent =
            Intent(context, ManagedAdActivity::class.java).apply {
                putExtra(Constants.Keys.placementId, placementId)
                putExtra(Constants.Keys.config, config)
                putExtra(Constants.Keys.html, html)
            }
    }
}
