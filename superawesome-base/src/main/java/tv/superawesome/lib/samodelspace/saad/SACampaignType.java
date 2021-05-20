/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.samodelspace.saad;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Enum that defines the overall types of acceptable  campaigns in AwesomeAds
 *  - CPM campaigns, where cost is done "per thousand impressions"
 *  - CMI campaigns, where cost is done "per thousand installs"
 */
public enum SACampaignType implements Parcelable {
    CPM(0) {
        @Override
        public String toString() {
            return "CPM";
        }
    },
    CPI(1) {
        @Override
        public String toString() {
            return "CPI";
        }
    };

    /**
     * Constructor that makes it possible for the enum to be initiated from an integer value
     *
     * @param i the integer value representing an enum
     */
    SACampaignType(int i) {
    }

    /**
     * Factory method that creates this enum from an integer value
     *
     * @param campaign the integer value in question
     * @return         an enum instance
     */
    public static SACampaignType fromValue (int campaign) {
        return campaign == 1 ? CPI : CPM;
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
    public static final Creator<SACampaignType> CREATOR = new Creator<SACampaignType>() {
        @Override
        public SACampaignType createFromParcel(final Parcel source) {
            return SACampaignType.values()[source.readInt()];
        }

        @Override
        public SACampaignType[] newArray(final int size) {
            return new SACampaignType[size];
        }
    };
}
