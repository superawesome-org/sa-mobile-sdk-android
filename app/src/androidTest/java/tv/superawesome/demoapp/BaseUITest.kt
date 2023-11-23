package tv.superawesome.demoapp

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.junit.runner.RunWith
import tv.superawesome.demoapp.rules.RetryTestRule

@RunWith(AndroidJUnit4::class)
@SmallTest
abstract class BaseUITest {

    @get:Rule
    val retryTestRule = RetryTestRule()

    @Rule
    @JvmField
    val wireMockRule: WireMockRule = WireMockRule(
        WireMockConfiguration.wireMockConfig()
            .port(httpPortNumber)
            .stubCorsEnabled(true)
            .notifier(ConsoleNotifier(isVerboseLoggingEnabled)),
        false,
    )

    open fun setup() {
        Intents.init()
        wireMockRule.resetAll()
    }

    open fun tearDown() {
        Intents.release()
    }

    companion object {
        private const val httpPortNumber = 8080
        private const val isVerboseLoggingEnabled = false
    }
}
