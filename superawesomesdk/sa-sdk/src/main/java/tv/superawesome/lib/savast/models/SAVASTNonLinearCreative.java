package tv.superawesome.lib.savast.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAVASTNonLinearCreative extends SAVASTCreative implements Parcelable {

    protected SAVASTNonLinearCreative(Parcel in) {
    }

    public static final Creator<SAVASTNonLinearCreative> CREATOR = new Creator<SAVASTNonLinearCreative>() {
        @Override
        public SAVASTNonLinearCreative createFromParcel(Parcel in) {
            return new SAVASTNonLinearCreative(in);
        }

        @Override
        public SAVASTNonLinearCreative[] newArray(int size) {
            return new SAVASTNonLinearCreative[size];
        }
    };

    @Override
    public void print() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
