/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.sdk.views;

/**
 * This enum holds all the possible callback values that an ad sends during its lifetime
 *  - adLoaded:         ad was loaded successfully and is ready to be displayed
 *  - adFailedToLoad:   ad was not loaded successfully and will not be able to play
 *  - adShown:          triggered once when the ad first displays
 *  - adFailedToShow:   for some reason the ad failed to show; technically this should
 *                      never happen nowadays
 *  - adClicked:        triggered every time the ad gets clicked
 *  - adClosed:         triggered once when the ad is closed;
 */
public enum SAEvent {
    adLoaded,
    adFailedToLoad,
    adShown,
    adFailedToShow,
    adClicked,
    adClosed
}
