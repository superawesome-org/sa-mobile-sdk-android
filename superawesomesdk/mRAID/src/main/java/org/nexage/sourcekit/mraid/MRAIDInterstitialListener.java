package org.nexage.sourcekit.mraid;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

public interface MRAIDInterstitialListener {

	/******************************************************************************
	 * A listener for basic MRAIDInterstitial ad functionality.
	 ******************************************************************************/

	public void mraidInterstitialLoaded(MRAIDInterstitial mraidInterstitial);

	public void mraidInterstitialShow(MRAIDInterstitial mraidInterstitial);

	public void mraidInterstitialHide(MRAIDInterstitial mraidInterstitial);

    public void mraidInterstitialPageFinished(View view);

}
