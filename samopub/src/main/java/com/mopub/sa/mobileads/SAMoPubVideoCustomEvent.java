/**
 * @Copyright: SADefaults Trading Limited 2017
 * @Author: Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package com.mopub.sa.mobileads;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.mopub.common.LifecycleListener;
import com.mopub.common.MoPubReward;
import com.mopub.mobileads.CustomEventRewardedVideo;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.lib.sasession.defines.SARTBStartDelay;
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
    private SARTBStartDelay playback;

    // context
    private Context context;

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
     * @param localExtras           values passed down from MoPub
     * @param serverExtras          values passed down from MoPub
     * @return whether the operation was successfull
     * @throws Exception    an exception
     */
    @Override
    protected boolean checkAndInitializeSdk(Activity activity, Map<String, Object> localExtras, Map<String, String> serverExtras) throws Exception {

        try {
            moPubId = localExtras.get(SAMoPub.kAD_UNIT).toString();
        } catch (Exception e) {
            // do nothing
        }

        try {
            placementId = Integer.parseInt(serverExtras.get(SAMoPub.kPLACEMENT_ID));
        } catch (Exception e) {
            placementId = SADefaults.defaultPlacementId();
        }

        try {
            isTestEnabled = Boolean.valueOf(serverExtras.get(SAMoPub.kTEST_ENABLED));
        } catch (Exception e) {
            isTestEnabled = SADefaults.defaultTestMode();
        }

        try {
            isParentalGateEnabled = Boolean.valueOf(serverExtras.get(SAMoPub.kPARENTAL_GATE));
        } catch (Exception e) {
            isParentalGateEnabled = SADefaults.defaultParentalGate();
        }

        try {
            isBumperPageEnabled = Boolean.valueOf(serverExtras.get(SAMoPub.kBUMPER_PAGE));
        } catch (Exception e) {
            isBumperPageEnabled = SADefaults.defaultBumperPage();
        }

        configuration = SADefaults.defaultConfiguration();
        try {
            String config = serverExtras.get(SAMoPub.kCONFIGURATION);
            if (config != null && config.equals("STAGING")) {
                configuration = SAConfiguration.STAGING;
            }
        } catch (Exception e) {
            // do nothing
        }

        orientation = SADefaults.defaultOrientation();
        try {
            String orient = serverExtras.get(SAMoPub.kORIENTATION);
            if (orient != null && orient.equals("PORTRAIT")) {
                orientation = SAOrientation.PORTRAIT;
            }
            if (orient != null && orient.equals("LANDSCAPE")) {
                orientation = SAOrientation.LANDSCAPE;
            }
        } catch (Exception e) {
            // do nothing
        }

        playback = SADefaults.defaultPlaybackMode();
        try {
            String play = serverExtras.get(SAMoPub.kPLAYBACK_MODE);
            if (play != null) {
                switch (play) {
                    case "POST_ROLL": {
                        playback = SARTBStartDelay.POST_ROLL;
                    }
                    case "MID_ROLL": {
                        playback = SARTBStartDelay.MID_ROLL;
                    }
                    case "PRE_ROLL": {
                        playback = SARTBStartDelay.PRE_ROLL;
                    }
                    case "GENERIC_MID_ROLL": {
                        playback = SARTBStartDelay.GENERIC_MID_ROLL;
                    }
                }
            }
        } catch (Exception e) {
            // do nothing
        }

        try {
            shouldShowCloseButton = Boolean.valueOf(serverExtras.get(SAMoPub.kSHOULD_SHOW_CLOSE));
        } catch (Exception e) {
            shouldShowCloseButton = SADefaults.defaultCloseButton();
        }

        try {
            shouldAutomaticallyCloseAtEnd = Boolean.valueOf(serverExtras.get(SAMoPub.kSHOULD_AUTO_CLOSE));
        } catch (Exception e) {
            shouldAutomaticallyCloseAtEnd = SADefaults.defaultCloseAtEnd();
        }

        try {
            shouldShowSmallClickButton = Boolean.valueOf(serverExtras.get(SAMoPub.kVIDEO_BUTTON_STYLE));
        } catch (Exception e) {
            shouldShowSmallClickButton = SADefaults.defaultCloseAtEnd();
        }

        try {
            enableBackButton = Boolean.valueOf(serverExtras.get(SAMoPub.kBACK_BUTTON));
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
     * @param localExtras           values passed down from MoPub
     * @param serverExtras          values passed down from MoPub
     * @throws Exception    an exception
     */
    @Override
    protected void loadWithSdkInitialized(@NonNull final Activity activity, @NonNull Map<String, Object> localExtras, @NonNull Map<String, String> serverExtras) throws Exception {

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
        SAVideoAd.setPlaybackMode(playback);
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
                        onRewardedVideoLoadFailure(SAMoPubVideoCustomEvent.class, moPubId, MoPubErrorCode.NETWORK_NO_FILL);
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
                    case adAlreadyLoaded:
                        break;
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
