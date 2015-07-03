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

import tv.superawesome.superawesomesdk.AdManager;
import tv.superawesome.superawesomesdk.R;
import tv.superawesome.superawesomesdk.SuperAwesome;


public class BannerView extends FrameLayout implements MRAIDViewListener, MRAIDNativeFeatureListener {

	private static final String TAG = "SA SDK - Banner";
    protected String placementID;
    protected boolean testMode = false;
    protected Context context;
    protected AdManager adManager;
    protected BannerViewListener listener = null;
    protected AdLoaderListener adLoaderListener;
    protected MRAIDView mraidView;
    protected Ad loadedAd = null;
    protected String baseUrl = "http://superawesome.tv";

    public BannerView(Context context, String placementID, AdManager adManager) {
        super(context);
        this.context = context;
        this.placementID = placementID;
        this.adManager = adManager;
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context);
        this.context = context;
        this.adManager = SuperAwesome.createAdManager();

        //Get attributes from resources file if they exist
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BannerView,
                0, 0);
        try {
            this.placementID = a.getString(R.styleable.BannerView_placementID);
            this.testMode = a.getBoolean(R.styleable.BannerView_testMode, false);
        } finally {
            a.recycle();
        }
        this.loadAd();
    }

    public void enableTestMode() {
        this.testMode = true;
    }

    public void disableTestMode() {
        this.testMode = false;
    }

    private void setView(String content) {
        if (this.mraidView != null) {
            this.removeView(mraidView);
        }
        String[] supportedNativeFeatures = {
            MRAIDNativeFeature.CALENDAR,
            MRAIDNativeFeature.INLINE_VIDEO,
            MRAIDNativeFeature.SMS,
            MRAIDNativeFeature.STORE_PICTURE,
            MRAIDNativeFeature.TEL,
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

    public void setListener(BannerViewListener listener) {
        this.listener = listener;
    }

    public void loadAd() {
        try {
            this.adManager.getAd(this.placementID, this.testMode, new AdLoaderListener() {
                @Override
                public void onBeginLoad(String url) {

                }

                @Override
                public void onError(String message) {
                    if (listener != null) listener.onAdError(message);
                }

                @Override
                public void onLoaded(Ad ad) {
                    if (listener != null) listener.onAdLoaded(ad);
                    loadedAd = ad;
                    Log.d(TAG, "Ad loaded");
                    try {
                        if (!ad.error) {
                            if (ad.richMediaUrl != null) baseUrl = ad.richMediaUrl;
                            setView(ad.getContent());
                        } else {
                            if (listener != null) listener.onAdError(ad.error_message);
                            Log.d(TAG, "Error: " + ad.error_message);
                        }
                    } catch (Exception e) {
                        if (listener != null) listener.onAdError(e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            if (listener != null) listener.onAdError(e.getMessage());
        }
    }

    @Override
    public void mraidNativeFeatureCallTel(String url) {

    }

    @Override
    public void mraidNativeFeatureCreateCalendarEvent(String eventJSON) {

    }

    @Override
    public void mraidNativeFeaturePlayVideo(String url) {

    }

    @Override
    public void mraidNativeFeatureOpenBrowser(String url) {
        Log.d(TAG, "mraidNativeFeatureOpenBrowser " + url);

        // Demo will open the URL in an external browser
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void mraidNativeFeatureStorePicture(String url) {

    }

    @Override
    public void mraidNativeFeatureSendSms(String url) {

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