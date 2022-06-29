@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.ui.interstitial

import android.app.Activity
import android.content.Context
import android.view.View
import org.koin.java.KoinJavaComponent.inject
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.Orientation
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.ui.common.AdControllerType
import tv.superawesome.sdk.publisher.common.ui.interstitial.InterstitialActivity

public object SAInterstitialAd {
    private var orientation: Orientation = Constants.defaultOrientation
    private var backButtonEnabled: Boolean = Constants.defaultBackButtonEnabled

    private val controller: AdControllerType by inject(AdControllerType::class.java)

    /**
     * Static method that loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId the Ad placement id to load data for
     * @param context the current context
     */
    public fun load(placementId: Int, context: Context) {
        controller.load(placementId, makeAdRequest(context))
    }

    /**
     * Static method that loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId the Ad placement id to load data for
     * @param lineItemId
     * @param creativeId id of the Creative
     * @param context the current context
     */
    public fun load(placementId: Int, lineItemId: Int, creativeId: Int, context: Context) {
        controller.load(placementId, lineItemId, creativeId, makeAdRequest(context))
    }

    /**
     * Static method that, if an ad data is loaded, will play the content for the user
     *
     * @param placementId the Ad placement id to play an ad for
     * @param context the current context (activity or fragment)
     */
    public fun play(placementId: Int, context: Context) {
        InterstitialActivity.start(placementId, context)
    }

    public fun setListener(value: SAInterface) {
        controller.delegate = value
    }

    public fun enableParentalGate() {
        setParentalGate(true)
    }

    public fun disableParentalGate() {
        setParentalGate(false)
    }

    public fun enableBumperPage() {
        setBumperPage(true)
    }

    public fun disableBumperPage() {
        setBumperPage(false)
    }

    public fun enableTestMode() {
        setTestMode(true)
    }

    public fun disableTestMode() {
        setTestMode(false)
    }

    public fun enableBackButton() {
        setBackButton(true)
    }

    public fun disableBackButton() {
        setBackButton(false)
    }

    fun setConfigurationProduction() {
    }

    fun setConfigurationStaging() {
    }

    public fun setOrientationAny() {
        setOrientation(Orientation.Any)
    }

    public fun setOrientationPortrait() {
        setOrientation(Orientation.Portrait)
    }

    public fun setOrientationLandscape() {
        setOrientation(Orientation.Landscape)
    }

    public fun getIsBumperPageEnabled(): Boolean = controller.config.isBumperPageEnabled

    public fun setParentalGate(value: Boolean) {
        controller.config.isParentalGateEnabled = value
    }

    public fun setBumperPage(value: Boolean) {
        controller.config.isBumperPageEnabled = value
    }

    public fun setTestMode(value: Boolean) {
        controller.config.testEnabled = value
    }

    public fun setBackButton(value: Boolean) {
        backButtonEnabled = value
    }

    public fun setOrientation(value: Orientation) {
        orientation = value
    }

    public fun disableMoatLimiting() {
        controller.moatLimiting = false
    }

    /**
     * Static method that returns whether ad data for a certain placement has already been loaded
     *
     * @param placementId the Ad placement id to check for
     * @return true or false
     */
    public fun hasAdAvailable(placementId: Int): Boolean = controller.hasAdAvailable(placementId)

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

        return AdRequest(
            test = isTestEnabled(),
            pos = AdRequest.Position.FullScreen.value,
            skip = AdRequest.Skip.Yes.value,
            playbackMethod = AdRequest.PlaybackSoundOnScreen,
            startDelay = AdRequest.StartDelay.PreRoll.value,
            install = AdRequest.FullScreen.On.value,
            w = width,
            h = height
        )
    }

    internal fun isTestEnabled(): Boolean = controller.config.testEnabled

    internal fun isBumperPageEnabled(): Boolean = controller.config.isBumperPageEnabled

    internal fun isParentalGateEnabled(): Boolean = controller.config.isParentalGateEnabled

    internal fun isMoatLimiting(): Boolean = controller.moatLimiting

    internal fun getDelegate(): SAInterface? = controller.delegate
}
