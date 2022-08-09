package tv.superawesome.demoapp.util

import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers

fun isVisible() = getViewAssertion(ViewMatchers.Visibility.VISIBLE)

private fun getViewAssertion(visibility: ViewMatchers.Visibility): ViewAssertion? =
    ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(visibility))