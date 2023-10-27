@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.ui.interstitial

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.ViewGroup
import tv.superawesome.sdk.publisher.ad.InterstitialAdConfig
import tv.superawesome.sdk.publisher.models.CloseButtonState
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.ui.banner.InternalBannerView
import tv.superawesome.sdk.publisher.ui.fullscreen.FullScreenActivity

/**
 * Class that abstracts away the process of loading & displaying an
 * interstitial / fullscreen type Ad.
 * A subclass of the Android "Activity" class.
 */
public class InterstitialActivity : FullScreenActivity() {
    private lateinit var interstitialBanner: InternalBannerView

    override val adConfig: InterstitialAdConfig by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.Keys.config, InterstitialAdConfig::class.java)
        } else {
            intent.getParcelableExtra(Constants.Keys.config)
        } ?: InterstitialAdConfig()
    }

    override fun initChildUI() {
        interstitialBanner = InternalBannerView(this)
        interstitialBanner.id = View.generateViewId()
        interstitialBanner.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        interstitialBanner.contentDescription = "Ad content"

        parentLayout.addView(interstitialBanner)

        closeButton.visibility =
            if (adConfig.closeButtonState == CloseButtonState.VisibleImmediately) View.VISIBLE else View.GONE
    }

    override fun playContent() {
        interstitialBanner.configure(placementId) {
            closeButton.visibility = View.VISIBLE
        }
        interstitialBanner.play()
    }

    override fun close() {
        interstitialBanner.close()
        super.close()
    }

    companion object {
        /**
         * Starts the activity.
         *
         * @param placementId ad placement identifier.
         * @param adConfig the ad configuration.
         * @param context an activity context.
         */
        fun start(placementId: Int, adConfig: InterstitialAdConfig, context: Context) {
            val intent = Intent(context, InterstitialActivity::class.java)
            intent.putExtra(Constants.Keys.placementId, placementId)
            intent.putExtra(Constants.Keys.config, adConfig)
            context.startActivity(intent)
        }
    }
}
