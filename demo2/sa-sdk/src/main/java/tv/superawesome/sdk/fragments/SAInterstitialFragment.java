package tv.superawesome.sdk.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tv.superawesome.sdk.R;
import tv.superawesome.sdk.views.SAInterstitialView;

/**
 * Created by connor.leigh-smith on 14/07/15.
 */
public class SAInterstitialFragment extends SAFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragmentId = R.layout.fragment_sa_interstitial;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called.");
        //Comment this line to stop interstitial from reloading on display rotate etc
        this.rootView = null;
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(fragmentId, container, false);
        this.placementView = (SAInterstitialView)rootView.findViewById(R.id.ad_container);
        this.placementView.setPlacementID(this.placementID);
        this.placementView.setTestMode(this.testMode);
        this.placementView.loadAd();
        if (this.showInstantly || ((SAInterstitialView)this.placementView).isDisplay()) {
            Log.d(TAG, "Showing interstitial...");
            ((SAInterstitialView)this.placementView).show();
        }
        this.showInstantly = false;
        return this.rootView;
    }

    public void show() {
        ((SAInterstitialView)this.placementView).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called.");
//        ((SAInterstitialView) this.rootView.findViewById(R.id.ad_container)).resumed();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called.");
        if (((SAInterstitialView)this.placementView).isDisplay()) {
            this.showInstantly = true;
        }
//        ((SAInterstitialView) this.rootView.findViewById(R.id.ad_container)).paused();
    }
}
