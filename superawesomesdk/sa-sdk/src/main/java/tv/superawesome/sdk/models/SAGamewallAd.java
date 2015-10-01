package tv.superawesome.sdk.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by connor.leigh-smith on 02/09/15.
 */
public class SAGamewallAd extends SAAd {

    public String scheme;
    public String appId;
    public String platform;

    public SAGamewallAd(JSONObject adResponse) throws JSONException {
        super(adResponse);
        this.format = Format.GAMEWALL;
        this.appId = this.details.getString("app_id");
        this.platform = this.details.getString("platform");
    }
}
