package tv.superawesome.sdk.publisher.ui.di

import org.koin.core.module.Module
import org.koin.dsl.module
import tv.superawesome.sdk.publisher.ui.common.AdController
import tv.superawesome.sdk.publisher.ui.common.AdControllerType

fun createUiModule(): Module = module {
    factory<AdControllerType> { AdController() }
}