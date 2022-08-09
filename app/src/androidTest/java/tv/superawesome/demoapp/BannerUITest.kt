package tv.superawesome.demoapp

import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.interaction.CommonInteraction
import tv.superawesome.demoapp.util.ColorMatcher.matchesColor
import tv.superawesome.demoapp.util.TestColors
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.waitUntil

@RunWith(AndroidJUnit4::class)
@SmallTest
class BannerUITest {
    @get:Rule
    var wireMockRule = WireMockRule(8080)

    @Test
    fun test_adLoading() {
        CommonInteraction.launchActivityWithSuccessStub("88001", "banner_success.json")

        CommonInteraction.clickItemAt(2)

        ViewTester()
            .waitForView(withId(R.id.bannerView))
            .perform(waitUntil(matchesColor(TestColors.yellow)))
    }

    @Test
    fun test_adFailure() {
        val placement = "88001"
        CommonInteraction.launchActivityWithFailureStub(placement)

        CommonInteraction.clickItemAt(2)

        CommonInteraction.checkSubtitle("$placement adFailedToLoad")
    }

    @Test
    fun test_adNotFound() {
        val placement = "88001"
        CommonInteraction.launchActivityWithSuccessStub(placement, "not_found.json")

        CommonInteraction.clickItemAt(2)

        CommonInteraction.checkSubtitle("$placement adEmpty")
    }

    @Test
    fun test_safeAdVisible() {
        val placement = "88001"
        CommonInteraction.launchActivityWithSuccessStub(placement, "padlock/banner_success_padlock_enabled.json")

        CommonInteraction.clickItemAt(2)

        ViewTester().waitForView(withContentDescription("Safe Ad Logo"))
    }
}