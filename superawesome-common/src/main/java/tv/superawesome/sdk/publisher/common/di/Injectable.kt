package tv.superawesome.sdk.publisher.common.di

import org.koin.core.Koin
import org.koin.core.KoinComponent

/**
 * [Injectable] interface let's classes to be injected with dependencies using [inject()] or [get()]
 */
interface Injectable : KoinComponent {
    override fun getKoin(): Koin = KoinInstanceProvider.koin
}

/**
 * Koin instance is registered when the AwesomeAdsSdk is initialised.
 * Separation needed to support multi modules architecture
 */
object KoinInstanceProvider {
    internal lateinit var koin: Koin
        private set

    fun register(koin: Koin) {
        this.koin = koin
    }
}