package tv.superawesome.sdk;

import tv.superawesome.sdk.models.SAAd;

public interface AdLoaderListener {

	void onBeginLoad(String url);

	void onError(String message);

	void onLoaded(SAAd superAwesomeAd);

}
