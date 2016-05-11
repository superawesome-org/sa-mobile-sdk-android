/**
 * @class: SAAd.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk.models;

/**
 * Imports needed for this class
 */
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * This model class contains all information that is received from the server
 * when an Ad is requested, as well as some aux fields that will be generated
 * by the SDK
 */
public class SAAd implements Parcelable {

    /**
     * the SA server can send an error; if that's the case, this field will not be nill
     */
    public int error;

    /**
     * the associated app id
     */
    public int app;

    /**
     * the ID of the placement that the ad was sent for
     */
    public int placementId;

    /**
     * line item
     */
    public int lineItemId;

    /**
     * the ID of the campaign that the ad is a part of
     */
    public int campaignId;

    /**
     * is true when the ad is a test ad
     */
    public boolean test;

    /**
     * is true when ad is fallback (fallback ads are sent when there are no
     * real ads to display for a certain placement)
     */
    public boolean isFallback;
    public boolean isFill;
    public boolean isHouse;

    /**
     * the Json of the original Ad - as sent by the server
     */
    public String adJson;

    /**
     * pointer to the creative data associated with the ad
     */
    public SACreative creative;

    /**
     * public constructor
     */
    public SAAd() {
        /** do nothing */
    }

    protected SAAd(Parcel in) {
        error = in.readInt();
        app = in.readInt();
        placementId = in.readInt();
        lineItemId = in.readInt();
        campaignId = in.readInt();
        test = in.readByte() != 0;
        isFallback = in.readByte() != 0;
        isFill = in.readByte() != 0;
        isHouse = in.readByte() != 0;
        adJson = in.readString();
        creative = in.readParcelable(SACreative.class.getClassLoader());
    }

    public static final Creator<SAAd> CREATOR = new Creator<SAAd>() {
        @Override
        public SAAd createFromParcel(Parcel in) {
            return new SAAd(in);
        }

        @Override
        public SAAd[] newArray(int size) {
            return new SAAd[size];
        }
    };

    /**
     * aux print function
     */
    public void print() {
        String printout = " \nAD:\n";
        printout += "error: " + error + "\n";
        printout += "app: " + app + "\n";
        printout += "placementId: " + placementId + "\n";
        printout += "lineItemId: " + lineItemId + "\n";
        printout += "campaignId: " + campaignId + "\n";
        printout += "isTest: " + test + "\n";
        printout += "isFallback: " + isFallback + "\n";
        printout += "isFill: " + isFill + "\n";
        Log.d("SuperAwesome", printout);
        creative.print();
    }

    public void shortPrint(){
        String printout = "Ad[" + placementId + "] " + creative.creativeFormat.toString() + " ";
        // printout += (creative.format == SACreativeFormat.video ? creative.details.vast + " " + creative.details.data.vastAds.size() + " VAST ads" : "");
        printout += (creative.creativeFormat == SACreativeFormat.image ? creative.details.image : "");
        printout += (creative.creativeFormat == SACreativeFormat.rich ? creative.details.url : "");
        printout += (creative.creativeFormat == SACreativeFormat.tag ? creative.details.tag : "");
        Log.d("SuperAwesome", printout);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(error);
        dest.writeInt(app);
        dest.writeInt(placementId);
        dest.writeInt(lineItemId);
        dest.writeInt(campaignId);
        dest.writeByte((byte) (test ? 1 : 0));
        dest.writeByte((byte) (isFallback ? 1 : 0));
        dest.writeByte((byte) (isFill ? 1 : 0));
        dest.writeByte((byte) (isHouse ? 1 : 0));
        dest.writeString(adJson);
        dest.writeParcelable(creative, flags);
    }
}
