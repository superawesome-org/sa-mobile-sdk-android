package tv.superawesome.sdk;

/**
 * Created by connor.leigh-smith on 29/06/15.
 */
public interface UrlLoaderListener {

    void onBeginLoad(String url);

    void onError(String message);

    void onLoaded(String content);

}
