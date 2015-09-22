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
            j.put("type", _request.type.toString());
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

    public void LogAdFetched(SAAd ad) {
        this.request.type = SAEventType.AdFetched;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogAdLoaded(SAAd ad) {
        this.request.type = SAEventType.AdLoaded;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogAdReady(SAAd ad) {
        this.request.type = SAEventType.AdReady;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogAdFailed(SAAd ad) {
        this.request.type = SAEventType.AdFailed;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogAdStart(SAAd ad) {
        this.request.type = SAEventType.AdStart;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogAdStop(SAAd ad) {
        this.request.type = SAEventType.AdStop;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogAdResume(SAAd ad) {
        this.request.type = SAEventType.AdResume;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogUserCanceledParentalGate(SAAd ad) {
        this.request.type = SAEventType.UserCanceledParentalGate;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogUserSuccessWithParentalGate(SAAd ad) {
        this.request.type = SAEventType.UserSuccessWithParentalGate;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }

    public void LogUserErrorWithParentalGate(SAAd ad) {
        this.request.type = SAEventType.UserErrorWithParentalGate;
        assignRequestFromResponse(ad);
        sendRequestWithEvent(this.request);
    }
}
