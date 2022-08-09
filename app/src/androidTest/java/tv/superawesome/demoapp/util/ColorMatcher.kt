package tv.superawesome.demoapp.util

import android.graphics.Color
import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher


object ColorMatcher {
    fun matchesColor(expectedColor: Color): Matcher<View> =
        object : BoundedMatcher<View, View>(View::class.java) {
            override fun matchesSafely(item: View): Boolean {
                val capturedColor = ScreenshotUtil.captureColorInCenter(item)
                return capturedColor == expectedColor
            }

            override fun describeTo(description: Description) {
                description.appendText("Color not found")
            }
        }
}