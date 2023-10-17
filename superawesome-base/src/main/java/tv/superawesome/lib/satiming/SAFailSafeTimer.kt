package tv.superawesome.lib.satiming

import android.os.CountDownTimer

class SAFailSafeTimer(private var timeout: Long = 15000L,
                      private val interval: Long = 1000L) {

    var listener: Listener? = null

    private var startTime: Long = 0L
    private var timer: CountDownTimer? = null
    private val deductedTime: Long
        get() = System.currentTimeMillis() - startTime

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

    fun pause() {
        clearTimer()
        timeout -= deductedTime
    }

    fun stop() {
        timeout = 0
        clearTimer()
    }

    private fun clearTimer() {
        timer?.cancel()
        timer = null
    }

    interface Listener {
        fun failSafeDidTimeOut()
    }
}