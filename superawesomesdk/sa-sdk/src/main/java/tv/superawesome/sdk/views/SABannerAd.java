package tv.superawesome.sdk.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.sawebview.SAWebPlayer;
import tv.superawesome.lib.sawebview.SAWebPlayerInterface;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.models.SACreativeFormat;


/**
 * Created by gabriel.coman on 30/12/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SABannerAd extends RelativeLayout implements SAWebPlayerInterface, SAViewInterface {

    /** Private variables */
    private boolean isParentalGateEnabled = true;
    private SAAd ad; /** private ad */

    /** Subviews */
    private RelativeLayout contentHolder;
    private SAWebPlayer webView;
    private ImageView padlock;
    private SAParentalGate gate;

    /** listeners */
    private SAAdInterface adListener;
    private SAParentalGateInterface parentalGateListener;

    /** helper vars */
    private float cWidth = 0;
    private float cHeight = 0;
    private boolean layoutOK = false;
    private String destinationURL = null;
    private boolean showOnce = false;

    /**********************************************************************************************/
    /** Init methods */
    /**********************************************************************************************/

    public SABannerAd(Context context) {
        this(context, null, 0);
    }

    public SABannerAd(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SABannerAd(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /** get ids */
        String packageName = context.getPackageName();
        int view_sa_bannerId = getResources().getIdentifier("view_sa_banner", "layout", packageName);
        int content_holderId = getResources().getIdentifier("content_holder", "id", packageName);
        int web_viewId = getResources().getIdentifier("web_view", "id", packageName);
        int padlockId = getResources().getIdentifier("padlock_image", "id", packageName);

        /** create / assign the subviews */
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(view_sa_bannerId, this);

        contentHolder = (RelativeLayout)findViewById(content_holderId);
        webView = (SAWebPlayer)findViewById(web_viewId);
        webView.setListener(this);
        padlock = (ImageView)findViewById(padlockId);

        /** set padlock size vs. screen display */
        this.setBackgroundColor(Color.rgb(191, 191, 191));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        cWidth = getWidth();
        cHeight = getHeight();

        if (cWidth != 0 && cHeight != 0 && !layoutOK){
            layoutOK = true;
        }
    }

    /**********************************************************************************************/
    /** <SAWebViewListener> */
    /**********************************************************************************************/

    @Override
    public void saWebViewWillNavigate(String url) {
        /** update the destination URL */
        destinationURL = url;

        /** check for PG */
        if (isParentalGateEnabled) {
            /** send event */
            SAEvents.sendEventToURL(ad.creative.parentalGateClickUrl);
            /** create pg */
            gate = new SAParentalGate(getContext(), this, ad);
            gate.show();
            gate.setListener(parentalGateListener);
        } else {
            advanceToClick();
        }
    }

    @Override
    public void saWebViewDidLoad() {
        if (!showOnce) {
            showOnce = true;

            /** send event viewable impression */
            SAEvents.sendEventToURL(ad.creative.viewableImpressionUrl);

            /** send moat */
            SAEvents.sendDisplayMoatEvent((Activity) this.getContext(), this, ad);

            /** send impression URL, if exits, to 3rd party only */
            if (ad.creative.impressionUrl != null && !ad.creative.impressionUrl.contains(SuperAwesome.getInstance().getBaseURL())) {
                SAEvents.sendEventToURL(ad.creative.impressionUrl);
            }

            /** call listener */
            if (adListener != null) {
                adListener.adWasShown(ad.placementId);
            }
        }
    }

    @Override
    public void saWebViewDidFail() {
        /** call listener */
        if (adListener != null) {
            adListener.adFailedToShow(ad.placementId);
        }
    }

    /**********************************************************************************************/
    /** <SAViewInterface> */
    /**********************************************************************************************/

    /**
     * This function is used to set the ad reference to a new, loaded Ad
     * @param _ad
     */
    @Override
    public void setAd(SAAd _ad) {
        this.ad = _ad;
    }

    /**
     * Functions from SANavigation interface
     */
    @Override
    public SAAd getAd() {
        return this.ad;
    }

    /**
     * Main play() function that inits the whole ad
     */
    @Override
    public void play() {
        /** check to see for the correct placement type */
        if (ad.creative.creativeFormat == SACreativeFormat.video) {
            if (adListener != null) {
                adListener.adHasIncorrectPlacement(ad.placementId);
            }

            return;
        }

        /** if ad is still not OK - wait a little while longer */
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                /** resize the views */
                resizeToSize((int) cWidth, (int) cHeight);

                /** make the padlock visible or not */
                if (ad.isFallback || ad.isHouse) {
                    padlock.setVisibility(View.GONE);
                } else {
                    padlock.setVisibility(View.VISIBLE);
                }
            }
        };

        /**
         * if the ad is ok (and implicitly the cWidth and cHeight params then start arranging the
         * ad as we should!
         */
        if (ad == null || !layoutOK) {
            this.postDelayed(runnable, 250);
        } else {
            post(runnable);
        }
    }

    @Override
    public void close() {
        /** do nothing here */
    }

    @Override
    public void advanceToClick() {
        /** call listener */
        if (adListener != null) {
            adListener.adWasClicked(ad.placementId);
        }

        if (!destinationURL.contains(SuperAwesome.getInstance().getBaseURL())) {
            Log.d("SuperAwesome", "Trying to send to " + ad.creative.trackingUrl);
            SAEvents.sendEventToURL(ad.creative.trackingUrl);
        }

        Log.d("SuperAwesome", "Going to " + destinationURL);

        /** go-to-url */
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(destinationURL));
        getContext().startActivity(browserIntent);
    }

    @Override
    public void resizeToSize(int width, int height){
        /** get ad width */
        int adWidth = ad.creative.details.width,
            adHeight = ad.creative.details.height;

        /** calc new frame */
        Rect newFrame = SAUtils.mapOldSizeIntoNewSize(width, height, adWidth, adHeight);
        int newWidth = newFrame.right,
            newHeight = newFrame.bottom;

        /**
         * create new layout for content Holder - this must happen ***before*** the parent view
         * (SABannerAd) triggers the onLayout callback
         */
        android.widget.RelativeLayout.LayoutParams params = new LayoutParams(newWidth, newHeight);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        contentHolder.setLayoutParams(params);

        /** update HTML as well */
        webView.loadHTML(ad.creative.details.data.adHtml, adWidth, adHeight, newWidth, newHeight);
    }

    /**********************************************************************************************/
    /** setters for listeners */
    /**********************************************************************************************/

    /**
     * This function sets the ad listener
     * @param adListener
     */
    public void setAdListener(SAAdInterface adListener) {
        this.adListener = adListener;
    }

    /**
     * This function sets the parental gate listener
     * @param parentalGateListener
     */
    public void setParentalGateListener(SAParentalGateInterface parentalGateListener){
        this.parentalGateListener = parentalGateListener;
    }

    /**
     * And this one for the parental gate
     * @param isParentalGateEnabled
     */
    public void setIsParentalGateEnabled (boolean isParentalGateEnabled) {
        this.isParentalGateEnabled = isParentalGateEnabled;
    }
}
