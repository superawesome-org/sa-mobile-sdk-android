package tv.superawesome.lib.sabumperpage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import tv.superawesome.lib.sautils.ImageProvider
import tv.superawesome.lib.sautils.SquareLayout
import tv.superawesome.sdk.publisher.base.R

class BumperPageDialog(context: Context) : Dialog(context) {
    private val imageProvider = ImageProvider()
    private var smallText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCancelable(false)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        val density = context.resources.displayMetrics.density
        val margin10: Int = (10 * density).toInt()
        val margin12: Int = (12 * density).toInt()
        val margin24: Int = (24 * density).toInt()
        val margin40: Int = (40 * density).toInt()

        val contentView = SquareLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            background = BitmapDrawable(resources, imageProvider.bumperBackgroundImage())
        }

        val topImageView = ImageView(context)
        topImageView.id = BIG_LOGO_ID
        topImageView.setImageDrawable(SABumperPage.appIcon)
        topImageView.layoutParams =
            RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, margin40).apply {
                setMargins(margin12, margin12, margin12, margin12)
            }

        val bottomImageView = ImageView(context)
        bottomImageView.id = SMALL_LOGO_ID
        bottomImageView.setImageDrawable(
            BitmapDrawable(
                context.resources,
                imageProvider.bumperPoweredByImage()
            )
        )
        bottomImageView.baselineAlignBottom = true
        bottomImageView.layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            margin24
        ).apply {
            setMargins(margin12, margin12, margin12, margin12)
            addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        }

        val subtitleTextView = TextView(context)
        subtitleTextView.id = SMALL_TEXT_ID
        subtitleTextView.text =
            context.getString(
                R.string.superawesome__bumper_page_time_left,
                defaultBumperPageShowTimeInSec
            )
        subtitleTextView.setTextColor(Color.WHITE)
        subtitleTextView.textSize = 12f
        subtitleTextView.gravity = Gravity.CENTER
        subtitleTextView.layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(margin24, 0, margin24, margin10)
            addRule(RelativeLayout.ABOVE, SMALL_LOGO_ID)
        }

        smallText = subtitleTextView

        val titleText = context.getString(
            R.string.superawesome__bumper_page_leaving,
            SABumperPage.appName ?: context.getString(R.string.superawesome__bumper_app_name_default)
        )
        val titleTextView = TextView(context)
        titleTextView.id = BIG_TEXT_ID
        titleTextView.text = titleText
        titleTextView.setTextColor(Color.WHITE)
        titleTextView.textSize = 14f
        titleTextView.setTypeface(null, Typeface.BOLD)
        titleTextView.gravity = Gravity.CENTER
        titleTextView.layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(margin24, 0, margin24, margin10)
            addRule(RelativeLayout.ABOVE, SMALL_TEXT_ID)
        }

        contentView.addView(topImageView)
        contentView.addView(bottomImageView)
        contentView.addView(subtitleTextView)
        contentView.addView(titleTextView)

        setContentView(contentView)
    }

    fun updateTimeLeft(timeLeft: Int) {
        smallText?.text = context.getString(R.string.superawesome__bumper_page_time_left, timeLeft)
    }
}

private const val BIG_LOGO_ID = 0x1201012
private const val SMALL_LOGO_ID = 0x212121
private const val SMALL_TEXT_ID = 0x212151
private const val BIG_TEXT_ID = 0x212751