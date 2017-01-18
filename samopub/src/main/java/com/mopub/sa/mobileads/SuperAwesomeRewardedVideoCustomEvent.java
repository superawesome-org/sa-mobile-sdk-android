/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package com.mopub.sa.mobileads;

import android.app.Activity;
import android.content.Context;

import com.mopub.common.LifecycleListener;
import com.mopub.common.MoPubReward;
import com.mopub.mobileads.CustomEventRewardedVideo;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAOrientation;
import tv.superawesome.sdk.views.SAVideoAd;

import static com.mopub.mobileads.MoPubRewardedVideoManager.onRewardedVideoClicked;
import static com.mopub.mobileads.MoPubRewardedVideoManager.onRewardedVideoClosed;
import static com.mopub.mobileads.MoPubRewardedVideoManager.onRewardedVideoCompleted;
import static com.mopub.mobileads.MoPubRewardedVideoManager.onRewardedVideoLoadFailure;
import static com.mopub.mobileads.MoPubRewardedVideoManager.onRewardedVideoLoadSuccess;
import static com.mopub.mobileads.MoPubRewardedVideoManager.onRewardedVideoPlaybackError;
import static com.mopub.mobileads.MoPubRewardedVideoManager.onRewardedVideoStarted;

/**
 * Class that extends the MoPub standard CustomEventRewardedVideo class in order to
 * communicate with MoPub and load a video ad
 */
public class SuperAwesomeRewardedVideoCustomEvent extends CustomEventRewardedVideo {

    // constants representing MoPub JSON keys to look for values in
    private static final String KEY_MoPub                           = "com_mopub_ad_unit_id";
    private static final String KEY_placementId                     = "placementId";
    private static final String KEY_isTestEnabled                   = "isTestEnabled";
    private static final String KEY_isParentalGateEnabled           = "isParentalGateEnabled";
    private static final String KEY_shouldShowCloseButton           = "shouldShowCloseButton";
    private static final String KEY_shouldAutomaticallyCloseAtEnd   = "shouldAutomaticallyCloseAtEnd";
    private static final String KEY_shouldShowSmallClickButton      = "shouldShowSmallClickButton";
    private static final String KEY_lockOrientation                 = "lockOrientation";
    private static final String KEY_orientation                     = "orientation";

    // private state vars
    private String moPubId;
    private int placementId;
    private boolean isTestEnabled;
    private boolean isParentalGateEnabled;
    private boolean shouldShowCloseButton;
    private boolean shouldAutomaticallyCloseAtEnd;
    private boolean shouldShowSmallClickButton;
    private SAOrientation orientation;

    // context
    private Context context;

    /**
     * Overridden "getVideoListenerForSdk" method of CustomEventRewardedVideo
     *
     * @return nulls
     */
    @Override
    protected CustomEventRewardedVideoListener getVideoListenerForSdk() {
        return null;
    }

    /**
     * Overridden "LifecycleListener" method of CustomEventRewardedVideo
     *
     * @return nulls
     */
    @Override
    protected LifecycleListener getLifecycleListener() {
        return null;
    }

    /**
     * Overridden "getAdNetworkId" method of CustomEventRewardedVideo
     *
     * @return moPubId
     */
    @Override
    protected String getAdNetworkId() {
        return moPubId;
    }

    /**
     * Overridden "onInvalidate" method of CustomEventRewardedVideo
     *
     */
    @Override
    protected void onInvalidate() {
        return;
    }

    /**
     * Overridden "checkAndInitializeSdk" method of CustomEventRewardedVideo that will initialize
     * a video ad from AA SDK
     *
     * @param activity      current Activity
     * @param map           values passed down from MoPub
     * @param map1          values passed down from MoPub
     * @return              whether the operation was successfull
     * @throws Exception    an exception
     */
    @Override
    protected boolean checkAndInitializeSdk(Activity activity, Map<String, Object> map, Map<String, String> map1) throws Exception {

        // get map variables
        placementId = SuperAwesome.getInstance().defaultPlacementId();
        isTestEnabled = SuperAwesome.getInstance().defaultTestMode();
        isParentalGateEnabled = SuperAwesome.getInstance().defaultParentalGate();
        shouldShowCloseButton = SuperAwesome.getInstance().defaultCloseButton();
        shouldAutomaticallyCloseAtEnd = SuperAwesome.getInstance().defaultCloseAtEnd();
        shouldShowSmallClickButton = SuperAwesome.getInstance().defaultSmallClick();
        orientation = SuperAwesome.getInstance().defaultOrientation();

        // get data
        if (map.containsKey(KEY_MoPub)) {
            try {
                moPubId = map.get(KEY_MoPub).toString();
            } catch (Exception e) {
                // do nothing
            }
        }
        if (map1.containsKey(KEY_placementId)) {
            try {
                placementId = Integer.parseInt(map1.get(KEY_placementId));
            } catch (Exception e) {
                // do nothing
            }
        }
        if (map1.containsKey(KEY_isTestEnabled)) {
            try {
                isTestEnabled = Boolean.valueOf(map1.get(KEY_isTestEnabled));
            } catch (Exception e) {
                // do nothing
            }
        }
        if (map1.containsKey(KEY_isParentalGateEnabled)) {
            try {
                isParentalGateEnabled = Boolean.valueOf(map1.get(KEY_isParentalGateEnabled));
            } catch (Exception e) {
                // do nothing
            }
        }
        if (map1.containsKey(KEY_shouldShowCloseButton)) {
            try {
                shouldShowCloseButton = Boolean.valueOf(map1.get(KEY_shouldShowCloseButton));
            } catch (Exception e) {
                // do nothing
            }
        }
        if (map1.containsKey(KEY_shouldAutomaticallyCloseAtEnd)) {
            try {
                shouldAutomaticallyCloseAtEnd = Boolean.valueOf(map1.get(KEY_shouldAutomaticallyCloseAtEnd));
            } catch (Exception e) {
                // do nothing
            }
        }
        if (map1.containsKey(KEY_shouldShowSmallClickButton)) {
            try {
                shouldShowSmallClickButton = Boolean.valueOf(map1.get(KEY_shouldShowSmallClickButton));
            } catch (Exception e) {
                // do nothing
            }
        }
        if (map1.containsKey(KEY_lockOrientation)) {
            try {
                String stringOrientation = map1.get(KEY_lockOrientation);
                if (stringOrientation != null && stringOrientation.equals("PORTRAIT")) {
                    orientation = SAOrientation.PORTRAIT;
                } else if (stringOrientation != null && stringOrientation.equals("LANDSCAPE")){
                    orientation = SAOrientation.LANDSCAPE;
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        if (map1.containsKey(KEY_orientation)) {
            try {
                String stringOrientation = map1.get(KEY_orientation);
                if (stringOrientation != null && stringOrientation.equals("PORTRAIT")) {
                    orientation = SAOrientation.PORTRAIT;
                } else if (stringOrientation != null && stringOrientation.equals("LANDSCAPE")){
                    orientation = SAOrientation.LANDSCAPE;
                }
            } catch (Exception e) {
                // do nothing
            }
        }

        return true;
    }

    /**
     * Overridden "loadWithSdkInitialized" method of CustomEventRewardedVideo that will load
     * a video ad from AA SDK
     *
     * @param activity      current Activity
     * @param map           values passed down from MoPub
     * @param map1          values passed down from MoPub
     * @throws Exception    an exception
     */
    @Override
    protected void loadWithSdkInitialized(final Activity activity, Map<String, Object> map, Map<String, String> map1) throws Exception {

        // get context
        this.context = activity;

        // configure the ad
        SAVideoAd.setConfigurationProduction();
        SAVideoAd.setTestMode(isTestEnabled);
        SAVideoAd.setParentalGate(isParentalGateEnabled);
        SAVideoAd.setCloseAtEnd(shouldAutomaticallyCloseAtEnd);
        SAVideoAd.setCloseButton(shouldShowCloseButton);
        SAVideoAd.setSmallClick(shouldShowSmallClickButton);
        SAVideoAd.setOrientation(orientation);
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
        // load the ad
        SAVideoAd.load(placementId, context);
    }

    /**
     * Overridden "hasVideoAvailable" method of CustomEventRewardedVideo that will check if
     * ad data is available in SA SDK
     *
     * @return true or false
     */
    @Override
    protected boolean hasVideoAvailable() {
        return SAVideoAd.hasAdAvailable(placementId);
    }

    /**
     * Overridden "showVideo" method of CustomEventRewardedVideo that will actually
     * start playing the video, if it has data
     *
     */
    @Override
    protected void showVideo() {
        SAVideoAd.play(placementId, context);
    }
}
