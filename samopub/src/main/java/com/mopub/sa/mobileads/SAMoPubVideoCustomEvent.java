/**
 * @Copyright:   SADefaults Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package com.mopub.sa.mobileads;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.mopub.common.LifecycleListener;
import com.mopub.common.MoPubReward;
import com.mopub.mobileads.CustomEventRewardedVideo;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.sdk.publisher.SADefaults;
import tv.superawesome.sdk.publisher.SAEvent;
import tv.superawesome.sdk.publisher.SAInterface;
import tv.superawesome.sdk.publisher.SAOrientation;
import tv.superawesome.sdk.publisher.SAVideoAd;

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
public class SAMoPubVideoCustomEvent extends CustomEventRewardedVideo {

    // private state vars
    private String moPubId;
    private int placementId;
    private boolean isTestEnabled;
    private boolean isParentalGateEnabled;
    private boolean isBumperPageEnabled;
    private boolean shouldShowCloseButton;
    private boolean shouldAutomaticallyCloseAtEnd;
    private boolean shouldShowSmallClickButton;
    private boolean enableBackButton;
    private SAOrientation orientation;
    private SAConfiguration configuration;

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
    @NonNull
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

        try {
            moPubId = map.get(SAMoPub.kAD_UNIT).toString();
        } catch (Exception e) {
            // do nothing
        }

        try {
            placementId = Integer.parseInt(map1.get(SAMoPub.kPLACEMENT_ID));
        } catch (Exception e) {
            placementId = SADefaults.defaultPlacementId();
        }

        try {
            isTestEnabled = Boolean.valueOf(map1.get(SAMoPub.kTEST_ENABLED));
        } catch (Exception e) {
            isTestEnabled = SADefaults.defaultTestMode();
        }

        try {
            isParentalGateEnabled = Boolean.valueOf(map1.get(SAMoPub.kPARENTAL_GATE));
        } catch (Exception e) {
            isParentalGateEnabled = SADefaults.defaultParentalGate();
        }

        try {
            isBumperPageEnabled = Boolean.valueOf(map1.get(SAMoPub.kBUMPER_PAGE));
        } catch (Exception e) {
            isBumperPageEnabled = SADefaults.defaultBumperPage();
        }

        configuration = SADefaults.defaultConfiguration();
        try {
            String config = map1.get(SAMoPub.kCONFIGURATION);
            if (config != null && config.equals("STAGING")) {
                configuration = SAConfiguration.STAGING;
            }
        } catch (Exception e) {
            // do nothing
        }

        orientation = SADefaults.defaultOrientation();
        try {
            String orient = map1.get(SAMoPub.kORIENTATION);
            if (orient != null && orient.equals("PORTRAIT")) {
                orientation = SAOrientation.PORTRAIT;
            }
            if (orient != null && orient.equals("LANDSCAPE")) {
                orientation = SAOrientation.LANDSCAPE;
            }
        } catch (Exception e) {
            // do nothing
        }

        try {
            shouldShowCloseButton = Boolean.valueOf(map1.get(SAMoPub.kSHOULD_SHOW_CLOSE));
        } catch (Exception e) {
            shouldShowCloseButton = SADefaults.defaultCloseButton();
        }

        try {
            shouldAutomaticallyCloseAtEnd = Boolean.valueOf(map1.get(SAMoPub.kSHOULD_AUTO_CLOSE));
        } catch (Exception e) {
            shouldAutomaticallyCloseAtEnd = SADefaults.defaultCloseAtEnd();
        }

        try {
            shouldShowSmallClickButton = Boolean.valueOf(map1.get(SAMoPub.kVIDEO_BUTTON_STYLE));
        } catch (Exception e) {
            shouldShowSmallClickButton = SADefaults.defaultCloseAtEnd();
        }

        try {
            enableBackButton = Boolean.valueOf(map1.get(SAMoPub.kBACK_BUTTON));
        } catch (Exception e) {
            enableBackButton = SADefaults.defaultBackButton();
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
        SAVideoAd.setConfiguration(configuration);
        SAVideoAd.setTestMode(isTestEnabled);
        SAVideoAd.setParentalGate(isParentalGateEnabled);
        SAVideoAd.setBumperPage(isBumperPageEnabled);
        SAVideoAd.setCloseAtEnd(shouldAutomaticallyCloseAtEnd);
        SAVideoAd.setCloseButton(shouldShowCloseButton);
        SAVideoAd.setSmallClick(shouldShowSmallClickButton);
        SAVideoAd.setBackButton(enableBackButton);
        SAVideoAd.setOrientation(orientation);
        SAVideoAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: {
                        onRewardedVideoLoadSuccess(SAMoPubVideoCustomEvent.class, moPubId);
                        break;
                    }
                    case adEmpty:
                    case adFailedToLoad: {
                        onRewardedVideoLoadFailure(SAMoPubVideoCustomEvent.class, moPubId, MoPubErrorCode.VIDEO_NOT_AVAILABLE);
                        break;
                    }
                    case adShown: {
                        onRewardedVideoStarted(SAMoPubVideoCustomEvent.class, moPubId);
                        break;
                    }
                    case adFailedToShow: {
                        onRewardedVideoPlaybackError(SAMoPubVideoCustomEvent.class, moPubId, MoPubErrorCode.VIDEO_NOT_AVAILABLE);
                        break;
                    }
                    case adClicked: {
                        onRewardedVideoClicked(SAMoPubVideoCustomEvent.class, moPubId);
                        break;
                    }
                    case adEnded: {
                        MoPubReward reward = MoPubReward.success(MoPubReward.NO_REWARD_LABEL, 0);
                        onRewardedVideoCompleted(SAMoPubVideoCustomEvent.class, moPubId, reward);
                        break;
                    }
                    case adClosed: {
                        onRewardedVideoClosed(SAMoPubVideoCustomEvent.class, moPubId);
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
