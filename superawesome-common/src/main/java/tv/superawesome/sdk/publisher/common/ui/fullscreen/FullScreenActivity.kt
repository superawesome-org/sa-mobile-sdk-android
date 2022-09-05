package tv.superawesome.sdk.publisher.common.ui.fullscreen

import android.app.Activity
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import org.koin.java.KoinJavaComponent.inject
import tv.superawesome.sdk.publisher.common.components.ImageProviderType
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.components.NumberGeneratorType
import tv.superawesome.sdk.publisher.common.extensions.toPx
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.Orientation
import tv.superawesome.sdk.publisher.common.ui.common.Config

open class FullScreenActivity : Activity() {
    private val imageProvider: ImageProviderType by inject(ImageProviderType::class.java)
    internal val logger: Logger by inject(Logger::class.java)
    internal val numberGenerator: NumberGeneratorType by inject(NumberGeneratorType::class.java)

    internal lateinit var parentLayout: RelativeLayout
    internal lateinit var closeButton: ImageButton

    internal val placementId: Int by lazy {
        intent?.getIntExtra(Constants.Keys.placementId, 0) ?: 0
    }

    internal val config: Config by lazy {
        intent?.getParcelableExtra(Constants.Keys.config) ?: Config.default
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initParentUI()
        initChildUI()

        // set the view hierarchy
        parentLayout.addView(closeButton)
        setContentView(parentLayout)

        playContent()
    }

    open fun initChildUI() {
        // to be overridden
    }

    open fun playContent() {
        // to be overridden
    }

    private fun initParentUI() {
        // create the parent relative layout
        parentLayout = RelativeLayout(this)
        parentLayout.id = numberGenerator.nextIntForCache()
        parentLayout.layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )

        // close button
        closeButton = ImageButton(this)
        closeButton.visibility = View.GONE
        closeButton.setImageBitmap(imageProvider.closeImage())
        closeButton.setBackgroundColor(Color.TRANSPARENT)
        closeButton.setPadding(0, 0, 0, 0)
        closeButton.scaleType = ImageView.ScaleType.FIT_XY
        closeButton.contentDescription = "Close"

        val buttonLayout = RelativeLayout.LayoutParams(30.toPx, 30.toPx)
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        closeButton.layoutParams = buttonLayout
        closeButton.setOnClickListener { close() }

        // make sure direction is locked
        requestedOrientation = when (config.orientation) {
            Orientation.Any -> ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            Orientation.Portrait -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            Orientation.Landscape -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    override fun onBackPressed() {
        if (config.isBackButtonEnabled) {
            close()
            super.onBackPressed()
        }
    }

    open fun close() {
        if (!isFinishing) {
            finish()
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}