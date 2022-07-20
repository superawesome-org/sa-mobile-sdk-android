package tv.superawesome.demoapp

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.hamcrest.Matchers.anything
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.WireMockHelper.stubCommonPaths
import tv.superawesome.demoapp.util.WireMockHelper.stubSuccess
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalled
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.sdk.publisher.SAVideoAd

@RunWith(AndroidJUnit4::class)
@SmallTest
class VideoAdUITest {
    @get:Rule
    var wireMockRule = WireMockRule(8080)

    @Before
    fun setup() {
        SAVideoAd.setConfiguration(SAConfiguration.UITESTING)
    }

    @Test
    fun test_CloseButtonWithNoDelay() {
        // Given
        stubCommonPaths()
        stubSuccess("82090", "direct_video_success.json")

        launchActivity<MainActivity>()

        // When the close button is configured to be displayed with no delay
        onView(withId(R.id.config2Button))
            .perform(waitUntil(isCompletelyDisplayed()), click(), click())

        onData(anything()).inAdapterView(withId(R.id.listView))
            .atPosition(10)
            .perform(click())

        ViewTester()
            .waitForView(withContentDescription("Close"))

        // Then
        isVisible()
        verifyUrlPathCalled("/moat")
    }

    @Test
    fun test_CloseButtonWithDelay() {
        // Given
        stubCommonPaths()
        stubSuccess("82090", "direct_video_success.json")

        launchActivity<MainActivity>()

        // When the close button is configured to be displayed with a delay
        onView(withId(R.id.config1Button))
            .perform(waitUntil(isCompletelyDisplayed()), click())

        onData(anything()).inAdapterView(withId(R.id.listView))
            .atPosition(10)
            .perform(click())

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))

        // Then
        onView(withContentDescription("Close"))
            .check(matches(isDisplayed()))

        verifyUrlPathCalled("/moat")
        verifyUrlPathCalled("/event")
    }
}