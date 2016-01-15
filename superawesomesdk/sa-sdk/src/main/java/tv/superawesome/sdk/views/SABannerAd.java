package tv.superawesome.sdk.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import tv.superawesome.lib.sanetwork.SASender;
import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.sawebview.SAWebView;
import tv.superawesome.lib.sawebview.SAWebViewListener;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.data.Models.SACreativeFormat;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;

/**
 * Created by gabriel.coman on 30/12/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SABannerAd extends RelativeLayout implements SAWebViewListener {

    /** Private variables */
    private boolean isParentalGateEnabled = true; /** init with default value */
    private SAAd ad; /** private ad */

    /** Subviews */
    private SAWebView webView;
    private ImageView padlock;

    /** listeners */
    private SAAdListener adListener;
    private SAParentalGateListener parentalGateListener;

    /** helper vars */
    private float cWidth = 0;
    private float cHeight = 0;
    private boolean layoutOK = false;

    /** Constructors */
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
        int web_viewId = getResources().getIdentifier("web_view", "id", packageName);
        int padlockId = getResources().getIdentifier("padlock_image", "id", packageName);

        /** create / assign the subviews */
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(view_sa_bannerId, this);

        webView = (SAWebView)findViewById(web_viewId);
        webView.setListener(this);
        padlock = (ImageView)findViewById(padlockId);

        this.setBackgroundColor(Color.rgb(191, 191, 191));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        cWidth = getWidth();
        cHeight = getHeight();
        delayLayout();
    }

    private void delayLayout() {
        /**
         * if the ad is ok (and implicitly the cWidth and cHeight params then start arranging the
         * ad as we should!
         */
        if (ad != null && !layoutOK ) {
            /** from this moment on the layout is OK */
            layoutOK = true;

            /** calc the new frame */
            final Rect newframe = SAUtils.arrangeAdInNewFrame(
                    cWidth,
                    cHeight,
                    ad.creative.details.width,
                    ad.creative.details.height);
            int w = newframe.right;
            int h = newframe.bottom;

            SALog.Log("And here: " + cWidth + ", " + cHeight + " New Ad: " + w + ", " + h);

            android.widget.RelativeLayout.LayoutParams params = new LayoutParams(w, h);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            webView.setLayoutParams(params);

            /** load the HTML with the custom SAWebView */
            webView.loadHTML(
                    ad.adHTML,
                    ad.creative.details.width,
                    ad.creative.details.height,
                    w, h);

            /** make the padlock visible or not */
            if (ad.isFallback) {
                padlock.setVisibility(View.GONE);
            } else {
                padlock.setVisibility(View.VISIBLE);
            }

            return;
        }

        /** if ad is still not OK - wait a little while longer */
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                delayLayout();
            }
        };
        this.postDelayed(runnable, 250);
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
     * This function sets the parental gate listener
     * @param parentalGateListener
     */
    public void setParentalGateListener(SAParentalGateListener parentalGateListener){
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
            SAParentalGate gate = new SAParentalGate(getContext(), ad);
            gate.show();
            gate.setListener(parentalGateListener);
        } else {
            if (!ad.creative.isFullClickURLReliable) {
                SASender.sendEventToURL(ad.creative.trackingURL);
            }

            /** go-to-url */
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ad.creative.fullClickURL));
            getContext().startActivity(browserIntent);
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
