package tv.superawesome.lib.savast.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAVASTCompanionAdsCreative extends SAVASTCreative implements Parcelable{

    protected SAVASTCompanionAdsCreative(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SAVASTCompanionAdsCreative> CREATOR = new Creator<SAVASTCompanionAdsCreative>() {
        @Override
        public SAVASTCompanionAdsCreative createFromParcel(Parcel in) {
            return new SAVASTCompanionAdsCreative(in);
        }

        @Override
        public SAVASTCompanionAdsCreative[] newArray(int size) {
            return new SAVASTCompanionAdsCreative[size];
        }
    };

    @Override
    public void print() {

    }
}
