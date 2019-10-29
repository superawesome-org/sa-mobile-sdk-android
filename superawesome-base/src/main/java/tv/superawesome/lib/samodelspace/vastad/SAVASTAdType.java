/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.samodelspace.vastad;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Enum that defines the types of VAST ads available
 *  - Invalid: when a valid vast ad could not be found
 *  - InLine: when a direct vast ad could be found (which should also contain a video)
 *  - Wrapper: when the current vast tag should redirect to another one down the line
 */
public enum SAVASTAdType implements Parcelable {
    Invalid (0) {
        @Override
        public String toString() {
            return "Invalid";
        }
    },
    InLine (1) {
        @Override
        public String toString() {
            return "InLine";
        }
    },
    Wrapper (2) {
        @Override
        public String toString() {
            return "Wrapper";
        }
    };

    private final int value;

    /**
     * Constructor that makes it possible for the enum to be initiated from an integer value
     *
     * @param i the integer value representing an enum
     */
    SAVASTAdType(int i) {
        this.value = i;
    }

    /**
     * Factory method that creates this enum from an integer value
     *
     * @param type the integer value in question
     * @return     an enum instance
     */
    public static SAVASTAdType fromValue (int type) {
        return type == 2 ? Wrapper : type == 1 ? InLine : Invalid;
    }

    /**
     * Method needed for Parcelable implementation
     *
     * @return always 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Method needed for Parcelable implementation
     *
     * @param dest  destination parcel
     * @param flags special flags
     */
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    /**
     * Method needed for Parcelable implementation
     */
    public static final Creator<SAVASTAdType> CREATOR = new Creator<SAVASTAdType>() {
        @Override
        public SAVASTAdType createFromParcel(final Parcel source) {
            return SAVASTAdType.values()[source.readInt()];
        }

        @Override
        public SAVASTAdType[] newArray(final int size) {
            return new SAVASTAdType[size];
        }
    };
}
