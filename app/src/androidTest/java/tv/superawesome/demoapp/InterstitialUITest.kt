package tv.superawesome.demoapp

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.interaction.AdInteraction.testAdLoading
import tv.superawesome.demoapp.interaction.BumperInteraction
import tv.superawesome.demoapp.interaction.CommonInteraction
import tv.superawesome.demoapp.interaction.ParentalGateInteraction
import tv.superawesome.demoapp.interaction.SettingsInteraction
import tv.superawesome.demoapp.model.TestData
import tv.superawesome.demoapp.util.IntentsHelper.stubIntents
import tv.superawesome.demoapp.util.TestColors
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalled
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalledWithQueryParam
import tv.superawesome.demoapp.util.isVisible
import tv.superawesome.demoapp.util.waitUntil
import tv.superawesome.sdk.publisher.SAInterstitialAd

@RunWith(AndroidJUnit4::class)
@SmallTest
class InterstitialUITest {
    @get:Rule
    var wireMockRule = WireMockRule(wireMockConfig().port(8080), false)

    @Before
    fun setup() {
        Intents.init()

        val ads = SAInterstitialAd::class.java.getDeclaredMethod("clearCache")
        ads.isAccessible = true
        ads.invoke(null)
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun test_standard_adLoading() {
        val testData = TestData.interstitialStandard
        testAdLoading(testData, TestColors.bannerYellow)

        CommonInteraction.waitForCloseButtonThenClick()

        CommonInteraction.checkSubtitleContains("${testData.placement} adLoaded")
        CommonInteraction.checkSubtitleContains("${testData.placement} adShown")
    }

    @Test
    fun test_ksf_adLoading() {
        val testData = TestData.interstitialKsf
        testAdLoading(testData, TestColors.ksfYellow)

        CommonInteraction.waitForCloseButtonThenClick()

        CommonInteraction.checkSubtitleContains("${testData.placement} adLoaded")
        CommonInteraction.checkSubtitleContains("${testData.placement} adShown")
    }

    @Test
    fun test_adFailure() {
        val testData = TestData.interstitialKsf
        CommonInteraction.launchActivityWithFailureStub(testData)

        CommonInteraction.clickItemAt(testData)

        CommonInteraction.checkSubtitleContains("${testData.placement} adFailedToLoad")
    }

    @Test
    fun test_adNotFound() {
        val testData = TestData("87970", "not_found.json")
        CommonInteraction.launchActivityWithSuccessStub(testData)

        CommonInteraction.clickItemAt(testData)

        CommonInteraction.checkSubtitleContains("${testData.placement} adEmpty")
    }

    @Test
    fun test_standard_safeAdVisible() {
        val testData =
            TestData("87892", "padlock/interstitial_standard_success_padlock_enabled.json")
        CommonInteraction.launchActivityWithSuccessStub(testData)

        CommonInteraction.clickItemAt(testData)

        CommonInteraction.waitAndCheckSafeAdLogo()
    }

    @Test
    fun test_standard_CloseButton() {
        val testData = TestData("87892", "interstitial_standard_success.json")
        CommonInteraction.launchActivityWithSuccessStub(testData)

        CommonInteraction.clickItemAt(testData)

        CommonInteraction.waitForCloseButtonThenClick()

        CommonInteraction.checkSubtitleContains("${testData.placement} adClosed")
    }

    @Test
    fun test_ksf_CloseButton() {
        val testData = TestData.interstitialKsf
        CommonInteraction.launchActivityWithSuccessStub(testData)

        CommonInteraction.clickItemAt(testData)

        CommonInteraction.waitForCloseButtonThenClick()

        CommonInteraction.checkSubtitleContains("${testData.placement} adClosed")
    }

    @Test
    fun test_bumper_enabled_from_settings() {
        // Given bumper page is enabled from settings
        val testData = TestData.interstitialStandard
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.enableBumper()
        }
        CommonInteraction.clickItemAt(testData)

        // When ad is clicked
        CommonInteraction.waitForAdContentThenClick()

        // Then bumper page is shown
        BumperInteraction.waitUntilBumper()

        // And view URL is redirected to browser
        Thread.sleep(4500)
        Intents.intended(hasAction(Intent.ACTION_VIEW))
        verifyUrlPathCalled("/click")
    }

    @Test
    fun test_bumper_enabled_from_api() {
        // Given bumper page is enabled from api
        val testData = TestData("87892", "interstitial_standard_enabled_success.json")
        stubIntents()
        CommonInteraction.launchActivityWithSuccessStub(testData)
        CommonInteraction.clickItemAt(testData)

        // When ad is clicked
        onView(withContentDescription("Ad content")).perform(click())

        // Then bumper page is shown
        BumperInteraction.waitUntilBumper()
    }

    @Test
    fun test_parental_gate_for_safe_ad_click() {
        val testData =
            TestData("87892", "padlock/interstitial_standard_success_padlock_enabled.json")
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.enableParentalGate()
        }
        CommonInteraction.clickItemAt(testData)

        CommonInteraction.waitForSafeAdLogoThenClick()

        ParentalGateInteraction.checkVisible()
    }

    @Test
    fun test_standard_adAlreadyLoaded_callback() {
        val testData = TestData.interstitialStandard
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.disablePlay()
        }

        CommonInteraction.clickItemAt(testData)
        CommonInteraction.clickItemAt(testData)

        CommonInteraction.checkSubtitleContains("${testData.placement} adAlreadyLoaded")
    }

    @Test
    fun test_ksf_adAlreadyLoaded_callback() {
        val testData = TestData.interstitialKsf
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.disablePlay()
        }

        CommonInteraction.clickItemAt(testData)
        CommonInteraction.clickItemAt(testData)

        CommonInteraction.checkSubtitleContains("${testData.placement} adAlreadyLoaded")
    }

    // Events
    @Test
    fun test_standard_ad_impression_events() {
        //Given
        val testData = TestData.interstitialStandard
        CommonInteraction.launchActivityWithSuccessStub(testData)
        CommonInteraction.clickItemAt(testData)

        // When
        Thread.sleep(2500)

        // Then
        verifyUrlPathCalled("/impression")
        verifyUrlPathCalledWithQueryParam(
            "/event",
            "data",
            ".*viewable_impression.*"
        )
    }

    @Test
    fun test_ksf_ad_impression_events() {
        //Given
        val testData = TestData.interstitialKsf
        CommonInteraction.launchActivityWithSuccessStub(testData)

        CommonInteraction.clickItemAt(testData)

        // When
        Thread.sleep(2500)

        // Then
        verifyUrlPathCalled("/impression")
        verifyUrlPathCalledWithQueryParam(
            "/event",
            "data",
            ".*viewable_impression.*"
        )
    }

    @Test
    fun test_standard_ad_click_event() {
        // Given
        val testData = TestData.interstitialStandard
        stubIntents()
        CommonInteraction.launchActivityWithSuccessStub(testData)
        CommonInteraction.clickItemAt(testData)

        // When
        onView(withContentDescription("Ad content"))
            .perform(click())
        CommonInteraction.waitForCloseButtonThenClick()

        // Then
        CommonInteraction.checkSubtitleContains("${testData.placement} adClicked")
        verifyUrlPathCalled("/click")
    }

    @Test
    fun test_parental_gate_success_event() {
        stubIntents()
        openParentalGate()
        ParentalGateInteraction.testSuccess()
        Intents.intended(hasAction(Intent.ACTION_VIEW))
    }

    @Test
    fun test_parental_gate_close_event() {
        openParentalGate()
        ParentalGateInteraction.testClose()
    }

    @Test
    fun test_parental_gate_failure_event() {
        openParentalGate()
        ParentalGateInteraction.testFailure()
    }

    @Test
    fun test_external_webpage_opening_on_click() {
        // Given
        val testData = TestData.interstitialStandard
        stubIntents()
        CommonInteraction.launchActivityWithSuccessStub(testData)
        CommonInteraction.clickItemAt(testData)

        // When ad is clicked
        CommonInteraction.waitForAdContentThenClick()

        // Then view URL is redirected to browser
        Intents.intended(hasAction(Intent.ACTION_VIEW))
        verifyUrlPathCalled("/click")
    }

    @Test
    fun test_standard_CloseButtonWithNoDelay() {
        val testData = TestData.interstitialStandard
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.closeNoDelay()
        }

        CommonInteraction.clickItemAt(testData)

        ViewTester()
            .waitForView(withContentDescription("Close"))
            .perform(waitUntil(isDisplayed()))
            .check(isVisible())
    }

    @Test
    fun test_standard_CloseButtonWithDelay() {
        val testData = TestData.interstitialStandard
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.closeDelayed()
        }

        CommonInteraction.clickItemAt(testData)

        CommonInteraction.waitForCloseButtonWithDelay()
    }

    private fun openParentalGate() {
        val testData =
            TestData("87892", "padlock/interstitial_standard_success_padlock_enabled.json")
        CommonInteraction.launchActivityWithSuccessStub(testData) {
            SettingsInteraction.enableParentalGate()
        }
        CommonInteraction.clickItemAt(testData)

        CommonInteraction.waitForSafeAdLogoThenClick()

        // Then parental gate open event is triggered
        ParentalGateInteraction.testOpen()
    }
}
