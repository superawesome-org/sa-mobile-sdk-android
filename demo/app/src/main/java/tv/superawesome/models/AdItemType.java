package tv.superawesome.models;

/**
 * Created by gabriel.coman on 07/01/16.
 */
public enum AdItemType {
    banner_item {
        @Override
        public String toString() {
            return "banner_item";
        }
    },
    fullscreen_video_item {
        @Override
        public String toString() {
            return "video_item";
        }
    },
    interstitial_item {
        @Override
        public String toString() {
            return "interstitial_item";
        }
    }
}
