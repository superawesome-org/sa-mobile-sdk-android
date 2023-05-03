package tv.superawesome.demoapp

import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.FlakyTest
import androidx.test.filters.SmallTest
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.interaction.*
import tv.superawesome.demoapp.interaction.AdInteraction.testAdLoading
import tv.superawesome.demoapp.model.TestData
import tv.superawesome.demoapp.rules.RetryTestRule
import tv.superawesome.demoapp.util.IntentsHelper.stubIntents
import tv.superawesome.demoapp.util.TestColors
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.WireMockHelper.verifyQueryParamContains
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalled
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalledWithQueryParam
import tv.superawesome.demoapp.util.waitUntil
import tv.superawesome.sdk.publisher.SAVideoAd


@RunWith(AndroidJUnit4::class)
@SmallTest
class VideoAdUITest {

    @get:Rule
    var wireMockRule = WireMockRule(wireMockConfig().port(8080), false)

    @get:Rule
    val retryTestRule = RetryTestRule()

    @Before
    fun setup() {
        Intents.init()

        val ads = SAVideoAd::class.java.getDeclaredMethod("clearCache")
        ads.isAccessible = true
        ads.invoke(null)

        wireMockRule.resetAll()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun test_closeAtEndEnabled_closeBeforeEnds_receiveOnlyAdClosedEvent() {
        val testData = TestData.videoDirect
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.closeDelayed()
        }
        CommonInteraction.clickItemAt(testData)

        CommonInteraction.waitForCloseButtonThenClick()

        CommonInteraction.checkSubtitleContains("${testData.placement} adClosed")
        CommonInteraction.checkSubtitleNotContains("${testData.placement} adEnded")
    }

    @Test
    fun test_closeAtEndDisabled_waitVideoEnds_receiveAdClosedEvent() {
        val testData = TestData.videoDirect
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.disableCloseAtEnd()
            SettingsInteraction.closeHidden()
        }
        CommonInteraction.clickItemAt(testData)

        CommonInteraction.waitForCloseButtonThenClick()

        CommonInteraction.checkSubtitleContains("${testData.placement} adClosed")
        CommonInteraction.checkSubtitleContains("${testData.placement} adEnded")
    }

    @Test
    fun test_standard_CloseButtonWithNoDelay() {
        val testData = TestData.videoDirect
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.closeNoDelay()
        }

        CommonInteraction.clickItemAt(testData)
        CommonInteraction.waitForCloseButtonThenClick()

        CommonInteraction.checkSubtitleContains("${testData.placement} adClosed")
    }

    @Test
    fun test_standard_CloseButtonWithDelay() {
        val testData = TestData.videoDirect
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.closeDelayed()
        }
        CommonInteraction.clickItemAt(testData)

        CommonInteraction.waitForCloseButtonWithDelayThenClick()

        CommonInteraction.checkSubtitleContains("${testData.placement} adClosed")
        verifyUrlPathCalled("/event")
    }

    @Test
    fun test_vast_CloseButtonWithNoDelay() {
        val testData = TestData.videoVast
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.closeNoDelay()
        }

        CommonInteraction.clickItemAt(testData)
        CommonInteraction.waitForCloseButtonThenClick()

        CommonInteraction.checkSubtitleContains("${testData.placement} adClosed")
    }

    @Test
    fun test_vast_CloseButtonWithDelay() {
        val testData = TestData.videoVast
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.closeDelayed()
        }

        CommonInteraction.clickItemAt(testData)

        CommonInteraction.waitForCloseButtonWithDelayThenClick()

        CommonInteraction.checkSubtitleContains("${testData.placement} adClosed")
    }

    @Test
    fun test_vpaid_CloseButton() {
        val testData = TestData.videoVpaid
        CommonInteraction.launchActivityWithSuccessStub(testData)

        CommonInteraction.clickItemAt(testData)

        CommonInteraction.waitForCloseButtonThenClick()

        CommonInteraction.checkSubtitleContains("${testData.placement} adClosed")
    }

    @Test
    fun test_auto_close_on_finish() {
        val testData = TestData.videoDirect
        CommonInteraction.launchActivityWithSuccessStub(testData)

        CommonInteraction.clickItemAt(testData)

        ViewTester()
            .waitForView(withId(R.id.subtitleTextView))
            .perform(waitUntil(isDisplayed()))

        CommonInteraction.checkSubtitleContains("${testData.placement} adEnded")

        // Then all of the events are triggered
        verifyQueryParamContains("/video/tracking", "event", "start")
        verifyQueryParamContains("/video/tracking", "event", "creativeView")
        verifyQueryParamContains("/video/tracking", "event", "firstQuartile")
        verifyQueryParamContains("/video/tracking", "event", "midpoint")
        verifyQueryParamContains("/video/tracking", "event", "thirdQuartile")
        verifyQueryParamContains("/video/tracking", "event", "complete")
    }

    @Test
    @FlakyTest
    fun test_vast_adLoading() {
        val testData = TestData.videoVast
        testAdLoading(testData, TestColors.vastYellow)

        CommonInteraction.waitForCloseButtonThenClick()

        CommonInteraction.checkSubtitleContains("${testData.placement} adLoaded")
        CommonInteraction.checkSubtitleContains("${testData.placement} adShown")
    }

    @Test
    fun test_vpaid_adLoading() {
        val testData = TestData.videoVpaid
        testAdLoading(testData, TestColors.vpaidYellow)

        CommonInteraction.waitForCloseButtonThenClick()

        CommonInteraction.checkSubtitleContains("${testData.placement} adLoaded")
    }

    @Test
    fun test_direct_adLoading() {
        val testData = TestData.videoDirect
        testAdLoading(testData, TestColors.vastYellow)

        CommonInteraction.waitForCloseButtonThenClick()

        CommonInteraction.checkSubtitleContains("${testData.placement} adLoaded")
        CommonInteraction.checkSubtitleContains("${testData.placement} adShown")
    }

    @Test
    fun test_adFailure() {
        val testData = TestData.videoDirect
        CommonInteraction.launchActivityWithFailureStub(testData)

        CommonInteraction.clickItemAt(testData)

        CommonInteraction.checkSubtitleContains("${testData.placement} adFailedToLoad")
    }

    @Test
    fun test_adNotFound() {
        val testData = TestData("87969", "not_found.json")
        CommonInteraction.launchActivityWithSuccessStub(testData)

        CommonInteraction.clickItemAt(testData)

        CommonInteraction.checkSubtitleContains("${testData.placement} adEmpty")
    }

    @Test
    fun test_direct_safeAdVisible() {
        CommonInteraction.launchActivityWithSuccessStub(TestData.videoPadlock)
        CommonInteraction.clickItemAt(TestData.videoPadlock)

        CommonInteraction.waitAndCheckSafeAdLogo()
    }

    @Test
    fun test_vast_safeAdVisible() {
        val testData = TestData("88406", "padlock/video_vast_success_padlock_enabled.json")
        CommonInteraction.launchActivityWithSuccessStub(testData)

        CommonInteraction.clickItemAt(testData)

        CommonInteraction.waitAndCheckSafeAdLogo()
    }

    @Test
    fun test_bumper_enabled_from_settings() {
        // Given bumper page is enabled from settings
        val testData = TestData.videoDirect
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.enableBumper()
        }
        CommonInteraction.clickItemAt(testData)

        // When ad is clicked
        CommonInteraction.waitForAdContentThenClick()

        // Then bumper page is shown
        BumperInteraction.waitUntilBumper()

        // And view URL is redirected to browser
        Thread.sleep(4500)
        IntentInteraction.checkVastClickIsOpenInBrowser()
        CommonInteraction.pressDeviceBackButton()

        // When close is tapped and app returns to list view
        CommonInteraction.waitForCloseButtonWithDelayThenClick()
        CommonInteraction.checkAdHasBeenLoadedShownClickedClosed(testData.placement)
    }

    @Test
    fun test_bumper_enabled_from_api() {
        // Given bumper page is enabled from api
        val testData = TestData("87969", "video_direct_enabled_success.json")
        CommonInteraction.launchActivityWithSuccessStub(testData)
        CommonInteraction.clickItemAt(testData)

        // When ad is clicked
        CommonInteraction.waitForAdContentThenClick()

        // Then bumper page is shown
        BumperInteraction.waitUntilBumper()

        // And view URL is redirected to browser
        Thread.sleep(4500)
        IntentInteraction.checkVastClickIsOpenInBrowser()
        CommonInteraction.pressDeviceBackButton()

        // When close is tapped and app returns to list view
        CommonInteraction.waitForCloseButtonWithDelayThenClick()
        CommonInteraction.checkAdHasBeenLoadedShownClickedClosed(testData.placement)
    }

    @Test
    fun test_parental_gate_for_safe_ad_click() {
        val testData = TestData.videoPadlock
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.enableParentalGate()
        }
        CommonInteraction.clickItemAt(testData)

        CommonInteraction.waitForSafeAdLogoThenClick()

        ParentalGateInteraction.checkVisible()
    }

    @Test
    fun test_parental_gate_for_ad_click() {
        val testData = TestData("87969", "padlock/video_direct_success_padlock_enabled.json")
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.enableParentalGate()
        }
        CommonInteraction.clickItemAt(testData)

        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()), click())

        ParentalGateInteraction.checkVisible()
    }

    @Test
    fun test_direct_adAlreadyLoaded_callback() {
        val testData = TestData.videoDirect
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.disablePlay()
        }

        CommonInteraction.clickItemAt(testData)
        CommonInteraction.clickItemAt(testData)

        CommonInteraction.checkSubtitleContains("${testData.placement} adAlreadyLoaded")
    }

    @Test
    fun test_vast_adAlreadyLoaded_callback() {
        val testData = TestData.videoVast
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.disablePlay()
        }

        CommonInteraction.clickItemAt(testData)
        CommonInteraction.clickItemAt(testData)

        CommonInteraction.checkSubtitleContains("${testData.placement} adAlreadyLoaded")
    }

    @Test
    fun test_vpaid_adAlreadyLoaded_callback() {
        val testData = TestData.videoVpaid
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.disablePlay()
        }

        CommonInteraction.clickItemAt(testData)
        CommonInteraction.clickItemAt(testData)

        CommonInteraction.checkSubtitleContains("${testData.placement} adAlreadyLoaded")
    }

    // Events
    @Test
    fun test_direct_ad_impression_events() {
        // Given
        val testData = TestData.videoDirect
        CommonInteraction.launchActivityWithSuccessStub(testData)
        CommonInteraction.clickItemAt(testData)

        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()))

        // When
        Thread.sleep(5000)

        // Then
        verifyUrlPathCalled("/vast/impression")
        verifyUrlPathCalledWithQueryParam(
            "/event",
            "data",
            ".*viewable_impression.*"
        )
    }

    @Test
    fun test_direct_ad_click_event() {
        // Given
        val testData = TestData.videoDirect
        stubIntents()
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.closeNoDelay()
        }
        CommonInteraction.clickItemAt(testData)

        // When
        CommonInteraction.waitForAdContentThenClick()

        // Then
        verifyUrlPathCalled("/vast/click")
        IntentInteraction.checkVastClickIsOpenInBrowser()
        CommonInteraction.pressDeviceBackButton()

        // When close is tapped and app returns to list view
        CommonInteraction.waitForCloseButtonThenClick()
        CommonInteraction.checkAdHasBeenLoadedShownClickedClosed(testData.placement)
    }

    @Test
    fun test_direct_ad_dwell_time() {
        // Given
        CommonInteraction.launchActivityWithSuccessStub(TestData.videoDirect)
        CommonInteraction.clickItemAt(TestData.videoDirect)

        // When
        Thread.sleep(3000)

        // Then
        verifyUrlPathCalledWithQueryParam(
            "/event",
            "type",
            ".*viewTime.*"
        )
    }

    @Test
    fun test_vast_ad_impression_events() {
        // Given
        CommonInteraction.launchActivityWithSuccessStub(TestData.videoVast)
        CommonInteraction.clickItemAt(TestData.videoVast)

        // When
        Thread.sleep(5000)

        // Then
        verifyUrlPathCalled("/vast/impression")
        verifyUrlPathCalledWithQueryParam(
            "/event",
            "data",
            ".*viewable_impression.*"
        )
    }

    @Test
    fun test_vast_ad_click_event() {
        // Given
        val testData = TestData.videoVast
        stubIntents()
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.closeNoDelay()
        }
        CommonInteraction.clickItemAt(testData)

        // When
        CommonInteraction.waitForAdContentThenClick()

        // Then
        verifyUrlPathCalled("/vast/click")
        IntentInteraction.checkVastClickIsOpenInBrowser()
        CommonInteraction.pressDeviceBackButton()

        // When close is tapped and app returns to list view
        CommonInteraction.waitForCloseButtonThenClick()
        CommonInteraction.checkAdHasBeenLoadedShownClickedClosed(testData.placement)
    }

    @Test
    fun test_vast_ad_dwell_time() {
        // Given
        CommonInteraction.launchActivityWithSuccessStub(TestData.videoVast)
        CommonInteraction.clickItemAt(TestData.videoVast)

        // When
        Thread.sleep(3000)

        // Then
        verifyUrlPathCalledWithQueryParam(
            "/event",
            "type",
            ".*viewTime.*"
        )
    }

    @Test
    fun test_parental_gate_success_event() {
        openParentalGate()
        ParentalGateInteraction.testSuccess()
        IntentInteraction.checkVastClickIsOpenInBrowser()
        CommonInteraction.pressDeviceBackButton()

        // When close is tapped and app returns to list view
        CommonInteraction.waitForCloseButtonWithDelayThenClick()
        CommonInteraction.checkAdHasBeenLoadedShownClickedClosed("87969")
    }

    @Test
    fun test_parental_gate_close_event() {
        openParentalGate()
        ParentalGateInteraction.testClose()
    }

    @Test
    fun test_parental_gate_failure_event() {
        openParentalGate()
        ParentalGateInteraction.testFailure()
    }

    @Test
    fun test_external_webpage_opening_on_click() {
        // Given
        stubIntents()
        CommonInteraction.launchActivityWithSuccessStub(TestData.videoDirect) {
            SettingsInteraction.closeNoDelay()
        }
        CommonInteraction.clickItemAt(TestData.videoDirect)

        // When ad is clicked
        CommonInteraction.waitForAdContentThenClick()

        // Then view URL is redirected to browser
        IntentInteraction.checkVastClickIsOpenInBrowser()

        // Then go back and check ad is loaded
        CommonInteraction.pressDeviceBackButton()
        CommonInteraction.waitForCloseButtonThenClick()
        CommonInteraction.checkAdHasBeenLoadedShownClickedClosed("87969")
    }

    @Test
    fun test_vast_click_event() {
        // Given CPI Vast Ad
        val testData = TestData("88406", "video_vast_cpi_success.json")
        stubIntents()
        CommonInteraction.launchActivityWithSuccessStub(testData)
        CommonInteraction.clickItemAt(testData)

        // When ad is clicked
        CommonInteraction.waitForAdContentThenClick()

        // Then view URL is redirected to browser
        // NOTE: this doesn't open the browser, but fires the event.
        verifyUrlPathCalled("/vast/clickthrough")
    }

    @Test
    fun test_iv_video_warn_dialog_press_close() {
        // Given VPAID Ad
        val testData = TestData.videoVpaidPJ

        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.enableVideoWarnDialog()
        }

        CommonInteraction.clickItemAt(testData)

        // When close is clicked
        CommonInteraction.waitForCloseButtonThenClick()

        VideoWarnInteraction.checkVisible()

        VideoWarnInteraction.clickClose()

        // Then
        CommonInteraction.checkSubtitleContains("${testData.placement} adLoaded")
        CommonInteraction.checkSubtitleContains("${testData.placement} adShown")
        CommonInteraction.checkSubtitleContains("${testData.placement} adPaused")
        CommonInteraction.checkSubtitleContains("${testData.placement} adClosed")
    }

    @Test
    fun test_iv_video_warn_dialog_press_resume() {
        // Given VPAID Ad
        val testData = TestData.videoVpaidPJ

        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.enableVideoWarnDialog()
            SettingsInteraction.disableCloseAtEnd()
        }

        CommonInteraction.clickItemAt(testData)

        // When close is clicked
        CommonInteraction.waitForCloseButtonThenClick()

        VideoWarnInteraction.checkVisible()

        VideoWarnInteraction.clickResume()

        Thread.sleep(20000)

        CommonInteraction.waitForCloseButtonThenClick()

        // Then
        CommonInteraction.checkSubtitleContains("${testData.placement} adEnded")
        CommonInteraction.checkSubtitleContains("${testData.placement} adClosed")
    }

    @Test
    fun test_direct_video_warn_dialog_press_close() {
        // Given a direct Ad
        val testData = TestData.videoDirect

        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.enableVideoWarnDialog()
        }

        CommonInteraction.clickItemAt(testData)

        // When close is clicked
        CommonInteraction.waitForCloseButtonThenClick()

        VideoWarnInteraction.checkVisible()

        VideoWarnInteraction.clickClose()

        // Then check correct events were called
        CommonInteraction.checkSubtitleContains("${testData.placement} adLoaded")
        CommonInteraction.checkSubtitleContains("${testData.placement} adShown")
        CommonInteraction.checkSubtitleContains("${testData.placement} adClosed")
    }

    @Test
    fun test_direct_video_warn_dialog_press_resume() {
        // Given a direct Ad
        val testData = TestData.videoDirect

        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.enableVideoWarnDialog()
            SettingsInteraction.disableCloseAtEnd()
        }

        CommonInteraction.clickItemAt(testData)

        // When close is clicked
        CommonInteraction.waitForCloseButtonThenClick()

        VideoWarnInteraction.checkVisible()

        VideoWarnInteraction.clickResume()

        Thread.sleep(20000)

        CommonInteraction.waitForCloseButtonThenClick()

        // Then
        CommonInteraction.checkSubtitleContains("${testData.placement} adEnded")
        CommonInteraction.checkSubtitleContains("${testData.placement} adClosed")
    }

    private fun openParentalGate() {
        CommonInteraction.launchActivityWithSuccessStub(TestData.videoPadlock) {
            SettingsInteraction.enableParentalGate()
        }
        CommonInteraction.clickItemAt(TestData.videoPadlock)
        CommonInteraction.waitForAdContentThenClick()

        // Then parental gate open event is triggered
        ParentalGateInteraction.testOpen()
    }
}
