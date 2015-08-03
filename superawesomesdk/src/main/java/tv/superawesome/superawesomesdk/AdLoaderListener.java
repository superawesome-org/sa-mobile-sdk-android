package tv.superawesome.superawesomesdk;

import tv.superawesome.superawesomesdk.models.SAAd;

public interface AdLoaderListener {

	void onBeginLoad(String url);

	void onError(String message);

	void onLoaded(SAAd superAwesomeAd);

}
