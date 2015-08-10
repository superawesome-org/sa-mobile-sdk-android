// Copyright 2014 Google Inc. All Rights Reserved.
package tv.superawesome.superawesomesdk.views.video;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.google.ads.interactivemedia.v3.api.AdDisplayContainer;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent;
import com.google.ads.interactivemedia.v3.api.AdEvent;
import com.google.ads.interactivemedia.v3.api.AdsLoader;
import com.google.ads.interactivemedia.v3.api.AdsManager;
import com.google.ads.interactivemedia.v3.api.AdsManagerLoadedEvent;
import com.google.ads.interactivemedia.v3.api.AdsRequest;
import com.google.ads.interactivemedia.v3.api.ImaSdkFactory;

/**
 * Ads logic for handling the IMA SDK integration code and events.
 */
public class VideoPlayerController implements AdErrorEvent.AdErrorListener,
        AdsLoader.AdsLoadedListener, AdEvent.AdEventListener {

    // Container with references to video player and ad UI ViewGroup.
    private AdDisplayContainer mAdDisplayContainer;

    // The AdsLoader instance exposes the requestAds method.
    private AdsLoader mAdsLoader;

    // AdsManager exposes methods to control ad playback and listen to ad events.
    private AdsManager mAdsManager;

    // Factory class for creating SDK objects.
    private ImaSdkFactory mSdkFactory;

    // SAAd-enabled video player.
    private VideoPlayerWithAdPlayback mVideoPlayerWithAdPlayback;

    // Listener for some events
    SAVideoViewListener listener;

    // Default VAST ad tag; more complex apps might select ad tag based on content video criteria.
    private String vastContent;

    public VideoPlayerController(Context context, VideoPlayer videoPlayer,
                                 ViewGroup adUiContainer, String vastContent) {
        mVideoPlayerWithAdPlayback = new VideoPlayerWithAdPlayback(videoPlayer, adUiContainer);
        mVideoPlayerWithAdPlayback.init();
        this.vastContent = vastContent;

        // Create an AdsLoader.
        mSdkFactory = ImaSdkFactory.getInstance();
        mAdsLoader = mSdkFactory.createAdsLoader(context);
        mAdsLoader.addAdErrorListener(this);
        mAdsLoader.addAdsLoadedListener(this);
    }

    /**
     * Request video ads from the given VAST ad tag.
     */
    private void requestAds(String tag) {
        mAdDisplayContainer = mSdkFactory.createAdDisplayContainer();
        mAdDisplayContainer.setPlayer(mVideoPlayerWithAdPlayback.getVideoAdPlayer());
        mAdDisplayContainer.setAdContainer(mVideoPlayerWithAdPlayback.getAdUiContainer());

        // Create the ads request.
        AdsRequest request = mSdkFactory.createAdsRequest();

        //Swap these out to use the vast content instead of the hardcoded URL below.
//        request.setAdTagUrl("http://pubads.g.doubleclick.net/gampad/ads?sz=640x360&iu=/6062/iab_vast_samples/skippable&ciu_szs=300x250,728x90&impl=s&gdfp_req=1&env=vp&output=xml_vast3&unviewed_position_start=1&url=[referrer_url]&correlator=[timestamp]");
//        request.setAdTagUrl("http://ad3.liverail.com/?LR_PUBLISHER_ID=1331&LR_CAMPAIGN_ID=229&LR_SCHEMA=vast2");
//        request.setAdTagUrl("https://beta.ads.superawesome.tv/v2/video/vast/5740/-1/-1/");
        request.setAdTagUrl(tag);


        request.setAdDisplayContainer(mAdDisplayContainer);
        request.setContentProgressProvider(mVideoPlayerWithAdPlayback.getContentProgressProvider());


        // Request the ad. After the ad is loaded, onAdsManagerLoaded() will be called.
        mAdsLoader.requestAds(request);
    }

    /**
     * An event raised when ads are successfully loaded from the ad server via an AdsLoader.
     */
    @Override
    public void onAdsManagerLoaded(AdsManagerLoadedEvent adsManagerLoadedEvent) {
        // Ads were successfully loaded, so get the AdsManager instance. AdsManager has
        // events for ad playback and errors.
        mAdsManager = adsManagerLoadedEvent.getAdsManager();

        // Attach event and error event listeners.
        mAdsManager.addAdErrorListener(this);
        mAdsManager.addAdEventListener(this);
        mAdsManager.init();
    }

    public void setListener(SAVideoViewListener listener) {
        this.listener = listener;
    }


    /**
     * Responds to AdEvents.
     */
    @Override
    public void onAdEvent(AdEvent adEvent) {
        Log.i("Google IMA", "Event: " + adEvent.getType());

        // These are the suggested event types to handle. For full list of all ad event types,
        // see the documentation for AdEvent.AdEventType.
        switch (adEvent.getType()) {
            case CLICKED:
                Log.d("CONTROLLER", "AD CLICKED");
                break;
            case STARTED:
                if (listener != null) listener.onAdStart();
                break;
            case FIRST_QUARTILE:
                if (listener != null) listener.onAdFirstQuartile();
                break;
            case MIDPOINT:
                if (listener != null) listener.onAdMidpoint();
                break;
            case THIRD_QUARTILE:
                if (listener != null) listener.onAdThirdQuartile();
                break;
            case LOADED:
                // AdEventType.LOADED will be fired when ads are ready to be played.
                // AdsManager.start() begins ad playback. This method is ignored for VMAP or ad
                // rules playlists, as the SDK will automatically start executing the playlist.
                mAdsManager.start();
                if (listener != null) listener.onAdLoaded(null);
                break;
            case CONTENT_PAUSE_REQUESTED:
                // AdEventType.CONTENT_PAUSE_REQUESTED is fired immediately before a video ad is
                // played.
                mVideoPlayerWithAdPlayback.pauseContentForAdPlayback();
                if (listener != null) listener.onAdPause();
                break;
            case CONTENT_RESUME_REQUESTED:
                // AdEventType.CONTENT_RESUME_REQUESTED is fired when the ad is completed.
                if (listener != null) listener.onAdResume();
                break;
            case ALL_ADS_COMPLETED:
                if (mAdsManager != null) {
                    mAdsManager.destroy();
                    mAdsManager = null;
                }
                if (listener != null) listener.onAdComplete();
                break;
            default:
                break;
        }
    }

    /**
     * An event raised when there is an error loading or playing ads.
     */
    @Override
    public void onAdError(AdErrorEvent adErrorEvent) {
        Log.e("ImaExample", "SAAd Error: " + adErrorEvent.getError().getMessage());
    }


    /**
     * Starts ad playback.
     */
    public void play(String tag) {
        requestAds(tag);
    }

    /**
     * Resumes video playback.
     */
    public void resume() {
        mVideoPlayerWithAdPlayback.restorePosition();
        if (mAdsManager != null && mVideoPlayerWithAdPlayback.getIsAdDisplayed()) {
            mAdsManager.resume();
        }
    }

    /**
     * Pauses video playback.
     */
    public void pause() {
        mVideoPlayerWithAdPlayback.savePosition();
        if (mAdsManager != null && mVideoPlayerWithAdPlayback.getIsAdDisplayed()) {
            mAdsManager.pause();
        }
    }
}