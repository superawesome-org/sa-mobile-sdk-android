package tv.superawesome.sdk.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

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
import tv.superawesome.lib.sautils.SAImageUtils;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.sawebplayer.SAWebPlayer;
import tv.superawesome.lib.sawebplayer.SAWebPlayerEvent;
import tv.superawesome.lib.sawebplayer.SAWebPlayerEventInterface;
import tv.superawesome.sdk.SuperAwesome;

public class SABannerAd extends FrameLayout implements SAParentalGateInterface {

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
    private static final String    webPlayerTag = "SA_WebPlayer";
    private SAWebPlayer     webPlayer;
    private ImageButton     padlock;
    private SAParentalGate  gate;

    // bool
    private boolean         canPlay = true;
    private boolean         firstPlay = true;
    private boolean         isClosed = false;

    /**
     * Constructor with context
     *
     * @param context current context (activity or fragment)
     */
    public SABannerAd(Context context) {
        this(context, null, 0);
    }

    /**
     * Constructor with context and attribute set
     *
     * @param context current context (activity or fragment)
     * @param attrs   new attribute set
     */
    public SABannerAd(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor with context, attribute set and default style attribute
     *
     * @param context       current context (activity or fragment)
     * @param attrs         new attribute set
     * @param defStyleAttr  default style attribute
     */
    public SABannerAd(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode()) return;

        // create the loader
        session = new SASession (context);
        loader = new SALoader(context);
        events = new SAEvents(context);

        // set default values
        setColor(SuperAwesome.getInstance().defaultBgColor());
        setParentalGate(SuperAwesome.getInstance().defaultParentalGate());
        setConfiguration(SuperAwesome.getInstance().defaultConfiguration());
        setTestMode(SuperAwesome.getInstance().defaultTestMode());
    }

    /**
     * Method that gets called when the state of the banner changes (e.g. when screen rotates).
     * This will save the current ad data for later use
     *
     * @return a new Parcelable bundle to be saved
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putString("ad", ad != null ? ad.writeToJson().toString() : new SAAd().writeToJson().toString());
        return bundle;
    }

    /**
     * Method that gets called when the state of the banner is restored (e.g. after a screen
     * rotation). This will try to get a previously saved ad and assign it to the banner.
     *
     * @param state a Parcelable state object (usually a Bundle)
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle)  {
            Bundle bundle = (Bundle) state;
            state = bundle.getParcelable("superState");
            String adJson = bundle.getString("ad");
            SAAd ad = new SAAd(adJson);
            setAd(ad.isValid() ? ad : null);
        }
        super.onRestoreInstanceState(state);
    }

    /**
     * One of the main public methods of the SABannerAd class. This will load a new SAAd object
     * corresponding to a given placement Id.
     *
     * @param placementId Awesome Ads ID for ad data to be loaded
     */
    public void load (final int placementId) {

        // from this moment on the ad can't be played, to avoid wierd things
        canPlay = false;

        // close
        if (!firstPlay) {
            close();
        }

        // set this to false
        isClosed = false;

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
     * One of the main public methods of the SABannerAd class. This will play an already existing
     * loaded ad, or fail.
     *
     * @param context current context (activity or fragment)
     */
    public void play (final Context context) {

        // if the banner ad has a valid ad loaded then play it
        if (ad != null && ad.creative.format != SACreativeFormat.video && canPlay && !isClosed) {

            // canPlay becomes "false" again so no other playing can happen until a new load
            canPlay = false;
            firstPlay = false;

            // create a new web player fragment object
            webPlayer = new SAWebPlayer();
            webPlayer.setContentSize(ad.creative.details.width, ad.creative.details.height);
            // and set it's event listener
            webPlayer.setEventListener(new SAWebPlayerEventInterface() {
                @Override
                public void saWebPlayerDidReceiveEvent(SAWebPlayerEvent event, String destination) {

                    switch (event) {
                        // this is called when the web player is on screen and prepared to load
                        // HTML data
                        case Web_Prepared: {

                            // prepare moat tracking
                            String moatString = events.registerDisplayMoatEvent((Activity)context, webPlayer.getWebView());
                            String fullHTML = ad.creative.details.media.html.replace("_MOAT_", moatString);

                            // load the HTML
                            webPlayer.loadHTML(fullHTML);

                            break;
                        }
                        // this is called after the HTML data is loaded and is where all
                        // events are fired
                        case Web_Loaded: {

                            // send viewable impression
                            events.sendViewableImpressionForDisplay(SABannerAd.this);

                            // call listener
                            listener.onEvent(ad.placementId, SAEvent.adShown);

                            break;
                        }
                        // this is actually a fragment event notifying the banner class that
                        // the fragment has started
                        case Web_Started:{

                            if (padlock != null) {
                                webPlayer.getHolder().removeView(padlock);
                            }

                            float sf = SAUtils.getScaleFactor((Activity)context);
                            padlock = new ImageButton(context);
                            padlock.setImageBitmap(SAImageUtils.createPadlockBitmap());
                            padlock.setBackgroundColor(Color.TRANSPARENT);
                            padlock.setScaleType(ImageView.ScaleType.FIT_XY);
                            padlock.setPadding(0, 0, 0, 0);
                            padlock.setLayoutParams(new ViewGroup.LayoutParams((int) (83 * sf), (int) (31 * sf)));
                            padlock.setVisibility(ad.showPadlock ? VISIBLE : GONE);
                            padlock.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ads.superawesome.tv/v2/safead"));
                                    context.startActivity(browserIntent);
                                }
                            });
                            webPlayer.getHolder().addView(padlock);

                            break;
                        }
                        // this is called when the fragment & web view have all been laid out
                        case Web_Layout:{
                            padlock.setTranslationX(webPlayer.getWebView().getTranslationX());
                            padlock.setTranslationY(webPlayer.getWebView().getTranslationY());
                            break;
                        }
                        // this is in case of error
                        case Web_Error: {

                            listener.onEvent(ad.placementId, SAEvent.adFailedToShow);

                            break;
                        }
                        // and this is in case of click
                        case Web_Click: {

                            if (destination != null) {

                                // check for PG
                                if (isParentalGateEnabled) {
                                    gate = new SAParentalGate(getContext(), 0, destination);
                                    gate.setListener(SABannerAd.this);
                                    gate.show();
                                } else {
                                    click(destination);
                                }

                            }

                            break;
                        }
                    }
                }
            });

            // actually add the fragment
            try {
                ((Activity) getContext()).getFragmentManager()
                        .beginTransaction()
                        .add(getId(), webPlayer, webPlayerTag)
                        .commit();
            } catch (Exception e) {
                // catch this error
                listener.onEvent(0, SAEvent.adFailedToShow);
            }

        }
        // if no ad has been loaded, send an ad failure event
        else {
            listener.onEvent(0, SAEvent.adFailedToShow);
        }
    }

    /**
     * One of the main public methods of the SABannerAd class that gets called when the web view
     * surface detects a click of some sort.
     *
     * @param destination the destination url
     */
    public void click (String destination) {

        Log.d("SuperAwesome", "Trying to go to: " + destination);

        // callback
        listener.onEvent(ad.placementId, SAEvent.adClicked);

        // send tracking events, if needed
        if (session != null && !destination.contains(session.getBaseUrl())) {
            events.sendEventsFor("superawesome_click");
        }

        // append CPI data to it
        destination += ad.campaignType == SACampaignType.CPI ? ("&referrer=" + ad.creative.referralData.writeToReferralQuery()) : "";

        // start browser
        getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(destination)));
    }

    /**
     * Method that gets called in order to close the banner ad, remove any fragments, etc
     */
    public void close () {
        // set listener
        listener.onEvent(ad != null ? ad.placementId : 0, SAEvent.adClosed);

        // reset any ad that might be in here
        setAd(null);

        // remove the web player
        if (webPlayer != null) {
            try {
                ((Activity) getContext()).getFragmentManager()
                        .beginTransaction()
                        .remove(webPlayer)
                        .commit();
            } catch (Exception e) {
                // this means the fragment wasn't set yet
            }
        }

        // make padlock invisible
        if (padlock != null) {
            padlock.setVisibility(GONE);
        }

        // close ad
        isClosed = true;

        // unregister moat events
        events.unregisterDisplayMoatEvent();
    }

    /**
     * Method that set an ad for the banner - and also sets an ad for the events object
     *
     * @param ad new ad
     */
    public void setAd(SAAd ad) {
        this.ad = ad;
        events.setAd(this.ad);
    }

    /**
     * Method that determines if an ad is available
     *
     * @return true or false
     */
    public boolean hasAdAvailable () {
        return ad != null;
    }

    @Override
    public void parentalGateOpen(int position) {
        // send Open Event
        events.sendEventsFor("superawesome_pg_open");
    }

    @Override
    public void parentalGateSuccess(int position, String destination) {
        // send event
        events.sendEventsFor("superawesome_pg_success");
        // go to click
        click(destination);
    }

    @Override
    public void parentalGateFailure(int position) {
        // send event
        events.sendEventsFor("superawesome_pg_fail");
    }

    @Override
    public void parentalGateCancel(int position) {
        // send event
        events.sendEventsFor("superawesome_pg_close");
    }

    /**********************************************************************************************
     * Setters & Getters
     **********************************************************************************************/

    public boolean isClosed () {
        return isClosed;
    }

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
