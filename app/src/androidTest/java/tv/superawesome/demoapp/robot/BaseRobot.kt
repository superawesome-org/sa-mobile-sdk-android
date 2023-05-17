package tv.superawesome.demoapp.robot

import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

open class BaseRobot {
    fun waitAndTapOnClose() {
        ViewTester()
            .waitForView(ViewMatchers.withContentDescription("Close"))
            .perform(waitUntil(ViewMatchers.isDisplayed()))
            .check(isVisible())
            .perform(ViewActions.click())
    }
}