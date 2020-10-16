package tv.superawesome.sdk.publisher.ui.video

import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.get
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import tv.superawesome.lib.savideoplayer.IVideoPlayer
import tv.superawesome.sdk.publisher.common.components.DispatcherProviderType
import tv.superawesome.sdk.publisher.common.di.Injectable
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.repositories.EventRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.VastEventRepositoryType
import tv.superawesome.sdk.publisher.ui.common.ViewableDetectorType

class VideoEvents(
        private val adResponse: AdResponse,
        private val eventRepository: EventRepositoryType,
        dispatcherProvider: DispatcherProviderType,
) : Injectable {
    interface Listener {
        fun hasBeenVisible()
    }
    private val vastEventRepository : VastEventRepositoryType by inject { parametersOf(adResponse) }

    private val scope = CoroutineScope(dispatcherProvider.main)

    var listener: Listener? = null
    private var isStartHandled = false
    private var is2SHandled = false
    private var isFirstQuartileHandled = false
    private var isMidpointHandled = false
    private var isThirdQuartileHandled = false
    private var viewableDetector: ViewableDetectorType? = null

    fun prepare(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
        if (videoPlayer != null && videoPlayer.surface != null) {
//            events.startMoatTrackingForVideoPlayer(videoPlayer.surface, duration)
        }
    }

    fun complete(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
//        events.sendMoatCompleteEvent(duration)
        scope.launch { vastEventRepository.complete() }
//        events.stopMoatTrackingForVideoPlayer()
    }

    fun error(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
//        events.stopMoatTrackingForVideoPlayer()
        scope.launch { vastEventRepository.error() }
    }

    fun time(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
        // Start
        if (time >= 1 && !isStartHandled) {
            isStartHandled = true

            // send vast events - including impression
            scope.launch {
                vastEventRepository.impression()
                vastEventRepository.start()
                vastEventRepository.creativeView()
            }

            // moat
//            events.sendMoatPlayingEvent(time)
//            events.sendMoatStartEvent(time)
        }
        // 2 second (viewability)
        if (time >= 2000 && !is2SHandled) {
            is2SHandled = true
            viewableDetector?.cancel()
            viewableDetector = get()
            if (videoPlayer is ViewGroup) {
                viewableDetector?.start(videoPlayer) {
//                    scope.launch { eventRepository.viewableImpression() }
//                    events.triggerViewableImpressionEvent()
                    listener?.hasBeenVisible()
                }
            }
        }
        // 1/4
        if (time >= duration / 4 && !isFirstQuartileHandled) {
            isFirstQuartileHandled = true

            // send events
//            events.sendMoatFirstQuartileEvent(time)
            scope.launch { vastEventRepository.firstQuartile() }
        }
        // 1/2
        if (time >= duration / 2 && !isMidpointHandled) {
            isMidpointHandled = true

            // send events
//            events.sendMoatMidpointEvent(time)
            scope.launch { vastEventRepository.midPoint() }
        }
        // 3/4
        if (time >= 3 * duration / 4 && !isThirdQuartileHandled) {
            isThirdQuartileHandled = true

            // send events
            scope.launch { vastEventRepository.thirdQuartile() }
//            events.sendMoatThirdQuartileEvent(time)
        }
    }
}