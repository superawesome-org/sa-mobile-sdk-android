package tv.superawesome.demoapp.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import tv.superawesome.demoapp.R

class SettingsScreenRobot {
    fun tapOnEnableBumper() {
        onView(withId(R.id.bumperEnableButton))
            .perform(click())
    }
}

fun settingsScreenRobot(func: SettingsScreenRobot.() -> Unit) =
    SettingsScreenRobot().apply { func() }