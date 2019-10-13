/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.samodelspace.vastad;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Class that represents a VAST media object, containing:
 *  - a type (mp4, wav, etc)
 *  - a media Url
 *  - bitrate
 *  - width and height
 */
public class SAVASTMedia extends SABaseObject implements Parcelable {

    // member variables
    public String type     = null;
    public String url      = null;
    public int    bitrate  = 0;
    public int    width    = 0;
    public int    height   = 0;

    /**
     * Basic constructor
     */
    public SAVASTMedia () {
        //
    }

    /**
     * Constructor with a JSON string
     *
     * @param json a valid JSON string
     */
    public SAVASTMedia (String json) {
        JSONObject jsonObject = SAJsonParser.newObject(json);
        readFromJson(jsonObject);
    }

    /**
     * Constructor with a JSON object
     *
     * @param jsonObject a valid JSON object
     */
    public SAVASTMedia (JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    /**
     * Constructor with a Parcel object
     *
     * @param in the parcel object to read data from
     */
    protected SAVASTMedia(Parcel in) {
        type = in.readString();
        url = in.readString();
        bitrate = in.readInt();
        width = in.readInt();
        height = in.readInt();
    }

    /**
     * Overridden SAJsonSerializable method that describes the conditions for model validity
     *
     * @return true or false
     */
    @Override
    public boolean isValid() {
        return url != null;
    }

    /**
     * Overridden SAJsonSerializable method that describes how a JSON object maps to a Java model
     *
     * @param jsonObject a valid JSONObject
     */
    @Override
    public void readFromJson(JSONObject jsonObject) {
        type = SAJsonParser.getString(jsonObject, "type", null);
        url = SAJsonParser.getString(jsonObject, "url", null);
        bitrate = SAJsonParser.getInt(jsonObject, "bitrate", 0);
        width = SAJsonParser.getInt(jsonObject, "width", 0);
        height = SAJsonParser.getInt(jsonObject, "height", 0);
    }

    /**
     * Overridden SAJsonSerializable method that describes how a Java model maps to a JSON object
     *
     * @return a valid JSONObject
     */
    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(
                "type", type,
                "url", url,
                "bitrate", bitrate,
                "width", width,
                "height", height);
    }

    /**
     * Method needed for Parcelable implementation
     */
    public static final Creator<SAVASTMedia> CREATOR = new Creator<SAVASTMedia>() {
        @Override
        public SAVASTMedia createFromParcel(Parcel in) {
            return new SAVASTMedia(in);
        }

        @Override
        public SAVASTMedia[] newArray(int size) {
            return new SAVASTMedia[size];
        }
    };

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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(url);
        dest.writeInt(bitrate);
        dest.writeInt(width);
        dest.writeInt(height);
    }
}
