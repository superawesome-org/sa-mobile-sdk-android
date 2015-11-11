package tv.superawesome.sdk.views.video;

import tv.superawesome.sdk.views.SAPlacementListener;

/**
 * Created by connor.leigh-smith on 10/07/15.
 */
public interface SAVideoViewListener extends SAPlacementListener {

    void onAdClick();

    void onAdStart();

    void onAdPause();

    void onAdResume();

    void onAdFirstQuartile();

    void onAdMidpoint();

    void onAdThirdQuartile();

    void onAdComplete();

    void onAdClosed();

    void onAdSkipped();
}
