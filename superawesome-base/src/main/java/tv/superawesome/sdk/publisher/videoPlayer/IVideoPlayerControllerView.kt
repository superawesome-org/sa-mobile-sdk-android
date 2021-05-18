package tv.superawesome.sdk.publisher.videoPlayer

/**
 * This interface defines the public interface of a video's chrome
 * (that is also used to control the video from the user's perspective)
 */
interface IVideoPlayerControllerView {
    /**
     * Method that sets the chrome in the playing state
     */
    fun setPlaying()

    /**
     * Method that sets the chrome in the paused state
     */
    fun setPaused()

    /**
     * Method that sets the chrome in the completed state
     */
    fun setCompleted()

    /**
     * Method that sets the chrome in the error state
     * @param: error - the error object
     */
    fun setError(error: Throwable?)

    /**
     * The chrome gets it's time indicators updated
     * @param time current time (in seconds)
     * @param duration current duration (in seconds)
     */
    fun setTime(time: Int, duration: Int)

    /**
     * @return whether the chrome element is in a playing state or not
     */
    val isPlaying: Boolean

    /**
     * Method that sets the chrome in the visible state
     */
    fun show()

    /**
     * Method that sets the chrome in the invisible state
     */
    fun hide()

    /**
     * Method that sets the chrome in the minimised state
     */
    fun setMinimised()

    /**
     * Method that sets the chrome in the maximised state
     */
    fun setMaximised()

    /**
     * Method that returns whether the chrome is in maximised or minimised state
     */
    val isMaximised: Boolean

    /**
     * Called when the chrome would want to close the video player somehow
     */
    fun close()

    /**
     * Set the listener
     */
    fun setListener(listener: Listener?)

    /**
     * The listener interface and all the delegated methods
     */
    interface Listener {
        /**
         * This method is called by the chrome control when the progress
         * bar has started to move
         */
        fun onStartProgressBarSeek()

        /**
         * This method is called by the chrome control when the progress
         * bar has finished to move
         * @param time - the value the chrome has seeked to
         */
        fun onEndProgressBarSeek(time: Int)

        /**
         * This method is called by the chrome control when the
         * play button has been clicked
         */
        fun onClickPlay()

        /**
         * This method is called by the chrome control when the
         * pause button has been clicked
         */
        fun onClickPause()

        /**
         * This method is called by the chrome control when the
         * replayu button has been clicked
         */
        fun onClickReplay()

        /**
         * This method is called by the chrome control when the
         * maximise button has been clicked
         */
        fun onClickMaximise()

        /**
         * This method is called by the chrome control when the
         * minimise button has been clicked
         */
        fun onClickMinimise()

        /**
         * This method is called by the chrome control when the
         * close button has been clicked
         */
        fun onClickClose()
    }
}
