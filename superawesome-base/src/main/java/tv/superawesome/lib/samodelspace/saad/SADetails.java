/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.samodelspace.saad;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.StatFs;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sautils.SAUtils;

/**
 * Class that contains details about a creative, such as:
 *  - width, height
 *  - name
 *  - format
 *  - bitrate, duration, value (for video; not really used)
 *  - image, video, tag, url, vast - needed to describe the location of the creative (whether
 *    rich media, video, 3rd party tag, etc)
 *  - cdn & zip (not really used)
 *  - a SAMedia object
 *
 */
public class SADetails extends SABaseObject implements Parcelable {

    // member variables
    public int     width           = 0;
    public int     height          = 0;
    public String  name            = null;
    public String  format          = null;
    public int     bitrate         = 0;
    public int     duration        = 0;
    public int     value           = 0;
    public String  image           = null;
    public String  video           = null;
    public String  tag             = null;
    public String  zip             = null;
    public String  url             = null;
    public String  cdn             = null;
    public String  base            = null;
    public String  vast            = null;
    public SAMedia media           = new SAMedia();

    /**
     * Basic constructor
     */
    public SADetails () {
        // do nothing
    }

    /**
     * Constructor with a JSON string
     *
     * @param json a valid JSON string
     */
    public SADetails (String json) {
        JSONObject jsonObject = SAJsonParser.newObject(json);
        readFromJson(jsonObject);
    }

    /**
     * Constructor with a JSON object
     *
     * @param jsonObject a valid JSON object
     */
    public SADetails (JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    /**
     * Constructor with a Parcel object
     *
     * @param in the parcel object to read data from
     */
    protected SADetails(Parcel in) {
        width = in.readInt();
        height = in.readInt();
        name = in.readString();
        format = in.readString();
        bitrate = in.readInt();
        duration = in.readInt();
        value = in.readInt();
        image = in.readString();
        video = in.readString();
        tag = in.readString();
        zip = in.readString();
        url = in.readString();
        cdn = in.readString();
        base = in.readString();
        vast = in.readString();
        media = in.readParcelable(SAMedia.class.getClassLoader());
    }

    /**
     * Overridden SAJsonSerializable method that describes the conditions for model validity
     *
     * @return true or false
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /**
     * Overridden SAJsonSerializable method that describes how a JSON object maps to a Java model
     *
     * @param jsonObject a valid JSONObject
     */
    @Override
    public void readFromJson(JSONObject jsonObject) {
        width = SAJsonParser.getInt(jsonObject, "width", width);
        height = SAJsonParser.getInt(jsonObject, "height", height);
        name = SAJsonParser.getString(jsonObject, "name", name);
        format = SAJsonParser.getString(jsonObject, "placement_format", format);
        bitrate = SAJsonParser.getInt(jsonObject, "bitrate", bitrate);
        duration = SAJsonParser.getInt(jsonObject, "duration", duration);
        value = SAJsonParser.getInt(jsonObject, "value", value);
        image = SAJsonParser.getString(jsonObject, "image", image);
        video = SAJsonParser.getString(jsonObject, "video", video);
        tag = SAJsonParser.getString(jsonObject, "tag", tag);
        zip = SAJsonParser.getString(jsonObject, "zipFile", zip);
        url = SAJsonParser.getString(jsonObject, "url", url);
        vast = SAJsonParser.getString(jsonObject, "vast", vast);

        cdn = SAJsonParser.getString(jsonObject, "cdn", cdn);
        if (cdn == null) cdn = SAUtils.findBaseURLFromResourceURL(image);
        if (cdn == null) cdn = SAUtils.findBaseURLFromResourceURL(video);
        if (cdn == null) cdn = SAUtils.findBaseURLFromResourceURL(url);

        JSONObject mediaJson = SAJsonParser.getJsonObject(jsonObject, "media", new JSONObject());
        media = new SAMedia(mediaJson);
    }

    /**
     * Overridden SAJsonSerializable method that describes how a Java model maps to a JSON object
     *
     * @return a valid JSONObject
     */
    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(
                "width", width,
                "height", height,
                "name", name,
                "placement_format", format,
                "bitrate", bitrate,
                "duration", duration,
                "value", value,
                "image", image,
                "video", video,
                "tag", tag,
                "zipFile", zip,
                "url", url,
                "cdn", cdn,
                "base", base,
                "vast", vast,
                "media", media.writeToJson());
    }

    /**
     * Method needed for Parcelable implementation
     */
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
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(name);
        dest.writeString(format);
        dest.writeInt(bitrate);
        dest.writeInt(duration);
        dest.writeInt(value);
        dest.writeString(image);
        dest.writeString(video);
        dest.writeString(tag);
        dest.writeString(zip);
        dest.writeString(url);
        dest.writeString(cdn);
        dest.writeString(base);
        dest.writeString(vast);
        dest.writeParcelable(media, flags);
    }
}
