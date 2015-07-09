package tv.superawesome.superawesomesdk.view;

import tv.superawesome.superawesomesdk.AdManager;
import tv.superawesome.superawesomesdk.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import org.nexage.sourcekit.mraid.MRAIDInterstitial;
import org.nexage.sourcekit.mraid.MRAIDInterstitialListener;
import org.nexage.sourcekit.mraid.MRAIDNativeFeature;


public class InterstitialView extends PlacementView implements MRAIDInterstitialListener {

    protected static final String TAG = "SA SDK - Interstitial";
    /* Updated when the mRaidInterstitial declares the ad is ready, after loading it. */
    private boolean isReady;
    /* Set when the user calls display(); the interstitial is shown when both 'isReady' and 'display' are true. */
    private boolean display;
    private MRAIDInterstitial mraidInterstitial;

    private View webView;

    public InterstitialView(Context context, String placementID, AdManager adManager) {
        super(context, placementID, adManager);
    }

    public InterstitialView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void fetchXmlAttrs(AttributeSet attrs) {
        //Get attributes from resources file
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PlacementView,
                0, 0);
        try {
            this.placementID = a.getString(R.styleable.PlacementView_placementID);
            this.testMode = a.getBoolean(R.styleable.PlacementView_testMode, false);
            this.display = a.getBoolean(R.styleable.PlacementView_showInstantly, false);
        } finally {
            a.recycle();
        }
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
        }
    }

    @Override
    public void mraidInterstitialLoaded(MRAIDInterstitial mraidInterstitial) {
        this.isReady = true;
        Log.d(TAG, ""+this.display);
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
}
