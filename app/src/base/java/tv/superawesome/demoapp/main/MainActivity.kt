package tv.superawesome.demoapp.main

import CustomRecyclerViewAdapter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import org.json.JSONObject
import tv.superawesome.demoapp.databinding.ActivityMainBinding
import tv.superawesome.demoapp.gestures.DeleteGesture
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

    private var additionalLoadOptions: Map<String, Any>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkTls()
        intent.getStringExtra(AD_LOAD_OPTIONS)?.let { param ->
            additionalLoadOptions = try {
                val json = JSONObject(param)
                json.keys().asSequence().associateWith { key -> json[key] }
            } catch (e: Exception) {
                mapOf(ADDITIONAL_OPTIONS to param)
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        observeSettings()
        setListeners()

        initButtons()
        updateSettings()
    }

    private fun checkTls() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            try {
                ProviderInstaller.installIfNeeded(this)
            } catch (e: GooglePlayServicesRepairableException) {
                GoogleApiAvailability.getInstance()
                    .showErrorNotification(this, e.connectionStatusCode)
                return

            } catch (e: GooglePlayServicesNotAvailableException) {
                return
            }
        }
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
                binding.bannerView.play(this)
            }
        }
    }

    private fun onBannerClick(item: PlacementItem) {
        if (item.isFull()) {
            binding.bannerView.load(
                item.placementId,
                item.lineItemId ?: 0,
                item.creativeId ?: 0,
                "12345",
                additionalLoadOptions,
            )
        } else {
            binding.bannerView.load(item.placementId, "12345", additionalLoadOptions)
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
                additionalLoadOptions,
            )
        } else {
            SAInterstitialAd.load(item.placementId, this, additionalLoadOptions)
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
            SAVideoAd.load(item.placementId, this, additionalLoadOptions)
        }
    }

    private fun updateSettings() {
        Log.i(TAG, "updateSettings")

        val settings = DataStore.data

        binding.bannerView.setBumperPage(settings.bumperEnabled)
        binding.bannerView.setParentalGate(settings.parentalEnabled)
        binding.bannerView.setConfiguration(settings.environment)

        SAInterstitialAd.setBumperPage(settings.bumperEnabled)
        SAInterstitialAd.setParentalGate(settings.parentalEnabled)
        SAInterstitialAd.setConfiguration(settings.environment)
        SAInterstitialAd.setOrientation(settings.orientation)

        SAVideoAd.setBumperPage(settings.bumperEnabled)
        SAVideoAd.setParentalGate(settings.parentalEnabled)
        SAVideoAd.setMuteOnStart(settings.muteOnStart)
        SAVideoAd.setCloseButtonWarning(settings.videoWarnOnClose)
        SAVideoAd.setCloseAtEnd(settings.closeAtEnd)
        SAVideoAd.setConfiguration(settings.environment)
        SAVideoAd.setOrientation(settings.orientation)

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
            is CloseButtonState.Custom -> {
                SAVideoAd.enableCloseButtonWithDelay(settings.closeButtonState.time)
                SAInterstitialAd.enableCloseButtonWithDelay(settings.closeButtonState.time)
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
        const val AD_LOAD_OPTIONS = "AD_LOAD_OPTIONS"
        const val ADDITIONAL_OPTIONS = "additionalOptions"
    }
}
