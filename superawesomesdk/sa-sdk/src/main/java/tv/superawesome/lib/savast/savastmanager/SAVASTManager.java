package tv.superawesome.lib.savast.savastmanager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tv.superawesome.lib.sanetwork.SASender;
import tv.superawesome.lib.sanetwork.SAURLUtils;
import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.lib.savast.savastparser.SAVASTParser;
import tv.superawesome.lib.savast.savastparser.SAVASTParserListener;
import tv.superawesome.lib.savast.savastparser.models.SAVASTAd;
import tv.superawesome.lib.savast.savastparser.models.SAVASTImpression;
import tv.superawesome.lib.savast.savastparser.models.SAVASTLinearCreative;
import tv.superawesome.lib.savast.savastparser.models.SAVASTTracking;
import tv.superawesome.lib.savast.savastplayer.SAVASTPlayer;
import tv.superawesome.lib.savast.savastplayer.SAVASTPlayerListener;

/**
 * Created by gabriel.coman on 23/12/15.
 */
public class SAVASTManager implements SAVASTParserListener, SAVASTPlayerListener{

    // private variables
    private SAVASTParser parser;
    private SAVASTPlayer refPlayer;
    private SAVASTManagerListener listener;

    // other private variables needed to play ads
    private List<SAVASTAd> adQueue;

    private int currentAdIndex;
    private int currentCreativeIndex;

    // references to current ad and current creative
    private SAVASTAd _cAd;
    private SAVASTLinearCreative _cCreative;

    /**
     * Constructor
     * @param refPlayer
     * @param listener
     */
    public SAVASTManager(SAVASTPlayer refPlayer, SAVASTManagerListener listener) {

        this.refPlayer = refPlayer;
        this.listener = listener;

        // create parser
        parser = new SAVASTParser();

        // set the listner
        if (this.refPlayer != null) {
            this.refPlayer.setListener(this);
        }
    }

    /**
     * Main Manager public function
     * @param url
     */
    public void parseVASTURL(String url) {
        parser.execute(url, this);
    }

    /** <SAVASTParserListener> */

    @Override
    public void didParseVASTAndHasResponse(List<SAVASTAd> ads) {

        // give ref to adQueue from the returned ads list
        adQueue = ads;

        // set the ad queue playhead
        currentAdIndex = 0;
        currentCreativeIndex = -1;

        // get current ad
        _cAd = adQueue.get(currentAdIndex);

        // call listener
        if (listener != null){
            listener.didParseVASTAndFindAds();
            listener.didStartAd();
        }

        // progress through ads
        this.progressThroughAds();

        SALog.Log("End of parsing: Ads[" + ads.size() + "]");
        for (Iterator<SAVASTAd> i = ads.iterator(); i.hasNext(); ){
            SAVASTAd ad = i.next();
            ad.print();
        }
    }

    @Override
    public void didNotFindAnyValidAds() {
        SALog.Log("Did not find any valid ads");

        if (listener != null){
            listener.didParseVASTButDidNotFindAnyAds();
        }
    }

    @Override
    public void didFindInvalidVASTResponse() {
        SALog.Log("Did find invalid ads");

        if (listener != null) {
            listener.didNotParseVAST();
        }
    }

    /** <SAVASTPlayerListener> */

    @Override
    public void didFindPlayerReady() {
        SALog.Log("didFindPlayerReady");

        // send impressions
        for (Iterator<SAVASTImpression> i = _cAd.Impressions.iterator(); i.hasNext(); ){
            SAVASTImpression impression = i.next();
            if (!impression.isSent) {
                impression.isSent = true;
                SASender.sendEventToURL(impression.URL);
            }
        }
    }

    @Override
    public void didStartPlayer() {
        SALog.Log("didStartPlayer");

        // send events
        sendCurrentCreativeTrackersFor("start");
        sendCurrentCreativeTrackersFor("creativeView");

        // call listner
        if (listener != null){
            listener.didStartCreative();
        }
    }

    @Override
    public void didReachFirstQuartile() {
        SALog.Log("didReachFirstQuartile");

        // send events
        sendCurrentCreativeTrackersFor("firstQuartile");

        // call listener
        if (listener != null) {
            listener.didReachFirstQuartileOfCreative();
        }
    }

    @Override
    public void didReachMidpoint() {
        SALog.Log("didReachMidpoint");

        // send events
        sendCurrentCreativeTrackersFor("midpoint");

        // call listener
        if (listener != null){
            listener.didReachMidpointOfCreative();
        }
    }

    @Override
    public void didReachThirdQuartile() {
        SALog.Log("didReachThirdQuartile");

        // send events
        sendCurrentCreativeTrackersFor("thirdQuartile");

        // call listener
        if (listener != null) {
            listener.didReachThirdQuartileOfCreative();
        }
    }

    @Override
    public void didReachEnd() {
        SALog.Log("didReachEnd");

        // send events
        sendCurrentCreativeTrackersFor("complete");

        // call listener
        if (listener != null){
            listener.didEndOfCreative();
        }

        // call to continue
        this.progressThroughAds();
    }

    @Override
    public void didPlayWithError() {
        SALog.Log("didPlayWithError");

        // if a creative is played with error, send events to the error tag
        // and advance to the next ad
        for (Iterator<String> i = _cAd.Errors.iterator(); i.hasNext(); ){
            String error = i.next();
            SASender.sendEventToURL(error);
        }

        // call to listner
        if (listener != null){
            listener.didHaveErrorForCreative();
        }

        // call to continue to progress
        this.progressThroughAds();
    }

    @Override
    public void didGoToURL() {
        SALog.Log("didGoToURL");

        // send event to URL
        for (Iterator<String> i = _cCreative.ClickTracking.iterator(); i.hasNext(); ){
            String clickTracking = i.next();
            SASender.sendEventToURL(clickTracking);
        }

        // setup the current click URL
        String url = "";
        if (_cCreative.ClickThrough != null && SAURLUtils.isValidURL(_cCreative.ClickThrough)){
            url = _cCreative.ClickThrough;
        }
        // if no click through is there - just go through the ClickTracking URLs and
        // maybe one is good
        else {
            for (Iterator<String> i = _cCreative.ClickTracking.iterator(); i.hasNext(); ){
                if (SAURLUtils.isValidURL(i.next())){
                    url = i.next();
                    break;
                }
            }
        }

        // call listner
        if (listener != null){
            listener.didGoToURL(url);
        }
    }

    /** Other needed functions */

    private void progressThroughAds() {
        int creativeCount = adQueue.get(currentAdIndex).Creatives.size();

        if (currentCreativeIndex < creativeCount - 1){
            // select current
            currentCreativeIndex++;
            _cCreative = (SAVASTLinearCreative)_cAd.Creatives.get(currentCreativeIndex);

            SALog.Log("Playing Ad " + currentAdIndex + " - Creative " + currentCreativeIndex);

            // play
            playCurrentAdWithCurrentCreative();
        } else  {
            // call listener
            if (listener != null){
                listener.didEndAd();
            }

            if (currentAdIndex < adQueue.size() - 1){
                // select current
                currentCreativeIndex = 0;
                currentAdIndex++;

                _cAd = adQueue.get(currentAdIndex);
                _cCreative = (SAVASTLinearCreative)_cAd.Creatives.get(currentCreativeIndex);

                // call listener again
                if (listener != null) {
                    listener.didStartAd();
                }

                SALog.Log("Playing Ad " + currentAdIndex + " - Creative " + currentCreativeIndex);

                // play
                playCurrentAdWithCurrentCreative();
            } else  {
                SALog.Log("REACHED THE FINAL END");

                if (listener != null){
                    listener.didEndAllAds();
                }
            }
        }
    }

    private void playCurrentAdWithCurrentCreative() {
        // get URL
        String url = _cCreative.MediaFiles.get(0).URL;

        // play
        if (refPlayer != null){
            refPlayer.setListener(this);
            refPlayer.playWithMediaURL(url);
        }
    }

    /** Aux functions */
    private void sendCurrentCreativeTrackersFor(String event) {
        // get all tracking events for this "event" string
        for (Iterator<SAVASTTracking> i = _cCreative.TrackingEvents.iterator(); i.hasNext(); ){
            SAVASTTracking tracking = i.next();
            if (tracking.event.equals(event)){
                // send event
                SASender.sendEventToURL(tracking.URL);
            }
        }
    }
}
