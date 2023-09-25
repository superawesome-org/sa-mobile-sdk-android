@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.ui.video

import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.repositories.EventRepositoryType
import tv.superawesome.sdk.publisher.repositories.VastEventRepositoryType
import tv.superawesome.sdk.publisher.ui.common.VIDEO_MAX_TICK_COUNT
import tv.superawesome.sdk.publisher.ui.common.ViewableDetectorType
import tv.superawesome.sdk.publisher.ui.video.player.IVideoPlayer

public class VideoEvents(
    private val adResponse: AdResponse,
    private val eventRepository: EventRepositoryType
) : KoinComponent {
    interface Listener {
        fun hasBeenVisible()
    }

    private val vastEventRepository: VastEventRepositoryType by inject {
        parametersOf(adResponse.vast)
    }

    private val scope = CoroutineScope(Dispatchers.Main)

    public var listener: Listener? = null
    private var isStartHandled = false
    private var is2SHandled = false
    private var isFirstQuartileHandled = false
    private var isMidpointHandled = false
    private var isThirdQuartileHandled = false
    private var viewableDetector: ViewableDetectorType? = null
    private var lastTick = 0

    public fun prepare(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
        // Do nothing
    }

    public fun complete(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
        scope.launch { vastEventRepository.complete() }
    }

    public fun error(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
        scope.launch { vastEventRepository.error() }
    }

    @Suppress("MagicNumber")
    public fun time(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
        handleDwellTime(time, videoPlayer)

        // Start
        if (time >= 1 && !isStartHandled) {
            isStartHandled = true

            // send vast events - including impression
            scope.launch {
                vastEventRepository.impression()
                vastEventRepository.start()
                vastEventRepository.creativeView()
            }
        }
        // 2 second (of viewing)
        if (time >= 2000 && !is2SHandled) {
            is2SHandled = true
            viewableDetector?.cancel()
            viewableDetector = get()
            if (videoPlayer is ViewGroup) {
                viewableDetector?.start(videoPlayer, VIDEO_MAX_TICK_COUNT) {
                    scope.launch { eventRepository.viewableImpression(adResponse) }
                    listener?.hasBeenVisible()
                }
            }
        }
        // 1/4
        if (time >= duration / 4 && !isFirstQuartileHandled) {
            isFirstQuartileHandled = true

            // send events
            scope.launch { vastEventRepository.firstQuartile() }
        }
        // 1/2
        if (time >= duration / 2 && !isMidpointHandled) {
            isMidpointHandled = true

            // send events
            scope.launch { vastEventRepository.midPoint() }
        }
        // 3/4
        if (time >= 3 * duration / 4 && !isThirdQuartileHandled) {
            isThirdQuartileHandled = true

            // send events
            scope.launch { vastEventRepository.thirdQuartile() }
        }
    }

    private fun handleDwellTime(time: Int, videoPlayer: IVideoPlayer?) {
        if (videoPlayer is ViewGroup) {
            viewableDetector?.start(videoPlayer, VIDEO_MAX_TICK_COUNT) {
                if (hasTicked(time)) {
                    lastTick = time
                    scope.launch { eventRepository.dwellTime(adResponse) }
                }
            }
        }
    }

    private fun hasTicked(time: Int) = (time - lastTick) / TICK == 1

    companion object {
        private const val TICK = 5000
    }
}
