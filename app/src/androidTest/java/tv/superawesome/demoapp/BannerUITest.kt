package tv.superawesome.demoapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.model.TestData
import tv.superawesome.demoapp.robot.bannerRobot
import tv.superawesome.demoapp.robot.bumperPageRobot
import tv.superawesome.demoapp.robot.deviceRobot
import tv.superawesome.demoapp.robot.listScreenRobot
import tv.superawesome.demoapp.robot.parentalGateRobot
import tv.superawesome.demoapp.robot.settingsScreenRobot
import tv.superawesome.demoapp.util.IntentsHelper
import tv.superawesome.demoapp.util.IntentsHelper.stubIntents
import tv.superawesome.demoapp.util.TestColors
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalled
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalledWithQueryParam
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathNotCalled
import tv.superawesome.sdk.publisher.SAEvent

@RunWith(AndroidJUnit4::class)
@SmallTest
class BannerUITest: BaseUITest() {

    @Test
    fun test_adLoading_placementId() {
        val testData = TestData.bannerSuccess

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            bannerRobot {
                waitForDisplay(TestColors.bannerYellow)
            }

            checkForEvent(testData, SAEvent.adLoaded)
            checkForEvent(testData, SAEvent.adShown)
        }
    }

    @Test
    fun test_adLoading_placementId_lineItemId_creativeId() {
        val testData = TestData.bannerMultiSuccess

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            bannerRobot {
                waitForDisplay(TestColors.bannerYellow)
            }

            checkForEvent(testData, SAEvent.adLoaded)
            checkForEvent(testData, SAEvent.adShown)
        }
    }

    @Test
    fun test_adFailure_placementId() {
        val testData = TestData.bannerSuccess

        listScreenRobot {
            launchActivityWithFailureStub(testData)
            tapOnPlacement(testData)

            checkForEvent(testData, SAEvent.adFailedToLoad)
        }
    }

    @Test
    fun test_adFailure_placementId_lineItemId_creativeId() {
        val testData = TestData.bannerMultiSuccess

        listScreenRobot {
            launchActivityWithFailureStub(testData)
            tapOnPlacement(testData)

            checkForEvent(testData, SAEvent.adFailedToLoad)
        }
    }

    @Test
    fun test_adNotFound() {
        val testData = TestData(placementId = "88001", fileName = "not_found.json")

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            checkForAnyEvent(testData, SAEvent.adFailedToLoad, SAEvent.adEmpty)
        }
    }

    @Test
    fun test_safeAdVisible() {
        val testData = TestData.bannerPadlock

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            bannerRobot {
                waitAndCheckSafeAdLogo()
            }
        }
    }

    @Test
    fun test_bumper_enabled_from_settings() {
        val testData = TestData.bannerSuccess
        IntentsHelper.stubIntentsForUrl()

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableBumperPage()
                }
            }
            tapOnPlacement(testData)


            bannerRobot {
                tapOnAd()

                bumperPageRobot {
                    waitForDisplay()
                }
            }

            waitForDisplay()

            checkForEvent(testData, SAEvent.adClicked)
            IntentsHelper.checkIntentsForUrl()
            verifyUrlPathCalled("/click")
        }
    }

    @Test
    fun test_bumper_outside_click_does_not_go_through() {
        // Given bumper page is enabled from settings
        val testData = TestData.bannerSuccess
        IntentsHelper.stubIntentsForUrl()

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableBumperPage()
                }
            }
            tapOnPlacement(testData)

            bannerRobot {
                tapOnAd()

                bumperPageRobot {
                    waitForDisplay()

                    deviceRobot {
                        // And If outside the bumper page is clicked
                        clickOnScreen(0, 100)
                    }

                    // Then bumper page title is still visible
                    checkIsVisible()
                }
            }

            waitForDisplay()
        }
    }

    @Test
    fun test_bumper_enabled_from_api() {
        // Given bumper page is enabled from api
        val testData = TestData(placementId = "88001", fileName = "banner_enabled_success.json")

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            bannerRobot {
                tapOnAd()

                bumperPageRobot {
                    waitForDisplay()
                }
            }
        }
    }

    @Test
    fun test_parental_gate_for_safe_ad_click() {
        val testData = TestData.bannerPadlock

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableParentalGate()
                }
            }
            tapOnPlacement(testData)

            bannerRobot {
                waitAndCheckSafeAdLogo()
                tapOnSafeAdLogo()

                parentalGateRobot {
                    checkVisible()
                }
            }
        }
    }

    @Test
    fun test_click_through_safe_ad_click() {
        val testData = TestData.bannerPadlock

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            bannerRobot {
                waitAndCheckSafeAdLogo()
                tapOnSafeAdLogo()
                checkClickThrough()
            }
        }
    }

    @Test
    fun test_bumper_safe_ad_click() {
        val testData = TestData.bannerPadlock

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableBumperPage()
                }
            }
            tapOnPlacement(testData)

            bannerRobot {
                waitAndCheckSafeAdLogo()
                tapOnSafeAdLogo()

                bumperPageRobot {
                    checkIsVisible()
                }
            }
        }
    }

    @Test
    fun test_parental_gate_bumper_safe_ad_click() {
        val testData = TestData.bannerPadlock

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableParentalGate()
                    tapOnEnableBumperPage()
                }
            }
            tapOnPlacement(testData)

            bannerRobot {
                waitAndCheckSafeAdLogo()
                tapOnSafeAdLogo()

                parentalGateRobot {
                    checkVisible()
                    solve()
                }

                bumperPageRobot {
                    checkIsVisible()
                }
            }
        }
    }

    @Test
    fun test_safe_ad_hidden_in_response() {
        val testData = TestData.bannerPadlockHidden

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableParentalGate()
                    tapOnEnableBumperPage()
                }
            }
            tapOnPlacement(testData)

            bannerRobot {
                waitAndCheckSafeAdLogoInvisible()
            }
        }
    }

    @Test
    fun test_adClosed_callback() {
        val testData = TestData.bannerSuccess

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            bannerRobot {
                waitForDisplay(TestColors.bannerYellow)
            }

            tapOnPlacement(testData)

            // The banner is closed automatically when a new one is opened
            checkForEvent(testData, SAEvent.adClosed)
        }
    }

    // Events
    @Test
    fun test_banner_impression_events() {
        // Given
        val testData = TestData.bannerSuccess

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            bannerRobot {
                waitForDisplay()

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
        }
    }

    @Test
    fun test_banner_click_event() {
        // Given
        val testData = TestData.bannerSuccess
        IntentsHelper.stubIntentsForUrl()

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            bannerRobot {
                waitForDisplay(TestColors.bannerYellow)

                tapOnAd()
            }

            IntentsHelper.checkIntentsForUrl()
            verifyUrlPathCalled("/click")
            checkForEvent(testData, SAEvent.adClicked)
        }
    }

    @Test
    fun test_parental_gate_success_event() {
        stubIntents()
        openParentalGate()

        parentalGateRobot {
            solve()
            checkEventForSuccess()
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
        val testData = TestData.bannerSuccess
        IntentsHelper.stubIntentsForUrl()

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            bannerRobot {
                tapOnAd()

                IntentsHelper.checkIntentsForUrl()
                verifyUrlPathCalled("/click")
            }

            checkForEvent(testData, SAEvent.adClicked)
        }
    }

    @Test
    fun test_banner_with_no_clickthrough() {
        val testData = TestData.bannerSuccessNoClickthrough
        IntentsHelper.stubIntentsForUrl()

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            bannerRobot {
                tapOnAd()
                verifyUrlPathNotCalled("/click")
            }

            checkEventNotSent(testData, SAEvent.adClicked)
        }
    }

    @Test
    fun test_load_banner_with_additional_options() {
        val testData = TestData.bannerSuccess
        IntentsHelper.stubIntentsForUrl()

        listScreenRobot {
            launchWithSuccessStub(testData, additionalOptions = mapOf("option1" to 123))
            tapOnPlacement(testData)
        }

        bannerRobot {
            verifyUrlPathCalledWithQueryParam(
                "/ad/${testData.placementId}",
                "option1",
                "123"
            )
        }
    }

    private fun openParentalGate() {
        val testData = TestData.bannerPadlock

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableParentalGate()
                }
            }
            tapOnPlacement(testData)

            bannerRobot {
                waitAndCheckSafeAdLogo()
                tapOnSafeAdLogo()

                parentalGateRobot {
                    checkEventForOpen()
                }
            }
        }
    }
}
