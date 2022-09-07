package tv.superawesome.demoapp.interaction

import androidx.test.espresso.matcher.ViewMatchers
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

object BumperInteraction {
    fun waitUntilBumper() {
        ViewTester()
            .waitForView(ViewMatchers.withText("Bye! You’re now leaving AwesomeAds Demo"))
            .perform(waitUntil(ViewMatchers.isDisplayed()))
            .check(isVisible())
    }
}