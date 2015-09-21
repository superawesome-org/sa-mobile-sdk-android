package tv.superawesome.sdk.events;

/**
 * Created by gabriel.coman on 21/09/15.
 */

public enum SAAdEventName {

    SAEventAdFetched {
        @Override
        public String toString() { return "SAEventAdFetched"; }
    },
    SAEventAdLoaded {
        @Override
        public String toString() { return "SAEventAdLoaded"; }
    },
    SAEventAdReady {
        @Override
        public String toString() { return "SAEventAdReady"; }
    },
    SAEventAdFailed {
        @Override
        public String toString() { return "SAEventAdFailed"; }
    },
    SAEventAdStart {
        @Override
        public String toString() { return "SAEventAdStart"; }
    },
    SAEventAdStop {
        @Override
        public String toString() { return "SAEventAdStop"; }
    },
    SAEventAdResume {
        @Override
        public String toString() { return "SAEventAdResume"; }
    },
    SAEventUserCanceledParentalGate {
        @Override
        public String toString() { return "SAEventUserCanceledParentalGate"; }
    },
    SAEventUserSuccessWithParentalGate {
        @Override
        public String toString() { return "SAEventUserSuccessWithParentalGate"; }
    },
    SAEventUserErrorWithParentalGate {
        @Override
        public String toString() { return "SAEventUserErrorWithParentalGate"; }
    },
    SAEventUserClickedOnAd {
        @Override
        public String toString() { return "SAEventUserClickedOnAd"; }
    }
}