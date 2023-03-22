package tv.superawesome.sdk.publisher

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import tv.superawesome.lib.sabumperpage.SABumperPage
import tv.superawesome.lib.saevents.SAEvents
import tv.superawesome.lib.samodelspace.saad.SAAd
import tv.superawesome.lib.samodelspace.saad.SACampaignType
import tv.superawesome.lib.saparentalgate.SAParentalGate
import kotlin.math.abs

class SAVideoClick internal constructor(
    private val ad: SAAd,
    private val isParentalGateEnabled: Boolean,
    private val isBumperPageEnabled: Boolean,
    private val events: SAEvents,
) {
    private var currentClickThreshold = 0L
    fun handleSafeAdClick(view: View) {
        val context = view.context
        val clickRunner = Runnable { showSuperAwesomeWebViewInExternalBrowser(context) }
        showParentalGateIfNeededWithCompletion(context, clickRunner)
    }

    private fun showSuperAwesomeWebViewInExternalBrowser(context: Context?) {
        var uri: Uri? = null
        try {
            val PADLOCK_URL = "https://ads.superawesome.tv/v2/safead"
            uri = Uri.parse(PADLOCK_URL)
        } catch (ignored: Exception) {
        }
        if (context != null && uri != null) {
            val safeUri: Uri = uri
            val bumperCallback = SABumperPage.Interface {
                val browserIntent = Intent(Intent.ACTION_VIEW, safeUri)
                try {
                    context.startActivity(browserIntent)
                } catch (e: Exception) {
                    Log.d("SuperAwesome", "Couldn't start browser in SAVideoClick: " + e.message)
                }
            }
            if (isBumperPageEnabled) {
                SABumperPage.setListener(bumperCallback)
                SABumperPage.play(context as Activity?)
            } else {
                bumperCallback.didEndBumper()
            }
        }
    }

    fun handleAdClick(view: View, url: String?) {
        // check to see if there is a click through url

        // if the campaign is a CPI one, get the normal CPI url so that
        // we can append the "referrer data" to it (since most likely
        // "click_through" will have a redirect)
        val destinationUrl: String? = if (ad.campaignType == SACampaignType.CPI) {
            ad.creative.clickUrl
        } else url ?: events.vastClickThroughEvent
        val context = view.context
        if (destinationUrl != null && context != null) {
            // check for parental gate on click
            val clickRunner = Runnable { click(context, destinationUrl) }
            showParentalGateIfNeededWithCompletion(context, clickRunner)
        }
    }

    private fun showParentalGateIfNeededWithCompletion(
        context: Context,
        completion: Runnable
    ) {
        if (isParentalGateEnabled) {
            SAParentalGate.setListener(object : SAParentalGate.Interface {
                override fun parentalGateOpen() {
                    events.triggerPgOpenEvent()
                }

                override fun parentalGateSuccess() {
                    events.triggerPgSuccessEvent()
                    completion.run()
                }

                override fun parentalGateFailure() {
                    events.triggerPgFailEvent()
                }

                override fun parentalGateCancel() {
                    events.triggerPgCloseEvent()
                }
            })
            SAParentalGate.show(context)
        } else {
            completion.run()
        }
    }

    /**
     * Method that handles a click on the ad surface
     */
    private fun click(context: Context, destination: String) {
        if (isBumperPageEnabled) {
            SABumperPage.setListener { handleUrl(context, destination) }
            if (context is Activity) {
                SABumperPage.play(context)
            } else {
                handleUrl(context, destination)
            }
        } else {
            handleUrl(context, destination)
        }
    }

    private fun handleUrl(context: Context, destination: String) {
        var destination = destination
        val currentTime = System.currentTimeMillis() / 1000
        val diff = abs(currentTime - currentClickThreshold)
        if (diff < SADefaults.defaultClickThreshold()) {
            Log.d("AwesomeAds-2", "Current diff is $diff")
            return
        }
        currentClickThreshold = currentTime
        Log.d("AwesomeAds-2", "Going to $destination")

        // send vast click tracking events
        events.triggerVASTClickTrackingEvent()

        // send only in case of CPI where we'll use the direct click url
        if (ad?.campaignType == SACampaignType.CPI) {
            events.triggerVASTClickThroughEvent()
        }

        // if it's a CPI campaign
        destination += if (ad.campaignType == SACampaignType.CPI) "&referrer=" + ad.creative.referral.writeToReferralQuery() else ""

        // start browser
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(destination)))
        } catch (e: Exception) {
            // do nothing
        }
    }
}
