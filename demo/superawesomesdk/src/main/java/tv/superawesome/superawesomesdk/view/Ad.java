package tv.superawesome.superawesomesdk.view;

import android.media.Image;
import android.os.AsyncTask;

import org.json.JSONObject;

import tv.superawesome.superawesomesdk.model.Placement;

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

    public Ad(JSONObject ad) throws Exception {
        try {
            if (ad.has("error")) {
                this.error = true;
                this.error_message = ad.getString("error_message");
                return;
            }
            JSONObject creative = ad.getJSONObject("creative");
            JSONObject details = creative.getJSONObject("details");

            this.imageURL = details.getString("image");
            this.width = details.getInt("width");
            this.height = details.getInt("height");
            if (ad.getBoolean("test")) {
                this.clickURL = null;
            } else {
                this.clickURL = creative.getString("click_url");
            }
        } catch (Exception e) {
            throw new Exception("JSON argument not valid");
        }
    }

}
