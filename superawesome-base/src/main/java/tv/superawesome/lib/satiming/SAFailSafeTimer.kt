package tv.superawesome.lib.satiming

import android.os.CountDownTimer

interface SAFailSafeTimerDelegate {
    fun failSafeDidTimeOut()
}

class SAFailSafeTimer(private var timeInterval: Long = 15000) {

    var delegate: SAFailSafeTimerDelegate? = null

    private var startTime: Long = 0
    private var timerHasFired: Boolean = false
    private var timer: CountDownTimer? = null
    private val deductedTime: Long
        get() = System.currentTimeMillis() - startTime

    val timerDidFire: Boolean
        get() = timerHasFired

    fun startFailSafeTimer() {
        clearTimer()
        startTime = System.currentTimeMillis()
        if (timeInterval <= 0L) return

        timer = object: CountDownTimer(timeInterval, 1000) {
            override fun onTick(millisUntilFinished: Long) = Unit

            override fun onFinish() {
                stopFailSafeTimer()
                delegate?.failSafeDidTimeOut()
            }
        }
        timer?.start()
    }

    fun pauseFailSafeTimer() {
        clearTimer()
        timeInterval -= deductedTime
    }

    fun stopFailSafeTimer() {
        timeInterval = 0
        clearTimer()
    }

    private fun clearTimer() {
        timer?.cancel()
        timer = null
    }
}