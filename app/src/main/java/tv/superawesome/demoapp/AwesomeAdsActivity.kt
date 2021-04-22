package tv.superawesome.demoapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import kotlinx.android.synthetic.main.activity_awesomeads.*
import tv.superawesome.demoapp.adapter.*
import tv.superawesome.sdk.publisher.common.awesomeAds.AwesomeAds
import tv.superawesome.sdk.publisher.common.models.Configuration
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.common.ui.video.SAVideoAd
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd

class AwesomeAdsActivity : Activity() {
    private val data = listOf(
            HeaderItem("Banners"),
            PlacementItem("Banner image", 61884, Type.BANNER),
            HeaderItem("Interstitials"),
            PlacementItem("Rich Media Interstitial", 61886, Type.INTERSTITIAL),
            PlacementItem("3rd party Tag", 61320, Type.INTERSTITIAL),
            PlacementItem("KSF Tag", 61321, Type.INTERSTITIAL),
            HeaderItem("Videos"),
            PlacementItem("Video", 61885, Type.VIDEO)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_awesomeads)

        AwesomeAds.init(applicationContext, Configuration(
                environment = Environment.Production,
                logging = true))

        initUI()

        configureBannerAd()
        configureInterstitialAd()
        configureVideoAd()

        initButtons()
    }

    private fun initButtons() {
        config1Button.setOnClickListener {
            Log.i("SuperAwesome", "Config 1 selected")
            bannerView.enableParentalGate()
            bannerView.enableBumperPage()

            SAInterstitialAd.enableParentalGate()
            SAInterstitialAd.enableBumperPage()

            SAVideoAd.enableParentalGate()
            SAVideoAd.enableBumperPage()
        }
        config2Button.setOnClickListener {
            Log.i("SuperAwesome", "Config 2 selected")
            bannerView.disableParentalGate()
            bannerView.disableBumperPage()

            SAInterstitialAd.disableParentalGate()
            SAInterstitialAd.disableBumperPage()

            SAVideoAd.disableParentalGate()
            SAVideoAd.disableBumperPage()
        }
    }

    private fun initUI() {
        configureListView()
    }

    private fun configureListView() {
        val adapter = CustomListAdapter<AdapterItem>(this)
        listView.adapter = adapter
        adapter.updateData(data)
        adapter.reloadList()

        listView.onItemClickListener = OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
            (data[position] as? PlacementItem)?.let { item ->
                when (item.type) {
                    Type.BANNER -> bannerView.play(item.pid)
                    Type.INTERSTITIAL -> SAInterstitialAd.load(item.pid, this@AwesomeAdsActivity)
                    Type.VIDEO -> {
                        if (SAVideoAd.hasAdAvailable(item.pid)) {
                            Log.e("AwesomeAds", "PLAYING VIDEO")
                            SAVideoAd.play(item.pid, this@AwesomeAdsActivity)
                        } else {
                            Log.e("AwesomeAds", "LOADING VIDEO")
                            SAVideoAd.load(item.pid, this@AwesomeAdsActivity)
                        }
                    }
                }
            }
        }
    }

    private fun configureVideoAd() {
        SAVideoAd.enableCloseButton()
        SAVideoAd.enableParentalGate()
        SAVideoAd.enableBumperPage()
        SAVideoAd.setListener { placementId, event ->
            Log.i("gunhan", "SAVideoAd event ${event.name} thread:${Thread.currentThread()}")

            if (event == SAEvent.AdLoaded) {
                SAVideoAd.play(placementId, this@AwesomeAdsActivity)
            }
        }
    }

    private fun configureInterstitialAd() {
        SAInterstitialAd.setListener { placementId, event ->
            Log.i("gunhan", "SAInterstitialAd event ${event.name} thread:${Thread.currentThread()}")

            if (event == SAEvent.AdLoaded) {
                SAInterstitialAd.play(placementId, this@AwesomeAdsActivity)
            }
        }
    }

    private fun configureBannerAd() {
        bannerView.enableBumperPage()
        bannerView.enableParentalGate()
        bannerView.visibility = View.VISIBLE
    }
}