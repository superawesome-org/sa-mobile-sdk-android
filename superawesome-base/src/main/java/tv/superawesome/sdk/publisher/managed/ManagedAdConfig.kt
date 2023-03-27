package tv.superawesome.sdk.publisher.managed

import android.os.Parcel
import android.os.Parcelable
import tv.superawesome.sdk.publisher.state.CloseButtonState

internal class ManagedAdConfig : Parcelable {
    val isParentalGateEnabled: Boolean
    val isBumperPageEnabled: Boolean
    val isBackButtonEnabled: Boolean
    val closeButtonState: CloseButtonState

    constructor(
        isParentalGateEnabled: Boolean,
        isBumperPageEnabled: Boolean,
        isBackButtonEnabled: Boolean,
        closeButtonState: CloseButtonState,
    ) {
        this.isParentalGateEnabled = isParentalGateEnabled
        this.isBumperPageEnabled = isBumperPageEnabled
        this.isBackButtonEnabled = isBackButtonEnabled
        this.closeButtonState = closeButtonState
    }

    constructor(input: Parcel) {
        isParentalGateEnabled = input.readByte().toInt() != 0
        isBumperPageEnabled = input.readByte().toInt() != 0
        isBackButtonEnabled = input.readByte().toInt() != 0
        closeButtonState = CloseButtonState.fromInt(input.readInt())
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeByte((if (isParentalGateEnabled) 1 else 0).toByte())
        parcel.writeByte((if (isBumperPageEnabled) 1 else 0).toByte())
        parcel.writeByte((if (isBackButtonEnabled) 1 else 0).toByte())
        parcel.writeInt(closeButtonState.value)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ManagedAdConfig> = object : Parcelable.Creator<ManagedAdConfig> {
            override fun createFromParcel(input: Parcel): ManagedAdConfig = ManagedAdConfig(input)
            override fun newArray(size: Int): Array<ManagedAdConfig?> = arrayOfNulls(size)
        }
    }
}
