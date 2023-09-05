package tv.superawesome.sdk.publisher.common.openmeasurement

import android.view.View
import android.webkit.WebView
import com.iab.omid.library.superawesome.adsession.AdEvents
import com.iab.omid.library.superawesome.adsession.AdSession
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import tv.superawesome.sdk.publisher.common.components.Logger
import kotlin.test.assertNotNull

class OpenMeasurementSessionManagerTests {

    private val argumentException = IllegalArgumentException("An error")
    private val stateException = IllegalStateException("An error")
    private val logger = spyk<Logger>()
    private val adUrl = "https://wwww.some.ad.com"
    private val webView = mockk<WebView>(relaxed = true) {
        every { url } returns adUrl
    }
    private val session = spyk<AdSession>()
    private val adEvents = mockk<AdEvents>(relaxed = true)
    private val sessionFactory = spyk<OpenMeasurementAdSessionFactoryType>()

    private val manager = OpenMeasurementSessionManager(
        sessionFactory = sessionFactory,
        logger = logger,
    )

    @Before
    fun setup() {
        mockkStatic(AdEvents::class)
        every { AdEvents.createAdEvents(any()) } returns adEvents
        every { sessionFactory.getHtmlAdSession(any(), any()) } returns session
    }

    @Test
    fun `setup successfully creates a session`() {
        // when
        manager.setup(webView = webView)

        // then
        verify(exactly = 1) { sessionFactory.getHtmlAdSession(webView, null) }
        confirmVerified(sessionFactory)
        verify(exactly = 0) { logger.error(any(), any()) }
        confirmVerified(logger)
    }

    @Test
    fun `setup fails to create a session with IllegalArgumentException`() {
        sessionFactoryFailsWithException(exception = argumentException)
    }

    @Test
    fun `setup fails to create a session with IllegalStateException`() {
        sessionFactoryFailsWithException(exception = stateException)
    }

    private fun sessionFactoryFailsWithException(exception: Exception) {
        // given
        every { sessionFactory.getHtmlAdSession(any(), any()) } throws exception

        // when
        manager.setup(webView = webView)

        // then
        verify(exactly = 1) { sessionFactory.getHtmlAdSession(webView, null) }
        confirmVerified(sessionFactory)
        verify(exactly = 1) {
            logger.error(
                "Unable to create the ad session error: An error",
                ofType(OpenMeasurementError.AdSessionCreationFailure::class),
            )
        }
        confirmVerified(logger)
    }

    @Test
    fun `start successfully starts a session and creates ad events`() {
        // when
        manager.setup(webView = webView)
        manager.start()

        // then
        verify(exactly = 1) { session.start() }
        confirmVerified(session)
        verify(exactly = 1) { sessionFactory.getHtmlAdSession(webView, null) }
        confirmVerified(sessionFactory)
        verify(exactly = 1) { AdEvents.createAdEvents(session) }
        verify(exactly = 0) { logger.error(any(), any()) }
    }

    @Test
    fun `start fails to start the session if it does not exist`() {
        // when
        manager.start()

        // then
        verify(exactly = 2) {
            logger.error(
                "No ad session available",
                ofType(OpenMeasurementError.AdSessionUnavailableFailure::class),
            )
        }
        confirmVerified(logger)
    }

    @Test
    fun `start fails to start the session due to an IllegalArgumentException`() {
        startSessionFailsWithAnException(exception = argumentException)
    }

    @Test
    fun `start fails to start the session due to an IllegalStateException`() {
        startSessionFailsWithAnException(exception = stateException)
    }

    private fun startSessionFailsWithAnException(exception: Exception) {
        // given
        every { session.start() } throws exception

        // when
        manager.setup(webView = webView)
        manager.start()

        // then
        verify(exactly = 1) {
            logger.error(
                "Unable to start the ad session error: An error",
                ofType(OpenMeasurementError.AdSessionStartFailure::class),
            )
        }
        confirmVerified(logger)
    }

    @Test
    fun `start succeeds in starting the session, but fails to create events due to an IllegalArgumentException`() {
        createAdsFailsWithException(
            exception = argumentException,
            exceptionMessage = "Unable to create ad events error: An error",
        )
    }

    @Test
    fun `start succeeds in starting the session, but fails to create events due to an IllegalStateException`() {
        createAdsFailsWithException(
            exception = stateException,
            exceptionMessage = "Unable to create ad events error: An error",
        )
    }

    private fun createAdsFailsWithException(exception: Exception, exceptionMessage: String) {
        // given
        every { session.start() } returns Unit
        every { AdEvents.createAdEvents(session) } throws exception

        // when
        manager.setup(webView = webView)
        manager.start()

        // then
        verify(exactly = 1) { session.start() }
        confirmVerified(session)
        verify(exactly = 1) { sessionFactory.getHtmlAdSession(webView, null) }
        confirmVerified(sessionFactory)
        verify {
            logger.error(exceptionMessage, exception)
        }
        confirmVerified(logger)
    }

    @Test
    fun `finish succeeds to finish session if it exists`() {
        // when
        manager.setup(webView = webView)
        manager.start()

        // then
        verify { session.start() }
        confirmVerified(session)

        // when
        manager.finish()

        // then
        verify(exactly = 1) { session.finish() }
    }

    @Test
    fun `finish does nothing to session if it does not exist`() {
        // when
        manager.finish()

        // then
        verify(exactly = 0) { session.finish() }
    }

    @Test
    fun `setAdView sets the advert view on the session as expected`() {
        // when
        manager.setup(webView = webView)
        manager.setAdView(webView)

        // then
        verify(exactly = 0) { logger.error(any(), any()) }
        verify(exactly = 1) { session.registerAdView(webView) }
        confirmVerified(logger)
        confirmVerified(session)
    }

    @Test
    fun `setAdView logs an error when session is null`() {
        // when
        manager.setAdView(webView)

        // then
        verify(exactly = 1) {
            logger.error(
                "Unable to update the ad view",
                ofType(OpenMeasurementError.UpdateAdViewFailure::class),
            )
        }
        verify(exactly = 0) { session.registerAdView(webView) }
        confirmVerified(logger)
        confirmVerified(session)
    }

    @Test
    fun `setAdView logs an error when view is null`() {
        // when
        manager.setAdView(null)

        // then
        verify(exactly = 1) {
            logger.error(
                "Unable to update the ad view",
                ofType(OpenMeasurementError.UpdateAdViewFailure::class),
            )
        }
        verify(exactly = 0) { session.registerAdView(null) }
        confirmVerified(logger)
        confirmVerified(session)
    }

    @Test
    fun `addFriendlyObstruction correctly adds view to session`() {
        // given
        val view = mockk<View>(relaxed = true)
        val purpose = FriendlyObstructionType.CloseAd
        val reason: String? = null

        // when
        manager.setup(webView)
        manager.addFriendlyObstruction(view, purpose, reason)

        // then
        verify(exactly = 1) {
            session.addFriendlyObstruction(view, purpose.omidFriendlyObstruction(), reason)
        }
        verify(exactly = 0) { logger.error(any(), any()) }
        confirmVerified(session)
        confirmVerified(logger)
    }

    @Test
    fun `addFriendlyObstruction throws error when adding view to session`() {
        // given
        val view = mockk<View>(relaxed = true)
        val purpose = FriendlyObstructionType.CloseAd
        val reason: String? = null

        every { session.addFriendlyObstruction(any(), any(), any()) } throws argumentException

        // when
        manager.setup(webView)
        manager.addFriendlyObstruction(view, purpose, reason)

        // then
        verify(exactly = 1) {
            session.addFriendlyObstruction(view, purpose.omidFriendlyObstruction(), reason)
        }
        verify(exactly = 1) {
            logger.error("Failed to add friendly obstruction error: An error", argumentException)
        }
        confirmVerified(session)
        confirmVerified(logger)
    }

    @Test
    fun `addFriendlyObstruction returns if ad session does not exist`() {
        // given
        val view = mockk<View>(relaxed = true)
        val purpose = FriendlyObstructionType.CloseAd
        val reason: String? = null

        // when
        manager.addFriendlyObstruction(view, purpose, reason)

        // then
        verify(exactly = 0) {
            session.addFriendlyObstruction(view, purpose.omidFriendlyObstruction(), reason)
        }
        verify(exactly = 1) {
            logger.error(
                "No ad session available",
                ofType(OpenMeasurementError.AdSessionUnavailableFailure::class),
            )
        }
        confirmVerified(session)
        confirmVerified(logger)
    }

    @Test
    fun `sendAdLoaded sends ad loaded event successfully`() {
        // when
        manager.setup(webView)
        manager.start()
        manager.sendAdLoaded()

        // then
        verify(exactly = 1) { adEvents.loaded() }
        verify(exactly = 0) { logger.error(any(), any()) }
        confirmVerified(adEvents)
        confirmVerified(logger)
    }

    @Test
    fun `sendAdLoaded logs error if adEvents is null`() {
        // when
        manager.sendAdLoaded()

        // then
        verify(exactly = 0) { adEvents.loaded() }
        verify(exactly = 1) {
            logger.error(
                "Unable to send the ad loaded event as ad events is null",
                null,
            )
        }
        confirmVerified(adEvents)
        confirmVerified(logger)
    }

    @Test
    fun `sendAdLoaded logs error if adEvents throws`() {
        // given
        every { adEvents.loaded() } throws stateException

        // when
        manager.setup(webView)
        manager.start()
        manager.sendAdLoaded()

        // then
        verify(exactly = 1) { adEvents.loaded() }
        verify(exactly = 1) {
            logger.error("Unable to send the ad loaded event error: An error", stateException)
        }
        confirmVerified(adEvents)
        confirmVerified(logger)
    }

    @Test
    fun `sendAdImpression sends ad loaded event successfully`() {
        // when
        manager.setup(webView)
        manager.start()
        manager.sendAdImpression()

        // then
        verify(exactly = 1) { adEvents.impressionOccurred() }
        verify(exactly = 0) { logger.error(any(), any()) }
        confirmVerified(adEvents)
        confirmVerified(logger)
    }

    @Test
    fun `sendAdImpression logs error if adEvents is null`() {
        // when
        manager.sendAdImpression()

        // then
        verify(exactly = 0) { adEvents.impressionOccurred() }
        verify(exactly = 1) {
            logger.error(
                "Unable to send the ad impression event as ad events is null",
                null,
            )
        }
        confirmVerified(adEvents)
        confirmVerified(logger)
    }

    @Test
    fun `sendAdImpression logs error if adEvents throws IllegalStateException`() {
        // given
        every { adEvents.impressionOccurred() } throws stateException

        // when
        manager.setup(webView)
        manager.start()
        manager.sendAdImpression()

        // then
        verify(exactly = 1) { adEvents.impressionOccurred() }
        verify(exactly = 1) {
            logger.error("Unable to send the ad impression event error: An error", stateException)
        }
        confirmVerified(adEvents)
        confirmVerified(logger)
    }
}
