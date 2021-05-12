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
import tv.superawesome.sdk.publisher.common.repositories.MoatRepositoryType
import tv.superawesome.sdk.publisher.common.repositories.VastEventRepositoryType
import tv.superawesome.sdk.publisher.common.ui.common.ViewableDetectorType
import tv.superawesome.sdk.publisher.common.ui.video.player.IVideoPlayer

class VideoEvents(
    private val adResponse: AdResponse,
    private val moatLimiting: Boolean,
    private val eventRepository: EventRepositoryType
) {
    interface Listener {
        fun hasBeenVisible()
    }

    private val vastEventRepository: VastEventRepositoryType by inject(VastEventRepositoryType::class.java) {
        parametersOf(adResponse.vast)
    }
    private val moatRepository: MoatRepositoryType by inject(MoatRepositoryType::class.java) {
        parametersOf(
            moatLimiting
        )
    }

    private val scope = CoroutineScope(Dispatchers.Main)

    var listener: Listener? = null
    private var isStartHandled = false
    private var is2SHandled = false
    private var isFirstQuartileHandled = false
    private var isMidpointHandled = false
    private var isThirdQuartileHandled = false
    private var viewableDetector: ViewableDetectorType? = null

    fun prepare(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
        if (videoPlayer?.surface != null) {
            moatRepository.startMoatTrackingForVideoPlayer(
                videoPlayer.surface,
                duration,
                adResponse
            )
        }
    }

    fun complete(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
        moatRepository.sendCompleteEvent(duration)
        scope.launch { vastEventRepository.complete() }
        moatRepository.stopMoatTrackingForVideoPlayer()
    }

    fun error(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
        moatRepository.stopMoatTrackingForVideoPlayer()
        scope.launch { vastEventRepository.error() }
    }

    fun time(videoPlayer: IVideoPlayer?, time: Int, duration: Int) {
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

            // moat
            moatRepository.sendPlayingEvent(time)
            moatRepository.sendStartEvent(time)
        }
        // 2 second (of viewing)
        if (time >= 2000 && !is2SHandled) {
            is2SHandled = true
            viewableDetector?.cancel()
            viewableDetector = get(ViewableDetectorType::class.java)
            if (videoPlayer is ViewGroup) {
                viewableDetector?.start(videoPlayer) {
                    scope.launch { eventRepository.viewableImpression(adResponse) }
                    listener?.hasBeenVisible()
                }
            }
        }
        // 1/4
        if (time >= duration / 4 && !isFirstQuartileHandled) {
            isFirstQuartileHandled = true

            // send events
            moatRepository.sendFirstQuartileEvent(time)
            scope.launch { vastEventRepository.firstQuartile() }
        }
        // 1/2
        if (time >= duration / 2 && !isMidpointHandled) {
            isMidpointHandled = true

            // send events
            moatRepository.sendMidpointEvent(time)
            scope.launch { vastEventRepository.midPoint() }
        }
        // 3/4
        if (time >= 3 * duration / 4 && !isThirdQuartileHandled) {
            isThirdQuartileHandled = true

            // send events
            scope.launch { vastEventRepository.thirdQuartile() }
            moatRepository.sendThirdQuartileEvent(time)
        }
    }
}
