package tv.superawesome.demoapp.interaction

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

object BumperInteraction {
    fun waitUntilBumper() {
        ViewTester()
            .waitForView(withText("Bye! You’re now leaving AwesomeAds Demo"))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
    }

    fun checkBumperPageIsGone() {
        onView(withText("Bye! You’re now leaving AwesomeAds Demo"))
            .check(isVisible())
    }
}