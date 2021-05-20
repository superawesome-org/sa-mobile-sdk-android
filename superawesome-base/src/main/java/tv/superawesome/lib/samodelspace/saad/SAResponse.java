/*
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.samodelspace.saad;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sajsonparser.SAJsonToList;
import tv.superawesome.lib.sajsonparser.SAListToJson;

/**
 * Class that defines an ad server response in AwesomeAds.
 * Each response can contain:
 *  - a placement id (that the ad request was made to)
 *  - the status of the network request
 *  - the general format of the ad / ads in the response; if there's a single ad in the list, the
 *    format is the same; if there are multiple it should only be appwall
 *  - a list of ads; usually used for appwall
 */
public class SAResponse extends SABaseObject implements Parcelable {

    // member variables
    public int              placementId = 0;
    public int              status      = 0;
    public SACreativeFormat format      = SACreativeFormat.invalid;
    public List<SAAd>       ads         = new ArrayList<>();

    /**
     * Basic constructor
     */
    public SAResponse () {
        // do nothing
    }

    /**
     * Constructor with a JSON string
     *
     * @param json a valid JSON string
     */
    public SAResponse (String json) {
        JSONObject jsonObject = SAJsonParser.newObject(json);
        readFromJson(jsonObject);
    }

    /**
     * Constructor with a JSON object
     *
     * @param jsonObject a valid JSON object
     */
    public SAResponse (JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    /**
     * Constructor with a Parcel object
     *
     * @param in the parcel object to read data from
     */
    protected SAResponse(Parcel in) {
        status = in.readInt();
        placementId = in.readInt();
        ads = in.createTypedArrayList(SAAd.CREATOR);
        format = in.readParcelable(SACreativeFormat.class.getClassLoader());
    }

    /**
     * Overridden SAJsonSerializable method that describes the conditions for model validity
     *
     * @return true or false
     */
    @Override
    public boolean isValid() {
        boolean allAdsValid = true;
        for (SAAd ad : ads) {
            if (!ad.isValid()) {
                allAdsValid = false;
                break;
            }
        }
        return ads.size() >= 1 && allAdsValid;
    }

    /**
     * Overridden SAJsonSerializable method that describes how a JSON object maps to a Java model
     *
     * @param jsonObject a valid JSONObject
     */
    @Override
    public void readFromJson(JSONObject jsonObject) {
        status = SAJsonParser.getInt(jsonObject, "status", status);
        placementId = SAJsonParser.getInt(jsonObject, "placementId", placementId);
        format = SACreativeFormat.fromValue(SAJsonParser.getInt(jsonObject, "format", format.ordinal()));

        JSONArray adsArray = SAJsonParser.getJsonArray(jsonObject, "ads", new JSONArray());
        ads = SAJsonParser.getListFromJsonArray(adsArray, new SAJsonToList<SAAd, JSONObject>() {
            @Override
            public SAAd traverseItem(JSONObject jsonObject) {
                return new SAAd(jsonObject);
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
                "status", status,
                "placementId", placementId,
                "format", format.ordinal(),
                "ads", SAJsonParser.getJsonArrayFromList(ads, new SAListToJson<JSONObject, SAAd>() {
                            @Override
                            public JSONObject traverseItem(SAAd saAd) {
                                return saAd.writeToJson();
                            }
                        }));
    }

    /**
     * Method needed for Parcelable implementation
     */
    public static final Creator<SAResponse> CREATOR = new Creator<SAResponse>() {
        @Override
        public SAResponse createFromParcel(Parcel in) {
            return new SAResponse(in);
        }

        @Override
        public SAResponse[] newArray(int size) {
            return new SAResponse[size];
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
        dest.writeInt(status);
        dest.writeInt(placementId);
        dest.writeTypedList(ads);
        dest.writeParcelable(format, flags);
    }
}
