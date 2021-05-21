/*
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.samodelspace.referral;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sautils.SAUtils;

/**
 * Class that contains information for data usually needed by the CPI process.
 * It's used when the Google Play Store sends additional information when installing
 * an app through our campaign system, such as:
 *  - the campaign, line item (sub-campaign), creative and placement IDs that generated the
 *    current install
 *  - the configuration (staging or production) where the install generated
 *
 */
public class SAReferral extends SABaseObject implements Parcelable {

    // constants
    private static final int CPI_DEF_VAL = -1;

    // member variables
    public int configuration = CPI_DEF_VAL;
    public int campaignId    = CPI_DEF_VAL;
    public int lineItemId    = CPI_DEF_VAL;
    public int creativeId    = CPI_DEF_VAL;
    public int placementId   = CPI_DEF_VAL;

    /**
     * Basic constructor
     */
    public SAReferral() {
        // do nothing
    }

    /**
     * Constructor with member variables
     *
     * @param configuration current configuration (0 = production, 1 = staging)
     * @param campaignId    campaign id
     * @param lineItemId    sub-campaign id
     * @param creativeId    creative id
     * @param placementId   placement id
     */
    public SAReferral(int configuration, int campaignId, int lineItemId, int creativeId, int placementId) {
        this.configuration = configuration;
        this.campaignId = campaignId;
        this.lineItemId = lineItemId;
        this.creativeId = creativeId;
        this.placementId = placementId;
    }

    /**
     * Constructor with a JSON string
     *
     * @param json a valid JSON string
     */
    public SAReferral(String json) {
        JSONObject jsonObject = SAJsonParser.newObject(json);
        readFromJson(jsonObject);
    }

    /**
     * Constructor with a JSON object
     *
     * @param jsonObject a valid JSON object
     */
    public SAReferral(JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    /**
     * Constructor with a Parcel object
     *
     * @param in the parcel object to read data from
     */
    protected SAReferral(Parcel in) {
        configuration = in.readInt();
        campaignId = in.readInt();
        lineItemId = in.readInt();
        creativeId = in.readInt();
        placementId = in.readInt();
    }

    /**
     * Overridden SAJsonSerializable method that describes the conditions for model validity
     *
     * @return true or false whether
     */
    @Override
    public boolean isValid() {
        return placementId > CPI_DEF_VAL &&
                campaignId > CPI_DEF_VAL &&
                lineItemId > CPI_DEF_VAL &&
                creativeId > CPI_DEF_VAL ;
    }

    /**
     * Overridden SAJsonSerializable method that describes how a JSON object maps to a Java model
     *
     * @param jsonObject a valid JSONObject
     */
    @Override
    public void readFromJson(JSONObject jsonObject) {
        configuration = SAJsonParser.getInt(jsonObject, "utm_source", configuration);
        campaignId = SAJsonParser.getInt(jsonObject, "utm_campaign", campaignId);
        lineItemId = SAJsonParser.getInt(jsonObject, "utm_term", lineItemId);
        creativeId = SAJsonParser.getInt(jsonObject, "utm_content", creativeId);
        placementId = SAJsonParser.getInt(jsonObject, "utm_medium", placementId);
    }

    /**
     * Overridden SAJsonSerializable method that describes how a Java model maps to a JSON object
     *
     * @return a valid JSONObject
     */
    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(
                "utm_source", configuration,
                "utm_campaign", campaignId,
                "utm_term", lineItemId,
                "utm_content", creativeId,
                "utm_medium", placementId);
    }

    /**
     * Method that writes as a referral html encoded query
     *
     * @return a string
     */
    public String writeToReferralQuery () {
        return SAUtils.formGetQueryFromDict(writeToJson()).replace("&", "%26").replace("=", "%3D");
    }

    /**
     * Method needed for Parcelable implementation
     */
    public static final Creator<SAReferral> CREATOR = new Creator<SAReferral>() {
        @Override
        public SAReferral createFromParcel(Parcel in) {
            return new SAReferral(in);
        }

        @Override
        public SAReferral[] newArray(int size) {
            return new SAReferral[size];
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
        dest.writeInt(configuration);
        dest.writeInt(campaignId);
        dest.writeInt(lineItemId);
        dest.writeInt(creativeId);
        dest.writeInt(placementId);
    }

}
