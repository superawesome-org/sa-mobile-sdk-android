/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
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
import android.widget.Button;
import android.widget.RelativeLayout;

import org.json.JSONObject;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.saadloader.SALoaderInterface;
import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.SAAd;
import tv.superawesome.lib.samodelspace.SACampaignType;
import tv.superawesome.lib.samodelspace.SACreativeFormat;
import tv.superawesome.lib.samodelspace.SAResponse;
import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.lib.sasession.SASessionInterface;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.sawebplayer.SAWebPlayer;
import tv.superawesome.lib.sawebplayer.SAWebPlayerClickInterface;
import tv.superawesome.lib.sawebplayer.SAWebPlayerEvent;
import tv.superawesome.lib.sawebplayer.SAWebPlayerEventInterface;
import tv.superawesome.sdk.SuperAwesome;

/**
 * Class that abstracts away the process of loading & displaying a banner type Ad.
 * A subclass of the Android "RelativeLayout" class.
 */
public class SABannerAd extends RelativeLayout {

    // constants
    private final int BANNER_BACKGROUND = Color.rgb(224, 224, 224);

    // private vars w/ exposed setters & getters
    private boolean isParentalGateEnabled = true;
    private SAAd ad;
    private SAInterface listener = new SAInterface() { @Override public void onEvent(int placementId, SAEvent event) {} };

    // the internal loader
    private SASession session;
    private SAEvents events;
    private SALoader loader;

    // private subviews
    private RelativeLayout contentHolder;
    private SAWebPlayer webView;
    private Button padlock;
    private SAParentalGate gate;

    // aux private vars
    private float cWidth = 0;
    private float cHeight = 0;
    private boolean layoutOK = false;
    private String destinationURL = null;
    private boolean showOnce = false;
    private boolean canPlay = true;

    /**********************************************************************************************
     * View initialization
     **********************************************************************************************/

    /**
     * Constructor with context
     *
     * @param context current context (activity or fragment)
     */
    public SABannerAd(Context context) {
        this(context, null, 0);
    }

    /**
     * Constructor with context and attribute sets
     *
     * @param context   current context (activity or fragment)
     * @param attrs     an attribute set
     */
    public SABannerAd(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor with context, attribute set and default style. This is where the layout
     * actually gets initialized
     *
     * @param context       current context (activity or fragment)
     * @param attrs         an attribute set
     * @param defStyleAttr  a default style
     */
    public SABannerAd(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode()) return;

        // create the loader
        session = new SASession (context);
        loader = new SALoader(context);
        events = new SAEvents(context);

        // get view ids dynamically
        String packageName = context.getPackageName();
        int view_sa_bannerId = getResources().getIdentifier("view_sa_banner", "layout", packageName);
        int content_holderId = getResources().getIdentifier("content_holder", "id", packageName);
        int web_viewId = getResources().getIdentifier("web_view", "id", packageName);
        int padlockId = getResources().getIdentifier("padlock_button", "id", packageName);

        // inflate the layout
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(view_sa_bannerId, this);

        // set default values
        setColor(SuperAwesome.getInstance().defaultBgColor());
        setParentalGate(SuperAwesome.getInstance().defaultParentalGate());
        setConfiguration(SuperAwesome.getInstance().defaultConfiguration());
        setTestMode(SuperAwesome.getInstance().defaultTestMode());

        // get the main relative layout content holder
        contentHolder = (RelativeLayout) findViewById(content_holderId);

        // get the padlock
        padlock = (Button) findViewById(padlockId);
        padlock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ads.superawesome.tv/v2/safead"));
                getContext().startActivity(browserIntent);
            }
        });

        // get and customize the web view
        webView = (SAWebPlayer) findViewById(web_viewId);
        webView.setClickListener(new SAWebPlayerClickInterface() {
            @Override
            public void SAWebPlayerClickHandled(String url) {
                // update the destination URL
                destinationURL = url;

                // check for PG
                if (isParentalGateEnabled) {
                    gate = new SAParentalGate(getContext(), SABannerAd.this, ad);
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

                            // send additional impressions
                            events.sendEventsFor("impression");
                            // events.sendEventsFor("sa_impr");

                            // send viewable impression
                            events.sendViewableImpressionForDisplay(SABannerAd.this);

                            // call listener
                            listener.onEvent(ad.placementId, SAEvent.adShown);
                        }
                        break;
                    }
                    case Web_Error: {
                        listener.onEvent(0, SAEvent.adFailedToShow);
                        break;
                    }
                }
            }
        });
    }

    /**
     * Overridden RelativeLayout method that is used to get the cWidth & cHeight member
     * variables updated
     *
     * @param changed   bool that holds if the layout changed
     * @param l         left margin
     * @param t         top margin
     * @param r         right margin
     * @param b         mottom margin
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        cWidth = getWidth();
        cHeight = getHeight();

        if (cWidth != 0 && cHeight != 0 && !layoutOK){
            layoutOK = true;
        }
    }

    /**********************************************************************************************
     * Public interface
     **********************************************************************************************/

    /**
     * Method that loads an ad into the banner ad queue. Ads can only be loaded once and then
     * can be reloaded after they've been played.
     *
     * @param placementId   the Ad placement id to load data for
     */
    public void load(final int placementId) {

        canPlay = false;

        session.setVersion(SuperAwesome.getInstance().getSDKVersion());
        session.prepareSession(new SASessionInterface() {
            @Override
            public void didFindSessionReady() {

                // after session is OK, prepare
                loader.loadAd(placementId, session, new SALoaderInterface() {
                    @Override
                    public void didLoadAd(SAResponse response) {
                        canPlay = response.isValid ();
                        setAd(response.isValid () ? response.ads.get(0) : null);
                        listener.onEvent(placementId, response.isValid () ? SAEvent.adLoaded : SAEvent.adFailedToLoad);
                    }
                });
            }
        });
    }

    /**
     * Method that returns whether ad data for a certain placement has already been loaded
     *
     * @return              true or false
     */
    public boolean hasAdAvailable () {
        return ad != null;
    }

    public void setAd(SAAd _ad) {
        this.ad = _ad;
        events.setAd(this.ad);
    }

    /**
     * Method that, if an ad data is loaded, will play the content for the user
     *
     * @param context       the current context (activity or fragment)
     */
    public void play(Context context) {

        if (ad != null && ad.creative.creativeFormat != SACreativeFormat.video && canPlay) {

            // if ad is still not OK - wait a little while longer
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // resize the views
                    canPlay = false;
                    resize((int) cWidth, (int) cHeight);

                    // make the padlock visible or not
                    if (shouldShowPadlock()) {
                        padlock.setVisibility(View.VISIBLE);
                    } else {
                        padlock.setVisibility(View.GONE);
                    }
                }
            };

            // wait until layout of the whole thing is OK before we start rendering the ad
            if (ad == null || !layoutOK) {
                this.postDelayed(runnable, 250);
            } else {
                post(runnable);
            }

        } else {
            listener.onEvent(0, SAEvent.adFailedToShow);
        }
    }

    /**
     * Method that closes the banner ad (right now it just unsubscribes from moat events)
     */
    public void close() {
        events.unregisterDisplayMoatEvent();
    }

    /**
     * Method that handles a click on the ad surface
     */
    public void click() {
        // callback
        listener.onEvent(ad.placementId, SAEvent.adClicked);

        // send tracking events, if needed
        if (!destinationURL.contains(session.getBaseUrl())) {
            events.sendEventsFor("sa_tracking");
        }

        // get the url to go to
        String finalUrl = destinationURL;

        // append CPI data to it
        if (ad.saCampaignType == SACampaignType.CPI) {

            // send install impression
            events.sendEventsFor("install");

            finalUrl += "&referrer=";
            JSONObject referrerData = SAJsonParser.newObject(new Object[]{
                    "utm_source", session.getConfiguration().ordinal(),
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
        Log.d("SuperAwesome-CPI", ad.writeToJson().toString());
        Log.d("SuperAwesome", "Going to " + finalUrl);

        // open URL
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl));
        getContext().startActivity(browserIntent);
    }

    /**
     * Method that determines if an ad should display a padlock over it's content to indicate
     * it has been properly approved by SuperAwesome
     *
     * @return true or false
     */
    private boolean shouldShowPadlock () {
        return ad.creative.creativeFormat != SACreativeFormat.tag && !ad.isFallback && !(ad.isHouse && !ad.safeAdApproved);
    }

    /**
     * Method that resized the content of the Banner Ad; Called every time the superview changes
     *
     * @param width     width to resize to
     * @param height    height to resize to
     */
    // todo: make sure Banner Ads don't get reloaded on each screen reload
    public void resize(int width, int height){
        // get ad W & H
        int adWidth = ad.creative.details.width,
            adHeight = ad.creative.details.height;

        // calc new frame
        Rect newFrame = SAUtils.mapOldSizeIntoNewSize(width, height, adWidth, adHeight);
        int newWidth = newFrame.right,
            newHeight = newFrame.bottom;

        android.widget.RelativeLayout.LayoutParams params = new LayoutParams(newWidth, newHeight);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        contentHolder.setLayoutParams(params);

        // prepare moat tracking
        String moatString = events.registerDisplayMoatEvent((Activity)this.getContext(), webView);
        String fullHTML = ad.creative.details.media.html.replace("_MOAT_", moatString);

        // update HTML as well
        webView.loadHTML(fullHTML, adWidth, adHeight, newWidth, newHeight);
    }

    /**********************************************************************************************
     * Setters & Getters
     **********************************************************************************************/

    public void setListener(SAInterface value) {
        listener = value != null ? value : listener;
    }

    public void enableParentalGate () {
        setParentalGate(true);
    }

    public void disableParentalGate () {
        setParentalGate(false);
    }

    public void enableTestMode () {
        setTestMode(true);
    }

    public void disableTestMode () {
        setTestMode(false);
    }

    public void setConfigurationProduction () {
        setConfiguration(SAConfiguration.PRODUCTION);
    }

    public void setConfigurationStaging () {
        setConfiguration(SAConfiguration.STAGING);
    }

    public void setColorTransparent () {
        setColor(true);
    }

    public void setColorGray () {
        setColor(false);
    }

    public void setParentalGate (boolean value) {
        isParentalGateEnabled = value;
    }

    public void setTestMode (boolean value) {
        session.setTestMode(value);
    }

    public void setConfiguration (SAConfiguration value) {
        session.setConfiguration(value);
    }

    public void setColor (boolean value) {
        if (value) {
            setBackgroundColor(Color.TRANSPARENT);
        } else  {
            setBackgroundColor(BANNER_BACKGROUND);
        }
    }
}
