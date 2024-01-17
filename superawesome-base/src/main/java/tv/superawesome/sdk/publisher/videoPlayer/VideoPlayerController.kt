@file:Suppress("TooGenericExceptionCaught")
package tv.superawesome.sdk.publisher.videoPlayer

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnErrorListener
import android.media.MediaPlayer.OnPreparedListener
import android.media.MediaPlayer.OnSeekCompleteListener
import android.net.Uri
import android.os.CountDownTimer
import android.util.Log

class VideoPlayerController :
    MediaPlayer(),
    IVideoPlayerController,
    OnPreparedListener,
    OnErrorListener,
    OnCompletionListener,
    OnSeekCompleteListener {

    private var listener: IVideoPlayerController.Listener? = null
    private var countDownTimer: CountDownTimer? = null
    private var completed: Boolean = false
    private var prepared: Boolean = false

    override val isIVideoPlaying: Boolean = false
    override val iVideoDuration: Int = 10
    override val currentIVideoPosition: Int
        get() = currentPosition
    override var videoIVideoWidth: Int = 100
        private set
    override var videoIVideoHeight: Int = 100
        private set

    override var isMuted: Boolean = false
        private set

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // Custom safe play & prepare method
    // //////////////////////////////////////////////////////////////////////////////////////////////
    override fun play(context: Context, uri: Uri) {
        try {
            setDataSource(context, uri)
            prepare()
        } catch (e: Exception) {
            listener?.onError(this, e, 0, 0)
        }
    }

    override fun playAsync(context: Context, uri: Uri) {
        try {
            setDataSource(context, uri)
            prepareAsync()
        } catch (e: Exception) {
            listener?.onError(this, e, 0, 0)
        }
    }

    override fun start() {
        if (!prepared) {
            // If the video is not ready, skip the "start" function call.
            return
        }
        if (completed) {
            // if the video is completed then show the last frame only
            seekTo(currentPosition)
            return
        }
        super.start()
        listener?.onPlay(this)
        createTimer()
    }

    override fun pause() {
        if (prepared) {
            super.pause()
        }
        listener?.onPause(this)
        removeTimer()
    }

    override fun stop() {
        if (prepared) {
            super.stop()
        }
        removeTimer()
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // Safe Media Player overrides
    // //////////////////////////////////////////////////////////////////////////////////////////////
    override fun destroy() {
        try {
            reset()
            release()
        } catch (exception: Exception) {
            Log.e("SuperAwesome", "Error destroying mediaPlayer ${exception.message}")
        }
    }

    override fun reset() {
        completed = false

        try {
            removeTimer()
            if (prepared) {
                super.reset()
            }
        } catch (exception: Exception) {
            Log.e("SuperAwesome", "Error resetting Video Player ${exception.message}")
        }
        prepared = false
    }

    override fun seekTo(position: Int) {
        /*
         * re-create timer if it has been destroyed
         */
        createTimer()
        super.seekTo(position)
    }

    override fun setMuted(muted: Boolean) {
        val volume = if (muted) 0f else 1f
        setVolume(volume, volume)
        isMuted = muted
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // Media Control setters & getters for the listeners
    // //////////////////////////////////////////////////////////////////////////////////////////////
    override fun setListener(listener: IVideoPlayerController.Listener) {
        this.listener = listener
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // Media Player listeners
    // //////////////////////////////////////////////////////////////////////////////////////////////
    override fun onPrepared(mediaPlayer: MediaPlayer) {
        prepared = true
        createTimer()
        listener?.onPrepared(this)
    }

    override fun onSeekComplete(mediaPlayer: MediaPlayer) {
        listener?.onSeekComplete(this)
    }

    override fun onCompletion(mediaPlayer: MediaPlayer) {
        completed = true
        removeTimer()
        // todo: add a "reset" here and see how it goes
        listener?.onMediaComplete(this, currentPosition, duration)
    }

    // todo: why doesn't the video player stop at the error?
    override fun onError(mediaPlayer: MediaPlayer, error: Int, payload: Int): Boolean {
        removeTimer()
        reset()
        listener?.onError(this, Throwable("Error: $error payload: $payload"), 0, 0)
        return false
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // Timer
    // //////////////////////////////////////////////////////////////////////////////////////////////
    override fun createTimer() {
        if (completed) return
        if (countDownTimer == null) {
            countDownTimer = object : CountDownTimer(duration.toLong(), 500) {
                override fun onTick(remainingTime: Long) {
                    listener?.onTimeUpdated(
                        this@VideoPlayerController,
                        currentPosition,
                        duration
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

    private fun onVideoSizeChanged(width: Int, height: Int) {
        videoIVideoWidth = width
        videoIVideoHeight = height
    }

    init {
        setOnPreparedListener(this)
        setOnCompletionListener(this)
        setOnErrorListener(this)
        setOnSeekCompleteListener(this)

        setOnVideoSizeChangedListener { _, width, height ->
            onVideoSizeChanged(width, height)
        }
    }
}
