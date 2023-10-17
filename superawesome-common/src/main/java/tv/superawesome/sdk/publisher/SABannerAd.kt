@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.ui.AdView
import tv.superawesome.sdk.publisher.ui.banner.InternalBannerView

/**
 * View that shows banner ads.
 */
@Suppress("TooManyFunctions")
public class SABannerAd @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), AdView {

    private val view = InternalBannerView(context, attrs, defStyleAttr)

    init {
        addView(view)
        view.visibility = VISIBLE
    }

    public override fun load(
        placementId: Int,
        openRtbPartnerId: String?,
        options: Map<String, Any>?,
    ) {
        view.load(placementId, openRtbPartnerId, options)
    }

    /**
     * Loads a new SAAd object corresponding to a given placement Id.
     *
     * @param placementId Awesome Ads ID for ad data to be loaded
     */
    public fun load(placementId: Int) {
        load(placementId, options = null, openRtbPartnerId = null)
    }

    public override fun load(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        openRtbPartnerId: String?,
        options: Map<String, Any>?,
    ) {
        view.load(placementId, lineItemId, creativeId, openRtbPartnerId, options)
    }

    /**
     * Loads an ad into the interstitial queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId the Ad placement id to load data for
     * @param lineItemId
     * @param creativeId id of the Creative
     */
    public fun load(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
    ) {
        load(placementId, lineItemId, creativeId, options = null, openRtbPartnerId = null)
    }

    /**
     * Plays an already existing loaded ad, or fail.
     */
    public override fun play() {
        view.play()
    }

    @Deprecated(
        message = "Use the new play function, this will be removed in next version",
        replaceWith = ReplaceWith("play()"),
        level = DeprecationLevel.WARNING,
    )
    @Suppress("UndocumentedPublicFunction")
    public fun play(context: Context) {
        play()
    }

    /**
     * Registers a callback to be called for certain events.
     *
     * @param delegate the callback delegate.
     */
    public override fun setListener(delegate: SAInterface) {
        view.setListener(delegate)
    }

    /**
     * Gets called in order to close the banner ad, remove any fragments, etc.
     */
    public override fun close() {
        view.close()
    }

    /**
     * Returns whether an ad is available.
     */
    public override fun hasAdAvailable(): Boolean = view.hasAdAvailable()

    /**
     * Returns whether the ad is closed.
     */
    public override fun isClosed(): Boolean = view.isClosed()

    /**
     * Sets parental gate enabled.
     */
    public override fun setParentalGate(value: Boolean) {
        view.setParentalGate(value)
    }

    /**
     * Sets bumper page enabled.
     */
    public override fun setBumperPage(value: Boolean) {
        view.setBumperPage(value)
    }

    /**
     * Sets the test mode.
     */
    public override fun setTestMode(value: Boolean) {
        view.setTestMode(value)
    }

    /**
     * Sets the transparency of the banner.
     *
     * @param value `true` makes the banner transparent, `false` makes it gray.
     */
    public override fun setColor(value: Boolean) {
       view.setColor(value)
    }

    @Deprecated(
        message = "Kept for compatibility, it has no function and it will be removed in the next version",
        level = DeprecationLevel.WARNING,
    )
    @Suppress("UndocumentedPublicFunction")
    public fun setConfiguration(value: SAConfiguration) {
        Log.w(
            "SABannerAd",
            "Function deprecated, please set the configuration via AwesomeAds.init()"
        )
    }

    @Deprecated(
        message = "Kept for compatibility, it has no function and it will be removed in the next version",
        level = DeprecationLevel.WARNING,
    )
    @Suppress("UndocumentedPublicFunction")
    public fun setBannerListener(value: SAConfiguration) {
        Log.w(
            "SABannerAd",
            "Function deprecated, please set listener using setListener(SAInterface)"
        )
    }

    @Deprecated(
        message = "Kept for compatibility, it has no function and it will be removed in the next version",
        level = DeprecationLevel.WARNING,
    )
    internal interface SABannerAdListener {
        fun hasBeenVisible()
        fun failedToShow()
    }
}
