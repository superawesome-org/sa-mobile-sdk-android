package tv.superawesome.demoapp.util

import android.graphics.Color
import android.view.View
import android.view.ViewTreeObserver
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.hamcrest.StringDescription
import java.lang.Thread.sleep
import java.util.concurrent.TimeoutException

private class ViewPropertyChangeCallback(
    private val matcher: Matcher<View>,
    private val view: View,
) : IdlingResource, ViewTreeObserver.OnDrawListener {
    private lateinit var callback: IdlingResource.ResourceCallback
    private var matched = false

    override fun getName() = "ViewPropertyChangeCallback"

    override fun isIdleNow() = matched

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.callback = callback
    }

    override fun onDraw() {
        matched = matcher.matches(view)
        callback.onTransitionToIdle()
    }
}

fun waitUntil(matcher: Matcher<View>): ViewAction = object : ViewAction {
    override fun getConstraints(): Matcher<View> = CoreMatchers.any(View::class.java)

    override fun getDescription(): String = StringDescription().let {
        matcher.describeTo(it)
        "wait until: $it"
    }

    override fun perform(uiController: UiController, view: View) {
        if (!matcher.matches(view)) {
            ViewPropertyChangeCallback(matcher, view).run {
                try {
                    IdlingRegistry.getInstance().register(this)
                    view.viewTreeObserver.addOnDrawListener(this)
                    uiController.loopMainThreadUntilIdle()
                } finally {
                    view.viewTreeObserver.removeOnDrawListener(this)
                    IdlingRegistry.getInstance().unregister(this)
                }
            }
        }
    }
}

fun waitForViewMatcherOneTime(viewMatcher: Matcher<View>): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = isRoot()

        override fun getDescription(): String {
            val matcherDescription = StringDescription()
            viewMatcher.describeTo(matcherDescription)
            return "wait for a specific $matcherDescription."
        }

        override fun perform(uiController: UiController, rootView: View) {
            for (child in TreeIterables.breadthFirstViewTraversal(rootView)) {
                if (viewMatcher.matches(child)) {
                    return
                }
            }
            throw PerformException.Builder()
                .withCause(TimeoutException())
                .withActionDescription(this.description)
                .withViewDescription(HumanReadables.describe(rootView))
                .build()
        }
    }
}

open class ViewTester {
    fun waitForView(
        viewMatcher: Matcher<View>,
        waitMillis: Int = 10000,
        waitMillisPerTry: Long = 100
    ): ViewInteraction {
        val maxTries = waitMillis / waitMillisPerTry.toInt()
        var tries = 0

        for (i in 0..maxTries)
            try {
                tries++
                onView(isRoot()).perform(waitForViewMatcherOneTime(viewMatcher))
                return onView(viewMatcher)
            } catch (e: Exception) {
                if (tries == maxTries) {
                    throw e
                }
                sleep(waitMillisPerTry)
            }
        throw Exception("Could not find view for $viewMatcher")
    }

    fun waitForColorInCenter(
        color: Color,
        waitMillis: Int = 15000,
        waitMillisPerTry: Long = 500
    ) {
        val maxTries = waitMillis / waitMillisPerTry.toInt()
        var tries = 0

        for (i in 0..maxTries)
            try {
                tries++
                if (color == ScreenshotUtil.captureColorInCenter()) {
                    return
                }
                sleep(waitMillisPerTry)
            } catch (e: Exception) {
                if (tries == maxTries) {
                    throw e
                }
                sleep(waitMillisPerTry)
            }
        val expectedColor = ScreenshotUtil.captureColorInCenter()
        throw Exception("Could not find color $color. Found: $expectedColor")
    }
}