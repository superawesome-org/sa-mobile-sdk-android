package tv.superawesome.superawesomesdk;

import android.util.Log;

import org.json.JSONObject;

import tv.superawesome.superawesomesdk.view.Ad;
import tv.superawesome.superawesomesdk.view.AdLoaderListener;

/**
 * Created by connor.leigh-smith on 29/06/15.
 */
public class AdLoader {

    private static final String TAG = "SA SDK - AdLoader";
    private AdLoaderListener listener;
    private UrlLoader urlLoader;
    private Ad ad;

    public AdLoader(AdLoaderListener listener, UrlLoader urlLoader) {
        this.listener = listener;
        this.urlLoader = urlLoader;
    }

    public void loadAd(String url) {
        this.urlLoader.setListener(new UrlLoaderListener() {
            @Override
            public void onBeginLoad(String url) {
                Log.d(TAG, "Beginning to load ad; URL: " + url);
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }

            @Override
            public void onLoaded(String response) {
                processLoadedAd(response);
            }
        });
        this.urlLoader.execute(url);
    }

    private void processLoadedAd(String response) {
        try {
            this.ad = new Ad(new JSONObject(response));
            switch (ad.format) {
                case RICH_MEDIA:
                    this.loadRichMediaContent(ad.richMediaUrl);
                    break;
                case IMAGE_WITH_LINK:
                    listener.onLoaded(this.ad);
                    break;
                default:
                    listener.onError("Ad type not yet supported");
                    break;
            }
        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }

    private void loadRichMediaContent(String url) {
        this.urlLoader.setListener(new UrlLoaderListener() {
            @Override
            public void onBeginLoad(String url) {
                Log.d(TAG, "Beginning to load rich media content; URL: " + url);
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }

            @Override
            public void onLoaded(String response) {
                Log.d(TAG, response);
                ad.setContent(response);
                listener.onLoaded(ad);
            }
        });
        this.urlLoader.execute(url);
    }
}
