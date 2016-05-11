/**
 * @class: SACreative.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk.models;

/**
 * imports for this class
 */
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;


/**
 * The creative contains essential ad information like format, click url
 * and such
 */
public class SACreative implements Parcelable {

    public int id;
    public String name;
    public int cpm;
    public String format;
    public String impressionUrl;
    public String clickUrl;
    public boolean live;
    public boolean approved;
    public SADetails details;

    public SACreativeFormat creativeFormat;
    public String viewableImpressionUrl;
    public String trackingUrl;
    public String parentalGateClickUrl;

    /**
     * public constructor
     */
    public SACreative() {
        /** do nothing */
    }

    protected SACreative(Parcel in) {
        id = in.readInt();
        name = in.readString();
        cpm = in.readInt();
        format = in.readString();
        impressionUrl = in.readString();
        clickUrl = in.readString();
        live = in.readByte() != 0;
        approved = in.readByte() != 0;
        details = in.readParcelable(SADetails.class.getClassLoader());viewableImpressionUrl = in.readString();

        creativeFormat = in.readParcelable(SACreativeFormat.class.getClassLoader());
        viewableImpressionUrl = in.readString();
        trackingUrl = in.readString();
        parentalGateClickUrl = in.readString();
    }

    public static final Creator<SACreative> CREATOR = new Creator<SACreative>() {
        @Override
        public SACreative createFromParcel(Parcel in) {
            return new SACreative(in);
        }

        @Override
        public SACreative[] newArray(int size) {
            return new SACreative[size];
        }
    };

    /**
     * aux print function
     */
    public void print() {
        String printout = " \nCREATIVE:\n";
        printout += "\t id: " + id + "\n";
        printout += "\t name: " + name + "\n";
        printout += "\t cpm: " + cpm + "\n";
        printout += "\t format: " + format + "\n";
        printout += "\t impressionURL: " + impressionUrl + "\n";
        printout += "\t clickURL: " + clickUrl + "\n";
        printout += "\t approved: " + approved + "\n";
        printout += "\t live: " + live + "\n";

        printout += "\t viewableImpressionURL: " + viewableImpressionUrl + "\n";
        printout += "\t trackingURL: " + trackingUrl + "\n";
        printout += "\t creativeFormat: " + creativeFormat.toString() + "\n";
        printout += "\t parentalGateClickURL: " + parentalGateClickUrl + "\n";
        Log.d("SuperAwesome", printout);
        details.print();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(cpm);
        dest.writeString(format);
        dest.writeString(impressionUrl);
        dest.writeString(clickUrl);
        dest.writeByte((byte) (approved ? 1 : 0));
        dest.writeByte((byte) (live ? 1: 0));
        dest.writeParcelable(details, flags);

        dest.writeString(viewableImpressionUrl);
        dest.writeString(trackingUrl);
        dest.writeParcelable(creativeFormat, flags);
        dest.writeString(parentalGateClickUrl);

    }
}
