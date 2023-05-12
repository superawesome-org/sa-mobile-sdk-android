package tv.superawesome.demoapp.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.isGone
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

class InterstitialScreenRobot : BaseRobot() {
    fun waitForDisplay() {
        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
    }

    fun tapOnAdDelayed() {
        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()))
            .perform(click())
    }

    fun tapOnAd() {
        onView(withContentDescription("Ad content"))
            .perform(click())
    }

    fun tapOnClose() {
        onView(withContentDescription("Close"))
            .perform(click())
    }

    fun tapOnCloseDelayed(): ViewInteraction = ViewTester()
        .waitForView(withContentDescription("Close"))
        .check(isGone())
        .perform(waitUntil(isDisplayed()))
//        .perform(click())

    fun tapOnSafeAdLogo() {
        ViewTester()
            .waitForView(withContentDescription("Safe Ad Logo"))
            .check(isVisible())
            .perform(click())
    }
}

fun interstitialScreenRobot(func: InterstitialScreenRobot.() -> Unit) =
    InterstitialScreenRobot().apply { func() }