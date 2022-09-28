package tv.superawesome.demoapp

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
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
import tv.superawesome.demoapp.interaction.ParentalGateInteraction
import tv.superawesome.demoapp.interaction.SettingsInteraction
import tv.superawesome.demoapp.util.*
import tv.superawesome.demoapp.util.WireMockHelper.stubIntents
import tv.superawesome.demoapp.util.WireMockHelper.stubVASTPaths
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalled
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalledWithQueryParam

@RunWith(AndroidJUnit4::class)
@SmallTest
class VideoAdUITest {
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
    fun test_standard_CloseButtonWithNoDelay() {
        val placement = "87969"
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(placement, "video_direct_success.json") {
            SettingsInteraction.closeNoDelay()
        }

        CommonInteraction.clickItemAt(13)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
            .perform(click())

        CommonInteraction.checkSubtitleContains("$placement adClosed")
        verifyUrlPathCalled("/moat")
    }

    @Test
    fun test_standard_CloseButtonWithDelay() {
        val placement = "87969"
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(placement, "video_direct_success.json") {
            SettingsInteraction.closeDelayed()
        }

        CommonInteraction.clickItemAt(13)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .check(isGone())
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
            .perform(click())

        CommonInteraction.checkSubtitleContains("$placement adClosed")
        verifyUrlPathCalled("/moat")
        verifyUrlPathCalled("/event")
    }

    @Test
    fun test_vast_CloseButtonWithNoDelay() {
        val placement = "88406"
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(placement, "video_vast_success.json") {
            SettingsInteraction.closeNoDelay()
        }

        CommonInteraction.clickItemAt(11)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
            .perform(click())

        CommonInteraction.checkSubtitleContains("$placement adClosed")
    }

    @Test
    fun test_vast_CloseButtonWithDelay() {
        val placement = "88406"
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(placement, "video_vast_success.json") {
            SettingsInteraction.closeDelayed()
        }

        CommonInteraction.clickItemAt(11)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .check(isGone())
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
            .perform(click())

        CommonInteraction.checkSubtitleContains("$placement adClosed")
    }

    @Test
    fun test_vpaid_CloseButton() {
        val placement = "89056"
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(placement, "video_vpaid_success.json")

        CommonInteraction.clickItemAt(12)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
            .perform(click())

        CommonInteraction.checkSubtitleContains("$placement adClosed")
    }

    @Test
    fun test_auto_close_on_finish() {
        val placement = "87969"
        stubVASTPaths()

        CommonInteraction.launchActivityWithSuccessStub(
            placement, "video_direct_success.json"
        )

        CommonInteraction.clickItemAt(13)

        ViewTester()
            .waitForView(withId(R.id.subtitleTextView))
            .perform(waitUntil(isDisplayed()))

        CommonInteraction.checkSubtitleContains("$placement adEnded", 20000)
    }

    @Test
    fun test_vast_adLoading() {
        val placement = "88406"
        stubVASTPaths()
        testAdLoading(
            placement,
            "video_vast_success.json",
            11,
            TestColors.vastYellow
        )

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .perform(click())

        CommonInteraction.checkSubtitleContains("$placement adLoaded")
        CommonInteraction.checkSubtitleContains("$placement adShown")
    }

    @Test
    fun test_vpaid_adLoading() {
        val placement = "89056"
        stubVASTPaths()
        testAdLoading(
            placement,
            "video_vpaid_success.json",
            12,
            TestColors.vpaidYellow
        )

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .perform(click())

        CommonInteraction.checkSubtitleContains("$placement adLoaded")
    }

    @Test
    fun test_direct_adLoading() {
        val placement = "87969"
        stubVASTPaths()
        testAdLoading(
            "87969",
            "video_direct_success.json",
            13,
            TestColors.directYellow
        )

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .perform(click())

        CommonInteraction.checkSubtitleContains("$placement adLoaded")
        CommonInteraction.checkSubtitleContains("$placement adShown")
    }

    @Test
    fun test_adFailure() {
        val placement = "87969"
        CommonInteraction.launchActivityWithFailureStub(placement)

        CommonInteraction.clickItemAt(13)

        CommonInteraction.checkSubtitleContains("$placement adFailedToLoad")
    }

    @Test
    fun test_adNotFound() {
        val placement = "87969"
        CommonInteraction.launchActivityWithSuccessStub(placement, "not_found.json")

        CommonInteraction.clickItemAt(13)

        CommonInteraction.checkSubtitleContains("$placement adEmpty")
    }

    @Test
    fun test_direct_safeAdVisible() {
        val placement = "87969"
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "padlock/video_direct_success_padlock_enabled.json"
        )

        CommonInteraction.clickItemAt(13)

        ViewTester()
            .waitForView(withContentDescription("Safe Ad Logo"))
            .check(isVisible())
    }

    @Test
    fun test_vast_safeAdVisible() {
        val placement = "88406"
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "padlock/video_vast_success_padlock_enabled.json"
        )

        CommonInteraction.clickItemAt(11)

        ViewTester()
            .waitForView(withContentDescription("Safe Ad Logo"))
            .check(isVisible())
    }

    @Test
    fun test_bumper_enabled_from_settings() {
        // Given bumper page is enabled from settings
        val placement = "87969"
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "video_direct_success.json"
        ) {
            SettingsInteraction.enableBumper()
        }
        CommonInteraction.clickItemAt(13)

        // When ad is clicked
        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()))
            .perform(click())

        // Then bumper page is shown
        BumperInteraction.waitUntilBumper()

        // And view URL is redirected to browser
        Thread.sleep(4500)
        intended(IntentMatchers.hasAction(Intent.ACTION_VIEW))
    }

    @Test
    fun test_bumper_enabled_from_api() {
        // Given bumper page is enabled from api
        val placement = "87969"
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "video_direct_enabled_success.json"
        )
        CommonInteraction.clickItemAt(13)

        // When ad is clicked
        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()))
            .perform(click())

        // Then bumper page is shown
        BumperInteraction.waitUntilBumper()
    }

    @Test
    fun test_parental_gate_for_safe_ad_click() {
        val placement = "87969"
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "padlock/video_direct_success_padlock_enabled.json"
        ) {
            SettingsInteraction.enableParentalGate()
        }
        CommonInteraction.clickItemAt(13)

        ViewTester()
            .waitForView(withContentDescription("Safe Ad Logo"))
            .check(isVisible())
            .perform(click())

        onView(withText("Parental Gate"))
            .check(isVisible())
    }

    @Test
    fun test_parental_gate_for_ad_click() {
        val placement = "87969"
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "padlock/video_direct_success_padlock_enabled.json"
        ) {
            SettingsInteraction.enableParentalGate()
        }
        CommonInteraction.clickItemAt(13)

        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()), click())

        onView(withText("Parental Gate"))
            .check(isVisible())
    }

    // Events
    @Test
    fun test_direct_ad_impression_events() {
        // Given
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(
            "87969",
            "video_direct_success.json"
        )
        CommonInteraction.clickItemAt(13)

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
        val placement = "87969"
        stubVASTPaths()
        stubIntents()
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "video_direct_success.json"
        ) {
            SettingsInteraction.closeNoDelay()
        }
        CommonInteraction.clickItemAt(13)

        // When
        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()))
            .perform(click())

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .perform(click())

        // Then
        verifyUrlPathCalled("/vast/click")
        CommonInteraction.checkSubtitleContains("$placement adClicked")
    }

    @Test
    fun test_direct_ad_dwell_time() {
        // Given
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(
            "87969",
            "video_direct_success.json"
        )

        CommonInteraction.clickItemAt(13)

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
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(
            "88406",
            "video_vast_success.json"
        )

        CommonInteraction.clickItemAt(11)

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
        val placement = "88406"
        stubIntents()
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "video_vast_success.json"
        ) {
            SettingsInteraction.closeNoDelay()
        }

        CommonInteraction.clickItemAt(11)

        // When
        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()))
            .perform(click())

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .perform(click())

        // Then
        verifyUrlPathCalled("/vast/click")
        CommonInteraction.checkSubtitleContains("$placement adClicked")
    }

    @Test
    fun test_vast_ad_dwell_time() {
        // Given
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(
            "88406",
            "video_vast_success.json"
        )

        CommonInteraction.clickItemAt(11)

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
        Intents.intending(IntentMatchers.hasAction(Intent.ACTION_VIEW)).respondWith(
            Instrumentation.ActivityResult(
                Activity.RESULT_OK,
                Intent()
            )
        )
        val placement = "87969"
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "video_direct_success.json"
        )
        CommonInteraction.clickPlacementById(placement)

        // When ad is clicked
        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()))
            .perform(click())

        // Then view URL is redirected to browser
        intended(IntentMatchers.hasAction(Intent.ACTION_VIEW))
    }

    private fun openParentalGate() {
        val placement = "87969"
        CommonInteraction.launchActivityWithSuccessStub(
            placement,
            "padlock/video_direct_success_padlock_enabled.json"
        ) {
            SettingsInteraction.enableParentalGate()
        }
        CommonInteraction.clickItemAt(13)

        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()), click())

        // Then parental gate open event is triggered
        ParentalGateInteraction.testOpen()
    }
}