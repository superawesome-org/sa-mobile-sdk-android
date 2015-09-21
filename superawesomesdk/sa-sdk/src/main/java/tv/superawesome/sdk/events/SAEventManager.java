package tv.superawesome.sdk.events;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.models.SAEventRequest;

/**
 * Created by gabriel.coman on 21/09/15.
 */
public class SAEventManager {
    // singleton variable
    protected static SAEventManager eventManager = new SAEventManager();

    // other singleton variables
    protected SAEventRequest request;

    protected SAEventManager(){
        // do nothing
        request = new SAEventRequest();
        request.evtype = SAEventType.SAEventTypeImpression;
    }

    // singleton function
    public static SAEventManager getIntance(){
        return eventManager;
    }

    // create a JSON from a request
    private JSONObject TransformRequestIntoJSON(SAEventRequest _request) {
        JSONObject j = new JSONObject();
        try {
            j.put("creative", _request.creativeId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            j.put("placement", _request.placementID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            j.put("line_item", _request.lineItemId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            j.put("evtype", _request.evtype.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            j.put("adtype", _request.adtype.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            j.put("eventname", _request.evtname.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return j;
    }

    private void assignRequestFromResponse(SAAd ad) {
        request.creativeId = ad.creativeId;
        request.lineItemId = ad.lineItemId;
        request.placementID = ad.placementId;
    }

    private void sendRequestWithEvent(SAEventRequest request) {
        JSONObject obj = TransformRequestIntoJSON(request);

        // perform data sending function to server
        System.out.println("Writing " + obj + " to event server");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // The main functions that actually send the event
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void LogSAEventAdFetched(SAAd ad) {
        this.request.evtname = SAAdEventName.SAEventAdFetched;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogSAEventAdLoaded(SAAd ad) {
        this.request.evtname = SAAdEventName.SAEventAdLoaded;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogSAEventAdReady(SAAd ad) {
        this.request.evtname = SAAdEventName.SAEventAdReady;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogSAEventAdFailed(SAAd ad) {
        this.request.evtname = SAAdEventName.SAEventAdFailed;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogSAEventAdStart(SAAd ad) {
        this.request.evtname = SAAdEventName.SAEventAdStart;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogSAEventAdStop(SAAd ad) {
        this.request.evtname = SAAdEventName.SAEventAdStop;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogSAEventAdResume(SAAd ad) {
        this.request.evtname = SAAdEventName.SAEventAdResume;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogSAEventUserCanceledParentalGate(SAAd ad) {
        this.request.evtname = SAAdEventName.SAEventUserCanceledParentalGate;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogSAEventUserSuccessWithParentalGate(SAAd ad) {
        this.request.evtname = SAAdEventName.SAEventUserSuccessWithParentalGate;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogSAEventUserErrorWithParentalGate(SAAd ad) {
        this.request.evtname = SAAdEventName.SAEventUserErrorWithParentalGate;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogSAEventUserClickedOnAd(SAAd ad) {
        this.request.evtname = SAAdEventName.SAEventUserClickedOnAd;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }
}
