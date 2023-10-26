package tv.superawesome.sdk.publisher.ui.fullscreen

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.ad.FullScreenAdConfig
import tv.superawesome.sdk.publisher.ad.AdController
import tv.superawesome.sdk.publisher.components.CoroutineTimer
import tv.superawesome.sdk.publisher.components.ImageProviderType
import tv.superawesome.sdk.publisher.components.Logger
import tv.superawesome.sdk.publisher.components.TimeProviderType
import tv.superawesome.sdk.publisher.extensions.toPx
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.models.Orientation

/**
 * A full screen activity used to play ad placements.
 */
open class FullScreenActivity : AppCompatActivity() {

    internal val imageProvider: ImageProviderType by inject()
    internal val logger: Logger by inject()
    private val timeProvider: TimeProviderType by inject()

    internal lateinit var parentLayout: RelativeLayout
    internal lateinit var closeButton: ImageButton

    internal val placementId: Int by lazy {
        intent?.getIntExtra(Constants.Keys.placementId, 0) ?: 0
    }

    internal val adConfig: FullScreenAdConfig by lazy {
        intent.getParcelableExtra(Constants.Keys.config) ?: FullScreenAdConfig()
    }

    protected lateinit var controller: AdController

    val closeButtonFailsafeTimer = CoroutineTimer(CLOSE_BUTTON_DELAY, timeProvider, lifecycleScope) {
        closeButton.visibility = View.VISIBLE
        closeButton.setOnClickListener {
            controller.listener?.onEvent(placementId, SAEvent.adEnded)
            onCloseButtonPressed()
            close()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller = get {
            parametersOf(placementId)
        }

        initParentUI()
        initChildUI()

        // set the view hierarchy
        parentLayout.addView(closeButton)
        setContentView(parentLayout)

        playContent()
        closeButtonFailsafeTimer.start()
    }

    override fun onStop() {
        super.onStop()
        closeButtonFailsafeTimer.pause()
    }

    override fun onStart() {
        super.onStart()
        closeButtonFailsafeTimer.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        closeButtonFailsafeTimer.stop()
    }

    /**
     * Initializes the child user interface.
     * Override this function.
     */
    open fun initChildUI() {
        // to be overridden
    }

    /**
     * Plays the content in the activity.
     * Override this function.
     */
    open fun playContent() {
        // to be overridden
    }

    private fun initParentUI() {
        // create the parent relative layout
        parentLayout = RelativeLayout(this)
        parentLayout.id = View.generateViewId()
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
        closeButton.setOnClickListener {
            onCloseButtonPressed()
            close()
        }

        // make sure direction is locked.
        requestedOrientation = when (adConfig.orientation) {
            Orientation.Any -> ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            Orientation.Portrait -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            Orientation.Landscape -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    override fun onBackPressed() {
        if (adConfig.isBackButtonEnabled) {
            close()
            super.onBackPressed()
        }
    }

    /**
     * Close button action specific, useful for tracking requirements.
     */
    open fun onCloseButtonPressed() { }

    /**
     * Closes the activity.
     */
    open fun close() {
        if (!isFinishing) {
            finish()
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    companion object {
        private const val CLOSE_BUTTON_DELAY = 15_000L
    }
}
