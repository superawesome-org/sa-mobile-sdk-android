package tv.superawesome.demoapp.util

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import org.hamcrest.CoreMatchers

object IntentsHelper {

    private const val stubUrl = "http://localhost:8080/mock_website"
    private const val stubUrlVastClickThrough = "http://localhost:8080/vast/clickthrough"

    fun stubIntentsForVast() {
        stubIntentsForUrl(stubUrlVastClickThrough)
    }

    fun checkIntentsForVast() {
        checkIntentsForUrl(stubUrlVastClickThrough)
    }

    fun stubIntentsForUrl(url: String = stubUrl) {
        val expectedIntent = CoreMatchers.allOf(
            IntentMatchers.hasAction(Intent.ACTION_VIEW),
            IntentMatchers.hasData(url)
        )
        Intents
            .intending(expectedIntent)
            .respondWith(Instrumentation.ActivityResult(0, null))
    }

    fun checkIntentsForUrl(url: String = stubUrl) {
        val expectedIntent = CoreMatchers.allOf(
            IntentMatchers.hasAction(Intent.ACTION_VIEW),
            IntentMatchers.hasData(url)
        )
        Intents.intended(expectedIntent)
    }

    fun stubIntents() {
        Intents.intending(IntentMatchers.hasAction(Intent.ACTION_VIEW)).respondWith(
            Instrumentation.ActivityResult(
                Activity.RESULT_OK,
                Intent()
            )
        )
    }
}
