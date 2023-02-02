@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.ui.managed

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.ui.common.Config
import tv.superawesome.sdk.publisher.common.ui.fullscreen.FullScreenActivity
import tv.superawesome.sdk.publisher.common.ui.video.SAVideoAd

public class ManagedAdActivity : FullScreenActivity(), AdViewJavaScriptBridge.Listener {
    private var listener: SAInterface? = null

    private val html by lazy {
        intent.getStringExtra(Constants.Keys.html) ?: ""
    }

    private lateinit var adView: ManagedAdView

    override fun initChildUI() {
        adView = ManagedAdView(this)
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
    }

    public override fun playContent() {
        listener = SAVideoAd.getDelegate()
        adView.configure(placementId, listener) {
            closeButton.visibility = View.VISIBLE
        }
        adView.load(placementId, html, this)
    }

    public override fun close() {
        adView.close()
        super.close()
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
        listener?.onEvent(this.placementId, SAEvent.AdShown)
    }

    override fun adFailedToShow() {
        listener?.onEvent(this.placementId, SAEvent.AdFailedToShow)
        close()
    }

    override fun adClicked() {
        listener?.onEvent(this.placementId, SAEvent.AdClicked)
    }

    override fun adEnded() {
        listener?.onEvent(this.placementId, SAEvent.AdEnded)
        close()
    }

    override fun adClosed() {
        listener?.onEvent(this.placementId, SAEvent.AdClosed)
        close()
    }

    companion object {
        @JvmStatic
        fun newInstance(context: Context, placementId: Int, config: Config, html: String): Intent =
            Intent(context, ManagedAdActivity::class.java).apply {
                putExtra(Constants.Keys.placementId, placementId)
                putExtra(Constants.Keys.config, config)
                putExtra(Constants.Keys.html, html)
            }
    }
}
