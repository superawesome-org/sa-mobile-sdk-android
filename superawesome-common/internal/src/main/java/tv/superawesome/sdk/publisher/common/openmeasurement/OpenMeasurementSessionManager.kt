package tv.superawesome.sdk.publisher.common.openmeasurement

import android.view.View
import android.webkit.WebView
import com.iab.omid.library.superawesome.adsession.AdEvents
import com.iab.omid.library.superawesome.adsession.AdSession
import tv.superawesome.sdk.publisher.common.components.Logger
import java.lang.IllegalStateException

/**
 * Main class for constructing and managing an OM ad session for a single ad instance.
 */
class OpenMeasurementSessionManager(
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
            logAdSessionCreateError(error)
            return
        } catch (error: IllegalStateException) {
            logAdSessionCreateError(error)
            return
        }
    }

    private fun logAdSessionCreateError(error: Exception) {
        val throwable = OpenMeasurementError.AdSessionCreationFailure(error)
        logger.error("${throwable.message} error: ${error.message}", throwable)
    }

    private fun startSession() {
        if (!checkIfSessionExists()) return
        try {
            adSession?.start()
        } catch (error: IllegalArgumentException) {
            logAdSessionStartError(error)
        } catch (error: IllegalStateException) {
            logAdSessionStartError(error)
        }
    }

    private fun logAdSessionStartError(error: Exception) {
        val throwable = OpenMeasurementError.AdSessionStartFailure()
        logger.error("${throwable.message} error: ${error.message}", throwable)
    }

    private fun setupAdEvents() {
        if (!checkIfSessionExists()) return
        try {
            adEvents = AdEvents.createAdEvents(adSession)
        } catch (error: IllegalArgumentException) {
            logAdEventsSetupError(error)
        } catch (error: IllegalStateException) {
            logAdEventsSetupError(error)
        }
    }

    private fun logAdEventsSetupError(error: Exception) {
        logger.error("Unable to create ad events error: ${error.message}", error)
    }

    private fun checkIfSessionExists(): Boolean =
        if (adSession == null) {
            val error = OpenMeasurementError.AdSessionUnavailableFailure()
            logger.error(error.message, error)
            false
        } else {
            true
        }

    // Public

    /**
     * Builds the ad session.
     * @param webView The web view containing the ad.
     */
    override fun setup(webView: WebView) {
        createSession(webView)
    }

    /**
     * Starts the ad session if one has been created and creates the ad events object.
     */
    override fun start() {
        startSession()
        setupAdEvents()
    }

    /**
     * Ends and cleans up the ad session and events.
     */
    override fun finish() {
        adSession?.finish()
        adEvents = null
        adSession = null
    }

    /**
     * Sets the ad view.
     * @param view The view containing the ad.
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
     * Injects the OMID JS into the ad html.
     * @param html The html string for the ad.
     * @return The html for the ad with the OMID js if injection was successful.
     */
    override fun injectJS(html: String): String = jsInjector.injectJS(html)

    /**
     * Adds a view to the friendly obstruction list in the ad session,
     * this is for views that are overlaid on the ad such as close buttons, images etc.
     * @param view The obstruction view.
     * @param purpose An enum containing the purpose of the obstructive view.
     * @param reason A descriptive reason for the view.
     */
    override fun addFriendlyObstruction(
        view: View,
        purpose: FriendlyObstructionType,
        reason: String?,
    ) {
        if (!checkIfSessionExists()) return
        try {
            adSession?.addFriendlyObstruction(view, purpose.omidFriendlyObstruction(), reason)
        } catch (error: IllegalArgumentException) {
            logger.error("Failed to add friendly obstruction error: ${error.message}", error)
        }
    }

    /**
     * Sends the ad loaded event to OMID via the AdEvents object if
     * the ad session exists and is started.
     */
    override fun sendAdLoaded() {
        adEvents?.let {
            try {
                adEvents?.loaded()
            } catch (error: IllegalStateException) {
                logger.error("Unable to send the ad loaded event error: ${error.message}", error)
            }
        } ?: logger.error("Unable to send the ad loaded event as ad events is null", null)
    }

    /**
     * Sends the ad impression event to OMID via the AdEvents object if
     * the ad session exists and is started.
     */
    override fun sendAdImpression() {
        adEvents?.let {
            try {
                adEvents?.impressionOccurred()
            } catch (error: IllegalStateException) {
                logger.error("Unable to send the ad impression event error: ${error.message}", error)
            }
        } ?: logger.error("Unable to send the ad impression event as ad events is null", null)
    }
}
