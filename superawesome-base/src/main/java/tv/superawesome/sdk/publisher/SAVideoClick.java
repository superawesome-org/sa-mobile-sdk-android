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

public class SAVideoClick implements View.OnClickListener {

    private SAAd ad;
    private boolean isParentalGateEnabled;
    private boolean isBumperPageEnabled;
    private SAEvents events;

    private Long currentClickThreshold = 0L;

    private Listener listener;

    SAVideoClick(SAAd ad,
                 boolean isParentalGateEnabled,
                 boolean isBumperPageEnabled,
                 SAEvents events) {
        this.ad = ad;
        this.isParentalGateEnabled = isParentalGateEnabled;
        this.isBumperPageEnabled = isBumperPageEnabled;
        this.events = events;
    }

    @Override
    public void onClick(View view) {
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
            if (isParentalGateEnabled) {
                SAParentalGate.setListener(new SAParentalGate.Interface() {
                    @Override
                    public void parentalGateOpen() {
                        SAVideoAd.control.pause();
                        events.triggerPgOpenEvent();
                    }

                    @Override
                    public void parentalGateSuccess() {
                        events.triggerPgSuccessEvent();
                        click(context, destinationUrl);
                        SAVideoAd.control.pause();
                    }

                    @Override
                    public void parentalGateFailure() {
                        events.triggerPgFailEvent();
                        SAVideoAd.control.start();
                    }

                    @Override
                    public void parentalGateCancel() {
                        events.triggerPgCloseEvent();
                        SAVideoAd.control.start();
                    }
                });

                SAParentalGate.show(context);
            } else {
                click(context, destinationUrl);
            }
        }
    }

    /**
     * Method that handles a click on the ad surface
     */
    private void click(final Context context, final String destination) {

        if (isBumperPageEnabled || ad.creative.bumper) {
            SABumperPage.setListener(new SABumperPage.Interface() {
                @Override
                public void didEndBumper() {
                    handleUrl(context, destination);
                }
            });

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
        Long diff = Math.abs(currentTime - currentClickThreshold);

        if (diff < SADefaults.defaultClickThreshold()) {
            Log.d("AwesomeAds-2", "Current diff is " + diff);
            return;
        }

        currentClickThreshold = currentTime;

        Log.d("AwesomeAds-2", "Going to " + destination);

        // get local
        // call listener
        if (listener != null) {
            listener.onClick();
        } else {
            Log.w("AwesomeAds", "Video Ad listener not implemented. Should have been adClicked");
        }

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

    void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
        void onClick();
    }
}
