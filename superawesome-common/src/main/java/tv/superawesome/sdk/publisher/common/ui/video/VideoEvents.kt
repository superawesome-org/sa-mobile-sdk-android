@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.ui.video

import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.repositories.EventRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.VastEventRepositoryType
import tv.superawesome.sdk.publisher.common.ui.common.ViewableDetectorType
import tv.superawesome.sdk.publisher.common.ui.common.videoMaxTickCount
import tv.superawesome.sdk.publisher.common.ui.video.player.IVideoPlayer

internal class VideoEvents(
    private val adResponse: AdResponse,
    private val eventRepository: EventRepositoryType
) {
    interface Listener {
        fun hasBeenVisible()
    }

    private val vastEventRepository: VastEventRepositoryType by inject(VastEventRepositoryType::class.java) {
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

    public fun prepare(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {}

    public fun complete(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
        scope.launch { vastEventRepository.complete() }
    }

    public fun error(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
        scope.launch { vastEventRepository.error() }
    }

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
            viewableDetector = get(ViewableDetectorType::class.java)
            if (videoPlayer is ViewGroup) {
                viewableDetector?.start(videoPlayer, videoMaxTickCount) {
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
