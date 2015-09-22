package tv.superawesome.sdk.models;
//package tv.superawesome.sdk.events.SAAdEventName;

import tv.superawesome.sdk.events.SAEventType;

/**
 * Created by gabriel.coman on 21/09/15.
 */
public class SAEventRequest {
    // public members
    public String placementID;
    public String creativeId;
    public String lineItemId;
    public SAEventType type;

    public SAEventRequest() {
        // do nothing
    }
}
