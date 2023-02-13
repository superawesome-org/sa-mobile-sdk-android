package tv.superawesome.demoapp.util

import java.util.*
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.timerTask

class UIAwait(private val signal: CountDownLatch = CountDownLatch(1)) {

    private val timer = Timer()

    fun await(delay: Long) {
        timer.schedule(timerTask { signal.countDown() }, delay)
        signal.await()
    }

    private fun callTimerFunc (until: () -> Boolean) {
        timer.schedule(getTask(until), 50)
    }

    private fun getTask(until: () -> Boolean) = object: TimerTask() {
        override fun run() {
            val result = until()
            if (result) {
                signal.countDown()
            } else {
                callTimerFunc(until)
            }
        }
    }

    fun await(until: () -> Boolean) {
        callTimerFunc(until)
        signal.await()
    }
}
