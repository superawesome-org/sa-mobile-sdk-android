package tv.superawesome.plugins.admob;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdAdapter;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener;

import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAVideoAd;

public class SAVideoCustomEvent implements MediationRewardedVideoAdAdapter{

    private MediationRewardedVideoAdListener listener = null;
    private Integer loadedPlacementId = 0;
    private Context context = null;

    @Override
    public void initialize(Context context, MediationAdRequest mediationAdRequest, String s, MediationRewardedVideoAdListener listener, Bundle bundle, Bundle bundle1) {

        if (!(context instanceof Activity)) {
            if (listener != null) {
                listener.onInitializationFailed(SAVideoCustomEvent.this, AdRequest.ERROR_CODE_INVALID_REQUEST);
            }
            return;
        }

        this.context = context;

        try {
            loadedPlacementId = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            try {
                String adUnit = bundle.getString(MediationRewardedVideoAdAdapter.CUSTOM_EVENT_SERVER_PARAMETER_FIELD);
                loadedPlacementId = Integer.parseInt(adUnit);
            } catch (NumberFormatException e2) {
                if (listener != null) {
                    listener.onInitializationFailed(SAVideoCustomEvent.this, AdRequest.ERROR_CODE_INVALID_REQUEST);
                }
                return;
            }
        }

        this.listener = listener;

        if (listener != null) {
            listener.onInitializationSucceeded(SAVideoCustomEvent.this);
        }
    }

    @Override
    public void loadAd(MediationAdRequest mediationAdRequest, Bundle bundle, Bundle bundle1) {

        SAVideoAd.setTestMode(mediationAdRequest.isTesting());
        SAVideoAd.setConfigurationStaging();
        SAVideoAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: {
                        listener.onAdLoaded(SAVideoCustomEvent.this);
                        break;
                    }
                    case adFailedToLoad: {
                        listener.onAdFailedToLoad(SAVideoCustomEvent.this, AdRequest.ERROR_CODE_INTERNAL_ERROR);
                        break;
                    }
                    case adAlreadyLoaded:
                        break;
                    case adShown: {
                        listener.onAdOpened(SAVideoCustomEvent.this);
                        break;
                    }
                    case adFailedToShow:
                        break;
                    case adClicked: {
                        listener.onAdClicked(SAVideoCustomEvent.this);
                        listener.onAdLeftApplication(SAVideoCustomEvent.this);
                        break;
                    }
                    case adEnded: {
                        SARewardItem item = new SARewardItem("", 1);
                        listener.onRewarded(SAVideoCustomEvent.this, item);
                        break;
                    }
                    case adClosed: {
                        listener.onAdClosed(SAVideoCustomEvent.this);
                        break;
                    }
                }
            }
        });
        SAVideoAd.load(loadedPlacementId, context);
    }

    @Override
    public void showVideo() {
        SAVideoAd.play(loadedPlacementId, context);
    }

    @Override
    public boolean isInitialized() {
        return loadedPlacementId > 0 && listener != null;
    }

    @Override
    public void onDestroy() {
        // do nothing
    }

    @Override
    public void onPause() {
        // do nothing
    }

    @Override
    public void onResume() {
        // do nothing
    }

    public class SARewardItem implements RewardItem {
        private String mRewardType;
        private int mRewardAmount;

        public SARewardItem(String rewardType, int rewardAmount) {
            this.mRewardType = rewardType;
            this.mRewardAmount = rewardAmount;
        }

        @Override
        public String getType() {
            return mRewardType;
        }

        @Override
        public int getAmount() {
            return mRewardAmount;
        }
    }
}
