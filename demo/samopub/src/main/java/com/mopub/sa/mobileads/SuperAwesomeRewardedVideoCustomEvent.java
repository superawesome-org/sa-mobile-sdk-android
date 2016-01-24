package com.mopub.sa.mobileads;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/** MoPub */
import com.mopub.common.LifecycleListener;
import com.mopub.common.MoPub;
import com.mopub.common.MoPubReward;
import com.mopub.mobileads.CustomEventRewardedVideo;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideoManager;
/** AwesomeAds */
import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.data.Loader.SALoader;
import tv.superawesome.sdk.data.Loader.SALoaderListener;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;
import tv.superawesome.sdk.listeners.SAVideoAdListener;
import tv.superawesome.sdk.views.SAVideoActivity;
import tv.superawesome.sdk.views.SAParentalGate;

import java.util.Map;
import java.util.Set;

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
    private String moPubId;

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
        if (map1.get("parentalGateEnabled") != null){
            isParentalGateEnabled = Boolean.valueOf(map1.get("parentalGateEnabled"));
        }
        if (map1.get("shouldShowCloseButton") != null){
            shouldShowCloseButton = Boolean.valueOf(map1.get("shouldShowCloseButton"));
        }
        if (map1.get("shouldAutomaticallyCloseAtEnd") != null) {
            shouldAutomaticallyCloseAtEnd = Boolean.valueOf(map1.get("shouldAutomaticallyCloseAtEnd"));
        }

        return true;
    }

    @Override
    protected void loadWithSdkInitialized(@NonNull final Activity activity, @NonNull Map<String, Object> map, @NonNull Map<String, String> map1) throws Exception {

        /** before loading */
        SuperAwesome.getInstance().setConfigurationProduction();
        SuperAwesome.getInstance().setApplicationContext(activity);
        SuperAwesome.getInstance().setTestMode(isTestEnabled);

        /** load and show the ad */
        SALoader loader = new SALoader();
        loader.loadAd(placementId, new SALoaderListener() {
            @Override
            public void didLoadAd(SAAd ad) {

                /** call this */
                onRewardedVideoLoadSuccess(SuperAwesomeRewardedVideoCustomEvent.class, moPubId);

                /** show video activity */
                SAVideoActivity video = new SAVideoActivity(activity);
                video.setAd(ad);
                video.setIsParentalGateEnabled(isParentalGateEnabled);
                video.setShouldAutomaticallyCloseAtEnd(shouldAutomaticallyCloseAtEnd);
                video.setShouldShowCloseButton(shouldShowCloseButton);

                video.setAdListener(new SAAdListener() {
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

                video.setVideoAdListener(new SAVideoAdListener() {
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

                /** play the ad */
                video.play();
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
        return true;
    }

    @Override
    protected void showVideo() {

    }
}
