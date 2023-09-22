package tv.superawesome.sdk.publisher.ui

import tv.superawesome.sdk.publisher.models.SAInterface

/**
 * Describes the functions of a view that display ads.
 * It allows the consumer to load and play ads as well as configure
 * how the ad is played.
 */
@Suppress("TooManyFunctions", "ComplexInterface")
public interface AdView {

    /**
     * Loads a new SAAd object corresponding to a given placement Id.
     *
     * @param placementId Awesome Ads ID for ad data to be loaded
     * @param openRtbPartnerId OpenRTB Partner ID parameter to be sent with all requests.
     * @param options: an optional dictionary of data to send with an ad's requests and events.
     * Supports String or Int values.
     */
    public fun load(
        placementId: Int,
        openRtbPartnerId: String? = null,
        options: Map<String, Any>? = null,
    )

    /**
     * Loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId the Ad placement id to load data for
     * @param lineItemId
     * @param creativeId id of the Creative
     * @param openRtbPartnerId OpenRTB Partner ID parameter to be sent with all requests.
     * @param options: an optional dictionary of data to send with an ad's requests and events.
     * Supports String or Int values.
     */
    public fun load(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        openRtbPartnerId: String? = null,
        options: Map<String, Any>? = null,
    )

    /**
     * Plays an already existing loaded ad, or fail.
     */
    public fun play()

    /**
     * Registers a callback to be called for certain events.
     *
     * @param delegate the callback delegate.
     */
    public fun setListener(delegate: SAInterface)

    /**
     * Gets called in order to close the banner ad, remove any fragments, etc.
     */
    public fun close()

    /**
     * Returns whether an ad is available.
     */
    public fun hasAdAvailable(): Boolean

    /**
     * Returns whether the ad is closed.
     */
    public fun isClosed(): Boolean

    /**
     * Enables parental gate.
     */
    public fun enableParentalGate() {
        setParentalGate(true)
    }

    /**
     * Disables parental gate.
     */
    public fun disableParentalGate() {
        setParentalGate(false)
    }

    /**
     * Enables bumper page.
     */
    public fun enableBumperPage() {
        setBumperPage(true)
    }

    /**
     * Disables bumper page.
     */
    public fun disableBumperPage() {
        setBumperPage(false)
    }

    /**
     * Enables test mode.
     */
    public fun enableTestMode() {
        setTestMode(true)
    }

    /**
     * Disables test mode.
     */
    public fun disableTestMode() {
        setTestMode(false)
    }

    /**
     * Sets transparent color background.
     */
    public fun setColorTransparent() {
        setColor(true)
    }

    /**
     * Sets gray color background.
     */
    public fun setColorGray() {
        setColor(false)
    }

    /**
     * Sets parental gate enabled.
     */
    public fun setParentalGate(value: Boolean)

    /**
     * Sets bumper page enabled.
     */
    public fun setBumperPage(value: Boolean)

    /**
     * Sets the test mode.
     */
    public fun setTestMode(value: Boolean)

    /**
     * Sets the transparency of the banner.
     *
     * @param value `true` makes the banner transparent, `false` makes it gray.
     */
    public fun setColor(value: Boolean)
}
