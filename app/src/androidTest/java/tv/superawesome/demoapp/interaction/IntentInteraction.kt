package tv.superawesome.demoapp.interaction

import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import org.hamcrest.CoreMatchers

object IntentInteraction {

    fun checkUrlIsOpenInBrowser(uri: String) {
        val expectedIntent = CoreMatchers.allOf(
            IntentMatchers.hasAction(Intent.ACTION_VIEW),
            IntentMatchers.hasData(uri)
        )
        Intents.intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))
        Intents.intended(expectedIntent)
    }

    fun checkVastClickIsOpenInBrowser() {
        checkUrlIsOpenInBrowser("http://localhost:8080/vast/clickthrough")
    }
}
