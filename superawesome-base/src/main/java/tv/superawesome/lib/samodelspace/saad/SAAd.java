/*
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.samodelspace.saad;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.referral.SAReferral;

/**
 * Main class that contains all the information needed to play an ad and send all relevant
 * events back to our ad server.
 * - error (not really used)
 * - advertiser, publisher, app, line item, campaign, placement IDs
 * - campaign type (CPM or CPI)
 * - test, is fallback, is fill, is house, safe ad approved, show padlock - flags that determine
 * whether the SDK should show the "Safe Ad Padlock" over an ad or not
 * - device
 * - a SACreative object
 */
public class SAAd extends SABaseObject implements Parcelable {

    // member variables
    public int error = 0;
    public int advertiserId = 0;
    public int publisherId = 0;
    public int appId = 0;
    public int lineItemId = 0;
    public int campaignId = 0;
    public int placementId = 0;
    public int configuration = 0;

    public SACampaignType campaignType = SACampaignType.CPM;

    public boolean isTest = false;
    public boolean isFallback = false;
    public boolean isFill = false;
    public boolean isHouse = false;
    public boolean isSafeAdApproved = false;
    public boolean isPadlockVisible = false;
    public boolean isVpaid = false;

    public String device = null;

    public SACreative creative = new SACreative();

    public long loadTime;
    public Map<String, Object> requestOptions = new HashMap<>();

    /**
     * Basic constructor
     */
    public SAAd() {
        loadTime = System.currentTimeMillis() / 1000L;
    }

    /**
     * Constructor with a JSON string
     *
     * @param json a valid JSON string
     */
    public SAAd(String json) {
        this();
        JSONObject jsonObject = SAJsonParser.newObject(json);
        readFromJson(jsonObject);
    }

    /**
     * Constructor with a JSON object
     *
     * @param jsonObject a valid JSON object
     */
    public SAAd(JSONObject jsonObject) {
        this();
        readFromJson(jsonObject);
    }

    /**
     * Constructor with placement id, configuration (as an integer) and json
     *
     * @param placementId   placement id of the ad (this *should* be returned by the ad response)
     * @param configuration configuration (STAGING or PRODUCTION) as an integer
     * @param json          a valid JSON string
     */
    public SAAd(int placementId, int configuration, String json) {
        this();
        this.placementId = placementId;
        this.configuration = configuration;
        JSONObject jsonObject = SAJsonParser.newObject(json);
        readFromJson(jsonObject);
    }

    /**
     * Constructor with placement id, configuration (as an integer) and json
     *
     * @param placementId   placement id of the ad (this *should* be returned by the ad response)
     * @param configuration configuration (STAGING or PRODUCTION) as an integer
     * @param jsonObject    a valid JSON object
     */
    public SAAd(int placementId, int configuration, JSONObject jsonObject) {
        this();
        this.placementId = placementId;
        this.configuration = configuration;
        readFromJson(jsonObject);
    }

    /**
     * Constructor with placement id, configuration (as an integer), request options and json
     *
     * @param placementId   placement id of the ad (this *should* be returned by the ad response)
     * @param configuration configuration (STAGING or PRODUCTION) as an integer
     * @param requestOptions a dictionary of additional data sent with the ad's request. Used for events.
     * @param jsonObject    a valid JSON object
     */
    public SAAd(int placementId, int configuration, Map<String, Object> requestOptions, JSONObject jsonObject) {
        this();
        this.placementId = placementId;
        this.configuration = configuration;
        this.requestOptions = requestOptions;
        readFromJson(jsonObject);
    }

    /**
     * Constructor with a Parcel object
     *
     * @param in the parcel object to read data from
     */
    protected SAAd(Parcel in) {
        error = in.readInt();
        advertiserId = in.readInt();
        publisherId = in.readInt();
        appId = in.readInt();
        lineItemId = in.readInt();
        campaignId = in.readInt();
        placementId = in.readInt();
        configuration = in.readInt();
        campaignType = in.readParcelable(SACampaignType.class.getClassLoader());
        isTest = in.readByte() != 0;
        isFallback = in.readByte() != 0;
        isFill = in.readByte() != 0;
        isHouse = in.readByte() != 0;
        isSafeAdApproved = in.readByte() != 0;
        isPadlockVisible = in.readByte() != 0;
        device = in.readString();
        creative = in.readParcelable(SACreative.class.getClassLoader());
        loadTime = in.readLong();
    }

    /**
     * Overridden SAJsonSerializable method that describes the conditions for model validity
     *
     * @return true or false
     */
    @Override
    public boolean isValid() {

        switch (creative.format) {
            case invalid: {
                return false;
            }
            case image: {
                return creative.details.image != null && creative.details.media.html != null;
            }
            case rich: {
                return creative.details.url != null && creative.details.media.html != null;
            }
            case video: {
                return (creative.details.vast != null &&
                        creative.details.media.url != null &&
                        creative.details.media.path != null &&
                        creative.details.media.isDownloaded)
                        || (this.isVpaid && isTagValid(creative));
            }
            case tag: {
                return isTagValid(creative);
            }
            case appwall: {
                return creative.details.image != null &&
                        creative.details.media.url != null &&
                        creative.details.media.path != null &&
                        creative.details.media.isDownloaded;
            }
        }

        return false;
    }

    /**
     * Overridden SAJsonSerializable method that describes how a JSON object maps to a Java model
     *
     * @param jsonObject a valid JSONObject
     */
    @Override
    public void readFromJson(JSONObject jsonObject) {

        error = SAJsonParser.getInt(jsonObject, "error", error);
        advertiserId = SAJsonParser.getInt(jsonObject, "advertiserId", advertiserId);
        publisherId = SAJsonParser.getInt(jsonObject, "publisherId", publisherId);
        appId = SAJsonParser.getInt(jsonObject, "app", appId);

        lineItemId = SAJsonParser.getInt(jsonObject, "line_item_id", lineItemId);
        campaignId = SAJsonParser.getInt(jsonObject, "campaign_id", campaignId);
        placementId = SAJsonParser.getInt(jsonObject, "placementId", placementId);

        configuration = SAJsonParser.getInt(jsonObject, "configuration", configuration);

        int campaign = SAJsonParser.getInt(jsonObject, "campaign_type", 0);
        campaignType = SACampaignType.fromValue(campaign);

        isTest = SAJsonParser.getBoolean(jsonObject, "test", isTest);
        isFallback = SAJsonParser.getBoolean(jsonObject, "is_fallback", isFallback);
        isFill = SAJsonParser.getBoolean(jsonObject, "is_fill", isFill);
        isHouse = SAJsonParser.getBoolean(jsonObject, "is_house", isHouse);
        isVpaid = SAJsonParser.getBoolean(jsonObject, "is_vpaid", isVpaid);
        isSafeAdApproved = SAJsonParser.getBoolean(jsonObject, "safe_ad_approved", isSafeAdApproved);
        isPadlockVisible = SAJsonParser.getBoolean(jsonObject, "show_padlock", isPadlockVisible);
        device = SAJsonParser.getString(jsonObject, "device", device);
        String ksfRequest = SAJsonParser.getString(jsonObject, "ksfRequest", null);

        JSONObject creativeJson = SAJsonParser.getJsonObject(jsonObject, "creative", new JSONObject());
        creative = new SACreative(creativeJson);
        creative.referral = new SAReferral(configuration, campaignId, lineItemId, creative.id, placementId);

        loadTime = SAJsonParser.getLong(jsonObject, "loadTime", loadTime);

        if (isPadlockVisible && ksfRequest != null && ksfRequest.length() > 0) {
            isPadlockVisible = false;
        }
    }

    /**
     * Overridden SAJsonSerializable method that describes how a Java model maps to a JSON object
     *
     * @return a valid JSONObject
     */
    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(
                "error", error,
                "advertiserId", advertiserId,
                "publisherId", publisherId,
                "app", appId,
                "line_item_id", lineItemId,
                "campaign_id", campaignId,
                "placementId", placementId,
                "configuration", configuration,
                "campaign_type", campaignType.ordinal(),
                "test", isTest,
                "is_fallback", isFallback,
                "is_fill", isFill,
                "is_house", isHouse,
                "safe_ad_approved", isSafeAdApproved,
                "show_padlock", isPadlockVisible,
                "creative", creative.writeToJson(),
                "device", device,
                "loadTime", loadTime);
    }

    /**
     * Method needed for Parcelable implementation
     */
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
        dest.writeInt(error);
        dest.writeInt(advertiserId);
        dest.writeInt(publisherId);
        dest.writeInt(appId);
        dest.writeInt(lineItemId);
        dest.writeInt(campaignId);
        dest.writeInt(placementId);
        dest.writeInt(configuration);
        dest.writeParcelable(campaignType, flags);
        dest.writeByte((byte) (isTest ? 1 : 0));
        dest.writeByte((byte) (isFallback ? 1 : 0));
        dest.writeByte((byte) (isFill ? 1 : 0));
        dest.writeByte((byte) (isHouse ? 1 : 0));
        dest.writeByte((byte) (isSafeAdApproved ? 1 : 0));
        dest.writeByte((byte) (isPadlockVisible ? 1 : 0));
        dest.writeString(device);
        dest.writeParcelable(creative, flags);
        dest.writeLong(loadTime);
    }

    private boolean isTagValid(SACreative creative) {
        return creative.details.tag != null && creative.details.media.html != null;
    }
}
