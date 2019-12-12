package tv.superawesome.lib.sagdprisminorsdk.minor.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

public class GetIsMinorModel extends SABaseObject implements Parcelable {

    public String country;
    public int consentAgeForCountry;
    public int age;
    public boolean isMinor;

    protected GetIsMinorModel(Parcel in) {
        country = in.readString();
        consentAgeForCountry = in.readInt();
        age = in.readInt();
        isMinor = in.readByte() != 0;
    }

    public GetIsMinorModel() {
    }

    public GetIsMinorModel(String json) {
        JSONObject jsonObject = SAJsonParser.newObject(json);
        readFromJson(jsonObject);
    }

    public static final Creator<GetIsMinorModel> CREATOR = new Creator<GetIsMinorModel>() {
        @Override
        public GetIsMinorModel createFromParcel(Parcel in) {
            return new GetIsMinorModel(in);
        }

        @Override
        public GetIsMinorModel[] newArray(int size) {
            return new GetIsMinorModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(country);
        dest.writeInt(consentAgeForCountry);
        dest.writeInt(age);
        dest.writeByte((byte) (isMinor ? 1 : 0));
    }

    @Override
    public void readFromJson(JSONObject jsonObject) {
        country = SAJsonParser.getString(jsonObject, "country");
        consentAgeForCountry = SAJsonParser.getInt(jsonObject, "consentAgeForCountry");
        age = SAJsonParser.getInt(jsonObject, "age");
        isMinor = SAJsonParser.getBoolean(jsonObject, "isMinor");
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[]{
                "country", country,
                "consentAgeForCountry", consentAgeForCountry,
                "age", age,
                "isMinor", isMinor
        });
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getConsentAgeForCountry() {
        return consentAgeForCountry;
    }

    public void setConsentAgeForCountry(int consentAgeForCountry) {
        this.consentAgeForCountry = consentAgeForCountry;
    }

    public boolean isMinor() {
        return isMinor;
    }

    public void setMinor(boolean minor) {
        isMinor = minor;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}