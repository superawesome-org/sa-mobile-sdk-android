/**
 * @class: SACreative.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk.models;

/**
 * Useful imports for this class
 **/
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * The SADetails class contains fine grained information about the creative
 * of an ad (such as width, iamge, vast, tag, etc)
 * Depending on the format of the creative, some fields are essential,
 * and some are optional
 *
 * This dependency is regulated by SAValidator.h
 */
public class SADetails implements Parcelable{

    public int width;
    public int height;
    public String image;
    public int value;
    public String name;
    public String video;
    public int bitrate;
    public int duration;
    public String vast;
    public String tag;
    public String zipFile;
    public String url;
    public String placementFormat;

    public SAData data;

    /**
     * public constructor
     */
    public SADetails() {
        /** do nothing */
    }

    protected SADetails(Parcel in) {
        width = in.readInt();
        height = in.readInt();
        image = in.readString();
        name = in.readString();
        video = in.readString();
        bitrate = in.readInt();
        duration = in.readInt();
        vast = in.readString();
        tag = in.readString();
        zipFile = in.readString();
        url = in.readString();
        placementFormat = in.readString();
        value = in.readInt();
        data = in.readParcelable(SAData.class.getClassLoader());
    }

    public static final Creator<SADetails> CREATOR = new Creator<SADetails>() {
        @Override
        public SADetails createFromParcel(Parcel in) {
            return new SADetails(in);
        }

        @Override
        public SADetails[] newArray(int size) {
            return new SADetails[size];
        }
    };

    /**
     * aux print function
     */
    public void print() {
        String printout = " \nDETAILS:\n";
        printout += "\t\t width: " + width + "\n";
        printout += "\t\t height: " + height + "\n";
        printout += "\t\t image: " + image + "\n";
        printout += "\t\t name: " + name + "\n";
        printout += "\t\t video: " + video + "\n";
        printout += "\t\t bitrate: " + bitrate + "\n";
        printout += "\t\t duration: " + duration + "\n";
        printout += "\t\t vast: " + vast + "\n";
        printout += "\t\t tag: " + tag + "\n";
        printout += "\t\t placementFormat: " + placementFormat + "\n";
        printout += "\t\t zip: " + zipFile + "\n";
        printout += "\t\t url: " + url + "\n";
        printout += "\t\t value: " + value + "\n";
        Log.d("SuperAwesome", printout);
        data.print();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(video);
        dest.writeInt(bitrate);
        dest.writeInt(duration);
        dest.writeString(vast);
        dest.writeString(tag);
        dest.writeString(zipFile);
        dest.writeString(url);
        dest.writeString(placementFormat);
        dest.writeInt(value);
        dest.writeParcelable(data, flags);
    }
}
