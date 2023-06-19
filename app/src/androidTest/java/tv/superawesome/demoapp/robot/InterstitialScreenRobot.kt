package tv.superawesome.demoapp.robot

import android.graphics.Color
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import org.junit.Assert
import tv.superawesome.demoapp.util.ScreenshotUtil
import tv.superawesome.demoapp.util.TestColors
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.isGone
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

class InterstitialScreenRobot : BaseRobot() {
    fun waitForImpression() {
        Thread.sleep(2500)
    }

    fun waitForDisplay() {
        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
    }

    fun waitForDisplay(color: Color) {
        ViewTester().waitForColorInCenter(color)
        Assert.assertTrue(
            TestColors.checkApproximatelyEqual(
                ScreenshotUtil.captureColorInCenter(),
                color
            )
        )
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

    fun tapOnCloseDelayed() {
        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .perform(click())
    }

    fun checkCloseAppearsDelayed(): ViewInteraction = ViewTester()
        .waitForView(withContentDescription("Close"))
        .check(isGone())
        .perform(waitUntil(isDisplayed()))

    fun checkCloseIsDisplayed() {
        onView(withContentDescription("Close"))
            .check(matches(isDisplayed()))
    }
}

fun interstitialScreenRobot(func: InterstitialScreenRobot.() -> Unit) =
    InterstitialScreenRobot().apply { func() }