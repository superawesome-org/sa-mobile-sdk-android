package tv.superawesome.demoapp.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import tv.superawesome.demoapp.databinding.ActivityMainBinding
import tv.superawesome.demoapp.model.FeatureType
import tv.superawesome.demoapp.model.PlacementItem
import tv.superawesome.demoapp.settings.DataStore
import tv.superawesome.demoapp.settings.SettingsDialogFragment
import tv.superawesome.sdk.publisher.models.CloseButtonState
import tv.superawesome.sdk.publisher.models.SAEvent
import tv.superawesome.sdk.publisher.AwesomeAds
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd
import tv.superawesome.sdk.publisher.ui.video.SAVideoAd

@Suppress("TooManyFunctions")
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
        val title = "AwesomeAds: v${AwesomeAds.info()?.versionNumber} - Common"
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
                SAInterstitialAd.play(placementId, this)
            }
        }

        SAVideoAd.setListener { placementId, event ->
            updateMessage(placementId, event)
            Log.i(TAG, "SAVideoAd event ${event.name} thread:${Thread.currentThread()}")

            if (event == SAEvent.adLoaded && isPlayEnabled()) {
                SAVideoAd.play(placementId, this)
            }
        }
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
        if (item.isFull()) {
            binding.bannerView.load(
                item.placementId,
                item.lineItemId ?: 0,
                item.creativeId ?: 0,
            )
        } else {
            binding.bannerView.load(item.placementId)
        }
    }

    private fun onInterstitialClick(item: PlacementItem) {
        if (item.isFull()) {
            SAInterstitialAd.load(
                item.placementId,
                item.lineItemId ?: 0,
                item.creativeId ?: 0,
                this,
            )
        } else {
            SAInterstitialAd.load(item.placementId, this)
        }
    }

    private fun onVideoClick(item: PlacementItem) {
        if (item.isFull()) {
            SAVideoAd.load(
                item.placementId,
                item.lineItemId ?: 0,
                item.creativeId ?: 0,
                this,
            )
        } else {
            SAVideoAd.load(item.placementId, this)
        }
    }

    private fun updateSettings() {
        binding.bannerView.visibility = View.GONE
        updateSettingsForCommon()
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

    private fun updateMessage(placementId: Int, event: SAEvent) {
        val originalMessage =binding. subtitleTextView.text
        val message = "$originalMessage $placementId $event"
        binding.subtitleTextView.text = message
    }
}

private const val TAG = "AwesomeAds"
