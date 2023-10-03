@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.ui.interstitial

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import org.koin.android.ext.android.inject
import tv.superawesome.sdk.publisher.ad.AdConfig
import tv.superawesome.sdk.publisher.models.CloseButtonState
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.models.SAEvent
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.ui.banner.InternalBannerView
import tv.superawesome.sdk.publisher.ad.AdManager
import tv.superawesome.sdk.publisher.ui.fullscreen.FullScreenActivity

/**
 * Class that abstracts away the process of loading & displaying an
 * interstitial / fullscreen type Ad.
 * A subclass of the Android "Activity" class.
 */
public class InterstitialActivity : FullScreenActivity(), SAInterface {
    private lateinit var interstitialBanner: InternalBannerView

    private val adManager by inject<AdManager>()

    override fun initChildUI() {
        interstitialBanner = InternalBannerView(this)
        interstitialBanner.id = numberGenerator.nextIntForCache()
        interstitialBanner.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        interstitialBanner.setColor(false)
        interstitialBanner.setTestMode(adManager.adConfig.testEnabled)
        interstitialBanner.setBumperPage(adManager.adConfig.isBumperPageEnabled)
        interstitialBanner.setParentalGate(adManager.adConfig.isParentalGateEnabled)
        interstitialBanner.setListener(this)
        interstitialBanner.contentDescription = "Ad content"

        parentLayout.addView(interstitialBanner)

        closeButton.visibility =
            if (adConfig.closeButtonState == CloseButtonState.VisibleImmediately) View.VISIBLE else View.GONE
    }

    override fun playContent() {
        interstitialBanner.configure(placementId, adManager.listener) {
            closeButton.visibility = View.VISIBLE
        }
        interstitialBanner.play()
    }

    override fun close() {
        interstitialBanner.close()
        super.close()
    }

    override fun onEvent(placementId: Int, event: SAEvent) {
        adManager.listener?.onEvent(placementId, event)
        if (event == SAEvent.adFailedToShow) {
            close()
        }
    }

    companion object {
        /**
         * Starts the activity.
         *
         * @param placementId ad placement identifier.
         * @param context an activity context.
         */
        fun start(placementId: Int, adConfig: AdConfig, context: Context) {
            val intent = Intent(context, InterstitialActivity::class.java)
            intent.putExtra(Constants.Keys.placementId, placementId)
            intent.putExtra(Constants.Keys.config, adConfig)
            context.startActivity(intent)
        }
    }
}
