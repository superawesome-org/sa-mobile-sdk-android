package tv.superawesome.plugins.admob;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdAdapter;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAOrientation;
import tv.superawesome.sdk.views.SAVideoAd;

public class SAAdMobVideoMediationAdapter implements MediationRewardedVideoAdAdapter{

    private MediationRewardedVideoAdListener listener = null;
    private Integer loadedPlacementId = 0;
    private Context context = null;

    @Override
    public void initialize(Context context,
                           MediationAdRequest mediationAdRequest,
                           String unused,
                           MediationRewardedVideoAdListener listener,
                           Bundle serverParameters,
                           Bundle mediationExtras) {

        //
        // Step 1. See if the context is an activity
        if (!(context instanceof Activity)) {
            if (listener != null) {
                listener.onInitializationFailed(SAAdMobVideoMediationAdapter.this, AdRequest.ERROR_CODE_INVALID_REQUEST);
            }
            return;
        }

        //
        // Step 2. Save context
        this.context = context;

        //
        // Step 3. Try to get the placement Id
        try {
            loadedPlacementId = Integer.parseInt(unused);
        } catch (NumberFormatException e) {
            try {
                String adUnit = serverParameters.getString(MediationRewardedVideoAdAdapter.CUSTOM_EVENT_SERVER_PARAMETER_FIELD);
                loadedPlacementId = Integer.parseInt(adUnit);
            } catch (NumberFormatException e2) {
                if (listener != null) {
                    listener.onInitializationFailed(SAAdMobVideoMediationAdapter.this, AdRequest.ERROR_CODE_INVALID_REQUEST);
                }
                return;
            }
        }

        //
        // Step 4. Try to get any mediation extras to customise the ad experience
        if (mediationExtras != null) {
            SAVideoAd.setConfiguration(SAConfiguration.fromValue(mediationExtras.getInt(SAAdMobExtras.kKEY_CONFIGURATION)));
            SAVideoAd.setTestMode(mediationExtras.getBoolean(SAAdMobExtras.kKEY_TEST));
            SAVideoAd.setOrientation(SAOrientation.fromValue(mediationExtras.getInt(SAAdMobExtras.kKEY_ORIENTATION)));
            SAVideoAd.setParentalGate(mediationExtras.getBoolean(SAAdMobExtras.kKEY_PARENTAL_GATE));
            SAVideoAd.setSmallClick(mediationExtras.getBoolean(SAAdMobExtras.kKEY_SMALL_CLICK));
            SAVideoAd.setCloseButton(mediationExtras.getBoolean(SAAdMobExtras.kKEY_CLOSE_BUTTON));
            SAVideoAd.setCloseAtEnd(mediationExtras.getBoolean(SAAdMobExtras.kKEY_CLOSE_AT_END));
            SAVideoAd.setBackButton(mediationExtras.getBoolean(SAAdMobExtras.kKEY_BACK_BUTTON));
        }

        //
        // Step 5. If we've got to here, save the listener
        this.listener = listener;

        //
        // Step 6. Tell AdMob initialisation has been successful
        if (listener != null) {
            listener.onInitializationSucceeded(SAAdMobVideoMediationAdapter.this);
        }
    }

    @Override
    public void loadAd(MediationAdRequest mediationAdRequest, Bundle bundle, Bundle bundle1) {

        SAVideoAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: {
                        listener.onAdLoaded(SAAdMobVideoMediationAdapter.this);
                        break;
                    }
                    case adEmpty:
                    case adFailedToLoad: {
                        listener.onAdFailedToLoad(SAAdMobVideoMediationAdapter.this, AdRequest.ERROR_CODE_INTERNAL_ERROR);
                        break;
                    }
                    case adAlreadyLoaded:
                        break;
                    case adShown: {
                        listener.onAdOpened(SAAdMobVideoMediationAdapter.this);
                        break;
                    }
                    case adFailedToShow:
                        break;
                    case adClicked: {
                        listener.onAdClicked(SAAdMobVideoMediationAdapter.this);
                        listener.onAdLeftApplication(SAAdMobVideoMediationAdapter.this);
                        break;
                    }
                    case adEnded: {
                        SARewardItem item = new SARewardItem("", 1);
                        listener.onRewarded(SAAdMobVideoMediationAdapter.this, item);
                        break;
                    }
                    case adClosed: {
                        listener.onAdClosed(SAAdMobVideoMediationAdapter.this);
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
