package tv.superawesome.sdk.publisher.ui.interstitial

import android.app.Activity
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import org.koin.core.inject
import tv.superawesome.sdk.publisher.common.components.ImageProviderType
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.components.NumberGeneratorType
import tv.superawesome.sdk.publisher.common.di.Injectable
import tv.superawesome.sdk.publisher.common.extensions.toPx
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.ui.banner.BannerView
import tv.superawesome.sdk.publisher.ui.common.AdControllerType

class InterstitialActivity : Activity(), Injectable {
    private val controller: AdControllerType by inject()
    private val imageProvider: ImageProviderType by inject()
    private val logger: Logger by inject()
    private val numberGenerator: NumberGeneratorType by inject()

    private var backButtonEnabled = Constants.defaultBackButtonEnabled
    private lateinit var interstitialBanner: BannerView
    private lateinit var closeButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // create the parent relative layout
        val parent = RelativeLayout(this)
        parent.id = numberGenerator.nextIntForCache()
        parent.layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT)

        // create the interstitial banner
        interstitialBanner = BannerView(this)
        interstitialBanner.id = numberGenerator.nextIntForCache()
        interstitialBanner.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        interstitialBanner.setColor(false)
        interstitialBanner.setTestMode(SAInterstitialAd.isTestEnabled())
        interstitialBanner.setBumperPage(SAInterstitialAd.isBumperPageEnabled())
        interstitialBanner.setParentalGate(SAInterstitialAd.isParentalGateEnabled())
        if (!SAInterstitialAd.isMoatLimiting()) {
            interstitialBanner.disableMoatLimiting()
        }

        val adResponse = SAInterstitialAd.getAdResponse()
        if (adResponse == null) {
            close()
            return
        }

        interstitialBanner.configure(adResponse, SAInterstitialAd.getDelegate()) {
            closeButton.visibility = View.VISIBLE
        }

        // close button
        closeButton = ImageButton(this)
        closeButton.visibility = View.GONE
        closeButton.setImageBitmap(imageProvider.closeImage())
        closeButton.setBackgroundColor(Color.TRANSPARENT)
        closeButton.setPadding(0, 0, 0, 0)
        closeButton.scaleType = ImageView.ScaleType.FIT_XY

        val buttonLayout = RelativeLayout.LayoutParams(30.toPx, 30.toPx)
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        closeButton.layoutParams = buttonLayout
        closeButton.setOnClickListener { close() }

        // set the view hierarchy
        parent.addView(interstitialBanner)
        parent.addView(closeButton)
        setContentView(parent)

        // finally play!
        interstitialBanner.play()
    }

    override fun onBackPressed() {
        if (backButtonEnabled) {
            close()
            super.onBackPressed()
        }
    }

    private fun close() {
        interstitialBanner.close()
        this.finish()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}