@file:Suppress("ClassOrdering", "MagicNumber")
package tv.superawesome.lib.satiming

import android.os.CountDownTimer
import java.lang.Long.max

/**
 * This class provides a countdown timer that calls the Listener back onFinish.
 */
class SACountDownTimer(private var timeout: Long = 15_000L,
                       private val interval: Long = 1_000L) {

    /**
     * Secondary constructor, with default interval of 1s between ticks.
     */
    constructor(timeout: Long = 15_000): this(timeout, 1_000)

    /**
     * The listener for the timer.
     * Set this to perform an action when the timer has finished counting down.
     */
    var listener: Listener? = null

    private var startTime: Long = 0L
    private var timer: CountDownTimer? = null
    private val deductedTime: Long
        get() = System.currentTimeMillis() - startTime

    /**
     * This method starts the countdown timer. It will also clear any previously created timer.
     */
    fun start() {
        clearTimer()
        startTime = System.currentTimeMillis()
        if (timeout == 0L) return
        timer = object: CountDownTimer(timeout, interval) {
            override fun onTick(millisUntilFinished: Long) = Unit

            override fun onFinish() {
                listener?.didTimeOut()
                stop()
            }
        }.start()
    }

    /**
     * This method pauses the countdown.
     * The countdown is paused by clearing the current timer and storing the remaining
     * milliseconds of the countdown which are used as the timeout of a new timer when start is
     * called again.
     */
    fun pause() {
        clearTimer()
        timeout = max(0L, timeout - deductedTime)
    }

    /**
     * This method clears the current timer and sets the timeout to 0.
     * The timer cannot be started again after being stopped.
     */
    fun stop() {
        timeout = 0
        clearTimer()
        listener = null
    }

    private fun clearTimer() {
        timer?.cancel()
        timer = null
    }

    /**
     * A listener interface for the timer.
     */
    fun interface Listener {
        /**
         * A method that is called when the timer has reached the end of the countdown.
         */
        fun didTimeOut()
    }
}
