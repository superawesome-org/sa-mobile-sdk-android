package tv.superawesome.sdk.views;

import tv.superawesome.sdk.models.SAAd;

/**
 * Created by connor.leigh-smith on 03/07/15.
 */
public interface SAPlacementListener {
    void onAdLoaded(SAAd superAwesomeAd);
    void onAdError(String message);
}
