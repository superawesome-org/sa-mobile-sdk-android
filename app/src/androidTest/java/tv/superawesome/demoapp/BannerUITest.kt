package tv.superawesome.demoapp

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
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
import tv.superawesome.demoapp.interaction.SettingsInteraction
import tv.superawesome.demoapp.util.ColorMatcher.matchesColor
import tv.superawesome.demoapp.util.TestColors
import tv.superawesome.demoapp.util.ViewTester
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
        CommonInteraction.launchActivityWithSuccessStub(
            "88001",
            "banner_success.json"
        )
        CommonInteraction.clickItemAt(2)

        // When
        ViewTester()
            .waitForView(withId(R.id.bannerView))
            .perform(click())

        // Then
        verifyUrlPathCalled("/click")
    }
}