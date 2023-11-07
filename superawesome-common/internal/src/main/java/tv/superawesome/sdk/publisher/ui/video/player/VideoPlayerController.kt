package tv.superawesome.sdk.publisher.ui.video.player

import android.content.Context
import android.net.Uri
import android.os.CountDownTimer
import android.util.Log
import android.view.SurfaceHolder
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Suppress("TooManyFunctions", "MagicNumber", "TooGenericExceptionCaught")
class VideoPlayerController(playerView: IVideoPlayer) : IVideoPlayerController {

    private val playerView = playerView as PlayerView
    private var player: ExoPlayer? = null
    private var listener: IVideoPlayerController.Listener? = null
    private var countDownTimer: CountDownTimer? = null
    private var completed: Boolean = false
    private var prepared: Boolean = false
    private var playbackState: Int = ExoPlayer.STATE_IDLE

    private val playbackStateListener: Player.Listener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            this@VideoPlayerController.playbackState = playbackState
            when (playbackState) {
                ExoPlayer.STATE_READY -> {
                    listener?.onPrepared(this@VideoPlayerController)
                    createTimer()
                }
                ExoPlayer.STATE_ENDED -> {
                    listener?.onMediaComplete(
                        this@VideoPlayerController,
                        currentIVideoPosition.toInt(),
                        iVideoDuration.toInt()
                    )
                    removeTimer()
                }
                else -> { /* no-op */ }
            }
        }
    }

    override val isIVideoPlaying: Boolean
        get() = player?.isPlaying ?: false
    override val iVideoDuration: Long
        get() = player?.contentDuration ?: 10L
    override val currentIVideoPosition: Long
        get() = player?.currentPosition ?: 0L
    override val videoIVideoWidth: Int
        get() = player?.videoSize?.width ?: 100
    override val videoIVideoHeight: Int
        get() = player?.videoSize?.height ?: 100

    override var isMuted: Boolean = false
        private set

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // Custom safe play & prepare method
    // //////////////////////////////////////////////////////////////////////////////////////////////
    override fun play(context: Context, uri: Uri) {
        try {
            player = ExoPlayer.Builder(context).build()
                .also { playerView.player = it }

            val mediaItem = MediaItem.Builder()
                .setUri(uri)
                .build()

            player?.setMediaItem(mediaItem)
            player?.playWhenReady = true
            player?.seekTo(0)
            player?.addListener(playbackStateListener)
            player?.prepare()
        } catch (e: Exception) {
            listener?.onError(this, e, 0, 0)
        }
    }

    override fun playAsync(context: Context, uri: Uri) {
        play(context, uri)
    }

    override fun start() {
        if (!prepared) {
            // If the video is not ready, skip the "start" function call.
            return
        }
        if (completed) {
            // if the video is completed then show the last frame only
            seekTo(currentIVideoPosition.toInt())
            return
        }
        player?.play()
        createTimer()
    }

    override fun pause() {
        if (prepared) {
            player?.pause()
        }
        removeTimer()
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // Safe Media Player overrides
    // //////////////////////////////////////////////////////////////////////////////////////////////
    override fun destroy() {
        try {
            player?.stop()
            setDisplay(null)
            player?.release()
            player?.removeListener(playbackStateListener)
            removeTimer()
            player = null
        } catch (ignored: Throwable) {
        }
    }

    override fun reset() {
        completed = false

        try {
            removeTimer()
            if (prepared) {
                player?.stop()
                player?.seekTo(0)

                if (playbackState == ExoPlayer.STATE_ENDED) {
                    player?.playWhenReady = true
                }
            }
        } catch (ignored: Exception) {
        }
        prepared = false
    }

    override fun seekTo(position: Int) {
        /*
         * re-create timer if it has been destroyed
         */
        createTimer()
        player?.seekTo(position.toLong())
    }

    override fun setMuted(muted: Boolean) {
        val volume = if (muted) 0f else 1f
        player?.volume = volume
        isMuted = muted
    }

    override fun setDisplay(holder: SurfaceHolder?) {
        /* no-op */
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // Media Constrol setters & getters for the listeners
    // //////////////////////////////////////////////////////////////////////////////////////////////
    override fun setListener(listener: IVideoPlayerController.Listener) {
        this.listener = listener
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // Timer
    // //////////////////////////////////////////////////////////////////////////////////////////////
    override fun createTimer() {
        Log.d("MATHEUS", "createTimer()")
        if (completed) return
        if (countDownTimer == null) {
            countDownTimer = object : CountDownTimer(iVideoDuration, 500) {
                override fun onTick(remainingTime: Long) {
                    Log.d("MATHEUS", "onTick remainignTime=$remainingTime $this")
                    listener?.onTimeUpdated(
                        this@VideoPlayerController,
                        currentIVideoPosition.toInt(),
                        iVideoDuration.toInt()
                    )
                }

                override fun onFinish() {
                    // not needed
                }
            }
            countDownTimer?.start()
        }
    }

    override fun removeTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
    }
}
