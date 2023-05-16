package tv.superawesome.plugins.publisher.admob

import android.content.Context
import android.os.Bundle
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.mediation.MediationAdRequest
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitial
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitialListener
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterface
import tv.superawesome.sdk.publisher.SAInterstitialAd.*
import tv.superawesome.sdk.publisher.SAOrientation

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
        placement: String?,
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
            setOrientation(SAOrientation.fromValue(bundle.getInt(SAAdMobExtras.kKEY_ORIENTATION)))
        }
        setListener(SAInterface { placementId: Int, event: SAEvent? ->
            when (event) {
                SAEvent.adLoaded -> {
                    loadedPlacementId = placementId
                    listener.onAdLoaded()
                }
                SAEvent.adEmpty, SAEvent.adFailedToLoad -> listener.onAdFailedToLoad(
                    AdError(
                        AdRequest.ERROR_CODE_NO_FILL,
                        "",
                        ""
                    )
                )
                SAEvent.adShown -> listener.onAdOpened()
                SAEvent.adFailedToShow -> listener.onAdFailedToLoad(
                    AdError(
                        AdRequest.ERROR_CODE_INTERNAL_ERROR,
                        "",
                        ""
                    )
                )
                SAEvent.adClicked -> {
                    listener.onAdClicked()
                    listener.onAdLeftApplication()
                }
                SAEvent.adClosed -> listener.onAdClosed()
                null -> return@SAInterface
                else -> Unit // Unused
            }
        })
        try {
            val placementId = placement!!.toInt()
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
