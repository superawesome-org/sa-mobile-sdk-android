package tv.superawesome.demoapp.runner

import android.app.UiAutomation
import android.os.ParcelFileDescriptor
import androidx.test.platform.app.InstrumentationRegistry

/**
 * Disable animations for the UI tests to have consistent test run
 */
class AnimationsDelegate : UITestRunner.TestRunnerDelegate {

    private var originalAnimationsValue = ANIMATION_SCALE_DEFAULT

    private val uiAutomation: UiAutomation
        get() = InstrumentationRegistry.getInstrumentation().uiAutomation

    override fun onCreate() {
        originalAnimationsValue = readDeviceAnimationsValue()
        setDeviceAnimationsValue(ANIMATION_SCALE_DISABLED)
    }

    override fun onFinish() {
        setDeviceAnimationsValue(originalAnimationsValue)
    }

    private fun readDeviceAnimationsValue(): Float {
        val pfd = uiAutomation.executeShellCommand("settings get global animator_duration_scale")
        pfd.checkError()
        return ParcelFileDescriptor.AutoCloseInputStream(pfd)
            .bufferedReader()
            .readLine()
            .toFloatOrNull() ?: ANIMATION_SCALE_DEFAULT
    }

    private fun setDeviceAnimationsValue(value: Float) {
        listOf(
            "settings put global animator_duration_scale $value",
            "settings put global transition_animation_scale $value",
            "settings put global window_animation_scale $value"
        ).forEach { command ->
            uiAutomation.executeShellCommand(command).run {
                checkError()
                close()
            }
        }
    }

    companion object {
        private const val ANIMATION_SCALE_DEFAULT = 1.0f
        private const val ANIMATION_SCALE_DISABLED = 0.0f
    }
}