package tv.superawesome.sdk.publisher.ui.common

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
import org.koin.java.KoinJavaComponent.inject
import tv.superawesome.sdk.publisher.common.R
import tv.superawesome.sdk.publisher.components.ImageProviderType
import tv.superawesome.sdk.publisher.extensions.toPx
import tv.superawesome.sdk.publisher.models.Constants

class BumperPageDialog(context: Context) : Dialog(context) {
    private val imageProvider: ImageProviderType by inject(ImageProviderType::class.java)
    private var smallText: TextView? = null

    @Suppress("MagicNumber", "LongMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCancelable(false)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        val contentView = SquareLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            background = BitmapDrawable(resources, imageProvider.bumperBackgroundImage())
        }

        val topImageView = ImageView(context)
        topImageView.id = BIG_LOGO_ID
        topImageView.setImageDrawable(BumperPage.appIcon)
        topImageView.layoutParams =
            RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40.toPx).apply {
                setMargins(12.toPx, 12.toPx, 12.toPx, 12.toPx)
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
            24.toPx
        ).apply {
            setMargins(12.toPx, 12.toPx, 12.toPx, 12.toPx)
            addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        }

        val subtitleTextView = TextView(context)
        subtitleTextView.id = SMALL_TEXT_ID
        subtitleTextView.text =
            context.getString(
                R.string.bumper_page_time_left,
                Constants.defaultBumperPageShowTimeInSec
            )
        subtitleTextView.setTextColor(Color.WHITE)
        subtitleTextView.textSize = 12f
        subtitleTextView.gravity = Gravity.CENTER
        subtitleTextView.layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(24.toPx, 0, 24.toPx, 10.toPx)
            addRule(RelativeLayout.ABOVE, SMALL_LOGO_ID)
        }

        smallText = subtitleTextView

        val titleText = context.getString(
            R.string.bumper_page_leaving,
            BumperPage.appName ?: context.getString(R.string.bumper_app_name_default)
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
            setMargins(24.toPx, 0, 24.toPx, 10.toPx)
            addRule(RelativeLayout.ABOVE, SMALL_TEXT_ID)
        }

        contentView.addView(topImageView)
        contentView.addView(bottomImageView)
        contentView.addView(subtitleTextView)
        contentView.addView(titleTextView)

        setContentView(contentView)
    }

    fun updateTimeLeft(timeLeft: Int) {
        smallText?.text = context.getString(R.string.bumper_page_time_left, timeLeft)
    }
}

private const val BIG_LOGO_ID = 0x1201012
private const val SMALL_LOGO_ID = 0x212121
private const val SMALL_TEXT_ID = 0x212151
private const val BIG_TEXT_ID = 0x212751
