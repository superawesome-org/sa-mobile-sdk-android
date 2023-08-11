package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import android.view.View
import android.webkit.WebView
import com.iab.omid.library.superawesome.adsession.AdEvents
import com.iab.omid.library.superawesome.adsession.AdSession
import tv.superawesome.sdk.publisher.common.components.Logger

/**
 * OpenMeasurementSessionManager - main class for constructing and managing the OM ad session
 */
internal class OpenMeasurementSessionManager(
    private val sessionBuilder: OpenMeasurementAdSessionBuilderType,
    private val jsInjector: OpenMeasurementJSInjectorType,
    private val logger: Logger,
): OpenMeasurementSessionManagerType {

    private var adSession: AdSession? = null
    private var adEvents: AdEvents? = null

    // Private

    private fun createSession(
        context: Context,
        webView: WebView,
    ) {
        try {
            adSession = sessionBuilder.getHtmlAdSession(
                context,
                webView,
                null,
            )
        } catch (error: IllegalArgumentException) {
            val throwable = OpenMeasurementError.AdSessionCreationFailure(error)
            logger.error(throwable.message, throwable)
            return
        }
    }

    private fun startSession() {
        try {
            adSession?.start()
        } catch (error: IllegalArgumentException) {
            val throwable = OpenMeasurementError.AdSessionStartFailure()
            logger.error(throwable.message, throwable)
            return
        }
    }

    private fun setupAdEvents() {
        if (adSession == null) {
            val error = OpenMeasurementError.AdSessionUnavailableFailure()
            logger.error(error.message, error)
            return
        }

        try {
            adEvents = AdEvents.createAdEvents(adSession)
        } catch (error: Exception) {
            logger.error("Unable to create ad events", error)
        }
    }

    // Public

    /**
     * setup - builds the ad session
     * @param context - the context used for activating and updating the OMID object
     * @param webView - the web view containing the ad
     */
    override fun setup(
        context: Context,
        webView: WebView,
    ) {
        createSession(context, webView)
    }

    /**
     * start - starts the ad session if one has been created and creates the ad events object
     */
    override fun start() {
        startSession()
        setupAdEvents()
    }

    /**
     * finish - ends and cleans up the ad session and events
     */
    override fun finish() {
        adSession?.finish()
        adEvents = null
        adSession = null
    }

    /**
     * setAdView - sets the ad view
     * @param view - the view containing the ad
     */
    override fun setAdView(view: View?) {
        if (view == null || adSession == null) {
            val error = OpenMeasurementError.UpdateAdViewFailure()
            logger.error(error.message, error)
            return
        }
        adSession?.registerAdView(view)
    }

    /**
     * injectJS - injects the OMID JS into the ad html
     * @param context - used to load the JS file from local storage
     * @param html - the html string for the ad
     * @return string - the html for the ad with the OMID js if injection was successful
     */
    override fun injectJS(
        context: Context,
        html: String,
    ): String = jsInjector.injectJS(context, html)

    /**
     * addFriendlyObstruction - adds a view to the friendly obstruction list in the ad session,
     * this is for views that are overlaid on the ad such as close buttons, images etc.
     * @param view - the obstruction view
     * @param purpose - an enum containing the purpose of the obstructive view
     * @param reason - a descriptive reason for the view
     */
    override fun addFriendlyObstruction(
        view: View,
        purpose: FriendlyObstructionType,
        reason: String?,
    ) {
        try {
            adSession?.addFriendlyObstruction(view, purpose.omidFriendlyObstruction(), reason)
        } catch (error: Exception) {
            logger.error("Failed to add friendly obstruction", error)
        }
    }

    /**
     * sendAdLoaded - sends the ad loaded event to OMID via the AdEvents object if
     * the ad session exists and is started
     */
    override fun sendAdLoaded() {
        try {
            adEvents?.loaded()
        } catch (error: Exception) {
            logger.error("Unable to send the ad loaded event", error)
        }
    }

    /**
     * sendAdImpression - sends the ad impression event to OMID via the AdEvents object if
     * the ad session exists and is started
     */
    override fun sendAdImpression() {
        try {
            adEvents?.impressionOccurred()
        } catch (error: Exception) {
            logger.error("Unable to send the ad impression event", error)
        }
    }
}
