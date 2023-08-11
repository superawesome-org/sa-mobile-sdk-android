package tv.superawesome.sdk.publisher.common.openmeasurement

import android.view.View
import android.webkit.WebView
import com.iab.omid.library.superawesome.adsession.AdEvents
import com.iab.omid.library.superawesome.adsession.AdSession
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.openmeasurement.error.AdSessionCreationFailureThrowable
import tv.superawesome.sdk.publisher.common.openmeasurement.error.AdSessionStartFailureThrowable
import tv.superawesome.sdk.publisher.common.openmeasurement.error.AdSessionUnavailableFailureThrowable
import tv.superawesome.sdk.publisher.common.openmeasurement.error.UpdateAdViewFailureThrowable

/**
 * OpenMeasurementSessionManager - main class for constructing and managing the OM ad session
 */
internal class OpenMeasurementSessionManager(
    private val sessionFactory: OpenMeasurementAdSessionFactoryType,
    private val jsInjector: OpenMeasurementJSInjectorType,
    private val logger: Logger,
): OpenMeasurementSessionManagerType {

    private var adSession: AdSession? = null
    private var adEvents: AdEvents? = null

    // Private

    private fun createSession(webView: WebView) {
        try {
            adSession = sessionFactory.getHtmlAdSession(
                webView,
                null,
            )
        } catch (error: IllegalArgumentException) {
            val throwable = AdSessionCreationFailureThrowable()
            logger.error(throwable.message, throwable)
            return
        }
    }

    private fun startSession() {
        if (!checkIfSessionExists()) return
        try {
            adSession?.start()
        } catch (error: Throwable) {
            val throwable = AdSessionStartFailureThrowable()
            logger.error(throwable.message, throwable)
            return
        }
    }

    private fun setupAdEvents() {
        if (!checkIfSessionExists()) return

        try {
            adEvents = AdEvents.createAdEvents(adSession)
        } catch (error: Throwable) {
            logger.error("Unable to create ad events", error)
        }
    }

    private fun checkIfSessionExists(): Boolean =
        if (adSession == null) {
            val error = AdSessionUnavailableFailureThrowable()
            logger.error(error.message, error)
            false
        } else {
            true
        }

    // Public

    /**
     * setup - builds the ad session
     * @param webView - the web view containing the ad
     */
    override fun setup(webView: WebView) {
        createSession(webView)
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
            val error = UpdateAdViewFailureThrowable()
            logger.error(error.message, error)
            return
        }
        adSession?.registerAdView(view)
    }

    /**
     * injectJS - injects the OMID JS into the ad html
     * @param html - the html string for the ad
     * @return string - the html for the ad with the OMID js if injection was successful
     */
    override fun injectJS(html: String): String = jsInjector.injectJS(html)

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
        if (!checkIfSessionExists()) return
        try {
            adSession?.addFriendlyObstruction(view, purpose.omidFriendlyObstruction(), reason)
        } catch (error: Throwable) {
            logger.error("Failed to add friendly obstruction", error)
        }
    }

    /**
     * sendAdLoaded - sends the ad loaded event to OMID via the AdEvents object if
     * the ad session exists and is started
     */
    override fun sendAdLoaded() {
        adEvents?.let {
            try {
                it.loaded()
            } catch (error: Throwable) {
                logger.error("Unable to send the ad loaded event", error)
            }
        } ?: logger.error("Unable to send the ad loaded event as ad events is null", null)
    }

    /**
     * sendAdImpression - sends the ad impression event to OMID via the AdEvents object if
     * the ad session exists and is started
     */
    override fun sendAdImpression() {
        adEvents?.let {
            try {
                it.impressionOccurred()
            } catch (error: Throwable) {
                logger.error("Unable to send the ad impression event", error)
            }
        } ?: logger.error("Unable to send the ad impression event as ad events is null", null)
    }
}
