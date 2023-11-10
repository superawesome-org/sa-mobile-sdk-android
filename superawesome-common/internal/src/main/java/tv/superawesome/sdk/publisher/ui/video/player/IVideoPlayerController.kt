package tv.superawesome.sdk.publisher.ui.video.player

import android.content.Context
import android.net.Uri
import android.view.SurfaceHolder

/**
 * This interface defines the public interface for any object that can be
 * used to control a piece of media.
 */
@Suppress("TooManyFunctions", "ComplexInterface")
interface IVideoPlayerController {

    /**
     * Get if the video is currently muted.
     */
    val isMuted: Boolean

    /**
     * Gets whether the video is currently playing.
     */
    val isIVideoPlaying: Boolean

    /**
     * Gets the width of the video resource.
     */
    val videoIVideoWidth: Int

    /**
     * Gets the height of the video resource.
     */
    val videoIVideoHeight: Int

    /**
     * Gets the total duration, in seconds, of the media control.
     */
    val iVideoDuration: Long

    /**
     * Gets the current position, in seconds, of the media control.
     */
    val currentIVideoPosition: Long

    /**
     * Plays the media in a synchronously.
     *
     * @param context the current context.
     * @param uri a Uri where the media is located.
     */
    fun play(context: Context, uri: Uri)

    /**
     * Plays the media in an asynchronously.
     *
     * @param context the current context.
     * @param uri a Uri where the media is located.
     */
    fun playAsync(context: Context, uri: Uri)

    /**
     * Resumes the media play.
     */
    fun start()

    /**
     * Pauses the media play.
     */
    fun pause()

    /**
     * Resets the state of the [IVideoPlayerController], but should
     * not release all resources so that it becomes unavailable.
     */
    fun reset()

    /**
     * Frees up all resources owned by the [IVideoPlayerController].
     * From here on, it cannot be re-used to play media.
     */
    fun destroy()

    /**
     * Seeks to a different position in the video.
     *
     * @param position the position to seek to.
     */
    fun seekTo(position: Int)

    /**
     * Sets the video muted state.
     *
     * @param muted `true` to mute the video, `false` to unmute.
     */
    fun setMuted(muted: Boolean)

    /**
     * Sets the current surface holder of the media control.
     * @param holder a surface holder, null or otherwise.
     */
    fun setDisplay(holder: SurfaceHolder?)

    /**
     * Sets the listener.
     * @param listener listener to be added.
     */
    fun setListener(listener: Listener)

    /**
     * Creates the timer for the video player.
     * This timer will interact with [Listener.onTimeUpdated] that itself sets the chrome time.
     */
    fun createTimer()

    /**
     * Removes the current timer.
     */
    fun removeTimer()

    /**
     * The listener interface and all the delegated methods.
     */
    interface Listener {
        /**
         * Called by the [IVideoPlayerController] when the media is prepared.
         *
         * @param control current media control instance.
         */
        fun onPrepared(control: IVideoPlayerController)

        /**
         * Called by the IVideoPlayerController when the time has been updated.
         *
         * @param control current media control instance.
         * @param time current media time.
         * @param duration total duration of the media.
         */
        fun onTimeUpdated(control: IVideoPlayerController, time: Int, duration: Int)

        /**
         * Called by the IVideoPlayerController when the media is finished.
         *
         * @param control current media control instance.
         * @param time current media time.
         * @param duration total duration of the media.
         */
        fun onMediaComplete(control: IVideoPlayerController, time: Int, duration: Int)

        /**
         * Called by the IVideoPlayerController when seeking is over.
         *
         * @param control current media control instance.
         */
        fun onSeekComplete(control: IVideoPlayerController)

        /**
         * Called by the IVideoPlayerController when an error occurs.
         *
         * @param control current media control instance
         * @param error exception that occurred.
         * @param time current media time
         * @param duration total duration of the media
         */
        fun onError(control: IVideoPlayerController, error: Throwable, time: Int, duration: Int)
    }
}
