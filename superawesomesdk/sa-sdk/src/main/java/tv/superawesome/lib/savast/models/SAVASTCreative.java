package tv.superawesome.lib.savast.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

import tv.superawesome.lib.sautils.SAUtils;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAVASTCreative implements Parcelable{

    public SAVASTCreativeType type;
    public String id;
    public String sequence;
    public String Duration;
    public String ClickThrough;
    public String playableMediaURL;
    public String playableDiskURL;
    public boolean isOnDisk = false;
    public List<SAVASTMediaFile> MediaFiles;
    public List<SAVASTTracking> TrackingEvents;
    public List<String> ClickTracking;
    public List<String> CustomClicks;

    public SAVASTCreative() {

    }

    protected SAVASTCreative(Parcel in) {
        type = in.readParcelable(SAVASTCreativeType.class.getClassLoader());
        id = in.readString();
        sequence = in.readString();
        Duration = in.readString();
        ClickThrough = in.readString();
        playableMediaURL = in.readString();
        playableDiskURL = in.readString();
        isOnDisk = in.readByte() != 0;
        MediaFiles = in.createTypedArrayList(SAVASTMediaFile.CREATOR);
        TrackingEvents = in.createTypedArrayList(SAVASTTracking.CREATOR);
        ClickTracking = in.createStringArrayList();
        CustomClicks = in.createStringArrayList();
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

    public void print() {
        String printout = " \n";
        printout += "\tCreative(" + id + ")" + "\n";
        printout += "\tsequence: " + sequence + "\n";
        printout += "\tDuration: " + Duration + "\n";
        printout += "\tplayableMediaURL: " + playableMediaURL + "\n";
        printout += "\tplayableDiskURL: " + isOnDisk + " " + playableDiskURL + "\n";
        printout += "\tClickThrough: " + ClickThrough + "\n";
        printout += "\tMediaFiles[" + MediaFiles.size() + "]" + "\n";
        printout += "\tTrackingEvents[" + TrackingEvents.size() + "]" + "\n";
        printout += "\tClickTracking[" + ClickTracking.size() + "]" + "\n";
        printout += "\tCustomClicks[" + CustomClicks.size() + "]" + "\n";
        Log.d("SuperAwesome", printout);
    }

    public void sumCreative(SAVASTCreative creative) {
        // call super

        this.id = creative.id;
        this.sequence = creative.sequence;
        this.Duration = creative.Duration;

        if (SAUtils.isValidURL(ClickThrough)) {
            /** this.ClickThrough = this.ClickThrough; */
        }
        if (SAUtils.isValidURL(creative.ClickThrough)) {
            this.ClickThrough = creative.ClickThrough;
        }
        if (SAUtils.isValidURL(playableMediaURL)) {
            /** this.playableMediaURL = this.playableMediaURL; */
        }
        if (SAUtils.isValidURL(creative.playableMediaURL)) {
            this.playableMediaURL = creative.playableMediaURL;
        }

        /** now add all other things */
        for (Iterator<SAVASTMediaFile> i = creative.MediaFiles.iterator(); i.hasNext(); ) {
            this.MediaFiles.add(i.next());
        }
        for (Iterator<SAVASTTracking> i = creative.TrackingEvents.iterator(); i.hasNext(); ) {
            this.TrackingEvents.add(i.next());
        }
        for (Iterator<String> i = creative.ClickTracking.iterator(); i.hasNext(); ) {
            this.ClickTracking.add(i.next());
        }
        for (Iterator<String> i = creative.CustomClicks.iterator(); i.hasNext(); ) {
            this.CustomClicks.add(i.next());
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
        dest.writeString(Duration);
        dest.writeString(ClickThrough);
        dest.writeString(playableMediaURL);
        dest.writeString(playableDiskURL);
        dest.writeByte((byte) (isOnDisk ? 1 : 0));
        dest.writeTypedList(MediaFiles);
        dest.writeTypedList(TrackingEvents);
        dest.writeStringList(ClickTracking);
        dest.writeStringList(CustomClicks);
    }
}
