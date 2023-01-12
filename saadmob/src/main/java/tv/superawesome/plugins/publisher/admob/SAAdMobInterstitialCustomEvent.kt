package tv.superawesome.plugins.publisher.admob

import android.content.Context
import android.os.Bundle
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.mediation.MediationAdRequest
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitial
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitialListener
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.Orientation.Companion.fromValue
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.ui.interstitial.SAInterstitialAd.load
import tv.superawesome.sdk.publisher.common.ui.interstitial.SAInterstitialAd.play
import tv.superawesome.sdk.publisher.common.ui.interstitial.SAInterstitialAd.setBackButton
import tv.superawesome.sdk.publisher.common.ui.interstitial.SAInterstitialAd.setBumperPage
import tv.superawesome.sdk.publisher.common.ui.interstitial.SAInterstitialAd.setListener
import tv.superawesome.sdk.publisher.common.ui.interstitial.SAInterstitialAd.setOrientation
import tv.superawesome.sdk.publisher.common.ui.interstitial.SAInterstitialAd.setParentalGate

@Deprecated(
    "Kept for backward compatibility reasons",
    ReplaceWith("SAAdMobAdapter")
)
class SAAdMobInterstitialCustomEvent : CustomEventInterstitial {
    private var context: Context? = null
    private var loadedPlacementId = 0
    override fun requestInterstitialAd(
        context: Context,
        listener: CustomEventInterstitialListener,
        s: String?,
        mediationAdRequest: MediationAdRequest,
        bundle: Bundle?
    ) {

        // save the context
        this.context = context

        // set values
        if (bundle != null) {
            setParentalGate(bundle.getBoolean(SAAdMobExtras.kKEY_PARENTAL_GATE))
            setBumperPage(bundle.getBoolean(SAAdMobExtras.kKEY_BUMPER_PAGE))
            setBackButton(bundle.getBoolean(SAAdMobExtras.kKEY_BACK_BUTTON))
            setOrientation(
                fromValue(bundle.getInt(SAAdMobExtras.kKEY_ORIENTATION))
                    ?: Constants.defaultOrientation
            )
        }
        setListener(SAInterface { placementId: Int, event: SAEvent? ->
            when (event) {
                SAEvent.AdLoaded -> {
                    loadedPlacementId = placementId
                    listener.onAdLoaded()
                }
                SAEvent.AdEmpty, SAEvent.AdFailedToLoad -> listener.onAdFailedToLoad(
                    AdError(
                        AdRequest.ERROR_CODE_NO_FILL,
                        "",
                        ""
                    )
                )
                SAEvent.AdAlreadyLoaded, SAEvent.AdEnded -> {}
                SAEvent.AdShown -> listener.onAdOpened()
                SAEvent.AdFailedToShow -> listener.onAdFailedToLoad(
                    AdError(
                        AdRequest.ERROR_CODE_INTERNAL_ERROR,
                        "",
                        ""
                    )
                )
                SAEvent.AdClicked -> {
                    listener.onAdClicked()
                    listener.onAdLeftApplication()
                }
                SAEvent.AdClosed -> listener.onAdClosed()
            }
        })
        try {
            val placementId = s!!.toInt()
            load(placementId, context)
        } catch (e: NumberFormatException) {
            listener.onAdFailedToLoad(AdError(AdRequest.ERROR_CODE_INVALID_REQUEST, "", ""))
        }
    }

    override fun showInterstitial() {
        play(loadedPlacementId, context!!)
    }

    override fun onDestroy() {
        // do nothing
    }

    override fun onPause() {
        // do nothing
    }

    override fun onResume() {
        // do nothing
    }
}