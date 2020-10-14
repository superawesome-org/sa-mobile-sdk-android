package tv.superawesome.sdk.publisher.ui.interstitial

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import org.koin.core.get
import tv.superawesome.sdk.publisher.common.di.Injectable
import tv.superawesome.sdk.publisher.common.models.*
import tv.superawesome.sdk.publisher.ui.common.AdControllerType


object SAInterstitialAd : Injectable {
    internal var orientation: Orientation = Constants.defaultOrientation
    internal var backButtonEnabled: Boolean = Constants.defaultBackButtonEnabled

    private val controller: AdControllerType by lazy { get() }

    /**
     * Static method that loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId   the Ad placement id to load data for
     * @param context       the current context
     */
    fun load(placementId: Int, context: Context) {
        controller.load(placementId, makeAdRequest(context))
    }

    /**
     * Static method that, if an ad data is loaded, will play the content for the user
     *
     * @param placementId   the Ad placement id to play an ad for
     * @param context       the current context (activity or fragment)
     */
    fun play(placementId: Int, context: Context) {
        val intent = Intent(context, InterstitialActivity::class.java)
        context.startActivity(intent)
    }

    fun setListener(value: SAInterface) {
        controller.delegate = value
    }

    fun enableParentalGate() {
        setParentalGate(true)
    }

    fun disableParentalGate() {
        setParentalGate(false)
    }

    fun enableBumperPage() {
        setBumperPage(true)
    }

    fun disableBumperPage() {
        setBumperPage(false)
    }

    fun enableTestMode() {
        setTestMode(true)
    }

    fun disableTestMode() {
        setTestMode(false)
    }

    fun enableBackButton() {
        setBackButton(true)
    }

    fun disableBackButton() {
        setBackButton(false)
    }

    fun setConfigurationProduction() {
    }

    fun setConfigurationStaging() {
    }

    fun setOrientationAny() {
        setOrientation(Orientation.any)
    }

    fun setOrientationPortrait() {
        setOrientation(Orientation.portrait)
    }

    fun setOrientationLandscape() {
        setOrientation(Orientation.landscape)
    }

    fun getIsBumperPageEnabled(): Boolean = controller.bumperPageEnabled

    fun setParentalGate(value: Boolean) {
        controller.parentalGateEnabled = value
    }

    fun setBumperPage(value: Boolean) {
        controller.bumperPageEnabled = value
    }

    fun setTestMode(value: Boolean) {
        controller.testEnabled = value
    }

    fun setBackButton(value: Boolean) {
        backButtonEnabled = value
    }

    fun setOrientation(value: Orientation) {
        orientation = value
    }

    fun disableMoatLimiting() {
        controller.moatLimiting = false
    }

    /**
     * Static method that returns whether ad data for a certain placement has already been loaded
     *
     * @param placementId   the Ad placement id to check for
     * @return              true or false
     */
    fun hasAdAvailable(placementId: Int): Boolean = controller.adAvailable

    private fun makeAdRequest(context: Context): AdRequest {
        val width: Int
        val height: Int

        if (context is Activity) {
            val decorView: View = context.window.decorView
            width = decorView.width
            height = decorView.height
        } else {
            width = 0
            height = 0
        }

        return AdRequest(test = controller.testEnabled,
                pos = AdRequest.Position.FullScreen.value,
                skip = AdRequest.Skip.Yes.value,
                playbackmethod = AdRequest.PlaybackSoundOnScreen,
                startdelay = AdRequest.StartDelay.PreRoll.value,
                instl = AdRequest.FullScreen.On.value,
                w = width,
                h = height)
    }

    internal fun getAdResponse(): AdResponse? {
        return controller.adResponse
    }

    internal fun isTestEnabled(): Boolean = controller.testEnabled

    internal fun isBumperPageEnabled(): Boolean = controller.bumperPageEnabled

    internal fun isParentalGateEnabled(): Boolean = controller.parentalGateEnabled

    internal fun isMoatLimiting(): Boolean = controller.moatLimiting

    internal fun getDelegate(): SAInterface? = controller.delegate
}