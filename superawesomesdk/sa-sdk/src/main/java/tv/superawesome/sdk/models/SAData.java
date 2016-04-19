package tv.superawesome.sdk.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.List;

import tv.superawesome.lib.savast.models.SAVASTAd;

/**
 * Created by gabriel.coman on 19/04/16.
 */
public class SAData implements Parcelable {

    /**
     * The ad HTML
     */
    public String adHtml;

    /**
     * Path to an image (for strictly image format creatives)
     */
    public String imagePath;

    /**
     * A list of vast ads
     */
    public List<SAVASTAd> vastAds;

    public void print(){
        String printout = " \n\t\tDATA:\n";
        printout += "\t\t\t adHtml (len): " + adHtml.length()+ "\n";
        printout += "\t\t\t imagePath: " + imagePath + "\n";
        printout += "\t\t\t vastAds: " + vastAds.size() + "\n";
        Log.d("SuperAwesome", printout);
    }

    protected SAData(Parcel in) {
        adHtml = in.readString();
        imagePath = in.readString();
        vastAds = in.createTypedArrayList(SAVASTAd.CREATOR);
    }

    public static final Creator<SAData> CREATOR = new Creator<SAData>() {
        @Override
        public SAData createFromParcel(Parcel in) {
            return new SAData(in);
        }

        @Override
        public SAData[] newArray(int size) {
            return new SAData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(adHtml);
        dest.writeString(imagePath);
        dest.writeTypedList(vastAds);
    }
}
