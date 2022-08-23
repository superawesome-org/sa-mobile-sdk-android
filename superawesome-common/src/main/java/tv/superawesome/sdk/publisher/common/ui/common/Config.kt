package tv.superawesome.sdk.publisher.common.ui.common

import android.os.Parcel
import android.os.Parcelable
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.Orientation
import tv.superawesome.sdk.publisher.common.state.CloseButtonState

open class Config : Parcelable {
    var moatLimiting: Boolean
    var testEnabled: Boolean
    var isParentalGateEnabled: Boolean
    var isBumperPageEnabled: Boolean
    var shouldShowSmallClick: Boolean
    var isBackButtonEnabled: Boolean
    var shouldCloseAtEnd: Boolean
    var closeButtonState: CloseButtonState
    var orientation: Orientation
    var startDelay: AdRequest.StartDelay

    constructor(
        moatLimiting: Boolean,
        testEnabled: Boolean,
        isParentalGateEnabled: Boolean,
        isBumperPageEnabled: Boolean,
        shouldShowSmallClick: Boolean,
        isBackButtonEnabled: Boolean,
        shouldCloseAtEnd: Boolean,
        closeButtonState: CloseButtonState,
        orientation: Orientation,
        startDelay: AdRequest.StartDelay,
    ) {
        this.moatLimiting = moatLimiting
        this.testEnabled = testEnabled
        this.isParentalGateEnabled = isParentalGateEnabled
        this.isBumperPageEnabled = isBumperPageEnabled
        this.shouldShowSmallClick = shouldShowSmallClick
        this.isBackButtonEnabled = isBackButtonEnabled
        this.shouldCloseAtEnd = shouldCloseAtEnd
        this.closeButtonState = closeButtonState
        this.orientation = orientation
        this.startDelay = startDelay
    }

    protected constructor(parcel: Parcel) {
        moatLimiting = parcel.readByte().toInt() != 0
        testEnabled = parcel.readByte().toInt() != 0
        isParentalGateEnabled = parcel.readByte().toInt() != 0
        isBumperPageEnabled = parcel.readByte().toInt() != 0
        shouldShowSmallClick = parcel.readByte().toInt() != 0
        isBackButtonEnabled = parcel.readByte().toInt() != 0
        shouldCloseAtEnd = parcel.readByte().toInt() != 0
        closeButtonState = CloseButtonState.fromInt(parcel.readInt())
        orientation = Orientation.fromValue(parcel.readInt()) ?: Orientation.Any
        startDelay = AdRequest.StartDelay.fromValue(parcel.readInt()) ?: Constants.defaultStartDelay
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeByte((if (moatLimiting) 1 else 0).toByte())
        parcel.writeByte((if (testEnabled) 1 else 0).toByte())
        parcel.writeByte((if (isParentalGateEnabled) 1 else 0).toByte())
        parcel.writeByte((if (isBumperPageEnabled) 1 else 0).toByte())
        parcel.writeByte((if (shouldShowSmallClick) 1 else 0).toByte())
        parcel.writeByte((if (isBackButtonEnabled) 1 else 0).toByte())
        parcel.writeByte((if (shouldCloseAtEnd) 1 else 0).toByte())
        parcel.writeInt(closeButtonState.value)
        parcel.writeInt(orientation.ordinal)
        parcel.writeInt(startDelay.value)
    }

    companion object CREATOR : Parcelable.Creator<Config> {
        override fun createFromParcel(parcel: Parcel): Config = Config(parcel)

        override fun newArray(size: Int): Array<Config?> = arrayOfNulls(size)

        val default = Config(
            moatLimiting = Constants.defaultMoatLimitingState,
            testEnabled = Constants.defaultTestMode,
            isParentalGateEnabled = Constants.defaultParentalGate,
            isBumperPageEnabled = Constants.defaultBumperPage,
            shouldShowSmallClick = Constants.defaultSmallClick,
            isBackButtonEnabled = Constants.defaultBackButtonEnabled,
            shouldCloseAtEnd = Constants.defaultCloseAtEnd,
            closeButtonState = Constants.defaultCloseButtonState,
            orientation = Constants.defaultOrientation,
            startDelay = Constants.defaultStartDelay,
        )
    }
}
