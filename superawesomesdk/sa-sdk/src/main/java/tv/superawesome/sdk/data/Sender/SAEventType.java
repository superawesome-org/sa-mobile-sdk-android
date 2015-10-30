/**
 * @class: SAEventType.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 29/10/2015
 *
 */
package tv.superawesome.sdk.data.Sender;

/**
 * Enum that holds event type variables
 */
public enum SAEventType {

    NoAd {
        @Override
        public String toString() {
            return "NoAd";
        }
    },
    viewable_impression {
        @Override
        public String toString() {
            return "viewable_impression";
        }
    },
    AdFailedToView {
        @Override
        public String toString() {
            return "AdFailedToView";
        }
    },
    AdRate {
        @Override
        public String toString() {
            return "AdRate";
        }
    },
    AdPGCancel {
        @Override
        public String toString() {
            return "AdPGCancel";
        }
    },
    AdPGSuccess {
        @Override
        public String toString() {
            return "AdPGSuccess";
        }
    },
    AdPGError {
        @Override
        public String toString() {
            return "AdPGError";
        }
    }
}
