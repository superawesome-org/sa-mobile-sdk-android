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
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.BitSet;

import tv.superawesome.lib.sautils.JSONSerializable;

/**
 * The SADetails class contains fine grained information about the creative
 * of an ad (such as width, iamge, vast, tag, etc)
 * Depending on the format of the creative, some fields are essential,
 * and some are optional
 *
 * This dependency is regulated by SAValidator.h
 */
public class SADetails implements Parcelable, JSONSerializable{

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

    public SADetails(JSONObject json) throws JSONException {
        readFromJson(json);
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

    @Override
    public void readFromJson(JSONObject json) {
        if (!json.isNull("width")){
            width = json.optInt("width");
        }
        if (!json.isNull("height")){
            height = json.optInt("height");
        }
        if (!json.isNull("image")){
            image = json.optString("image");
        }
        if (!json.isNull("value")){
            value = json.optInt("value");
        }
        if (!json.isNull("name")){
            name = json.optString("name");
        }
        if (!json.isNull("video")){
            video = json.optString("video");
        }
        if (!json.isNull("bitrate")){
            bitrate = json.optInt("bitrate");
        }
        if (!json.isNull("duration")){
            duration = json.optInt("duration");
        }
        if (!json.isNull("vast")){
            vast = json.optString("vast");
        }
        if (!json.isNull("tag")){
            tag = json.optString("tag");
        }
        if (!json.isNull("zipFile")){
            zipFile = json.optString("zipFile");
        }
        if (!json.isNull("url")){
            url = json.optString("url");
        }
        if (!json.isNull("placementFormat")){
            placementFormat = json.optString("placementFormat");
        }
        if (!json.isNull("data")){
            JSONObject obj = json.optJSONObject("data");
            try {
                data = new SAData(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public JSONObject writeToJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("width", width);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("height", height);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("image", image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("value", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("video", video);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("bitrate", bitrate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("duration", duration);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("vast", vast);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("tag", tag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("zipFile", zipFile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("url", url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("placementFormat", placementFormat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (data != null) {
            try {
                json.put("data", data.writeToJson());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json;
    }
}
