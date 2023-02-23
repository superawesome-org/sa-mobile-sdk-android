package tv.superawesome.plugins.publisher.admob

import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest

object SAAdMobError {
    fun adFailedToShow(placementId: Int): AdError = AdError(
        AdRequest.ERROR_CODE_INTERNAL_ERROR,
        "Ad failed to show for $placementId",
        ""
    )

    fun adFailedToLoad(placementId: Int): AdError = AdError(
        AdRequest.ERROR_CODE_NO_FILL,
        "Ad failed to load for $placementId",
        ""
    )

    fun adFailedToLoad(message: String): AdError = AdError(
        AdRequest.ERROR_CODE_NO_FILL,
        message,
        ""
    )
}