package tv.superawesome.demoapp.util

import android.graphics.Color
import android.view.View
import android.view.ViewTreeObserver
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
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

fun waitUntil(matcher: Matcher<View>, timeout: Int = 600): ViewAction = object : ViewAction {
    override fun getConstraints(): Matcher<View> = CoreMatchers.any(View::class.java)

    override fun getDescription(): String = StringDescription().let {
        matcher.describeTo(it)
        "wait until: $it"
    }

    override fun perform(uiController: UiController, rootView: View) {
        uiController.loopMainThreadUntilIdle()
        val startTime = System.currentTimeMillis()
        val endTime = startTime + timeout

        do {
            // Iterate through all views on the screen and see if the view we are looking for is there already
            for (child in TreeIterables.breadthFirstViewTraversal(rootView)) {
                // found view with required ID
                if (matcher.matches(child)) {
                    return
                }
            }
            // Loops the main thread for a specified period of time.
            // Control may not return immediately, instead it'll return after the provided delay has passed and the queue is in an idle state again.
            uiController.loopMainThreadForAtLeast(100)
        } while (System.currentTimeMillis() < endTime) // in case of a timeout we throw an exception -> test fails
        throw PerformException.Builder()
            .withCause(TimeoutException())
            .withActionDescription(this.description)
            .withViewDescription(HumanReadables.describe(rootView))
            .build()
    }

//    override fun perform(uiController: UiController, view: View) {
//        if (!matcher.matches(view)) {
//            ViewPropertyChangeCallback(matcher, view).run {
//                try {
//                    IdlingRegistry.getInstance().register(this)
//                    view.viewTreeObserver.addOnDrawListener(this)
//                    uiController.loopMainThreadUntilIdle()
//                } finally {
//                    view.viewTreeObserver.removeOnDrawListener(this)
//                    IdlingRegistry.getInstance().unregister(this)
//                }
//            }
//        }
//    }
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
        waitMillis: Int = 90000,
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
        var lastColor:Color? = null

        for (i in 0..maxTries)
            try {
                tries++
                lastColor = ScreenshotUtil.captureColorInCenter()
                if (TestColors.checkApproximatelyEqual(color, lastColor)) {
                    return
                }
                sleep(waitMillisPerTry)
            } catch (e: Exception) {
                if (tries == maxTries) {
                    throw e
                }
                sleep(waitMillisPerTry)
            }
        throw Exception("Could not find color $color but last color was $lastColor")
    }
}
