package tv.superawesome.plugins.publisher.admob

import android.os.Bundle
import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.models.AdRequest.StartDelay
import tv.superawesome.sdk.publisher.models.Orientation

class SAAdMobExtras private constructor() {
    var transparent = false
        private set
    var testMode = false
        private set
    var orientation = Orientation.Any
        private set
    var playback = AdRequest.StartDelay.PreRoll
        private set
    var parentalGate = false
        private set
    var bumperPage = false
        private set
    var backButton = true
        private set
    var closeButton = false
        private set
    var closeAtEnd = true
        private set
    var smallClick = false
        private set

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

    fun setPlaybackMode(mode: StartDelay): SAAdMobExtras {
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
        const val kKEY_PARENTAL_GATE = "SA_PG"
        const val kKEY_BUMPER_PAGE = "SA_BUMPER"
        const val kKEY_BACK_BUTTON = "SA_BACK_BUTTON"
        const val kKEY_CLOSE_BUTTON = "SA_CLOSE_BUTTON"
        const val kKEY_CLOSE_AT_END = "SA_CLOSE_AT_END"
        const val kKEY_SMALL_CLICK = "SA_SMALL_CLICK"
        const val kKEY_PLAYBACK_MODE = "SA_PLAYBACK_MODE"
        const val PARAMETER = "parameter"

        fun extras(): SAAdMobExtras = SAAdMobExtras()

        fun readBundle(bundle: Bundle?): SAAdMobExtras? {
            if (bundle == null || bundle.size() == 0) {
                return null
            }

            val extras = extras()

            val orientation =
                Orientation.fromValue(bundle.getInt(kKEY_ORIENTATION))
                    ?: extras.orientation
            val playback =
                StartDelay.fromValue(bundle.getInt(kKEY_PLAYBACK_MODE))
                    ?: extras.playback

            extras.setTestMode(bundle.getBoolean(kKEY_TEST))
            extras.setTransparent(bundle.getBoolean(kKEY_TRANSPARENT))
            extras.setOrientation(orientation)
            extras.setPlaybackMode(playback)
            extras.setParentalGate(bundle.getBoolean(kKEY_PARENTAL_GATE))
            extras.setBumperPage(bundle.getBoolean(kKEY_BUMPER_PAGE))
            extras.setBackButton(bundle.getBoolean(kKEY_BACK_BUTTON))
            extras.setCloseButton(bundle.getBoolean(kKEY_CLOSE_BUTTON))
            extras.setCloseAtEnd(bundle.getBoolean(kKEY_CLOSE_AT_END))
            extras.setSmallClick(bundle.getBoolean(kKEY_SMALL_CLICK))

            return extras
        }
    }
}
