package tv.superawesome.demoapp

import android.content.Intent
import android.graphics.Color
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.model.TestData
import tv.superawesome.demoapp.robot.bannerRobot
import tv.superawesome.demoapp.robot.bumperPageRobot
import tv.superawesome.demoapp.robot.interstitialScreenRobot
import tv.superawesome.demoapp.robot.listScreenRobot
import tv.superawesome.demoapp.robot.parentalGateRobot
import tv.superawesome.demoapp.robot.settingsScreenRobot
import tv.superawesome.demoapp.rules.RetryTestRule
import tv.superawesome.demoapp.settings.DataStore
import tv.superawesome.demoapp.util.IntentsHelper
import tv.superawesome.demoapp.util.IntentsHelper.stubIntents
import tv.superawesome.demoapp.util.TestColors
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalled
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalledWithQueryParam
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterstitialAd

@RunWith(AndroidJUnit4::class)
@SmallTest
class InterstitialUITest {
    @get:Rule
    var wireMockRule = WireMockRule(wireMockConfig().port(8080), false)

    @get:Rule
    val retryTestRule = RetryTestRule()

    @Before
    fun setup() {
        Intents.init()

        val ads = SAInterstitialAd::class.java.getDeclaredMethod("clearCache")
        ads.isAccessible = true
        ads.invoke(null)

        val base =
            tv.superawesome.sdk.publisher.SAInterstitialAd::class.java.getDeclaredMethod("clearCache")
        base.isAccessible = true
        base.invoke(null)

        wireMockRule.resetAll()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun test_standard_adLoading_placementId() {
        val testData = TestData.interstitialStandard
        testAdLoading(testData, TestColors.bannerYellow)
    }

    @Test
    fun test_standard_adLoading_placementId_lineItemId_creativeId() {
        val testData = TestData.interstitialStandardMulti
        testAdLoading(testData, TestColors.bannerYellow)
    }

    @Test
    fun test_ksf_adLoading() {
        val testData = TestData.interstitialKsf
        testAdLoading(testData, TestColors.ksfYellow)
    }

    private fun testAdLoading(testData: TestData, color: Color) {
        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            interstitialScreenRobot {
                waitForDisplay(color)
                tapOnCloseDelayed()
            }

            checkForEvent(testData, SAEvent.adLoaded)
            checkForEvent(testData, SAEvent.adShown)
        }
    }

    @Test
    fun test_adFailure_placementId() {
        val testData = TestData.interstitialKsf

        listScreenRobot {
            launchActivityWithFailureStub(testData)
            tapOnPlacement(testData)

            checkForEvent(testData, SAEvent.adFailedToLoad)
        }
    }

    @Test
    fun test_adFailure_placementId_lineItemId_creativeId() {
        val testData = TestData.interstitialStandardMulti

        listScreenRobot {
            launchActivityWithFailureStub(testData)
            tapOnPlacement(testData)

            checkForEvent(testData, SAEvent.adFailedToLoad)
        }
    }

    @Test
    fun test_adNotFound() {
        val testData = TestData(placementId = "87970", fileName = "not_found.json")

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            checkForAnyEvent(testData, SAEvent.adFailedToLoad, SAEvent.adEmpty)
        }
    }

    @Test
    fun test_standard_safeAdVisible() {
        val testData =
            TestData(placementId = "87892", fileName = "padlock/interstitial_standard_success_padlock_enabled.json")

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            interstitialScreenRobot {
                waitAndCheckSafeAdLogo()
            }
        }
    }

    @Test
    fun test_standard_CloseButton() {
        val testData = TestData(placementId = "87892", fileName = "interstitial_standard_success.json")

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            interstitialScreenRobot {
                tapOnCloseDelayed()
            }

            checkForEvent(testData, SAEvent.adClosed)
        }
    }

    @Test
    fun test_ksf_CloseButton() {
        val testData = TestData.interstitialKsf

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            interstitialScreenRobot {
                tapOnCloseDelayed()
            }

            checkForEvent(testData, SAEvent.adClosed)
        }
    }

    @Test
    fun test_bumper_enabled_from_settings() {
        // Given bumper page is enabled from settings
        IntentsHelper.stubIntentsForVast()
        val testData = TestData.interstitialStandard

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableBumperPage()
                }
            }

            tapOnPlacement(testData)

            interstitialScreenRobot {
                tapOnAdDelayed()

                bumperPageRobot {
                    waitForDisplay()
                    waitForFinish()

                    // Then view URL is redirected to browser
                    IntentsHelper.checkIntentsForVast()
                    verifyUrlPathCalled("/click")
                }

                tapOnCloseDelayed()
            }

            checkForEvent(testData, SAEvent.adClicked)
        }
    }

    @Test
    fun test_bumper_enabled_from_api() {
        // Given bumper page is enabled from api
        val testData = TestData(
            placementId = "87892",
            fileName = "interstitial_standard_enabled_success.json",
        )
        IntentsHelper.stubIntentsForUrl()

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            interstitialScreenRobot {
                tapOnAd()

                bumperPageRobot {
                    waitForDisplay()
                    waitForFinish()
                }
                IntentsHelper.checkIntentsForUrl()
                waitForDisplay()
            }
        }
    }

    @Test
    fun test_parental_gate_for_safe_ad_click() {
        val testData = TestData(
            placementId = "87892",
            fileName = "padlock/interstitial_standard_success_padlock_enabled.json",
        )

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableParentalGate()
                }
            }
            tapOnPlacement(testData)

            interstitialScreenRobot {
                tapOnSafeAdLogo()

                parentalGateRobot {
                    checkVisible()
                }
            }
        }
    }

    @Test
    fun test_standard_adAlreadyLoaded_callback() {
        val testData = TestData.interstitialStandard

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

    @Test
    fun test_ksf_adAlreadyLoaded_callback() {
        val testData = TestData.interstitialKsf

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

    // Events
    @Test
    fun test_standard_ad_impression_events() {
        val testData = TestData.interstitialStandard

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            interstitialScreenRobot {
                waitForImpression()

                verifyUrlPathCalled("/impression")
                verifyUrlPathCalledWithQueryParam(
                    "/event",
                    "data",
                    ".*viewable_impression.*"
                )
            }
        }
    }

    @Test
    fun test_ksf_ad_impression_events() {
        val testData = TestData.interstitialKsf

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            interstitialScreenRobot {
                waitForImpression()

                verifyUrlPathCalled("/impression")
                verifyUrlPathCalledWithQueryParam(
                    "/event",
                    "data",
                    ".*viewable_impression.*"
                )
            }
        }
    }

    @Test
    fun test_standard_ad_click_event() {
        IntentsHelper.stubIntentsForVast()
        val testData = TestData.interstitialStandard

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            interstitialScreenRobot {
                tapOnAd()

                IntentsHelper.checkIntentsForVast()
                waitAndTapOnClose()
            }

            verifyUrlPathCalled("/click")
            checkForEvent(testData, SAEvent.adClicked)
        }
    }

    @Test
    fun test_parental_gate_success_event() {
        if (DataStore.data.useBaseModule) return
        IntentsHelper.stubIntentsForUrl()
        openParentalGate()
        parentalGateRobot {
            solve()
            checkEventForSuccess()
        }
        IntentsHelper.checkIntentsForUrl()
    }

    @Test
    fun test_parental_gate_close_event() {
        if (DataStore.data.useBaseModule) return
        openParentalGate()

        parentalGateRobot {
            tapOnCancel()
            checkEventForClose()
        }
    }

    @Test
    fun test_parental_gate_failure_event() {
        if (DataStore.data.useBaseModule) return
        openParentalGate()

        parentalGateRobot {
            solveForFailure()
        }
    }

    @Test
    fun test_external_webpage_opening_on_click() {
        val testData = TestData.interstitialStandard
        stubIntents()

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            interstitialScreenRobot {
                tapOnAdDelayed()
            }

            // Then view URL is redirected to browser
            Intents.intended(hasAction(Intent.ACTION_VIEW))
            verifyUrlPathCalled("/click")
        }
    }

    @Test
    fun test_standard_CloseButtonWithNoDelay() {
        val testData = TestData.interstitialStandard

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseNoDelay()
                }
            }
            tapOnPlacement(testData)

            interstitialScreenRobot {
                checkCloseIsDisplayed()
            }
        }
    }

    @Test
    fun test_standard_CloseButtonWithDelay() {
        val testData = TestData.interstitialStandard

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseDelayed()
                }
            }

            tapOnPlacement(testData)

            interstitialScreenRobot {
                checkCloseAppearsDelayed()
            }
        }
    }

    @Test
    fun test_load_interstitial_with_additional_options() {
        val testData = TestData.interstitialStandard

        listScreenRobot {
            launchWithSuccessStub(testData, additionalOptions = mapOf("option1" to 123))
            tapOnPlacement(testData)
        }

        interstitialScreenRobot {
            verifyUrlPathCalledWithQueryParam(
                "/ad/${testData.placementId}",
                "option1",
                "123"
            )
        }
    }

    private fun openParentalGate() {
        val testData = TestData(
            placementId = "87892",
            fileName = "padlock/interstitial_standard_success_padlock_enabled.json",
        )

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableParentalGate()
                }
            }
            tapOnPlacement(testData)

            interstitialScreenRobot {
                tapOnAd()

                parentalGateRobot {
                    checkEventForOpen()
                }
            }
        }
    }
}
