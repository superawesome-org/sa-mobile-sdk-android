package tv.superawesome.demoapp.interaction

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.util.UIAwait
import tv.superawesome.demoapp.util.waitUntil

object SettingsInteraction {
    fun openSettings() {
        onView(withId(R.id.settingsButton))
            .perform(click())
//            .perform(waitUntil(isCompletelyDisplayed()), click())
    }

    private fun resetSettings() {
        onView(withId(R.id.resetButton))
            .perform(click())
    }

    private fun enableUITesting() {
        onView(withId(R.id.uiTestingButton))
            .perform(click())
    }

    fun closeDelayed() {
        onView(withId(R.id.closeDelayedButton))
            .perform(click())
    }

    fun closeNoDelay() {
        onView(withId(R.id.closeImmediatelyButton))
            .perform(click())
    }

    fun enableBumper() {
        onView(withId(R.id.bumperEnableButton))
            .perform(click())
    }

    fun enableParentalGate() {
        onView(withId(R.id.parentalEnableButton))
            .perform(click())
    }

    fun disablePlay() {
        onView(withId(R.id.playbackDisableButton))
            .perform(click())
    }

    fun closeSettings() {
        onView(withId(R.id.closeButton))
            .perform(click())
    }

    fun commonSettings() {
        resetSettings()
        enableUITesting()
    }

    fun applyCommonSettings() {
        openSettings()
        commonSettings()
        closeSettings()
    }
}
