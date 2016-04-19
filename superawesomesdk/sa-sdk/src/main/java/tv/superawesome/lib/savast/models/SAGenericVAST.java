package tv.superawesome.lib.savast.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAGenericVAST implements Parcelable {

    public SAGenericVAST() {
        /** generic constructor */
    }

    protected SAGenericVAST(Parcel in) {
    }

    public static final Creator<SAGenericVAST> CREATOR = new Creator<SAGenericVAST>() {
        @Override
        public SAGenericVAST createFromParcel(Parcel in) {
            return new SAGenericVAST(in);
        }

        @Override
        public SAGenericVAST[] newArray(int size) {
            return new SAGenericVAST[size];
        }
    };

    // function to print
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
