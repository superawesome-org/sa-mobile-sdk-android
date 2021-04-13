package tv.superawesome.sdk.publisher.common.ui.common

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import org.koin.java.KoinJavaComponent.inject
import tv.superawesome.sdk.publisher.common.components.ImageProviderType
import tv.superawesome.sdk.publisher.common.components.StringProviderType
import tv.superawesome.sdk.publisher.common.extensions.toPx
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.ui.common.SquareLayout

class BumperPageActivity : Activity() {
    private var handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    private val imageProvider: ImageProviderType by inject(ImageProviderType::class.java)
    private val stringProvider: StringProviderType by inject(StringProviderType::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        )
        window.setFlags(
            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
        )

        // panel
        val panel = SquareLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            background = BitmapDrawable(resources, imageProvider.bumperBackgroundImage())
        }

        // big logo
        val fullDrawable = appIcon
        val bigLogo = ImageView(this)
        bigLogo.id = BIG_LOGO_ID
        bigLogo.setImageDrawable(fullDrawable)
        val bigLogoLayout =
            RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40.toPx)
        bigLogoLayout.setMargins(12.toPx, 12.toPx, 12.toPx, 12.toPx)
        bigLogo.layoutParams = bigLogoLayout

        // small logo
        val smallLogo = ImageView(this)
        smallLogo.id = SMALL_LOGO_ID
        smallLogo.setImageDrawable(BitmapDrawable(resources, imageProvider.bumperPoweredByImage()))
        smallLogo.baselineAlignBottom = true
        val smallLogoLayout = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            24.toPx
        )
        smallLogoLayout.setMargins(12.toPx, 12.toPx, 12.toPx, 12.toPx)
        smallLogoLayout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        smallLogo.layoutParams = smallLogoLayout

        // small text
        val smallText = TextView(this)
        smallText.id = SMALL_TEXT_ID
        smallText.text = stringProvider.bumperPageTimeLeft(Constants.defaultBumperPageShowTimeInSec)
        smallText.setTextColor(-0x1)
        smallText.textSize = 12f
        smallText.gravity = Gravity.CENTER
        val smallTextLayout = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        smallTextLayout.setMargins(24.toPx, 0, 24.toPx, 10.toPx)
        smallTextLayout.addRule(RelativeLayout.ABOVE, SMALL_LOGO_ID)
        smallText.layoutParams = smallTextLayout

        // big text
        val fullText = stringProvider.bumperPageLeaving(appName)
        val bigText = TextView(this)
        bigText.id = BIG_TEXT_ID
        bigText.text = fullText
        bigText.setTextColor(-0x1)
        bigText.textSize = 14f
        bigText.setTypeface(null, Typeface.BOLD)
        bigText.gravity = Gravity.CENTER
        val bigTextLayout = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        bigTextLayout.setMargins(24.toPx, 0, 24.toPx, 10.toPx)
        bigTextLayout.addRule(RelativeLayout.ABOVE, SMALL_TEXT_ID)
        bigText.layoutParams = bigTextLayout

        // assemble them all together
        panel.addView(bigLogo)
        panel.addView(smallLogo)
        panel.addView(smallText)
        panel.addView(bigText)

        setContentView(panel)

        // create the timer
        val countdown = intArrayOf(Constants.defaultBumperPageShowTimeInSec)
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            if (countdown[0] <= 0) {
                listener?.didEndBumper()
                finish()
            } else {
                countdown[0]--
                smallText.text = stringProvider.bumperPageTimeLeft(countdown[0])
                runnable?.let { handler.postDelayed(it, 1000) }
            }
        }
        runnable?.let { handler.postDelayed(it, 1000) }
    }

    override fun onDestroy() {
        listener = null
        runnable?.let { handler.removeCallbacks(it) }
        runnable = null
        super.onDestroy()
    }

    override fun onBackPressed() {
        runnable?.let { handler.removeCallbacks(it) }
        super.onBackPressed()
    }

    interface Interface {
        fun didEndBumper()
    }

    companion object {
        private const val BIG_LOGO_ID = 0x1201012
        private const val SMALL_LOGO_ID = 0x212121
        private const val SMALL_TEXT_ID = 0x212151
        private const val BIG_TEXT_ID = 0x212751

        // Private vars that need to be set
        private var appName: String? = null
        private var appIcon: Drawable? = null
        private var listener: Interface? = null

        // //////////////////////////////////////////////////////////////////////////////////////////////
        // Public methods
        // //////////////////////////////////////////////////////////////////////////////////////////////
        fun play(activity: Activity) {
            val intent = Intent(activity, BumperPageActivity::class.java)
            activity.startActivity(intent)
        }

        fun overrideName(name: String?) {
            appName = name
        }

        fun overrideLogo(drawable: Drawable?) {
            appIcon = drawable
        }

        fun setListener(lis: Interface?) {
            listener = lis
        }
    }
}
