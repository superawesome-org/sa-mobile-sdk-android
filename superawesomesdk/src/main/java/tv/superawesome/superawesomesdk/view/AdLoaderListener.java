package tv.superawesome.superawesomesdk.view;

import tv.superawesome.superawesomesdk.model.Ad;

public interface AdLoaderListener {

	void onBeginLoad(String url);

	void onError(String message);

	void onLoaded(Ad ad);

}
