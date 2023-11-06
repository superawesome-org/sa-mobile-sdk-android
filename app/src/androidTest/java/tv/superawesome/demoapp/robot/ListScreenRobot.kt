package tv.superawesome.demoapp.robot

import android.content.Intent
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers.anyOf
import org.junit.Assert
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.main.MainActivity
import tv.superawesome.demoapp.model.TestData
import tv.superawesome.demoapp.util.ScreenshotUtil
import tv.superawesome.demoapp.util.TestColors
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.WireMockHelper
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil
import tv.superawesome.sdk.publisher.SAEvent

class ListScreenRobot : BaseRobot() {
    private fun launchActivityWithSuccessStub(
        placement: String,
        fileName: String,
        settings: (() -> Unit)? = null
    ) {
        WireMockHelper.stubCommonPaths()
        WireMockHelper.stubSuccess(placement, fileName)

        launchActivity<MainActivity>(
            intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
                .apply { putExtra("environment", "UITesting") }
        )

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

        settingsScreenRobot {
            applyCommonSettings()
        }
    }

    private fun clickPlacementById(placementId: String) {
        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItem<ViewHolder>
                (hasDescendant(withText(containsString(placementId))), click()))
    }

    fun tapOnPlacement(testData: TestData) {
        clickPlacementById(testData.placement)
    }

    fun checkAdHasBeenLoadedShownClickedClosed(placement: TestData) {
        checkForEvent(placement, SAEvent.adLoaded)
        checkForEvent(placement, SAEvent.adShown)
        checkForEvent(placement, SAEvent.adClicked)
        checkForEvent(placement, SAEvent.adClosed)
    }

    fun checkForEvent(placement: TestData, event: SAEvent) {
        onView(withId(R.id.subtitleTextView))
            .perform(waitUntil(isDisplayed()))
            .perform(waitUntil(withSubstring("${placement.placement} ${event.name}")))
    }

    /**
     * Checks if the event has not occurred
     */
    fun checkEventNotSent(placement: TestData, event: SAEvent) {
        onView(withId(R.id.subtitleTextView))
            .check(matches(not(withSubstring("${placement.placement} ${event.name}"))))
    }

    fun checkForAnyEvent(placement: TestData, event: SAEvent, event2: SAEvent) {
        onView(withId(R.id.subtitleTextView))
            .perform(waitUntil(isDisplayed()))
            .perform(
                waitUntil(
                    anyOf(
                        withSubstring("${placement.placement} ${event.name}"),
                        withSubstring("${placement.placement} ${event2.name}")
                    )
                )
            )
    }

    fun waitForDisplay() {
        ViewTester()
            .waitForView(withId(R.id.subtitleTextView))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
    }

    fun waitForDisplay(color: Color) {
        ViewTester().waitForColorInCenter(color)
        Assert.assertTrue(
            TestColors.checkApproximatelyEqual(
                ScreenshotUtil.captureColorInCenter(),
                color
            )
        )
    }
}

fun listScreenRobot(func: ListScreenRobot.() -> Unit) =
    ListScreenRobot().apply { func() }
