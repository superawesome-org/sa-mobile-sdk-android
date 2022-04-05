package tv.superawesome.sdk.publisher.managed

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import tv.superawesome.lib.sautils.SAImageUtils
import tv.superawesome.lib.sautils.SAUtils

class SAManagedVideoAd: Activity() {

    private val banner by lazy {
        SAManagedBannerAd(this)
    }

    private val closeButton by lazy {
        // create the close button
        val fp = SAUtils.getScaleFactor(this)
        val closeButton = ImageButton(this)
        closeButton.visibility = View.VISIBLE
        closeButton.setImageBitmap(SAImageUtils.createCloseButtonBitmap())
        closeButton.setBackgroundColor(Color.TRANSPARENT)
        closeButton.setPadding(0, 0, 0, 0)
        closeButton.scaleType = ImageView.ScaleType.FIT_XY
        val buttonLayout = RelativeLayout.LayoutParams((30 * fp).toInt(), (30 * fp).toInt())
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        closeButton.layoutParams = buttonLayout
        closeButton.setOnClickListener { close() }
        return@lazy closeButton
    }

    private val placementId by lazy {
        intent.getIntExtra(PLACEMENT_ID_KEY, 0)
    }

    private val html by lazy {
        intent.getStringExtra(HTML_KEY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(banner)
        if(html != null) {
            banner.load(placementId, html);
        } else {
            banner.load(placementId = placementId)
        }
        banner.addView(closeButton)
    }

    private fun close() = finish()

    companion object {

        private const val PLACEMENT_ID_KEY = "PLACEMENT_ID"
        private const val HTML_KEY = "HTML"

        @JvmStatic
        fun load(context: Context, placementId: Int, html: String) {
            val intent = Intent(context, SAManagedVideoAd::class.java)
            intent.putExtra(PLACEMENT_ID_KEY, placementId)
            intent.putExtra(HTML_KEY, html)
            context.startActivity(intent)
        }
    }
}