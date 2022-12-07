package tv.superawesome.sdk.publisher.managed

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import tv.superawesome.lib.sautils.SAImageUtils
import tv.superawesome.lib.sautils.SAUtils
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterface
import tv.superawesome.sdk.publisher.SAVideoAd

class SAManagedAdActivity : Activity(), AdViewJavaScriptBridge.Listener {
    private var listener: SAInterface? = null

    private val placementId by lazy {
        intent.getIntExtra(PLACEMENT_ID_KEY, 0)
    }

    private val html by lazy {
        intent.getStringExtra(HTML_KEY) ?: ""
    }

    private val adView by lazy {
        SAManagedAdView(this)
    }

    private val closeButton by lazy {
        val size = (SAUtils.getScaleFactor(this) * 30).toInt()

        val buttonLayout = RelativeLayout.LayoutParams(size, size)
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP)

        val closeButton = ImageButton(this)
        closeButton.visibility = View.VISIBLE
        closeButton.setImageBitmap(SAImageUtils.createCloseButtonBitmap())
        closeButton.setBackgroundColor(Color.TRANSPARENT)
        closeButton.setPadding(0, 0, 0, 0)
        closeButton.scaleType = ImageView.ScaleType.FIT_XY
        closeButton.layoutParams = buttonLayout
        closeButton.setOnClickListener {
            close()
        }
        closeButton.contentDescription = "Close"

        return@lazy closeButton
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(adView)
        adView.load(placementId, html, this)
        adView.addView(closeButton)

        listener = SAVideoAd.getListener()
    }

    override fun adLoaded() {
        listener?.onEvent(this.placementId, SAEvent.adLoaded)
    }

    override fun adEmpty() {
        listener?.onEvent(this.placementId, SAEvent.adEmpty)
        close()
    }

    override fun adFailedToLoad() {
        listener?.onEvent(this.placementId, SAEvent.adFailedToLoad)
        close()
    }

    override fun adAlreadyLoaded() {
        listener?.onEvent(this.placementId, SAEvent.adAlreadyLoaded)
    }

    override fun adShown() {
        listener?.onEvent(this.placementId, SAEvent.adShown)
    }

    override fun adFailedToShow() {
        listener?.onEvent(this.placementId, SAEvent.adFailedToShow)
        close()
    }

    override fun adClicked() {
        listener?.onEvent(this.placementId, SAEvent.adClicked)
    }

    override fun adEnded() {
        listener?.onEvent(this.placementId, SAEvent.adEnded)
        close()
    }

    override fun adClosed() {
        close()
    }

    private fun close() {
        if (!isFinishing) {
            listener?.onEvent(this.placementId, SAEvent.adClosed)
            finish()
        }
    }

    companion object {
        private const val PLACEMENT_ID_KEY = "PLACEMENT_ID"
        private const val HTML_KEY = "HTML"

        @JvmStatic
        fun newInstance(context: Context, placementId: Int, html: String): Intent =
            Intent(context, SAManagedAdActivity::class.java).apply {
                putExtra(PLACEMENT_ID_KEY, placementId)
                putExtra(HTML_KEY, html)
            }
    }
}