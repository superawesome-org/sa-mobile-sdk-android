package tv.superawesome.sdk.publisher.common.models

/**
 * This enum holds all the possible callback values that an ad sends during its lifetime
 *
 *  - adLoaded:         ad was loaded successfully and is ready to be displayed
 *  - adEmpty:          ad was empty
 *  - adFailedToLoad:   ad was not loaded successfully and will not be able to play
 *  - adAlreadyLoaded   ad was previously loaded in an interstitial, video or app wall queue
 *  - adShown:          triggered once when the ad first displays
 *  - adFailedToShow:   for some reason the ad failed to show; technically this should
 *                      never happen nowadays
 *  - adClicked:        triggered every time the ad gets clicked
 *  - adClosed:         triggered once when the ad is closed
 *  - adPaused:         triggered when the ad is paused
 *  - adPlaying:        triggered when the ad is playing or resumes
 */
@Suppress("EnumEntryName")
enum class SAEvent {
    /** ad was loaded successfully and is ready to be displayed */
    adLoaded,

    /** ad was empty */
    adEmpty,

    /** ad was not loaded successfully and will not be able to play */
    adFailedToLoad,

    /** ad was previously loaded in an interstitial, video or app wall queue */
    adAlreadyLoaded,

    /** triggered once when the ad first displays */
    adShown,

    /** Ad is failed to show for an unknown reason */
    adFailedToShow,

    /**  triggered every time the ad gets clicked */
    adClicked,

    /**  triggered once when the video ad is displayed until the end
     * Also `adEnded` can be used for rewarding the user
     * */
    adEnded,

    /** triggered once when the ad is closed */
    adClosed,

    /** triggered once when the video ad is paused */
    adPaused,

    /** triggered once when the video ad is playing or resumes */
    adPlaying,
}

/**
 * A callback interface to receive events from the SDK
 *
 * It is typically used to receive information about
 * the status of ad requests, ad loading, and ad display
 * */
fun interface SAInterface {
    /**
     * Only method that needs to be implemented to be able to receive events back from the SDK
     *
     * @param placementId   the Ad Placement Id the event happened for
     * @param event         the type of the event, as an SAEvent enum variable
     */
    fun onEvent(placementId: Int, event: SAEvent)
}
