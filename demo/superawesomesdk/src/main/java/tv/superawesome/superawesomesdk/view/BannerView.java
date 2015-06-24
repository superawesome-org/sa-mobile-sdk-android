package tv.superawesome.superawesomesdk.view;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;

import org.json.JSONObject;
import org.nexage.sourcekit.mraid.MRAIDNativeFeature;
import org.nexage.sourcekit.mraid.MRAIDNativeFeatureListener;
import org.nexage.sourcekit.mraid.MRAIDView;
import org.nexage.sourcekit.mraid.MRAIDViewListener;

import tv.superawesome.superawesomesdk.SuperAwesome;


public class BannerView extends FrameLayout implements AdView {

	private static final String TAG = "SA SDK - Banner";
    private String placementID;
    private boolean testMode = false;
    private Context context;
    private MRAIDViewListener mraidViewListener = null;
    private MRAIDNativeFeatureListener mraidNativeFeatureListener = null;


	public BannerView(Context context) {
        super(context);
        this.context = context;
        this.loadAd();
	}

    public void setPlacementID(String placementID) {
        this.placementID = placementID;
    }

    public void loadAd()
    {
        SuperAwesome.getInstance().getAdManager().getAd(this.placementID, this.testMode, this);
    }

    public MRAIDView getView() {
        String[] supportedNativeFeatures = {
                MRAIDNativeFeature.CALENDAR,
                MRAIDNativeFeature.INLINE_VIDEO,
                MRAIDNativeFeature.SMS,
                MRAIDNativeFeature.STORE_PICTURE,
                MRAIDNativeFeature.TEL,
        };

        MRAIDView mraidView = new MRAIDView(this.context, "http://superawesome.tv",
                "<h1>Rich Media Content</h1>", supportedNativeFeatures, this.mraidViewListener,
                this.mraidNativeFeatureListener);
//        mraidView.setLayoutParams(params);

        return mraidView;
    }

    public void addMRAIDViewListener(MRAIDViewListener listener) {
        this.mraidViewListener = listener;
    }

    public void addMRAIDNativeFeatureListener(MRAIDNativeFeatureListener listener) {
        this.mraidNativeFeatureListener = listener;
    }

    @Override
    public void onAdLoaded(JSONObject response) {
        System.out.println(response);
    }

    @Override
    public void onAdBeginLoad() {
        Log.d(TAG, "Ad loading");
    }

    @Override
    public void onAdError() {
        Log.d(TAG, "Error loading ad");
    }

}