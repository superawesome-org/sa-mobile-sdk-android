package tv.superawesome.sdk.publisher.videoPlayer

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaPlayer.*
import android.net.Uri
import android.os.CountDownTimer

class VideoPlayerController :
    MediaPlayer(),
    IVideoPlayerController,
    OnPreparedListener,
    OnErrorListener,
    OnCompletionListener,
    OnSeekCompleteListener {
    private var listener: IVideoPlayerController.Listener? = null
    private var countDownTimer: CountDownTimer? = null

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
        super.start()
        createTimer()
    }

    override fun pause() {
        super.pause()
        removeTimer()
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // Safe Media Player overrides
    // //////////////////////////////////////////////////////////////////////////////////////////////
    override fun destroy() {
        try {
            stop()
            setDisplay(null)
            release()
            removeTimer()
        } catch (ignored: Throwable) {
        }
    }

    override fun reset() {
        removeTimer()
        try {
            super.reset()
        } catch (ignored: Exception) {
        }
    }

    override val isIVideoPlaying: Boolean = false
    override val iVideoDuration: Int = 10
    override val currentIVideoPosition: Int = 0
    override var videoIVideoWidth: Int = 100
        private set
    override  var videoIVideoHeight: Int = 100
        private set

    override fun seekTo(position: Int) {
        /*
         * re-create timer if it has been destroyed
         */
        createTimer()
        super.seekTo(position)
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // Media Constrol setters & getters for the listeners
    // //////////////////////////////////////////////////////////////////////////////////////////////
    override fun setListener(listener: IVideoPlayerController.Listener) {
        this.listener = listener
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // Media Player listeners
    // //////////////////////////////////////////////////////////////////////////////////////////////
    override fun onPrepared(mediaPlayer: MediaPlayer) {
        createTimer()
        listener?.onPrepared(this)
    }

    override fun onSeekComplete(mediaPlayer: MediaPlayer) {
        listener?.onSeekComplete(this)
    }

    override fun onCompletion(mediaPlayer: MediaPlayer) {
        //removeTimer()
        reset()
        listener?.onMediaComplete(this, currentPosition, duration)
    }

    // todo: why doesn't the video player stop at the error?
    override fun onError(mediaPlayer: MediaPlayer, error: Int, payload: Int): Boolean {
        //removeTimer()
        reset()
        listener?.onError(this, Throwable(), 0, 0)
        return false
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // Timer
    // //////////////////////////////////////////////////////////////////////////////////////////////
    override fun createTimer() {
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
        videoIVideoWidth = width;
        videoIVideoHeight = height;
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
