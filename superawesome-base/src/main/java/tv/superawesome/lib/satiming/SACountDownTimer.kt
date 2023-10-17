package tv.superawesome.lib.satiming

import android.os.CountDownTimer

/**
 * This class provides a countdown timer that calls the Listener back onFinish.
 * Designed to be used with a timeout of 15000 milliseconds.
 */
class SACountDownTimer(private var timeout: Long = 15000L,
                       private val interval: Long = 1000L) {

    var listener: Listener? = null

    private var startTime: Long = 0L
    private var timer: CountDownTimer? = null
    private val deductedTime: Long
        get() = System.currentTimeMillis() - startTime

    /**
     * This method starts the countdown timer. It will also clear any previously started timer.
     */
    fun start() {
        clearTimer()
        startTime = System.currentTimeMillis()
        if (timeout <= 0L) return

        timer = object: CountDownTimer(timeout, interval) {
            override fun onTick(millisUntilFinished: Long) = Unit

            override fun onFinish() {
                stop()
                listener?.failSafeDidTimeOut()
            }
        }.start()
    }

    /**
     * This method simulates a pause action by clearing the current timer and storing the remaining
     * milliseconds of the countdown which are used as the timeout of a new timer when start is
     * called again.
     */
    fun pause() {
        clearTimer()
        timeout -= deductedTime
    }

    /**
     * This method clears the current timer and sets the timeout to 0.
     */
    fun stop() {
        timeout = 0
        clearTimer()
    }

    private fun clearTimer() {
        timer?.cancel()
        timer = null
    }

    /**
     * A listener interface for the timer
     */
    interface Listener {
        /**
         * A method that is called when the timer has reached the end of the countdown.
         */
        fun failSafeDidTimeOut()
    }
}