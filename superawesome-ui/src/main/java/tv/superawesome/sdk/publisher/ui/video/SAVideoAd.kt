package tv.superawesome.sdk.publisher.ui.video

import android.app.Activity
import android.content.Context
import android.view.View
import org.koin.core.get
import org.koin.core.inject
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.di.Injectable
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.models.Orientation
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.ui.common.AdControllerType

object SAVideoAd : Injectable {
    private val controller: AdControllerType by lazy { get() }
    private val logger: Logger by inject()

    /**
     * Static method that loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId   the Ad placement id to load data for
     * @param context       the current context
     */
    fun load(placementId: Int, context: Context) {
        logger.info("load($placementId)")
        controller.load(placementId, makeAdRequest(context))
    }

    /**
     * Static method that, if an ad data is loaded, will play the content for the user
     *
     * @param placementId   the Ad placement id to play an ad for
     * @param context       the current context (activity or fragment)
     */
    fun play(placementId: Int, context: Context) {
        logger.info("play($placementId)")
        VideoActivity.start(placementId, controller.config, context)
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

    fun setCloseButton(value: Boolean) {
        controller.config.shouldShowCloseButton = value
    }

    fun enableCloseButton() {
        setCloseButton(true)
    }

    fun disableCloseButton() {
        setCloseButton(false)
    }

    fun setConfigurationProduction() {
    }

    fun setConfigurationStaging() {
    }

    fun setOrientationAny() {
        setOrientation(Orientation.Any)
    }

    fun setOrientationPortrait() {
        setOrientation(Orientation.Portrait)
    }

    fun setOrientationLandscape() {
        setOrientation(Orientation.Landscape)
    }

    fun getIsBumperPageEnabled(): Boolean = controller.config.isBumperPageEnabled

    fun setParentalGate(value: Boolean) {
        controller.config.isParentalGateEnabled = value
    }

    fun setBumperPage(value: Boolean) {
        controller.config.isBumperPageEnabled = value
    }

    fun setTestMode(value: Boolean) {
        controller.config.testEnabled = value
    }

    fun setBackButton(value: Boolean) {
        controller.config.isBackButtonEnabled = value
    }

    fun setOrientation(value: Orientation) {
        controller.config.orientation = value
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
    fun hasAdAvailable(placementId: Int): Boolean = controller.hasAdAvailable(placementId)

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

        return AdRequest(test = isTestEnabled(),
                pos = AdRequest.Position.FullScreen.value,
                skip = if (controller.config.shouldShowCloseButton) AdRequest.Skip.Yes.value else AdRequest.Skip.No.value,
                playbackmethod = AdRequest.PlaybackSoundOnScreen,
                startdelay = AdRequest.StartDelay.PreRoll.value,
                instl = AdRequest.FullScreen.On.value,
                w = width,
                h = height)
    }

    internal fun isTestEnabled(): Boolean = controller.config.testEnabled

    internal fun isBumperPageEnabled(): Boolean = controller.config.isBumperPageEnabled

    internal fun isParentalGateEnabled(): Boolean = controller.config.isParentalGateEnabled

    internal fun isMoatLimiting(): Boolean = controller.moatLimiting

    internal fun getDelegate(): SAInterface? = controller.delegate
}