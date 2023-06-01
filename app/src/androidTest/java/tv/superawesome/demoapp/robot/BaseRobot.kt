package tv.superawesome.demoapp.robot

import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

open class BaseRobot {
    fun waitAndTapOnClose() {
        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(ViewMatchers.isDisplayed()))
            .check(isVisible())
            .perform(click())
    }

    fun tapOnSafeAdLogo() {
        ViewTester()
            .waitForView(withContentDescription("Safe Ad Logo"))
            .check(isVisible())
            .perform(click())
    }

    fun waitAndCheckSafeAdLogo() {
        ViewTester()
            .waitForView(withContentDescription("Safe Ad Logo"))
            .check(isVisible())
    }
}