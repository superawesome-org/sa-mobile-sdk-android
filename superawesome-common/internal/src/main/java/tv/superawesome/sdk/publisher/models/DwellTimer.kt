package tv.superawesome.sdk.publisher.models

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Launches a dwell timer on a given [coroutineScope], executed every [delay] ms until it's
 * cancelled.
 */
class DwellTimer(
    private val delay: Long,
    private val coroutineScope: CoroutineScope,
) {

    private var job: Job? = null

    /**
     * Starts a new dwell timer, executing [callback] every [delay] ms.
     */
    fun start(callback: () -> Unit) {
        if (job != null) return

        job = coroutineScope.launch {
            while (isActive) {
                delay(delay)
                callback()
            }
        }
    }

    /**
     * Stops the dwell timer.
     */
    fun stop() {
        job?.cancel()
        job = null
    }
}
