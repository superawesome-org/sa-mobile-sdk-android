package tv.superawesome.superawesomesdk.model;

import android.media.Image;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.superawesomesdk.UrlLoader;

/**
 * Created by connor.leigh-smith on 24/06/15.
 */
public class Ad {
    private static final String TAG = "SA SDK - Ad object";
    public String imageURL;
    public String clickURL;
    public int width;
    public int height;
    public Image image;

    public boolean error = false;
    public String error_message;

    public enum Format {
        RICH_MEDIA, IMAGE_WITH_LINK
    }
    public Format format;

    private JSONObject creative;
    private JSONObject details;

    private String content;
    public String richMediaUrl;
    public boolean isFallback;


    public Ad(JSONObject ad) throws Exception {
        try {
            if (ad.has("error")) {
                this.error = true;
                this.error_message = ad.getString("error_message");
                return;
            }

            this.creative = ad.getJSONObject("creative");
            this.details = creative.getJSONObject("details");
            this.width = this.details.getInt("width");
            this.height = this.details.getInt("height");
            this.isFallback = ad.getBoolean("is_fallback");
            switch (this.creative.getString("format")) {
                case "rich_media":
                    this.format = Format.RICH_MEDIA;
                    this.richMediaUrl = this.details.getString("url");
                    break;
                case "image_with_link":
                    this.format = Format.IMAGE_WITH_LINK;
                    this.imageURL = this.details.getString("image");
                    if (ad.has("test") && ad.getBoolean("test")) {
                        this.clickURL = null;
                    } else {
                        this.clickURL = creative.getString("click_url");
                    }
                    this.setContent(String.format("<div style=\"background-color: red;\"><a href=\"%s\"><img src=\"%s\" /></a></div>", this.clickURL, this.imageURL));
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            throw e;
        }
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return this.content;
    }

}
