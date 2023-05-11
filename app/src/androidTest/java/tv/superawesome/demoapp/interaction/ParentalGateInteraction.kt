package tv.superawesome.demoapp.interaction

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.endsWith
import tv.superawesome.demoapp.util.ParentalGateUtil
import tv.superawesome.demoapp.util.WireMockHelper.verifyQueryParamContains
import tv.superawesome.demoapp.util.getText
import tv.superawesome.demoapp.util.isVisible

object ParentalGateInteraction {
    fun testFailure() {
        onView(withClassName(endsWith("EditText")))
            .perform(replaceText("999999"))

        onView(withId(android.R.id.button1))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withText("OK"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click())

        // Then success event is sent
        verifyQueryParamContains("/event", "data", "parentalGateFail")
    }

    fun testClose() {
        onView(withText("Cancel"))
            .perform(click())

        // Then close event is sent
        verifyQueryParamContains("/event", "data", "parentalGateClose")
    }

    fun testSuccess() {
        // And finds a solution to the given challenge
        val inputText =
            onView(withSubstring("Please solve the following problem")).getText()
        val solution = ParentalGateUtil.solve(inputText)

        onView(withClassName(endsWith("EditText")))
            .perform(click(), typeText(solution.toString()))

        onView(withText("Continue"))
            .perform(click())

        // Then success event is sent
        verifyQueryParamContains("/event", "data", "parentalGateSuccess")
    }

    fun testOpen() {
        verifyQueryParamContains("/event", "data", "parentalGateOpen")
    }

    fun checkVisible() {
        onView(withText("Parental Gate"))
            .check(isVisible())
    }
}