package tv.superawesome.superawesomesdk.view;

import tv.superawesome.superawesomesdk.Ad;

/**
 * Created by connor.leigh-smith on 03/07/15.
 */
public interface PlacementViewListener {
    void onAdLoaded(Ad ad);
    void onAdError(String message);
}
