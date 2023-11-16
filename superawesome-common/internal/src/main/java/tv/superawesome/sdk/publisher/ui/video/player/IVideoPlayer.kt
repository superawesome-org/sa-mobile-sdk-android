package tv.superawesome.sdk.publisher.ui.video.player

import android.widget.VideoView

/**
 * Defines the public interface for a video player.
 * A video player brings together:
 * - media control
 * - chrome control
 * - video surfaces
 * To play local or remote videos.
 */
interface IVideoPlayer : IVideoPlayerController.Listener, IVideoPlayerControllerView.Listener {

    /**
     * Gets the video view surface.
     */
    val surface: VideoView?

    /**
     * Sets the media control for the video player.
     *
     * @param control an object that implements [IVideoPlayerController].
     */
    fun setController(control: IVideoPlayerController)

    /**
     * Sets the chrome control for the video player.
     *
     * @param chrome an object that implements [IVideoPlayerControllerView].
     */
    fun setControllerView(chrome: IVideoPlayerControllerView)

    /**
     * Sets the fullscreen mode.
     *
     * @param mode the desired [FullscreenMode].
     */
    fun setFullscreenMode(mode: FullscreenMode?)

    fun setCanDismissOnRotateToPortrait(canDismissOnRotateToPortrait: Boolean)

    /**
     * Sets the state of the video to maximised.
     */
    fun setMaximised()

    /**
     * Sets the state of the video to minimised.
     */
    fun setMinimised()

    /**
     * Destroys and release the whole video player.
     */
    fun destroy()

    /**
     * Sets the video player listener.
     * @param listener an object that implements [IVideoPlayer.Listener].
     */
    fun setListener(listener: Listener)

    /**
     * The Listener of the video player.
     */
    interface Listener {

        /**
         * Called by the video player when it is prepared and has started to play a video.
         *
         * @param player current video player instance.
         * @param time current time of the video player.
         * @param duration current total duration of the media.
         */
        fun onPrepared(player: IVideoPlayer, time: Int, duration: Int)

        /**
         * Called by the video player when it has updated the time.
         *
         * @param player current video player instance.
         * @param time current time of the video player.
         * @param duration the current total duration of the media.
         */
        fun onTimeUpdated(player: IVideoPlayer, time: Int, duration: Int)

        /**
         * Called by the video player when it has finished playing.
         *
         * @param player current video player instance.
         * @param time current time of the video player
         * @param duration current total duration of the media.
         */
        fun onComplete(player: IVideoPlayer, time: Int, duration: Int)

        /**
         * Called by the video player when it has encountered an error.
         *
         * @param player current video player instance.
         * @param error the error that occurred.
         * @param time current time of the video player.
         * @param duration the current total duration of the media.
         */
        fun onError(player: IVideoPlayer, error: Throwable, time: Int, duration: Int)

        /**
         * Called by the video player when playback starts.
         *
         * @param player current video player instance.
         */
        fun onPlay(player: IVideoPlayer)

        /**
         * Called by the video player when it is paused.
         *
         * @param player current video player instance.
         */
        fun onPause(player: IVideoPlayer)
    }
}
