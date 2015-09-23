package tv.superawesome.sdk.events;

/**
 * Created by gabriel.coman on 21/09/15.
 */

public enum SAEventType {

    NoAd {
        @Override
        public String toString(){ return "NoAd"; }
    },
    AdFetched {
        @Override
        public String toString() { return "AdFetched"; }
    },
    AdLoaded {
        @Override
        public String toString() { return "AdLoaded"; }
    },
    AdReady {
        @Override
        public String toString() { return "AdReady"; }
    },
    AdFailed {
        @Override
        public String toString() { return "AdFailed"; }
    },
    AdStart {
        @Override
        public String toString() { return "AdStart"; }
    },
    AdStop {
        @Override
        public String toString() { return "AdStop"; }
    },
    AdResume {
        @Override
        public String toString() { return "AdResume"; }
    },
    AdRate {
        @Override
        public String toString() { return "AdRate"; }
    },
    UserCanceledParentalGate {
        @Override
        public String toString() { return "UserCanceledParentalGate"; }
    },
    UserSuccessWithParentalGate {
        @Override
        public String toString() { return "UserSuccessWithParentalGate"; }
    },
    UserErrorWithParentalGate {
        @Override
        public String toString() { return "UserErrorWithParentalGate"; }
    }
}