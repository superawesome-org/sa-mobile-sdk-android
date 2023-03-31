package tv.superawesome.demoapp

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.interaction.BumperInteraction
import tv.superawesome.demoapp.interaction.CommonInteraction
import tv.superawesome.demoapp.interaction.ParentalGateInteraction
import tv.superawesome.demoapp.interaction.SettingsInteraction
import tv.superawesome.demoapp.model.TestData
import tv.superawesome.demoapp.util.*
import tv.superawesome.demoapp.util.ColorMatcher.matchesColor
import tv.superawesome.demoapp.util.IntentsHelper.stubIntents
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalled
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalledWithQueryParam

@RunWith(AndroidJUnit4::class)
@SmallTest
class BannerUITest {
    @get:Rule
    var wireMockRule = WireMockRule(wireMockConfig().port(8080), false)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun test_adLoading() {
        val testData = TestData.bannerSuccess
        CommonInteraction.launchActivityWithSuccessStub(testData)
        CommonInteraction.clickItemAt(testData)

        ViewTester()
            .waitForView(withId(R.id.bannerView))
            .perform(waitUntil(matchesColor(TestColors.bannerYellow)))

        CommonInteraction.checkSubtitleContains("${testData.placement} adLoaded")
        CommonInteraction.checkSubtitleContains("${testData.placement} adShown")
    }

    @Test
    fun test_adFailure() {
        val testData = TestData.bannerSuccess
        CommonInteraction.launchActivityWithFailureStub(testData)

        CommonInteraction.clickItemAt(testData)

        CommonInteraction.checkSubtitleContains("${testData.placement} adFailedToLoad")
    }

    @Test
    fun test_adNotFound() {
        val testData = TestData("88001", "not_found.json")
        CommonInteraction.launchActivityWithSuccessStub(testData)

        CommonInteraction.clickItemAt(testData)

        CommonInteraction.checkSubtitleContains("${testData.placement} adEmpty")
    }

    @Test
    fun test_safeAdVisible() {
        // Given
        val testData = TestData.bannerPadlock
        CommonInteraction.launchActivityWithSuccessStub(testData)

        // When
        CommonInteraction.clickItemAt(testData)

        // Then
        CommonInteraction.waitAndCheckSafeAdLogo()
    }

    @Test
    fun test_bumper_enabled_from_settings() {
        // Given bumper page is enabled from settings
        val testData = TestData.bannerSuccess
        stubIntents()
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.enableBumper()
        }
        CommonInteraction.clickItemAt(testData)

        // When ad is clicked
        onView(withId(R.id.bannerView))
            .perform(click())

        // Then bumper page is shown
        BumperInteraction.waitUntilBumper()

        // And view URL is redirected to browser
        Thread.sleep(4500)
        Intents.intended(hasAction(Intent.ACTION_VIEW))
        verifyUrlPathCalled("/click")
    }

    @Test
    fun test_bumper_outside_click_does_not_go_through() {
        // Given bumper page is enabled from settings
        val testData = TestData.bannerSuccess
        stubIntents()
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.enableBumper()
        }
        CommonInteraction.clickItemAt(testData)

        // When ad is clicked
        onView(withId(R.id.bannerView))
            .perform(click())

        BumperInteraction.waitUntilBumper()

        // And If outside the bumper page is clicked
        UiDevice.getInstance(getInstrumentation()).click(0, 100)

        // Then bumper page title is still visible
        BumperInteraction.checkBumperPageIsVisible()
    }

    @Test
    fun test_bumper_enabled_from_api() {
        // Given bumper page is enabled from api
        val testData = TestData("88001", "banner_enabled_success.json")
        CommonInteraction.launchActivityWithSuccessStub(testData)
        CommonInteraction.clickItemAt(testData)

        // When ad is clicked
        onView(withId(R.id.bannerView))
            .perform(click())

        // Then bumper page is shown
        BumperInteraction.waitUntilBumper()
    }

    @Test
    fun test_parental_gate_for_safe_ad_click() {
        val testData = TestData.bannerPadlock
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.enableParentalGate()
        }
        CommonInteraction.clickItemAt(testData)

        CommonInteraction.waitForSafeAdLogoThenClick()

        ParentalGateInteraction.checkVisible()
    }

    @Test
    fun test_adClosed_callback() {
        val testData = TestData.bannerSuccess
        CommonInteraction.launchActivityWithSuccessStub(testData)

        CommonInteraction.clickItemAt(testData)

        ViewTester()
            .waitForView(withId(R.id.bannerView))
            .perform(waitUntil(matchesColor(TestColors.bannerYellow)))

        CommonInteraction.clickItemAt(testData)

        // The banner is closed automatically when a new one is opened
        CommonInteraction.checkSubtitleContains("${testData.placement} adClosed")
    }

    // Events
    @Test
    fun test_banner_impression_events() {
        // Given
        val testData = TestData.bannerSuccess
        CommonInteraction.launchActivityWithSuccessStub(testData)

        CommonInteraction.clickItemAt(testData)

        ViewTester()
            .waitForView(withId(R.id.bannerView))

        // When
        Thread.sleep(2500)

        // Then
        verifyUrlPathCalled("/impression")
        verifyUrlPathCalledWithQueryParam(
            "/event",
            "data",
            ".*viewable_impression.*"
        )
    }

    @Test
    fun test_banner_click_event() {
        // Given
        val testData = TestData.bannerSuccess
        stubIntents()
        CommonInteraction.launchActivityWithSuccessStub(testData)
        CommonInteraction.clickItemAt(testData)

        // When
        ViewTester()
            .waitForView(withId(R.id.bannerView))
            .perform(click())

        // Then
        CommonInteraction.checkSubtitleContains("${testData.placement} adClicked")
        verifyUrlPathCalled("/click")
    }

    @Test
    fun test_parental_gate_success_event() {
        stubIntents()
        openParentalGate()
        ParentalGateInteraction.testSuccess()
        Intents.intended(hasAction(Intent.ACTION_VIEW))
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
        val testData = TestData.bannerSuccess
        stubIntents()
        CommonInteraction.launchActivityWithSuccessStub(testData)
        CommonInteraction.clickItemAt(testData)

        // When ad is clicked
        onView(withId(R.id.bannerView))
            .perform(click())

        // Then view URL is redirected to browser
        Intents.intended(hasAction(Intent.ACTION_VIEW))
        verifyUrlPathCalled("/click")
    }

    private fun openParentalGate() {
        CommonInteraction.launchActivityWithSuccessStub(TestData.bannerPadlock) {
            SettingsInteraction.enableParentalGate()
        }
        CommonInteraction.clickItemAt(TestData.bannerPadlock)

        // When parental gate is shown
        CommonInteraction.waitForSafeAdLogoThenClick()

        // Then parental gate open event is triggered
        ParentalGateInteraction.testOpen()
    }
}