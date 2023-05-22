@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.ui.video

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.annotation.VisibleForTesting
import org.koin.java.KoinJavaComponent.inject
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.models.CloseButtonState
import tv.superawesome.sdk.publisher.common.models.Orientation
import tv.superawesome.sdk.publisher.common.models.SAInterface
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
     * @param options: an optional dictionary of data to send with an ad's requests and events.
     * Supports String or Int values.
     */
    @JvmStatic
    @JvmOverloads
    public fun load(placementId: Int, context: Context, options: Map<String, Any>? = null) {
        logger.info("load($placementId)")
        controller.load(placementId, makeAdRequest(context, options))
    }

    /**
     * Static method that loads an ad into the queue.
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
        options: Map<String, Any>? = null,
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
        logger.info("play($placementId)")
        val adResponse = controller.peekAdResponse(placementId)
        val intent = if (adResponse?.ad?.isVpaid == true) {
            ManagedAdActivity.newInstance(
                context = context,
                placementId = placementId,
                config = controller.config,
                html = adResponse.html ?: "",
                baseUrl = adResponse.baseUrl,
            )
        } else {
            VideoActivity.newInstance(context, placementId, controller.config)
        }
        context.startActivity(intent)
    }

    @JvmStatic
    public fun setListener(value: SAInterface) {
        controller.delegate = value
    }

    @JvmStatic
    public fun setPlaybackMode(mode: AdRequest.StartDelay) {
        controller.config.startDelay = mode
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

    /**
     * Enables the back button to close the ad.
     */
    @JvmStatic
    public fun enableBackButton() {
        setBackButton(true)
    }

    /**
     * Disables the back button to close the ad.
     */
    @JvmStatic
    public fun disableBackButton() {
        setBackButton(false)
    }

    @JvmStatic
    public fun setSmallClick(value: Boolean) {
        controller.config.shouldShowSmallClick = value
    }

    @JvmStatic
    public fun setCloseAtEnd(value: Boolean) {
        controller.config.shouldCloseAtEnd = value
    }

    @JvmStatic
    public fun setCloseButton(value: Boolean) {
        controller.config.closeButtonState =
            if (value) CloseButtonState.VisibleWithDelay else CloseButtonState.Hidden
    }

    /**
     * Enables close button to be displayed after a delay.
     */
    @JvmStatic
    public fun enableCloseButton() {
        setCloseButton(true)
    }

    /**
     * Disables the close button and makes it hidden until the ad ends.
     */
    @JvmStatic
    public fun disableCloseButton() {
        setCloseButton(false)
    }

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
     * Method that shows a warning dialog prior to closing the video via the close button or the
     * the back button.
     */
    @JvmStatic
    public fun enableCloseButtonWithWarning() {
        setCloseButton(true)
        setCloseButtonWarning(true)
    }

    @JvmStatic
    public fun setCloseButtonWarning(value: Boolean) {
        controller.config.shouldShowCloseWarning = value
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
        controller.config.isBackButtonEnabled = value
    }

    @JvmStatic
    public fun setOrientation(value: Orientation) {
        controller.config.orientation = value
    }

    @JvmStatic
    fun setMuteOnStart(mute: Boolean) {
        controller.config.shouldMuteOnStart = mute
    }

    @JvmStatic
    fun enableMuteOnStart() {
        setMuteOnStart(true)
    }

    @JvmStatic
    fun disableMuteOnStart() {
        setMuteOnStart(false)
    }

    /**
     * Static method that returns whether ad data for a certain placement has already been loaded
     *
     * @param placementId the Ad placement id to check for
     * @return true or false
     */
    @JvmStatic
    public fun hasAdAvailable(placementId: Int): Boolean = controller.hasAdAvailable(placementId)

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

        val skip = if (controller.config.closeButtonState.isVisible())
            AdRequest.Skip.Yes.value else AdRequest.Skip.No.value

        val playbackMethod = if (controller.config.shouldMuteOnStart)
            AdRequest.PlaybackSoundOffScreen else AdRequest.PlaybackSoundOnScreen

        return AdRequest(
            test = isTestEnabled(),
            pos = AdRequest.Position.FullScreen.value,
            skip = skip,
            playbackMethod = playbackMethod,
            startDelay = AdRequest.StartDelay.PreRoll.value,
            install = AdRequest.FullScreen.On.value,
            w = width,
            h = height,
            options = options,
        )
    }

    private fun isTestEnabled(): Boolean = controller.config.testEnabled

    internal fun getDelegate(): SAInterface? = controller.delegate

    @VisibleForTesting
    @JvmStatic
    private fun clearCache() {
        controller.clearCache()
    }
}
