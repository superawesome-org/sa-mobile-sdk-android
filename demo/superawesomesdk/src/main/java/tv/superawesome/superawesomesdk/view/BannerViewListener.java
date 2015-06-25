package tv.superawesome.superawesomesdk.view;

import org.nexage.sourcekit.mraid.MRAIDNativeFeatureListener;
import org.nexage.sourcekit.mraid.MRAIDViewListener;

public interface BannerViewListener {
	public void onAdLeave();

	public void onAdError();

	public void onAdLoaded();

}
