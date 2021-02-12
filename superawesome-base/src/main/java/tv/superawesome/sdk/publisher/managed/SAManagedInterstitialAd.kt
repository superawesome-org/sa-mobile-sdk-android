package tv.superawesome.sdk.publisher.managed

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

class SAManagedInterstitialAd: Activity() {

    private val banner by lazy {
        SAManagedBannerAd(this)
    }

    private val placementId by lazy {
        intent.getIntExtra(PLACEMENT_ID_KEY, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(banner)
        banner.load(placementId = placementId)
    }

    companion object {

        private const val PLACEMENT_ID_KEY = "PLACEMENT_ID"

        @JvmStatic
        fun load(context: Context, placementId: Int) {
            val intent = Intent(context, SAManagedInterstitialAd::class.java)
            intent.putExtra(PLACEMENT_ID_KEY, placementId)
            context.startActivity(intent)
        }
    }
}