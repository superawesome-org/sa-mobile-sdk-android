package tv.superawesome.demoapp.interaction

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.endsWith
import tv.superawesome.demoapp.util.ParentalGateUtil
import tv.superawesome.demoapp.util.WireMockHelper.verifyQueryParamContains
import tv.superawesome.demoapp.util.getText
import tv.superawesome.demoapp.util.isVisible

object ParentalGateInteraction {
    fun testFailure() {
        onView(withClassName(endsWith("EditText")))
            .perform(click(), typeText("999999"))

        onView(withText("Continue"))
            .perform(click())

        onView(withText("Ok"))
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
            onView(ViewMatchers.withSubstring("Please solve the following problem")).getText()
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