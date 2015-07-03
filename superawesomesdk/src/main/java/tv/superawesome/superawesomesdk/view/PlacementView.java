package tv.superawesome.superawesomesdk.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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


public abstract class PlacementView extends FrameLayout implements MRAIDNativeFeatureListener {

	protected static final String TAG = "SA SDK - Placement";
    protected String placementID;
    protected boolean testMode = false;
    protected Context context;
    protected AdManager adManager;
    protected PlacementViewListener listener = null;
    protected AdLoaderListener adLoaderListener;
    protected MRAIDView mraidView;
    protected Ad loadedAd = null;
    protected String baseUrl = "http://superawesome.tv";

    public PlacementView(Context context, String placementID, AdManager adManager) {
        super(context);
        this.context = context;
        this.placementID = placementID;
        this.adManager = adManager;
    }

    public PlacementView(Context context, AttributeSet attrs) {
        super(context);
        this.context = context;
        this.adManager = SuperAwesome.createAdManager();
        this.fetchXmlAttrs(attrs);
        this.loadAd();
    }

    private boolean checkAppPermissions() {
        int res = getContext().checkCallingOrSelfPermission("android.permission.INTERNET");
        if (res != PackageManager.PERMISSION_GRANTED) {
            String message = "Error: Your app does not have a required permission: INTERNET. Not requesting ad.";
            if (this.listener != null) this.listener.onAdError(message);
            Log.d(TAG, message);
            return false;
        }
        res = getContext().checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE");
        if (res != PackageManager.PERMISSION_GRANTED) {
            String message = "Error: Your app does not have a required permission: ACCESS_NETWORK_STATE. Not requesting ad.";
            if (this.listener != null) this.listener.onAdError(message);
            Log.d(TAG, message);
            return false;
        }
        return true;
    }

    protected abstract void fetchXmlAttrs(AttributeSet attrs);

    public void enableTestMode() {
        this.testMode = true;
    }

    public void disableTestMode() {
        this.testMode = false;
    }

    protected abstract void setView(String content);

    public void setListener(PlacementViewListener listener) {
        this.listener = listener;
    }

    public void loadAd() {
        if (!this.checkAppPermissions()) {
            return;
        }
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

}