package tv.superawesome.sdk.publisher.ad

import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.models.SAInterface

interface AdManager {

    var listener: SAInterface?

    val adConfig: AdConfig

    suspend fun load(
        placementId: Int,
        adRequest: AdRequest,
    )

    suspend fun load(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        adRequest: AdRequest,
    )

    fun hasAdAvailable(placementId: Int): Boolean

    fun getController(placementId: Int): NewAdController

    fun removeController(placementId: Int)
}
