package tv.superawesome.sdk.publisher.common.ui.video.player

import android.content.Context
import android.net.Uri
import android.view.SurfaceHolder

/**
 * This interface defines the public interface for any object that can be
 * used to control a piece of media.
 */
interface IVideoPlayerController {
    /**
     * This will play the media in a sync way
     * @param context the current Android context
     * @param uri a valid Uri
     */
    fun play(context: Context, uri: Uri)

    /**
     * This will play the media in an async way
     * @param context the current Android context
     * @param uri a valid Uri
     */
    fun playAsync(context: Context, uri: Uri)

    /**
     * Method that resumes the media play
     */
    fun start()

    /**
     * Method that pauses the media play
     */
    fun pause()

    /**
     * Method that resets the state of the IVideoPlayerController, but should
     * not release all resources so that it becomes unavailable
     */
    fun reset()

    /**
     * Method that frees up all resources owned by the IVideoPlayerController;
     * From here on, it cannot be re-used to play media
     */
    fun destroy()

    /**
     * Method to seek to a different position
     */
    fun seekTo(position: Int)

    /**
     * @return true or false, depending on whether the media control is playing
     */
    val isIVideoPlaying: Boolean

    /**
     * Method that sets the current surface holder of the media control
     * @param holder a surface holder, null or otherwise
     */
    fun setDisplay(holder: SurfaceHolder?)

    /**
     * @return the width of the video resource
     */
    val videoIVideoWidth: Int

    /**
     * @return the height of the video resource
     */
    val videoIVideoHeight: Int

    /**
     * Method that returns the total duration, in seconds, of the
     * media control
     */
    val iVideoDuration: Int

    /**
     * Method that returns the current position, in seconds, of the
     * media control
     */
    val currentIVideoPosition: Int

    /**
     * @param listener the listener to be added
     */
    fun setListener(listener: Listener)

    /**
     * Method that creates the timer for the video player.
     * This timer will interact with Listener.onTimeUpdated that itself sets the chrome time.
     */
    fun createTimer()

    /**
     * Method that removes the current timer.
     */
    fun removeTimer()

    /**
     * The listener interface and all the delegated methods
     */
    interface Listener {
        /**
         * This method is called by the IVideoPlayerController when the media is prepared
         * @param control - the current media control instance
         */
        fun onPrepared(control: IVideoPlayerController)

        /**
         * This method is called by the IVideoPlayerController when the time has been updated
         * @param control - the current media control instance
         * @param time - the current media time
         * @param duration - the total duration of the media
         */
        fun onTimeUpdated(control: IVideoPlayerController, time: Int, duration: Int)

        /**
         * This method is called by the IVideoPlayerController when the media is finished
         * @param control - the current media control instance
         * @param time - the current media time
         * @param duration - the total duration of the media
         */
        fun onMediaComplete(control: IVideoPlayerController, time: Int, duration: Int)

        /**
         * This method is called by the IVideoPlayerController when seeking is over
         * @param control - the current media control instance
         */
        fun onSeekComplete(control: IVideoPlayerController)

        /**
         * This method is called by the IVideoPlayerController when an error happened
         * @param control - the current media control instance
         * @param time - the current media time
         * @param duration - the total duration of the media
         */
        fun onError(control: IVideoPlayerController, error: Throwable, time: Int, duration: Int)
    }
}