package tv.superawesome.superawesomesdk.view;

import android.media.Image;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import tv.superawesome.superawesomesdk.AdLoader;
import tv.superawesome.superawesomesdk.RichMediaLoader;

/**
 * Created by connor.leigh-smith on 24/06/15.
 */
public class Ad {
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

    public String content;
    public String richMediaUrl;


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
            switch (this.creative.getString("format")) {
                case "rich_media":
                    this.format = Format.RICH_MEDIA;
                    this.richMediaUrl = this.details.getString("url");
                    break;
                case "image_with_link":
                default:
                    this.format = Format.IMAGE_WITH_LINK;
                    this.imageURL = this.details.getString("image");
                    if (ad.getBoolean("test")) {
                        this.clickURL = null;
                    } else {
                        this.clickURL = creative.getString("click_url");
                    }
                    break;
            }

        } catch (Exception e) {
            throw e;
        }
    }

    public void retrieveRichMediaContent(AdLoaderListener listener) {

        RichMediaLoader adLoader = new RichMediaLoader(listener);
        try {
            adLoader.execute(this.details.getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
