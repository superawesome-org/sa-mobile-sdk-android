package tv.superawesome.demoapp

import android.graphics.Color
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
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
import tv.superawesome.demoapp.robot.bumperPageRobot
import tv.superawesome.demoapp.robot.listScreenRobot
import tv.superawesome.demoapp.robot.parentalGateRobot
import tv.superawesome.demoapp.robot.settingsScreenRobot
import tv.superawesome.demoapp.robot.videoScreenRobot
import tv.superawesome.demoapp.robot.videoWarningRobot
import tv.superawesome.demoapp.rules.RetryTestRule
import tv.superawesome.demoapp.settings.DataStore
import tv.superawesome.demoapp.util.IntentsHelper
import tv.superawesome.demoapp.util.TestColors
import tv.superawesome.demoapp.util.WireMockHelper.stubFailingVPAIDJavaScript
import tv.superawesome.demoapp.util.WireMockHelper.verifyQueryParamContains
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalled
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalledWithQueryParam
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAVideoAd

@RunWith(AndroidJUnit4::class)
@SmallTest
class VideoAdUITest {

    @get:Rule
    var wireMockRule = WireMockRule(wireMockConfig().port(8080), false)

    @get:Rule
    val retryTestRule = RetryTestRule()

    @Before
    fun setup() {
        Intents.init()

        val ads = SAVideoAd::class.java.getDeclaredMethod("clearCache")
        ads.isAccessible = true
        ads.invoke(null)

        val base =
            tv.superawesome.sdk.publisher.SAVideoAd::class.java.getDeclaredMethod("clearCache")
        base.isAccessible = true
        base.invoke(null)

        wireMockRule.resetAll()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun test_closeAtEndEnabled_closeBeforeEnds_receiveOnlyAdClosedEvent() {
        val testData = TestData.videoDirect

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseDelayed()
                }
            }

            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndTapOnClose()
            }

            checkForEvent(testData, SAEvent.adClosed)
            checkEventNotSent(testData, SAEvent.adEnded)
        }
    }

    @Test
    fun test_closeAtEndDisabled_waitVideoEnds_receiveAdClosedEvent() {
        val testData = TestData.videoDirect

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnDisableCloseAtEnd()
                    tapOnCloseHidden()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndTapOnClose()
            }

            checkForEvent(testData, SAEvent.adClosed)
            checkForEvent(testData, SAEvent.adEnded)
        }
    }

    @Test
    fun test_standard_CloseButtonWithNoDelay() {
        val testData = TestData.videoDirect

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseNoDelay()
                }
            }

            tapOnPlacement(testData)

            videoScreenRobot {
                tapOnClose()
            }

            checkForEvent(testData, SAEvent.adClosed)
        }
    }

    @Test
    fun test_standard_CloseButtonWithDelay() {
        val testData = TestData.videoDirect

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseDelayed()
                }
            }

            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndTapOnClose()
            }

            checkForEvent(testData, SAEvent.adClosed)
            verifyUrlPathCalled("/event")
        }
    }

    @Test
    fun test_vast_CloseButtonWithNoDelay() {
        val testData = TestData.videoVast

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseNoDelay()
                }
            }

            tapOnPlacement(testData)

            videoScreenRobot {
                tapOnClose()
            }

            checkForEvent(testData, SAEvent.adClosed)
        }
    }

    @Test
    fun test_vast_CloseButtonWithDelay() {
        val testData = TestData.videoVast

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseDelayed()
                }
            }

            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndTapOnClose()
            }

            checkForEvent(testData, SAEvent.adClosed)
        }
    }

    @Test
    fun test_vpaid_CloseButton() {
        val testData = TestData.videoVpaid

        listScreenRobot {
            launchWithSuccessStub(testData)

            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndTapOnClose()
            }

            checkForEvent(testData, SAEvent.adClosed)
        }
    }

    @Test
    fun test_vpaid_hidden_CloseButton_is_hidden_until_video_ends() {
        val testData = TestData.videoVpaidGreyBox

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseHidden()
                    tapOnDisableCloseAtEnd()
                }
            }

            tapOnPlacement(testData)

            checkForEvent(testData, SAEvent.adEnded)
        }
    }

    @Test
    fun test_auto_close_on_finish() {
        val testData = TestData.videoDirect

        listScreenRobot {
            launchWithSuccessStub(testData)

            tapOnPlacement(testData)

            // Wait for returning to the list screen
            waitForDisplay()

            checkForEvent(testData, SAEvent.adEnded)

            // Then all of the events are triggered
            verifyQueryParamContains("/video/tracking", "event", "start")
            verifyQueryParamContains("/video/tracking", "event", "creativeView")
            verifyQueryParamContains("/video/tracking", "event", "firstQuartile")
            verifyQueryParamContains("/video/tracking", "event", "midpoint")
            verifyQueryParamContains("/video/tracking", "event", "thirdQuartile")
            verifyQueryParamContains("/video/tracking", "event", "complete")
        }
    }

    private fun testAdLoading(testData: TestData, color: Color) {
        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay(color)

                waitAndTapOnClose()
            }

            checkForEvent(testData, SAEvent.adLoaded)

        }
    }

    @Test
    fun test_vast_adLoading_placementId() {
        val testData = TestData.videoVast
        testAdLoading(testData, TestColors.vastYellow)

        listScreenRobot {
            checkForEvent(testData, SAEvent.adShown)
        }
    }

    @Test
    fun test_vast_adLoading_placementId_lineItemId_creativeId() {
        val testData = TestData.videoVastMulti
        testAdLoading(testData, TestColors.vastYellow)

        listScreenRobot {
            checkForEvent(testData, SAEvent.adShown)
        }
    }

    @Test
    fun test_vpaid_adLoading_placementId() {
        val testData = TestData.videoVpaid
        testAdLoading(testData, TestColors.vpaidYellow)
    }

    @Test
    fun test_vpaid_adLoading_placementId_lineItemId_creativeId() {
        val testData = TestData.videoVpaidMulti
        testAdLoading(testData, TestColors.vpaidYellow)
    }

    @Test
    fun test_direct_adLoading() {
        val testData = TestData.videoDirect
        testAdLoading(testData, TestColors.vastYellow)

        listScreenRobot {
            checkForEvent(testData, SAEvent.adShown)
        }
    }

    @Test
    fun test_adFailure() {
        val testData = TestData.videoDirect

        listScreenRobot {
            launchActivityWithFailureStub(testData)
            tapOnPlacement(testData)

            checkForEvent(testData, SAEvent.adFailedToLoad)
        }
    }

    @Test
    fun test_adNotFound() {
        val testData = TestData(placementId = "87969", fileName = "not_found.json")

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            checkForAnyEvent(testData, SAEvent.adFailedToLoad, SAEvent.adEmpty)
        }
    }

    @Test
    fun test_direct_safeAdVisible() {
        val testData = TestData.videoPadlock

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndCheckSafeAdLogo()
            }
        }
    }

    @Test
    fun test_vast_safeAdVisible() {
        val testData = TestData(
            placementId = "88406",
            fileName = "padlock/video_vast_success_padlock_enabled.json",
        )

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndCheckSafeAdLogo()
            }
        }
    }

    @Test
    fun test_bumper_enabled_from_settings() {
        if (DataStore.data.useBaseModule) return
        IntentsHelper.stubIntentsForVast()
        val testData = TestData.videoDirect

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableBumperPage()
                    tapOnCloseNoDelay()
                }
            }

            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay()

                tapOnAd()

                bumperPageRobot {
                    waitForDisplay()
                    waitForFinish()

                    // Then view URL is redirected to browser
                    IntentsHelper.checkIntentsForVast()
                    verifyUrlPathCalled("/video/click")
                }

                tapOnClose()
            }

            checkAdHasBeenLoadedShownClickedClosed(testData)
        }
    }

    @Test
    fun test_bumper_enabled_from_api() {
        if (DataStore.data.useBaseModule) return

        // Given bumper page is enabled from api
        IntentsHelper.stubIntentsForVast()
        val testData = TestData(
            placementId = "87969",
            fileName = "video_direct_enabled_success.json",
        )

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseNoDelay()
                }
            }

            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay()

                tapOnAd()

                bumperPageRobot {
                    waitForDisplay()
                    waitForFinish()

                    // Then view URL is redirected to browser
                    IntentsHelper.checkIntentsForVast()
                    verifyUrlPathCalled("/video/click")
                }

                tapOnClose()
            }

            checkAdHasBeenLoadedShownClickedClosed(testData)
        }
    }

    @Test
    fun test_parental_gate_for_safe_ad_click() {
        val testData = TestData.videoPadlock

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableParentalGate()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                tapOnSafeAdLogo()

                parentalGateRobot {
                    checkVisible()
                }
            }
        }
    }

    @Test
    fun test_parental_gate_for_ad_click() {
        val testData = TestData(
            placementId = "87969",
            fileName = "padlock/video_direct_success_padlock_enabled.json",
        )

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableParentalGate()
                }
            }

            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay()
                tapOnAd()

                parentalGateRobot {
                    checkVisible()
                }
            }
        }
    }

    private fun testAdAlreadyLoaded(testData: TestData) {
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
    fun test_direct_adAlreadyLoaded_callback() {
        val testData = TestData.videoDirect
        testAdAlreadyLoaded(testData)
    }

    @Test
    fun test_vast_adAlreadyLoaded_callback() {
        val testData = TestData.videoVast
        testAdAlreadyLoaded(testData)
    }

    @Test
    fun test_vpaid_adAlreadyLoaded_callback() {
        val testData = TestData.videoVpaid
        testAdAlreadyLoaded(testData)
    }

    // Events
    @Test
    fun test_direct_ad_impression_events() {
        val testData = TestData.videoDirect

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay()
                waitForImpression()

                verifyUrlPathCalled("/vast/impression")
                verifyUrlPathCalledWithQueryParam(
                    "/event",
                    "data",
                    ".*viewable_impression.*"
                )
            }
        }
    }

    @Test
    fun test_direct_ad_click_event() {
        val testData = TestData.videoDirect
        IntentsHelper.stubIntentsForVast()

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseNoDelay()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay()
                tapOnAd()

                // Then
                verifyUrlPathCalled("/vast/click")
                IntentsHelper.checkIntentsForVast()

                tapOnClose()
            }

            waitForDisplay()
            checkAdHasBeenLoadedShownClickedClosed(testData)
        }
    }

    @Test
    fun test_direct_ad_dwell_time() {
        val testData = TestData.videoDirect

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay()
                waitForImpression()

                verifyUrlPathCalledWithQueryParam(
                    "/event",
                    "type",
                    ".*viewTime.*"
                )
            }
        }
    }

    @Test
    fun test_vast_ad_impression_events() {
        val testData = TestData.videoVast

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay()
                waitForImpression()

                verifyUrlPathCalled("/vast/impression")
                verifyUrlPathCalledWithQueryParam(
                    "/event",
                    "data",
                    ".*viewable_impression.*"
                )
            }
        }
    }

    @Test
    fun test_vast_ad_dwell_time() {
        val testData = TestData.videoVast

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay()
                waitForImpression()

                verifyUrlPathCalledWithQueryParam(
                    "/event",
                    "type",
                    ".*viewTime.*"
                )
            }
        }
    }

    @Test
    fun test_parental_gate_success_event() {
        val testData = TestData.videoDirect
        IntentsHelper.stubIntentsForVast()

        openParentalGate()
        parentalGateRobot {
            solve()
            checkEventForSuccess()
        }
        IntentsHelper.checkIntentsForVast()

        videoScreenRobot {
            waitForDisplay()
            tapOnClose()
        }

        listScreenRobot {
            waitForDisplay()
            checkAdHasBeenLoadedShownClickedClosed(testData)
        }
    }

    @Test
    fun test_parental_gate_close_event() {
        openParentalGate()

        parentalGateRobot {
            tapOnCancel()
            checkEventForClose()
        }
    }

    @Test
    fun test_parental_gate_failure_event() {
        openParentalGate()

        parentalGateRobot {
            solveForFailure()
        }
    }

    @Test
    fun test_external_webpage_opening_on_click() {
        val testData = TestData.videoDirect
        IntentsHelper.stubIntentsForVast()

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseNoDelay()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay()
                tapOnAd()

                // Then view URL is redirected to browser
                IntentsHelper.checkIntentsForVast()

                waitAndTapOnClose()
            }

            checkAdHasBeenLoadedShownClickedClosed(testData)
        }
    }

    @Test
    fun test_vast_click_event() {
        if (DataStore.data.useBaseModule) return
        // Given CPI Vast Ad
        val testData = TestData(placementId = "88406", fileName = "video_vast_cpi_success.json")
        val url = "https://www.superawesome.com/&referrer=null"
        IntentsHelper.stubIntentsForUrl(url)

        listScreenRobot {
            launchWithSuccessStub(testData)

            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay()

                tapOnAd()

                IntentsHelper.checkIntentsForUrl(url)
            }
        }
    }

    @Test
    fun test_iv_video_warn_dialog_press_close() {
        val testData = TestData.videoVpaidPJ

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableVideoWarnDialog()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndTapOnClose()

                videoWarningRobot {
                    checkVisible()

                    tapOnClose()
                }
            }

            checkForEvent(testData, SAEvent.adLoaded)
            checkForEvent(testData, SAEvent.adShown)
            checkForEvent(testData, SAEvent.adPaused)
            checkForEvent(testData, SAEvent.adClosed)
        }
    }

    @Test
    fun test_iv_video_warn_dialog_press_resume() {
        val testData = TestData.videoVpaidPJ

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableVideoWarnDialog()
                    tapOnDisableCloseAtEnd()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndTapOnClose()

                videoWarningRobot {
                    checkVisible()

                    tapOnResume()
                }

                waitForAdEnds()

                waitAndTapOnClose()
            }

            checkForEvent(testData, SAEvent.adEnded)
            checkForEvent(testData, SAEvent.adClosed)
        }
    }

    @Test
    fun test_direct_video_warn_dialog_press_close() {
        val testData = TestData.videoDirect

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableVideoWarnDialog()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndTapOnClose()

                videoWarningRobot {
                    checkVisible()

                    tapOnClose()
                }
            }

            checkForEvent(testData, SAEvent.adLoaded)
            checkForEvent(testData, SAEvent.adShown)
            checkForEvent(testData, SAEvent.adClosed)
        }
    }

    @Test
    fun test_direct_video_warn_dialog_press_resume() {
        val testData = TestData.videoDirect

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnDisableCloseAtEnd()
                    tapOnEnableVideoWarnDialog()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndTapOnClose()

                videoWarningRobot {
                    checkVisible()
                    tapOnResume()
                }

                waitForAdEnds()

                tapOnClose()
            }

            checkForEvent(testData, SAEvent.adEnded)
            checkForEvent(testData, SAEvent.adClosed)
        }
    }

    @Test
    fun test_failing_vpaid_ad_eventually_shows_close_button() {
        val testData = TestData.failingVideoVpaid

        stubFailingVPAIDJavaScript()

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnDisableCloseAtEnd()
                    tapOnCloseHidden()
                }
            }

            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndTapOnClose()
            }

            // expected events are dispatched
            checkForEvent(testData, SAEvent.adEnded)
            checkForEvent(testData, SAEvent.adClosed)
        }
    }

    private fun openParentalGate() {
        val testData = TestData.videoDirect

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableParentalGate()
                    tapOnCloseNoDelay()
                }
            }
            tapOnPlacement(testData)
        }

        videoScreenRobot {
            waitForDisplay()
            tapOnAd()

            parentalGateRobot {
                checkEventForOpen()
            }
        }
    }
}
