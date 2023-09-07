package tv.superawesome.sdk.publisher.common.ui.video

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tv.superawesome.sdk.publisher.common.R
import tv.superawesome.sdk.publisher.common.components.ImageProviderType

@Suppress("MagicNumber")
class VideoComponentFactory : KoinComponent {

    private val imageProvider: ImageProviderType by inject()

    private val bgCreator: ComponentCreator<ImageView> = ComponentCreator { id, context ->
        val scale: Float = VideoUtils.getScale(context)
        val view = ImageView(context)
        view.id = id
        view.setImageBitmap(
            imageProvider.createBitmap(
                100,
                52,
                0xFF4D4D4D.toInt(),
                10.0f,
            ),
        )
        view.scaleType = ImageView.ScaleType.FIT_XY
        view.alpha = 0.7f
        val layout = RelativeLayout.LayoutParams((50 * scale).toInt(), (26 * scale).toInt())
        layout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        layout.setMargins((5 * scale).toInt(), 0, 0, (5 * scale).toInt())
        view.layoutParams = layout
        view
    }

    private val chronoCreator: ComponentCreator<TextView> = ComponentCreator { id, context ->
        val scale: Float = VideoUtils.getScale(context)
        val view = TextView(context)
        view.id = id
        view.setTextColor(Color.WHITE)
        view.textSize = 11f
        view.gravity = Gravity.CENTER
        val layoutParams = RelativeLayout.LayoutParams((50 * scale).toInt(), (26 * scale).toInt())
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        layoutParams.setMargins((5 * scale).toInt(), 0, 0, (5 * scale).toInt())
        view.layoutParams = layoutParams
        view
    }

    private val clickCreator: ComponentCreator<Button> = ComponentCreator { id, context ->
        val view = Button(context)
        view.id = id
        view.transformationMethod = null
        view.setBackgroundColor(Color.TRANSPARENT)
        val params = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.layoutParams = params
        view
    }

    private val smallClickCreator: ComponentCreator<Button> = ComponentCreator { id, context ->
        val scale: Float = VideoUtils.getScale(context)
        val view = Button(context)
        view.id = id
        view.transformationMethod = null
        view.setTextColor(Color.WHITE)
        view.textSize = 12f
        view.setBackgroundColor(Color.TRANSPARENT)
        view.gravity = Gravity.CENTER_VERTICAL
        val layout = RelativeLayout.LayoutParams((200 * scale).toInt(), (26 * scale).toInt())
        layout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        layout.setMargins(0, 0, 0, (5 * scale).toInt())
        view.layoutParams = layout
        view.setPadding((65 * scale).toInt(), 0, 0, 0)
        view
    }

    private val padlock: ComponentCreator<ImageButton> = ComponentCreator { id, context ->
        val view = ImageButton(context)
        view.id = id
        view.setImageBitmap(imageProvider.padlockImage())
        val res = context.resources
        view.setPadding(
            res.getDimensionPixelOffset(R.dimen.safe_ad_logo_left_inset),
            res.getDimensionPixelOffset(R.dimen.safe_ad_logo_top_inset),
            res.getDimensionPixelOffset(R.dimen.safe_ad_logo_right_inset),
            res.getDimensionPixelOffset(R.dimen.safe_ad_logo_bottom_inset),
        )
        view.layoutParams = ViewGroup.LayoutParams(
            res.getDimensionPixelOffset(R.dimen.safe_ad_logo_width),
            res.getDimensionPixelOffset(R.dimen.safe_ad_logo_height) +
                    res.getDimensionPixelOffset(R.dimen.safe_ad_logo_top_inset),
        )
        view.setBackgroundColor(Color.TRANSPARENT)
        view.scaleType = ImageView.ScaleType.FIT_XY
        view
    }

    fun getChronographBackground(id: Int, context: Context): ImageView =
        bgCreator.createComponent(id, context)

    fun getChronograph(id: Int, context: Context): TextView =
        chronoCreator.createComponent(id, context)

    fun getClick(id: Int, context: Context): Button = clickCreator.createComponent(id, context)

    fun getSmallClick(id: Int, context: Context): Button =
        smallClickCreator.createComponent(id, context)

    fun getPadlock(id: Int, context: Context): ImageButton = padlock.createComponent(id, context)
}

/**
 * Defines a video component creator.
 */
fun interface ComponentCreator<T> {

    /**
     * Creates a component.
     *
     * @param id an identifier.
     * @param context an activity context.
     * @return the created component of type [T].
     */
    fun createComponent(id: Int, context: Context): T
}
