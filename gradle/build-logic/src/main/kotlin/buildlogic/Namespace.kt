package buildlogic

object Namespace {

    const val base = "tv.superawesome"

    object SDK {
        const val publisher = "${base}.sdk.publisher"
    }

    val sdk = SDK

    object Plugins {
        const val publisher = "${base}.plugins.publisher"
    }

    val plugins = Plugins
}
