package tv.superawesome.demoapp.interaction

import android.graphics.Color
import org.junit.Assert.assertEquals
import tv.superawesome.demoapp.util.ScreenshotUtil
import tv.superawesome.demoapp.util.ViewTester

object AdInteraction {

    fun testAdLoading(placement: String, fileName: String, position: Int, color: Color) {
        CommonInteraction.launchActivityWithSuccessStub(placement, fileName)
        assertColor(position, color)
    }

    fun assertColor(position: Int, color: Color) {
        CommonInteraction.clickItemAt(position)
        ViewTester().waitForColorInCenter(color)
        assertEquals(ScreenshotUtil.captureColorInCenter(), color)
    }
}