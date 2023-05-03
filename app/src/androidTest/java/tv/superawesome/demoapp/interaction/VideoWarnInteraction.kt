package tv.superawesome.demoapp.interaction

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import tv.superawesome.demoapp.util.isVisible

object VideoWarnInteraction {
    fun clickClose() {
        onView(withText("Close Video"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withText("Close Video?"))
            .check(doesNotExist())
    }

    fun clickResume() {
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
