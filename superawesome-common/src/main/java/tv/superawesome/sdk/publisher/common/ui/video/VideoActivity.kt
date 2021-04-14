package tv.superawesome.sdk.publisher.common.ui.video

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.RelativeLayout
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject
import tv.superawesome.lib.savideoplayer.IVideoPlayer
import tv.superawesome.lib.savideoplayer.IVideoPlayerController
import tv.superawesome.lib.savideoplayer.VideoPlayer
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.Orientation
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.ui.common.AdControllerType
import tv.superawesome.sdk.publisher.common.ui.fullscreen.FullScreenActivity
import tv.superawesome.sdk.publisher.common.ui.common.Config
import java.io.File

/**
 * Class that abstracts away the process of loading & displaying a video type Ad.
 * A subclass of the Android "Activity" class.
 */
class VideoActivity : FullScreenActivity() {
    private val controller: AdControllerType by inject(AdControllerType::class.java)
    private val control: IVideoPlayerController by inject(IVideoPlayerController::class.java)
    private var videoEvents: VideoEvents? = null
    private var listener: SAInterface? = null

    private lateinit var videoPlayer: VideoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        logger.info("onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun initChildUI() {
        listener = SAVideoAd.getDelegate()
        // make sure direction is locked
        requestedOrientation = when (config.orientation) {
            Orientation.Any -> ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            Orientation.Portrait -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            Orientation.Landscape -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        val size = RelativeLayout.LayoutParams.MATCH_PARENT
        val params = RelativeLayout.LayoutParams(size, size)

        // Chrome
        val chrome = AdVideoPlayerControllerView(this)
        chrome.shouldShowPadlock(config.shouldShowPadlock)
        chrome.setShouldShowSmallClickButton(config.shouldShowSmallClick)
        chrome.setClickListener {
            controller.adClicked()
            controller.handleAdTapForVast(this)
        }
        chrome.padlock.setOnClickListener { controller.handleSafeAdTap(this) }

        // Video Player
        videoPlayer = VideoPlayer(this)
        videoPlayer.layoutParams = params
        videoPlayer.setController(control)
        videoPlayer.setControllerView(chrome)
        videoPlayer.setBackgroundColor(Color.BLACK)
        parentLayout.addView(videoPlayer)

        videoPlayer.setListener(object : IVideoPlayer.Listener {
            override fun onPrepared(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
                videoEvents?.prepare(videoPlayer, time, duration)
                controller.adShown()
            }

            override fun onTimeUpdated(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
                videoEvents?.time(videoPlayer, time, duration)
            }

            override fun onComplete(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
                videoEvents?.complete(videoPlayer, time, duration)
                closeButton.visibility = View.VISIBLE

                controller.adEnded()

                if (config.shouldCloseAtEnd) {
                    close()
                }
            }

            override fun onError(
                videoPlayer: IVideoPlayer?,
                throwable: Throwable?,
                time: Int,
                duration: Int
            ) {
                videoEvents?.error(videoPlayer, time, duration)
                controller.adFailedToShow()
                close()
            }
        })
        videoPlayer
    }

    override fun playContent() {
        controller.play(placementId)?.let {
            it.vast?.let { _ ->
                videoEvents = get(
                    clazz = VideoEvents::class.java,
                    parameters = { parametersOf(it, config.moatLimiting) }
                )
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

    override fun close() {
        controller.adClosed()
        controller.close()
        videoPlayer.destroy()

        super.close()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        videoPlayer.updateLayout(width, height)
    }

    companion object {
        internal fun start(placementId: Int, config: Config, context: Context) {
            val intent = Intent(context, VideoActivity::class.java)
            intent.putExtra(Constants.Keys.placementId, placementId)
            intent.putExtra(Constants.Keys.config, config)
            context.startActivity(intent)
        }
    }
}
