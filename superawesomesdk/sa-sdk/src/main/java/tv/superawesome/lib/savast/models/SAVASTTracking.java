package tv.superawesome.lib.savast.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAVASTTracking extends SAGenericVAST implements Parcelable {

    public String event;
    public String URL;

    public SAVASTTracking() {

    }

    protected SAVASTTracking(Parcel in) {
        event = in.readString();
        URL = in.readString();
    }

    public static final Creator<SAVASTTracking> CREATOR = new Creator<SAVASTTracking>() {
        @Override
        public SAVASTTracking createFromParcel(Parcel in) {
            return new SAVASTTracking(in);
        }

        @Override
        public SAVASTTracking[] newArray(int size) {
            return new SAVASTTracking[size];
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
        dest.writeString(event);
        dest.writeString(URL);
    }
}
