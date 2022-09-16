package tv.superawesome.demoapp

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.interaction.AdInteraction.testAdLoading
import tv.superawesome.demoapp.interaction.BumperInteraction
import tv.superawesome.demoapp.interaction.CommonInteraction
import tv.superawesome.demoapp.interaction.SettingsInteraction
import tv.superawesome.demoapp.util.TestColors
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalled
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalledWithQueryParam
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

@RunWith(AndroidJUnit4::class)
@SmallTest
class InterstitialUITest {
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
    fun test_standard_adLoading() {
        testAdLoading(
            "87892",
            "interstitial_standard_success.json",
            5,
            TestColors.yellow
        )
    }

    @Test
    fun test_ksf_adLoading() {
        testAdLoading(
            "87970",
            "interstitial_ksf_success.json",
            8,
            TestColors.ksfYellow
        )
    }

    @Test
    fun test_adFailure() {
        val placement = "87970"
        CommonInteraction.launchActivityWithFailureStub(placement)

        CommonInteraction.clickItemAt(8)

        CommonInteraction.checkSubtitle("$placement adFailedToLoad")
    }

    @Test
    fun test_adNotFound() {
        val placement = "87970"
        CommonInteraction.launchActivityWithSuccessStub(placement, "not_found.json")

        CommonInteraction.clickItemAt(8)

        CommonInteraction.checkSubtitle("$placement adEmpty")
    }

    @Test
    fun test_standard_safeAdVisible() {
        val placement = "87892"
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "padlock/interstitial_standard_success_padlock_enabled.json"
        )

        CommonInteraction.clickItemAt(5)

        ViewTester()
            .waitForView(withContentDescription("Safe Ad Logo"))
            .check(isVisible())
    }

    @Test
    fun test_standard_CloseButton() {
        CommonInteraction.launchActivityWithSuccessStub(
            "87892",
            "interstitial_standard_success.json"
        )

        CommonInteraction.clickItemAt(5)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(ViewMatchers.isDisplayed()))
            .check(isVisible())
    }

    @Test
    fun test_ksf_CloseButton() {
        CommonInteraction.launchActivityWithSuccessStub("87970", "interstitial_ksf_success.json")

        CommonInteraction.clickItemAt(8)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(ViewMatchers.isDisplayed()))
            .check(isVisible())
    }

    @Test
    fun test_bumper_enabled_from_settings() {
        // Given bumper page is enabled from settings
        val placement = "87892"
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "interstitial_standard_success.json"
        ) {
            SettingsInteraction.enableBumper()
        }
        CommonInteraction.clickItemAt(5)

        // When ad is clicked
        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(ViewMatchers.isDisplayed()))
            .perform(click())

        // Then bumper page is shown
        BumperInteraction.waitUntilBumper()

        // And view URL is redirected to browser
        Thread.sleep(4500)
        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_VIEW))
        verifyUrlPathCalled("/click")
    }

    @Test
    fun test_bumper_enabled_from_api() {
        // Given bumper page is enabled from api
        val placement = "87892"
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "interstitial_standard_enabled_success.json"
        )
        CommonInteraction.clickItemAt(5)

        // When ad is clicked
        onView(withContentDescription("Ad content")).perform(click())

        // Then bumper page is shown
        BumperInteraction.waitUntilBumper()
    }

    @Test
    fun test_parental_gate_for_safe_ad_click() {
        val placement = "87892"
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "padlock/interstitial_standard_success_padlock_enabled.json"
        ) {
            SettingsInteraction.enableParentalGate()
        }
        CommonInteraction.clickItemAt(5)

        ViewTester()
            .waitForView(withContentDescription("Safe Ad Logo"))
            .check(isVisible())
            .perform(click())

        onView(withText("Parental Gate"))
            .check(isVisible())
    }

    // Events
    @Test
    fun test_standard_ad_impression_events() {
        //Given
        CommonInteraction.launchActivityWithSuccessStub(
            "87892",
            "interstitial_standard_success.json"
        )

        CommonInteraction.clickItemAt(5)

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
    fun test_ksf_ad_impression_events() {
        //Given
        CommonInteraction.launchActivityWithSuccessStub(
            "87970",
            "interstitial_ksf_success.json"
        )

        CommonInteraction.clickItemAt(8)

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
    fun test_standard_ad_click_event() {
        // Given
        CommonInteraction.launchActivityWithSuccessStub(
            "87892",
            "interstitial_standard_success.json"
        )
        CommonInteraction.clickItemAt(5)

        // When
        onView(withContentDescription("Ad content")).perform(click())

        // Then
        verifyUrlPathCalled("/click")
    }
}