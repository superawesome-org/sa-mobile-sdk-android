package tv.superawesome.sdk.publisher;

import android.os.Parcel;
import android.os.Parcelable;

import tv.superawesome.sdk.publisher.state.CloseButtonState;

class VideoConfig implements Parcelable {

    final boolean shouldShowPadlock;
    final boolean isParentalGateEnabled;
    final boolean isBumperPageEnabled;
    final boolean shouldShowSmallClick;
    final boolean isBackButtonEnabled;
    final boolean shouldCloseAtEnd;
    final boolean shouldMuteOnStart;
    final CloseButtonState closeButtonState;
    final long closeButtonDelayTimer;
    final boolean shouldShowCloseWarning;
    final SAOrientation orientation;

    VideoConfig(boolean shouldShowPadlock,
                boolean isParentalGateEnabled,
                boolean isBumperPageEnabled,
                boolean shouldShowSmallClick,
                boolean isBackButtonEnabled,
                boolean shouldCloseAtEnd,
                boolean shouldMuteOnStart,
                CloseButtonState closeButtonState,
                long closeButtonDelayTimer,
                boolean shouldShowCloseWarning,
                SAOrientation orientation) {
        this.shouldShowPadlock = shouldShowPadlock;
        this.isParentalGateEnabled = isParentalGateEnabled;
        this.isBumperPageEnabled = isBumperPageEnabled;
        this.shouldShowSmallClick = shouldShowSmallClick;
        this.isBackButtonEnabled = isBackButtonEnabled;
        this.shouldCloseAtEnd = shouldCloseAtEnd;
        this.shouldMuteOnStart = shouldMuteOnStart;
        this.closeButtonState = closeButtonState;
        this.closeButtonDelayTimer = closeButtonDelayTimer;
        this.shouldShowCloseWarning = shouldShowCloseWarning;
        this.orientation = orientation;
    }

    protected VideoConfig(Parcel in) {
        shouldShowPadlock = in.readByte() != 0;
        isParentalGateEnabled = in.readByte() != 0;
        isBumperPageEnabled = in.readByte() != 0;
        shouldShowSmallClick = in.readByte() != 0;
        isBackButtonEnabled = in.readByte() != 0;
        shouldCloseAtEnd = in.readByte() != 0;
        shouldMuteOnStart = in.readByte() != 0;
        int closeState = in.readInt();
        closeButtonDelayTimer = in.readLong();
        closeButtonState = CloseButtonState.Companion.fromInt(closeState, closeButtonDelayTimer);
        shouldShowCloseWarning = in.readByte() != 0;
        orientation = SAOrientation.fromValue(in.readInt());
    }

    public static final Creator<VideoConfig> CREATOR = new Creator<VideoConfig>() {
        @Override
        public VideoConfig createFromParcel(Parcel in) {
            return new VideoConfig(in);
        }

        @Override
        public VideoConfig[] newArray(int size) {
            return new VideoConfig[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (shouldShowPadlock ? 1 : 0));
        parcel.writeByte((byte) (isParentalGateEnabled ? 1 : 0));
        parcel.writeByte((byte) (isBumperPageEnabled ? 1 : 0));
        parcel.writeByte((byte) (shouldShowSmallClick ? 1 : 0));
        parcel.writeByte((byte) (isBackButtonEnabled ? 1 : 0));
        parcel.writeByte((byte) (shouldCloseAtEnd ? 1 : 0));
        parcel.writeByte((byte) (shouldMuteOnStart ? 1 : 0));
        parcel.writeInt(closeButtonState.getValue());
        parcel.writeLong(closeButtonDelayTimer);
        parcel.writeByte((byte) (shouldShowCloseWarning ? 1 : 0));
        parcel.writeInt(orientation.ordinal());
    }
}
