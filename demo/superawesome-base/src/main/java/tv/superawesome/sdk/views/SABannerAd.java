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
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONObject;

import java.util.HashMap;

import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.SACampaignType;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.sawebplayer.SAWebPlayer;
import tv.superawesome.lib.sawebplayer.SAWebPlayerInterface;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.lib.samodelspace.SAAd;
import tv.superawesome.lib.samodelspace.SACreativeFormat;

/**
 * Created by gabriel.coman on 30/12/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SABannerAd extends RelativeLayout implements SAWebPlayerInterface, SAViewInterface {

    /** constants */
    private final int DISPLAY_VIEWABILITY_COUNT = 1;

    /** Private variables */
    private boolean isParentalGateEnabled = true;
    private boolean isPartOfFullscreen = false;
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

    private int ticks = 0;
    private int viewabilityCount = 0;
    private Runnable viewabilityRunnable = null;

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
            gate = new SAParentalGate(getContext(), this, ad);
            gate.setListener(parentalGateListener);
            gate.show();
        } else {
            advanceToClick();
        }
    }

    @Override
    public void saWebViewDidLoad() {
        if (!showOnce) {
            showOnce = true;

            if (isPartOfFullscreen) {
                Log.d("SuperAwesome", "[AA :: Info] Sending viewable impression");
                SAEvents.sendEventToURL(ad.creative.viewableImpressionUrl);
            }
            else {
                viewabilityRunnable = new Runnable() {
                    @Override
                    public void run() {

                        if (ticks >= DISPLAY_VIEWABILITY_COUNT) {

                            if (viewabilityCount == DISPLAY_VIEWABILITY_COUNT){
                                Log.d("SuperAwesome", "[AA :: Info] Sending viewable impression");
                                SAEvents.sendEventToURL(ad.creative.viewableImpressionUrl);
                            } else {
                                Log.d("SuperAwesome", "[AA :: Error] Video is not in viewable rectangle");
                            }

                        } else {
                            ticks++;

                            // child
                            int[] array = new int[2];
                            getLocationInWindow(array);
                            Rect banner = new Rect(array[0], array[1], getWidth(), getHeight());

                            // super view
                            int[] sarray = new int[2];
                            View parent = (View)getParent();
                            parent.getLocationInWindow(sarray);
                            Rect sbanner = new Rect(sarray[0], sarray[1], parent.getWidth(), parent.getHeight());

                            // window
                            SAUtils.SASize screenSize = SAUtils.getRealScreenSize((Activity)getContext(), false);
                            Rect screen = new Rect(0, 0, screenSize.width, screenSize.height);

                            if (SAUtils.isTargetRectInFrameRect(banner, sbanner) && SAUtils.isTargetRectInFrameRect(banner, screen)){
                                viewabilityCount++;
                            }

                            Log.d("SuperAwesome", "[AA :: Info] Timer tick: " + ticks + "/5 and viewability count: " + viewabilityCount + "/5");

                            postDelayed(this, 1000);
                        }

                    }
                };
                postDelayed(viewabilityRunnable, 1000);
            }

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
                if (shouldShowPadlock()) {
                    padlock.setVisibility(View.VISIBLE);
                } else {
                    padlock.setVisibility(View.GONE);
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
    public boolean shouldShowPadlock () {
        return ad.creative.creativeFormat != SACreativeFormat.tag && !ad.isFallback && !(ad.isHouse && !ad.safeAdApproved);
    }

    @Override
    public void close() {
        SAEvents.unregisterDisplayMoatEvent(ad.placementId);
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

        /** switch between CPM & CPI campaigns */
        String finalUrl = destinationURL;
        if (ad.saCampaignType == SACampaignType.CPI) {
            finalUrl += "&referrer=";
            JSONObject referrerData = SAJsonParser.newObject(new Object[]{
                    "utm_source", SuperAwesome.getInstance().getConfiguration(), // used to be ad.advertiserId
                    "utm_campaign", ad.campaignId,
                    "utm_term", ad.lineItemId,
                    "utm_content", ad.creative.id,
                    "utm_medium", ad.placementId
            });
            String referrerQuery = SAUtils.formGetQueryFromDict(referrerData);
            referrerQuery = referrerQuery.replace("&", "%26");
            referrerQuery = referrerQuery.replace("=", "%3D");
            finalUrl += referrerQuery;
        }

        Log.d("SuperAwesome", "Going to " + finalUrl);

        /** go-to-url */
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl));
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

        /** prepare moat tracking */
        HashMap<String, String> adData = new HashMap<>();
        adData.put("advertiserId", "" + ad.advertiserId);
        adData.put("campaignId", "" + ad.campaignId);
        adData.put("lineItemId", "" + ad.lineItemId);
        adData.put("creativeId", "" + ad.creative.id);
        adData.put("app", "" + ad.app);
        adData.put("placementId", "" + ad.placementId);
        adData.put("publisherId", "" + ad.publisherId);
        String moatString = SAEvents.registerDisplayMoatEvent((Activity)this.getContext(), webView, adData);
        String fullHTML = ad.creative.details.data.adHtml.replace("_MOAT_", moatString);

        /** update HTML as well */
        webView.loadHTML(fullHTML, adWidth, adHeight, newWidth, newHeight);
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

    public void setIsPartOfFullscreen(boolean isPartOfFullscreen) {
        this.isPartOfFullscreen = isPartOfFullscreen;
    }

    /**
     * And this one for the parental gate
     * @param isParentalGateEnabled
     */
    public void setIsParentalGateEnabled (boolean isParentalGateEnabled) {
        this.isParentalGateEnabled = isParentalGateEnabled;
    }
}
