package tv.superawesome.demoapp.util

import android.graphics.Color
import kotlin.math.abs

object TestColors {
    val bannerYellow = Color.valueOf(0.96862745f, 0.8862745f, 0.41960785f)
    val vastYellow = Color.valueOf(0.9647059f, 0.90588236f, 0.46666667f)
    val vpaidYellow = Color.valueOf(0.96862745f, 0.8862745f, 0.4627451f)
    val vpaidClickBlue = Color.valueOf(0.0f, 0.0f, 1.0f)

    fun checkApproximatelyEqual(givenColor: Color?, targetColor: Color?): Boolean {
        if (givenColor == null || targetColor == null) return false
        val threshold = 0.01
        return abs(givenColor.red() - targetColor.red()) <= threshold
                && abs(givenColor.red() - targetColor.red()) <= threshold
                && abs(givenColor.red() - targetColor.red()) <= threshold
    }
}
