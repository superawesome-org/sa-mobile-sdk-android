package tv.superawesome.demoapp.main

import CustomRecyclerViewAdapter
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tv.superawesome.demoapp.databinding.ActivityMainBinding
import tv.superawesome.demoapp.gestures.PlacementRowSwipeGesture
import tv.superawesome.demoapp.management.AddPlacementDialogFragment
import tv.superawesome.demoapp.model.FeatureType
import tv.superawesome.demoapp.model.PlacementItem
import tv.superawesome.demoapp.settings.DataStore
import tv.superawesome.demoapp.settings.SettingsDialogFragment
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterstitialAd
import tv.superawesome.sdk.publisher.SAVersion
import tv.superawesome.sdk.publisher.SAVideoAd
import tv.superawesome.sdk.publisher.state.CloseButtonState

@Suppress("TooManyFunctions")
class MainActivity : FragmentActivity() {
    private lateinit var adapter: CustomRecyclerViewAdapter
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
        binding.addPlacementButton.setOnClickListener {
            val dialog = AddPlacementDialogFragment()
            dialog.show(supportFragmentManager, "addPlacement")
            dialog.onSubmitListener = {
                viewModel.insertPlacementItem(it)
            }
        }
        binding.settingsButton.contentDescription = "AdList.Buttons.Settings"
        binding.addPlacementButton.contentDescription = "AdList.Buttons.AddPlacement"
    }

    private fun initUI() {
        val title = "AwesomeAds: v${SAVersion.getSDKVersionNumber()} - Base"
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

    private fun setupListView() {
        val layoutManager = LinearLayoutManager(this)
        val touchHelper = ItemTouchHelper(object: PlacementRowSwipeGesture() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)
                when(direction) {
                    ItemTouchHelper.LEFT -> viewModel.removePlacementItem(
                        viewHolder.adapterPosition
                    )
                }
            }
        })

        adapter = CustomRecyclerViewAdapter()
        adapter.setOnItemClickListener {
            when (it.type) {
                FeatureType.BANNER -> onBannerClick(it)
                FeatureType.INTERSTITIAL -> onInterstitialClick(it)
                FeatureType.VIDEO -> onVideoClick(it)
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                layoutManager.orientation
            )
        )
        touchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun onBannerClick(item: PlacementItem) {
        if (item.isFull()) {
            binding.bannerView2.load(
                item.placementId,
                item.lineItemId ?: 0,
                item.creativeId ?: 0,
            )
        } else {
            binding.bannerView2.load(item.placementId)
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
        Log.i(TAG, "updateSettings")

        val settings = DataStore.data

        binding.bannerView2.setBumperPage(settings.bumperEnabled)
        binding.bannerView2.setParentalGate(settings.parentalEnabled)
        binding.bannerView2.setConfiguration(settings.environment)

        SAInterstitialAd.setBumperPage(settings.bumperEnabled)
        SAInterstitialAd.setParentalGate(settings.parentalEnabled)
        SAInterstitialAd.setConfiguration(settings.environment)

        SAVideoAd.setBumperPage(settings.bumperEnabled)
        SAVideoAd.setParentalGate(settings.parentalEnabled)
        SAVideoAd.setMuteOnStart(settings.muteOnStart)
        SAVideoAd.setCloseButtonWarning(settings.videoWarnOnClose)
        SAVideoAd.setCloseAtEnd(settings.closeAtEnd)
        SAVideoAd.setConfiguration(settings.environment)

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

    private fun setListeners() {
        SAVideoAd.setListener { placementId, event ->
            updateMessage(placementId, event)
            Log.i(TAG, "SAVideoAd event ${event.name} thread:${Thread.currentThread()}")

            if (event == SAEvent.adLoaded && isPlayEnabled()) {
                SAVideoAd.play(placementId, this)
            }
        }

        SAInterstitialAd.setListener { placementId, event ->
            updateMessage(placementId, event)
            Log.i(TAG, "SAInterstitialAd event ${event.name} thread:${Thread.currentThread()}")

            if (event == SAEvent.adLoaded && isPlayEnabled()) {
                SAInterstitialAd.play(placementId, this)
            }
        }

        binding.bannerView2.setListener { placementId, event ->
            updateMessage(placementId, event)
            Log.i(TAG, "bannerView2 event ${event.name} thread:${Thread.currentThread()}")

            if (event == SAEvent.adLoaded && isPlayEnabled()) {
                binding.bannerView2.play(this)
            }
        }
    }

    private fun updateMessage(placementId: Int, event: SAEvent) {
        val originalMessage = binding.subtitleTextView.text
        val message = "$originalMessage $placementId $event"
        binding.subtitleTextView.text = message
    }

    private companion object {
        const val TAG = "MainActivity"
    }
}
