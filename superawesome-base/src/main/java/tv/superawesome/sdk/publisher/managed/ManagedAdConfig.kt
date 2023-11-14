package tv.superawesome.sdk.publisher.managed

import android.os.Parcel
import android.os.Parcelable
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.sdk.publisher.state.CloseButtonState

internal class ManagedAdConfig : Parcelable {
    val isParentalGateEnabled: Boolean
    val isBumperPageEnabled: Boolean
    val shouldShowCloseWarning: Boolean
    val isBackButtonEnabled: Boolean
    val autoCloseAtEnd: Boolean
    val closeButtonState: CloseButtonState
    val environment: SAConfiguration

    @Suppress("LongParameterList")
    constructor(
        isParentalGateEnabled: Boolean,
        isBumperPageEnabled: Boolean,
        shouldShowCloseWarning: Boolean,
        isBackButtonEnabled: Boolean,
        autoCloseAtEnd: Boolean,
        closeButtonState: CloseButtonState,
        environment: SAConfiguration,
    ) {
        this.isParentalGateEnabled = isParentalGateEnabled
        this.isBumperPageEnabled = isBumperPageEnabled
        this.shouldShowCloseWarning = shouldShowCloseWarning
        this.isBackButtonEnabled = isBackButtonEnabled
        this.autoCloseAtEnd = autoCloseAtEnd
        this.closeButtonState = closeButtonState
        this.environment = environment
    }

    constructor(input: Parcel) {
        isParentalGateEnabled = input.readByte().toInt() != 0
        isBumperPageEnabled = input.readByte().toInt() != 0
        shouldShowCloseWarning = input.readByte().toInt() != 0
        isBackButtonEnabled = input.readByte().toInt() != 0
        autoCloseAtEnd = input.readByte().toInt() != 0
        closeButtonState = CloseButtonState.fromInt(input.readInt())
        environment = SAConfiguration.fromOrdinal(input.readInt())
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeByte((if (isParentalGateEnabled) 1 else 0).toByte())
        parcel.writeByte((if (isBumperPageEnabled) 1 else 0).toByte())
        parcel.writeByte((if (shouldShowCloseWarning) 1 else 0).toByte())
        parcel.writeByte((if (isBackButtonEnabled) 1 else 0).toByte())
        parcel.writeByte((if (autoCloseAtEnd) 1 else 0).toByte())
        parcel.writeInt(closeButtonState.value)
        parcel.writeInt(environment.ordinal)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ManagedAdConfig> = object : Parcelable.Creator<ManagedAdConfig> {
            override fun createFromParcel(input: Parcel): ManagedAdConfig = ManagedAdConfig(input)
            override fun newArray(size: Int): Array<ManagedAdConfig?> = arrayOfNulls(size)
        }
    }
}
