package tv.superawesome.sdk.publisher.common.admob

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.mediation.MediationAdRequest
import com.google.android.gms.ads.mediation.customevent.CustomEventBanner
import com.google.android.gms.ads.mediation.customevent.CustomEventBannerListener
import tv.superawesome.sdk.publisher.common.components.NumberGenerator
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.ui.banner.BannerView
import kotlin.math.roundToInt

class SAAdMobBannerCustomEvent : CustomEventBanner {
    private var laidOut = false
    private var loaded = false
    private var setup = false
    private var bannerView: BannerView? = null

    override fun requestBannerAd(
        context: Context,
        listener: CustomEventBannerListener,
        s: String,
        adSize: AdSize,
        mediationAdRequest: MediationAdRequest,
        bundle: Bundle
    ) {
        bannerView = BannerView(context)
        bannerView?.apply {

            id = NumberGenerator().nextIntForCache()

            // Internally, smart banners use constants to represent their ad size, which means a call to
            // AdSize.getHeight could return a negative value. You can accommodate this by using
            // AdSize.getHeightInPixels and AdSize.getWidthInPixels instead, and then adjusting to match
            // the device's display metrics.
            val widthInPixels = adSize.getWidthInPixels(context)
            val heightInPixels = adSize.getHeightInPixels(context)
            val displayMetrics = Resources.getSystem().displayMetrics
            val widthInDp = (widthInPixels / displayMetrics.density).roundToInt()
            val heightInDp = (heightInPixels / displayMetrics.density).roundToInt()
            layoutParams = ViewGroup.LayoutParams(widthInDp, heightInDp)
            setTestMode(bundle.getBoolean(SAAdMobExtras.kKEY_TEST))
            setColor(bundle.getBoolean(SAAdMobExtras.kKEY_TRANSPARENT))
            setParentalGate(bundle.getBoolean(SAAdMobExtras.kKEY_PARENTAL_GATE))
            setBumperPage(bundle.getBoolean(SAAdMobExtras.kKEY_BUMPER_PAGE))
            setListener { _: Int, saEvent: SAEvent? ->
                when (saEvent) {
                    SAEvent.AdLoaded -> {
                        // send load event
                        listener.onAdLoaded(bannerView)
                        loaded = true
                        if (laidOut && !setup) {
                            bannerView?.play()
                            setup = true
                        }
                    }
                    SAEvent.AdEmpty, SAEvent.AdFailedToLoad -> listener.onAdFailedToLoad(AdRequest.ERROR_CODE_NO_FILL)
                    SAEvent.AdShown -> listener.onAdOpened()
                    SAEvent.AdFailedToShow -> listener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR)
                    SAEvent.AdClicked -> {
                        listener.onAdClicked()
                        listener.onAdLeftApplication()
                    }
                    SAEvent.AdClosed -> listener.onAdClosed()
                    else -> { /* do nothing */
                    }
                }
            }
            try {
                addOnLayoutChangeListener { _: View?, _: Int, _: Int, _: Int, _: Int, _: Int, _: Int, _: Int, _: Int ->
                    laidOut = true
                    if (loaded && !setup) {
                        bannerView?.play()
                    }
                }
                val placementId = s.toInt()
                load(placementId)
            } catch (e: NumberFormatException) {
                listener.onAdFailedToLoad(AdRequest.ERROR_CODE_INVALID_REQUEST)
            }
        }
    }

    override fun onDestroy() {
        bannerView?.close()
        val parent = bannerView?.parent as? ViewGroup
        parent?.removeView(bannerView)
        bannerView = null
    }

    override fun onPause() {
        // do nothing
    }

    override fun onResume() {
        // do nothing
    }
}
