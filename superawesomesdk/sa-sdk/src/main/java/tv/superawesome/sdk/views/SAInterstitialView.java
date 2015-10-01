package tv.superawesome.sdk.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import org.nexage.sourcekit.mraid.MRAIDInterstitial;
import org.nexage.sourcekit.mraid.MRAIDInterstitialListener;
import org.nexage.sourcekit.mraid.MRAIDNativeFeature;

import tv.superawesome.sdk.AdManager;
import tv.superawesome.sdk.events.SAEventManager;


public class SAInterstitialView extends SAPlacementView implements MRAIDInterstitialListener {

    protected static final String TAG = "SA SDK - Interstitial";
    /* Updated when the mRaidInterstitial declares the ad is ready, after loading it. */
    private boolean isReady;
    /* Set when the user calls show(); the interstitial is shown when both 'isReady' and 'display' are true. */
    private boolean display;
    private MRAIDInterstitial mraidInterstitial;

    private View webView;

    public SAInterstitialView(Context context, String placementID, AdManager adManager) {
        super(context, placementID, adManager);
    }

    public boolean isDisplay() {
        return this.display;
    }

    public SAInterstitialView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void setView(String content) {
        this.removeAllViews();
        String[] supportedNativeFeatures = {
                MRAIDNativeFeature.INLINE_VIDEO,
                MRAIDNativeFeature.STORE_PICTURE,
        };
        this.mraidInterstitial = new MRAIDInterstitial(this.context, baseUrl, content,
                supportedNativeFeatures, this, this);
    }

    public void show() {
        this.display = true;
        if (this.isReady) {
            this.mraidInterstitial.show();
            SAEventManager.getIntance().LogViewableImpression(super.loadedAd);
        }
    }

    @Override
    public void mraidInterstitialLoaded(MRAIDInterstitial mraidInterstitial) {
        this.isReady = true;
        if (this.display) {
            this.mraidInterstitial.show();
        }
    }

    @Override
    public void mraidInterstitialShow(MRAIDInterstitial mraidInterstitial) {
        this.showPadlock(this.webView);

       /* Ought to change this from a timeout... */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                showPadlock(webView);
            }
        }, 1000);
    }

    @Override
    public void mraidInterstitialHide(MRAIDInterstitial mraidInterstitial) {
        this.display = false;
        this.loadAd();
    }

    @Override
    public void mraidInterstitialPageFinished(View view) {
        this.webView = view;
        showPadlock(webView);

       /* Ought to change this from a timeout... */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                showPadlock(webView);
            }
        }, 1000);
    }

    @Override
    public void resumed() {
        if (this.loadedAd != null) {
            this.mraidInterstitial.show();
        }
    }

    @Override
    public void paused() {
        if (this.loadedAd != null) {

        }
    }
}
