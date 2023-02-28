package tv.superawesome.sdk.publisher.managed

import android.os.Parcel
import android.os.Parcelable
import tv.superawesome.sdk.publisher.state.CloseButtonState

internal class ManangedAdConfig : Parcelable {
    val isBackButtonEnabled: Boolean
    val closeButtonState: CloseButtonState

    constructor(
        isBackButtonEnabled: Boolean,
        closeButtonState: CloseButtonState,
    ) {
        this.isBackButtonEnabled = isBackButtonEnabled
        this.closeButtonState = closeButtonState
    }

    constructor(input: Parcel) {
        isBackButtonEnabled = input.readByte().toInt() != 0
        closeButtonState = CloseButtonState.fromInt(input.readInt())
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeByte((if (isBackButtonEnabled) 1 else 0).toByte())
        parcel.writeInt(closeButtonState.value)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ManangedAdConfig> = object : Parcelable.Creator<ManangedAdConfig> {
            override fun createFromParcel(input: Parcel): ManangedAdConfig = ManangedAdConfig(input)
            override fun newArray(size: Int): Array<ManangedAdConfig?> = arrayOfNulls(size)
        }
    }
}
