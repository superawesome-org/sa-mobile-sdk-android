package tv.superawesome.sdk.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by connor.leigh-smith on 24/07/15.
 */
public class SAVideoAd extends SAAd {

    public SAVideoAd(JSONObject adResponse) throws JSONException {
        super(adResponse);
        this.format = Format.VIDEO;
        this.url = this.details.getString("vast");
        // this is always set to false for the moment, because somehow the
        // server response for SAVideoAd does not return a fallback field
        this.isFallback = false;
    }
}
