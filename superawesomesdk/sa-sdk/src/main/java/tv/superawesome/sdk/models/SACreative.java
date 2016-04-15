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

    /** the creative ID is a unique ID associated by the server with this Ad */
    public int creativeId;

    /** name of the creative - set by the user in the dashboard */
    public String name;

    /** agreed upon CPM; not really a useful field */
    public int cpm;

    /**
     * the creative format defines the type of ad (image, video, rich media, tag, etc)
     * and is an enum defined in SACreativeFormat.h
     */
    public String baseFormat;
    public SACreativeFormat format;

    /** the impression URL; not really useful */
    public String impressionURL;

    /** the viewable impression URL - used to send impression data to the Ad server */
    public String viewableImpressionURL;

    /**
     * the click URL taken from the Ad server - usually it's used in conjunction with the
     * tracking URL to form the complete URL
     * */
    public String clickURL;

    /** the tracking URL is used to send clicks to the Ad server */
    public String trackingURL;

    /**
     * The URL that's going to be called when the parental gate is called as well
     */
    public String parentalGateClickURL;

    /** must be always true for real ads */
    public boolean approved;

    /** pointer to a SADetails object containing even more creative information */
    public SADetails details;

    /** public constructor */
    public SACreative(){
        /** do nothing */
    }

    /** aux print function */
    public void print() {
        String printout = " \nCREATIVE:\n";
        printout += "\t creativeId: " + creativeId + "\n";
        printout += "\t name: " + name + "\n";
        printout += "\t cpm: " + cpm + "\n";
        printout += "\t baseFormat: " + baseFormat + "\n";
        printout += "\t format: " + format.toString() + "\n";
        printout += "\t impressionURL: " + impressionURL + "\n";
        printout += "\t viewableImpressionURL: " + viewableImpressionURL + "\n";
        printout += "\t clickURL: " + clickURL + "\n";
        printout += "\t trackingURL: " + trackingURL + "\n";
        printout += "\t parentalGateClickURL: " + parentalGateClickURL + "\n";
        printout += "\t approved: " + approved + "\n";
        Log.d("SuperAwesome", printout);
        details.print();
    }

    /** <Parceable> implementation */
    protected SACreative(Parcel in) {
        creativeId = in.readInt();
        name = in.readString();
        cpm = in.readInt();
        baseFormat = in.readString();
        impressionURL = in.readString();
        viewableImpressionURL = in.readString();
        clickURL = in.readString();
        trackingURL = in.readString();
        parentalGateClickURL = in.readString();
        approved = in.readByte() != 0;
        details = in.readParcelable(SADetails.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(creativeId);
        dest.writeString(name);
        dest.writeInt(cpm);
        dest.writeString(baseFormat);
        dest.writeString(impressionURL);
        dest.writeString(viewableImpressionURL);
        dest.writeString(clickURL);
        dest.writeString(trackingURL);
        dest.writeString(parentalGateClickURL);
        dest.writeByte((byte) (approved ? 1 : 0));
        dest.writeParcelable(details, flags);
    }
}
