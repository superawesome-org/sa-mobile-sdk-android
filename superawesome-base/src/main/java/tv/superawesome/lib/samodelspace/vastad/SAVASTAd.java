/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.samodelspace.vastad;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sajsonparser.SAJsonToList;
import tv.superawesome.lib.sajsonparser.SAListToJson;

/**
 * Class that represents a VAST Ad
 *  - a VAST URL redirect
 *  - a VAST type (starting out as Invalid, but should either be InLine or Wrapper)
 *  - a media URL
 *  - an array of vast tracking elements
 */
public class SAVASTAd extends SABaseObject implements Parcelable {

    // member variables
    public String            redirect = null;
    public SAVASTAdType      type     = SAVASTAdType.Invalid;
    public String            url      = null;
    public List<SAVASTMedia> media    = new ArrayList<>();
    public List<SAVASTEvent> events   = new ArrayList<>();

    /**
     * Basic constructor
     */
    public SAVASTAd () {
        // do nothing
    }

    /**
     * Constructor with a JSON string
     *
     * @param json a valid JSON string
     */
    public SAVASTAd (String json) {
        JSONObject jsonObject = SAJsonParser.newObject(json);
        readFromJson(jsonObject);
    }

    /**
     * Constructor with a JSON object
     *
     * @param jsonObject a valid JSON object
     */
    public SAVASTAd (JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    /**
     * Constructor with a Parcel object
     *
     * @param in the parcel object to read data from
     */
    protected SAVASTAd(Parcel in) {
        redirect = in.readString();
        type = in.readParcelable(SAVASTAdType.class.getClassLoader());
        url = in.readString();
        media = in.createTypedArrayList(SAVASTMedia.CREATOR);
        events = in.createTypedArrayList(SAVASTEvent.CREATOR);
    }

    /**
     * Specific method that sums two ads together
     *
     * @param toBeAdded the ad that's going to be added
     */
    public void sumAd (SAVASTAd toBeAdded) {
        url = toBeAdded.url != null ? toBeAdded.url : url;
        events.addAll(toBeAdded.events);
        media.addAll(toBeAdded.media);
    }

    /**
     * Overridden SAJsonSerializable method that describes the conditions for model validity
     *
     * @return true or false
     */
    @Override
    public boolean isValid() {
        return url != null && type != SAVASTAdType.Invalid && media.size() >= 1;
    }

    /**
     * Overridden SAJsonSerializable method that describes how a JSON object maps to a Java model
     *
     * @param jsonObject a valid JSONObject
     */
    @Override
    public void readFromJson(JSONObject jsonObject) {
        redirect = SAJsonParser.getString(jsonObject, "redirect", null);
        url = SAJsonParser.getString(jsonObject, "url", null);
        type = SAVASTAdType.fromValue(SAJsonParser.getInt(jsonObject, "type", 0));

        media = SAJsonParser.getListFromJsonArray(jsonObject, "media", new SAJsonToList<SAVASTMedia, JSONObject>() {
            @Override
            public SAVASTMedia traverseItem(JSONObject jsonObject) {
                return new SAVASTMedia(jsonObject);
            }
        });

        events = SAJsonParser.getListFromJsonArray(jsonObject, "events", new SAJsonToList<SAVASTEvent, JSONObject>() {
            @Override
            public SAVASTEvent traverseItem(JSONObject jsonObject) {
                return new SAVASTEvent(jsonObject);
            }
        });
    }

    /**
     * Overridden SAJsonSerializable method that describes how a Java model maps to a JSON object
     *
     * @return a valid JSONObject
     */
    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(
                "redirect", redirect,
                "url", url,
                "type", type.ordinal(),
                "media", SAJsonParser.getJsonArrayFromList(media, new SAListToJson<JSONObject, SAVASTMedia>() {
                    @Override
                    public JSONObject traverseItem(SAVASTMedia savastMedia) {
                        return savastMedia.writeToJson();
                    }
                }),
                "events", SAJsonParser.getJsonArrayFromList(events, new SAListToJson<JSONObject, SAVASTEvent>() {
                    @Override
                    public JSONObject traverseItem(SAVASTEvent saTracking) {
                        return saTracking.writeToJson();
                    }
                }));
    }

    /**
     * Method needed for Parcelable implementation
     */
    public static final Creator<SAVASTAd> CREATOR = new Creator<SAVASTAd>() {
        @Override
        public SAVASTAd createFromParcel(Parcel in) {
            return new SAVASTAd(in);
        }

        @Override
        public SAVASTAd[] newArray(int size) {
            return new SAVASTAd[size];
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
        dest.writeString(redirect);
        dest.writeParcelable(type, flags);
        dest.writeString(url);
        dest.writeTypedList(media);
        dest.writeTypedList(events);
    }
}