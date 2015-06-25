package tv.superawesome.superawesomesdk.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.nexage.sourcekit.mraid.MRAIDNativeFeature;
import org.nexage.sourcekit.mraid.MRAIDNativeFeatureListener;
import org.nexage.sourcekit.mraid.MRAIDView;
import org.nexage.sourcekit.mraid.MRAIDViewListener;

import java.net.URL;

import tv.superawesome.superawesomesdk.R;
import tv.superawesome.superawesomesdk.SuperAwesome;


public class BannerView extends FrameLayout implements PlacementView, MRAIDViewListener, MRAIDNativeFeatureListener {

	private static final String TAG = "SA SDK - Banner";
    private String placementID;
    private boolean testMode = false;
    private Context context;
    private BannerViewListener listener = null;
    private MRAIDView mraidView;
    private Ad ad = null;

	public BannerView(Context context, String placementID) {
        super(context);
        this.context = context;
        this.placementID = placementID;
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
        this.mraidView = new MRAIDView(this.context, "http://superawesome.tv", content,
                supportedNativeFeatures, this, this);
//        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        mraidView.setLayoutParams(params);
//        this.mraidView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 100));
        this.addView(mraidView);
    }

    public BannerViewListener getListener() {
        return this.listener;
    }

    public void loadAd()
    {
        SuperAwesome.getInstance().getAdManager().getAd(this.placementID, this.testMode, this);
    }

    @Override
    public void onAdLoaded(JSONObject response) {
        Log.d(TAG, "Ad loaded");
        try {
            this.ad = new Ad(response);
            if (!this.ad.error) {
                String contents = String.format("<div><a href=\"%s\"><img src=\"%s\" /></a></div>", ad.clickURL, ad.imageURL);
                Log.d(TAG, contents);
                this.setView(contents);
            } else {
                Log.d(TAG, "Error: " + ad.error_message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onAdBeginLoad() {

    }

    @Override
    public void onAdBeginLoad(URL url) {

        Log.d(TAG, "Ad loading, URL: " + url.toString());
    }

    @Override
    public void onAdError() {
        if(this.listener != null) listener.onAdError();
        Log.d(TAG, "Error loading ad");
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