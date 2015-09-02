package tv.superawesome.superawesomesdk.models;

import android.media.Image;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by connor.leigh-smith on 24/06/15.
 */
public abstract class SAAd {
    private static final String TAG = "SA SDK - SAAd object";


    public enum Format {
        RICH_MEDIA, IMAGE_WITH_LINK, VIDEO, GAMEWALL
    }
    public Format format;

    public int width;
    public int height;
    public boolean isFallback;
    public Image image;
    public String imageURL;
    public String clickURL;
    public String url;

    protected String content;
    public boolean error = false;
    public String error_message;

    protected JSONObject creative;
    protected JSONObject details;

    public SAAd (JSONObject adResponse) throws JSONException {
        this.creative = adResponse.getJSONObject("creative");
        this.details = creative.getJSONObject("details");

        if (adResponse.has("error")) {
            this.error = true;
            this.error_message = adResponse.getString("error_message");
            return;
        }
    }

    public static SAAd generateAd(JSONObject adResponse) throws JSONException {

        Log.d(TAG, "adResponse: " + adResponse);

        switch (adResponse.getJSONObject("creative").getString("format")) {
            case "rich_media":
                return new SARichMediaAd(adResponse);
            case "video":
                return new SAVideoAd(adResponse);
            case "image_with_link":
                return new SAImageWithLinkAd(adResponse);
            case "gamewall":
                return new SAGamewallAd(adResponse);
            default:
                return null;
        }
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

}
