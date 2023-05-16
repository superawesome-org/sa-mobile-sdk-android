package tv.superawesome.demoapp.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

class VideoScreenRobot : BaseRobot() {
    fun waitForDisplay() {
        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
    }

    fun tapOnAd() {
        onView(withContentDescription("Ad content"))
            .perform(click())
    }

    fun tapOnClose() {
        onView(withContentDescription("Close"))
            .perform(click())
    }
}

fun videoScreenRobot(func: VideoScreenRobot.() -> Unit) =
    VideoScreenRobot().apply { func() }