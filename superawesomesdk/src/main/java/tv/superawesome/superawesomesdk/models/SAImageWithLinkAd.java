package tv.superawesome.superawesomesdk.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by connor.leigh-smith on 24/07/15.
 */
public class SAImageWithLinkAd extends SAAd {

    public SAImageWithLinkAd(JSONObject adResponse) throws JSONException {
        super(adResponse);
        this.format = Format.IMAGE_WITH_LINK;
        this.width = this.details.getInt("width");
        this.height = this.details.getInt("height");
        this.imageURL = this.details.getString("image");
        this.isFallback = adResponse.getBoolean("is_fallback");
        if (adResponse.has("test") && adResponse.getBoolean("test")) {
            this.clickURL = null;
        } else {
            this.clickURL = creative.getString("click_url");
        }
        this.setContent(String.format("<div><a href=\"%s\"><img src=\"%s\" /></a></div>", this.clickURL, this.imageURL));
    }
}
