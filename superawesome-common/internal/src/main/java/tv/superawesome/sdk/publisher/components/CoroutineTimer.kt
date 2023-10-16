package tv.superawesome.sdk.publisher.components

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * A generic coroutine-enabled timer that executes an [action] after a certain [delay].
 */
class CoroutineTimer(
    private val delay: Long,
    private val timeProvider: TimeProviderType,
    private val scope: CoroutineScope,
    private val action: () -> Unit,
) {

    private var job: Job? = null
    private var remainingTime = 0L
    private var startTime = 0L

    /**
     * Starts the timer.
     * If the timer has already been started, do nothing.
     */
    fun start() {
        if (job != null) return

        startTime = timeProvider.millis()
        job = scope.launch {
            delay(delay)
            if (isActive) {
                action()
            }
        }
    }

    /**
     * Pauses the timer.
     * If the timer has already been paused, do nothing.
     */
    fun pause() {
        if (job == null || remainingTime > 0L) return

        remainingTime = delay - (timeProvider.millis() - startTime)
        job?.cancel()
        job = null
    }

    /**
     * Resumes the timer.
     * If the timer hasn't been paused, do nothing.
     */
    fun resume() {
        if (job != null || remainingTime == 0L) return

        startTime = timeProvider.millis()
        job = scope.launch {
            delay(remainingTime)
            if (isActive) {
                action()
            }
        }
    }

    /**
     * Stops the timer. Timer can't be restarted after it's been stopped.
     */
    fun stop() {
        job?.cancel()
        job = null
        startTime = 0L
        remainingTime = 0L
    }
}
