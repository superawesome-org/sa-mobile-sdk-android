package tv.superawesome.demoapp.main

import CustomRecyclerViewAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import tv.superawesome.demoapp.MyApplication
import tv.superawesome.demoapp.SDKEnvironment
import tv.superawesome.demoapp.databinding.ActivityMainBinding
import tv.superawesome.demoapp.gestures.DeleteGesture
import tv.superawesome.demoapp.management.AddPlacementDialogFragment
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
    private lateinit var adapter: CustomRecyclerViewAdapter
    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private var additionalLoadOptions: Map<String, Any>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getStringExtra(AD_LOAD_OPTIONS)?.let { param ->
            try {
                val json = JSONObject(param)
                val options = mutableMapOf<String, Any>()
                json.keys().forEach { key ->
                    options[key] = json[key]
                }
                additionalLoadOptions = options
            } catch (e: Exception) {
                additionalLoadOptions = mapOf(ADDITIONAL_OPTIONS to param)
            }
        }

        val environment = when(intent.getStringExtra("environment")) {
            SDKEnvironment.UITesting.name -> SDKEnvironment.UITesting
            SDKEnvironment.Staging.name -> SDKEnvironment.Staging
            else -> SDKEnvironment.Production
        }

        MyApplication.initSDK(application, environment)

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

    private fun setupListView() {
        val layoutManager = LinearLayoutManager(this)
        val touchHelper = ItemTouchHelper(object: DeleteGesture() {
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

        binding.bannerView.setListener { placementId, event ->
            updateMessage(placementId, event)
            Log.i(TAG, "bannerView event ${event.name} thread:${Thread.currentThread()}")

            if (event == SAEvent.adLoaded && isPlayEnabled()) {
                binding.bannerView.play()
            }
        }
    }

    private fun onBannerClick(item: PlacementItem) {
        if (item.isFull()) {
            binding.bannerView.load(
                item.placementId,
                item.lineItemId ?: 0,
                item.creativeId ?: 0,
                null,
                additionalLoadOptions,
            )
        } else {
            binding.bannerView.load(item.placementId, null, additionalLoadOptions)
        }
    }

    private fun onInterstitialClick(item: PlacementItem) {
        if (item.isFull()) {
            SAInterstitialAd.load(
                item.placementId,
                item.lineItemId ?: 0,
                item.creativeId ?: 0,
                this,
                null,
                additionalLoadOptions
            )
        } else {
            SAInterstitialAd.load(
                item.placementId,
                this,
                null,
                additionalLoadOptions,
            )
        }
    }

    private fun onVideoClick(item: PlacementItem) {
        if (item.isFull()) {
            SAVideoAd.load(
                item.placementId,
                item.lineItemId ?: 0,
                item.creativeId ?: 0,
                this,
                null,
                additionalLoadOptions,
            )
        } else {
            SAVideoAd.load(item.placementId, this, null, additionalLoadOptions)
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

    private companion object {
        const val AD_LOAD_OPTIONS = "AD_LOAD_OPTIONS"
        const val ADDITIONAL_OPTIONS = "additionalOptions"
    }
}

private const val TAG = "AwesomeAds"
