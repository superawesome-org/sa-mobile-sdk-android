package tv.superawesome.demoapp.interaction

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.anything
import tv.superawesome.demoapp.MainActivity
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.util.WireMockHelper.stubCommonPaths
import tv.superawesome.demoapp.util.WireMockHelper.stubFailure
import tv.superawesome.demoapp.util.WireMockHelper.stubSuccess

object CommonInteraction {
    fun launchActivityWithSuccessStub(
        placement: String,
        fileName: String,
        settings: (() -> Unit)? = null
    ) {
        stubCommonPaths()
        stubSuccess(placement, fileName)

        launchActivity<MainActivity>()

        settings?.let {
            SettingsInteraction.openSettings()
            SettingsInteraction.enableUITesting()
            settings.invoke()
            SettingsInteraction.closeSettings()
        } ?: run {
            SettingsInteraction.applyCommonSettings()
        }
    }

    fun launchActivityWithFailureStub(placement: String) {
        stubCommonPaths()
        stubFailure(placement)

        launchActivity<MainActivity>()

        SettingsInteraction.applyCommonSettings()
    }

    fun clickItemAt(position: Int) {
        onData(anything()).inAdapterView(withId(R.id.listView))
            .atPosition(position)
            .perform(click())
    }

    fun checkSubtitle(text: String) {
        onView(withId(R.id.subtitleTextView))
            .check(matches(withText(text)))
    }
}