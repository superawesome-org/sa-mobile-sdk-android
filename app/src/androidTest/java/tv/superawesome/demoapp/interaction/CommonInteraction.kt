package tv.superawesome.demoapp.interaction

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.hamcrest.CoreMatchers.not
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.main.MainActivity
import tv.superawesome.demoapp.model.TestData
import tv.superawesome.demoapp.robot.settingsScreenRobot
import tv.superawesome.demoapp.util.AdapterUtil
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.WireMockHelper.stubCommonPaths
import tv.superawesome.demoapp.util.WireMockHelper.stubFailure
import tv.superawesome.demoapp.util.WireMockHelper.stubSuccess
import tv.superawesome.demoapp.util.isGone
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil

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
            settingsScreenRobot {
                openSettings()
                commonSettings()

                settings.invoke()

                closeSettings()
            }
        } ?: run {
            settingsScreenRobot {
                applyCommonSettings()
            }
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

        settingsScreenRobot {
            applyCommonSettings()
        }
    }

    private fun clickPlacementById(placementId: String) {
        Thread.sleep(200)
        onData(AdapterUtil.withPlacementId(placementId)).inAdapterView(withId(R.id.listView))
            .perform(click())
    }

    fun clickItemAt(testData: TestData) {
        clickPlacementById(testData.placement)
    }

    fun checkSubtitleContains(text: String) {
        onView(withId(R.id.subtitleTextView))
            .perform(waitUntil(isDisplayed()))
            .perform(waitUntil(withSubstring(text)))
    }

    fun checkSubtitleNotContains(text: String) {
        onView(withId(R.id.subtitleTextView))
            .check(matches(not(withSubstring(text))))
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

    fun checkAdHasBeenLoadedShownClickedClosed(placementId: String) {
        checkSubtitleContains("$placementId adLoaded")
        checkSubtitleContains("$placementId adShown")
        checkSubtitleContains("$placementId adClicked")
        checkSubtitleContains("$placementId adClosed")
    }

    fun pressDeviceBackButton() {
        val mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        mDevice.pressBack()
    }
}
