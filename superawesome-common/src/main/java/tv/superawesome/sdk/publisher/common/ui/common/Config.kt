package tv.superawesome.sdk.publisher.common.ui.common

import android.os.Parcel
import android.os.Parcelable
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.Orientation

open class Config : Parcelable {
    var moatLimiting: Boolean
    var testEnabled: Boolean
    var shouldShowPadlock: Boolean
    var isParentalGateEnabled: Boolean
    var isBumperPageEnabled: Boolean
    var shouldShowSmallClick: Boolean
    var isBackButtonEnabled: Boolean
    var shouldCloseAtEnd: Boolean
    var shouldShowCloseButton: Boolean
    var orientation: Orientation
    var startDelay: AdRequest.StartDelay

    constructor(
        moatLimiting: Boolean,
        testEnabled: Boolean,
        shouldShowPadlock: Boolean,
        isParentalGateEnabled: Boolean,
        isBumperPageEnabled: Boolean,
        shouldShowSmallClick: Boolean,
        isBackButtonEnabled: Boolean,
        shouldCloseAtEnd: Boolean,
        shouldShowCloseButton: Boolean,
        orientation: Orientation,
        startDelay: AdRequest.StartDelay,
    ) {
        this.moatLimiting = moatLimiting
        this.testEnabled = testEnabled
        this.shouldShowPadlock = shouldShowPadlock
        this.isParentalGateEnabled = isParentalGateEnabled
        this.isBumperPageEnabled = isBumperPageEnabled
        this.shouldShowSmallClick = shouldShowSmallClick
        this.isBackButtonEnabled = isBackButtonEnabled
        this.shouldCloseAtEnd = shouldCloseAtEnd
        this.shouldShowCloseButton = shouldShowCloseButton
        this.orientation = orientation
        this.startDelay = startDelay
    }

    protected constructor(parcel: Parcel) {
        moatLimiting = parcel.readByte().toInt() != 0
        testEnabled = parcel.readByte().toInt() != 0
        shouldShowPadlock = parcel.readByte().toInt() != 0
        isParentalGateEnabled = parcel.readByte().toInt() != 0
        isBumperPageEnabled = parcel.readByte().toInt() != 0
        shouldShowSmallClick = parcel.readByte().toInt() != 0
        isBackButtonEnabled = parcel.readByte().toInt() != 0
        shouldCloseAtEnd = parcel.readByte().toInt() != 0
        shouldShowCloseButton = parcel.readByte().toInt() != 0
        orientation = Orientation.fromValue(parcel.readInt()) ?: Orientation.Any
        startDelay = AdRequest.StartDelay.fromValue(parcel.readInt()) ?: Constants.defaultStartDelay
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeByte((if (moatLimiting) 1 else 0).toByte())
        parcel.writeByte((if (testEnabled) 1 else 0).toByte())
        parcel.writeByte((if (shouldShowPadlock) 1 else 0).toByte())
        parcel.writeByte((if (isParentalGateEnabled) 1 else 0).toByte())
        parcel.writeByte((if (isBumperPageEnabled) 1 else 0).toByte())
        parcel.writeByte((if (shouldShowSmallClick) 1 else 0).toByte())
        parcel.writeByte((if (isBackButtonEnabled) 1 else 0).toByte())
        parcel.writeByte((if (shouldCloseAtEnd) 1 else 0).toByte())
        parcel.writeByte((if (shouldShowCloseButton) 1 else 0).toByte())
        parcel.writeInt(orientation.ordinal)
        parcel.writeInt(startDelay.value)
    }

    companion object CREATOR : Parcelable.Creator<Config> {
        override fun createFromParcel(parcel: Parcel): Config = Config(parcel)

        override fun newArray(size: Int): Array<Config?> = arrayOfNulls(size)

        val default = Config(
            moatLimiting = Constants.defaultMoatLimitingState,
            testEnabled = Constants.defaultTestMode,
            shouldShowPadlock = Constants.defaultPadlock,
            isParentalGateEnabled = Constants.defaultParentalGate,
            isBumperPageEnabled = Constants.defaultBumperPage,
            shouldShowSmallClick = Constants.defaultSmallClick,
            isBackButtonEnabled = Constants.defaultBackButtonEnabled,
            shouldCloseAtEnd = Constants.defaultCloseAtEnd,
            shouldShowCloseButton = Constants.defaultCloseButton,
            orientation = Constants.defaultOrientation,
            startDelay = Constants.defaultStartDelay,
        )
    }
}
