/**
 * @Copyright:   SADefaults Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.publisher.unity

import android.content.Context
import tv.superawesome.sdk.publisher.common.models.Orientation
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.hasAdAvailable
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.load
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.play
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.setBackButton
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.setBumperPage
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.setListener
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.setOrientation
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.setParentalGate
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd.setTestMode

/**
 * Class that holds a number of static methods used to communicate with Unity
 */
object SAUnityInterstitialAd {
    private const val unityName = "SAInterstitialAd"

    /**
     * Method that creates a new Interstitial Ad (from Unity)
     */
    fun SuperAwesomeUnitySAInterstitialAdCreate(context: Context?) {
        setListener(object : SAInterface {
            override fun onEvent(placementId: Int, event: SAEvent) {
                when (event) {
                    SAEvent.adLoaded -> SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adLoaded.toString())
                    SAEvent.adEmpty -> SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adEmpty.toString())
                    SAEvent.adFailedToLoad -> SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adFailedToLoad.toString())
                    SAEvent.adAlreadyLoaded -> SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adAlreadyLoaded.toString())
                    SAEvent.adShown -> SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adShown.toString())
                    SAEvent.adFailedToShow -> SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adFailedToShow.toString())
                    SAEvent.adClicked -> SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adClicked.toString())
                    SAEvent.adEnded -> SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adEnded.toString())
                    SAEvent.adClosed -> SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adClosed.toString())
                }
            }
        })
    }

    /**
     * Method that loads a new Interstitial AD (from Unity)
     */
    fun SuperAwesomeUnitySAInterstitialAdLoad(context: Context?, placementId: Int, configuration: Int, test: Boolean) {
        setTestMode(test)
        load(placementId, context!!)
    }

    /**
     * Method that checks to see if an ad is available for an interstitial ad (from Unity)
     */
    fun SuperAwesomeUnitySAInterstitialAdHasAdAvailable(context: Context?, placementId: Int): Boolean {
        return hasAdAvailable(placementId)
    }

    /**
     * Method that plays a new Interstitial Ad (from Unity)
     */
    fun SuperAwesomeUnitySAInterstitialAdPlay(context: Context?, placementId: Int, isParentalGateEnabled: Boolean, isBumperPageEnabled: Boolean, orientation: Int, isBackButtonEnabled: Boolean) {
        setParentalGate(isParentalGateEnabled)
        setBumperPage(isBumperPageEnabled)
        Orientation.fromValue(orientation)?.let { setOrientation(it) }
        setBackButton(isBackButtonEnabled)
        play(placementId, context!!)
    }
}