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
    public int detailValue;

    // constructor that initialises data to some init value
    public SAEventRequest() {
        this.placementID = null;
        this.creativeId = null;
        this.lineItemId = null;
        this.type = SAEventType.NoAd;
        this.detailValue = -1;
    }
}
