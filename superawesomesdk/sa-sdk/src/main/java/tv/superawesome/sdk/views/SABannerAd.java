package tv.superawesome.sdk.views;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import tv.superawesome.lib.sanetwork.SAApplication;
import tv.superawesome.lib.sanetwork.SASender;
import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.lib.sawebview.SAWebView;
import tv.superawesome.lib.sawebview.SAWebViewListener;
import tv.superawesome.sdk.R;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.data.Models.SACreativeFormat;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;

/**
 * Created by gabriel.coman on 30/12/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SABannerAd extends Fragment implements SAWebViewListener {

    /** Private variables */
    private boolean isParentalGateEnabled = true; /** init with default value */
    private SAAd ad; /** private ad */

    /** Subviews */
    private SAWebView webView;
    private ImageView padlock;

    /** listeners */
    private SAAdListener adListener;
    private SAParentalGateListener parentalGateListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sa_banner, container, false);

        // create / assign the subviews
        webView = (SAWebView)v.findViewById(R.id.web_view);
        webView.setListener(this);

        padlock = (ImageView)v.findViewById(R.id.padlock_image);

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    /**
     * This function is used to set the ad reference to a new, loaded Ad
     * @param _ad
     */
    public void setAd(SAAd _ad) {
        this.ad = _ad;
    }

    /**
     * This function sets the ad listener
     * @param adListener
     */
    public void setAdListener(SAAdListener adListener) {
        this.adListener = adListener;
    }

    /**
     * And this one the parental gate listener
     * @param parentalGateListener
     */
    public void setParentalGateListener (SAParentalGateListener parentalGateListener) {
        this.parentalGateListener = parentalGateListener;
    }

    /**
     * And this one for the parental gate
     * @param isParentalGateEnabled
     */
    public void setIsParentalGateEnabled (boolean isParentalGateEnabled) {
        this.isParentalGateEnabled = isParentalGateEnabled;
    }

    /**
     * Main play() function that inits the whole ad
     */
    public void play() {
        /** check to see for the correct placement type */
        if (ad.creative.format == SACreativeFormat.video) {
            if (adListener != null) {
                adListener.adHasIncorrectPlacement(ad.placementId);
            }

            return;
        }

        /** make the padlock visible or not */
        if (ad.isFallback) {
            padlock.setVisibility(View.GONE);
        } else {
            padlock.setVisibility(View.VISIBLE);
        }

        /** load the HTML with the custom SAWebView */
        webView.loadHTML(ad.adHTML);
    }

    /** <SAWebViewListener> */

    @Override
    public void saWebViewWillNavigate(String url) {

        /** call listener */
        if (adListener != null) {
            adListener.adWasClicked(ad.placementId);
        }

        if (!ad.creative.isFullClickURLReliable){
            ad.creative.fullClickURL = url;
        }

        /** check for PG */
        if (isParentalGateEnabled) {
            SAParentalGate gate = new SAParentalGate(getActivity(), ad);
            gate.show();
            gate.setListener(parentalGateListener);
        } else {
            if (!ad.creative.isFullClickURLReliable) {
                SASender.sendEventToURL(ad.creative.trackingURL);
            }

            /** go-to-url */
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ad.creative.fullClickURL));
            startActivity(browserIntent);
        }
    }

    @Override
    public void saWebViewDidLoad() {
        /** send event */
        SASender.sendEventToURL(ad.creative.viewableImpressionURL);

        /** call listener */
        if (adListener != null){
            adListener.adWasShown(ad.placementId);
        }
    }

    @Override
    public void saWebViewDidFail() {
        /** call listener */
        if (adListener != null) {
            adListener.adFailedToShow(ad.placementId);
        }
    }
}
