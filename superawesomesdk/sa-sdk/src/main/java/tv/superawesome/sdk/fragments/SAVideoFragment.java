package tv.superawesome.sdk.fragments;

/**
 * Created by connor.leigh-smith on 22/07/15.
 */

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tv.superawesome.sdk.R;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.views.SAPlacementListener;
import tv.superawesome.sdk.views.video.SAVideoView;
import tv.superawesome.sdk.views.video.SAVideoViewListener;

/**
 * Created by connor.leigh-smith on 14/07/15.
 */
public class SAVideoFragment extends SAFragment {

    protected SAVideoViewListener listener;
    protected boolean playInstantly;

    /**
     * Parse attributes during inflation from a view hierarchy into the
     * arguments we handle.
     */
    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        TypedArray a = activity.obtainStyledAttributes(attrs, R.styleable.SAVideoFragment);
        this.playInstantly = a.getBoolean(R.styleable.SAVideoFragment_playInstantly, true);
        a.recycle();
    }

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
                    if (listener != null) listener.onAdStart();
                }

                @Override
                public void onAdPause() {
                    if (listener != null) listener.onAdPause();
                }

                @Override
                public void onAdResume() {
                    if (listener != null) listener.onAdResume();
                }

                @Override
                public void onAdFirstQuartile() {
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
                    Log.d(TAG, "AD COMPLETE TEST");
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
        if (this.rootView == null) {


            // Inflate the layout for this fragment
            this.rootView = inflater.inflate(fragmentId, container, false);
            this.placementView = (SAVideoView)rootView.findViewById(R.id.ad_container);

            // placement Id
            if (this.getActivity().getIntent().getStringExtra("placementId") != null) {
                this.placementView.setPlacementID(this.getActivity().getIntent().getStringExtra("placementId"));
            } else {
                this.placementView.setPlacementID(this.placementID);
            }

            // test mode
            if (this.getActivity().getIntent().getStringExtra("testMode") != null ){
                if (this.getActivity().getIntent().getStringExtra("testMode").equals("true")){
                    this.placementView.setTestMode(true);
                }
                else {
                    this.placementView.setTestMode(false);
                }
            }
            else {
                ((SAVideoView) this.placementView).setTestMode(this.testMode);
            }

            // parental gate
            if (this.getActivity().getIntent().getStringExtra("isParentalGateEnabled") != null){
                if (this.getActivity().getIntent().getStringExtra("isParentalGateEnabled").equals("true")){
                    this.placementView.setParentalGateEnabled(true);
                } else {
                    this.placementView.setParentalGateEnabled(false);
                }
            }
            else {
                ((SAVideoView) this.placementView).setParentalGateEnabled(this.isParentalGateEnabled);
            }

            this.setListener(this.listener);

            if (this.showInstantly) {
                this.placementView.loadAd();
                ((SAVideoView)this.placementView).show();
            }
            if (this.playInstantly) {
                ((SAVideoView)this.placementView).setPlayInstantly(true);
            }
            this.placementView.bringToFront();
        } else {
            ((ViewGroup) this.rootView.getParent()).removeView(this.rootView);
        }
        return this.rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.d(TAG, "onPause called.");
        if (placementView != null) {
            placementView.paused();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.d(TAG, "onResume called.");
        if (placementView != null) {
            placementView.resumed();
        }
    }


}
