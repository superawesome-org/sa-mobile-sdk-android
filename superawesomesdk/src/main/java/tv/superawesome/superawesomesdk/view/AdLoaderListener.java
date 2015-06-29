package tv.superawesome.superawesomesdk.view;

public interface AdLoaderListener {

	void onBeginLoad(String url);

	void onError(String message);

	void onLoaded(Ad ad);

}
