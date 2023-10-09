package tv.superawesome.sdk.publisher.ad

import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.models.SAInterface

/**
 * Manages ad loading and retrieval.
 */
interface AdManager {

    /**
     * Listener for ad loading events, should be set before calling [load].
     */
    var listener: SAInterface?

    /**
     * The ad configuration.
     */
    val adConfig: AdConfig

    /**
     * Loads an ad with just [placementId].
     *
     * @param placementId placementId of the ad to be loaded.
     * @param adRequest the configured and built [AdRequest].
     */
    suspend fun load(
        placementId: Int,
        adRequest: AdRequest,
    )

    /**
     * Loads an ad with all 3 ids.
     *
     * @param placementId of the ad to be loaded.
     * @param lineItemId of the ad to be loaded.
     * @param creativeId of the ad to be loaded.
     * @param adRequest the configured and built [AdRequest].
     */
    suspend fun load(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        adRequest: AdRequest,
    )

    /**
     * Returns whether there is an ad already loaded with the given [placementId].
     */
    fun hasAdAvailable(placementId: Int): Boolean

    /**
     * Removes the created [AdController] of a loaded ad with the given [placementId].
     */
    fun removeController(placementId: Int)

    /**
     * Clears the entire cache, removing all ads.
     */
    fun clearCache()
}
