package tv.superawesome.demoapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.model.TestData
import tv.superawesome.demoapp.robot.interstitialScreenRobot
import tv.superawesome.demoapp.robot.listScreenRobot
import tv.superawesome.demoapp.robot.parentalGateRobot
import tv.superawesome.demoapp.robot.settingsScreenRobot
import tv.superawesome.demoapp.robot.videoScreenRobot
import tv.superawesome.demoapp.robot.videoWarningRobot
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
            videoScreenRobot {
                waitForAdEnds()
                tapOnClose()
            }

            checkForEvent(testData, SAEvent.adEnded)
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
    fun test_vpaid_parental_gate_for_safe_ad_click() {
        val testData = TestData.videoVpaidYellowBoxPadlock

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
    fun test_vpaid_adAlreadyLoaded_callback() {
        val testData = TestData.videoVpaid
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
        val testData = TestData.videoVpaidGreyBox

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
}