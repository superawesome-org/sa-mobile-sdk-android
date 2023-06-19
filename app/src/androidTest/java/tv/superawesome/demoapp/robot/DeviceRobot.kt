package tv.superawesome.demoapp.robot

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice

class DeviceRobot {
    fun clickOnScreen(x: Int, y: Int) {
        UiDevice.getInstance(getInstrumentation()).click(x, y)
    }
}

fun deviceRobot(func: DeviceRobot.() -> Unit) =
    DeviceRobot().apply { func() }