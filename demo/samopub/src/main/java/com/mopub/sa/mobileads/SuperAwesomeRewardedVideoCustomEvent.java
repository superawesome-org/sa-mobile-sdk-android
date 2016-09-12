package com.mopub.sa.mobileads;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

// MoPub
import com.mopub.common.LifecycleListener;
import com.mopub.common.MoPubReward;
import com.mopub.mobileads.CustomEventRewardedVideo;
import com.mopub.mobileads.MoPubErrorCode;

// AwesomeAds
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAVideoAd;

import java.util.Map;

import static com.mopub.mobileads.MoPubRewardedVideoManager.*;

/**
 * Created by gabriel.coman on 27/12/15.
 */
public class SuperAwesomeRewardedVideoCustomEvent extends CustomEventRewardedVideo {
    /** private vars */
    private int placementId;
    private boolean isTestEnabled;
    private boolean isParentalGateEnabled;
    private boolean shouldShowCloseButton;
    private boolean shouldAutomaticallyCloseAtEnd;
    private boolean shouldLockOrientation;
    private boolean shouldShowSmallClickButton;
    private int lockOrientation;
    private String moPubId;

    // context
    private Context context;

    @Nullable
    @Override
    protected CustomEventRewardedVideoListener getVideoListenerForSdk() {
        return null;
    }

    @Nullable
    @Override
    protected LifecycleListener getLifecycleListener() {
        return null;
    }

    @NonNull
    @Override
    protected String getAdNetworkId() {
        return moPubId;
    }

    @Override
    protected void onInvalidate() {
        return;
    }

    @Override
    protected boolean checkAndInitializeSdk(@NonNull Activity activity, @NonNull Map<String, Object> map, @NonNull Map<String, String> map1) throws Exception {

        // get map variables
        placementId = 0;
        isTestEnabled = false;
        isParentalGateEnabled = true;
        shouldShowCloseButton = false;
        shouldAutomaticallyCloseAtEnd = true;
        shouldLockOrientation = false;
        shouldShowSmallClickButton = false;
        lockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        // get data
        if (map.get("com_mopub_ad_unit_id") != null){
            moPubId = (String)map.get("com_mopub_ad_unit_id").toString();
        }
        if (map1.get("placementId") != null ){
            placementId = Integer.parseInt((String) map1.get("placementId"));
        }
        if (map1.get("isTestEnabled") != null) {
            isTestEnabled = Boolean.valueOf(map1.get("isTestEnabled"));
        }
        if (map1.get("isParentalGateEnabled") != null){
            isParentalGateEnabled = Boolean.valueOf(map1.get("isParentalGateEnabled"));
        }
        if (map1.get("shouldShowCloseButton") != null){
            shouldShowCloseButton = Boolean.valueOf(map1.get("shouldShowCloseButton"));
        }
        if (map1.get("shouldAutomaticallyCloseAtEnd") != null) {
            shouldAutomaticallyCloseAtEnd = Boolean.valueOf(map1.get("shouldAutomaticallyCloseAtEnd"));
        }
        if (map1.get("shouldLockOrientation") != null) {
            shouldLockOrientation = Boolean.valueOf(map1.get("shouldLockOrientation"));
            if (map1.get("lockDirection") != null) {
                if (map1.get("lockOrientation").equals("PORTRAIT")) {
                    lockOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                } else if (map1.get("lockDirection").equals("LANDSCAPE")){
                    lockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                }
            }
        }
        if (map1.get("shouldShowSmallClickButton") != null) {
            shouldShowSmallClickButton = Boolean.valueOf(map1.get("shouldShowSmallClickButton"));
        }

        Log.d("SuperAwesome-MoPub", "loaded data until here for " + placementId);

        return true;
    }

    @Override
    protected void loadWithSdkInitialized(@NonNull final Activity activity, @NonNull Map<String, Object> map, @NonNull Map<String, String> map1) throws Exception {

        // get context
        this.context = activity;

        // load and show the ad
        SAVideoAd.setTest(isTestEnabled);
        SAVideoAd.setConfigurationProduction();
        SAVideoAd.setIsParentalGateEnabled(isParentalGateEnabled);
        SAVideoAd.setShouldAutomaticallyCloseAtEnd(shouldAutomaticallyCloseAtEnd);
        SAVideoAd.setShouldShowCloseButton(shouldShowCloseButton);
        SAVideoAd.setShouldLockOrientation(shouldLockOrientation);
        SAVideoAd.setShouldShowSmallClickButton(shouldShowSmallClickButton);
        SAVideoAd.setLockOrientation(lockOrientation);
        SAVideoAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: {
                        onRewardedVideoLoadSuccess(SuperAwesomeRewardedVideoCustomEvent.class, moPubId);
                        break;
                    }
                    case adFailedToLoad: {
                        onRewardedVideoLoadFailure(SuperAwesomeRewardedVideoCustomEvent.class, moPubId, MoPubErrorCode.VIDEO_NOT_AVAILABLE);
                        break;
                    }
                    case adShown: {
                        onRewardedVideoStarted(SuperAwesomeRewardedVideoCustomEvent.class, moPubId);
                        break;
                    }
                    case adFailedToShow: {
                        onRewardedVideoPlaybackError(SuperAwesomeRewardedVideoCustomEvent.class, moPubId, MoPubErrorCode.VIDEO_NOT_AVAILABLE);
                        break;
                    }
                    case adClicked: {
                        onRewardedVideoClicked(SuperAwesomeRewardedVideoCustomEvent.class, moPubId);
                        break;
                    }
                    case adClosed: {
                        MoPubReward reward = MoPubReward.success(MoPubReward.NO_REWARD_LABEL, 0);
                        onRewardedVideoCompleted(SuperAwesomeRewardedVideoCustomEvent.class, moPubId, reward);
                        onRewardedVideoClosed(SuperAwesomeRewardedVideoCustomEvent.class, moPubId);
                        break;
                    }
                }
            }
        });
        SAVideoAd.load(placementId);
    }

    @Override
    protected boolean hasVideoAvailable() {
        return SAVideoAd.hasAdAvailable();
    }

    @Override
    protected void showVideo() {
        SAVideoAd.play(context);
    }
}
