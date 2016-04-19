package tv.superawesome.lib.savast.models;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAVASTCreative extends SAGenericVAST implements Parcelable {
    public SAVASTCreativeType type;

    public SAVASTCreative() {
        /** normal constructor */
    }

    protected SAVASTCreative(Parcel in) {
        type = in.readParcelable(SAVASTCreativeType.class.getClassLoader());
    }

    public static final Creator<SAVASTCreative> CREATOR = new Creator<SAVASTCreative>() {
        @Override
        public SAVASTCreative createFromParcel(Parcel in) {
            return new SAVASTCreative(in);
        }

        @Override
        public SAVASTCreative[] newArray(int size) {
            return new SAVASTCreative[size];
        }
    };

    @Override
    public void print() {

    }

    public void sumCreative(SAVASTCreative creative) {
        // do nothing
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(type, flags);
    }
}
