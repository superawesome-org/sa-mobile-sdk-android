package tv.superawesome.sdk.publisher.models

/**
 * A callback interface to receive events from the SDK.
 *
 * It is typically used to receive information about
 * the status of ad requests, ad loading, and ad display.
 * */
public fun interface SAInterface {
    /**
     * Only method that needs to be implemented to be able to receive events back from the SDK.
     *
     * @param placementId   the Ad Placement Id the event happened for
     * @param event         the type of the event, as an SAEvent enum variable
     */
    public fun onEvent(placementId: Int, event: SAEvent)
}
