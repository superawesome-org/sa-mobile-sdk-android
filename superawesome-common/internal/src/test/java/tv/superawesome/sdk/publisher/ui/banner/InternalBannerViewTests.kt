package tv.superawesome.sdk.publisher.ui.banner

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import tv.superawesome.sdk.publisher.components.ImageProviderType
import tv.superawesome.sdk.publisher.components.Logger
import tv.superawesome.sdk.publisher.components.TimeProviderType
import tv.superawesome.sdk.publisher.models.Ad
import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.models.Creative
import tv.superawesome.sdk.publisher.models.CreativeDetail
import tv.superawesome.sdk.publisher.models.CreativeFormatType
import tv.superawesome.sdk.publisher.models.DefaultAdRequest
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.ui.common.AdControllerType
import tv.superawesome.sdk.publisher.ui.common.Config
import tv.superawesome.sdk.publisher.ui.common.ViewableDetectorType
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class InternalBannerViewTests: KoinTest {

    private lateinit var activityController: ActivityController<Activity>
    private lateinit var activity: Activity

    private lateinit var sut: InternalBannerView

    private val placementId = 1234
    private val lineItemId = 123
    private val creativeId = 12
    private val adRequest = DefaultAdRequest(
        test = false,
        pos = AdRequest.Position.AboveTheFold.value,
        skip = AdRequest.Skip.No.value,
        playbackMethod = DefaultAdRequest.PlaybackSoundOnScreen,
        startDelay = AdRequest.StartDelay.PreRoll.value,
        install = AdRequest.FullScreen.Off.value,
        w = 0,
        h = 0,
        options = null,
    )

    private val controller = spyk<AdControllerType>().apply {
        every { config } returns Config()
        every { play(placementId) } returns AdResponse(
            placementId = placementId,
            ad = Ad(
                campaignType = 0,
                showPadlock = false,
                lineItemId = 123,
                test = false,
                creative = Creative(
                    id = 1234590,
                    name = null,
                    format = CreativeFormatType.ImageWithLink,
                    clickUrl = null,
                    details = CreativeDetail(
                        url = null,
                        image = null,
                        video = null,
                        placementFormat = "",
                        tag = null,
                        width = 0,
                        height = 0,
                        duration = 0,
                        vast = null,
                    ),
                    bumper = false,
                    referral = null,
                    isKSF = false,
                ),
                isVpaid = false,
                random = "",
            ),
            requestOptions = null,
            html = "<html><p></p></html>",
            vast = null,
            baseUrl = "https://someurl.com",
            filePath = null,
            referral = null,
        )
    }
    private val imageProvider = mockk<ImageProviderType>(relaxed = true)
    private val saLogger = spyk<Logger>()
    private val timeProvider = mockk<TimeProviderType>(relaxed = true)
    private val viewableDetector = spyk<ViewableDetectorType>()

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    single { controller }
                    single { imageProvider }
                    single { saLogger }
                    single { timeProvider }
                    single { viewableDetector }
                }
            )
        }
        activityController = Robolectric.buildActivity(Activity::class.java)
        activity = activityController.get()
        sut = InternalBannerView(activity)
    }

    @After
    fun cleanup() {
        stopKoin()
    }

    @Test
    fun `load banner loads ad successfully with no options`() {
        // given
        val placementId = 1234
        val adRequest = DefaultAdRequest(
            test = false,
            pos = AdRequest.Position.AboveTheFold.value,
            skip = AdRequest.Skip.No.value,
            playbackMethod = DefaultAdRequest.PlaybackSoundOnScreen,
            startDelay = AdRequest.StartDelay.PreRoll.value,
            install = AdRequest.FullScreen.Off.value,
            w = 0,
            h = 0,
            options = null,
        )

        // when
        sut.load(placementId)

        // then
        verify(exactly = 1) {
            saLogger.info("load(1234)")
            controller.config
            controller.load(placementId, adRequest)
        }
        confirmVerified(saLogger)
        confirmVerified(controller)
    }

    @Test
    fun `load banner loads ad successfully with lineItemId and creativeId and no options`() {
        // when
        sut.load(placementId, lineItemId, creativeId)

        // then
        verify(exactly = 1) {
            saLogger.info("load(1234, 123, 12)")
            controller.config
            controller.load(placementId, lineItemId, creativeId, adRequest)
        }
        confirmVerified(saLogger)
        confirmVerified(controller)
    }

    @Test
    fun `play banner is successful with showPadlockIfNeeded false`() {
        // given
        every { controller.shouldShowPadlock } returns false

        // when
        sut.load(placementId)
        sut.play()

        // then
        verify(exactly = 1) {
            saLogger.info("load(1234)")
            controller.load(placementId, adRequest)
            controller.config
            saLogger.info("play(1234)")
            controller.play(placementId)
            controller.shouldShowPadlock
        }
        verify(exactly = 0) {
            controller.adFailedToShow()
        }
        confirmVerified(saLogger)
        confirmVerified(controller)
        confirmVerified(viewableDetector)
    }

    @Test
    fun `play banner is successful with showPadlockIfNeeded true`() {
        // given
        every { controller.shouldShowPadlock } returns true

        // when
        sut.load(placementId)
        sut.play()

        // then
        verify(exactly = 1) {
            saLogger.info("load(1234)")
            controller.load(placementId, adRequest)
            controller.config
            saLogger.info("play(1234)")
            controller.play(placementId)
            controller.shouldShowPadlock
        }
        verify(exactly = 0) {
            controller.adFailedToShow()
        }
        confirmVerified(saLogger)
        confirmVerified(controller)
        confirmVerified(viewableDetector)
    }

    @Test
    fun `banner can be configured with a delegate that is called on successful loading of the ad`() {
        // given
        val delegate = spyk<SAInterface>()
        every { controller.shouldShowPadlock } returns true

        // when
        sut.configure(placementId, delegate, hasBeenVisible = {})
        sut.load(placementId)
        sut.play()

        // then
        verify(exactly = 1) {
            controller.delegate = delegate
            saLogger.info("load(1234)")
            controller.load(placementId, adRequest)
            controller.config
            saLogger.info("play(1234)")
            controller.play(placementId)
            controller.shouldShowPadlock
        }
        verify(exactly = 0) {
            controller.adFailedToShow()
        }
        confirmVerified(saLogger)
        confirmVerified(controller)
        confirmVerified(viewableDetector)
    }

    @Test
    fun `banner will clear itself if loading an ad for the second time`() {
        // given
        every { controller.shouldShowPadlock } returns true

        // when
        sut.load(placementId)
        sut.play()
        sut.load(placementId)
        sut.play()

        // then
        verify(exactly = 1) {
            controller.close()
            viewableDetector.cancel()
        }
        verify(exactly = 2) {
            saLogger.info("load(1234)")
            controller.load(placementId, adRequest)
            controller.config
            saLogger.info("play(1234)")
            controller.play(placementId)
            controller.shouldShowPadlock
        }
        verify(exactly = 0) {
            controller.adFailedToShow()
        }
        confirmVerified(saLogger)
        confirmVerified(controller)
        confirmVerified(viewableDetector)
    }

    @Test
    fun `banner will close correctly`() {
        // when
        sut.close()

        // then
        verify(exactly = 1) {
            controller.close()
            viewableDetector.cancel()
        }
        confirmVerified(controller)
        confirmVerified(viewableDetector)
    }

    @Test
    fun `banner hasAdAvailable returns what the controller specifies when true`() {
        // given
        every { controller.hasAdAvailable(any()) } returns true

        // when
        val result = sut.hasAdAvailable()

        // then
        assertTrue(result)
    }

    @Test
    fun `banner hasAdAvailable returns what the controller specifies when false`() {
        // given
        every { controller.hasAdAvailable(any()) } returns false

        // when
        val result = sut.hasAdAvailable()

        // then
        assertFalse(result)
    }

    @Test
    fun `banner isClosed returns what the controller specifies when true`() {
        // given
        every { controller.closed } returns true

        // when
        val result = sut.isClosed()

        // then
        assertTrue(result)
    }

    @Test
    fun `banner isClosed returns what the controller specifies when false`() {
        // given
        every { controller.closed } returns false

        // when
        val result = sut.isClosed()

        // then
        assertFalse(result)
    }

    @Test
    fun `banner setParentGate sets the controller value to true`() {
        // given
        val isParentGateEnabled = true

        // when
        sut.setParentalGate(isParentGateEnabled)

        // then
        assertTrue(controller.config.isParentalGateEnabled)
    }

    @Test
    fun `banner setParentGate sets the controller value to false`() {
        // given
        val isParentGateEnabled = false

        // when
        sut.setParentalGate(isParentGateEnabled)

        // then
        assertFalse(controller.config.isParentalGateEnabled)
    }

    @Test
    fun `banner enableParentGate sets the controller value to true`() {
        // when
        sut.enableParentalGate()

        // then
        assertTrue(controller.config.isParentalGateEnabled)
    }

    @Test
    fun `banner disableParentGate sets the controller value to false`() {
        // when
        sut.disableParentalGate()

        // then
        assertFalse(controller.config.isParentalGateEnabled)
    }

    @Test
    fun `banner setBumperPage sets the controller value to true`() {
        // given
        val isBumperPageEnabled = true

        // when
        sut.setBumperPage(isBumperPageEnabled)

        // then
        assertTrue(controller.config.isBumperPageEnabled)
    }

    @Test
    fun `banner setBumperPage sets the controller value to false`() {
        // given
        val isBumperPageEnabled = false

        // when
        sut.setBumperPage(isBumperPageEnabled)

        // then
        assertFalse(controller.config.isBumperPageEnabled)
    }

    @Test
    fun `banner enableBumperPage sets the controller value to true`() {
        // when
        sut.enableBumperPage()

        // then
        assertTrue(controller.config.isBumperPageEnabled)
    }

    @Test
    fun `banner disableBumperPage sets the controller value to false`() {
        // when
        sut.disableBumperPage()

        // then
        assertFalse(controller.config.isBumperPageEnabled)
    }

    @Test
    fun `banner setTestMode sets the controller value to true`() {
        // given
        val isTestEnabled = true

        // when
        sut.setTestMode(isTestEnabled)

        // then
        assertTrue(controller.config.testEnabled)
    }

    @Test
    fun `banner setTestMode sets the controller value to false`() {
        // given
        val isTestEnabled = false

        // when
        sut.setTestMode(isTestEnabled)

        // then
        assertFalse(controller.config.testEnabled)
    }

    @Test
    fun `banner enableTestMode sets the controller value to true`() {
        // when
        sut.enableTestMode()

        // then
        assertTrue(controller.config.testEnabled)
    }

    @Test
    fun `banner disableTestMode sets the controller value to false`() {
        // when
        sut.disableTestMode()

        // then
        assertFalse(controller.config.testEnabled)
    }

    @Test
    fun `banner setColor sets the view background to transparent when true`() {
        // given
        val isTransparent = true

        // when
        sut.setColor(isTransparent)

        // then
        assertEquals(Color.TRANSPARENT, (sut.background as? ColorDrawable)?.color)
    }

    @Test
    fun `banner setColor sets the view background to gray when false`() {
        // given
        val isTransparent = false

        // when
        sut.setColor(isTransparent)

        // then
        assertEquals(Constants.backgroundColorGray, (sut.background as? ColorDrawable)?.color)
    }

    @Test
    fun `banner setColorTransparent sets the view background to transparent`() {
        // when
        sut.setColorTransparent()

        // then
        assertEquals(Color.TRANSPARENT, (sut.background as? ColorDrawable)?.color)
    }

    @Test
    fun `banner setColorGray sets the view background to gray`() {
        // when
        sut.setColorGray()

        // then
        assertEquals(Constants.backgroundColorGray, (sut.background as? ColorDrawable)?.color)
    }
}
