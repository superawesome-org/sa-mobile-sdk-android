package tv.superawesome.demoapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main_2.*
import tv.superawesome.demoapp.adapter.AdapterItem
import tv.superawesome.demoapp.adapter.CustomListAdapter
import tv.superawesome.demoapp.adapter.PlacementItem
import tv.superawesome.demoapp.adapter.Type
import tv.superawesome.sdk.publisher.SAVersion
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.ui.common.BumperPageActivity
import tv.superawesome.sdk.publisher.common.ui.interstitial.SAInterstitialAd
import tv.superawesome.sdk.publisher.common.ui.video.SAVideoAd

class MainActivity2 : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_2)

        BumperPageActivity.overrideName("AwesomeAds SDK Demo")

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
            // SAVideoAd.enableCloseButtonNoDelay()
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
                                this@MainActivity2
                            )
                        } else {
                            SAInterstitialAd.load(item.placementId, this@MainActivity2)
                        }
                    }
                    Type.VIDEO -> {
                        if(item.isFull()){
                            SAVideoAd.load(
                                item.placementId,
                                item.lineItemId ?: 0,
                                item.creativeId ?: 0,
                                this@MainActivity2
                            )
                        } else {
                            SAVideoAd.load(item.placementId, this@MainActivity2)
                        }
                    }
                }
            }
        }
    }

    private fun configureVideoAd() {
        SAVideoAd.enableCloseButton()
        SAVideoAd.setListener { placementId, event ->
            Log.i(TAG, "SAVideoAd event ${event.name} thread:${Thread.currentThread()}")

            if (event == SAEvent.AdLoaded) {
                SAVideoAd.play(placementId, this@MainActivity2)
            }
        }
    }

    private fun configureInterstitialAd() {
        SAInterstitialAd.setListener { placementId, event ->
            Log.i(TAG, "SAInterstitialAd event ${event.name} thread:${Thread.currentThread()}")

            if (event == SAEvent.AdLoaded) {
                SAInterstitialAd.play(placementId, this@MainActivity2)
            }
        }
    }

    private fun configureBannerAd() {
        bannerView.visibility = View.VISIBLE
        bannerView.setListener { _, event ->
            if (event == SAEvent.AdLoaded) {
                bannerView.play()
            }
        }
    }
}

private const val TAG = "AwesomeAds"