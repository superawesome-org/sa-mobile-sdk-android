package tv.superawesome.lib.savast.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAVASTImpression extends SAGenericVAST implements Parcelable {
    public boolean isSent = false;
    public String URL;

    public SAVASTImpression() {
        super();
    }

    protected SAVASTImpression(Parcel in) {
        isSent = in.readByte() != 0;
        URL = in.readString();
    }

    public static final Creator<SAVASTImpression> CREATOR = new Creator<SAVASTImpression>() {
        @Override
        public SAVASTImpression createFromParcel(Parcel in) {
            return new SAVASTImpression(in);
        }

        @Override
        public SAVASTImpression[] newArray(int size) {
            return new SAVASTImpression[size];
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
        dest.writeByte((byte) (isSent ? 1 : 0));
        dest.writeString(URL);
    }
}
