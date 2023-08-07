package tv.superawesome.sdk.publisher.common.models

/**
 * This enum holds all the possible callback values that an ad sends during its lifetime.
 */
@Suppress("EnumEntryName", "EnumNaming")
enum class SAEvent {
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

/**
 * A callback interface to receive events from the SDK.
 *
 * It is typically used to receive information about
 * the status of ad requests, ad loading, and ad display.
 * */
fun interface SAInterface {
    /**
     * Only method that needs to be implemented to be able to receive events back from the SDK.
     *
     * @param placementId   the Ad Placement Id the event happened for
     * @param event         the type of the event, as an SAEvent enum variable
     */
    fun onEvent(placementId: Int, event: SAEvent)
}
