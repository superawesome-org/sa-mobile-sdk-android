package tv.superawesome.demoapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main_2.*
import kotlinx.android.synthetic.main.activity_main_2.bannerView
import kotlinx.android.synthetic.main.activity_main_2.config1Button
import kotlinx.android.synthetic.main.activity_main_2.config2Button
import kotlinx.android.synthetic.main.activity_main_2.listView
import kotlinx.android.synthetic.main.activity_main_2.titleTextView
import tv.superawesome.sdk.publisher.SAVersion
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.ui.common.BumperPageActivity
import tv.superawesome.sdk.publisher.common.ui.interstitial.SAInterstitialAd
import tv.superawesome.sdk.publisher.common.ui.video.SAVideoAd
import tv.superawesome.sdk.publisher.state.CloseButtonState
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import tv.superawesome.demoapp.adapter.*
import tv.superawesome.demoapp.adapter.CustomListAdapter
import tv.superawesome.demoapp.model.Constants

class MainActivity2 : FragmentActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var adapter: CustomListAdapter<AdapterItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_2)

        BumperPageActivity.overrideName("AwesomeAds SDK Demo")

        initUI()

        configureDataSource()
        configureBannerAd()
        configureInterstitialAd()
        configureVideoAd()

        initButtons()
    }

    private fun configureDataSource() {
        database = Firebase.database(Constants.FIREBASE_DATABASE_URL).reference

        database.child("list-items").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                data = dataSnapshot.children.mapNotNull { item ->

                    val rowStyle = item.getValue(ListItem::class.java)?.rowStyle

                    if(rowStyle == RowStyle.HEADER) {
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
            SAVideoAd.enableCloseButtonNoDelay()
        }
        settingsButton.setOnClickListener {
            val dialog = SettingsDialogFragment()
            dialog.show(supportFragmentManager, "settings")
            dialog.onDismissListener = {
                updateSettings()
            }
        }
    }

    private fun initUI() {
        val title = "AwesomeAds.version: ${SAVersion.getSDKVersion(null)}"
        titleTextView.text = title
        configureListView()
    }

    private fun updateSettings() {
        val app = application as? MyApplication ?: return
        val settings = app.settings

        bannerView.setBumperPage(settings.bumperEnabled)
        bannerView.setParentalGate(settings.parentalEnabled)

        SAInterstitialAd.setBumperPage(settings.bumperEnabled)
        SAInterstitialAd.setParentalGate(settings.parentalEnabled)

        SAVideoAd.setBumperPage(settings.bumperEnabled)
        SAVideoAd.setParentalGate(settings.parentalEnabled)

        when (app.settings.closeButtonState) {
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
