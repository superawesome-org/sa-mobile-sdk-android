package tv.superawesome.demoapp

import androidx.test.espresso.assertion.ViewAssertions.matches
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
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

@RunWith(AndroidJUnit4::class)
@SmallTest
class VideoAdUITest {
    @get:Rule
    var wireMockRule = WireMockRule(8080)

    @Test
    fun test_standard_CloseButtonWithNoDelay() {
        CommonInteraction.launchActivityWithSuccessStub("87969", "video_direct_success.json") {
            SettingsInteraction.closeNoDelay()
        }

        CommonInteraction.clickItemAt(11)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
    }

    @Test
    fun test_standard_CloseButtonWithDelay() {
        CommonInteraction.launchActivityWithSuccessStub("87969", "video_direct_success.json")

        CommonInteraction.clickItemAt(11)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
    }

    @Test
    fun test_vast_CloseButtonWithNoDelay() {
        CommonInteraction.launchActivityWithSuccessStub("88406", "video_vast_success.json") {
            SettingsInteraction.closeNoDelay()
        }

        CommonInteraction.clickItemAt(9)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
    }

    @Test
    fun test_vast_CloseButtonWithDelay() {
        CommonInteraction.launchActivityWithSuccessStub("88406", "video_vast_success.json")

        CommonInteraction.clickItemAt(9)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
    }

    @Test
    fun test_vpaid_CloseButton() {
        CommonInteraction.launchActivityWithSuccessStub("89056", "video_vpaid_success.json")

        CommonInteraction.clickItemAt(10)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
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