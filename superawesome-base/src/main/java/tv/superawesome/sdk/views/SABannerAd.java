/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.sdk.views;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

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
public class SABannerAd extends FrameLayout {

    // constants
    private final int       BANNER_BACKGROUND = Color.rgb(224, 224, 224);

    // private vars w/ exposed setters & getters
    private boolean         isParentalGateEnabled = true;
    private SAAd            ad;
    private SAInterface     listener = new SAInterface() { @Override public void onEvent(int placementId, SAEvent event) {} };

    // the internal loader
    private SASession       session;
    private SAEvents        events;
    private SALoader        loader;

    // private subviews
    private SAWebPlayer     webView;
    private String          bannerTag;
    private Button          padlock;
    private SAParentalGate  gate;

    private String          destinationURL = null;
    private boolean         canPlay = true;

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
     * actually gets initialized.
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
        int sa_banner_adId = getResources().getIdentifier("sa_banner_ad", "id", packageName);
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
        String bannerTag = "My_WebView";

        FragmentManager manager = ((Activity) context).getFragmentManager();
        if (manager.findFragmentByTag(bannerTag) == null) {
            webView = new SAWebPlayer();
            manager.beginTransaction().add(getId(), webView, bannerTag).commit();
        } else {
            webView = (SAWebPlayer) manager.findFragmentByTag(bannerTag);
        }

        webView.setClickListener(new SAWebPlayerClickInterface() {
            @Override
            public void saWebPlayerDidReceiveClick(String url) {
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
            public void saWebPlayerDidReceiveEvent(SAWebPlayerEvent saWebPlayerEvent) {
                switch (saWebPlayerEvent) {
                    case Web_Start: {

                        // send additional impressions
                        events.sendEventsFor("impression");
                        // events.sendEventsFor("sa_impr");

                        // send viewable impression
                        events.sendViewableImpressionForDisplay(SABannerAd.this);

                        // call listener
                        listener.onEvent(ad.placementId, SAEvent.adShown);
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
     * Overridden method that saves the current important instance data for a Banner Ad when
     * it rotates.
     *
     * @return a Parcelable object with the current state
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        if (ad != null) {
            bundle.putString("ad", this.ad.writeToJson().toString());
        }
        return bundle;
    }

    /**
     * Overridden method that restores a previous state when a Banner Ad rotates
     *
     * @param state previous saved state, as a Parcelable object
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle)  {
            Bundle bundle = (Bundle) state;
            String adJson = bundle.getString("ad");
            if (adJson != null) {
                SAAd ad = new SAAd(adJson);
                setAd(ad);
            }
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
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
    public void load (final int placementId) {

        // when starting an ad loading procedure, re-set this
        canPlay = false;

        // first close any previously existing ads
        close();

        // next init a new session & prepare it
        session.setVersion(SuperAwesome.getInstance().getSDKVersion());
        session.prepareSession(new SASessionInterface() {
            @Override
            public void didFindSessionReady() {

                // after session is OK, prepare
                loader.loadAd(placementId, session, new SALoaderInterface() {
                    @Override
                    public void saDidLoadAd(SAResponse response) {
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
     * @return true or false
     */
    public boolean hasAdAvailable () {
        return ad != null;
    }

    /**
     * More important setter that both copies a reference to a valid, non-null ad and sets the
     * ad for the events object
     *
     * @param ad a copy of a SAAd object
     */
    public void setAd(SAAd ad) {
        this.ad = ad;
        events.setAd(this.ad);
    }

    /**
     * Method that, if an ad data is loaded, will play the content for the user
     *
     * @param context the current context (activity or fragment)
     */
    public void play (Context context) {

        // if the banner ad has a valid ad loaded then play it
        if (ad != null && ad.creative.format != SACreativeFormat.video && canPlay) {

            // set can play = false
            canPlay = false;

            // set content size
            webView.setContentSize(ad.creative.details.width, ad.creative.details.height);

            // prepare moat tracking
            String moatString = events.registerDisplayMoatEvent((Activity)this.getContext(), webView.getWebView());
            String fullHTML = ad.creative.details.media.html.replace("_MOAT_", moatString);

            // load html
            webView.loadHTML(fullHTML);

            // make the padlock visible or not
            padlock.setVisibility(shouldShowPadlock() ? VISIBLE : GONE);

        }
        // else just send a new event listener
        else {
            listener.onEvent(0, SAEvent.adFailedToShow);
        }
    }

    /**
     * Overridden onLayout method in which some minor layout setup are being finalised
     *
     * @param changed whether the layout has changed
     * @param left    new left margin of the banner ad
     * @param top     new top margin of the banner ad
     * @param right   new right margin of the banner ad
     * @param bottom  new bottom margin of the banner ad
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // call to super
        super.onLayout(changed, left, top, right, bottom);

        // set visibility for the padlock
        padlock.setVisibility(shouldShowPadlock() ? VISIBLE : GONE);

        // if the web view exists
        if (webView != null && webView.getWebView() != null) {
            // move the padlock so that it looks nice ...
            float translationX = webView.getWebView().getTranslationX();
            float translationY = webView.getWebView().getTranslationY();
            padlock.setTranslationX(translationX);
            padlock.setTranslationY(translationY);
        }
    }

    /**
     * Method that handles a click on the ad surface
     */
    public void click () {
        // callback
        listener.onEvent(ad.placementId, SAEvent.adClicked);

        // send click counter events
        events.sendEventsFor("clk_counter");

        // send tracking events, if needed
        if (!destinationURL.contains(session.getBaseUrl())) {
            events.sendEventsFor("sa_tracking");
        }

        // get the url to go to
        String finalUrl = destinationURL;

        // append CPI data to it
        if (ad.campaignType == SACampaignType.CPI) {

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
        return ad != null && ad.creative.format != SACreativeFormat.tag && !ad.isFallback && !(ad.isHouse && !ad.safeAdApproved);
    }

    /**
     * Method that closes the banner ad (right now it just un-subscribes from moat events)
     */
    public void close() {
        // reset any ad that might be in here
        setAd(null);

        // set web view content size to 0
        webView.setContentSize(0, 0);

        // make padlock invisible
        padlock.setVisibility(GONE);

        // unregister moat events
        events.unregisterDisplayMoatEvent();
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
