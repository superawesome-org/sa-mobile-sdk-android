package tv.superawesome.sdk.publisher.ad

import android.os.Parcel
import android.os.Parcelable
import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.models.CloseButtonState
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.models.Orientation

interface AdConfig {
    /** Enabled for testing. */
    val testEnabled: Boolean

    /** Should show parental age gate before showing the ad. */
    val isParentalGateEnabled: Boolean

    /** Should show a bumper page before opening the ad. */
    val isBumperPageEnabled: Boolean

    /** Should show small button. */
    val shouldShowSmallClick: Boolean?

    /** Should show a warning before closing the ad. */
    val shouldShowCloseWarning: Boolean?

    /** Should the back button be enabled. */
    val isBackButtonEnabled: Boolean?

    /** Ad should close automatically at the end. */
    val shouldCloseAtEnd: Boolean?

    /** Ad should be muted on start. */
    val shouldMuteOnStart: Boolean?

    /** Close button option. **/
    val closeButtonState: CloseButtonState?

    /** Optional delay timer for close button. **/
    val closeButtonDelayTimer: Long
    val orientation: Orientation?
    val startDelay: AdRequest.StartDelay?
}

/**
 * Ad presentation configuration holder.
 */
class InterstitialAdConfig : Parcelable, AdConfig {

    override var testEnabled: Boolean
    override var isParentalGateEnabled: Boolean
    override var isBumperPageEnabled: Boolean
    override var closeButtonState: CloseButtonState
    override var closeButtonDelayTimer: Long
    override var orientation: Orientation
    override val shouldShowSmallClick: Boolean? = null
    override val shouldShowCloseWarning: Boolean? = null
    override val isBackButtonEnabled: Boolean? = null
    override val shouldCloseAtEnd: Boolean? = null
    override val shouldMuteOnStart: Boolean? = null
    override val startDelay: AdRequest.StartDelay? = null

    @Suppress("LongParameterList")
    constructor(
        testEnabled: Boolean = Constants.defaultTestMode,
        isParentalGateEnabled: Boolean = Constants.defaultParentalGate,
        isBumperPageEnabled: Boolean = Constants.defaultBumperPage,
        closeButtonState: CloseButtonState = Constants.defaultCloseButtonState,
        closeButtonDelayTimer: Long = Constants.defaultCloseButtonDelayTimer,
        orientation: Orientation = Constants.defaultOrientation,
    ) {
        this.testEnabled = testEnabled
        this.isParentalGateEnabled = isParentalGateEnabled
        this.isBumperPageEnabled = isBumperPageEnabled
        this.closeButtonState = closeButtonState
        this.closeButtonDelayTimer = closeButtonDelayTimer
        this.orientation = orientation
    }

    constructor(parcel: Parcel) {
        testEnabled = parcel.readByte().toInt() != 0
        isParentalGateEnabled = parcel.readByte().toInt() != 0
        isBumperPageEnabled = parcel.readByte().toInt() != 0
        val state = parcel.readInt()
        closeButtonDelayTimer = parcel.readLong()
        closeButtonState = CloseButtonState.fromInt(state, closeButtonDelayTimer)
        orientation = Orientation.fromValue(parcel.readInt()) ?: Orientation.Any
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeByte((if (testEnabled) 1 else 0).toByte())
        parcel.writeByte((if (isParentalGateEnabled) 1 else 0).toByte())
        parcel.writeByte((if (isBumperPageEnabled) 1 else 0).toByte())
        parcel.writeInt(closeButtonState.value)
        parcel.writeLong(closeButtonDelayTimer)
        parcel.writeInt(orientation.ordinal)
    }

    /**
     * Parcelable creator object.
     */
    companion object CREATOR : Parcelable.Creator<InterstitialAdConfig> {

        override fun createFromParcel(parcel: Parcel): InterstitialAdConfig = InterstitialAdConfig(parcel)

        override fun newArray(size: Int): Array<InterstitialAdConfig?> = arrayOfNulls(size)
    }
}

class VideoAdConfig : Parcelable, AdConfig {

    override var testEnabled: Boolean
    override var isParentalGateEnabled: Boolean
    override var isBumperPageEnabled: Boolean
    override var shouldShowSmallClick: Boolean
    override var shouldShowCloseWarning: Boolean
    override var isBackButtonEnabled: Boolean
    override var shouldCloseAtEnd: Boolean
    override var shouldMuteOnStart: Boolean
    override var closeButtonState: CloseButtonState
    override var closeButtonDelayTimer: Long
    override var orientation: Orientation
    override var startDelay: AdRequest.StartDelay

    @Suppress("LongParameterList")
    constructor(
        testEnabled: Boolean = Constants.defaultTestMode,
        isParentalGateEnabled: Boolean = Constants.defaultParentalGate,
        isBumperPageEnabled: Boolean = Constants.defaultBumperPage,
        shouldShowSmallClick: Boolean = Constants.defaultSmallClick,
        shouldShowCloseWarning: Boolean = Constants.defaultCloseWarning,
        isBackButtonEnabled: Boolean = Constants.defaultBackButtonEnabled,
        shouldCloseAtEnd: Boolean = Constants.defaultCloseAtEnd,
        closeButtonState: CloseButtonState = Constants.defaultCloseButtonState,
        closeButtonDelayTimer: Long = Constants.defaultCloseButtonDelayTimer,
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
        this.closeButtonDelayTimer = closeButtonDelayTimer
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
        val state = parcel.readInt()
        closeButtonDelayTimer = parcel.readLong()
        closeButtonState = CloseButtonState.fromInt(state, closeButtonDelayTimer)
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
        parcel.writeLong(closeButtonDelayTimer)
        parcel.writeInt(orientation.ordinal)
        parcel.writeInt(startDelay.value)
        parcel.writeByte((if (shouldMuteOnStart) 1 else 0).toByte())
    }

    /**
     * Parcelable creator object.
     */
    companion object CREATOR : Parcelable.Creator<VideoAdConfig> {

        override fun createFromParcel(parcel: Parcel): VideoAdConfig = VideoAdConfig(parcel)

        override fun newArray(size: Int): Array<VideoAdConfig?> = arrayOfNulls(size)
    }
}

/**
 * Ad presentation configuration holder.
 */
class BannerAdConfig : Parcelable, AdConfig {

    override var testEnabled: Boolean
    override var isParentalGateEnabled: Boolean
    override var isBumperPageEnabled: Boolean
    override val shouldShowSmallClick: Boolean? = null
    override val shouldShowCloseWarning: Boolean? = null
    override val isBackButtonEnabled: Boolean? = null
    override val shouldCloseAtEnd: Boolean? = null
    override val shouldMuteOnStart: Boolean? = null
    override val closeButtonState: CloseButtonState? = null
    override val orientation: Orientation? = null
    override val startDelay: AdRequest.StartDelay? = null
    override val closeButtonDelayTimer: Long = 0L

    @Suppress("LongParameterList")
    constructor(
        testEnabled: Boolean = Constants.defaultTestMode,
        isParentalGateEnabled: Boolean = Constants.defaultParentalGate,
        isBumperPageEnabled: Boolean = Constants.defaultBumperPage,
    ) {
        this.testEnabled = testEnabled
        this.isParentalGateEnabled = isParentalGateEnabled
        this.isBumperPageEnabled = isBumperPageEnabled
    }

    constructor(parcel: Parcel) {
        testEnabled = parcel.readByte().toInt() != 0
        isParentalGateEnabled = parcel.readByte().toInt() != 0
        isBumperPageEnabled = parcel.readByte().toInt() != 0
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeByte((if (testEnabled) 1 else 0).toByte())
        parcel.writeByte((if (isParentalGateEnabled) 1 else 0).toByte())
        parcel.writeByte((if (isBumperPageEnabled) 1 else 0).toByte())
    }

    /**
     * Parcelable creator object.
     */
    companion object CREATOR : Parcelable.Creator<BannerAdConfig> {
        override fun createFromParcel(parcel: Parcel): BannerAdConfig = BannerAdConfig(parcel)
        override fun newArray(size: Int): Array<BannerAdConfig?> = arrayOfNulls(size)
    }
}
