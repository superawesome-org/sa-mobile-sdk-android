package tv.superawesome.demoapp.robot

import android.graphics.Color
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import org.hamcrest.Matchers.allOf
import tv.superawesome.demoapp.util.ColorMatcher.matchesColor
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

class BannerRobot {

    fun waitForDisplay() {
        ViewTester()
            .waitForView(allOf(withContentDescription("Ad content"), isDisplayed()))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
    }

    fun waitAndCheckSafeAdLogo() {
        ViewTester()
            .waitForView(withContentDescription("Safe Ad Logo"))
            .check(isVisible())
    }

    fun waitForDisplay(color: Color) {
        ViewTester()
            .waitForView(allOf(withContentDescription("Ad content"), isDisplayed()))
            .perform(waitUntil(matchesColor(color)))
    }

    fun tapOnAd() {
        onView(allOf(withContentDescription("Ad content"), isDisplayed()))
            .perform(click())
    }
}

fun bannerRobot(func: BannerRobot.() -> Unit) =
    BannerRobot().apply { func() }
