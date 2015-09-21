package tv.superawesome.sdk.events;

/**
 * Created by gabriel.coman on 21/09/15.
 */

public enum SAAdType {

    SAAdTypeBanner {
        @Override
        public String toString() {
            return "SAAdTypeBanner";
        }
    },
    SAAdTypeInterstitial {
        @Override
        public String toString() {
            return "SAAdTypeInterstitial";
        }
    },
    SAAdTypeVideo {
        @Override
        public String toString() {
            return "SAAdTypeVideo";
        }
    }

}