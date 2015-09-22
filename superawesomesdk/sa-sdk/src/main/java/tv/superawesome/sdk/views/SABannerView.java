package tv.superawesome.sdk.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import org.nexage.sourcekit.mraid.MRAIDNativeFeature;
import org.nexage.sourcekit.mraid.MRAIDView;
import org.nexage.sourcekit.mraid.MRAIDViewListener;

import tv.superawesome.sdk.AdManager;
import tv.superawesome.sdk.events.SAEventManager;


public class SABannerView extends SAPlacementView implements MRAIDViewListener {

	protected static final String TAG = "SA SDK - Banner";
    private static final int AD_RELOAD_INTERVAL = 30000;
    private Handler handler;

    public SABannerView(Context context, String placementID, AdManager adManager) {
        super(context, placementID, adManager);
    }

    public SABannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void setView(String content) {
        this.removeAllViews();
        String[] supportedNativeFeatures = {
                MRAIDNativeFeature.INLINE_VIDEO,
                MRAIDNativeFeature.STORE_PICTURE,
        };
        this.mraidView = new MRAIDView(this.context, baseUrl, content,
                supportedNativeFeatures, this, this);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = this.loadedAd.width * metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT;
        int height = this.loadedAd.height * metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT;

        LayoutParams params = new LayoutParams(width, height);
        mraidView.setLayoutParams(params);
        this.addView(mraidView);

        this.handler = new Handler();
        this.handler.postDelayed(new Runnable() {
            public void run() {
                Log.d(TAG, "Loading ad in handler");
                loadAd();
            }
        }, AD_RELOAD_INTERVAL);
    }

    @Override
    public void mraidViewLoaded(MRAIDView mraidView) {
        SAEventManager.getIntance().LogAdFailed(super.loadedAd);

    }

    @Override
    public void mraidViewExpand(MRAIDView mraidView) {

    }

    @Override
    public void mraidViewClose(MRAIDView mraidView) {

    }

    @Override
    public void mraidViewPageFinished(View view) {
        showPadlock(view);
    }

    @Override
    public boolean mraidViewResize(MRAIDView mraidView, int width, int height, int offsetX, int offsetY) {
        return false;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.paused();
    }

    @Override
    public void paused() {
        if (this.handler != null) {
            /* Remove the handler callback so the ad stops reloading every x seconds. */
            this.handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void resumed() {
        if (this.loadedAd != null) {
            this.setView(this.loadedAd.getContent());
        }
    }
}