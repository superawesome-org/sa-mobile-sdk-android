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

import org.json.JSONObject;

import java.util.HashMap;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.saadloader.SALoaderInterface;
import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.SACampaignType;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.sawebplayer.SAWebPlayer;
import tv.superawesome.lib.sawebplayer.SAWebPlayerClickInterface;
import tv.superawesome.lib.sawebplayer.SAWebPlayerEvent;
import tv.superawesome.lib.sawebplayer.SAWebPlayerEventInterface;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.lib.samodelspace.SAAd;
import tv.superawesome.lib.samodelspace.SACreativeFormat;

/**
 * Created by gabriel.coman on 30/12/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SABannerAd extends RelativeLayout implements SAViewInterface {

    /** constants */
    private final int DISPLAY_VIEWABILITY_COUNT = 1;

    /** Private variables */
    public boolean isParentalGateEnabled = true;
    private boolean isPartOfFullscreen = false;
    private SAAd ad;

    /** Subviews */
    private RelativeLayout contentHolder;
    private SALoader loader;
    private SAWebPlayer webView;
    private ImageView padlock;
    private SAParentalGate gate;

    /** listeners */
    public SAInterface adListener;

    /** helper vars */
    private float cWidth = 0;
    private float cHeight = 0;
    private boolean layoutOK = false;
    private String destinationURL = null;
    private boolean showOnce = false;
    private boolean canPlay = true;

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

        // create the loader
        loader = new SALoader();

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
        webView.setClickListener(new SAWebPlayerClickInterface() {
            @Override
            public void SAWebPlayerClickHandled(String url) {
                /** update the destination URL */
                destinationURL = url;

                /** check for PG */
                if (isParentalGateEnabled) {
                    gate = new SAParentalGate(getContext(), this, ad);
                    gate.show();
                } else {
                    click();
                }
            }
        });
        webView.setEventListener(new SAWebPlayerEventInterface() {
            @Override
            public void SAWebPlayerEventHandled(SAWebPlayerEvent saWebPlayerEvent) {
                switch (saWebPlayerEvent) {
                    case Web_Start: {

                        if (!showOnce) {
                            showOnce = true;

                            if (isPartOfFullscreen) {
                                SAEvents.sendEventsFor(ad.creative.events, "viewable_impr");
                            }
                            else {
                                viewabilityRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        runViewableImpression();
                                    }
                                };
                                postDelayed(viewabilityRunnable, 1000);
                            }

                            /** send impression URL, if exits, to 3rd party only */
                            SAEvents.sendEventsFor(ad.creative.events, "impression");

                            /** call listener */
                            if (adListener != null) {
                                adListener.adWasShown(ad.placementId);
                            }
                        }
                        break;
                    }
                    case Web_Error: {
                        if (adListener != null) {
                            adListener.adFailedToShow(ad.placementId);
                        }
                        break;
                    }
                }
            }
        });
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
    /** <SAViewInterface> */
    /**********************************************************************************************/

    @Override public void load(final int placementId) {

        canPlay = false;

        loader.loadAd(placementId, new SALoaderInterface() {
            @Override
            public void didLoadAd(SAAd saAd) {
                ad = saAd;
                canPlay = true;

                if (ad != null) {
                    if (adListener != null) {
                        adListener.adWasLoaded(placementId);
                    }
                } else {
                    if (adListener != null) {
                        adListener.adWasNotLoaded(placementId);
                    }
                }
            }
        });

    }

    @Override public void setAd(SAAd _ad) {
        this.ad = _ad;
    }

    @Override public SAAd getAd() {
        return this.ad;
    }

    @Override public void play() {

        if (ad != null && ad.creative.creativeFormat != SACreativeFormat.video && canPlay) {
            /** if ad is still not OK - wait a little while longer */
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    /** resize the views */
                    canPlay = false;
                    resize((int) cWidth, (int) cHeight);

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

        } else {
            if (adListener != null) {
                adListener.adHasIncorrectPlacement(ad.placementId);
            }
        }
    }

    @Override public boolean shouldShowPadlock () {
        return ad.creative.creativeFormat != SACreativeFormat.tag && !ad.isFallback && !(ad.isHouse && !ad.safeAdApproved);
    }

    @Override public void close() {
        SAEvents.unregisterDisplayMoatEvent(ad.placementId);
    }

    @Override public void click() {
        /** call listener */
        if (adListener != null) {
            adListener.adWasClicked(ad.placementId);
        }

        if (!destinationURL.contains(SuperAwesome.getInstance().getBaseURL())) {
            SAEvents.sendEventsFor(ad.creative.events, "sa_tracking");
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

    @Override public void resize(int width, int height){
        /** get ad width */
        int adWidth = ad.creative.details.width,
            adHeight = ad.creative.details.height;

        /** calc new frame */
        Rect newFrame = SAUtils.mapOldSizeIntoNewSize(width, height, adWidth, adHeight);
        int newWidth = newFrame.right,
            newHeight = newFrame.bottom;

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
        String fullHTML = ad.creative.details.media.html.replace("_MOAT_", moatString);

        /** update HTML as well */
        webView.loadHTML(fullHTML, adWidth, adHeight, newWidth, newHeight);
    }

    /**********************************************************************************************/
    /** setters for listeners */
    /**********************************************************************************************/

    public void setIsPartOfFullscreen(boolean isPartOfFullscreen) {
        this.isPartOfFullscreen = isPartOfFullscreen;
    }

    public void runViewableImpression () {
        if (ticks >= DISPLAY_VIEWABILITY_COUNT) {

            if (viewabilityCount == DISPLAY_VIEWABILITY_COUNT){
                SAEvents.sendEventsFor(ad.creative.events, "viewable_impr");
            } else {
                Log.d("SuperAwesome", "[AA :: Error] Banner is not in viewable rectangle");
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

            Log.d("SuperAwesome", "[AA :: Info] Timer tick: " + ticks + "/" + DISPLAY_VIEWABILITY_COUNT + " and viewability count: " + viewabilityCount + "/" + DISPLAY_VIEWABILITY_COUNT);

            postDelayed(viewabilityRunnable, 1000);
        }
    }
}
