package tv.superawesome.demoapp.robot

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

open class BaseRobot {
    fun waitAndTapOnClose() {
        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
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

    fun checkClickThrough(url: String) {
        intended(
            allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(url),
            )
        )
    }

    fun waitAndCheckSafeAdLogoInvisible() {
        onView(withContentDescription("Safe Ad Logo"))
            .check(matches(not(isDisplayed())))
    }

    fun checkCloseIsNotDisplayed() {
        onView(withContentDescription("Close"))
            .check(matches(not(isDisplayed())))
    }

    fun checkCloseIsDisplayed() {
        onView(withContentDescription("Close"))
            .check(matches(isDisplayed()))
    }

    fun waitForCloseAppear() {
        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
    }

    fun waitForCloseAppear(delay: Long) {
        Thread.sleep(delay)
    }
}