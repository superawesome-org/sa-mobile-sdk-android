package tv.superawesome.sdk.publisher.common.admob

import android.os.Bundle
import tv.superawesome.sdk.publisher.common.models.AdRequest.StartDelay
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.Constants.defaultOrientation
import tv.superawesome.sdk.publisher.common.models.Constants.defaultStartDelay
import tv.superawesome.sdk.publisher.common.models.Orientation

class SAAdMobExtras private constructor() {
    private var transparent = Constants.defaultBackgroundColorEnabled
    private var testMode = Constants.defaultTestMode
    private var orientation = defaultOrientation
    private var playback = defaultStartDelay
    private var parentalGate = Constants.defaultParentalGate
    private var bumperPage = Constants.defaultBumperPage
    private var backButton = Constants.defaultBackButtonEnabled
    private var closeButton = Constants.defaultCloseButton
    private var closeAtEnd = Constants.defaultCloseAtEnd
    private var smallClick = Constants.defaultSmallClick

    fun setTransparent(value: Boolean): SAAdMobExtras {
        transparent = value
        return this
    }

    fun setTestMode(value: Boolean): SAAdMobExtras {
        testMode = value
        return this
    }

    fun setOrientation(value: Orientation): SAAdMobExtras {
        orientation = value
        return this
    }

    fun setParentalGate(value: Boolean): SAAdMobExtras {
        parentalGate = value
        return this
    }

    fun setBumperPage(value: Boolean): SAAdMobExtras {
        bumperPage = value
        return this
    }

    fun setBackButton(value: Boolean): SAAdMobExtras {
        backButton = value
        return this
    }

    fun setCloseButton(value: Boolean): SAAdMobExtras {
        closeButton = value
        return this
    }

    fun setCloseAtEnd(value: Boolean): SAAdMobExtras {
        closeAtEnd = value
        return this
    }

    fun setSmallClick(value: Boolean): SAAdMobExtras {
        smallClick = value
        return this
    }

    fun setPlayabckMode(mode: StartDelay): SAAdMobExtras {
        playback = mode
        return this
    }

    fun build(): Bundle {
        val bundle = Bundle()
        bundle.putBoolean(kKEY_TEST, testMode)
        bundle.putBoolean(kKEY_TRANSPARENT, transparent)
        bundle.putInt(kKEY_ORIENTATION, orientation.ordinal)
        bundle.putInt(kKEY_PLAYBACK_MODE, playback.ordinal)
        bundle.putBoolean(kKEY_PARENTAL_GATE, parentalGate)
        bundle.putBoolean(kKEY_BUMPER_PAGE, bumperPage)
        bundle.putBoolean(kKEY_BACK_BUTTON, backButton)
        bundle.putBoolean(kKEY_CLOSE_BUTTON, closeButton)
        bundle.putBoolean(kKEY_CLOSE_AT_END, closeAtEnd)
        bundle.putBoolean(kKEY_SMALL_CLICK, smallClick)
        return bundle
    }

    companion object {
        const val kKEY_TEST = "SA_TEST_MODE"
        const val kKEY_TRANSPARENT = "SA_TRANSPARENT"
        const val kKEY_ORIENTATION = "SA_ORIENTATION"
        const val kKEY_CONFIGURATION = "SA_CONFIGURATION"
        const val kKEY_PARENTAL_GATE = "SA_PG"
        const val kKEY_BUMPER_PAGE = "SA_BUMPER"
        const val kKEY_BACK_BUTTON = "SA_BACK_BUTTON"
        const val kKEY_CLOSE_BUTTON = "SA_CLOSE_BUTTON"
        const val kKEY_CLOSE_AT_END = "SA_CLOSE_AT_END"
        const val kKEY_SMALL_CLICK = "SA_SMALL_CLICK"
        const val kKEY_PLAYBACK_MODE = "SA_PLAYBACK_MODE"

        fun extras(): SAAdMobExtras {
            return SAAdMobExtras()
        }
    }
}