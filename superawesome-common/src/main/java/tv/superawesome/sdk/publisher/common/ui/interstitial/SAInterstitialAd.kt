@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.ui.interstitial

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.annotation.VisibleForTesting
import org.koin.java.KoinJavaComponent.inject
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.models.CloseButtonState
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.Orientation
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.ui.common.AdControllerType

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
     * @param options: an optional dictionary of data to send with an ad's requests and events.
     * Supports String or Int values.
     */
    @JvmStatic
    @JvmOverloads
    public fun load(placementId: Int, context: Context, options: Map<String, Any>? = null) {
        controller.load(placementId, makeAdRequest(context, options))
    }

    /**
     * Static method that loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId the Ad placement id to load data for
     * @param lineItemId
     * @param creativeId id of the Creative
     * @param context the current context
     * @param options: an optional dictionary of data to send with an ad's requests and events.
     * Supports String or Int values.
     */
    @JvmStatic
    @JvmOverloads
    public fun load(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        context: Context,
        options: Map<String, Any>? = null
    ) {
        controller.load(placementId, lineItemId, creativeId, makeAdRequest(context, options))
    }

    /**
     * Static method that, if an ad data is loaded, will play the content for the user
     *
     * @param placementId the Ad placement id to play an ad for
     * @param context the current context (activity or fragment)
     */
    @JvmStatic
    public fun play(placementId: Int, context: Context) {
        InterstitialActivity.start(placementId, context)
    }

    @JvmStatic
    public fun setListener(value: SAInterface) {
        controller.delegate = value
    }

    @JvmStatic

    public fun enableParentalGate() {
        setParentalGate(true)
    }

    @JvmStatic
    public fun disableParentalGate() {
        setParentalGate(false)
    }

    @JvmStatic
    public fun enableBumperPage() {
        setBumperPage(true)
    }

    @JvmStatic
    public fun disableBumperPage() {
        setBumperPage(false)
    }

    @JvmStatic
    public fun enableTestMode() {
        setTestMode(true)
    }

    @JvmStatic
    public fun disableTestMode() {
        setTestMode(false)
    }

    @JvmStatic
    public fun enableBackButton() {
        setBackButton(true)
    }

    @JvmStatic
    public fun disableBackButton() {
        setBackButton(false)
    }

    @JvmStatic
    public fun setOrientationAny() {
        setOrientation(Orientation.Any)
    }

    @JvmStatic
    public fun setOrientationPortrait() {
        setOrientation(Orientation.Portrait)
    }

    @JvmStatic
    public fun setOrientationLandscape() {
        setOrientation(Orientation.Landscape)
    }

    @JvmStatic
    public fun setParentalGate(value: Boolean) {
        controller.config.isParentalGateEnabled = value
    }

    @JvmStatic
    public fun setBumperPage(value: Boolean) {
        controller.config.isBumperPageEnabled = value
    }

    @JvmStatic
    public fun setTestMode(value: Boolean) {
        controller.config.testEnabled = value
    }

    @JvmStatic
    public fun setBackButton(value: Boolean) {
        backButtonEnabled = value
    }

    @JvmStatic
    public fun setOrientation(value: Orientation) {
        orientation = value
    }

    /**
     * Static method that returns whether ad data for a certain placement has already been loaded
     *
     * @param placementId the Ad placement id to check for
     * @return true or false
     */
    @JvmStatic
    public fun hasAdAvailable(placementId: Int): Boolean = controller.hasAdAvailable(placementId)

    /**
     * Method that enables the close button to display immediately without a delay.
     * WARNING: this will allow users to close the ad before the viewable tracking event is fired
     * and should only be used if you explicitly want this behaviour over consistent tracking.
     */
    @JvmStatic
    public fun enableCloseButtonNoDelay() {
        controller.config.closeButtonState = CloseButtonState.VisibleImmediately
    }

    /**
     * Method that enables the close button to display with a delay.
     */
    @JvmStatic
    fun enableCloseButton() {
        controller.config.closeButtonState = CloseButtonState.VisibleWithDelay
    }

    private fun makeAdRequest(context: Context, options: Map<String, Any>?): AdRequest {
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
            h = height,
            options = options
        )
    }

    internal fun isTestEnabled(): Boolean = controller.config.testEnabled

    internal fun isBumperPageEnabled(): Boolean = controller.config.isBumperPageEnabled

    internal fun isParentalGateEnabled(): Boolean = controller.config.isParentalGateEnabled

    internal fun getDelegate(): SAInterface? = controller.delegate

    @VisibleForTesting
    @JvmStatic
    private fun clearCache() {
        controller.clearCache()
    }
}
