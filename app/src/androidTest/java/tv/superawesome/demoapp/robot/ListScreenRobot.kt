package tv.superawesome.demoapp.robot

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.interaction.SettingsInteraction
import tv.superawesome.demoapp.main.MainActivity
import tv.superawesome.demoapp.model.TestData
import tv.superawesome.demoapp.util.AdapterUtil.withPlacementId
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.WireMockHelper
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

class ListScreenRobot : BaseRobot() {
    private fun launchActivityWithSuccessStub(
        placement: String,
        fileName: String,
        settings: (() -> Unit)? = null
    ) {
        WireMockHelper.stubCommonPaths()
        WireMockHelper.stubSuccess(placement, fileName)

        launchActivity<MainActivity>()

        settings?.let {
            SettingsInteraction.openSettings()
            SettingsInteraction.commonSettings()
            settings.invoke()
            SettingsInteraction.closeSettings()
        } ?: run {
            SettingsInteraction.applyCommonSettings()
        }
    }

    fun launchWithSuccessStub(testData: TestData, settings: (() -> Unit)? = null) {
        launchActivityWithSuccessStub(testData.placement, testData.fileName, settings)
    }

    fun launchActivityWithFailureStub(testData: TestData) {
        launchActivityWithFailureStub(testData.placement)
    }

    private fun launchActivityWithFailureStub(placement: String) {
        WireMockHelper.stubCommonPaths()
        WireMockHelper.stubFailure(placement)

        launchActivity<MainActivity>()

        SettingsInteraction.applyCommonSettings()
    }

    private fun clickPlacementById(placementId: String) {
        Thread.sleep(200)
        onData(withPlacementId(placementId)).inAdapterView(withId(R.id.listView))
            .perform(click())
    }

    fun tapOnPlacement(testData: TestData) {
        clickPlacementById(testData.placement)
    }

    fun checkSubtitleContains(text: String) {
        onView(withId(R.id.subtitleTextView))
            .perform(waitUntil(isDisplayed()))
            .perform(waitUntil(withSubstring(text)))
    }

    fun waitForDisplay() {
        ViewTester()
            .waitForView(withId(R.id.subtitleTextView))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
    }
}

fun listScreenRobot(func: ListScreenRobot.() -> Unit) =
    ListScreenRobot().apply { func() }