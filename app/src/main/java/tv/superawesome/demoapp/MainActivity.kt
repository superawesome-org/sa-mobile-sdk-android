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
    PlacementItem("Banner Test Multi Id", 82088, lineItemId = 176803, creativeId = 499387, type = Type.BANNER),
    HeaderItem("Interstitials"),
    PlacementItem("Mobile Interstitial Flat Colour Portrait", 87892, type = Type.INTERSTITIAL),
    PlacementItem("Mobile Interstitial Portrait", 82089, type = Type.INTERSTITIAL),
    PlacementItem("Interstitial via KSF", 84799, type = Type.INTERSTITIAL),
    PlacementItem("Interstitial Flat Colour via KSF", 87970, type = Type.INTERSTITIAL),
    PlacementItem("Interstitial Test Multi Id", 82089, lineItemId = 176803, creativeId = 503038, type = Type.INTERSTITIAL),
    HeaderItem("Videos"),
    PlacementItem("VAST Video Flat Colour", 88406, type = Type.VIDEO),
    PlacementItem("VPAID Video Flat Colour", 89056, type = Type.VIDEO),
    PlacementItem("Direct Video Flat Colour", 87969, type = Type.VIDEO),
    PlacementItem("Direct Video", 82090, type = Type.VIDEO),
    PlacementItem("Vast Video", 84777, lineItemId = 178822, creativeId = 503585, type = Type.VIDEO),
    PlacementItem("VPAID via KSF", 84798, type = Type.VIDEO),
    PlacementItem("Video Test Multi Id", 82090, lineItemId = 176803, creativeId = 499385, type = Type.VIDEO),
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
        config1Button.setOnClickListener {
            Log.i(TAG, "Config 1 selected")
            bannerView.enableParentalGate()
            bannerView.enableBumperPage()

            SAInterstitialAd.enableParentalGate()
            SAInterstitialAd.enableBumperPage()

            SAVideoAd.enableParentalGate()
            SAVideoAd.enableBumperPage()
            SAVideoAd.enableCloseButton()
        }
        config2Button.setOnClickListener {
            Log.i(TAG, "Config 2 selected")
            bannerView.disableParentalGate()
            bannerView.disableBumperPage()

            SAInterstitialAd.disableParentalGate()
            SAInterstitialAd.disableBumperPage()

            SAVideoAd.disableParentalGate()
            SAVideoAd.disableBumperPage()
            SAVideoAd.enableCloseButtonNoDelay()
        }
        settingsButton.setOnClickListener {
            val dialog = SettingsDialogFragment()
            dialog.show(supportFragmentManager, "settings")
            dialog.onDismissListener = {
                updateSettings()
            }
        }
    }

    private fun updateSettings() {
        val app = application as? MyApplication ?: return
        val config = app.settings.environment
        bannerView.setConfiguration(config)
        SAInterstitialAd.setConfiguration(config)
        SAVideoAd.setConfiguration(config)
        when (app.settings.closeButtonState) {
            CloseButtonState.VisibleImmediately -> {
                SAVideoAd.enableCloseButtonNoDelay()
            }
            CloseButtonState.VisibleWithDelay -> {
                SAVideoAd.enableCloseButton()
            }
            CloseButtonState.Hidden -> {
                SAVideoAd.disableCloseButton()
            }
        }
    }

    private fun initUI() {
        val title = "AwesomeAds.version: ${SAVersion.getSDKVersion(null)}"
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
                        if (SAVideoAd.hasAdAvailable(item.placementId)) {
                            Log.i(TAG, "PLAYING VIDEO")
                            SAVideoAd.play(item.placementId, this@MainActivity)
                        } else {
                            Log.i(TAG, "LOADING VIDEO")
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
    }

    private fun configureVideoAd() {
        SAVideoAd.enableCloseButton()
        SAVideoAd.setListener { placementId, event ->
            updateMessage(placementId, event)
            Log.i(TAG, "SAVideoAd event ${event.name} thread:${Thread.currentThread()}")

            if (event == SAEvent.adLoaded) {
                SAVideoAd.play(placementId, this@MainActivity)
            }
        }
    }

    private fun configureInterstitialAd() {
        SAInterstitialAd.setListener { placementId, event ->
            updateMessage(placementId, event)
            Log.i(TAG, "SAInterstitialAd event ${event.name} thread:${Thread.currentThread()}")

            if (event == SAEvent.adLoaded) {
                SAInterstitialAd.play(placementId, this@MainActivity)
            }
        }
    }

    private fun configureBannerAd() {
        bannerView.visibility = View.VISIBLE
        bannerView.setListener { placementId, event ->
            updateMessage(placementId, event)

            if (event == SAEvent.adLoaded) {
                bannerView.play(this@MainActivity)
            }
        }
    }

    private fun updateMessage(placementId: Int, event: SAEvent) {
        val message = "$placementId $event"
        subtitleTextView.text = message
    }
}

private const val TAG = "AwesomeAds"