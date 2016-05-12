package tv.superawesome.lib.savast.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tv.superawesome.lib.sautils.JSONSerializable;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAVASTAd implements Parcelable, JSONSerializable {

    public SAVASTAdType type;
    public String id;
    public String sequence;
    public boolean isImpressionSent;
    public List<String> errors;
    public List<String> impressions;
    public List<SAVASTCreative> creatives;

    public SAVASTAd(){

    }

    public SAVASTAd(JSONObject json)  throws JSONException {
        readFromJson(json);
    }

    protected SAVASTAd(Parcel in) {
        type = in.readParcelable(SAVASTAdType.class.getClassLoader());
        id = in.readString();
        sequence = in.readString();
        errors = in.createStringArrayList();
        impressions = in.createStringArrayList();
        isImpressionSent = in.readByte() != 0;
        creatives = in.createTypedArrayList(SAVASTCreative.CREATOR);
    }

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

    public void print() {
        String printout = " \n";
        printout += type.toString() + " Ad(" + id + ")" + "\n";
        printout += "Sequence: " + sequence + "\n";
        printout += "Errors[" + errors.size() + "]" + "\n";
        printout += "Impressions[" + impressions.size() + "]" + "\n";
        printout += "Creatives[" + creatives.size() + "]" + "\n";
        Log.d("SuperAwesome", printout);

        for (Iterator<SAVASTCreative> i = creatives.iterator(); i.hasNext(); ) {
            SAVASTCreative item = i.next();
            item.print();
        }
    }

    public void sumAd(SAVASTAd ad){
        this.id = ad.id;
        this.sequence = ad.sequence;

        // add errors
        for (Iterator<String> i = ad.errors.iterator(); i.hasNext(); ){
            this.errors.add(i.next());
        }

        // add impressions
        for (String impression : ad.impressions) {
            this.impressions.add(impression);
        }

        // add creatives
        for (SAVASTCreative creative1 : this.creatives) {
            for (SAVASTCreative creative2 : ad.creatives) {
                creative1.sumCreative(creative2);
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(type, flags);
        dest.writeString(id);
        dest.writeString(sequence);
        dest.writeStringList(errors);
        dest.writeStringList(impressions);
        dest.writeByte((byte) (isImpressionSent ? 1 : 0));
        dest.writeTypedList(creatives);
    }

    @Override
    public void readFromJson(JSONObject json) {
        if (!json.isNull("id")){
            id = json.optString("id");
        }
        if (!json.isNull("sequence")){
            sequence = json.optString("sequence");
        }
        if (!json.isNull("isImpressionSent")){
            isImpressionSent = json.optBoolean("isImpressionSent");
        }
        if (!json.isNull("type")){
            String obj = json.optString("type");
            if (obj != null) {
                if (obj.equals(SAVASTAdType.Invalid.toString())) {
                    type = SAVASTAdType.Invalid;
                }
                if (obj.equals(SAVASTAdType.InLine.toString())){
                    type = SAVASTAdType.InLine;
                }
                if (obj.equals(SAVASTAdType.Wrapper.toString())){
                    type = SAVASTAdType.Wrapper;
                }
            }
        }
        if (!json.isNull("errors")){
            errors = new ArrayList<String>();
            JSONArray jsonArray = json.optJSONArray("errors");
            for (int i = 0; i < jsonArray.length(); i++) {
                String obj = jsonArray.optString(i);
                if (obj != null) {
                    errors.add(obj);
                }
            }
        }
        if (!json.isNull("impressions")){
            impressions = new ArrayList<String>();
            JSONArray jsonArray = json.optJSONArray("impressions");
            for (int i = 0; i < jsonArray.length(); i++) {
                String obj = jsonArray.optString(i);
                if (obj != null) {
                    impressions.add(obj);
                }
            }
        }
        if (!json.isNull("creatives")) {
            creatives = new ArrayList<SAVASTCreative>();
            JSONArray jsonArray = json.optJSONArray("creatives");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.optJSONObject(i);
                try {
                    SAVASTCreative creative = new SAVASTCreative(obj);
                    creatives.add(creative);
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
            json.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("sequence", sequence);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("isImpressionSent", isImpressionSent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (errors != null) {
            JSONArray errorsJsonArray = new JSONArray();
            for (String error : errors) {
                errorsJsonArray.put(error);
            }
            try {
                json.put("errors", errorsJsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (impressions != null) {
            JSONArray impressionsJsonArray = new JSONArray();
            for (String impression : impressions) {
                impressionsJsonArray.put(impression);
            }
            try {
                json.put("impressions", impressionsJsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (creatives != null) {
            JSONArray creativesJsonArray = new JSONArray();
            for (SAVASTCreative creative : creatives) {
                creativesJsonArray.put(creative.writeToJson());
            }
            try {
                json.put("creatives", creativesJsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return json;
    }
}
