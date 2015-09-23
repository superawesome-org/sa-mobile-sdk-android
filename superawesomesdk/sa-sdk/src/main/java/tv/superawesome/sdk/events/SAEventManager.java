package tv.superawesome.sdk.events;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.UrlLoaderListener;
import tv.superawesome.sdk.UrlPoster;
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
            j.put("creative", Integer.parseInt(_request.creativeId));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            j.put("placement", Integer.parseInt(_request.placementID));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            j.put("line_item", Integer.parseInt(_request.lineItemId));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (_request.type != SAEventType.NoAd) {
            try {
                j.put("type", _request.type.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (_request.detailValue > 0){
            try {
                JSONObject mj = new JSONObject();
                mj.put("value", _request.detailValue);
                j.put("details", mj);
            } catch (JSONException e){
                e.printStackTrace();;
            }
        }

        return j;
    }

    private void assignRequestFromResponse(SAAd ad) {
        request.creativeId = ad.creativeId;
        request.lineItemId = ad.lineItemId;
        request.placementID = ad.placementId;
        request.detailValue = -1;   // just reset the detail value here
    }

    private void sendRequestWithEvent(SAEventRequest request) {
        String finalUrl = SuperAwesome.getBaseUrl() + "/event";

        final JSONObject obj = TransformRequestIntoJSON(request);

        UrlPoster poster = new UrlPoster();
        poster.setPOSTParams(obj);
        poster.setListener(new UrlLoaderListener() {
            @Override
            public void onBeginLoad(String url) {
                // do this
                System.out.println("Begin send of event data to " + url + " with data " + obj);
            }

            @Override
            public void onError(String message) {
                // do this
            }

            @Override
            public void onLoaded(String content) {
                // do this
                System.out.println("Sent event data " + content);
            }
        });
        poster.execute(finalUrl);
    }

    private void sendClickWithEvent(SAEventRequest request) {
        String finalUrl = SuperAwesome.getBaseUrl() + "/click";

        JSONObject obj = TransformRequestIntoJSON(request);

        UrlPoster poster = new UrlPoster();
        poster.setPOSTParams(obj);
        poster.setListener(new UrlLoaderListener() {
            @Override
            public void onBeginLoad(String url) {
                // do this
                System.out.println("Begin send of event data");
            }

            @Override
            public void onError(String message) {
                // do this
            }

            @Override
            public void onLoaded(String content) {
                // do this
                System.out.println("Sent click data " + content);
            }
        });
        poster.execute(finalUrl);
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

    public void LogClick(SAAd ad) {
        this.request.type = SAEventType.NoAd;
        assignRequestFromResponse(ad);
        sendClickWithEvent(this.request);
    }

    public void LogRating(SAAd ad, int rating) {
        assignRequestFromResponse(ad);
        this.request.type = SAEventType.AdRate;
        this.request.detailValue = rating;
        sendRequestWithEvent(this.request);
    }
}
