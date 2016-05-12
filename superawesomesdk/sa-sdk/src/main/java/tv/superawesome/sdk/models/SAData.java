package tv.superawesome.sdk.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tv.superawesome.lib.sautils.JSONSerializable;
import tv.superawesome.lib.savast.models.SAVASTAd;
import tv.superawesome.lib.savast.models.SAVASTTracking;

/**
 * Created by gabriel.coman on 19/04/16.
 */
public class SAData implements Parcelable, JSONSerializable {

    public String adHtml;
    public String imagePath;
    public List<SAVASTAd> vastAds = new ArrayList<SAVASTAd>();

    public SAData(){
        /** standard constructor */
    }

    public SAData(JSONObject json) throws JSONException {
        readFromJson(json);
    }

    protected SAData(Parcel in) {
        adHtml = in.readString();
        imagePath = in.readString();
        vastAds = in.createTypedArrayList(SAVASTAd.CREATOR);
    }

    public static final Creator<SAData> CREATOR = new Creator<SAData>() {
        @Override
        public SAData createFromParcel(Parcel in) {
            return new SAData(in);
        }

        @Override
        public SAData[] newArray(int size) {
            return new SAData[size];
        }
    };

    public void print() {
        String printout = " \n\t\tDATA:\n";
        printout += "\t\t\t adHtml: " + adHtml + "\n";
        printout += "\t\t\t imagePath: " + imagePath + "\n";
        printout += "\t\t\t vastAds: " + vastAds.size() + "\n";
        Log.d("SuperAwesome", printout);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(adHtml);
        dest.writeString(imagePath);
        dest.writeTypedList(vastAds);
    }

    @Override
    public void readFromJson(JSONObject json) {
        if (!json.isNull("adHtml")) {
            adHtml = json.optString("adHtml");
        }
        if (!json.isNull("imagePath")) {
            imagePath = json.optString("imagePath");
        }
        if (!json.isNull("vastAds")){
            vastAds = new ArrayList<SAVASTAd>();
            JSONArray jsonArray = json.optJSONArray("vastAds");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.optJSONObject(i);
                try {
                    SAVASTAd savastAd = new SAVASTAd(obj);
                    vastAds.add(savastAd);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public JSONObject writeToJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("adHtml", adHtml);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("imagePath", imagePath);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (vastAds != null) {
            JSONArray vastAdsJsonArray = new JSONArray();
            for (SAVASTAd savastAd : vastAds) {
                vastAdsJsonArray.put(savastAd.writeToJson());
            }
            try {
                json.put("vastAds", vastAdsJsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return json;
    }
}
