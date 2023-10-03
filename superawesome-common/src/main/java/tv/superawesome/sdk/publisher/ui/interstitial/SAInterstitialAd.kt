@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.ui.interstitial

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.models.CloseButtonState
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.models.DefaultAdRequest
import tv.superawesome.sdk.publisher.models.Orientation
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.ad.AdManager

/**
 * Interstitial ads are full-screen ads that cover the interface of their host app.
 * They're typically displayed at natural transition points in the flow of an app,
 * such as between activities or during the pause between levels in a game.
 *
 * This class allows the configuration of the SuperAwesome interstitial ads.
 */
@Suppress("TooManyFunctions")
public object SAInterstitialAd {
    private var orientation: Orientation = Constants.defaultOrientation
    private var backButtonEnabled: Boolean = Constants.defaultBackButtonEnabled

    private val adManager: AdManager by inject(AdManager::class.java)
    private val scope: CoroutineScope by inject(CoroutineScope::class.java)

    /**
     * Loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId the Ad placement id to load data for
     * @param context the current context
     * @param openRtbPartnerId OpenRTB Partner ID parameter to be sent with all requests.
     * @param options: an optional dictionary of data to send with an ad's requests and events.
     * Supports String or Int values.
     */
    @JvmStatic
    @JvmOverloads
    public fun load(
        placementId: Int,
        context: Context,
        openRtbPartnerId: String? = null,
        options: Map<String, Any>? = null,
    ) {
        scope.launch {
            Log.d("MATHEUS", "Lading ad $placementId")
            adManager.load(placementId, makeAdRequest(context, options, openRtbPartnerId))
        }
    }

    /**
     * Loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId the Ad placement id to load data for
     * @param lineItemId
     * @param creativeId id of the Creative
     * @param context the current context
     * @param openRtbPartnerId OpenRTB Partner ID parameter to be sent with all requests.
     * @param options: an optional dictionary of data to send with an ad's requests and events.
     * Supports String or Int values.
     */
    @JvmStatic
    @JvmOverloads
    @Suppress("LongParameterList")
    public fun load(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        context: Context,
        openRtbPartnerId: String? = null,
        options: Map<String, Any>? = null,
    ) {
        scope.launch {
            adManager.load(
                placementId,
                lineItemId,
                creativeId,
                makeAdRequest(context, options, openRtbPartnerId)
            )
        }
    }

    /**
     * Plays the content for the user if an ad data is loaded.
     *
     * @param placementId the Ad placement id to play an ad for
     * @param context an activity context.
     */
    @JvmStatic
    public fun play(placementId: Int, context: Context) {
        InterstitialActivity.start(placementId, adManager.adConfig, context)
    }

    /**
     * Sets the callback listener.
     *
     * @param value [SAInterface] listener implementation.
     */
    @JvmStatic
    public fun setListener(value: SAInterface) {
        adManager.listener = value
    }

    /**
     * Enables parental gate.
     */
    @JvmStatic
    public fun enableParentalGate() {
        setParentalGate(true)
    }

    /**
     * Disables parental gate.
     */
    @JvmStatic
    public fun disableParentalGate() {
        setParentalGate(false)
    }

    /**
     * Enables bumper page.
     */
    @JvmStatic
    public fun enableBumperPage() {
        setBumperPage(true)
    }

    /**
     * Disables bumper page.
     */
    @JvmStatic
    public fun disableBumperPage() {
        setBumperPage(false)
    }

    /**
     * Enables test mode.
     */
    @JvmStatic
    public fun enableTestMode() {
        setTestMode(true)
    }

    /**
     * Disables test mode.
     */
    @JvmStatic
    public fun disableTestMode() {
        setTestMode(false)
    }

    /**
     * Enables the back button.
     */
    @JvmStatic
    public fun enableBackButton() {
        setBackButton(true)
    }

    /**
     * Disables the back button.
     */
    @JvmStatic
    public fun disableBackButton() {
        setBackButton(false)
    }

    /**
     * Sets the interstitial orientation to any (portrait or landscape).
     */
    @JvmStatic
    public fun setOrientationAny() {
        setOrientation(Orientation.Any)
    }

    /**
     * Sets the interstitial orientation to portrait.
     */
    @JvmStatic
    public fun setOrientationPortrait() {
        setOrientation(Orientation.Portrait)
    }

    /**
     * Sets the interstitial orientation to landscape.
     */
    @JvmStatic
    public fun setOrientationLandscape() {
        setOrientation(Orientation.Landscape)
    }

    /**
     * Sets whether the parental gate should show.
     *
     * @param value `true` to enable parental gate, `false` otherwise.
     */
    @JvmStatic
    public fun setParentalGate(value: Boolean) {
        adManager.adConfig.isParentalGateEnabled = value
    }

    /**
     * Sets whether the bumper page should show.
     *
     * @param value `true` enables bumper page, `false` otherwise.
     */
    @JvmStatic
    public fun setBumperPage(value: Boolean) {
        adManager.adConfig.isBumperPageEnabled = value
    }

    /**
     * Sets if the ad shows in test mode.
     *
     * @param value whether it should enable test mode or not.
     */
    @JvmStatic
    public fun setTestMode(value: Boolean) {
        adManager.adConfig.testEnabled = value
    }

    /**
     * Sets if the back button is enabled.
     *
     * @param value whether the button is enabled or not.
     */
    @JvmStatic
    public fun setBackButton(value: Boolean) {
        backButtonEnabled = value
    }

    /**
     * Sets the orientation for the interstitial ad.
     *
     * @param value the new orientation.
     */
    @JvmStatic
    public fun setOrientation(value: Orientation) {
        orientation = value
    }

    /**
     * Returns whether ad data for a certain placement has already been loaded.
     *
     * @param placementId the Ad placement id to check for.
     * @return true or false.
     */
    @JvmStatic
    public fun hasAdAvailable(placementId: Int): Boolean = adManager.hasAdAvailable(placementId)

    /**
     * Method that enables the close button to display immediately without a delay.
     * WARNING: this will allow users to close the ad before the viewable tracking event is fired
     * and should only be used if you explicitly want this behaviour over consistent tracking.
     */
    @JvmStatic
    public fun enableCloseButtonNoDelay() {
        adManager.adConfig.closeButtonState = CloseButtonState.VisibleImmediately
    }

    /**
     * Method that enables the close button to display with a delay.
     */
    @JvmStatic
    public fun enableCloseButton() {
        adManager.adConfig.closeButtonState = CloseButtonState.VisibleWithDelay
    }

    private fun makeAdRequest(
        context: Context,
        options: Map<String, Any>?,
        openRtbPartnerId: String?,
    ): AdRequest {
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

        return DefaultAdRequest(
            test = adManager.adConfig.testEnabled,
            pos = AdRequest.Position.FullScreen.value,
            skip = AdRequest.Skip.Yes.value,
            playbackMethod = DefaultAdRequest.PlaybackSoundOnScreen,
            startDelay = AdRequest.StartDelay.PreRoll.value,
            install = AdRequest.FullScreen.On.value,
            w = width,
            h = height,
            openRtbPartnerId = openRtbPartnerId,
            options = options
        )
    }

    @VisibleForTesting
    @JvmStatic
    private fun clearCache() {
        // TODO
     //   controller.clearCache()
    }
}
