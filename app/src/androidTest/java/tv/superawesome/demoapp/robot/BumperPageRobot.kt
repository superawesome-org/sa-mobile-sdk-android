package tv.superawesome.demoapp.robot

import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

class BumperPageRobot {
    fun waitForDisplay() {
        ViewTester()
            .waitForView(ViewMatchers.withText("Bye! You’re now leaving AwesomeAds Demo"))
            .perform(waitUntil(ViewMatchers.isDisplayed()))
            .check(isVisible())
    }

    fun checkIsVisible() {
        Espresso.onView(ViewMatchers.withText("Bye! You’re now leaving AwesomeAds Demo"))
            .check(isVisible())
    }
}

fun bumperPageRobot(func: BumperPageRobot.() -> Unit) =
    BumperPageRobot().apply { func() }