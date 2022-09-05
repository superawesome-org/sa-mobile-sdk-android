package tv.superawesome.sdk.publisher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import tv.superawesome.lib.sabumperpage.SABumperPage;
import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACampaignType;
import tv.superawesome.lib.saparentalgate.SAParentalGate;

public class SAVideoClick {

    private final SAAd ad;
    private final boolean isParentalGateEnabled;
    private final boolean isBumperPageEnabled;
    private final SAEvents events;

    private Long currentClickThreshold = 0L;

    SAVideoClick(SAAd ad,
                 boolean isParentalGateEnabled,
                 boolean isBumperPageEnabled,
                 SAEvents events) {
        this.ad = ad;
        this.isParentalGateEnabled = isParentalGateEnabled;
        this.isBumperPageEnabled = isBumperPageEnabled;
        this.events = events;
    }

    public void handleSafeAdClick(View view) {
        final Context context = view.getContext();

        Runnable clickRunner = () -> showSuperAwesomeWebViewInExternalBrowser(context);
        showParentalGateIfNeededWithCompletion(context, clickRunner);
    }

    private void showSuperAwesomeWebViewInExternalBrowser(final Context context) {
        Uri uri = null;
        try {
            String PADLOCK_URL = "https://ads.superawesome.tv/v2/safead";
            uri = Uri.parse(PADLOCK_URL);
        } catch (Exception ignored) {}

        if (context != null && uri != null) {
            final Uri safeUri = uri;

            SABumperPage.Interface bumperCallback = () -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, safeUri);
                context.startActivity(browserIntent);
            };

            if (isBumperPageEnabled) {
                SABumperPage.setListener(bumperCallback);
                SABumperPage.play((Activity)context);
            } else {
                bumperCallback.didEndBumper();
            }
        }
    }

    public void handleAdClick(View view) {
        // check to see if there is a click through url
        final String destinationUrl;

        // if the campaign is a CPI one, get the normal CPI url so that
        // we can append the "referrer data" to it (since most likely
        // "click_through" will have a redirect)
        if (ad.campaignType == SACampaignType.CPI) {
            destinationUrl = ad.creative.clickUrl;
        } else {
            destinationUrl = events.getVASTClickThroughEvent();
        }

        final Context context = view.getContext();

        if (destinationUrl != null && context != null) {
            // check for parental gate on click
            Runnable clickRunner = () -> click(context, destinationUrl);
            showParentalGateIfNeededWithCompletion(context, clickRunner);
        }
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

    /**
     * Method that handles a click on the ad surface
     */
    private void click(final Context context, final String destination) {

        if (isBumperPageEnabled) {
            SABumperPage.setListener(() -> handleUrl(context, destination));

            if (context instanceof Activity) {
                SABumperPage.play((Activity)context);
            } else {
                handleUrl(context, destination);
            }
        } else {
            handleUrl(context, destination);
        }
    }

    private void handleUrl (Context context, String destination) {

        Long currentTime = System.currentTimeMillis()/1000;
        long diff = Math.abs(currentTime - currentClickThreshold);

        if (diff < SADefaults.defaultClickThreshold()) {
            Log.d("AwesomeAds-2", "Current diff is " + diff);
            return;
        }

        currentClickThreshold = currentTime;

        Log.d("AwesomeAds-2", "Going to " + destination);

        // send vast click tracking events
        events.triggerVASTClickTrackingEvent();

        // send only in case of CPI where we'll use the direct click url
        if (ad.campaignType == SACampaignType.CPI) {
            events.triggerVASTClickThroughEvent();
        }

        // if it's a CPI campaign
        destination += ad.campaignType == SACampaignType.CPI ? ("&referrer=" + ad.creative.referral.writeToReferralQuery()) : "";

        // start browser
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(destination)));
        } catch (Exception e) {
            // do nothing
        }
    }
}