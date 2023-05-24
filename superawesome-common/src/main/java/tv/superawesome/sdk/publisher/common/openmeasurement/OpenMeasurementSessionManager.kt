package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import android.view.View
import android.webkit.WebView
import com.iab.omid.library.superawesome.adsession.AdEvents
import com.iab.omid.library.superawesome.adsession.AdSession
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.openmeasurement.error.AdSessionCreationFailureThrowable
import tv.superawesome.sdk.publisher.common.openmeasurement.error.AdSessionStartFailureThrowable
import tv.superawesome.sdk.publisher.common.openmeasurement.error.AdSessionUnavailableFailureThrowable
import tv.superawesome.sdk.publisher.common.openmeasurement.error.UpdateAdViewFailureThrowable

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
            val throwable = AdSessionCreationFailureThrowable()
            logger.error(throwable.message, throwable)
            return
        }
    }

    private fun startSession() {
        try {
            adSession?.start()
        } catch (error: IllegalArgumentException) {
            val throwable = AdSessionStartFailureThrowable()
            logger.error(throwable.message, throwable)
            return
        }
    }

    private fun setupAdEvents() {
        if (adSession == null) {
            val error = AdSessionUnavailableFailureThrowable()
            logger.error(error.message, error)
            return
        }

        try {
            adEvents = AdEvents.createAdEvents(adSession)
        } catch (error: Throwable) {
            logger.error("Unable to create ad events", error)
        }
    }

    // Public

    override fun start(
        context: Context,
        webView: WebView,
    ) {
        createSession(context, webView)
        startSession()
        setupAdEvents()
    }

    override fun finish() {
        adSession?.finish()
        adEvents = null
        adSession = null
    }

    override fun setAdView(view: View?) {
        if (view == null || adSession == null) {
            val error = UpdateAdViewFailureThrowable()
            logger.error(error.message, error)
            return
        }
        adSession?.registerAdView(view)
    }

    override fun injectJS(
        context: Context,
        html: String,
    ): String = jsInjector.injectJS(context, html)

    override fun addFriendlyObstruction(
        view: View,
        purpose: FriendlyObstructionType,
        reason: String?,
    ) {
        try {
            adSession?.addFriendlyObstruction(view, purpose.OMIDFriendlyObstruction(), reason)
        } catch (error: Throwable) {
            logger.error("Failed to add friendly obstruction", error)
        }
    }

    override fun sendAdLoaded() {
        try {
            adEvents?.loaded()
        } catch (error: Throwable) {
            logger.error("Unable to send the ad loaded event", error)
        }
    }

    override fun sendAdImpression() {
        try {
            adEvents?.impressionOccurred()
        } catch (error: Throwable) {
            logger.error("Unable to send the ad impression event", error)
        }
    }
}
