package tv.superawesome.demoapp.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*
import tv.superawesome.demoapp.MyApplication
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.SettingsDialogFragment
import tv.superawesome.demoapp.adapter.CustomListAdapter
import tv.superawesome.demoapp.model.FeatureType
import tv.superawesome.demoapp.model.SettingsData
import tv.superawesome.lib.sabumperpage.SABumperPage
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterstitialAd
import tv.superawesome.sdk.publisher.SAVersion
import tv.superawesome.sdk.publisher.SAVideoAd
import tv.superawesome.sdk.publisher.state.CloseButtonState

class MainActivity : FragmentActivity() {
    private lateinit var adapter: CustomListAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SABumperPage.overrideName("AwesomeAds Demo")

        initUI()

        configureDataSource()
        configureBannerAd()
        configureInterstitialAd()
        configureVideoAd()

        initButtons()
        updateSettings()
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

    private fun initUI() {
        val title = "AwesomeAds: v${SAVersion.getSDKVersionNumber()}"
        titleTextView.text = title
        configureListView()
    }

    private fun getSettings(): SettingsData? = (application as? MyApplication)?.settings

    private fun isPlayEnabled(): Boolean {
        return getSettings()?.playEnabled == true
    }

    private fun configureDataSource() {
        viewModel.items.observe(this) {
            adapter.updateData(it)
            adapter.reloadList()
        }
    }

    private fun configureListView() {
        adapter = CustomListAdapter(this)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val item = adapter.getItem(position)
            when (item.type) {
                FeatureType.BANNER -> {
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
                FeatureType.INTERSTITIAL -> {
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
                FeatureType.VIDEO -> {
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

    private fun updateMessage(placementId: Int, event: SAEvent) {
        val settings = getSettings() ?: return

        if (settings.environment == SAConfiguration.UITESTING) {
            val originalMessage = subtitleTextView.text
            val message = "$originalMessage $placementId $event"
            subtitleTextView.text = message
        }
    }
}

private const val TAG = "AwesomeAds"
