@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.ui.interstitial

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.state.CloseButtonState
import tv.superawesome.sdk.publisher.common.ui.banner.BannerView
import tv.superawesome.sdk.publisher.common.ui.fullscreen.FullScreenActivity

/**
 * Class that abstracts away the process of loading & displaying an
 * interstitial / fullscreen type Ad.
 * A subclass of the Android "Activity" class.
 */
public class InterstitialActivity : FullScreenActivity(), SAInterface {
    private lateinit var interstitialBanner: BannerView

    override fun initChildUI() {
        interstitialBanner = BannerView(this)
        interstitialBanner.id = numberGenerator.nextIntForCache()
        interstitialBanner.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        interstitialBanner.setColor(false)
        interstitialBanner.setTestMode(SAInterstitialAd.isTestEnabled())
        interstitialBanner.setBumperPage(SAInterstitialAd.isBumperPageEnabled())
        interstitialBanner.setParentalGate(SAInterstitialAd.isParentalGateEnabled())
        interstitialBanner.setListener(this)

        parentLayout.addView(interstitialBanner)

        closeButton.visibility =
            if (config.closeButtonState == CloseButtonState.VisibleImmediately) View.VISIBLE else View.GONE
    }

    public override fun playContent() {
        interstitialBanner.configure(placementId, SAInterstitialAd.getDelegate()) {
            closeButton.visibility = View.VISIBLE
        }
        interstitialBanner.play()
    }

    public override fun close() {
        interstitialBanner.close()
        super.close()
    }

    override fun onEvent(placementId: Int, event: SAEvent) {
        SAInterstitialAd.getDelegate()?.onEvent(placementId, event)
        if (event == SAEvent.adFailedToShow) {
            close()
        }
    }

    companion object {
        public fun start(placementId: Int, context: Context) {
            val intent = Intent(context, InterstitialActivity::class.java)
            intent.putExtra(Constants.Keys.placementId, placementId)
            context.startActivity(intent)
        }
    }
}
