package tv.superawesome.sdk.publisher.ui.banner

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import tv.superawesome.sdk.publisher.ad.AdController
import tv.superawesome.sdk.publisher.ad.AdManager
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.di.testCommonModule
import tv.superawesome.sdk.publisher.models.SAEvent
import tv.superawesome.sdk.publisher.network.datasources.MockServerTest
import tv.superawesome.sdk.publisher.network.enqueueResponse
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class InternalBannerViewTests: MockServerTest(), KoinTest {

    private lateinit var activityController: ActivityController<Activity>
    private lateinit var activity: Activity

    private lateinit var sut: InternalBannerView

    private val dispatcher = UnconfinedTestDispatcher()
    private val adManager by inject<AdManager>()

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

    @Test
    fun `load banner loads ad successfully with no options`() {
        // given
        val placementId = 1000

        // when
        sut.load(placementId)
        sut.play()

        // then
        assertTrue(adManager.hasAdAvailable(placementId))
    }

    @Test
    fun `load banner loads ad successfully with lineItemId and creativeId and no options`() {
        // given
        val placementId = 2000

        // when
        sut.load(placementId, 1234, 1234)

        // then
        assertTrue(adManager.hasAdAvailable(placementId))
    }

    @Test
    fun `banner can be configured with a delegate that is called on successful loading of the ad`() {
        // given
        val placementId = 3000
        val listener = SAInterface { actualPlacementId, event ->
            // then
            assertEquals(placementId, actualPlacementId)
            assertEquals(SAEvent.adLoaded, event)
        }

        // when
        sut.configure(placementId, listener) {}
        sut.load(placementId)
    }

    @Test
    fun `banner will close correctly`() = runTest {
        // given
        mockServer.enqueueResponse("mock_ad_response_1.json", 200)
        sut.load(1234)
        advanceUntilIdle()
        sut.play()

        // when
        sut.close()

        // then
        assertTrue(sut.isClosed())
    }

    @Test
    fun `banner hasAdAvailable returns what the controller specifies when true`() {
        // given
        val placementId = 4000
        sut.load(placementId)

        // when
        val result = sut.hasAdAvailable()

        // then
        assertTrue(result)
    }

    @Test
    fun `banner hasAdAvailable returns what the controller specifies when false`() {
        // when
        val result = sut.hasAdAvailable()

        // then
        assertFalse(result)
    }

    @Test
    fun `banner isClosed returns what the controller specifies when true`() {
        // given
        val placementId = 5000
        sut.load(placementId)
        val controller = get<AdController> { parametersOf(placementId) }
        controller.close()

        // when
        val result = sut.isClosed()

        // then
        assertTrue(result)
    }

    @Test
    fun `banner isClosed returns what the controller specifies when false`() {
        // given
        val placementId = 6000
        sut.load(placementId)

        // when
        val result = sut.isClosed()

        // then
        assertFalse(result)
    }

    @Test
    fun `banner setParentGate sets the config value to true`() {
        // given
        val isParentGateEnabled = true

        // when
        sut.setParentalGate(isParentGateEnabled)

        // then
        assertTrue(adManager.adConfig.isParentalGateEnabled)
    }

    @Test
    fun `banner setParentGate sets the config value to false`() {
        // given
        val isParentGateEnabled = false

        // when
        sut.setParentalGate(isParentGateEnabled)

        // then
        assertFalse(adManager.adConfig.isParentalGateEnabled)
    }

    @Test
    fun `banner enableParentGate sets the config value to true`() {
        // when
        sut.enableParentalGate()

        // then
        assertTrue(adManager.adConfig.isParentalGateEnabled)
    }

    @Test
    fun `banner disableParentGate sets the config value to false`() {
        // when
        sut.disableParentalGate()

        // then
        assertFalse(adManager.adConfig.isParentalGateEnabled)
    }

    @Test
    fun `banner setBumperPage sets the config value to true`() {
        // given
        val isBumperPageEnabled = true

        // when
        sut.setBumperPage(isBumperPageEnabled)

        // then
        assertTrue(adManager.adConfig.isBumperPageEnabled)
    }

    @Test
    fun `banner setBumperPage sets the config value to false`() {
        // given
        val isBumperPageEnabled = false

        // when
        sut.setBumperPage(isBumperPageEnabled)

        // then
        assertFalse(adManager.adConfig.isBumperPageEnabled)
    }

    @Test
    fun `banner enableBumperPage sets the config value to true`() {
        // when
        sut.enableBumperPage()

        // then
        assertTrue(adManager.adConfig.isBumperPageEnabled)
    }

    @Test
    fun `banner disableBumperPage sets the config value to false`() {
        // when
        sut.disableBumperPage()

        // then
        assertFalse(adManager.adConfig.isBumperPageEnabled)
    }

    @Test
    fun `banner setTestMode sets the config value to true`() {
        // given
        val isTestEnabled = true

        // when
        sut.setTestMode(isTestEnabled)

        // then
        assertTrue(adManager.adConfig.testEnabled)
    }

    @Test
    fun `banner setTestMode sets the config value to false`() {
        // given
        val isTestEnabled = false

        // when
        sut.setTestMode(isTestEnabled)

        // then
        assertFalse(adManager.adConfig.testEnabled)
    }

    @Test
    fun `banner enableTestMode sets the controller value to true`() {
        // when
        sut.enableTestMode()

        // then
        assertTrue(adManager.adConfig.testEnabled)
    }

    @Test
    fun `banner disableTestMode sets the controller value to false`() {
        // when
        sut.disableTestMode()

        // then
        assertFalse(adManager.adConfig.testEnabled)
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
