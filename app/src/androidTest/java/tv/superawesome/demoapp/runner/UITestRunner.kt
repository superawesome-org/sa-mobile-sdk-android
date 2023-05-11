package tv.superawesome.demoapp.runner

import android.app.Application
import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner
import tv.superawesome.demoapp.MyApplication


/**
 * Custom test runner to disable animations and do other initialisations when needed
 */
class UITestRunner(
    private val delegates: List<TestRunnerDelegate> = listOf(AnimationsDelegate())
) : AndroidJUnitRunner() {

    override fun callApplicationOnCreate(application: Application) {
        if (application is MyApplication) {
            application.setupUITest()
        }
        super.callApplicationOnCreate(application)
        delegates.forEach { it.onCreate() }
    }

    override fun finish(resultCode: Int, results: Bundle?) {
        delegates.forEach { it.onFinish() }
        super.finish(resultCode, results)
    }

    interface TestRunnerDelegate {
        fun onCreate()
        fun onFinish()
    }
}