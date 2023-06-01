package tv.superawesome.demoapp.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import tv.superawesome.demoapp.util.isVisible

class VideoWarningRobot {
    fun tapOnClose() {
        onView(withText("Close Video"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withText("Close Video?"))
            .check(doesNotExist())
    }

    fun tapOnResume() {
        onView(withText("Resume Video"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withText("Close Video?"))
            .check(doesNotExist())
    }

    fun checkVisible() {
        onView(withText("Close Video?"))
            .check(isVisible())
        onView(withText("You will lose your reward"))
            .check(isVisible())
        onView(withText("Close Video"))
            .check(isVisible())
        onView(withText("Resume Video"))
            .check(isVisible())
    }
}

fun videoWarningRobot(func: VideoWarningRobot.() -> Unit) =
    VideoWarningRobot().apply { func() }