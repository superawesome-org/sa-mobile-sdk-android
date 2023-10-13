package tv.superawesome.sdk.publisher.ui.banner

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.di.testCommonModule
import tv.superawesome.sdk.publisher.models.SAEvent
import tv.superawesome.sdk.publisher.network.datasources.MockServerTest
import tv.superawesome.sdk.publisher.network.enqueueResponse
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class InternalBannerViewTests: MockServerTest(), KoinTest {

    private lateinit var activityController: ActivityController<Activity>
    private lateinit var activity: Activity

    private lateinit var sut: InternalBannerView

    private val dispatcher = StandardTestDispatcher()

    @Before
    override fun setup() {
        super.setup()
        Dispatchers.setMain(dispatcher)
        startKoin { modules(testCommonModule(mockServer)) }
        activityController = Robolectric.buildActivity(Activity::class.java)
        activity = activityController.get()
        sut = InternalBannerView(activity)
    }

    @After
    override fun tearDown() {
        super.tearDown()
        stopKoin()
        Dispatchers.resetMain()
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `load banner loads ad successfully with no options`() = runTest {
        // given
        mockServer.enqueueResponse("mock_ad_response_banner.json", 200)
        val placementId = 1000

        // when
        sut.load(placementId)
        sut.getJob()?.join()

        // then
        assertTrue(sut.getManager().hasAdAvailable(placementId))
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `load banner loads ad successfully with lineItemId and creativeId and no options`() = runTest {
        // given
        mockServer.enqueueResponse("mock_ad_response_banner.json", 200)
        val placementId = 2000

        // when
        sut.load(placementId, 1234, 1234)
        sut.getJob()?.join()

        // then
        assertTrue(sut.getManager().hasAdAvailable(placementId))
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner can be configured with a delegate that is called on successful loading of the ad`() = runTest {
        // given
        mockServer.enqueueResponse("mock_ad_response_banner.json", 200)
        val placementId = 3000
        var event: SAEvent = SAEvent.webSDKReady
        var actualPlacementId = 0
        val listener = SAInterface { p, e ->
            event = e
            actualPlacementId = p
        }

        // when
        sut.configure(placementId, listener) {}
        sut.load(placementId)
        sut.getJob()?.join()

        // then
        assertEquals(placementId, actualPlacementId)
        assertEquals(SAEvent.adLoaded, event)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner will close correctly`() = runTest {
        // given
        mockServer.enqueueResponse("mock_ad_response_banner.json", 200)
        sut.load(1234)
        sut.getJob()?.join()
        sut.play()

        // when
        sut.close()

        // then
        assertNotNull(sut.getController())
        assertTrue(sut.isClosed())
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner hasAdAvailable returns true if ad was loaded`() = runTest {
        // given
        mockServer.enqueueResponse("mock_ad_response_banner.json", 200)
        val placementId = 4000
        sut.load(placementId)
        sut.getJob()?.join()
        // when
        val result = sut.hasAdAvailable()

        // then
        assertTrue(result)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner hasAdAvailable returns false if ad wasn't loaded`() {
        // when
        val result = sut.hasAdAvailable()

        // then
        assertFalse(result)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner isClosed returns true if ad has been closed`() = runTest {
        // given
        mockServer.enqueueResponse("mock_ad_response_banner.json", 200)
        val placementId = 5000
        sut.load(placementId)
        sut.getJob()?.join()
        sut.play()
        val controller = sut.getController()
        controller?.close()

        // when
        val result = sut.isClosed()

        // then
        assertNotNull(controller)
        assertTrue(result)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner isClosed returns false if ad hasn't been closed`() = runTest {
        // given
        mockServer.enqueueResponse("mock_ad_response_banner.json", 200)
        val placementId = 6000
        sut.load(placementId)
        sut.getJob()?.join()
        sut.play()

        // when
        val result = sut.isClosed()

        // then
        assertFalse(result)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner setParentGate sets the config value to true`() {
        // when
        sut.setParentalGate(true)

        // then
        assertTrue(sut.getManager().adConfig.isParentalGateEnabled)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner setParentGate sets the config value to false`() {
        // when
        sut.setParentalGate(false)

        // then
        assertFalse(sut.getManager().adConfig.isParentalGateEnabled)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner enableParentGate sets the config value to true`() {
        // when
        sut.enableParentalGate()

        // then
        assertTrue(sut.getManager().adConfig.isParentalGateEnabled)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner disableParentGate sets the config value to false`() {
        // when
        sut.disableParentalGate()

        // then
        assertFalse(sut.getManager().adConfig.isParentalGateEnabled)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner setBumperPage sets the config value to true`() {
        // given
        val isBumperPageEnabled = true

        // when
        sut.setBumperPage(isBumperPageEnabled)

        // then
        assertTrue(sut.getManager().adConfig.isBumperPageEnabled)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner setBumperPage sets the config value to false`() {
        // given
        val isBumperPageEnabled = false

        // when
        sut.setBumperPage(isBumperPageEnabled)

        // then
        assertFalse(sut.getManager().adConfig.isBumperPageEnabled)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner enableBumperPage sets the config value to true`() {
        // when
        sut.enableBumperPage()

        // then
        assertTrue(sut.getManager().adConfig.isBumperPageEnabled)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner disableBumperPage sets the config value to false`() {
        // when
        sut.disableBumperPage()

        // then
        assertFalse(sut.getManager().adConfig.isBumperPageEnabled)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner setTestMode sets the config value to true`() {
        // given
        val isTestEnabled = true

        // when
        sut.setTestMode(isTestEnabled)

        // then
        assertTrue(sut.getManager().adConfig.testEnabled)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner setTestMode sets the config value to false`() {
        // given
        val isTestEnabled = false

        // when
        sut.setTestMode(isTestEnabled)

        // then
        assertFalse(sut.getManager().adConfig.testEnabled)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner enableTestMode sets the controller value to true`() {
        // when
        sut.enableTestMode()

        // then
        assertTrue(sut.getManager().adConfig.testEnabled)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner disableTestMode sets the controller value to false`() {
        // when
        sut.disableTestMode()

        // then
        assertFalse(sut.getManager().adConfig.testEnabled)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner setColor sets the view background to transparent when true`() {
        // given
        val isTransparent = true

        // when
        sut.setColor(isTransparent)

        // then
        assertEquals(Color.TRANSPARENT, (sut.background as? ColorDrawable)?.color)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner setColor sets the view background to gray when false`() {
        // given
        val isTransparent = false

        // when
        sut.setColor(isTransparent)

        // then
        assertEquals(Constants.backgroundColorGray, (sut.background as? ColorDrawable)?.color)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner setColorTransparent sets the view background to transparent`() {
        // when
        sut.setColorTransparent()

        // then
        assertEquals(Color.TRANSPARENT, (sut.background as? ColorDrawable)?.color)
    }

    @Ignore("Broken on CI due to Android KeyStore not existing")
    @Test
    fun `banner setColorGray sets the view background to gray`() {
        // when
        sut.setColorGray()

        // then
        assertEquals(Constants.backgroundColorGray, (sut.background as? ColorDrawable)?.color)
    }
}
