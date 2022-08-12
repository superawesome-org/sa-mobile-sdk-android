package tv.superawesome.demoapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.interaction.AdInteraction.testAdLoading
import tv.superawesome.demoapp.interaction.CommonInteraction
import tv.superawesome.demoapp.interaction.SettingsInteraction
import tv.superawesome.demoapp.util.TestColors
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalled
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

@RunWith(AndroidJUnit4::class)
@SmallTest
class VideoAdUITest {
    @get:Rule
    var wireMockRule = WireMockRule(8080)

    @Test
    fun test_CloseButtonWithNoDelay() {
        // Given
        CommonInteraction.launchActivityWithSuccessStub("87969", "video_direct_success.json") {
            SettingsInteraction.closeNoDelay()
        }

        // When the close button is configured to be displayed with no delay
        CommonInteraction.clickItemAt(11)

        ViewTester()
            .waitForView(withContentDescription("Close"))

        // Then
        verifyUrlPathCalled("/moat")
    }

    @Test
    fun test_CloseButtonWithDelay() {
        // Given
        CommonInteraction.launchActivityWithSuccessStub("87969", "video_direct_success.json")

        // When the close button is configured to be displayed with a delay
        CommonInteraction.clickItemAt(11)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))

        // Then
        onView(withContentDescription("Close"))
            .check(matches(isDisplayed()))

        verifyUrlPathCalled("/moat")
        verifyUrlPathCalled("/event")
    }

    @Test
    fun test_auto_close_on_finish() {
        testAdLoading(
            "87969",
            "video_direct_success.json",
            11,
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
            9,
            TestColors.vastYellow
        )
    }

    @Test
    fun test_vpaid_adLoading() {
        testAdLoading(
            "89056",
            "video_vpaid_success.json",
            10,
            TestColors.vpaidYellow
        )
    }

    @Test
    fun test_direct_adLoading() {
        testAdLoading(
            "87969",
            "video_direct_success.json",
            11,
            TestColors.directYellow
        )
    }

    @Test
    fun test_adFailure() {
        val placement = "87969"
        CommonInteraction.launchActivityWithFailureStub(placement)

        CommonInteraction.clickItemAt(11)

        CommonInteraction.checkSubtitle("$placement adFailedToLoad")
    }

    @Test
    fun test_adNotFound() {
        val placement = "87969"
        CommonInteraction.launchActivityWithSuccessStub(placement, "not_found.json")

        CommonInteraction.clickItemAt(11)

        CommonInteraction.checkSubtitle("$placement adEmpty")
    }

    @Test
    fun test_direct_safeAdVisible() {
        val placement = "87969"
        CommonInteraction.launchActivityWithSuccessStub(placement, "padlock/video_direct_success_padlock_enabled.json")

        CommonInteraction.clickItemAt(11)

        ViewTester()
            .waitForView(withContentDescription("Safe Ad Logo"))
            .check(isVisible())
    }

    @Test
    fun test_vast_safeAdVisible() {
        val placement = "88406"
        CommonInteraction.launchActivityWithSuccessStub(placement, "padlock/video_vast_success_padlock_enabled.json")

        CommonInteraction.clickItemAt(9)

        ViewTester()
            .waitForView(withContentDescription("Safe Ad Logo"))
            .check(isVisible())
    }
}