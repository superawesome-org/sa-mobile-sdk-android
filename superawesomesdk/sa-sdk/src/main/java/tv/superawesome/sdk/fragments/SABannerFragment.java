package tv.superawesome.sdk.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tv.superawesome.sdk.R;
import tv.superawesome.sdk.views.SABannerView;

/**
 * Created by connor.leigh-smith on 14/07/15.
 */
public class SABannerFragment extends SAFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragmentId = R.layout.fragment_sa_banner;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called.");
        if (this.rootView == null) {
            // Inflate the layout for this fragment
            this.rootView = inflater.inflate(fragmentId, container, false);
            this.placementView = (SABannerView)rootView.findViewById(R.id.ad_container);
            this.placementView.setPlacementID(this.placementID);
            this.placementView.setParentalGateEnabled(true);
            this.placementView.setTestMode(this.testMode);
            if (this.listener != null) {
                this.placementView.setListener(this.listener);
                this.listener = null;
            }
            if (this.showInstantly) {
                this.placementView.loadAd();
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
        this.placementView.paused();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called.");
        this.placementView.resumed();
    }
}
