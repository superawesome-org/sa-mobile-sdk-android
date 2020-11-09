package tv.superawesome.sdk.publisher.common.di

import android.content.Context
import org.koin.core.module.Module
import org.koin.dsl.module
import tv.superawesome.sdk.publisher.common.repositories.MoatRepositoryType

private const val moatModuleClassName = "tv.superawesome.sdk.publisher.moat.di.MoatModule"

fun createMoatModule(applicationContext: Context, loggingEnabled: Boolean): Module =
        ProxyModuleInjector().createModule(moatModuleClassName, applicationContext, loggingEnabled)
                ?: fallbackDefaultMoatModule()

private fun fallbackDefaultMoatModule() = module {
    single { object : MoatRepositoryType {} }
}