package tv.superawesome.demoapp.util

import android.app.Activity
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage

object EspressoUtils {
    inline fun <reified T : Activity> getCurrentActivity(): T? {
        var currentActivity: Activity? = null
        getInstrumentation().runOnMainSync {
            run {
                currentActivity = ActivityLifecycleMonitorRegistry
                    .getInstance()
                    .getActivitiesInStage(Stage.RESUMED)
                    .elementAtOrNull(0)
            }
        }

        return currentActivity as? T
    }
}