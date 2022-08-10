package tv.superawesome.demoapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.interaction.AdInteraction.testAdLoading
import tv.superawesome.demoapp.interaction.CommonInteraction
import tv.superawesome.demoapp.util.TestColors
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.isVisible
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription

@RunWith(AndroidJUnit4::class)
@SmallTest
class InterstitialUITest {
    @get:Rule
    var wireMockRule = WireMockRule(8080)

    @Test
    fun test_standard_adLoading() {
        testAdLoading(
            "87892",
            "interstitial_standard_success.json",
            4,
            TestColors.yellow
        )
    }

    @Test
    fun test_ksf_adLoading() {
        testAdLoading(
            "87970",
            "interstitial_ksf_success.json",
            7,
            TestColors.ksfYellow
        )
    }

    @Test
    fun test_adFailure() {
        val placement = "87970"
        CommonInteraction.launchActivityWithFailureStub(placement)

        CommonInteraction.clickItemAt(7)

        CommonInteraction.checkSubtitle("$placement adFailedToLoad")
    }

    @Test
    fun test_adNotFound() {
        val placement = "87970"
        CommonInteraction.launchActivityWithSuccessStub(placement, "not_found.json")

        CommonInteraction.clickItemAt(7)

        CommonInteraction.checkSubtitle("$placement adEmpty")
    }

    @Test
    fun test_standard_safeAdVisible() {
        val placement = "87892"
        CommonInteraction.launchActivityWithSuccessStub(placement, "padlock/interstitial_standard_success_padlock_enabled.json")

        CommonInteraction.clickItemAt(4)

        ViewTester()
            .waitForView(withContentDescription("Safe Ad Logo"))
            .check(isVisible())
    }

    @Test
    fun test_ksf_safeAdVisible() {
        val placement = "87970"
        CommonInteraction.launchActivityWithSuccessStub(placement, "padlock/interstitial_standard_success_padlock_enabled.json")

        CommonInteraction.clickItemAt(7)

        ViewTester()
            .waitForView(withContentDescription("Safe Ad Logo"))
            .check(isVisible())
    }
}