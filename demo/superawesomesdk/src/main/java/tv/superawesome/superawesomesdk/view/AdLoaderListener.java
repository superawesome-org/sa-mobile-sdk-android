package tv.superawesome.superawesomesdk.view;

import org.json.JSONObject;
import org.nexage.sourcekit.mraid.MRAIDNativeFeatureListener;
import org.nexage.sourcekit.mraid.MRAIDViewListener;

public interface AdLoaderListener {

	public void onError();

	public void onResponse(JSONObject response);

	public void onRichMediaLoaded(String content);

	public void onAdBeginLoad(String url);

	public void onRichMediaBeginLoad(String url);

}
