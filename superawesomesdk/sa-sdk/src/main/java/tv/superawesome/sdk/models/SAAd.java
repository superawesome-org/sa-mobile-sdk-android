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

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sautils.JSONSerializable;

/**
 * This model class contains all information that is received from the server
 * when an Ad is requested, as well as some aux fields that will be generated
 * by the SDK
 */
public class SAAd implements Parcelable, JSONSerializable {

    public int error;
    public int app;
    public int placementId;
    public int lineItemId;
    public int campaignId;
    public boolean test;
    public boolean isFallback;
    public boolean isFill;
    public boolean isHouse;
    public SACreative creative;

    /**
     * public constructor
     */
    public SAAd() {
        /** do nothing */
    }

    public SAAd(JSONObject json) throws JSONException {
        readFromJson(json);
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
        dest.writeParcelable(creative, flags);
    }

    @Override
    public void readFromJson(JSONObject json) {
        if (!json.isNull("error")) {
            error = json.optInt("error");
        }
        if (!json.isNull("app")){
            app = json.optInt("app");
        }
        if (!json.isNull("placementId")){
            placementId = json.optInt("placementId");
        }
        if (!json.isNull("line_item_id")){
            lineItemId = json.optInt("line_item_id");
        }
        if (!json.isNull("campaign_id")){
            campaignId = json.optInt("campaign_id");
        }
        if (!json.isNull("test")){
            test = json.optBoolean("test");
        }
        if (!json.isNull("is_fallback")){
            isFallback = json.optBoolean("is_fallback");
        }
        if (!json.isNull("is_fill")){
            isFill = json.optBoolean("is_fill");
        }
        if (!json.isNull("is_house")){
            isHouse = json.optBoolean("is_house");
        }
        if (!json.isNull("creative")){
            JSONObject obj = json.optJSONObject("creative");
            try {
                creative = new SACreative(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public JSONObject writeToJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("error", error);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("app", app);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("placementId", placementId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("line_item_id", lineItemId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("campaign_id", campaignId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("test", test);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("is_fallback", isFallback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("is_fill", isFill);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("is_house", isHouse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("creative", creative.writeToJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
