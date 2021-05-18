package tv.superawesome.sdk.publisher.videoPlayer

import android.widget.VideoView

/**
 * This protocol defines the public interface for a video player.
 * A video player brings together a:
 * - media control
 * - chrome control
 * - video surfaces
 * to play local or remote videos
 */
interface IVideoPlayer : IVideoPlayerController.Listener, IVideoPlayerControllerView.Listener {
    /**
     * Sets the media control for the video player
     * @param control - an instance of an object that implements the IVideoPlayerController protocol
     */
    fun setController(control: IVideoPlayerController)

    /**
     * Sets the chrome control for the video player
     * @param chrome - an instance of an object that implements the IVideoPlayerControllerView protocol
     */
    fun setControllerView(chrome: IVideoPlayerControllerView)
    fun setFullscreenMode(mode: FullscreenMode?)
    fun setCanDismissOnRotateToPortrait(canDismissOnRotateToPortrait: Boolean)

    /**
     * Sets the state of the video to maximised
     */
    fun setMaximised()

    /**
     * Sets the state of the video to minimised
     */
    fun setMinimised()

    /**
     * Destroy & release the whole video player
     */
    fun destroy()

    /**
     * Getter for the video view surface
     * @return a valid video view
     */
    val surface: VideoView?

    /**
     * Setter for the IVideoPlayer's listener
     * @param listener an instance of an object that implements IVideoPlayer.Listener
     */
    fun setListener(listener: Listener)

    /**
     * The Listener of the video player
     */
    interface Listener {
        /**
         * This method is called by the video player when it is prepared and
         * has started to play a video
         * @param player - the current videoPlayer instance
         * @param time - the current time of the video player
         * @param duration - the current total duration of the video player
         */
        fun onPrepared(player: IVideoPlayer, time: Int, duration: Int)

        /**
         * This method is called by the video player when it has updated the time
         * @param player - the current videoPlayer instance
         * @param time - the current time of the video player
         * @param duration - the current total duration of the video player
         */
        fun onTimeUpdated(player: IVideoPlayer, time: Int, duration: Int)

        /**
         * This method is called by the video player when it has finished playing
         * @param player - the current videoPlayer instance
         * @param time - the current time of the video player
         * @param duration - the current total duration of the video player
         */
        fun onComplete(player: IVideoPlayer, time: Int, duration: Int)

        /**
         * This method is called by the video player when it has encountered an error
         * @param player - the current videoPlayer instance
         * @param time - the current time of the video player
         * @param duration - the current total duration of the video player
         */
        fun onError(player: IVideoPlayer, error: Throwable, time: Int, duration: Int)
    }
}
