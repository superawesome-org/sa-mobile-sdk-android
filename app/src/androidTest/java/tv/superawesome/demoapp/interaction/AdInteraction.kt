package tv.superawesome.demoapp.interaction

import android.graphics.Color
import org.junit.Assert.assertTrue
import tv.superawesome.demoapp.model.TestData
import tv.superawesome.demoapp.util.ScreenshotUtil
import tv.superawesome.demoapp.util.TestColors
import tv.superawesome.demoapp.util.ViewTester

object AdInteraction {
    fun testAdLoading(testData: TestData, color: Color) {
        CommonInteraction.launchActivityWithSuccessStub(testData)
        Thread.sleep(200)
        assertColor(testData, color)
    }

    private fun assertColor(testData: TestData, targetColor: Color) {
        CommonInteraction.clickItemAt(testData)
        ViewTester().waitForColorInCenter(targetColor)
        assertTrue(
            TestColors.checkApproximatelyEqual(
                ScreenshotUtil.captureColorInCenter(),
                targetColor
            )
        )
    }
}
