package tv.superawesome.demoapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*
import tv.superawesome.demoapp.adapter.*
import tv.superawesome.demoapp.model.SettingsData
import tv.superawesome.lib.sabumperpage.SABumperPage
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterstitialAd
import tv.superawesome.sdk.publisher.SAVersion
import tv.superawesome.sdk.publisher.SAVideoAd
import tv.superawesome.sdk.publisher.state.CloseButtonState

val data = listOf(
    HeaderItem("Banners"),
    PlacementItem("Banner image", 82088, type = Type.BANNER),
    PlacementItem("Banner image Flat Colour", 88001, type = Type.BANNER),
    PlacementItem(
        "Banner Test Multi Id",
        82088,
        lineItemId = 176803,
        creativeId = 499387,
        type = Type.BANNER
    ),
    HeaderItem("Interstitials"),
    PlacementItem("Mobile Interstitial Flat Colour Portrait", 87892, type = Type.INTERSTITIAL),
    PlacementItem("Mobile Interstitial Portrait", 82089, type = Type.INTERSTITIAL),
    PlacementItem("Interstitial via KSF", 84799, type = Type.INTERSTITIAL),
    PlacementItem("Interstitial Flat Colour via KSF", 87970, type = Type.INTERSTITIAL),
    PlacementItem(
        "Interstitial Test Multi Id",
        82089,
        lineItemId = 176803,
        creativeId = 503038,
        type = Type.INTERSTITIAL
    ),
    HeaderItem("Videos"),
    PlacementItem("PopJam VPAID Video", 93969, type = Type.VIDEO),
    PlacementItem("VAST Video Flat Colour", 88406, type = Type.VIDEO),
    PlacementItem("VPAID Video Flat Colour", 89056, type = Type.VIDEO),
    PlacementItem("Direct Video Flat Colour", 87969, type = Type.VIDEO),
    PlacementItem("Direct Video", 82090, type = Type.VIDEO),
    PlacementItem("Vast Video", 84777, lineItemId = 178822, creativeId = 503585, type = Type.VIDEO),
    PlacementItem("VPAID via KSF", 84798, type = Type.VIDEO),
    PlacementItem(
        "Video Test Multi Id",
        82090,
        lineItemId = 176803,
        creativeId = 499385,
        type = Type.VIDEO
    ),
)

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SABumperPage.overrideName("AwesomeAds Demo")

        initUI()

        configureBannerAd()
        configureInterstitialAd()
        configureVideoAd()

        initButtons()
    }

    private fun initButtons() {
        settingsButton.setOnClickListener {
            val dialog = SettingsDialogFragment()
            dialog.show(supportFragmentManager, "settings")
            dialog.onDismissListener = {
                updateSettings()
            }
        }
    }

    private fun getSettings(): SettingsData? = (application as? MyApplication)?.settings

    private fun updateSettings() {
        val settings = getSettings() ?: return
        val config = settings.environment

        bannerView.setConfiguration(config)
        bannerView.setBumperPage(settings.bumperEnabled)
        bannerView.setParentalGate(settings.parentalEnabled)

        SAInterstitialAd.setConfiguration(config)
        SAInterstitialAd.setBumperPage(settings.bumperEnabled)
        SAInterstitialAd.setParentalGate(settings.parentalEnabled)

        SAVideoAd.setConfiguration(config)
        SAVideoAd.setBumperPage(settings.bumperEnabled)
        SAVideoAd.setParentalGate(settings.parentalEnabled)
        SAVideoAd.setMuteOnStart(settings.muteOnStart)

        when (settings.closeButtonState) {
            CloseButtonState.VisibleImmediately -> {
                SAVideoAd.enableCloseButtonNoDelay()
                SAInterstitialAd.enableCloseButtonNoDelay()
            }
            CloseButtonState.VisibleWithDelay -> {
                SAVideoAd.enableCloseButton()
                SAInterstitialAd.enableCloseButton()
            }
            CloseButtonState.Hidden -> SAVideoAd.disableCloseButton()
        }
    }

    private fun initUI() {
        val title = "AwesomeAds: v${SAVersion.getSDKVersionNumber()}"
        titleTextView.text = title
        configureListView()
    }

    private fun configureListView() {
        val adapter = CustomListAdapter<AdapterItem>(this)
        listView.adapter = adapter
        adapter.updateData(data)
        adapter.reloadList()

        listView.setOnItemClickListener { _, _, position, _ ->
            (data[position] as? PlacementItem)?.let { item ->
                when (item.type) {
                    Type.BANNER -> {
                        if (item.isFull()) {
                            bannerView.load(
                                item.placementId,
                                item.lineItemId ?: 0,
                                item.creativeId ?: 0
                            )
                        } else {
                            bannerView.load(item.placementId)
                        }
                    }
                    Type.INTERSTITIAL -> {
                        if (item.isFull()) {
                            SAInterstitialAd.load(
                                item.placementId,
                                item.lineItemId ?: 0,
                                item.creativeId ?: 0,
                                this@MainActivity
                            )
                        } else {
                            SAInterstitialAd.load(item.placementId, this@MainActivity)
                        }
                    }
                    Type.VIDEO -> {
                        if (item.isFull()) {
                            SAVideoAd.load(
                                item.placementId,
                                item.lineItemId ?: 0,
                                item.creativeId ?: 0,
                                this@MainActivity
                            )
                        } else {
                            SAVideoAd.load(item.placementId, this@MainActivity)
                        }
                    }
                }
            }
        }
    }

    private fun configureVideoAd() {
        SAVideoAd.enableCloseButton()
        SAVideoAd.setListener { placementId, event ->
            updateMessage(placementId, event)
            Log.i(TAG, "SAVideoAd event ${event.name} thread:${Thread.currentThread()}")

            if (event == SAEvent.adLoaded && isPlayEnabled()) {
                SAVideoAd.play(placementId, this@MainActivity)
            }
        }
    }

    private fun configureInterstitialAd() {
        SAInterstitialAd.setListener { placementId, event ->
            updateMessage(placementId, event)
            Log.i(TAG, "SAInterstitialAd event ${event.name} thread:${Thread.currentThread()}")

            if (event == SAEvent.adLoaded && isPlayEnabled()) {
                SAInterstitialAd.play(placementId, this@MainActivity)
            }
        }
    }

    private fun configureBannerAd() {
        bannerView.visibility = View.VISIBLE
        bannerView.setListener { placementId, event ->
            updateMessage(placementId, event)

            if (event == SAEvent.adLoaded && isPlayEnabled()) {
                bannerView.play(this@MainActivity)
            }
        }
    }

    private fun isPlayEnabled(): Boolean {
        return getSettings()?.playEnabled == true
    }

    private fun updateMessage(placementId: Int, event: SAEvent) {
        val originalMessage = subtitleTextView.text
        val message = "$originalMessage $placementId $event"
        subtitleTextView.text = message
    }
}

private const val TAG = "AwesomeAds"