package tv.superawesome.superawesomesdk.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by connor.leigh-smith on 24/07/15.
 */
public class SARichMediaAd extends SAAd {

    public SARichMediaAd(JSONObject adResponse) throws JSONException {
        super(adResponse);
        this.format = Format.RICH_MEDIA;
        this.url = this.details.getString("url");
        this.width = this.details.getInt("width");
        this.height = this.details.getInt("height");
        this.isFallback = adResponse.getBoolean("is_fallback");

    }
}
