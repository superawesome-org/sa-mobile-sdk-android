package tv.superawesome.superawesomesdk.view;

import org.json.JSONObject;

import java.net.URL;

/**
 * Created by connor.leigh-smith on 24/06/15.
 */
public interface PlacementView {
    void onAdResponse(JSONObject response);
    void onAdLoaded(String content);
    void onAdBeginLoad();
    void onAdBeginLoad(URL url);
    void onAdError();
}
