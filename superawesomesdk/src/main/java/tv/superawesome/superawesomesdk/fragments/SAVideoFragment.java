package tv.superawesome.superawesomesdk.fragments;

/**
 * Created by connor.leigh-smith on 22/07/15.
 */

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;

import tv.superawesome.superawesomesdk.R;
import tv.superawesome.superawesomesdk.models.SAAd;
import tv.superawesome.superawesomesdk.views.SAPlacementListener;
import tv.superawesome.superawesomesdk.views.video.SAVideoView;
import tv.superawesome.superawesomesdk.views.video.SAVideoViewListener;

/**
 * Created by connor.leigh-smith on 14/07/15.
 */
public class SAVideoFragment extends SAFragment {

    protected SAVideoViewListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragmentId = R.layout.fragment_sa_video;
    }

    @Override
    public void setListener(SAPlacementListener viewListener) {
        this.listener = (SAVideoViewListener)viewListener;

        if (this.placementView != null) {
            this.placementView.setListener(new SAPlacementListener() {

                @Override
                public void onAdLoaded(SAAd superAwesomeAd) {
                    if (listener != null) listener.onAdLoaded(superAwesomeAd);
                }

                @Override
                public void onAdError(String message) {
                    if (listener != null) listener.onAdError(message);
                }
            });
            ((SAVideoView)this.placementView).setVideoListener(new SAVideoViewListener() {
                @Override
                public void onAdStart() {
                    Log.d(TAG, "SAVideoFragment onAdStart");
                    if (listener != null) listener.onAdStart();
                }

                @Override
                public void onAdPause() {
                    Log.d(TAG, "SAVideoFragment onAdPause");
                    if (listener != null) listener.onAdPause();
                }

                @Override
                public void onAdResume() {
                    if (listener != null) listener.onAdResume();
                }

                @Override
                public void onAdFirstQuartile() {
                    Log.d(TAG, "SAVideoFragment onAdFQ");
                    if (listener != null) listener.onAdFirstQuartile();
                }

                @Override
                public void onAdMidpoint() {
                    if (listener != null) listener.onAdMidpoint();
                }

                @Override
                public void onAdThirdQuartile() {
                    if (listener != null) listener.onAdThirdQuartile();
                }

                @Override
                public void onAdComplete() {
                    Log.d(TAG, "COMPLETE TEST");
                    if (listener != null) listener.onAdComplete();
                    ((ViewGroup)placementView.getParent()).removeView(placementView);
                    placementView.setVisibility(View.GONE);
                }

                @Override
                public void onAdClosed() {
                    if (listener != null) listener.onAdClosed();
                }

                @Override
                public void onAdSkipped() {
                    if (listener != null) listener.onAdSkipped();
                }

                @Override
                public void onAdLoaded(SAAd superAwesomeAd) {
                }

                @Override
                public void onAdError(String message) {
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called.");
        if (this.rootView == null) {
            // Inflate the layout for this fragment
            this.rootView = inflater.inflate(fragmentId, container, false);
            this.placementView = (SAVideoView)rootView.findViewById(R.id.ad_container);
            this.placementView.setPlacementID(this.placementID);
            this.placementView.setTestMode(this.testMode);
            this.placementView.setListener(this.listener);

            if (this.showInstantly) {
                this.placementView.loadAd();
                ((SAVideoView)this.placementView).show();
            }
        } else {
            ((ViewGroup) this.rootView.getParent()).removeView(this.rootView);
        }
        return this.rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called.");
        if (placementView != null) {
            placementView.paused();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called.");
        if (placementView != null) {
            placementView.resumed();
        }
    }
}
