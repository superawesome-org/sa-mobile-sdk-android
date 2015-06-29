package tv.superawesome.superawesomesdk.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.FrameLayout;

import org.nexage.sourcekit.mraid.MRAIDNativeFeature;
import org.nexage.sourcekit.mraid.MRAIDNativeFeatureListener;
import org.nexage.sourcekit.mraid.MRAIDView;
import org.nexage.sourcekit.mraid.MRAIDViewListener;

import tv.superawesome.superawesomesdk.SuperAwesome;


public class BannerView extends FrameLayout implements MRAIDViewListener, MRAIDNativeFeatureListener {

	private static final String TAG = "SA SDK - Banner";
    private String placementID;
    private boolean testMode = false;
    private Context context;
    private BannerViewListener listener = null;
    private AdLoaderListener adLoaderListener;
    private MRAIDView mraidView;
    private Ad ad = null;
    private String baseUrl = "http://superawesome.tv";

	public BannerView(Context context, String placementID) {
        super(context);
        this.context = context;
        this.placementID = placementID;
        this.adLoaderListener = new AdLoaderListener() {
            @Override
            public void onBeginLoad(String url) {

            }

            @Override
            public void onError(String message) {
                if (listener != null) listener.onAdError(message);
            }

            @Override
            public void onLoaded(Ad ad) {
                if (listener != null) listener.onAdLoaded();
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
        };
        this.loadAd();
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
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mraidView.setLayoutParams(params);
        this.addView(mraidView);
    }

    public void setListener(BannerViewListener listener) {
        this.listener = listener;
    }

    public void loadAd()
    {
        SuperAwesome.getInstance().getAdManager().getAd(this.placementID, this.testMode, this.adLoaderListener);
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