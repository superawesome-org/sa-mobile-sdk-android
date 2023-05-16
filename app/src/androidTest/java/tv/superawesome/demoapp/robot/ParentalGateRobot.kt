package tv.superawesome.demoapp.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers
import tv.superawesome.demoapp.util.ParentalGateUtil
import tv.superawesome.demoapp.util.WireMockHelper.verifyQueryParamContains
import tv.superawesome.demoapp.util.getText

class ParentalGateRobot {
    fun checkEventForOpen() {
        verifyQueryParamContains("/event", "data", "parentalGateOpen")
    }

    fun checkEventForSuccess() {
        verifyQueryParamContains("/event", "data", "parentalGateSuccess")
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
}

fun parentalGateRobot(func: ParentalGateRobot.() -> Unit) =
    ParentalGateRobot().apply { func() }