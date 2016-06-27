package com.mopub.sa.mobileads;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/** MoPub */
import com.mopub.common.LifecycleListener;
import com.mopub.common.MoPubReward;
import com.mopub.mobileads.CustomEventRewardedVideo;
import com.mopub.mobileads.MoPubErrorCode;
/** AwesomeAds */
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.saadloader.SALoaderInterface;
import tv.superawesome.lib.samodelspace.SAAd;
import tv.superawesome.sdk.views.SAAdInterface;
import tv.superawesome.sdk.views.SAFullscreenVideoAd;
import tv.superawesome.sdk.views.SAVideoAdInterface;

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

    /** the actual video activity */
    private SAAd cAd;
    private SAFullscreenVideoAd video;

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

        /** get map variables */
        placementId = 0;
        isTestEnabled = false;
        isParentalGateEnabled = true;
        shouldShowCloseButton = false;
        shouldAutomaticallyCloseAtEnd = true;
        shouldLockOrientation = false;
        shouldShowSmallClickButton = false;
        lockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        /** get datas */
        if (map.get("com_mopub_ad_unit_id") != null){
            moPubId = (String)map.get("com_mopub_ad_unit_id").toString();
        }
        if (map1.get("placementId") != null ){
            placementId = Integer.parseInt((String)map1.get("placementId").toString());
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

        /** before loading */
        SuperAwesome.getInstance().setConfigurationProduction();
        SuperAwesome.getInstance().setApplicationContext(activity);
        SuperAwesome.getInstance().setTestMode(isTestEnabled);

        Log.d("SuperAwesome-MoPub", "load SDK with Initialized for " + placementId);

        /** load and show the ad */
        SALoader loader = new SALoader();
        loader.loadAd(placementId, new SALoaderInterface() {
            @Override
            public void didLoadAd(SAAd ad) {
                Log.d("SuperAwesome-MoPub", "Did Load Ad " + placementId);

                /** add cAd */
                cAd = ad;

                /** show video activity */
                video = new SAFullscreenVideoAd(activity);
                video.setAd(cAd);
                video.setIsParentalGateEnabled(isParentalGateEnabled);
                video.setShouldAutomaticallyCloseAtEnd(shouldAutomaticallyCloseAtEnd);
                video.setShouldShowCloseButton(shouldShowCloseButton);
                video.setShouldLockOrientation(shouldLockOrientation);
                video.setLockOrientation(lockOrientation);
                video.setShouldShowSmallClickButton(shouldShowSmallClickButton);

                video.setAdListener(new SAAdInterface() {
                    @Override
                    public void adWasShown(int placementId) {

                    }

                    @Override
                    public void adFailedToShow(int placementId) {
                        onRewardedVideoPlaybackError(SuperAwesomeRewardedVideoCustomEvent.class, moPubId, MoPubErrorCode.VIDEO_NOT_AVAILABLE);
                    }

                    @Override
                    public void adWasClosed(int placementId) {
                        onRewardedVideoClosed(SuperAwesomeRewardedVideoCustomEvent.class, moPubId);
                    }

                    @Override
                    public void adWasClicked(int placementId) {
                        onRewardedVideoClicked(SuperAwesomeRewardedVideoCustomEvent.class, moPubId);
                    }

                    @Override
                    public void adHasIncorrectPlacement(int placementId) {
                        onRewardedVideoPlaybackError(SuperAwesomeRewardedVideoCustomEvent.class, moPubId, MoPubErrorCode.VIDEO_NOT_AVAILABLE);
                    }
                });

                video.setVideoAdListener(new SAVideoAdInterface() {
                    @Override
                    public void adStarted(int placementId) {

                    }

                    @Override
                    public void videoStarted(int placementId) {
                        onRewardedVideoStarted(SuperAwesomeRewardedVideoCustomEvent.class, moPubId);
                    }

                    @Override
                    public void videoReachedFirstQuartile(int placementId) {

                    }

                    @Override
                    public void videoReachedMidpoint(int placementId) {

                    }

                    @Override
                    public void videoReachedThirdQuartile(int placementId) {

                    }

                    @Override
                    public void videoEnded(int placementId) {

                    }

                    @Override
                    public void adEnded(int placementId) {

                    }

                    @Override
                    public void allAdsEnded(int placementId) {
                        MoPubReward reward = MoPubReward.success(MoPubReward.NO_REWARD_LABEL, 0);
                        onRewardedVideoCompleted(SuperAwesomeRewardedVideoCustomEvent.class, moPubId, reward);
                    }
                });

                /** call this */
                onRewardedVideoLoadSuccess(SuperAwesomeRewardedVideoCustomEvent.class, moPubId);
            }

            @Override
            public void didFailToLoadAdForPlacementId(int placementId) {
                onRewardedVideoLoadFailure(SuperAwesomeRewardedVideoCustomEvent.class, moPubId, MoPubErrorCode.VIDEO_NOT_AVAILABLE);
            }
        });

        return;
    }

    @Override
    protected boolean hasVideoAvailable() {
        return (cAd != null ? true : false);
    }

    @Override
    protected void showVideo() {
        /** play the ad */
        video.play();
    }
}
