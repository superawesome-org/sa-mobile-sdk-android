package tv.superawesome.demoapp

import android.graphics.Color
import android.os.Handler
import android.view.KeyEvent
import androidx.core.os.postDelayed
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.After
import org.junit.Before
import org.junit.Ignore
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
import tv.superawesome.demoapp.settings.DataStore
import tv.superawesome.demoapp.util.EspressoUtils
import tv.superawesome.demoapp.util.IntentsHelper
import tv.superawesome.demoapp.util.TestColors
import tv.superawesome.demoapp.util.WireMockHelper
import tv.superawesome.demoapp.util.WireMockHelper.verifyQueryParamContains
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalled
import tv.superawesome.demoapp.util.WireMockHelper.verifyUrlPathCalledWithQueryParam
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAVideoActivity
import tv.superawesome.sdk.publisher.SAVideoAd
import java.lang.reflect.InvocationTargetException


@RunWith(AndroidJUnit4::class)
class VideoAdUITest: BaseUITest() {

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
                waitForDisplay()
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
    fun test_standard_testCloseButtonCustom5seconds() {
        val testData = TestData.videoVast

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseCustom()
                    tapOnCustom5s()
                }
            }

            tapOnPlacement(testData)

            videoScreenRobot {
                checkCloseIsNotDisplayed()
                waitForCloseAppear(5_000)
                tapOnClose()
            }
        }
    }

    @Test
    fun test_standard_testCloseButtonCustom10seconds() {
        val testData = TestData.videoVast

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
                waitForCloseAppear(2_000)
                tapOnClose()
            }
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

    @Test
    fun test_auto_close_on_finish_close_visible() {
        val testData = TestData.videoDirect

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

            listScreenRobot {
                waitForDisplay()
                checkForEvent(testData, SAEvent.adEnded)
            }
        }
    }

    @Test
    fun test_auto_close_on_finish_close_delayed() {
        val testData = TestData.videoDirect

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

            listScreenRobot {
                waitForDisplay()
                checkForEvent(testData, SAEvent.adEnded)
            }
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
    fun test_vast_doesNotLoad_noVastTag() {
        val testData = TestData.videoVastNoVastTag
        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)
            checkForEvent(testData, SAEvent.adEmpty)
        }
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
    fun test_direct_video_network_failure() {
        val testData = TestData.videoDirect

        listScreenRobot {
            launchActivityWithNetworkFailure(testData)
            tapOnPlacement(testData)

            checkForEvent(testData, SAEvent.adFailedToLoad)
        }
    }

    @Test
    fun test_direct_video_poor_conection() {
        val testData = TestData.videoDirect

        listScreenRobot {
            launchActivityWithPoorConnection(testData)
            tapOnPlacement(testData)

            waitForRequestTimeout()

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
                waitForDwellTime()

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
                waitForDwellTime()

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
        val url = "http://localhost:8080/mock_website"
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
    fun test_load_video_with_additional_options() {
        val testData = TestData.videoVast

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

    @Test
    fun test_vast_video_no_clickthrough() {
        val testData = TestData.videoDirectNoClickthrough

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay()
                tapOnAd()
                // The video is still visible
                waitForDisplay(TestColors.vastYellow)
                WireMockHelper.verifyUrlPathNotCalled("/click")
            }
        }
    }

    @Test
    fun test_direct_video_safe_ad_click() {
        val testData = TestData.videoPadlock

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndCheckSafeAdLogo()
                tapOnSafeAdLogo()
                checkClickThrough(Endpoints.safeAdClickthrough)
            }
        }
    }

    @Test
    fun test_direct_video_parental_gate_ad_click() {
        val testData = TestData.videoPadlock

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableParentalGate()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndCheckSafeAdLogo()
                tapOnSafeAdLogo()

                parentalGateRobot {
                    checkVisible()
                    solve()
                }
                checkClickThrough(Endpoints.safeAdClickthrough)
            }
        }
    }

    @Test
    fun test_direct_video_bumper_safe_ad_click() {
        val testData = TestData.videoPadlock

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableBumperPage()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndCheckSafeAdLogo()
                tapOnSafeAdLogo()

                bumperPageRobot {
                    checkIsVisible()
                    waitForFinish()
                }
                checkClickThrough(Endpoints.safeAdClickthrough)
            }
        }
    }

    @Test
    fun test_direct_video_parental_gate_bumper_safe_ad_click() {
        val testData = TestData.videoPadlock

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnEnableParentalGate()
                    tapOnEnableBumperPage()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndCheckSafeAdLogo()
                tapOnSafeAdLogo()

                parentalGateRobot {
                    checkVisible()
                    solve()
                }

                bumperPageRobot {
                    checkIsVisible()
                    waitForFinish()
                }

                checkClickThrough(Endpoints.safeAdClickthrough)
            }
        }
    }

    @Test
    fun test_safe_ad_hidden_in_response() {
        val testData = TestData.videoDirect

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                waitAndCheckSafeAdLogoInvisible()
            }
        }
    }

    @Test
    fun test_direct_video_mute_on_start() {
        val testData = TestData.videoDirectNoClickthrough

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnMuteOnStart()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                checkVideoIsMuted()
                tapOnVolume()
                checkVideoIsUnmuted()
            }
        }
    }

    @Test
    fun test_direct_ad_background_pause_and_resume() {
        val testData = TestData.videoDirect
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseNoDelay()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay(TestColors.vastYellow)
                device.pressHome()
                device.waitForIdle()
                device.pressKeyCode(KeyEvent.KEYCODE_APP_SWITCH)
                device.pressKeyCode(KeyEvent.KEYCODE_APP_SWITCH)

                waitAndTapOnClose()

                checkForEvent(testData, SAEvent.adPaused)
                checkForEvent(testData, SAEvent.adPlaying)
            }
        }
    }

    @Test
    fun test_video_frozen_failsafe_activates_when_video_freezes() {
        val testData = TestData.videoDirect

        listScreenRobot {
            launchWithSuccessStub(testData) {
                settingsScreenRobot {
                    tapOnCloseHidden()
                    tapOnDisableCloseAtEnd()
                }
            }
            tapOnPlacement(testData)

            videoScreenRobot {
                checkCloseIsNotDisplayed()

                val activity = EspressoUtils.getCurrentActivity<SAVideoActivity>()
                activity?.forceVideoPause()

                waitForFreezeFailsafeTime()
                checkCloseIsDisplayed()
            }
        }
    }

    @Test
    fun test_video_gives_reward_if_errors_after_set_time() {
        val testData = TestData.videoDirect

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay()

                val activity = EspressoUtils.getCurrentActivity<SAVideoActivity>()
                if (activity != null) {
                    val handler = Handler(activity.mainLooper)
                    handler.postDelayed(2500) {
                        activity.forceVideoCrash()
                    }
                }
            }

            waitForDisplay()

            checkForEvent(testData, SAEvent.adLoaded)
            checkForEvent(testData, SAEvent.adShown)
            checkForEvent(testData, SAEvent.adPlaying)
            checkForEvent(testData, SAEvent.adFailedToShow)
            checkForEvent(testData, SAEvent.adEnded)
            checkForEvent(testData, SAEvent.adClosed)
        }
    }

    @Test
    @Ignore("Won't work properly unless the isAdResponseVASTEnabled flag is enabled manually.")
    fun test_video_inline_vast() {
        val testData = TestData.vastInline

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay(TestColors.vastYellow)
            }
        }
    }

    @Test
    @Ignore("Won't work properly unless the isAdResponseVASTEnabled flag is enabled manually.")
    fun test_video_inline_wrapper_vast() {
        val testData = TestData.vastInlineWrapper

        listScreenRobot {
            launchWithSuccessStub(testData)
            tapOnPlacement(testData)

            videoScreenRobot {
                waitForDisplay(TestColors.vastYellow)
            }
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
}
