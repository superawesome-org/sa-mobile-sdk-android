package tv.superawesome.lib.savast.savastplayer;

/**
 * Created by gabriel.coman on 23/12/15.
 */
public interface SAVASTPlayerListener {

    void didFindPlayerReady();
    void didStartPlayer();
    void didReachFirstQuartile();
    void didReachMidpoint();
    void didReachThirdQuartile();
    void didReachEnd();
    void didPlayWithError();
    void didGoToURL(String url);
}
