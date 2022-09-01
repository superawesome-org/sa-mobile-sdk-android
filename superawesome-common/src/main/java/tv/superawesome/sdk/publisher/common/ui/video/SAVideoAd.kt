@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.ui.video

import android.app.Activity
import android.content.Context
import android.view.View
import org.koin.java.KoinJavaComponent.inject
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.models.Orientation
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.state.CloseButtonState
import tv.superawesome.sdk.publisher.common.ui.common.AdControllerType
import tv.superawesome.sdk.publisher.common.ui.managed.ManagedAdActivity

public object SAVideoAd {
    private val controller: AdControllerType by inject(AdControllerType::class.java)
    private val logger: Logger by inject(Logger::class.java)

    /**
     * Static method that loads an ad into the queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId the Ad placement id to load data for
     * @param context the current context
     */
    public fun load(placementId: Int, context: Context) {
        logger.info("load($placementId)")
        controller.load(placementId, makeAdRequest(context))
    }

    /**
     * Static method that loads an ad into the queue.
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
        logger.info("play($placementId)")
        val adResponse = controller.peekAdResponse(placementId)
        val intent = if (adResponse?.ad?.isVpaid == true) {
            ManagedAdActivity.newInstance(
                context,
                placementId,
                controller.config,
                adResponse.html ?: ""
            )
        } else {
            VideoActivity.newInstance(context, placementId, controller.config)
        }
        context.startActivity(intent)
    }

    public fun setListener(value: SAInterface) {
        controller.delegate = value
    }

    public fun setPlaybackMode(mode: AdRequest.StartDelay) {
        controller.config.startDelay = mode
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

    public fun setSmallClick(value: Boolean) {
        controller.config.shouldShowSmallClick = value
    }

    public fun setCloseAtEnd(value: Boolean) {
        controller.config.shouldCloseAtEnd = value
    }

    public fun setCloseButton(value: Boolean) {
        controller.config.closeButtonState =
            if (value) CloseButtonState.VisibleWithDelay else CloseButtonState.Hidden
    }

    public fun enableCloseButton() {
        setCloseButton(true)
    }

    public fun disableCloseButton() {
        setCloseButton(false)
    }

    /**
     * Method that enables the close button to display immediately without a delay.
     * WARNING: this will allow users to close the ad before the viewable tracking event is fired
     * and should only be used if you explicitly want this behaviour over consistent tracking.
     */
    public fun enableCloseButtonNoDelay() {
        controller.config.closeButtonState = CloseButtonState.VisibleImmediately
    }

    /**
     * Method that shows a warning dialog prior to closing the video via the close button or the
     * the back button.
     */
    public fun enableCloseButtonWithWarning() {
        setCloseButton(true)
        setCloseButtonWarning(true)
    }

    public fun setCloseButtonWarning(value: Boolean) {
        controller.config.shouldShowCloseWarning = value
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
        controller.config.isBackButtonEnabled = value
    }

    public fun setOrientation(value: Orientation) {
        controller.config.orientation = value
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
            skip = if (controller.config.closeButtonState.isVisible()) AdRequest.Skip.Yes.value else AdRequest.Skip.No.value,
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
