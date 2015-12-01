package tv.superawesome.sdk;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.sdk.models.SAAd;

/**
 * Created by connor.leigh-smith on 29/06/15.
 */
public class AdLoader {

    private static final String TAG = "SA SDK - AdLoader";
    private AdLoaderListener listener;
    private UrlLoader urlLoaderAd;
    private UrlLoader urlLoaderRichMedia;
    private SAAd superAwesomeAd;
    public String placementId;

    public AdLoader(AdLoaderListener listener, UrlLoader urlLoaderAd, UrlLoader urlLoaderRichMedia) {
        this.listener = listener;
        this.urlLoaderAd = urlLoaderAd;
        this.urlLoaderRichMedia = urlLoaderRichMedia;
    }

    public void loadAd(String url) {

        this.urlLoaderAd.setListener(new UrlLoaderListener() {
            @Override
            public void onBeginLoad(String url) {
                Log.d(TAG, "Beginning to load superAwesomeAd; URL: " + url);
            }

            @Override
            public void onError(String message) {
                Log.d(TAG, "Error:" + message);
                listener.onError(message);
            }

            @Override
            public void onLoaded(String response) {
                try {
                    processLoadedAd(response);
                } catch (JSONException e) {
                    listener.onError("Error parsing the JSON");
                }
            }
        });
        this.urlLoaderAd.execute(url);
    }

    private void processLoadedAd(String response) throws JSONException {

        System.out.println("JSON response " + response);

        JSONObject json;
        json = new JSONObject(response);

        if (json.length() == 0) {
            listener.onError("Json empty");
            return;
        } else {
            this.superAwesomeAd = SAAd.generateAd(json);
        }

        if (this.superAwesomeAd != null) {
            this.superAwesomeAd.placementId = this.placementId;

            switch (superAwesomeAd.format) {
                case RICH_MEDIA:
                case VIDEO:
                    this.loadExtraContent(superAwesomeAd.url);
                    break;
                case IMAGE_WITH_LINK:
                case GAMEWALL:
                    listener.onLoaded(this.superAwesomeAd);
                    break;
                default:
                    listener.onError("Ad type not yet supported");
                    break;
            }
        }
    }

    private void loadExtraContent(String url) {
        this.urlLoaderRichMedia.setListener(new UrlLoaderListener() {
            @Override
            public void onBeginLoad(String url) {
                Log.d(TAG, "Beginning to load extra content; URL: " + url);
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }

            @Override
            public void onLoaded(String response) {
                Log.d(TAG, response);
                superAwesomeAd.setContent(response);
                listener.onLoaded(superAwesomeAd);
            }
        });
        this.urlLoaderRichMedia.execute(url);
    }
}
