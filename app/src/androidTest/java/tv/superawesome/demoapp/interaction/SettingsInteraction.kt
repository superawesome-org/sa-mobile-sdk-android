package tv.superawesome.demoapp.interaction

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.util.waitUntil

object SettingsInteraction {
    fun openSettings() {
        onView(withId(R.id.settingsButton))
            .perform(waitUntil(isCompletelyDisplayed()), click())
    }

    fun enableUITesting() {
        onView(withId(R.id.uiTestingButton))
            .perform(click())
    }

    fun closeNoDelay() {
        onView(withId(R.id.closeImmediatelyButton))
            .perform(click())
    }

    fun closeSettings() {
        onView(withId(R.id.closeButton))
            .perform(click())
    }

    fun applyCommonSettings() {
        openSettings()
        enableUITesting()
        closeSettings()
    }
}