package tv.superawesome.demoapp

import android.graphics.Color
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.model.Endpoints
import tv.superawesome.demoapp.model.TestData
import tv.superawesome.demoapp.robot.bumperPageRobot
import tv.superawesome.demoapp.robot.interstitialScreenRobot
import tv.superawesome.demoapp.robot.listScreenRobot
import tv.superawesome.demoapp.robot.parentalGateRobot
import tv.superawesome.demoapp.robot.settingsScreenRobot
import tv.superawesome.demoapp.robot.videoScreenRobot
import tv.superawesome.demoapp.robot.videoWarningRobot
import tv.superawesome.demoapp.util.IntentsHelper
import tv.superawesome.demoapp.util.TestColors
import tv.superawesome.demoapp.util.WireMockHelper
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAVideoAd
import java.lang.reflect.InvocationTargetException

@RunWith(AndroidJUnit4::class)
@SmallTest
class VpaidVideoAdUITest: BaseUITest() {

    @Before
    override fun setup() {
        super.setup()
        val ads = SAVideoAd::class.java.getDeclaredMethod("clearCache")
        ads.isAccessible = true
        try {
            ads.invoke(null)
        } catch (e: InvocationTargetException) {
            /* no-op */
        }
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun test_vpaid_CloseButton() {
        val testData = TestData.videoVpaidYellowBox

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
        val testData = TestData.videoVpaidYellowBox

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseHidden()
                    tapOnDisableCloseAtEnd()
                }
            }

            tapOnPlacement(testData)
            videoScreenRobot {
                waitForAdEnds()
                tapOnClose()
            }

            checkForEvent(testData, SAEvent.adEnded)
        }
    }

    @Test
    fun test_vpaid_auto_close_on_finish_close_button_visible() {
        val testData = TestData.videoVpaidYellowBox

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseNoDelay()
                }
            }

            tapOnPlacement(testData)
            videoScreenRobot {
                checkCloseIsDisplayed()
                waitForAdEnds()
            }

            waitForDisplay()
            checkForEvent(testData, SAEvent.adEnded)
        }
    }

    @Test
    fun test_vpaid_auto_close_on_finish_close_button_delayed() {
        val testData = TestData.videoVpaidYellowBox

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseDelayed()
                }
            }

            tapOnPlacement(testData)
            videoScreenRobot {
                checkCloseIsNotDisplayed()
                waitForCloseAppear()
                checkCloseIsDisplayed()
                waitForAdEnds()
            }

            waitForDisplay()
            checkForEvent(testData, SAEvent.adEnded)
        }
    }

    @Test
    fun test_standard_testCloseButtonCustom5seconds() {
        val testData = TestData.videoVpaidYellowBox

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseCustom()
                    tapOnCustom5s()
                    tapOnDisableCloseAtEnd()
                }
            }

            tapOnPlacement(testData)

            videoScreenRobot {
                checkCloseIsNotDisplayed()
                waitForCloseAppear(6_000)
                tapOnClose()
            }
        }
    }

    @Test
    fun test_standard_testCloseButtonCustom10seconds() {
        val testData = TestData.videoVpaidYellowBox

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseCustom()
                    tapOnCustom10s()
                }
            }

            tapOnPlacement(testData)

            videoScreenRobot {
                checkCloseIsNotDisplayed()
                waitForCloseAppear(8_000)
                checkCloseIsNotDisplayed()
                waitForCloseAppear(3_000)
                tapOnClose()
            }
        }
    }

    @Test
    fun test_vpaid_adLoading_placementId() {
        val testData = TestData.videoVpaidYellowBox
        testAdLoading(testData, TestColors.vpaidYellow)
    }

    @Test
    fun test_vpaid_adLoading_placementId_lineItemId_creativeId() {
        val testData = TestData.videoVpaidMulti
        testAdLoading(testData, TestColors.vpaidYellow)
    }

    @Test
    fun test_vpaid_parental_gate_for_ad_click() {
        val testData = TestData.videoVpaidYellowBox

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableParentalGate()
                }
            }

            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay(TestColors.vpaidYellow)
                waitForDisplay(TestColors.vpaidClickBlue)
                tapOnAd()

                parentalGateRobot {
                    checkVisible()
                }
            }
        }
    }

    @Test
    fun test_parental_gate_success_event() {
        val testData = TestData.videoVpaidYellowBox

        IntentsHelper.stubIntentsForVpaid()

        openParentalGate()
        parentalGateRobot {
            solve()
            checkEventForSuccess()
        }
        IntentsHelper.checkIntentsForVpaid()

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
    fun test_vpaid_video_parental_gate_ad_click() {
        val testData = TestData.videoVpaidYellowBox

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableParentalGate()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay(TestColors.vpaidYellow)
                waitForDisplay(TestColors.vpaidClickBlue)
                tapOnAd()

                parentalGateRobot {
                    checkVisible()
                    solve()
                }
            }
            checkClickThrough(Endpoints.stubUrlVpaidClickThrough)
        }
    }

    @Test
    fun test_vpaid_bumper_enabled_from_settings() {
        IntentsHelper.stubIntentsForVpaid()
        val testData = TestData.videoVpaidYellowBox

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableBumperPage()
                    tapOnCloseNoDelay()
                }
            }

            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay(TestColors.vpaidYellow)
                waitForDisplay(TestColors.vpaidClickBlue)

                tapOnAd()

                bumperPageRobot {
                    waitForDisplay()
                    waitForFinish()

                    IntentsHelper.checkIntentsForVpaid()
                    WireMockHelper.verifyUrlPathCalled("/video/click")
                }

                tapOnClose()
            }

            checkAdHasBeenLoadedShownClickedClosed(testData)
        }
    }

    @Test
    fun test_vpaid_bumper_enabled_from_api() {
        IntentsHelper.stubIntentsForVpaid()
        val testData = TestData.videoVpaidYellowBoxBumper

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseNoDelay()
                }
            }

            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay(TestColors.vpaidYellow)
                waitForDisplay(TestColors.vpaidClickBlue)

                tapOnAd()

                bumperPageRobot {
                    waitForDisplay()
                    waitForFinish()

                    IntentsHelper.checkIntentsForVpaid()
                    WireMockHelper.verifyUrlPathCalled("/video/click")
                }

                tapOnClose()
            }

            checkAdHasBeenLoadedShownClickedClosed(testData)
        }
    }

    @Test
    fun test_vpaid_adAlreadyLoaded_callback() {
        val testData = TestData.videoVpaidYellowBox
        testAdAlreadyLoaded(testData)
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
                waitForDwellTime()
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

                waitForPJAdEnd()

                waitAndTapOnClose()
            }

            checkForEvent(testData, SAEvent.adEnded)
            checkForEvent(testData, SAEvent.adClosed)
        }
    }

    @Test
    fun test_failing_vpaid_ad_eventually_shows_close_button() {
        val testData = TestData.failingVideoVpaid

        WireMockHelper.stubFailingVPAIDJavaScript()

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnDisableCloseAtEnd()
                    tapOnCloseHidden()
                }
            }

            tapOnPlacement(testData)

            videoScreenRobot {
                waitFailsafeTime()
                waitAndTapOnClose()
            }

            // expected events are dispatched
            checkForEvent(testData, SAEvent.adEnded)
            checkForEvent(testData, SAEvent.adClosed)
        }
    }

    @Test
    fun test_load_iv_with_additional_options() {
        val testData = TestData.videoVpaidPJ

        listScreenRobot {
            launchWithSuccessStub(testData, additionalOptions = mapOf("option1" to 123))
            tapOnPlacement(testData)
        }

        interstitialScreenRobot {
            WireMockHelper.verifyUrlPathCalledWithQueryParam(
                "/ad/${testData.placementId}",
                "option1",
                "123"
            )
        }
    }

    @Test
    fun test_vpaid_with_clickthrough_to_webpage_and_back() {
        val testData = TestData.videoVpaidYellowBox

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                // Wait for the ad to render
                waitForDisplay(TestColors.vpaidYellow)
                // Wait for the clickable blue box to render
                waitForDisplay(TestColors.vpaidClickBlue)
                tapOnAd()
                // Exiting the browser takes us back to the app with the ad still showing
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack()
                // Wait for the ad view to be visible
                waitForDisplay()
                waitAndTapOnClose()
            }

            WireMockHelper.verifyUrlPathCalled("/clickthrough")
            checkForEvent(testData, SAEvent.adClicked)
        }
    }

    @Test
    fun test_vpaid_with_clickthrough_broken_does_nothing() {
        val testData = TestData.videoVpaidYellowBoxBrokenClickThrough

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                // Wait for the ad to render
                waitForDisplay(TestColors.vpaidYellow)
                // Wait for the clickable blue box to render
                waitForDisplay(TestColors.vpaidClickBlue)
                tapOnAd()

                IntentsHelper.checkIntentsForUrl("h11p://localhost.com/clickthrough")

                waitAndTapOnClose()
            }

            WireMockHelper.verifyUrlPathNotCalled("/clickthrough")
            checkForEvent(testData, SAEvent.adClicked)
        }
    }

    @Test
    fun test_vpaid_video_parental_gate_bumper_ad_click() {
        IntentsHelper.stubIntentsForVpaid()
        val testData = TestData.videoVpaidYellowBox

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableParentalGate()
                    tapOnEnableBumperPage()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay(TestColors.vpaidYellow)
                waitForDisplay(TestColors.vpaidClickBlue)

                tapOnAd()

                parentalGateRobot {
                    checkVisible()
                    solve()
                }

                bumperPageRobot {
                    checkIsVisible()
                    waitForFinish()
                }

                WireMockHelper.verifyUrlPathCalled("/video/click")
                IntentsHelper.checkIntentsForVpaid()
            }
        }
    }

    @Test
    fun test_vpaid_all_websdk_events() {
        val testData = TestData.videoVpaidYellowBox

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                // Wait for the ad to render
                waitForDisplay(TestColors.vpaidYellow)
                // Wait for the clickable white box to render
                waitForDisplay(TestColors.vpaidClickBlue)
                tapOnAd()
                // Exiting the browser takes us back to the app with the ad still showing
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack()
                waitForAdEnds()
            }

            waitForDisplay()
            checkForEvent(testData, SAEvent.adLoaded)
            checkForEvent(testData, SAEvent.webSDKReady)
            checkForEvent(testData, SAEvent.adShown)
            checkForEvent(testData, SAEvent.adClicked)
            checkForEvent(testData, SAEvent.adPaused)
            checkForEvent(testData, SAEvent.adPlaying)
            checkForEvent(testData, SAEvent.adEnded)
            checkForEvent(testData, SAEvent.adClosed)
        }
    }

    @Test
    fun test_vpaid_empty_html_tag() {
        val testData = TestData.videoVpaidYellowBoxNoTag

        listScreenRobot {
            launchWithSuccessStub(testData)

            tapOnPlacement(testData)

            // expected event is dispatched
            checkForEvent(testData, SAEvent.adFailedToShow)
        }
    }

    @Test
    fun test_vpaid_video_poor_conection() {
        val testData = TestData.videoVpaidPJ

        listScreenRobot {
            launchActivityWithPoorConnection(testData)
            tapOnPlacement(testData)

            waitForRequestTimeout()

            checkForEvent(testData, SAEvent.adFailedToLoad)
        }
    }

    @Test
    fun test_vpaid_video_network_failure() {
        val testData = TestData.videoVpaidPJ

        listScreenRobot {
            launchActivityWithNetworkFailure(testData)
            tapOnPlacement(testData)

            checkForEvent(testData, SAEvent.adFailedToLoad)
        }
    }

    @Test
    fun test_vpaid_no_is_vpaid_flag() {
        val testData = TestData.videoVpaidYellowBoxNoIsVpaidFlag

        listScreenRobot {
            launchWithSuccessStub(testData)

            tapOnPlacement(testData)

            // expected event is dispatched
            checkForEvent(testData, SAEvent.adEmpty)
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
            checkForEvent(testData, SAEvent.adShown)
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

    private fun openParentalGate() {
        val testData = TestData.videoVpaidYellowBox

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableParentalGate()
                    tapOnCloseNoDelay()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay(TestColors.vpaidYellow)
                waitForDisplay(TestColors.vpaidClickBlue)
                tapOnAd()

                parentalGateRobot {
                    checkEventForOpen()
                }
            }
        }
    }
}
