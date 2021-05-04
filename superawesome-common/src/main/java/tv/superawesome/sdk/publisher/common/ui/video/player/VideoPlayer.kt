package tv.superawesome.sdk.publisher.common.ui.video.player

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.RelativeLayout
import android.widget.VideoView
import java.lang.ref.WeakReference

class VideoPlayer @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), IVideoPlayer, SurfaceHolder.Callback,
    OnTouchListener {
    /**
     * The fullscreen mode of the video player
     */
    private var mode: FullscreenMode? = FullscreenMode.ANY

    /**
     * The fullscreen mode of the video player
     */
    private var canDismissOnRotateToPortrait = false

    /**
     * Elements that make up the base of the Video Player
     */
    private var control: IVideoPlayerController? = null
    private var chrome: IVideoPlayerControllerView? = null
    override var surface: VideoView? = null

    /**
     * Elements that make up the video player state
     */
    private var initialLayout: ViewGroup.LayoutParams? = null
    private var weakParent: WeakReference<ViewParent>? = null

    /**
     * The video player's listener
     */
    private var listener: IVideoPlayer.Listener? = null
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width
        val height = height
        updateLayout(width, height)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Smart setters for the media control and the chrome control and other variables
    ////////////////////////////////////////////////////////////////////////////////////////////////
    override fun setController(control: IVideoPlayerController) {
        this.control = control
        this.control!!.setListener(this)
        try {
            this.control!!.setDisplay(surface!!.holder)
        } catch (ignored: Exception) { /* N/A */
        }
    }

    override fun setControllerView(chrome: IVideoPlayerControllerView) {
        /**
         * If the chrome control passed as parameter is not a view group, then don't do anything
         * cause we might be in an invalid state
         */
        if (chrome !is ViewGroup) return
        /**
         * Remove any existing chrome first
         */
        if (this.chrome != null) {
            removeView(this.chrome as ViewGroup?)
        }
        /**
         * Now add the new chrome instead of the old one
         */
        this.chrome = chrome.also { it.setListener(this) }
        val size = LayoutParams.MATCH_PARENT
        val params = LayoutParams(size, size)
        addView(this.chrome as ViewGroup?, params)
    }

    override fun setFullscreenMode(mode: FullscreenMode?) {
        this.mode = mode
    }

    override fun setCanDismissOnRotateToPortrait(canDismissOnRotateToPortrait: Boolean) {
        this.canDismissOnRotateToPortrait = canDismissOnRotateToPortrait
    }

    override fun setMaximised() {
        if (chrome != null) {
            chrome!!.setMaximised()
        }
    }

    override fun setMinimised() {
        if (chrome != null) {
            chrome!!.setMinimised()
        }
    }

    /**
     * Set a listener to the media control
     *
     * @param listener the listener to add
     */
    override fun setListener(listener: IVideoPlayer.Listener) {
        this.listener = listener
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Ending the video player
    ////////////////////////////////////////////////////////////////////////////////////////////////
    override fun destroy() {
        if (control != null) {
            control!!.setDisplay(null)
            control!!.reset()
        }
        if (weakParent != null) {
            weakParent!!.clear()
            weakParent = null
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SurfaceHolder.Callback
    ////////////////////////////////////////////////////////////////////////////////////////////////
    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        try {
            control!!.setDisplay(surfaceHolder)
            if (chrome != null && chrome!!.isPlaying && control != null) {
                control!!.start()
            }
        } catch (ignored: Exception) { /* N/A */
        }
    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) { /* N/A */
    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        try {
            if (control != null && control!!.isIVideoPlaying) {
                control!!.pause()
            }
        } catch (ignored: Exception) { /* N/A */
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // IVideoPlayerController.Listener
    ////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onPrepared(control: IVideoPlayerController) {
        /**
         * When media control says media is prepared, start the media
         */
        control.start()
        /**
         * Put the chrome in it's playing state
         */
        if (chrome != null) {
            chrome!!.setPlaying()
        }
        /**
         * Send back message
         */
        if (listener != null) {
            listener!!.onPrepared(this, control.currentIVideoPosition, control.iVideoDuration)
        }
    }

    override fun onTimeUpdated(control: IVideoPlayerController, time: Int, duration: Int) {
        /**
         * Update the current chrome's time
         */
        if (chrome != null) {
            chrome!!.setTime(time, duration)
        }
        /**
         * Send back message
         */
        if (listener != null) {
            listener!!.onTimeUpdated(this, time, duration)
        }
    }

    override fun onSeekComplete(control: IVideoPlayerController) {
        /**
         * When seeking is complete, re-start the media control (e.g. resume play)
         */
        control.start()
        /**
         * Then set the chrome state in it's playing state
         */
        if (chrome != null) {
            chrome!!.setPlaying()
        }
    }

    override fun onMediaComplete(control: IVideoPlayerController, time: Int, duration: Int) {
        /**
         * When the media has finished, put the chrome in its completed state
         */
        if (chrome != null) {
            chrome!!.setCompleted()
        }
        /**
         * Send back message
         */
        if (listener != null) {
            listener!!.onComplete(this, time, duration)
        }
    }

    override fun onError(
        control: IVideoPlayerController,
        error: Throwable,
        time: Int,
        duration: Int
    ) {
        /**
         * When the media encounters an error, put the chrome in an error state
         */
        if (chrome != null) {
            chrome!!.setError(error)
        }
        /**
         * Send back message
         */
        if (listener != null) {
            listener!!.onError(this, error, time, duration)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // IVideoPlayerControllerView.Listener
    ////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onStartProgressBarSeek() {
        /**
         * When the seek bar starts, pause the media control
         */
        if (control != null) {
            control!!.pause()
        }
    }

    override fun onEndProgressBarSeek(time: Int) {
        /**
         * When the seek bar ends, instruct the control to seek to the current time
         */
        if (control != null) {
            control!!.seekTo(time)
        }
    }

    override fun onClickPlay() {
        /**
         * When the play button is pressed, instruct the media control to start playing
         */
        if (control != null) {
            control!!.start()
        }
    }

    override fun onClickPause() {
        /**
         * When the pause button is pressed, instruct the media control to pause playing
         */
        if (control != null) {
            control!!.pause()
        }
    }

    override fun onClickReplay() {
        /**
         * When the replay button is pressed, seek to 0 and play
         */
        if (control != null) {
            control!!.seekTo(0)
        }
    }

    override fun onClickMaximise() {
        /**
         * Save the previous layout & width & height
         */
        initialLayout = this.layoutParams
        /**
         * Save a weak reference to this view's parent so we know where to re-add it later on,
         * when the user will press "minimise"
         */
        if (weakParent == null) {
            weakParent = WeakReference(this.parent)
        }
        /**
         * Pause the media control
         */
        if (control != null) {
            control!!.pause()
        }
        /**
         * Create weak reference to self (this)
         */
        if (videoPlayerWeakReference == null) {
            videoPlayerWeakReference = WeakReference(this)
        }
        /**
         * Remove this view from it's parent (visually)
         */
        if (this.parent is ViewGroup) {
            (this.parent as ViewGroup).removeView(this)
        }
        /**
         * Start the new intent
         */
        val intent = Intent(context, VideoPlayerActivity::class.java)
        intent.putExtra(FULLSCREEN_KEY, mode!!.value)
        intent.putExtra(ROTATION_DISMISS_KEY, canDismissOnRotateToPortrait)
        context.startActivity(intent)
    }

    override fun onClickMinimise() {
        minimiseOrClose()
    }

    override fun onClickClose() {
        minimiseOrClose()
    }

    private fun minimiseOrClose() {
        /**
         * Get the previous view parent
         */
        val previousViewParent = this.parent

        /**
         * Get the previous parent as a relative layout
         */
        var previousParent: ViewGroup? = null
        if (previousViewParent is ViewGroup) {
            previousParent = previousViewParent
        }
        /**
         * Pause the control, first
         */
        if (control != null) {
            control!!.pause()
        }
        /**
         * If we do have a previous parent
         */
        if (previousParent != null) {
            /**
             * Remove self from parent
             */
            previousParent.removeView(this)
            /**
             * Set the minimised by button flag to true
             */
            if (previousParent.context is VideoPlayerActivity) {
                (previousParent.context as VideoPlayerActivity).minimisedByButton = true
            }
        }
        /**
         * Add the same layout params to the video player
         */
        this.layoutParams = initialLayout
        /**
         * Re-add this video player to the previous parent
         */
        if (weakParent != null) {
            val prevParent = weakParent!!.get()
            if (prevParent is ViewGroup) {
                prevParent.addView(this)
            }
        }
        /**
         * Clear weak reference to self
         */
        if (videoPlayerWeakReference != null) {
            videoPlayerWeakReference!!.clear()
            videoPlayerWeakReference = null
        }
        /**
         * At the very end - just press back
         */
        if (previousParent != null && previousParent.context is VideoPlayerActivity) {
            (previousParent.context as VideoPlayerActivity).onBackPressed()
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Update the layout
    ////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        /**
         * show the chrome on touch
         */
        if (chrome != null) {
            chrome!!.show()
        }
        return false
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Update the layout
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private val videoWidth: Int
        private get() = if (control != null && control!!.videoIVideoWidth > 0) {
            control!!.videoIVideoWidth
        } else {
            0
        }
    private val videoHeight: Int
        private get() = if (control != null && control!!.videoIVideoHeight > 0) {
            control!!.videoIVideoHeight
        } else {
            0
        }

    fun updateLayout(overriddenWidth: Int, overriddenHeight: Int) {
        val videoWidth = videoWidth
        val videoHeight = videoHeight
        surface!!.layoutParams = mapBounds(
            videoWidth.toFloat(),
            videoHeight.toFloat(),
            overriddenWidth.toFloat(),
            overriddenHeight.toFloat()
        )
    }

    private fun mapBounds(
        sourceWidth: Float,
        sourceHeight: Float,
        boundingWidth: Float,
        boundingHeight: Float
    ): LayoutParams {
        val sourceRatio = sourceWidth / sourceHeight
        val boundingRatio = boundingWidth / boundingHeight
        val X: Float
        val Y: Float
        val W: Float
        val H: Float
        if (sourceRatio > boundingRatio) {
            W = boundingWidth
            H = W / sourceRatio
            X = 0.0f
            Y = (boundingHeight - H) / 2.0f
        } else {
            H = boundingHeight
            W = sourceRatio * H
            Y = 0.0f
            X = (boundingWidth - W) / 2.0f
        }
        val params = LayoutParams(W.toInt(), H.toInt())
        params.setMargins(X.toInt(), Y.toInt(), 0, 0)
        return params
    }

    companion object {
        /**
         * Different constants
         */
        const val VIDEO_VIEW_ID = 0x1121
        const val FULLSCREEN_KEY = "video_fullscreen_mode"
        const val ROTATION_DISMISS_KEY = "video_rotation_mode"

        @JvmField
        var videoPlayerWeakReference: WeakReference<VideoPlayer>? = null
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Video player constructors
    ////////////////////////////////////////////////////////////////////////////////////////////////
    init {
        /**
         * Create the video surface that will draw everything
         */
        val size = LayoutParams.MATCH_PARENT
        val params = LayoutParams(size, size)
        surface = VideoView(getContext())
        surface?.id = VIDEO_VIEW_ID
        surface?.holder?.addCallback(this)
        surface?.layoutParams = params
        addView(surface)
        /**
         * Set touch listener for whole vieew
         */
        setOnTouchListener(this)
    }
}