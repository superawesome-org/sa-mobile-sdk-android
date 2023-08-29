@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.ui.video

import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.repositories.EventRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.VastEventRepositoryType
import tv.superawesome.sdk.publisher.common.ui.common.ViewableDetectorType
import tv.superawesome.sdk.publisher.common.ui.common.VIDEO_MAX_TICK_COUNT
import tv.superawesome.sdk.publisher.common.ui.video.player.IVideoPlayer

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
        if (viewableDetector?.isVisible == null) {
            viewableDetector?.isVisible = {
                scope.launch {
                    eventRepository.oneSecondDwellTime(adResponse)
                }
            }
        }
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
}
