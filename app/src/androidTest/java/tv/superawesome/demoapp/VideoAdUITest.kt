package tv.superawesome.demoapp

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.hamcrest.Matchers.anything
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

@RunWith(AndroidJUnit4::class)
@SmallTest
class VideoAdUITest {
    @Test
    fun test_CloseButtonWithNoDelay() {
        launchActivity<MainActivity>()

        // When the close button is configured to be displayed with no delay
        onView(withId(R.id.config2Button))
            .perform(waitUntil(isCompletelyDisplayed()), click(), click())

        onData(anything()).inAdapterView(withId(R.id.listView))
            .atPosition(12)
            .perform(click())

        ViewTester()
            .waitForView(withContentDescription("Close"))

        // Then
        isVisible()
    }

    @Test
    fun test_CloseButtonWithDelay() {
        launchActivity<MainActivity>()

        // When the close button is configured to be displayed with a delay
        onView(withId(R.id.config1Button))
            .perform(waitUntil(isCompletelyDisplayed()), click())

        onData(anything()).inAdapterView(withId(R.id.listView))
            .atPosition(12)
            .perform(click())

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))

        // Then
        onView(withContentDescription("Close"))
            .check(matches(isDisplayed()))
    }
}