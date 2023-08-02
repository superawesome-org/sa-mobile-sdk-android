package tv.superawesome.demoapp.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import tv.superawesome.demoapp.MyApplication
import tv.superawesome.demoapp.databinding.ActivityMainBinding
import tv.superawesome.demoapp.model.FeatureType
import tv.superawesome.demoapp.model.PlacementItem
import tv.superawesome.demoapp.settings.DataStore
import tv.superawesome.demoapp.settings.SettingsDialogFragment
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.sdk.publisher.SABannerAd.SABannerAdListener
import tv.superawesome.sdk.publisher.common.models.CloseButtonState
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.common.sdk.AwesomeAds
import tv.superawesome.sdk.publisher.common.ui.interstitial.SAInterstitialAd
import tv.superawesome.sdk.publisher.common.ui.video.SAVideoAd

class MainActivity : FragmentActivity() {
    private lateinit var adapter: CustomListAdapter
    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        observeSettings()
        setListeners()

        initButtons()
        updateSettings()
    }

    private fun initButtons() {
        binding.settingsButton.setOnClickListener {
            val dialog = SettingsDialogFragment()
            dialog.show(supportFragmentManager, "settings")
            dialog.onDismissListener = {
                updateSettings()
            }
        }
        binding.settingsButton.contentDescription = "AdList.Buttons.Settings"
    }

    private fun initUI() {
        val title = "AwesomeAds: v${AwesomeAds.info()?.versionNumber}"
        binding.titleTextView.text = title
        setupListView()
    }

    private fun isPlayEnabled(): Boolean {
        return DataStore.data.playEnabled
    }

    private fun observeSettings() {
        viewModel.items.observe(this) {
            adapter.updateData(it)
            adapter.reloadList()
        }
    }

    private fun setListeners() {
        binding.bannerView.setListener { placementId, event ->
            updateMessage(placementId, event)

            if (event == SAEvent.adLoaded && isPlayEnabled()) {
                binding.bannerView.play()
            }
        }

        SAInterstitialAd.setListener { placementId, event ->
            updateMessage(placementId, event)
            Log.i(TAG, "SAInterstitialAd event ${event.name} thread:${Thread.currentThread()}")

            if (event == SAEvent.adLoaded && isPlayEnabled()) {
                SAInterstitialAd.play(placementId, this@MainActivity)
            }
        }

        SAVideoAd.setListener { placementId, event ->
            updateMessage(placementId, event)
            Log.i(TAG, "SAVideoAd event ${event.name} thread:${Thread.currentThread()}")

            if (event == SAEvent.adLoaded && isPlayEnabled()) {
                SAVideoAd.play(placementId, this@MainActivity)
            }
        }

        setListenersForBase()
    }

    private fun setupListView() {
        adapter = CustomListAdapter(this)
        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { _, _, position, _ ->
            val item = adapter.getItem(position)
            when (item.type) {
                FeatureType.BANNER -> onBannerClick(item)
                FeatureType.INTERSTITIAL -> onInterstitialClick(item)
                FeatureType.VIDEO -> onVideoClick(item)
            }
        }
    }

    private fun onBannerClick(item: PlacementItem) {
        if (DataStore.data.useBaseModule) {
            onBannerClickForBase(item)
            return
        }
        if (item.isFull()) {
            binding.bannerView.load(
                item.placementId,
                item.lineItemId ?: 0,
                item.creativeId ?: 0
            )
        } else {
            binding.bannerView.load(item.placementId)
        }
    }

    private fun onInterstitialClick(item: PlacementItem) {
        if (DataStore.data.useBaseModule) {
            onInterstitialClickForBase(item)
            return
        }
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

    private fun onVideoClick(item: PlacementItem) {
        if (DataStore.data.useBaseModule) {
            onVideoClickForBase(item)
            return
        }
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

    private fun onBannerClickForBase(item: PlacementItem) {
        if (item.isFull()) {
            binding.bannerView2.load(
                item.placementId,
                item.lineItemId ?: 0,
                item.creativeId ?: 0
            )
        } else {
            binding.bannerView2.load(item.placementId)
        }
    }

    private fun onInterstitialClickForBase(item: PlacementItem) {
        if (item.isFull()) {
            tv.superawesome.sdk.publisher.SAInterstitialAd.load(
                item.placementId,
                item.lineItemId ?: 0,
                item.creativeId ?: 0,
                this@MainActivity
            )
        } else {
            tv.superawesome.sdk.publisher.SAInterstitialAd.load(item.placementId, this@MainActivity)
        }
    }

    private fun onVideoClickForBase(item: PlacementItem) {
        if (item.isFull()) {
            tv.superawesome.sdk.publisher.SAVideoAd.load(
                item.placementId,
                item.lineItemId ?: 0,
                item.creativeId ?: 0,
                this@MainActivity
            )
        } else {
            tv.superawesome.sdk.publisher.SAVideoAd.load(item.placementId, this@MainActivity)
        }
    }

    private fun updateSettings() {
        val settings = DataStore.data

        binding.bannerView.visibility = View.GONE
        binding.bannerView2.visibility = View.GONE

        if (settings.useBaseModule) {
            updateSettingsForBase()
        } else {
            updateSettingsForCommon()
        }
    }

    private fun updateSettingsForCommon() {
        Log.i(TAG, "updateSettingsForCommon")

        val settings = DataStore.data

        binding.bannerView.visibility = View.VISIBLE
        binding.bannerView.setBumperPage(settings.bumperEnabled)
        binding.bannerView.setParentalGate(settings.parentalEnabled)

        SAInterstitialAd.setBumperPage(settings.bumperEnabled)
        SAInterstitialAd.setParentalGate(settings.parentalEnabled)

        SAVideoAd.setBumperPage(settings.bumperEnabled)
        SAVideoAd.setParentalGate(settings.parentalEnabled)
        SAVideoAd.setMuteOnStart(settings.muteOnStart)
        SAVideoAd.setCloseButtonWarning(settings.videoWarnOnClose)
        SAVideoAd.setCloseAtEnd(settings.closeAtEnd)

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

    private fun setListenersForBase() {
        tv.superawesome.sdk.publisher.SAVideoAd.setListener { placementId, event ->
            updateMessage(placementId, event)
            Log.i(TAG, "SAVideoAd event ${event.name} thread:${Thread.currentThread()}")

            if (event == tv.superawesome.sdk.publisher.SAEvent.adLoaded && isPlayEnabled()) {
                tv.superawesome.sdk.publisher.SAVideoAd.play(placementId, this@MainActivity)
            }
        }

        tv.superawesome.sdk.publisher.SAInterstitialAd.setListener { placementId, event ->
            updateMessage(placementId, event)
            Log.i(TAG, "SAInterstitialAd event ${event.name} thread:${Thread.currentThread()}")

            if (event == tv.superawesome.sdk.publisher.SAEvent.adLoaded && isPlayEnabled()) {
                tv.superawesome.sdk.publisher.SAInterstitialAd.play(placementId, this@MainActivity)
            }
        }

        binding.bannerView2.setListener { placementId, event ->
            updateMessage(placementId, event)
            Log.i(TAG, "bannerView2 event ${event.name} thread:${Thread.currentThread()}")

            if (event == tv.superawesome.sdk.publisher.SAEvent.adLoaded && isPlayEnabled()) {
                binding.bannerView2.play(this)
            }
        }

        binding.bannerView2.setBannerListener( object : SABannerAdListener {
            override fun hasLoaded() {

            }

            override fun hasBeenVisible() {

            }

            override fun failedToShow() {

            }
        })
    }

    private fun updateSettingsForBase() {
        Log.i(TAG, "updateSettingsForBase")

        val settings = DataStore.data
        val environment = if ((application as MyApplication).environment == Environment.UITesting)
            SAConfiguration.UITESTING else SAConfiguration.PRODUCTION

        binding.bannerView2.visibility = View.VISIBLE
        binding.bannerView2.setBumperPage(settings.bumperEnabled)
        binding.bannerView2.setParentalGate(settings.parentalEnabled)
        binding.bannerView2.setConfiguration(environment)

        tv.superawesome.sdk.publisher.SAInterstitialAd.setBumperPage(settings.bumperEnabled)
        tv.superawesome.sdk.publisher.SAInterstitialAd.setParentalGate(settings.parentalEnabled)
        tv.superawesome.sdk.publisher.SAInterstitialAd.setConfiguration(environment)

        tv.superawesome.sdk.publisher.SAVideoAd.setBumperPage(settings.bumperEnabled)
        tv.superawesome.sdk.publisher.SAVideoAd.setParentalGate(settings.parentalEnabled)
        tv.superawesome.sdk.publisher.SAVideoAd.setMuteOnStart(settings.muteOnStart)
        tv.superawesome.sdk.publisher.SAVideoAd.setCloseButtonWarning(settings.videoWarnOnClose)
        tv.superawesome.sdk.publisher.SAVideoAd.setCloseAtEnd(settings.closeAtEnd)
        tv.superawesome.sdk.publisher.SAVideoAd.setConfiguration(environment)

        when (settings.closeButtonState) {
            CloseButtonState.VisibleImmediately -> {
                tv.superawesome.sdk.publisher.SAVideoAd.enableCloseButtonNoDelay()
                tv.superawesome.sdk.publisher.SAInterstitialAd.enableCloseButtonNoDelay()
            }

            CloseButtonState.VisibleWithDelay -> {
                tv.superawesome.sdk.publisher.SAVideoAd.enableCloseButton()
                tv.superawesome.sdk.publisher.SAInterstitialAd.enableCloseButton()
            }

            CloseButtonState.Hidden -> tv.superawesome.sdk.publisher.SAVideoAd.disableCloseButton()
        }
    }

    private fun updateMessage(placementId: Int, event: SAEvent) {
        val originalMessage =binding. subtitleTextView.text
        val message = "$originalMessage $placementId $event"
        binding.subtitleTextView.text = message
    }

    private fun updateMessage(placementId: Int, event: tv.superawesome.sdk.publisher.SAEvent) {
        val originalMessage = binding.subtitleTextView.text
        val message = "$originalMessage $placementId $event"
        binding.subtitleTextView.text = message
    }
}

private const val TAG = "AwesomeAds"
