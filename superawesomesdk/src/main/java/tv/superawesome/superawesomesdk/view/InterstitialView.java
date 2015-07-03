package tv.superawesome.superawesomesdk.view;

import tv.superawesome.superawesomesdk.AdManager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import org.nexage.sourcekit.mraid.MRAIDInterstitial;
import org.nexage.sourcekit.mraid.MRAIDInterstitialListener;
import org.nexage.sourcekit.mraid.MRAIDNativeFeature;
import org.nexage.sourcekit.mraid.MRAIDView;


public class InterstitialView extends PlacementView implements MRAIDInterstitialListener {

    protected static final String TAG = "SA SDK - Interstitial";
    private MRAIDInterstitial mraidInterstitial;

    public InterstitialView(Context context, String placementID, AdManager adManager) {
        super(context, placementID, adManager);
    }

    public InterstitialView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void setView(String content) {
        this.mraidInterstitial = null;
        String[] supportedNativeFeatures = {
                MRAIDNativeFeature.CALENDAR,
                MRAIDNativeFeature.INLINE_VIDEO,
                MRAIDNativeFeature.SMS,
                MRAIDNativeFeature.STORE_PICTURE,
                MRAIDNativeFeature.TEL,
        };
        this.mraidInterstitial = new MRAIDInterstitial(this.context, baseUrl, content,
                supportedNativeFeatures, this, this);
    }

    @Override
    public void mraidInterstitialLoaded(MRAIDInterstitial mraidInterstitial) {
        this.mraidInterstitial.show();
    }

    @Override
    public void mraidInterstitialShow(MRAIDInterstitial mraidInterstitial) {

    }

    @Override
    public void mraidInterstitialHide(MRAIDInterstitial mraidInterstitial) {

    }
}
