package tv.superawesome.demoapp

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
import tv.superawesome.demoapp.interaction.SettingsInteraction
import tv.superawesome.demoapp.util.*
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
        CommonInteraction.launchActivityWithSuccessStub("87969", "video_direct_success.json") {
            SettingsInteraction.closeNoDelay()
        }

        CommonInteraction.clickItemAt(13)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())

        verifyUrlPathCalled("/moat")
    }

    @Test
    fun test_standard_CloseButtonWithDelay() {
        CommonInteraction.launchActivityWithSuccessStub("87969", "video_direct_success.json") {
            SettingsInteraction.closeDelayed()
        }

        CommonInteraction.clickItemAt(13)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .check(isGone())
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())

        verifyUrlPathCalled("/moat")
        verifyUrlPathCalled("/event")
    }

    @Test
    fun test_vast_CloseButtonWithNoDelay() {
        CommonInteraction.launchActivityWithSuccessStub("88406", "video_vast_success.json") {
            SettingsInteraction.closeNoDelay()
        }

        CommonInteraction.clickItemAt(11)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
    }

    @Test
    fun test_vast_CloseButtonWithDelay() {
        CommonInteraction.launchActivityWithSuccessStub("88406", "video_vast_success.json") {
            SettingsInteraction.closeDelayed()
        }

        CommonInteraction.clickItemAt(11)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .check(isGone())
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
    }

    @Test
    fun test_vpaid_CloseButton() {
        CommonInteraction.launchActivityWithSuccessStub("89056", "video_vpaid_success.json")

        CommonInteraction.clickItemAt(12)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
    }

    @Test
    fun test_auto_close_on_finish() {
        testAdLoading(
            "87969",
            "video_direct_success.json",
            13,
            TestColors.directYellow
        )
        ViewTester()
            .waitForView(withId(R.id.subtitleTextView))
            .perform(waitUntil(isDisplayed()))
    }

    @Test
    fun test_vast_adLoading() {
        testAdLoading(
            "88406",
            "video_vast_success.json",
            11,
            TestColors.vastYellow
        )
    }

    @Test
    fun test_vpaid_adLoading() {
        testAdLoading(
            "89056",
            "video_vpaid_success.json",
            12,
            TestColors.vpaidYellow
        )
    }

    @Test
    fun test_direct_adLoading() {
        testAdLoading(
            "87969",
            "video_direct_success.json",
            13,
            TestColors.directYellow
        )
    }

    @Test
    fun test_adFailure() {
        val placement = "87969"
        CommonInteraction.launchActivityWithFailureStub(placement)

        CommonInteraction.clickItemAt(13)

        CommonInteraction.checkSubtitle("$placement adFailedToLoad")
    }

    @Test
    fun test_adNotFound() {
        val placement = "87969"
        CommonInteraction.launchActivityWithSuccessStub(placement, "not_found.json")

        CommonInteraction.clickItemAt(13)

        CommonInteraction.checkSubtitle("$placement adEmpty")
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
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(
            "87969",
            "video_direct_success.json"
        )
        CommonInteraction.clickItemAt(13)

        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()))

        Thread.sleep(5000)
        verifyUrlPathCalled("/vast/impression")
        verifyUrlPathCalledWithQueryParam(
            "/event",
            "data",
            ".*viewable_impression.*"
        )
    }

    @Test
    fun test_direct_ad_click_event() {
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(
            "87969",
            "video_direct_success.json"
        )
        CommonInteraction.clickItemAt(13)

        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()))
            .perform(click())

        verifyUrlPathCalled("/vast/click")
    }

    @Test
    fun test_direct_ad_dwell_time() {
        CommonInteraction.launchActivityWithSuccessStub(
            "87969",
            "video_direct_success.json"
        )

        CommonInteraction.clickItemAt(13)

        Thread.sleep(3000)

        verifyUrlPathCalledWithQueryParam(
            "/event",
            "type",
            ".*viewTime.*"
        )
    }

    @Test
    fun test_vast_ad_impression_events() {
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(
            "88406",
            "video_vast_success.json"
        )

        CommonInteraction.clickItemAt(11)

        Thread.sleep(5000)

        verifyUrlPathCalled("/vast/impression")
        verifyUrlPathCalledWithQueryParam(
            "/event",
            "data",
            ".*viewable_impression.*"
        )
    }

    @Test
    fun test_vast_ad_click_event() {
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(
            "88406",
            "video_vast_success.json"
        )

        CommonInteraction.clickItemAt(11)

        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()))
            .perform(click())

        verifyUrlPathCalled("/vast/click")
    }

    @Test
    fun test_vast_ad_dwell_time() {
        stubVASTPaths()
        CommonInteraction.launchActivityWithSuccessStub(
            "88406",
            "video_vast_success.json"
        )

        CommonInteraction.clickItemAt(11)

        Thread.sleep(3000)

        verifyUrlPathCalledWithQueryParam(
            "/event",
            "type",
            ".*viewTime.*"
        )
    }
}