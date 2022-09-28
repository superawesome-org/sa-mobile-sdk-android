package tv.superawesome.demoapp

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
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
import tv.superawesome.demoapp.util.ColorMatcher.matchesColor
import tv.superawesome.demoapp.util.TestColors
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.IntentsHelper.stubIntents
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalled
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalledWithQueryParam
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

@RunWith(AndroidJUnit4::class)
@SmallTest
class BannerUITest {
    @get:Rule
    var wireMockRule = WireMockRule(8080)

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
        val placement = "88001"
        CommonInteraction.launchActivityWithSuccessStub(placement, "banner_success.json")

        CommonInteraction.clickItemAt(2)

        ViewTester()
            .waitForView(withId(R.id.bannerView))
            .perform(waitUntil(matchesColor(TestColors.yellow)))

        CommonInteraction.checkSubtitleContains("$placement adLoaded")
        CommonInteraction.checkSubtitleContains("$placement adShown")
    }

    @Test
    fun test_adFailure() {
        val placement = "88001"
        CommonInteraction.launchActivityWithFailureStub(placement)

        CommonInteraction.clickItemAt(2)

        CommonInteraction.checkSubtitleContains("$placement adFailedToLoad")
    }

    @Test
    fun test_adNotFound() {
        val placement = "88001"
        CommonInteraction.launchActivityWithSuccessStub(placement, "not_found.json")

        CommonInteraction.clickItemAt(2)

        CommonInteraction.checkSubtitleContains("$placement adEmpty")
    }

    @Test
    fun test_safeAdVisible() {
        val placement = "88001"
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "padlock/banner_success_padlock_enabled.json"
        )

        CommonInteraction.clickItemAt(2)

        ViewTester()
            .waitForView(withContentDescription("Safe Ad Logo"))
            .check(isVisible())
    }

    @Test
    fun test_bumper_enabled_from_settings() {
        // Given bumper page is enabled from settings
        val placement = "88001"
        CommonInteraction.launchActivityWithSuccessStub(placement, "banner_success.json") {
            SettingsInteraction.enableBumper()
        }
        CommonInteraction.clickItemAt(2)

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
    fun test_bumper_enabled_from_api() {
        // Given bumper page is enabled from api
        val placement = "88001"
        CommonInteraction.launchActivityWithSuccessStub(placement, "banner_enabled_success.json")
        CommonInteraction.clickItemAt(2)

        // When ad is clicked
        onView(withId(R.id.bannerView))
            .perform(click())

        // Then bumper page is shown
        BumperInteraction.waitUntilBumper()
    }

    @Test
    fun test_parental_gate_for_safe_ad_click() {
        val placement = "88001"
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "padlock/banner_success_padlock_enabled.json"
        ) {
            SettingsInteraction.enableParentalGate()
        }
        CommonInteraction.clickItemAt(2)

        ViewTester()
            .waitForView(withContentDescription("Safe Ad Logo"))
            .check(isVisible())
            .perform(click())

        onView(withText("Parental Gate"))
            .check(isVisible())
    }

    @Test
    fun test_adClosed_callback() {
        val placement = "88001"
        CommonInteraction.launchActivityWithSuccessStub(placement, "banner_success.json")

        CommonInteraction.clickItemAt(2)

        ViewTester()
            .waitForView(withId(R.id.bannerView))
            .perform(waitUntil(matchesColor(TestColors.yellow)))

        CommonInteraction.clickItemAt(2)

        // The banner is closed automatically when a new one is opened
        CommonInteraction.checkSubtitleContains("$placement adClosed")
    }

    // Events
    @Test
    fun test_banner_impression_events() {
        // Given
        CommonInteraction.launchActivityWithSuccessStub(
            "88001",
            "banner_success.json"
        )

        CommonInteraction.clickItemAt(2)

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
        stubIntents()
        val placement = "88001"
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "banner_success.json"
        )
        CommonInteraction.clickItemAt(2)

        // When
        ViewTester()
            .waitForView(withId(R.id.bannerView))
            .perform(click())

        // Then
        CommonInteraction.checkSubtitleContains("$placement adClicked")
        verifyUrlPathCalled("/click")
    }

    @Test
    fun test_parental_gate_success_event() {
        openParentalGate()
        ParentalGateInteraction.testSuccess()
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
        val placement = "88001"
        CommonInteraction.launchActivityWithSuccessStub(placement, "banner_success.json")
        CommonInteraction.clickPlacementById(placement)

        // When ad is clicked
        onView(withId(R.id.bannerView))
            .perform(click())

        // Then view URL is redirected to browser
        Intents.intended(hasAction(Intent.ACTION_VIEW))
        verifyUrlPathCalled("/click")
    }

    private fun openParentalGate() {
        val placement = "88001"
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "padlock/banner_success_padlock_enabled.json"
        ) {
            SettingsInteraction.enableParentalGate()
        }
        CommonInteraction.clickItemAt(2)

        // When parental gate is shown
        ViewTester()
            .waitForView(withContentDescription("Safe Ad Logo"))
            .check(isVisible())
            .perform(click())

        // Then parental gate open event is triggered
        ParentalGateInteraction.testOpen()
    }
}