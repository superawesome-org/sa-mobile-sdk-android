package tv.superawesome.sdk.publisher.models

/**
 * This enum holds all the possible callback values that an ad sends during its lifetime.
 */
@Suppress("EnumEntryName", "EnumNaming")
public enum class SAEvent {
    /** Ad was loaded successfully and is ready to be displayed. */
    adLoaded,

    /** Ad was empty. */
    adEmpty,

    /** Ad was not loaded successfully and will not be able to play. */
    adFailedToLoad,

    /** Ad was previously loaded in an interstitial, video or app wall queue. */
    adAlreadyLoaded,

    /** Triggered once when the ad first displays. */
    adShown,

    /** Ad is failed to show for an unknown reason. */
    adFailedToShow,

    /** Triggered every time the ad gets clicked. */
    adClicked,

    /**
     * Triggered once when the video ad is displayed until the end.
     * Also `adEnded` can be used for rewarding the user.
     */
    adEnded,

    /** Triggered once when the ad is closed .*/
    adClosed,

    /** Triggered once when the video ad is paused. */
    adPaused,

    /** Triggered once when the video ad is playing or resumes. */
    adPlaying,
}
