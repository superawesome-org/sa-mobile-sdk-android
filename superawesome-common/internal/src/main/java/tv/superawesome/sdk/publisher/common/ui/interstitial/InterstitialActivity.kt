@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.ui.interstitial

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import org.koin.android.ext.android.inject
import tv.superawesome.sdk.publisher.common.models.CloseButtonState
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.openmeasurement.FriendlyObstructionType
import tv.superawesome.sdk.publisher.common.ui.banner.InternalBannerView
import tv.superawesome.sdk.publisher.common.ui.common.AdControllerType
import tv.superawesome.sdk.publisher.common.ui.fullscreen.FullScreenActivity

/**
 * Class that abstracts away the process of loading & displaying an
 * interstitial / fullscreen type Ad.
 * A subclass of the Android "Activity" class.
 */
public class InterstitialActivity : FullScreenActivity(), SAInterface {
    private lateinit var interstitialBanner: InternalBannerView

    private val adController by inject<AdControllerType>()

    override fun initChildUI() {
        interstitialBanner = InternalBannerView(this)
        interstitialBanner.id = numberGenerator.nextIntForCache()
        interstitialBanner.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        interstitialBanner.setColor(false)
        interstitialBanner.setTestMode(adController.config.testEnabled)
        interstitialBanner.setBumperPage(adController.config.isBumperPageEnabled)
        interstitialBanner.setParentalGate(adController.config.isParentalGateEnabled)
        interstitialBanner.setListener(this)
        interstitialBanner.contentDescription = "Ad content"

        parentLayout.addView(interstitialBanner)

        closeButton.visibility =
            if (config.closeButtonState == CloseButtonState.VisibleImmediately) View.VISIBLE else View.GONE
    }

    override fun playContent() {
        interstitialBanner.configure(placementId, adController.delegate) {
            closeButton.visibility = View.VISIBLE
        }
        interstitialBanner.play()
    }

    override fun close() {
        interstitialBanner.close()
        super.close()
    }

    override fun onEvent(placementId: Int, event: SAEvent) {
        adController.delegate?.onEvent(placementId, event)
        when (event) {
            SAEvent.adShown ->
                interstitialBanner
                    .omSessionManager
                    .addFriendlyObstruction(closeButton, FriendlyObstructionType.CloseAd, "CloseButton")
            SAEvent.adFailedToShow -> close()
            else -> {}
        }
    }

    companion object {
        /**
         * Starts the activity.
         *
         * @param placementId ad placement identifier.
         * @param context an activity context.
         */
        fun start(placementId: Int, context: Context) {
            val intent = Intent(context, InterstitialActivity::class.java)
            intent.putExtra(Constants.Keys.placementId, placementId)
            context.startActivity(intent)
        }
    }
}
