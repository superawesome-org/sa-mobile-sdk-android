package tv.superawesome.sdk.publisher.moat.di

import android.app.Application
import com.moat.analytics.mobile.sup.MoatAnalytics
import com.moat.analytics.mobile.sup.MoatOptions
import org.koin.dsl.module
import tv.superawesome.sdk.publisher.common.di.ProxyModule
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.repositories.MoatRepositoryType
import tv.superawesome.sdk.publisher.moat.repositories.MoatRepository

class MoatModule : ProxyModule {
    override fun init(vararg args: Any) {
        val options = MoatOptions()
        options.disableAdIdCollection = true
        options.loggingEnabled = args[1] as? Boolean ?: false
        MoatAnalytics.getInstance().start(options, args[0] as? Application)
    }

    override fun create() = module {
        factory<MoatRepositoryType> { (adResponse: AdResponse, moatLimiting: Boolean) ->
            MoatRepository(adResponse, moatLimiting, get(), get())
        }
    }
}