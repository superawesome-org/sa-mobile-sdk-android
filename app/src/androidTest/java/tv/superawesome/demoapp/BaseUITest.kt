package tv.superawesome.demoapp

import android.graphics.Color
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.junit.runner.RunWith
import tv.superawesome.demoapp.model.TestData
import tv.superawesome.demoapp.robot.listScreenRobot
import tv.superawesome.demoapp.robot.settingsScreenRobot
import tv.superawesome.demoapp.robot.videoScreenRobot
import tv.superawesome.demoapp.rules.RetryTestRule
import tv.superawesome.sdk.publisher.SAEvent

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

    fun testAdLoading(testData: TestData, color: Color) {
        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay(color)
                waitAndTapOnClose()
            }

            checkForEvent(testData, SAEvent.adLoaded)
            checkForEvent(testData, SAEvent.adShown)
        }
    }

    fun testAdAlreadyLoaded(testData: TestData) {
        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnDisablePlay()
                }
            }
            tapOnPlacement(testData)
            tapOnPlacement(testData)

            checkForEvent(testData, SAEvent.adAlreadyLoaded)
        }
    }

    companion object {
        private const val httpPortNumber = 8080
        private const val isVerboseLoggingEnabled = false
    }
}
