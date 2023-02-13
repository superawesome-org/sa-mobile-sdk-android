package tv.superawesome.demoapp.interaction

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import tv.superawesome.demoapp.MainActivity
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.model.TestData
import tv.superawesome.demoapp.util.*
import tv.superawesome.demoapp.util.WireMockHelper.stubCommonPaths
import tv.superawesome.demoapp.util.WireMockHelper.stubFailure
import tv.superawesome.demoapp.util.WireMockHelper.stubSuccess

object CommonInteraction {
    private fun launchActivityWithSuccessStub(
        placement: String,
        fileName: String,
        settings: (() -> Unit)? = null
    ) {
        stubCommonPaths()
        stubSuccess(placement, fileName)

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

    fun launchActivityWithSuccessStub(testData: TestData, settings: (() -> Unit)? = null) {
        launchActivityWithSuccessStub(testData.placement, testData.fileName, settings)
    }

    fun launchActivityWithFailureStub(testData: TestData) {
        launchActivityWithFailureStub(testData.placement)
    }

    private fun launchActivityWithFailureStub(placement: String) {
        stubCommonPaths()
        stubFailure(placement)

        launchActivity<MainActivity>()

        SettingsInteraction.applyCommonSettings()
    }

    private fun clickPlacementById(placementId: String) {
        onData(AdapterUtil.withPlacementId(placementId)).inAdapterView(withId(R.id.listView))
            .perform(click())
    }

    fun clickItemAt(testData: TestData) {
        clickPlacementById(testData.placement)
    }

    fun checkSubtitleContains(text: String) {
        onView(withId(R.id.subtitleTextView))
            .check(matches(withSubstring(text)))
//            .perform(waitUntil(isDisplayed()))
//            .perform(waitUntil(withSubstring(text)))
    }

    fun waitForCloseButtonThenClick() {
        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
            .perform(click())
    }

    fun waitForCloseButtonWithDelayThenClick() {
        waitForCloseButtonWithDelay()
            .perform(click())
    }

    fun waitForCloseButtonWithDelay(): ViewInteraction = ViewTester()
        .waitForView(withContentDescription("Close"))
        .check(isGone())
        .perform(waitUntil(isDisplayed()))
        .check(isVisible())

    fun waitForAdContentThenClick() {
        ViewTester()
            .waitForView(withContentDescription("Ad content"))
            .perform(waitUntil(isDisplayed()))
            .perform(click())
    }

    fun waitForSafeAdLogoThenClick() {
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
}
