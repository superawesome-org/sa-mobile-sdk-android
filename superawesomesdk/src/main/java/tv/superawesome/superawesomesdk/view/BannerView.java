package tv.superawesome.superawesomesdk.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.FrameLayout;

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
        if (this.mraidView != null) {
            this.removeView(mraidView);
        }
        String[] supportedNativeFeatures = {
                MRAIDNativeFeature.INLINE_VIDEO,
                MRAIDNativeFeature.STORE_PICTURE,
        };
        this.mraidView = new MRAIDView(this.context, baseUrl, content,
                supportedNativeFeatures, this, this);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = this.loadedAd.width * metrics.densityDpi / 160;
        int height = this.loadedAd.height * metrics.densityDpi / 160;

        LayoutParams params = new LayoutParams(width, height);
        mraidView.setLayoutParams(params);
        this.addView(mraidView);
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
    public boolean mraidViewResize(MRAIDView mraidView, int width, int height, int offsetX, int offsetY) {
        return false;
    }

}