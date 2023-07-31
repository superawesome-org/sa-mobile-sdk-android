package tv.superawesome.demoapp.robot

import android.R
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers
import org.hamcrest.Matchers.anyOf
import tv.superawesome.demoapp.util.ParentalGateUtil
import tv.superawesome.demoapp.util.WireMockHelper.verifyQueryParamContains
import tv.superawesome.demoapp.util.getText
import tv.superawesome.demoapp.util.isVisible

class ParentalGateRobot {
    fun checkVisible() {
        onView(withText("Parental Gate"))
            .check(isVisible())
    }

    fun checkEventForOpen() {
        verifyQueryParamContains("/event", "data", "parentalGateOpen")
    }

    fun checkEventForSuccess() {
        verifyQueryParamContains("/event", "data", "parentalGateSuccess")
    }

    fun checkEventForClose() {
        verifyQueryParamContains("/event", "data", "parentalGateClose")
    }

    private fun checkEventForFailure() {
        verifyQueryParamContains("/event", "data", "parentalGateFail")
    }

    fun solveForFailure() {
        onView(withClassName(Matchers.endsWith("EditText")))
            .perform(ViewActions.replaceText("999999"))

        onView(ViewMatchers.withId(R.id.button1))
            .inRoot(RootMatchers.isDialog())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(click())

        onView(anyOf(withText("OK"), withText("Ok")))
            .inRoot(RootMatchers.isDialog())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(click())

        checkEventForFailure()
    }

    fun solve() {
        // Find a solution to the given challenge
        val inputText =
            onView(withSubstring("Please solve the following problem")).getText()
        val solution = ParentalGateUtil.solve(inputText)

        onView(withClassName(Matchers.endsWith("EditText")))
            .perform(click(), typeText(solution.toString()))

        onView(withText("Continue"))
            .perform(click())
    }

    fun tapOnCancel() {
        onView(withText("Cancel"))
            .perform(click())
    }
}

fun parentalGateRobot(func: ParentalGateRobot.() -> Unit) =
    ParentalGateRobot().apply { func() }