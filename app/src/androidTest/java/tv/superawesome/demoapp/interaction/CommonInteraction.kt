package tv.superawesome.demoapp.interaction

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.Matcher
import org.hamcrest.Matchers.anything
import tv.superawesome.demoapp.MainActivity
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.util.AdapterUtil
import tv.superawesome.demoapp.util.WireMockHelper.stubCommonPaths
import tv.superawesome.demoapp.util.WireMockHelper.stubFailure
import tv.superawesome.demoapp.util.WireMockHelper.stubSuccess
import tv.superawesome.demoapp.util.waitUntil
import java.util.concurrent.TimeoutException

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

    fun clickPlacementById(placementId: String) {
        onData(AdapterUtil.withPlacementId(placementId)).inAdapterView(withId(R.id.listView))
            .perform(click())
    }

    fun checkSubtitle(text: String) {
        onView(withId(R.id.subtitleTextView))
            .perform(waitUntil(isDisplayed()))
            .check(matches(withText(text)))
    }

    fun checkSubtitleContains(text: String, timeout: Long = 5000) {
        onView(withId(R.id.subtitleTextView))
            .perform(waitUntil(isDisplayed()))
            .perform(waitForText(text, timeout))
    }

    fun waitForText(text: String, timeout: Long): ViewAction = object : ViewAction {

        override fun getConstraints(): Matcher<View> {
            return isAssignableFrom(TextView::class.java)
        }

        override fun getDescription(): String {
            return "wait up to $timeout milliseconds for the view to have text $text"
        }

        override fun perform(uiController: UiController?, view: View?) {
            val endTime = System.currentTimeMillis() + timeout

            do {
                if ((view as? TextView)?.text?.contains(text) == true) return
                with(uiController) { this?.loopMainThreadForAtLeast(50) }
            } while (System.currentTimeMillis() < endTime)

            throw PerformException.Builder()
                .withActionDescription(description)
                .withCause(TimeoutException("Waited $timeout milliseconds"))
                .withViewDescription(HumanReadables.describe(view))
                .build()
        }
    }

    fun stubIntents() {
        Intents.intending(IntentMatchers.hasAction(Intent.ACTION_VIEW)).respondWith(
            Instrumentation.ActivityResult(
                Activity.RESULT_OK,
                Intent()
            )
        )
    }
}