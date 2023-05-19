package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import android.view.View
import android.webkit.WebView
import com.iab.omid.library.superawesome.adsession.AdEvents
import com.iab.omid.library.superawesome.adsession.AdSession
import com.iab.omid.library.superawesome.adsession.FriendlyObstructionPurpose
import tv.superawesome.sdk.publisher.common.components.Logger

internal interface OMSessionManagerType {
    fun setUpSessionWithWebView(context: Context, webView: WebView)
    fun sendLoadEvent()
    fun sendImpressionEvent()
    fun addCloseButton(view: View?)
    fun addOtherFABView(view: View?)
    fun cleanUp()
}

internal class OMSessionManager(
    private val sessionBuilder: OMAdSessionBuilderType,
    private val logger: Logger,
): OMSessionManagerType {
    private var adSession: AdSession? = null
    private var adEvents: AdEvents? = null

    override fun setUpSessionWithWebView(context: Context, webView: WebView) {
        try {
            adSession = sessionBuilder.getHtmlAdSession(
                context,
                webView,
                null,
            )
            adSession?.start()
        } catch (error: IllegalArgumentException) {
            logger.error("Failed to setup ad session for open measurement", error)
            return
        }
        try {
            adEvents = AdEvents.createAdEvents(adSession)
            adEvents?.loaded()
        } catch (error: IllegalArgumentException) {
            logger.error("Failed to setup ad events for open measurement", error)
            return
        }
    }

    override fun sendLoadEvent() {
        try {
            adEvents?.impressionOccurred()
        } catch (error: Throwable) {
            logger.error("Unable to log Open Measurement impression", error)
        }
    }

    override fun sendImpressionEvent() {
        try {
            adEvents?.impressionOccurred()
        } catch (error: Throwable) {
            logger.error("Unable to log Open Measurement impression", error)
        }
    }

    override fun addCloseButton(view: View?) {
        view?.let {
            adSession?.addFriendlyObstruction(it, FriendlyObstructionPurpose.CLOSE_AD, null)
        }
    }

    override fun addOtherFABView(view: View?) {
        view?.let {
            adSession?.addFriendlyObstruction(it, FriendlyObstructionPurpose.OTHER, null)
        }
    }

    override fun cleanUp() {
        adSession?.finish()
        adEvents = null
        adSession = null
    }
}
