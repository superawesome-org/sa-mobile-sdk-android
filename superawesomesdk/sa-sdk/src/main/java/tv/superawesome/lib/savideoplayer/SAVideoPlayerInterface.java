package tv.superawesome.lib.savideoplayer;

/**
 * Public interface
 */
public interface SAVideoPlayerInterface {

    /**
     * called when the player is read to play
     */
    void didFindPlayerReady();

    /**
     * called when the player actually starts playing
     */
    void didStartPlayer();

    /**
     * called when the player reaches 1/4 of duration
     */
    void didReachFirstQuartile();

    /**
     * called when the player reaches 1/2 of duration
     */
    void didReachMidpoint();

    /**
     * called when the player reaches 3/4 of duration
     */
    void didReachThirdQuartile();

    /**
     * called when the player reaches end
     */
    void didReachEnd();

    /**
     * called when the player plays with error
     */
    void didPlayWithError();

    /**
     * called when the player clicks on the clicker
     */
    void didGoToURL();
    void didClickOnClose();
}
