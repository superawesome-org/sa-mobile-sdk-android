package tv.superawesome.sdk.publisher.ui.common

import android.os.Parcel
import android.os.Parcelable
import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.models.CloseButtonState
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.models.Orientation

/**
 * Ad presentation configuration holder.
 */
class Config : Parcelable {

    /** Enabled for testing. */
    var testEnabled: Boolean

    /** Should show parental age gate before showing the ad. */
    var isParentalGateEnabled: Boolean

    /** Should show a bumper page before opening the ad. */
    var isBumperPageEnabled: Boolean

    /** Should show small button. */
    var shouldShowSmallClick: Boolean

    /** Should show a warning before closing the ad. */
    var shouldShowCloseWarning: Boolean

    /** Should the back button be enabled. */
    var isBackButtonEnabled: Boolean

    /** Ad should close automatically at the end. */
    var shouldCloseAtEnd: Boolean

    /** Ad should be muted on start. */
    var shouldMuteOnStart: Boolean

    var closeButtonState: CloseButtonState
    var orientation: Orientation
    var startDelay: AdRequest.StartDelay

    @Suppress("LongParameterList")
    internal constructor(
        testEnabled: Boolean = Constants.defaultTestMode,
        isParentalGateEnabled: Boolean = Constants.defaultParentalGate,
        isBumperPageEnabled: Boolean = Constants.defaultBumperPage,
        shouldShowSmallClick: Boolean = Constants.defaultSmallClick,
        shouldShowCloseWarning: Boolean = Constants.defaultCloseWarning,
        isBackButtonEnabled: Boolean = Constants.defaultBackButtonEnabled,
        shouldCloseAtEnd: Boolean = Constants.defaultCloseAtEnd,
        closeButtonState: CloseButtonState = Constants.defaultCloseButtonState,
        orientation: Orientation = Constants.defaultOrientation,
        startDelay: AdRequest.StartDelay = Constants.defaultStartDelay,
        shouldMuteOnStart: Boolean = Constants.defaultMuteOnStart,
    ) {
        this.testEnabled = testEnabled
        this.isParentalGateEnabled = isParentalGateEnabled
        this.isBumperPageEnabled = isBumperPageEnabled
        this.shouldShowSmallClick = shouldShowSmallClick
        this.shouldShowCloseWarning = shouldShowCloseWarning
        this.isBackButtonEnabled = isBackButtonEnabled
        this.shouldCloseAtEnd = shouldCloseAtEnd
        this.closeButtonState = closeButtonState
        this.orientation = orientation
        this.startDelay = startDelay
        this.shouldMuteOnStart = shouldMuteOnStart
    }

    constructor(parcel: Parcel) {
        testEnabled = parcel.readByte().toInt() != 0
        isParentalGateEnabled = parcel.readByte().toInt() != 0
        isBumperPageEnabled = parcel.readByte().toInt() != 0
        shouldShowSmallClick = parcel.readByte().toInt() != 0
        shouldShowCloseWarning = parcel.readByte().toInt() != 0
        isBackButtonEnabled = parcel.readByte().toInt() != 0
        shouldCloseAtEnd = parcel.readByte().toInt() != 0
        closeButtonState = CloseButtonState.fromInt(parcel.readInt())
        orientation = Orientation.fromValue(parcel.readInt()) ?: Orientation.Any
        startDelay = AdRequest.StartDelay.fromValue(parcel.readInt()) ?: Constants.defaultStartDelay
        shouldMuteOnStart = parcel.readByte().toInt() != 0
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeByte((if (testEnabled) 1 else 0).toByte())
        parcel.writeByte((if (isParentalGateEnabled) 1 else 0).toByte())
        parcel.writeByte((if (isBumperPageEnabled) 1 else 0).toByte())
        parcel.writeByte((if (shouldShowSmallClick) 1 else 0).toByte())
        parcel.writeByte((if (shouldShowCloseWarning) 1 else 0).toByte())
        parcel.writeByte((if (isBackButtonEnabled) 1 else 0).toByte())
        parcel.writeByte((if (shouldCloseAtEnd) 1 else 0).toByte())
        parcel.writeInt(closeButtonState.value)
        parcel.writeInt(orientation.ordinal)
        parcel.writeInt(startDelay.value)
        parcel.writeByte((if (shouldMuteOnStart) 1 else 0).toByte())
    }

    /**
     * Parcelable creator object.
     */
    companion object CREATOR : Parcelable.Creator<Config> {

        override fun createFromParcel(parcel: Parcel): Config = Config(parcel)

        override fun newArray(size: Int): Array<Config?> = arrayOfNulls(size)
    }
}
