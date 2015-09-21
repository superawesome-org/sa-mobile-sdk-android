package tv.superawesome.sdk.events;

/**
 * Created by gabriel.coman on 21/09/15.
 */

public enum SAEventType{

    SAEventTypeImpression {
        @Override
        public String toString() { return "SAEventTypeImpression"; }
    },
    SAEventTypeEvent {
        @Override
        public String toString() { return "SAEventTypeEvent"; }
    }

}