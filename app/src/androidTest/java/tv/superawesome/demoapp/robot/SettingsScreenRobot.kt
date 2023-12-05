package tv.superawesome.demoapp.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.util.waitUntil

class SettingsScreenRobot {
    fun tapOnEnableBumperPage() {
        onView(withContentDescription("SettingsItem.Buttons.BumperEnable"))
            .perform(click())
    }

    fun tapOnCloseNoDelay() {
        onView(withContentDescription("SettingsItem.Buttons.CloseImmediately"))
            .perform(click())
    }

    fun tapOnCloseHidden() {
        onView(withContentDescription("SettingsItem.Buttons.CloseHidden"))
            .perform(click())
    }

    fun tapOnCloseDelayed() {
        onView(withContentDescription("SettingsItem.Buttons.CloseDelayed"))
            .perform(click())
    }

    fun tapOnCloseCustom() {
        onView(withContentDescription("SettingsItem.Buttons.CloseCustom"))
            .perform(click())
    }

    fun tapOnCustom5s() {
        onView(withContentDescription("SettingsItem.Buttons.5s"))
            .perform(click())
    }

    fun tapOnCustom10s() {
        onView(withContentDescription("SettingsItem.Buttons.10s"))
            .perform(click())
    }

    fun tapOnCustom15s() {
        onView(withContentDescription("SettingsItem.Buttons.15s"))
            .perform(click())
    }

    fun tapOnCustom30s() {
        onView(withContentDescription("SettingsItem.Buttons.30s"))
            .perform(click())
    }

    fun tapOnEnableParentalGate() {
        onView(withContentDescription("SettingsItem.Buttons.ParentalGateEnable"))
            .perform(click())
    }

    fun tapOnDisableCloseAtEnd() {
        onView(withContentDescription("SettingsItem.Buttons.VideoCloseAtEndDisable"))
            .perform(click())
    }

    fun tapOnEnableVideoWarnDialog() {
        onView(withContentDescription("SettingsItem.Buttons.VideoCloseDialogEnable"))
            .perform(click())
    }

    fun tapOnDisablePlay() {
        onView(withContentDescription("SettingsItem.Buttons.PlaybackDisable"))
            .perform(click())
    }

    fun tapOnMuteOnStart() {
        onView(withContentDescription("SettingsItem.Buttons.MuteEnable"))
            .perform(click())
    }

    fun openSettings() {
        onView(withId(R.id.settingsButton))
            .perform(waitUntil(ViewMatchers.isCompletelyDisplayed()), click())
    }

    private fun resetSettings() {
        onView(withId(R.id.resetButton))
            .perform(click())
    }

    fun closeSettings() {
        onView(withId(R.id.closeButton))
            .perform(click())
    }

    fun commonSettings() {
        resetSettings()
    }

    fun applyCommonSettings() {
        openSettings()
        commonSettings()
        closeSettings()
    }
}

fun settingsScreenRobot(func: SettingsScreenRobot.() -> Unit) =
    SettingsScreenRobot().apply { func() }
