package tv.superawesome.sdk.publisher.ad

import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.SAInterface

/**
 * Factory for [AdController]s.
 */
interface AdControllerFactory {

    /**
     * Makes an [AdController].
     *
     * @param adResponse loaded ad.
     * @param adConfig ad configuration.
     * @param listener the set listener, if any.
     */
    fun make(
        adResponse: AdResponse,
        adConfig: AdConfig,
        listener: SAInterface?,
    ): AdController
}
