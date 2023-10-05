@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.ui.video

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.View
import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.getKoin
import org.koin.java.KoinJavaComponent.inject
import tv.superawesome.sdk.publisher.components.Logger
import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.models.CloseButtonState
import tv.superawesome.sdk.publisher.models.DefaultAdRequest
import tv.superawesome.sdk.publisher.models.Orientation
import tv.superawesome.sdk.publisher.models.SAEvent
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.ad.AdManager
import tv.superawesome.sdk.publisher.ad.AdController
import tv.superawesome.sdk.publisher.ui.managed.ManagedAdActivity
import java.io.File

/**
 * Video ads are full-screen ads that cover the interface of their host app.
 * They are a full screen experience where users opt-in to view a video ad in exchange for something
 * of value, such as virtual currency, in-app items, exclusive content, and more.
 * This class allows the configuration of the SuperAwesome video ads.
 */
@Suppress("TooManyFunctions")
public object SAVideoAd {
    private val adManager: AdManager by inject(AdManager::class.java)
    private val logger: Logger by inject(Logger::class.java)
    private val scope: CoroutineScope by inject(CoroutineScope::class.java)

    /**
     * Loads an ad into the queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId the Ad placement id to load data for.
     * @param context the current context.
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
        logger.info("load($placementId)")
        scope.launch {
            adManager.load(placementId, makeAdRequest(context, options, openRtbPartnerId))
        }
    }

    /**
     * Loads an ad into the queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId the Ad placement id to load data for.
     * @param lineItemId
     * @param creativeId id of the Creative.
     * @param context the current context.
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

        getKoin().createScope<AdController>(scopeId = placementId.toString())
    }

    /**
     * Plays the content for the user if the ad data is loaded.
     *
     * @param placementId the Ad placement id to play an ad for.
     * @param context the current activity context.
     */
    @JvmStatic
    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    public fun play(placementId: Int, context: Context) {
        logger.info("play($placementId)")
        val controller = getKoin().get<AdController> {
            parametersOf(placementId)
        }
        val adResponse = controller.adResponse
        controller.startTimerForLoadTime()
        val intent = if (adResponse.ad.isVpaid) {
            ManagedAdActivity.newInstance(
                context = context,
                placementId = placementId,
                adConfig = adManager.adConfig,
                html = adResponse.html ?: "",
                baseUrl = adResponse.baseUrl,
            )
        } else {

            if (adResponse.filePath == null) {
                controller.listener?.onEvent(placementId, SAEvent.adFailedToShow)
                return
            }

            try {
                Uri.fromFile(File(adResponse.filePath))
            } catch (error: Exception) {
                controller.listener?.onEvent(placementId, SAEvent.adFailedToShow)
                return
            }

            VideoActivity.newInstance(context, placementId, adManager.adConfig)
        }
        context.startActivity(intent)
    }

    /**
     * Sets the video listener.
     *
     * @param value listener.
     */
    @JvmStatic
    public fun setListener(value: SAInterface) {
        adManager.listener = value
    }

    /**
     * Sets the video playback mode.
     *
     * @param mode the desired playback mode.
     */
    @JvmStatic
    public fun setPlaybackMode(mode: AdRequest.StartDelay) {
        adManager.adConfig.startDelay = mode
    }

    /**
     * Enables showing the parental gate.
     */
    @JvmStatic
    public fun enableParentalGate() {
        setParentalGate(true)
    }

    /**
     * Disables showing the parental gate.
     */
    @JvmStatic
    public fun disableParentalGate() {
        setParentalGate(false)
    }

    /**
     * Enables showing the bumper page.
     */
    @JvmStatic
    public fun enableBumperPage() {
        setBumperPage(true)
    }

    /**
     * Disables showing the bumper page.
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

    /**
     * Sets whether the small click button should show.
     *
     * @param value `true` to show the button, `false` to hide it.
     */
    @JvmStatic
    public fun setSmallClick(value: Boolean) {
        adManager.adConfig.shouldShowSmallClick = value
    }

    /**
     * Sets whether the ad should close at the end of the video.
     *
     * @param value `true` to close the ad, `false` otherwise.
     */
    @JvmStatic
    public fun setCloseAtEnd(value: Boolean) {
        adManager.adConfig.shouldCloseAtEnd = value
    }

    /**
     * Sets the close button visibility.
     *
     * @param `true` makes the button visible with delay, `false` hides the button.
     */
    @JvmStatic
    public fun setCloseButton(value: Boolean) {
        adManager.adConfig.closeButtonState =
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
     * Enables the close button to display immediately without a delay.
     * **WARNING**: This allows users to close the ad before the viewable tracking event is fired
     * and should only be used if you explicitly want this behaviour over consistent tracking.
     */
    @JvmStatic
    public fun enableCloseButtonNoDelay() {
        adManager.adConfig.closeButtonState = CloseButtonState.VisibleImmediately
    }

    /**
     * Shows a warning dialog prior to closing the video via the close button or the
     * the back button.
     */
    @JvmStatic
    public fun enableCloseButtonWithWarning() {
        setCloseButton(true)
        setCloseButtonWarning(true)
    }

    /**
     * Sets if the warning when closing the video ad should show.
     *
     * @param value `true` to show warning, `false` otherwise.
     */
    @JvmStatic
    public fun setCloseButtonWarning(value: Boolean) {
        adManager.adConfig.shouldShowCloseWarning = value
    }

    /**
     * Sets the orientation to any (portrait or landscape).
     */
    @JvmStatic
    public fun setOrientationAny() {
        setOrientation(Orientation.Any)
    }

    /**
     * Sets the video orientation to portrait.
     */
    @JvmStatic
    public fun setOrientationPortrait() {
        setOrientation(Orientation.Portrait)
    }

    /**
     * Sets the video orientation to landscape.
     */
    @JvmStatic
    public fun setOrientationLandscape() {
        setOrientation(Orientation.Landscape)
    }

    /**
     * Sets whether the parental gate should show.
     *
     * @param value `true` to show parental gate, `false` otherwise.
     */
    @JvmStatic
    public fun setParentalGate(value: Boolean) {
        adManager.adConfig.isParentalGateEnabled = value
    }

    /**
     * Sets whether the bumper page should show.
     *
     * @param value `true` to show bumper page, `false` otherwise.
     */
    @JvmStatic
    public fun setBumperPage(value: Boolean) {
        adManager.adConfig.isBumperPageEnabled = value
    }

    /**
     * Sets whether the test mode is enabled.
     *
     * @param value `true` to enable test mode, `false` otherwise.
     */
    @JvmStatic
    public fun setTestMode(value: Boolean) {
        adManager.adConfig.testEnabled = value
    }

    /**
     * Sets whether the back button should be enabled.
     *
     * @param value `true` to enable back button, `false` otherwise.
     */
    @JvmStatic
    public fun setBackButton(value: Boolean) {
        adManager.adConfig.isBackButtonEnabled = value
    }

    /**
     * Sets the video orientation.
     *
     * @param value the desired [Orientation].
     */
    @JvmStatic
    public fun setOrientation(value: Orientation) {
        adManager.adConfig.orientation = value
    }

    /**
     * Sets whether the video should be muted on start or not.
     *
     * @param mute `true` to mute the video, `false` otherwise.
     */
    @JvmStatic
    public fun setMuteOnStart(mute: Boolean) {
        adManager.adConfig.shouldMuteOnStart = mute
    }

    /**
     * Makes the video muted on start.
     */
    @JvmStatic
    public fun enableMuteOnStart() {
        setMuteOnStart(true)
    }

    /**
     * Makes the video un-muted on start.
     */
    @JvmStatic
    public fun disableMuteOnStart() {
        setMuteOnStart(false)
    }

    /**
     * Returns whether ad data for a certain placement has already been loaded.
     *
     * @param placementId the Ad placement id to check for.
     * @return true or false.
     */
    @JvmStatic
    public fun hasAdAvailable(placementId: Int): Boolean = adManager.hasAdAvailable(placementId)

    private fun makeAdRequest(
        context: Context,
        options: Map<String, Any>?,
        openRtbPartnerId: String? = null,
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

        val skip = if (adManager.adConfig.closeButtonState.isVisible()) {
            AdRequest.Skip.Yes.value
        } else {
            AdRequest.Skip.No.value
        }

        val playbackMethod = if (adManager.adConfig.shouldMuteOnStart) {
            DefaultAdRequest.PlaybackSoundOffScreen
        } else {
            DefaultAdRequest.PlaybackSoundOffScreen
        }


        return DefaultAdRequest(
            test = adManager.adConfig.testEnabled,
            pos = AdRequest.Position.FullScreen.value,
            skip = skip,
            playbackMethod = playbackMethod,
            startDelay = AdRequest.StartDelay.PreRoll.value,
            install = AdRequest.FullScreen.On.value,
            w = width,
            h = height,
            openRtbPartnerId = openRtbPartnerId,
            options = options,
        )
    }

    @VisibleForTesting
    @JvmStatic
    private fun clearCache() {
        // TODO
        //controller.clearCache()
    }
}
