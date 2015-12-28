package com.mopub.mobileads;

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
public class SuperAwesomeRewardedVideoCustomEvent extends CustomEventRewardedVideo implements SAAdListener, SAParentalGateListener, SAVideoAdListener{
    /** private vars */
    private int placementId;
    private boolean testMode;
    private boolean isParentalGateEnabled;
    private boolean closeButtonAppears;
    private String moPubId;

    /** make this the listener */

//     public MoPubRewardedVideoListener listener;

    /** other listeners */
    private SAAdListener adListener = this;
    private SAParentalGateListener parentalGateListener = this;
    private SAVideoAdListener videoAdListener = this;

    @Nullable
    @Override
    protected CustomEventRewardedVideoListener getVideoListenerForSdk() {
        SALog.Log("CustomEventRewardedVideoListener ");
        return null;
    }

    @Nullable
    @Override
    protected LifecycleListener getLifecycleListener() {
        SALog.Log("getLifecycleListener");
        return null;
    }

    @NonNull
    @Override
    protected String getAdNetworkId() {
        SALog.Log("getAdNetworkId");
        return moPubId;
    }

    @Override
    protected void onInvalidate() {
        SALog.Log("onInvalidate");
        return;
    }

    @Override
    protected boolean checkAndInitializeSdk(@NonNull Activity activity, @NonNull Map<String, Object> map, @NonNull Map<String, String> map1) throws Exception {

        /** get map variables */
        placementId = 0;
        testMode = true;
        isParentalGateEnabled = true;
        closeButtonAppears = false;

        /** get datas */
        if (map.get("com_mopub_ad_unit_id") != null){
            moPubId = (String)map.get("com_mopub_ad_unit_id").toString();
        }
        if (map1.get("placementId") != null ){
            placementId = Integer.parseInt((String)map1.get("placementId").toString());
        }
        if (map1.get("testMode") != null) {
            testMode = Boolean.valueOf(map1.get("testMode"));
        }
        if (map1.get("parentalGateEnabled") != null){
            isParentalGateEnabled = Boolean.valueOf(map1.get("parentalGateEnabled"));
        }
        if (map1.get("closeButtonAppears") != null){
            closeButtonAppears = Boolean.valueOf(map1.get("closeButtonAppears"));
        }

        SALog.Log("Placement: " + placementId + " testMode: " + testMode + " isPG: " + isParentalGateEnabled);

        return true;
    }

    @Override
    protected void loadWithSdkInitialized(@NonNull final Activity activity, @NonNull Map<String, Object> map, @NonNull Map<String, String> map1) throws Exception {
        SALog.Log("loadWithSdkInitialized");

        /** before loading */
        SuperAwesome.getInstance().setConfigurationProduction();
        if (testMode) {
            SuperAwesome.getInstance().enableTestMode();
        } else {
            SuperAwesome.getInstance().disableTestMode();
        }

        /** load and show the ad */
        SALoader.loadAd(placementId, new SALoaderListener() {
            @Override
            public void didLoadAd(SAAd ad) {

//                onRewardedVideoLoadSuccess(SuperAwesomeRewardedVideoCustomEvent.getClass(), moPubId);


                // show ad interstitial
                SAVideoActivity.start(activity, ad, false, adListener, parentalGateListener, videoAdListener);
            }

            @Override
            public void didFailToLoadAdForPlacementId(int placementId) {
                SALog.Log("Failed to load " + placementId);

//                onRewardedVideoPlaybackError(SuperAwesomeRewardedVideoCustomEvent.getClass(), moPubId, MoPubErrorCode.VIDEO_NOT_AVAILABLE);
            }
        });

        return;
    }

    @Override
    protected boolean hasVideoAvailable() {
        SALog.Log("hasVideoAvailable");
        return true;
    }

    @Override
    protected void showVideo() {
        SALog.Log("Show video");
    }

    /** <SAAdListener> */

    @Override
    public void adWasShown(int i) {
        onRewardedVideoLoadSuccess(this.getClass(), moPubId);
    }

    @Override
    public void adFailedToShow(int i) {

        onRewardedVideoPlaybackError(this.getClass(), moPubId, MoPubErrorCode.VIDEO_NOT_AVAILABLE);
    }

    @Override
    public void adWasClosed(int i) {
        onRewardedVideoClosed(this.getClass(), moPubId);
    }

    @Override
    public void adWasClicked(int i) {
        onRewardedVideoClicked(this.getClass(), moPubId);
    }

    @Override
    public void adHasIncorrectPlacement(int i) {

    }

    /** <SAParentalGateListener> */

    @Override
    public void parentalGateWasCanceled(int i) {
        // n/a
    }

    @Override
    public void parentalGateWasFailed(int i) {
        // n/a
    }

    @Override
    public void parentalGateWasSucceded(int i) {
        // n/a
    }

    /** <SAVideoAdListener> */

    @Override
    public void adStarted(int i) {

    }

    @Override
    public void videoStarted(int i) {
        onRewardedVideoStarted(this.getClass(), moPubId);
    }

    @Override
    public void videoReachedFirstQuartile(int i) {

    }

    @Override
    public void videoReachedMidpoint(int i) {

    }

    @Override
    public void videoReachedThirdQuartile(int i) {

    }

    @Override
    public void videoEnded(int i) {

    }

    @Override
    public void adEnded(int i) {

    }

    @Override
    public void allAdsEnded(int i) {

        MoPubReward reward = MoPubReward.success(MoPubReward.NO_REWARD_LABEL, 0);

        onRewardedVideoCompleted(this.getClass(), moPubId, reward);
    }
}
