package tv.superawesome.lib.savast.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAVASTMediaFile extends SAGenericVAST implements Parcelable {
    public String width;
    public String height;
    public String URL;
    public String type;

    public SAVASTMediaFile() {

    }

    protected SAVASTMediaFile(Parcel in) {
        width = in.readString();
        height = in.readString();
        URL = in.readString();
        type = in.readString();
    }

    public static final Creator<SAVASTMediaFile> CREATOR = new Creator<SAVASTMediaFile>() {
        @Override
        public SAVASTMediaFile createFromParcel(Parcel in) {
            return new SAVASTMediaFile(in);
        }

        @Override
        public SAVASTMediaFile[] newArray(int size) {
            return new SAVASTMediaFile[size];
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
        dest.writeString(width);
        dest.writeString(height);
        dest.writeString(URL);
        dest.writeString(type);
    }
}
