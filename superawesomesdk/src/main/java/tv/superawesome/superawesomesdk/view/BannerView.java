package tv.superawesome.superawesomesdk.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.nexage.sourcekit.mraid.MRAIDNativeFeature;
import org.nexage.sourcekit.mraid.MRAIDNativeFeatureListener;
import org.nexage.sourcekit.mraid.MRAIDView;
import org.nexage.sourcekit.mraid.MRAIDViewListener;

import tv.superawesome.superawesomesdk.Ad;
import tv.superawesome.superawesomesdk.AdManager;
import tv.superawesome.superawesomesdk.R;
import tv.superawesome.superawesomesdk.SuperAwesome;


public class BannerView extends PlacementView implements MRAIDViewListener {

	protected static final String TAG = "SA SDK - Banner";

    public BannerView(Context context, String placementID, AdManager adManager) {
        super(context, placementID, adManager);
    }

    public BannerView(Context context, AttributeSet attrs) {
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

//        this.showPadlock();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                loadAd();
            }
        }, 30000);
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
        } finally {
            a.recycle();
        }
    }

    @Override
    public void mraidViewLoaded(MRAIDView mraidView) {

    }

    @Override
    public void mraidViewExpand(MRAIDView mraidView) {

    }

    @Override
    public void mraidViewClose(MRAIDView mraidView) {

    }

    @Override
    public void mraidViewAddPadlock(View view, ImageButton padlockRegion) {
        showPadlock(view, padlockRegion);
    }

    @Override
    public boolean mraidViewResize(MRAIDView mraidView, int width, int height, int offsetX, int offsetY) {
        return false;
    }

}