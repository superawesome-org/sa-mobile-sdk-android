package tv.superawesome.sdk.publisher.ui.di

import org.koin.core.module.Module
import org.koin.dsl.module
import tv.superawesome.lib.savideoplayer.IVideoPlayerController
import tv.superawesome.lib.savideoplayer.VideoPlayerController
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.ui.common.*
import tv.superawesome.sdk.publisher.ui.video.VideoComponentFactory
import tv.superawesome.sdk.publisher.ui.video.VideoEvents

fun createUiModule(): Module = module {
    factory<AdControllerType> { AdController(get(), get(), get(), get(), get()) }
    factory { ParentalGate(get(), get()) }
    factory<ViewableDetectorType> { ViewableDetector() }
    factory<IVideoPlayerController> { VideoPlayerController() }
    factory { VideoComponentFactory() }
    factory { (adResponse: AdResponse, moatLimiting: Boolean) -> VideoEvents(adResponse, moatLimiting, get(), get()) }
}