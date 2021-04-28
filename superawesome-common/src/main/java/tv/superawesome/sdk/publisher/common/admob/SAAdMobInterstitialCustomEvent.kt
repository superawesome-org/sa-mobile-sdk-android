package tv.superawesome.sdk.publisher.common.admob

import android.content.Context
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.mediation.MediationAdRequest
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitial
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitialListener
import tv.superawesome.sdk.publisher.common.models.Orientation
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.load
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.play
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.setBackButton
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.setBumperPage
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.setListener
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.setOrientation
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.setParentalGate
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.setTestMode

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
            setTestMode(bundle.getBoolean(SAAdMobExtras.kKEY_TEST))
            setParentalGate(bundle.getBoolean(SAAdMobExtras.kKEY_PARENTAL_GATE))
            setBumperPage(bundle.getBoolean(SAAdMobExtras.kKEY_BUMPER_PAGE))
            setBackButton(bundle.getBoolean(SAAdMobExtras.kKEY_BACK_BUTTON))
            Orientation.fromValue(bundle.getInt(SAAdMobExtras.kKEY_ORIENTATION))
                ?.let { setOrientation(it) }
        }
        setListener(
            SAInterface { placementId, event ->
                when (event) {
                    SAEvent.AdLoaded -> {
                        loadedPlacementId = placementId
                        listener.onAdLoaded()
                    }
                    SAEvent.AdClicked -> {
                        listener.onAdClicked()
                        listener.onAdLeftApplication()
                    }
                    SAEvent.AdEmpty, SAEvent.AdFailedToLoad -> listener.onAdFailedToLoad(AdRequest.ERROR_CODE_NO_FILL)
                    SAEvent.AdShown -> listener.onAdOpened()
                    SAEvent.AdFailedToShow -> listener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR)
                    SAEvent.AdClosed -> listener.onAdClosed()
                    else -> {
                        // do nothing
                    }
                }
            }
        )
        try {
            val placementId = s?.toInt() ?: throw java.lang.NumberFormatException("string is null or not a number")
            load(placementId, context)
        } catch (e: NumberFormatException) {
            listener.onAdFailedToLoad(AdRequest.ERROR_CODE_INVALID_REQUEST)
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
