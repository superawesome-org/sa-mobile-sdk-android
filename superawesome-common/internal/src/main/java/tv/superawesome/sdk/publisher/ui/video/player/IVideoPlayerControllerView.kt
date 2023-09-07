package tv.superawesome.sdk.publisher.ui.video.player

/**
 * This interface defines the public interface of a video's chrome.
 * (that is also used to control the video from the user's perspective)
 */
@Suppress("TooManyFunctions", "ComplexInterface")
interface IVideoPlayerControllerView {

    /**
     * Gets whether the chrome element is in a playing state or not.
     */
    val isPlaying: Boolean

    /**
     * Gets whether the chrome is in maximised state.
     */
    val isMaximised: Boolean

    /**
     * Setts the chrome in the playing state.
     */
    fun setPlaying()

    /**
     * Sets the chrome in the paused state.
     */
    fun setPaused()

    /**
     * Sets the chrome in the completed state.
     */
    fun setCompleted()

    /**
     * Sets the chrome in the error state.
     * @param error the error object.
     */
    fun setError(error: Throwable?)

    /**
     * Set the chrome's time indicators.
     * @param time current time (in seconds).
     * @param duration current duration (in seconds).
     */
    fun setTime(time: Int, duration: Int)

    /**
     * Sets the chrome in the visible state.
     */
    fun show()

    /**
     * Sets the chrome in the invisible state.
     */
    fun hide()

    /**
     * Sets the chrome in the minimised state.
     */
    fun setMinimised()

    /**
     * Sets the chrome in the maximised state.
     */
    fun setMaximised()

    /**
     * Closes the video player.
     */
    fun close()

    /**
     * Sets the listener.
     *
     * @param listener listener to be set.
     */
    fun setListener(listener: Listener?)

    /**
     * The listener interface and all the delegated methods.
     */
    interface Listener {
        /**
         * Called by the chrome control when the progress
         * bar has started to move.
         */
        fun onStartProgressBarSeek()

        /**
         * Called by the chrome control when the progress bar has finished moving.
         * @param time the value the chrome has sought to.
         */
        fun onEndProgressBarSeek(time: Int)

        /**
         * Called by the chrome control when the play button has been clicked.
         */
        fun onClickPlay()

        /**
         * Called by the chrome control when the pause button has been clicked.
         */
        fun onClickPause()

        /**
         * Called by the chrome control when the replay button has been clicked.
         */
        fun onClickReplay()

        /**
         * Called by the chrome control when the maximise button has been clicked.
         */
        fun onClickMaximise()

        /**
         * Called by the chrome control when the minimise button has been clicked.
         */
        fun onClickMinimise()

        /**
         * Called by the chrome control when the close button has been clicked.
         */
        fun onClickClose()
    }
}
