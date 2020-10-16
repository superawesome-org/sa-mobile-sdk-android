package tv.superawesome.demoapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import kotlinx.android.synthetic.main.activity_awesomeads.*
import tv.superawesome.demoapp.adapter.*
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.core.AwesomeAdsSdk
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd
import tv.superawesome.sdk.publisher.ui.video.SAVideoAd

class AwesomeAdsActivity : Activity() {
    private val data = listOf(
            HeaderItem("Banners"),
            PlacementItem("Banner image", 44258, Type.BANNER),
            HeaderItem("Interstitials"),
            PlacementItem("Rich Media Interstitial", 44259, Type.INTERSTITIAL),
            PlacementItem("3rd party Tag", 5393, Type.INTERSTITIAL),
            PlacementItem("KSF Tag", 5387, Type.INTERSTITIAL),
            HeaderItem("Videos"),
            PlacementItem("Video", 39318, Type.VIDEO)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_awesomeads)

        AwesomeAdsSdk.initSdk(applicationContext, AwesomeAdsSdk.Configuration(
                environment = Environment.production,
                logging = true))

        initUI()

        configureBannerAd()
        configureInterstitialAd()
        configureVideoAd()

        requestBannerAd()
        requestInterstitialAd()
        requestVideoAd()
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
            val item = data[position]
            if (item is PlacementItem) {
                when (item.type) {
                    Type.BANNER -> bannerView.load(item.pid)
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
        SAVideoAd.setListener(object : SAInterface {
            override fun onEvent(placementId: Int, event: SAEvent) {
                Log.i("gunhan", "SAVideoAd event ${event.name} thread:${Thread.currentThread()}")

                if (event == SAEvent.adLoaded) {
                    SAVideoAd.play(placementId, this@AwesomeAdsActivity)
                }
            }
        })
    }

    private fun configureInterstitialAd() {
        SAInterstitialAd.setListener(object : SAInterface {
            override fun onEvent(placementId: Int, event: SAEvent) {
                Log.i("gunhan", "SAInterstitialAd event ${event.name} thread:${Thread.currentThread()}")

                if (event == SAEvent.adLoaded) {
                    SAInterstitialAd.play(placementId, this@AwesomeAdsActivity)
                }
            }
        })
    }

    private fun configureBannerAd() {
        bannerView.enableBumperPage()
        bannerView.enableParentalGate()
        bannerView.setListener(object : SAInterface {
            override fun onEvent(placementId: Int, event: SAEvent) {
                Log.i("gunhan", "bannerView event ${event.name} thread:${Thread.currentThread()}")

                if (event == SAEvent.adLoaded) {
                    bannerView.play()
                }
            }
        })
    }

    private fun requestBannerAd() {
    }

    private fun requestInterstitialAd() {
    }

    private fun requestVideoAd() {

    }

    fun playInterstitial(view: View?) {

    }

    fun playVideo(view: View?) {

    }
}