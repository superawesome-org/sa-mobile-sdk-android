@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.ui.video

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject
import tv.superawesome.sdk.publisher.common.extensions.toPx
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.state.CloseButtonState
import tv.superawesome.sdk.publisher.common.ui.common.AdControllerType
import tv.superawesome.sdk.publisher.common.ui.common.Config
import tv.superawesome.sdk.publisher.common.ui.dialog.CloseWarning
import tv.superawesome.sdk.publisher.common.ui.fullscreen.FullScreenActivity
import tv.superawesome.sdk.publisher.common.ui.video.player.IVideoPlayer
import tv.superawesome.sdk.publisher.common.ui.video.player.IVideoPlayerController
import tv.superawesome.sdk.publisher.common.ui.video.player.VideoPlayer
import java.io.File


/**
 * Class that abstracts away the process of loading & displaying a video type Ad.
 * A subclass of the Android "Activity" class.
 */
class VideoActivity : FullScreenActivity() {
    private val controller: AdControllerType by inject(AdControllerType::class.java)
    private val control: IVideoPlayerController by inject(IVideoPlayerController::class.java)
    private var videoEvents: VideoEvents? = null
    private var completed = false
    private var volumeButton: ImageButton? = null

    private lateinit var videoPlayer: VideoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        logger.info("onCreate")
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onPause() {
        super.onPause()
        control.pause()
    }

    override fun initChildUI() {
        controller.delegate = SAVideoAd.getDelegate()

        val size = RelativeLayout.LayoutParams.MATCH_PARENT
        val params = RelativeLayout.LayoutParams(size, size)

        // Video Player
        videoPlayer = VideoPlayer(this)
        videoPlayer.layoutParams = params
        videoPlayer.setController(control)
        videoPlayer.setBackgroundColor(Color.BLACK)
        parentLayout.addView(videoPlayer)

        closeButton.visibility =
            if (config.closeButtonState == CloseButtonState.VisibleImmediately) View.VISIBLE else View.GONE
        closeButton.setOnClickListener { onCloseAction() }

        videoPlayer.setListener(object : IVideoPlayer.Listener {
            override fun onPrepared(player: IVideoPlayer, time: Int, duration: Int) {
                videoEvents?.prepare(player, time, duration)
                controller.adShown()
            }

            override fun onTimeUpdated(player: IVideoPlayer, time: Int, duration: Int) {
                videoEvents?.time(player, time, duration)
            }

            override fun onComplete(player: IVideoPlayer, time: Int, duration: Int) {
                completed = true
                videoEvents?.complete(player, time, duration)

                controller.adEnded()

                if (config.shouldCloseAtEnd) {
                    close()
                }
            }

            override fun onError(
                player: IVideoPlayer,
                error: Throwable,
                time: Int,
                duration: Int
            ) {
                videoEvents?.error(player, time, duration)
                controller.adFailedToShow()
                close()
            }
        })
    }

    public override fun playContent() {
        controller.play(placementId)?.let {
            addChrome()
            it.vast?.let { _ ->
                videoEvents = get(
                    clazz = VideoEvents::class.java,
                    parameters = { parametersOf(it) }
                )
                videoEvents?.listener = object : VideoEvents.Listener {
                    override fun hasBeenVisible() {
                        closeButton.visibility =
                            if (config.closeButtonState.isVisible()) View.VISIBLE else View.GONE
                    }
                }
                val filePath = it.filePath ?: ""
                try {
                    val fileUri = Uri.fromFile(File(filePath))
                    control.playAsync(this, fileUri)
                } catch (exception: Exception) {
                    logger.error("File $filePath does not exist on disk. Will not play!", exception)
                }
            }
        }
    }

    private fun onVolumeAction() {
        setMuted(!control.isMuted)
    }

    private fun setMuted(muted: Boolean) {
        volumeButton?.setImageBitmap(
            if (muted) imageProvider.volumeOff() else imageProvider.volumeOn()
        )
        control.setMuted(muted)
    }

    private fun initVolumeButton() {
        val button = ImageButton(this)
        button.visibility = if (config.shouldMuteOnStart) View.VISIBLE else View.GONE
        button.setBackgroundColor(Color.TRANSPARENT)
        button.setPadding(0, 0, 0, 0)
        button.scaleType = ImageView.ScaleType.FIT_XY
        button.contentDescription = "Volume"
        button.setOnClickListener { onVolumeAction() }

        val buttonLayout = RelativeLayout.LayoutParams(40.toPx, 40.toPx)
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        button.layoutParams = buttonLayout
        button.setOnClickListener { close() }

        volumeButton = button
        setMuted(config.shouldMuteOnStart)

        parentLayout.addView(button)
    }

    private fun onCloseAction() {
        if (config.shouldShowCloseWarning && !completed) {
            control.pause()
            CloseWarning.setListener(object : CloseWarning.Interface {
                override fun onResumeSelected() {
                    control.start()
                }

                override fun onCloseSelected() {
                    close()
                }
            })
            CloseWarning.show(this)
        } else {
            close()
        }
    }

    override fun onBackPressed() {
        if (config.isBackButtonEnabled) {
            onCloseAction()
        }
    }

    override fun close() {
        CloseWarning.close()
        controller.adClosed()
        controller.close()
        videoPlayer.destroy()
        super.close()
    }

    override fun onDestroy() {
        CloseWarning.close()
        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val displayMetrics = resources.displayMetrics
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        videoPlayer.updateLayout(width, height)
    }

    private fun addChrome() {
        val chrome = AdVideoPlayerControllerView(this)
        chrome.shouldShowPadlock(controller.shouldShowPadlock)
        chrome.setShouldShowSmallClickButton(config.shouldShowSmallClick)
        chrome.setClickListener {
            controller.adClicked()
            controller.handleAdTapForVast(this)
        }
        chrome.padlock.setOnClickListener { controller.handleSafeAdTap(this) }
        videoPlayer.setControllerView(chrome)
    }

    companion object {
        internal fun newInstance(context: Context, placementId: Int, config: Config): Intent =
            Intent(context, VideoActivity::class.java).apply {
                putExtra(Constants.Keys.placementId, placementId)
                putExtra(Constants.Keys.config, config)
            }
    }
}
