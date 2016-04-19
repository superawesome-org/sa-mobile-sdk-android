package tv.superawesome.lib.savast;

import android.util.Log;

import java.util.Iterator;
import java.util.List;

import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.savast.models.SAVASTAd;
import tv.superawesome.lib.savast.models.SAVASTImpression;
import tv.superawesome.lib.savast.models.SAVASTLinearCreative;
import tv.superawesome.lib.savast.models.SAVASTTracking;
import tv.superawesome.lib.savideoplayer.SAVideoPlayer;

/**
 * Created by gabriel.coman on 23/12/15.
 */
public class SAVASTManager implements SAVASTParser.SAVASTParserListener, SAVideoPlayer.SAVideoPlayerListener {

    /** private variables */
    private SAVASTParser parser;
    private SAVideoPlayer refPlayer;
    private SAVASTManagerListener listener;

    /** other private variables needed to play ads */
    private List<SAVASTAd> adQueue;

    private int currentAdIndex;
    private int currentCreativeIndex;

    /** references to current ad and current creative */
    private SAVASTAd _cAd;
    private SAVASTLinearCreative _cCreative;

    /**
     * Constructor
     * @param refPlayer
     * @param listener
     */
    public SAVASTManager(SAVideoPlayer refPlayer, SAVASTManagerListener listener) {

        this.refPlayer = refPlayer;
        this.listener = listener;

        /** create parser */
        parser = new SAVASTParser();

        /** set the listener */
        if (this.refPlayer != null) {
            this.refPlayer.listener = this;
        }
    }

    /**
     * Main Manager public function
     * @param url
     */
    public void parseVASTURL(String url) {
        parser.execute(url, this);
    }

    public void manageWithAds(List<SAVASTAd> ads) {
        /** error checking */
        if (ads.size() < 1) {
            if (listener != null) {
                listener.didNotFindAds();
            }
            return;
        }

        /** give ref to adQueue from the returned ads list  */
        adQueue = ads;

        /** set the ad queue play head */
        currentAdIndex = 0;
        currentCreativeIndex = -1;

        /** get current ad */
        _cAd = adQueue.get(currentAdIndex);

        /** call listener */
        if (listener != null){
            listener.didStartAd();
        }

        /** progress through ads */
        this.progressThroughAds();
    }

    /** <SAVASTParserListener> */

    @Override
    public void didParseVAST(List<SAVASTAd> ads) {
        manageWithAds(ads);
    }

    /** <SAVideoPlayerListener> */

    @Override
    public void didFindPlayerReady() {
        Log.d("SuperAwesome", "didFindPlayerReady");

        /** send impressions  */
        for (Iterator<SAVASTImpression> i = _cAd.Impressions.iterator(); i.hasNext(); ){
            SAVASTImpression impression = i.next();
            if (!impression.isSent) {
                impression.isSent = true;
                SAEvents.sendEventToURL(impression.URL);
            }
        }
    }

    @Override
    public void didStartPlayer() {
        Log.d("SuperAwesome", "didStartPlayer");

        /** send events */
        sendCurrentCreativeTrackersFor("start");
        sendCurrentCreativeTrackersFor("creativeView");

        /** call listener  */
        if (listener != null){
            listener.didStartCreative();
        }
    }

    @Override
    public void didReachFirstQuartile() {
        Log.d("SuperAwesome", "didReachFirstQuartile");

        /** send events */
        sendCurrentCreativeTrackersFor("firstQuartile");

        /** call listener */
        if (listener != null) {
            listener.didReachFirstQuartileOfCreative();
        }
    }

    @Override
    public void didReachMidpoint() {
        Log.d("SuperAwesome", "didReachMidpoint");

        /** send events */
        sendCurrentCreativeTrackersFor("midpoint");

        /** call listener */
        if (listener != null){
            listener.didReachMidpointOfCreative();
        }
    }

    @Override
    public void didReachThirdQuartile() {
        Log.d("SuperAwesome", "didReachThirdQuartile");

        /** send events */
        sendCurrentCreativeTrackersFor("thirdQuartile");

        /** call listener */
        if (listener != null) {
            listener.didReachThirdQuartileOfCreative();
        }
    }

    @Override
    public void didReachEnd() {
        Log.d("SuperAwesome", "didReachEnd");

        /** send events */
        sendCurrentCreativeTrackersFor("complete");

        /** call listener */
        if (listener != null){
            listener.didEndOfCreative();
        }

        /** call to continue */
        this.progressThroughAds();
    }

    @Override
    public void didPlayWithError() {
        Log.d("SuperAwesome", "didPlayWithError");

        /**
         * if a creative is played with error, send events to the error tag
         * and advance to the next ad
         */
        if (_cAd != null) {
            for (String error : _cAd.Errors) {
                SAEvents.sendEventToURL(error);
            }
        }

        /** call to listener */
        if (listener != null){
            listener.didHaveErrorForCreative();
        }

        /** call to continue to progress */
        // this.progressThroughAds();
    }

    @Override
    public void didGoToURL() {
        Log.d("SuperAwesome", "didGoToURL");

        /** setup the current click URL */
        String url = "";
        if (_cCreative.ClickThrough != null && SAUtils.isValidURL(_cCreative.ClickThrough)){
            url = _cCreative.ClickThrough;
        }

        /** call listener */
        if (listener != null){
            listener.didGoToURL(url, _cCreative.ClickTracking);
        }
    }

    @Override
    public void didClickOnClose() {
        if (listener != null) {
            listener.didClickOnClose();
        }
    }

    /** Other needed functions */

    private void progressThroughAds() {
        int creativeCount = adQueue.get(currentAdIndex).Creatives.size();

        if (currentCreativeIndex < creativeCount - 1){
            /** select current */
            currentCreativeIndex++;
            _cCreative = (SAVASTLinearCreative)_cAd.Creatives.get(currentCreativeIndex);

            /** play */
            playCurrentAdWithCurrentCreative();
        } else  {
            /** call listener */
            if (listener != null){
                listener.didEndAd();
            }

            if (currentAdIndex < adQueue.size() - 1){
                /** select current */
                currentCreativeIndex = 0;
                currentAdIndex++;

                _cAd = adQueue.get(currentAdIndex);
                _cCreative = (SAVASTLinearCreative)_cAd.Creatives.get(currentCreativeIndex);

                /** call listener again */
                if (listener != null) {
                    listener.didStartAd();
                }

                /** play */
                playCurrentAdWithCurrentCreative();
            } else  {
                if (listener != null){
                    listener.didEndAllAds();
                }
            }
        }
    }

    private void playCurrentAdWithCurrentCreative() {
        /** play */
        if (refPlayer != null) {
            refPlayer.listener = this;
            if (_cCreative.isOnDisk) {
                Log.d("SuperAwesome", "Taking file from disk " + _cCreative.playableDiskURL);
                refPlayer.playWithDiskURL(_cCreative.playableDiskURL);
            } else {
                Log.d("SuperAwesome", "Taking file from remote " + _cCreative.playableMediaURL);
                refPlayer.playWithMediaURL(_cCreative.playableMediaURL);
            }
        }
    }

    /** Aux functions */
    private void sendCurrentCreativeTrackersFor(String event) {
        /** get all tracking events for this "event" string */
        for (Iterator<SAVASTTracking> i = _cCreative.TrackingEvents.iterator(); i.hasNext(); ){
            SAVASTTracking tracking = i.next();
            if (tracking.event.equals(event)){
                /** send event */
                SAEvents.sendEventToURL(tracking.URL);
            }
        }
    }

    /**
     * ************************************************************
     * Public interface
     * ************************************************************
     */
    public interface SAVASTManagerListener {

        /**
         * parsed vast and found errors / could not parse vast
         */
        void didNotFindAds();

        /**
         * ad started
         */
        void didStartAd();

        /**
         * creative / video started
         */
        void didStartCreative();

        /**
         * video 1/4
         */
        void didReachFirstQuartileOfCreative();

        /**
         * video 1/2
         */
        void didReachMidpointOfCreative();

        /**
         * video 3/4
         */
        void didReachThirdQuartileOfCreative();

        /**
         * video full
         */
        void didEndOfCreative();

        /**
         * video played with errors
         */
        void didHaveErrorForCreative();

        /**
         * ad ended
         */
        void didEndAd();

        /**
         * all ads ended
         */
        void didEndAllAds();

        /**
         * ad was clicked
         * @param url the URL to go to
         * @param clickTracking additoonal click tracking URLs
         */
        void didGoToURL(String url, List<String> clickTracking);

        /**
         * ad was closed
         */
        void didClickOnClose();
    }
}
