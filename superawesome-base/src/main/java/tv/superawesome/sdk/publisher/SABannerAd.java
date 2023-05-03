package tv.superawesome.sdk.publisher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Collections;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.sabumperpage.SABumperPage;
import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACampaignType;
import tv.superawesome.lib.samodelspace.saad.SACreativeFormat;
import tv.superawesome.lib.saparentalgate.SAParentalGate;
import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.lib.sasession.defines.SARTBInstl;
import tv.superawesome.lib.sasession.defines.SARTBPlaybackMethod;
import tv.superawesome.lib.sasession.defines.SARTBPosition;
import tv.superawesome.lib.sasession.defines.SARTBSkip;
import tv.superawesome.lib.sasession.defines.SARTBStartDelay;
import tv.superawesome.lib.sasession.session.SASession;
import tv.superawesome.lib.sautils.SAClock;
import tv.superawesome.lib.sautils.SAImageUtils;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.sawebplayer.SAWebPlayer;

public class SABannerAd extends FrameLayout {

    interface SABannerAdListener {
        void hasBeenVisible();

        void failedToShow();
    }

    // constants
    private final int BANNER_BACKGROUND = Color.rgb(224, 224, 224);

    // private vars w/ exposed setters & getters
    private boolean isParentalGateEnabled = false;
    private boolean isBumperPageEnabled = false;
    private SAAd ad;
    private SAInterface listener = (placementId, event) -> {
    };

    // the internal loader
    private final SASession session;
    private final SAEvents events;
    private final SALoader loader;

    // private subviews
    private SAWebPlayer webPlayer;
    private ImageButton padlock;

    // bool
    private boolean canPlay = true;
    private boolean firstPlay = true;
    private boolean isClosed = false;

    private Long currentClickThreshold = 0L;

    private SABannerAdListener bannerListener = null;

    // Utils
    private final SAClock clock;

    /**
     * Constructor with context
     *
     * @param context current context (activity or fragment)
     */
    public SABannerAd(Context context) {
        this(context, null, 0, new SAClock());
    }

    /**
     * Constructor with context and attribute set
     *
     * @param context current context (activity or fragment)
     * @param attrs   new attribute set
     */
    public SABannerAd(Context context, AttributeSet attrs) {
        this(context, attrs, 0, new SAClock());
    }

    /**
     * Constructor with context, attribute set and default style attribute
     *
     * @param context      current context (activity or fragment)
     * @param attrs        new attribute set
     * @param defStyleAttr default style attribute
     */
    public SABannerAd(Context context, AttributeSet attrs, int defStyleAttr, SAClock clock) {
        super(context, attrs, defStyleAttr);

        // create the loader
        session = new SASession(context);
        loader = new SALoader(context);
        events = new SAEvents();

        // Add the clock
        this.clock = clock;

        // set default values
        setColor(SADefaults.defaultBgColor());
        setParentalGate(SADefaults.defaultParentalGate());
        setBumperPage(SADefaults.defaultBumperPage());
        setConfiguration(SADefaults.defaultConfiguration());
        setTestMode(SADefaults.defaultTestMode());
    }

    /**
     * One of the main public methods of the SABannerAd class. This will load a new SAAd object
     * corresponding to a given placement Id.
     *
     * @param placementId Awesome Ads ID for ad data to be loaded
     */
    public void load(final int placementId) {
        load(placementId, Collections.emptyMap());
    }

    /**
     * One of the main public methods of the SABannerAd class. This will load a new SAAd object
     * corresponding to a given placement Id.
     *
     * @param placementId Awesome Ads ID for ad data to be loaded
     * @param options     a dictionary of data to send with an ad's requests and events.
     *                    Supports String or Int values.
     */
    public void load(final int placementId, final Map<String, Object> options) {

        // very late init of the AwesomeAds SDK
        try {
            AwesomeAds.init(((Activity) this.getContext()).getApplication(), false);
        } catch (Exception e) {
            Log.d("SuperAwesome", "Error initing AwesomeAds in SABannerAd " + e.getMessage());
        }

        // from this moment on the ad can't be played, to avoid wierd things
        canPlay = false;

        // close
        if (!firstPlay) {
            close();
        }

        // set this to false
        isClosed = false;

        // next init a new session & prepare it
        session.setPos(SARTBPosition.ABOVE_THE_FOLD);
        session.setPlaybackMethod(SARTBPlaybackMethod.WITH_SOUND_ON_SCREEN);
        session.setInstl(SARTBInstl.NOT_FULLSCREEN);
        session.setSkip(SARTBSkip.NO_SKIP);
        session.setStartDelay(SARTBStartDelay.PRE_ROLL);
        try {
            session.setWidth(getWidth());
            session.setHeight(getHeight());
        } catch (Exception e) {
            // do nothing
        }

        session.prepareSession(() -> {

            // after session is OK, prepare
            loader.loadAd(placementId, session, options, response -> {

                if (response.status != 200) {
                    if (listener != null) {
                        listener.onEvent(placementId, SAEvent.adFailedToLoad);
                    } else {
                        Log.w("AwesomeAds", "Banner Ad listener not implemented. Event would have been: adFailedToLoad");
                    }
                } else {
                    canPlay = response.isValid();
                    setAd(response.isValid() ? response.ads.get(0) : null);
                    if (listener != null) {
                        SAEvent eventToSend = response.isValid() ? SAEvent.adLoaded : SAEvent.adEmpty;
                        listener.onEvent(placementId, eventToSend);
                        Log.d("SABannerAd", "Event callback: " + eventToSend);
                    } else {
                        Log.w("AwesomeAds", "Banner Ad listener not implemented. Event would have been either adLoaded or adEmpty");
                    }
                }
            });
        });
    }

    /**
     * One of the main public methods of the SABannerAd class. This will load a new SAAd object
     * corresponding to a given placement Id.
     *
     * @param placementId Awesome Ads ID for ad data to be loaded
     * @param lineItemId  The id of the lineItem
     * @param creativeId  The id of the creative
     */
    public void load(final int placementId,
                     final int lineItemId,
                     final int creativeId) {
        load(placementId, lineItemId, creativeId, Collections.emptyMap());
    }

    /**
     * One of the main public methods of the SABannerAd class. This will load a new SAAd object
     * corresponding to a given placement Id.
     *
     * @param placementId Awesome Ads ID for ad data to be loaded
     * @param lineItemId  The id of the lineItem
     * @param creativeId  The id of the creative
     * @param options     a dictionary of data to send with an ad's requests and events.
     *                    Supports String or Int values.
     */
    public void load(final int placementId,
                     final int lineItemId,
                     final int creativeId,
                     final Map<String, Object> options) {

        // very late init of the AwesomeAds SDK
        try {
            AwesomeAds.init(((Activity) this.getContext()).getApplication(), false);
        } catch (Exception e) {
            Log.d("SuperAwesome", "Error initing AwesomeAds in SABannerAd " + e.getMessage());
        }

        // from this moment on the ad can't be played, to avoid wierd things
        canPlay = false;

        // close
        if (!firstPlay) {
            close();
        }

        // set this to false
        isClosed = false;

        // next init a new session & prepare it
        session.setPos(SARTBPosition.ABOVE_THE_FOLD);
        session.setPlaybackMethod(SARTBPlaybackMethod.WITH_SOUND_ON_SCREEN);
        session.setInstl(SARTBInstl.NOT_FULLSCREEN);
        session.setSkip(SARTBSkip.NO_SKIP);
        session.setStartDelay(SARTBStartDelay.PRE_ROLL);
        try {
            session.setWidth(getWidth());
            session.setHeight(getHeight());
        } catch (Exception e) {
            // do nothing
        }

        session.prepareSession(() ->

                // after session is OK, prepare
                loader.loadAd(placementId, lineItemId, creativeId, session, options, response -> {

                    if (response.status != 200) {
                        if (listener != null) {
                            listener.onEvent(placementId, SAEvent.adFailedToLoad);
                        } else {
                            Log.w("AwesomeAds", "Banner Ad listener not implemented. Event would have been: adFailedToLoad");
                        }
                    } else {
                        canPlay = response.isValid();
                        setAd(response.isValid() ? response.ads.get(0) : null);
                        if (listener != null) {
                            SAEvent eventToSend = response.isValid() ? SAEvent.adLoaded : SAEvent.adEmpty;
                            listener.onEvent(placementId, eventToSend);
                            Log.d("SABannerAd", "Event callback: " + eventToSend);
                        } else {
                            Log.w("AwesomeAds", "Banner Ad listener not implemented. Event would have been either adLoaded or adEmpty");
                        }
                    }
                })
        );
    }

    /**
     * One of the main public methods of the SABannerAd class. This will play an already existing
     * loaded ad, or fail.
     *
     * @param context current context (activity or fragment)
     */
    public void play(final Context context) {

        // if the banner ad has a valid ad loaded then play it
        if (ad != null && ad.creative.format != SACreativeFormat.video && canPlay && !isClosed) {

            // canPlay becomes "false" again so no other playing can happen until a new load
            canPlay = false;
            firstPlay = false;

            // create a new web player fragment object
            webPlayer = new SAWebPlayer(context);
            webPlayer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            // and set it's event listener
            webPlayer.setEventListener((event, destination) -> {

                switch (event) {
                    // this is called when the web player is on screen and prepared to load
                    // HTML data
                    case Web_Prepared: {

                        // trigger impression
                        events.triggerImpressionEvent();

                        String fullHTML = ad.creative
                                .details
                                .media
                                .html
                                .replace("_TIMESTAMP_", Long.toString(clock.getTimestamp()));
                        // load the HTML
                        Log.d("SADefaults", "Full HTML is " + fullHTML);
                        webPlayer.loadHTML(ad.creative.details.base, fullHTML);

                        break;
                    }
                    // this is called after the HTML data is loaded and is where all
                    // events are fired
                    case Web_Loaded: {

                        // send viewable impression
                        events.checkViewableStatusForDisplay(SABannerAd.this, success -> {
                            if (success) {
                                events.triggerViewableImpressionEvent();
                                if (bannerListener != null) {
                                    bannerListener.hasBeenVisible();
                                }
                            }
                        });
                        // call listener
                        if (listener != null) {
                            listener.onEvent(ad.placementId, SAEvent.adShown);
                            Log.d("SABannerAd", "Event callback: " + SAEvent.adShown);
                        } else {
                            Log.w("AwesomeAds", "Banner Ad listener not implemented. Event would have been adShown");
                        }

                        break;
                    }
                    // this is actually a fragment event notifying the banner class that
                    // the fragment has started
                    case Web_Started: {

                        float sf = SAUtils.getScaleFactor((Activity) context);
                        padlock = new ImageButton(context);
                        padlock.setImageBitmap(SAImageUtils.createPadlockBitmap());
                        padlock.setBackgroundColor(Color.TRANSPARENT);
                        padlock.setScaleType(ImageView.ScaleType.FIT_XY);
                        int topPadding = (int) (2 * sf);
                        padlock.setPadding(0, topPadding, 0, 0);
                        padlock.setLayoutParams(new ViewGroup.LayoutParams((int) (77 * sf), (int) (31 * sf)));
                        padlock.setContentDescription("Safe Ad Logo");

                        //
                        // weird condition: if banner is closed (and ad is nulled) in the time
                        // it was "play()-ed" and the time "Web_Started" gets called,
                        // then ad is null-ed and we get an exception.
                        try {
                            padlock.setVisibility(ad.isPadlockVisible ? VISIBLE : GONE);
                        } catch (Exception e) {
                            padlock.setVisibility(GONE);
                        }

                        padlock.setOnClickListener(v -> {
                            Runnable runner = () -> showSuperAwesomeWebViewInExternalBrowser(context);
                            showParentalGateIfNeededWithCompletion(context, runner);
                        });
                        webPlayer.getHolder().addView(padlock);
                        padlock.setTranslationX(webPlayer.getWebView().getTranslationX());
                        padlock.setTranslationY(webPlayer.getWebView().getTranslationY());

                        break;
                    }
                    // this is called when the fragment & web view have all been laid out
                    case Web_Layout: {

                        if (webPlayer.getWebView() != null && padlock != null) {
                            padlock.setTranslationX(webPlayer.getWebView().getTranslationX());
                            padlock.setTranslationY(webPlayer.getWebView().getTranslationY());
                        }
                        break;
                    }
                    // this is in case of error
                    case Web_Error: {
                        if (listener != null) {
                            listener.onEvent(ad.placementId, SAEvent.adFailedToShow);
                        } else {
                            Log.w("AwesomeAds", "Banner Ad listener not implemented. Event would have been adFailedToShow");
                        }
                        if (bannerListener != null) {
                            bannerListener.failedToShow();
                        }
                        break;
                    }
                    // this is most likely from fallbacks indicating an ad failed to load
                    case Web_Empty: {
                        if (listener != null) {
                            listener.onEvent(ad.placementId, SAEvent.adFailedToLoad);
                        } else {
                            Log.w("AwesomeAds", "Banner Ad listener not implemented. Event would have been adFailedToLoad");
                        }
                        break;
                    }
                    // and this is in case of click
                    case Web_Click: {

                        if (destination != null) {
                            Runnable runner = () -> click(destination);
                            runner.run();
                            // showParentalGateIfNeededWithCompletion(context, runner);
                        }

                        break;
                    }
                }
            });

            this.addView(webPlayer);
            webPlayer.setup();
        }
        // if no ad has been loaded, send an ad failure event
        else {
            if (listener != null) {
                listener.onEvent(0, SAEvent.adFailedToShow);
            } else {
                Log.w("AwesomeAds", "Banner Ad listener not implemented. Event would have been adFailedToShow");
            }
        }
    }

    /**
     * One of the main public methods of the SABannerAd class that gets called when the web view
     * surface detects a click of some sort.
     *
     * @param destination the destination url
     */
    public void click(final String destination) {
        if ((ad != null && ad.creative != null && ad.creative.bumper) || isBumperPageEnabled) {
            playBumperPage(getContext(), () -> {
                handleUrl(destination);
                return null;
            });
        } else {
            handleUrl(destination);
        }
    }

    private SABumperPage bumperPage = null;

    private void playBumperPage(Context context, Function0<Unit> onFinish) {
        if (bumperPage != null) {
            bumperPage.stop();
        }
        bumperPage = new SABumperPage();
        bumperPage.setOnFinish(onFinish);
        bumperPage.show(context);
    }

    private void handleUrl(String destination) {
        // if someone's closed this thing
        if (ad == null || ad.creative == null) {
            return;
        }

        Log.d("AwesomeAds-2", "Got here!");

        Long currentTime = System.currentTimeMillis() / 1000;
        long diff = Math.abs(currentTime - currentClickThreshold);

        if (diff < SADefaults.defaultClickThreshold()) {
            Log.d("AwesomeAds-2", "Current diff is " + diff);
            return;
        }

        currentClickThreshold = currentTime;

        Log.d("AwesomeAds-2", "Going to " + destination);

        // callback
        if (listener != null) {
            listener.onEvent(ad.placementId, SAEvent.adClicked);
            Log.d("SABannerAd", "Event callback: " + SAEvent.adClicked);
        } else {
            Log.w("AwesomeAds", "Banner Ad listener not implemented. Event would have been adClicked");
        }

        // send tracking events, if needed
        if (ad != null &&
                ad.creative != null &&
                ad.creative.format != SACreativeFormat.rich &&
                session != null &&
                !destination.contains(session.getBaseUrl())) {
            events.triggerClickEvent();
        }

        // append CPI data to it
        destination += ad.campaignType == SACampaignType.CPI ? ("&referrer=" + ad.creative.referral.writeToReferralQuery()) : "";

        // start browser
        try {
            getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(destination)));
        } catch (Exception e) {
            // do nothing
        }
    }

    /**
     * Method that gets called in order to close the banner ad, remove any fragments, etc
     */
    public void close() {
        // de-set visibility listener
        if (bannerListener != null) {
            bannerListener = null;
        }

        // de-set public listener
        if (listener != null) {
            listener.onEvent(ad != null ? ad.placementId : 0, SAEvent.adClosed);
            Log.d("SABannerAd", "Event callback: " + SAEvent.adClosed);
        } else {
            Log.w("AwesomeAds", "Banner Ad listener not implemented. Event would have been adClosed");
        }

        // reset any ad that might be in here
        setAd(null);

        // remove the web player
        if (webPlayer != null) {
            this.removeView(webPlayer);
            webPlayer.destroy();
            webPlayer = null;
        }

        // make padlock invisible
        if (padlock != null) {
            padlock.setVisibility(GONE);
        }

        if (bumperPage != null) {
            bumperPage.stop();
            bumperPage = null;
        }

        // close ad
        isClosed = true;
    }

    /**
     * Method that set an ad for the banner - and also sets an ad for the events object
     *
     * @param ad new ad
     */
    public void setAd(SAAd ad) {
        this.ad = ad;
        events.setAd(session, this.ad);
    }

    /**
     * Method that determines if an ad is available
     *
     * @return true or false
     */
    public boolean hasAdAvailable() {
        return ad != null;
    }

    public SAAd getAd() {
        return ad;
    }

    private void showParentalGateIfNeededWithCompletion(final Context context,
                                                        final Runnable completion) {

        if (isParentalGateEnabled) {
            SAParentalGate.setListener(new SAParentalGate.Interface() {
                @Override
                public void parentalGateOpen() {
                    events.triggerPgOpenEvent();
                }

                @Override
                public void parentalGateSuccess() {
                    events.triggerPgSuccessEvent();
                    completion.run();
                }

                @Override
                public void parentalGateFailure() {
                    events.triggerPgFailEvent();
                }

                @Override
                public void parentalGateCancel() {
                    events.triggerPgCloseEvent();
                }
            });

            SAParentalGate.show(context);
        } else {
            completion.run();
        }
    }

    private void showSuperAwesomeWebViewInExternalBrowser(final Context context) {
        Function0<Unit> bumperCallback = () -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ads.superawesome.tv/v2/safead"));
            try {
                context.startActivity(browserIntent);
            } catch (Exception e) {
                Log.d("SuperAwesome", "Couldn't start browser in SABannerAd: " + e.getMessage());
            }
            return null;
        };

        if (isBumperPageEnabled) {
            playBumperPage(getContext(), bumperCallback);
        } else {
            bumperCallback.invoke();
        }
    }

    /**********************************************************************************************
     * Setters & Getters
     **********************************************************************************************/

    public boolean isClosed() {
        return isClosed;
    }

    public void setListener(SAInterface value) {
        listener = value != null ? value : listener;
    }

    public void enableParentalGate() {
        setParentalGate(true);
    }

    public void disableParentalGate() {
        setParentalGate(false);
    }

    public void enableBumperPage() {
        setBumperPage(true);
    }

    public void disableBumperPage() {
        setBumperPage(false);
    }

    public void enableTestMode() {
        setTestMode(true);
    }

    public void disableTestMode() {
        setTestMode(false);
    }

    public void setConfigurationProduction() {
        setConfiguration(SAConfiguration.PRODUCTION);
    }

    public void setConfigurationStaging() {
        setConfiguration(SAConfiguration.STAGING);
    }

    public void setConfigurationDev() {
        setConfiguration(SAConfiguration.DEV);
    }

    public void setColorTransparent() {
        setColor(true);
    }

    public void setColorGray() {
        setColor(false);
    }

    public void setParentalGate(boolean value) {
        isParentalGateEnabled = value;
    }

    public void setBumperPage(boolean value) {
        isBumperPageEnabled = value;
    }

    public void setTestMode(boolean value) {
        session.setTestMode(value);
    }

    public void setConfiguration(SAConfiguration value) {
        session.setConfiguration(value);
    }

    void setBannerListener(SABannerAdListener listener) {
        bannerListener = listener;
    }

    public void setColor(boolean value) {
        if (value) {
            setBackgroundColor(Color.TRANSPARENT);
        } else {
            setBackgroundColor(BANNER_BACKGROUND);
        }
    }
}
