package tv.superawesome.demoapp

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import tv.superawesome.demoapp.rules.RetryTestRule
import tv.superawesome.demoapp.util.FileUtils
import java.io.File
import java.io.FileOutputStream

@RunWith(AndroidJUnit4::class)
@SmallTest
abstract class BaseUITest {

    @get:Rule
    val retryTestRule = RetryTestRule()

    @Rule
    @JvmField val wireMockRule: WireMockRule

    init {
        val context = InstrumentationRegistry.getInstrumentation().context
        val keyStoreFile = File(keyStorePath, keyStoreName)
        val inStream = context.assets.open(keyStoreName)
        val outStream = FileOutputStream(keyStoreFile)
        FileUtils.copyFile(inStream, outStream)
        wireMockRule = WireMockRule(
            WireMockConfiguration.wireMockConfig()
                .port(httpPortNumber)
                .httpsPort(httpsPortNumber)
                .bindAddress(httpsAddress)
                .notifier(ConsoleNotifier(isVerboseLoggingEnabled))
                .keystorePath(keyStoreFile.absolutePath)
                .keystoreType(keyStoreFileType)
                .keystorePassword(keyStorePassword),
            false,
        )
    }

    @Before
    open fun setup() {
        Intents.init()
        wireMockRule.resetAll()
    }

    @After
    open fun tearDown() {
        Intents.release()
    }

    companion object {
        private const val keyStorePath = "/sdcard/Documents"
        private const val keyStoreName = "wiremock.bks"
        private const val keyStorePassword = "127.0.0.1"
        private const val keyStoreFileType = "BKS"
        private const val httpsAddress = "127.0.0.1"
        private const val httpsPortNumber = 8443
        private const val httpPortNumber = 8080
        private const val isVerboseLoggingEnabled = false
    }
}
