package tv.superawesome.sdk.publisher

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
    interface Listener {
        fun didRequestPlaybackPause()
        fun didRequestPlaybackResume()
    }

    var listener: Listener? = null

    private var currentClickThreshold = 0L
    private var bumperPage: SABumperPage? = null

    fun handleSafeAdClick(view: View) {
        val context = view.context
        val clickRunner = Runnable { showSuperAwesomeWebViewInExternalBrowser(context) }
        showParentalGateIfNeededWithCompletion(context, clickRunner)
    }

    private fun showSuperAwesomeWebViewInExternalBrowser(context: Context?) {
        if (context == null) {
            Log.d("SuperAwesome", "Couldn't start browser in SAVideoClick: Context is null")
            return
        }
        if (isBumperPageEnabled) {
            playBumperPage(context) {
                listener?.didRequestPlaybackResume()
                navigateToUrl(PADLOCK_URL, context)
            }
        } else {
            navigateToUrl(PADLOCK_URL, context)
        }
    }

    private fun playBumperPage(context: Context, onFinish: (() -> Unit)) {
        listener?.didRequestPlaybackPause()
        bumperPage?.stop()
        bumperPage = SABumperPage()
        bumperPage?.onFinish = onFinish
        bumperPage?.show(context)
    }

    private fun navigateToUrl(url: String, context: Context) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: Exception) {
            Log.d("SuperAwesome", "Couldn't start browser in SAVideoClick: " + e.message)
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

    fun close() {
        bumperPage?.stop()
        bumperPage = null
    }

    private fun showParentalGateIfNeededWithCompletion(
        context: Context,
        completion: Runnable
    ) {
        if (isParentalGateEnabled) {
            SAParentalGate.setListener(object : SAParentalGate.Interface {
                override fun parentalGateOpen() {
                    listener?.didRequestPlaybackPause()
                    events.triggerPgOpenEvent()
                }

                override fun parentalGateSuccess() {
                    listener?.didRequestPlaybackResume()
                    events.triggerPgSuccessEvent()
                    completion.run()
                }

                override fun parentalGateFailure() {
                    listener?.didRequestPlaybackResume()
                    events.triggerPgFailEvent()
                }

                override fun parentalGateCancel() {
                    listener?.didRequestPlaybackResume()
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
            playBumperPage(context) {
                listener?.didRequestPlaybackResume()
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
            Log.d("SuperAwesome", "Current diff is $diff")
            return
        }
        currentClickThreshold = currentTime
        Log.d("SuperAwesome", "Going to $destination")

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
            Log.d("SuperAwesome", "Couldn't start browser in SAVideoClick: " + e.message)
        }
    }
}

const val PADLOCK_URL = "https://ads.superawesome.tv/v2/safead"

