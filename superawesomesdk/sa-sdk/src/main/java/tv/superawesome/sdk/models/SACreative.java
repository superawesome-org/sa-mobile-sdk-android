/**
 * @class: SACreative.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk.models;

/**
 * imports for this class
 */
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sautils.JSONSerializable;


/**
 * The creative contains essential ad information like format, click url
 * and such
 */
public class SACreative implements Parcelable, JSONSerializable {

    public int id;
    public String name;
    public int cpm;
    public String format;
    public String impressionUrl;
    public String clickUrl;
    public boolean live;
    public boolean approved;
    public SADetails details;

    public SACreativeFormat creativeFormat;
    public String viewableImpressionUrl;
    public String trackingUrl;
    public String parentalGateClickUrl;

    /**
     * public constructor
     */
    public SACreative() {
        /** do nothing */
    }

    public SACreative(JSONObject json) throws JSONException {
        readFromJson(json);
    }

    protected SACreative(Parcel in) {
        id = in.readInt();
        name = in.readString();
        cpm = in.readInt();
        format = in.readString();
        impressionUrl = in.readString();
        clickUrl = in.readString();
        live = in.readByte() != 0;
        approved = in.readByte() != 0;
        details = in.readParcelable(SADetails.class.getClassLoader());viewableImpressionUrl = in.readString();

        creativeFormat = in.readParcelable(SACreativeFormat.class.getClassLoader());
        viewableImpressionUrl = in.readString();
        trackingUrl = in.readString();
        parentalGateClickUrl = in.readString();
    }

    public static final Creator<SACreative> CREATOR = new Creator<SACreative>() {
        @Override
        public SACreative createFromParcel(Parcel in) {
            return new SACreative(in);
        }

        @Override
        public SACreative[] newArray(int size) {
            return new SACreative[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(cpm);
        dest.writeString(format);
        dest.writeString(impressionUrl);
        dest.writeString(clickUrl);
        dest.writeByte((byte) (approved ? 1 : 0));
        dest.writeByte((byte) (live ? 1: 0));
        dest.writeParcelable(details, flags);

        dest.writeString(viewableImpressionUrl);
        dest.writeString(trackingUrl);
        dest.writeParcelable(creativeFormat, flags);
        dest.writeString(parentalGateClickUrl);

    }

    @Override
    public void readFromJson(JSONObject json) {
        if (!json.isNull("id")){
            id = json.optInt("id");
        }
        if (!json.isNull("name")){
            name = json.optString("name");
        }
        if (!json.isNull("cpm")){
            cpm = json.optInt("cpm");
        }
        if (!json.isNull("format")){
            format = json.optString("format");
        }
        if (!json.isNull("impression_url")){
            impressionUrl = json.optString("impression_url");
        }
        if (!json.isNull("click_url")){
            clickUrl = json.optString("click_url");
        }
        if (!json.isNull("approved")){
            approved = json.optBoolean("approved");
        }
        if (!json.isNull("live")){
            live = json.optBoolean("live");
        }
        if (!json.isNull("details")){
            JSONObject obj = json.optJSONObject("details");
            try {
                details = new SADetails(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!json.isNull("viewableImpressionUrl")){
            viewableImpressionUrl = json.optString("viewableImpressionUrl");
        }
        if (!json.isNull("trackingUrl")){
            trackingUrl = json.optString("trackingUrl");
        }
        if (!json.isNull("parentalGateClickUrl")){
            parentalGateClickUrl = json.optString("parentalGateClickUrl");
        }
        if (!json.isNull("creativeFormat")){
            String obj = json.optString("creativeFormat");
            if (obj != null) {
                if (obj.equals(SACreativeFormat.invalid.toString())){
                    creativeFormat = SACreativeFormat.invalid;
                }
                if (obj.equals(SACreativeFormat.image.toString())){
                    creativeFormat = SACreativeFormat.image;
                }
                if (obj.equals(SACreativeFormat.video.toString())){
                    creativeFormat = SACreativeFormat.video;
                }
                if (obj.equals(SACreativeFormat.rich.toString())){
                    creativeFormat = SACreativeFormat.rich;
                }
                if (obj.equals(SACreativeFormat.tag.toString())){
                    creativeFormat = SACreativeFormat.tag;
                }
            }
        }
    }

    @Override
    public JSONObject writeToJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("cpm", cpm);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("format", format);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("impression_url", impressionUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("click_url", clickUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("live", live);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("approved", approved);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (details != null) {
            try {
                json.put("details", details.writeToJson());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            json.put("creativeFormat", creativeFormat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("viewableImpressionUrl", viewableImpressionUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("parentalGateClickUrl", parentalGateClickUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("trackingUrl", trackingUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
