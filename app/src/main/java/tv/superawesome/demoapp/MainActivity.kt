package tv.superawesome.demoapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import kotlinx.android.synthetic.main.activity_main.*
import tv.superawesome.demoapp.adapter.*
import tv.superawesome.demoapp.model.SettingsData
import tv.superawesome.lib.sabumperpage.SABumperPage
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterstitialAd
import tv.superawesome.sdk.publisher.SAVersion
import tv.superawesome.sdk.publisher.SAVideoAd
import tv.superawesome.sdk.publisher.state.CloseButtonState

var data: List<AdapterItem> = listOf()
    private set
private var adapter: CustomListAdapter<AdapterItem>? = null

class MainActivity : FragmentActivity() {

    private lateinit var database: DatabaseReference

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

    private fun getSettings(): SettingsData? = (application as? MyApplication)?.settings

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

    private fun initUI() {
        val title = "AwesomeAds: v${SAVersion.getSDKVersionNumber()}"
        titleTextView.text = title
        configureListView()
    }

    private fun configureDataSource() {
        database = Firebase.database(DATABASE_URL).reference

        database.child("list-items").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                data = dataSnapshot.children.mapNotNull { item ->

                    val rowType = item.getValue(ListItem::class.java)?.rowStyle

                    if(rowType == RowStyle.HEADER) {
                        item.getValue(HeaderItem::class.java)
                    } else {
                        item.getValue(PlacementItem::class.java)
                    }
                }

                adapter?.updateData(data)
                adapter?.reloadList()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to load list items.", error.toException())
            }
        })
    }

    private fun configureListView() {
        adapter = CustomListAdapter(this)
        listView.adapter = adapter

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

    private fun isPlayEnabled(): Boolean {
        return getSettings()?.playEnabled == true
    }

    private fun updateMessage(placementId: Int, event: SAEvent) {
        val settings = getSettings() ?: return

        if(settings.environment == SAConfiguration.UITESTING) {
            val originalMessage = subtitleTextView.text
            val message = "$originalMessage $placementId $event"
            subtitleTextView.text = message
        }
    }
}

private const val TAG = "AwesomeAds"
private const val DATABASE_URL = "https://awesomeads-default-rtdb.europe-west1.firebasedatabase.app/"