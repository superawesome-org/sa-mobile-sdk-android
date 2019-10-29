/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.samodelspace.saad;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * An enum that defines the number of formats an ad can be in
 *  - invalid:  defined by the SDK in case of some error
 *  - image:    the creative is a simple banner image
 *  - video:    the creative is a video that must be streamed
 *  - rich:     a mini-HTML page with user interaction
 *  - tag:      a rich-media (usually) served as a JS file via a 3rd party service
 *  - appwall:  a pop-up with links to games
 */
public enum SACreativeFormat implements Parcelable {

    invalid(0) {
        @Override
        public String toString() {
            return "invalid";
        }
    },
    image(1) {
        @Override
        public String toString() {
            return "image_with_link";
        }
    },
    video(2) {
        @Override
        public String toString() {
            return "video";
        }
    },
    rich(3){
        @Override
        public String toString() {
            return "rich_media";
        }
    },
    tag(4) {
        @Override
        public String toString() {
            return "tag";
        }
    },
    appwall(5) {
        @Override
        public String toString() {
            return "appwall";
        }
    };

    private final int value;

    /**
     * Constructor that makes it possible for the enum to be initiated from an integer value
     *
     * @param i the integer value representing an enum
     */
    SACreativeFormat(int i) {
        this.value = i;
    }

    /**
     * Factory method that creates this enum from an integer value
     *
     * @param format the integer value in question
     * @return       an enum instance
     */
    public static SACreativeFormat fromValue (int format) {
        if (format == 5) return appwall;
        if (format == 4) return tag;
        if (format == 3) return rich;
        if (format == 2) return video;
        if (format == 1) return image;
        return invalid;
    }

    /**
     * Factory method that creates this enum from a string value
     *
     * @param value the string value in question
     * @return      an enum instance
     */
    public static SACreativeFormat fromString (String value) {
        if (value == null) return invalid;
        if (value.equals("image_with_link")) return image;
        if (value.equals("video")) return video;
        if (value.contains("rich_media")) return rich;
        if (value.contains("tag")) return tag;
        if (value.contains("gamewall")) return appwall;
        if (value.contains("appwall")) return appwall;
        return invalid;
    }

    /**
     * Method needed for Parcelable implementation
     *
     * @return always 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Method needed for Parcelable implementation
     *
     * @param dest  destination parcel
     * @param flags special flags
     */
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    /**
     * Method needed for Parcelable implementation
     */
    public static final Creator<SACreativeFormat> CREATOR = new Creator<SACreativeFormat>() {
        @Override
        public SACreativeFormat createFromParcel(final Parcel source) {
            return SACreativeFormat.values()[source.readInt()];
        }

        @Override
        public SACreativeFormat[] newArray(final int size) {
            return new SACreativeFormat[size];
        }
    };
}
