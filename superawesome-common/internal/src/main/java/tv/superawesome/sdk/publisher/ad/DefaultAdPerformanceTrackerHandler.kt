package tv.superawesome.sdk.publisher.ad

import tv.superawesome.sdk.publisher.components.TimeProviderType
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.PerformanceTimer
import tv.superawesome.sdk.publisher.repositories.PerformanceRepositoryType

/**
 * Default ad performance tracker handler, should work with any type of ad.
 */
class DefaultAdPerformanceTrackerHandler(
    private val performanceRepository: PerformanceRepositoryType,
    private val timeProvider: TimeProviderType,
    override val adResponse: AdResponse,
) : AdPerformanceTrackerHandler {

    private val loadTimeTimer by lazy { PerformanceTimer() }
    private val dwellTimeTimer by lazy { PerformanceTimer() }
    private val closeButtonPressedTimer by lazy { PerformanceTimer() }

    override fun startTimerForLoadTime() {
        loadTimeTimer.start(timeProvider.millis())
    }

    override suspend fun trackLoadTime() {
        trackTimer(loadTimeTimer)
    }

    override fun startTimerForDwellTime() {
        dwellTimeTimer.start(timeProvider.millis())
    }

    override suspend fun trackDwellTime() {
        trackTimer(dwellTimeTimer)
    }

    override fun startTimerForCloseButtonPressed() {
        closeButtonPressedTimer.start(timeProvider.millis())
    }

    override suspend fun trackCloseButtonPressed() {
        trackTimer(closeButtonPressedTimer)
    }

    private suspend fun trackTimer(timer: PerformanceTimer) {
        if (timer.startTime == 0L) return
        performanceRepository.trackLoadTime(
            timer.delta(timeProvider.millis()),
            adResponse,
        )
    }
}
