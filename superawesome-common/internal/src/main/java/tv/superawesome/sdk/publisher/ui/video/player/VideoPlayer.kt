package tv.superawesome.sdk.publisher.ui.video.player

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
import android.widget.VideoView
import androidx.media3.ui.PlayerView
import java.lang.ref.WeakReference

@Suppress("TooManyFunctions")
internal class VideoPlayer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : PlayerView(context, attrs, defStyleAttr),
    IVideoPlayer,
    OnTouchListener {

    override val surface: VideoView? = null

    private var mode: FullscreenMode? = FullscreenMode.ANY
    private var canDismissOnRotateToPortrait = false
    private var control: IVideoPlayerController? = null
    private var chrome: IVideoPlayerControllerView? = null
    private var initialLayout: ViewGroup.LayoutParams? = null
    private var weakParent: WeakReference<ViewParent>? = null
    private var listener: IVideoPlayer.Listener? = null

    private val videoWidth: Int
        get() = control?.takeIf { it.videoIVideoWidth > 0 }?.videoIVideoWidth ?: 0

    private val videoHeight: Int
        get() = control?.takeIf { it.videoIVideoHeight > 0 }?.videoIVideoHeight ?: 0

    init {
        val size = LayoutParams.MATCH_PARENT
        val params = LayoutParams(size, size)
        id = VIDEO_VIEW_ID
        layoutParams = params
        setOnTouchListener(this)

        useController = false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width
        val height = height
        updateLayout(width, height)
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // Smart setters for the media control and the chrome control and other variables.
    // //////////////////////////////////////////////////////////////////////////////////////////////
    override fun setController(control: IVideoPlayerController) {
        this.control = control
        this.control?.setListener(this)
    }

    override fun setControllerView(chrome: IVideoPlayerControllerView) {
        /*
         * If the chrome control passed as parameter is not a view group, then don't do anything
         * cause we might be in an invalid state.
         */
        if (chrome !is ViewGroup) return

        // Remove any existing chrome first
        if (this.chrome != null) {
            removeView(this.chrome as? ViewGroup)
        }

        // Then, add the new chrome instead of the old one.
        this.chrome = chrome.apply { setListener(this@VideoPlayer) }
        val size = LayoutParams.MATCH_PARENT
        val params = LayoutParams(size, size)
        addView(this.chrome as? ViewGroup, params)
    }

    override fun setFullscreenMode(mode: FullscreenMode?) {
        this.mode = mode
    }

    override fun setCanDismissOnRotateToPortrait(canDismissOnRotateToPortrait: Boolean) {
        this.canDismissOnRotateToPortrait = canDismissOnRotateToPortrait
    }

    override fun setMaximised() {
        chrome?.setMaximised()
    }

    override fun setMinimised() {
        chrome?.setMinimised()
    }

    /**
     * Sets a listener to the media control.
     *
     * @param listener the listener to add.
     */
    override fun setListener(listener: IVideoPlayer.Listener) {
        this.listener = listener
    }

    override fun destroy() {
        control?.setDisplay(null)
        control?.reset()
        player?.release()
        weakParent?.clear()
        weakParent = null
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // IVideoPlayerController.Listener
    // //////////////////////////////////////////////////////////////////////////////////////////////
    override fun onPrepared(control: IVideoPlayerController) {
        control.start()
        chrome?.setPlaying()
        listener?.onPrepared(this, control.currentIVideoPosition.toInt(), control.iVideoDuration.toInt())
    }

    override fun onTimeUpdated(control: IVideoPlayerController, time: Int, duration: Int) {
        chrome?.setTime(time, duration)
        listener?.onTimeUpdated(this, time, duration)
    }

    override fun onSeekComplete(control: IVideoPlayerController) {
        // We don't need to re-start video automatically after seek to.
    }

    override fun onMediaComplete(control: IVideoPlayerController, time: Int, duration: Int) {
        chrome?.setCompleted()
        listener?.onComplete(this, time, duration)
    }

    override fun onError(
        control: IVideoPlayerController,
        error: Throwable,
        time: Int,
        duration: Int
    ) {
        chrome?.setError(error)
        listener?.onError(this, error, time, duration)
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // IVideoPlayerControllerView.Listener
    // //////////////////////////////////////////////////////////////////////////////////////////////
    override fun onStartProgressBarSeek() {
        control?.pause()
    }

    override fun onEndProgressBarSeek(time: Int) {
        control?.seekTo(time)
    }

    override fun onClickPlay() {
        control?.start()
    }

    override fun onClickPause() {
        control?.pause()
    }

    override fun onClickReplay() {
        control?.seekTo(0)
    }

    override fun onClickMaximise() {
        initialLayout = layoutParams

        // Save a weak reference to this view's parent so we know where to re-add it later on.
        // When the user will presses "minimise".
        if (weakParent == null) {
            weakParent = WeakReference(this.parent)
        }
        control?.pause()

        if (videoPlayerWeakReference == null) {
            videoPlayerWeakReference = WeakReference(this)
        }
        (this.parent as? ViewGroup)?.removeView(this)

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
        val previousViewParent = this.parent
        val previousParent: ViewGroup? = previousViewParent as? ViewGroup

        // Pause first.
        control?.pause()

        if (previousParent != null) {
            previousParent.removeView(this)
            (previousParent.context as? VideoPlayerActivity)?.minimisedByButton = true
        }

        layoutParams = initialLayout
        (weakParent?.get() as? ViewGroup)?.addView(this)
        videoPlayerWeakReference?.clear()
        videoPlayerWeakReference = null

        // Press back at the very end.
        (previousParent?.context as? VideoPlayerActivity)?.onBackPressed()
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        /**
         * show the chrome on touch
         */
        chrome?.show()
        return false
    }

    /**
     * Updates the layout, overriding its size.
     *
     * @param overriddenWidth the new width.
     * @param overriddenHeight the new height.
     */
    fun updateLayout(overriddenWidth: Int, overriddenHeight: Int) {
        val videoWidth = videoWidth
        val videoHeight = videoHeight
        surface?.layoutParams = mapBounds(
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
        val x: Float
        val y: Float
        val width: Float
        val height: Float
        if (sourceRatio > boundingRatio) {
            width = boundingWidth
            height = width / sourceRatio
            x = 0.0f
            y = (boundingHeight - height) / 2.0f
        } else {
            height = boundingHeight
            width = sourceRatio * height
            y = 0.0f
            x = (boundingWidth - width) / 2.0f
        }
        val params = LayoutParams(width.toInt(), height.toInt())
        params.setMargins(x.toInt(), y.toInt(), 0, 0)
        return params
    }

    companion object {
        private const val VIDEO_VIEW_ID = 0x1121
        internal const val FULLSCREEN_KEY = "video_fullscreen_mode"
        internal const val ROTATION_DISMISS_KEY = "video_rotation_mode"

        /**
         * Weak reference to the video player.
         */
        @JvmField
        var videoPlayerWeakReference: WeakReference<VideoPlayer>? = null
    }
}
