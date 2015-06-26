package tv.superawesome.superawesomesdk.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.FrameLayout;

import org.json.JSONObject;
import org.nexage.sourcekit.mraid.MRAIDNativeFeature;
import org.nexage.sourcekit.mraid.MRAIDNativeFeatureListener;
import org.nexage.sourcekit.mraid.MRAIDView;
import org.nexage.sourcekit.mraid.MRAIDViewListener;

import java.net.URL;

import tv.superawesome.superawesomesdk.SuperAwesome;


public class BannerView extends FrameLayout implements PlacementView, MRAIDViewListener, MRAIDNativeFeatureListener {

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
            public void onError() {

            }

            @Override
            public void onResponse(JSONObject response) {

                if (listener != null) listener.onLoaded();
                Log.d(TAG, "Ad loaded");
                try {
                    ad = new Ad(response);
                    if (!ad.error) {
                        if (ad.format == Ad.Format.RICH_MEDIA) {
                            ad.retrieveRichMediaContent(adLoaderListener);
                            baseUrl = ad.richMediaUrl;
                        } else {
                            String content = String.format("<div><a href=\"%s\"><img src=\"%s\" /></a></div>", ad.clickURL, ad.imageURL);
                            Log.d(TAG, content);
                            setView(content);
                        }
                    } else {
                        Log.d(TAG, "Error: " + ad.error_message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRichMediaLoaded(String content) {
                Log.d(TAG, "Setting content: " + content);
                setView(content);
            }

            @Override
            public void onAdBeginLoad(String url) {

            }

            @Override
            public void onRichMediaBeginLoad(String url) {

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
    public void onAdResponse(JSONObject response) {

    }

    @Override
    public void onAdLoaded(String content) {
        Log.d(TAG, "Setting content: " + content);
        this.setView(content);
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