package tv.superawesome.sdk.publisher.common.ui.managed

import android.webkit.JavascriptInterface
import tv.superawesome.sdk.publisher.common.components.Logger

internal class AdViewJavaScriptBridge(private val listener: Listener, private val logger: Logger) {
    @JavascriptInterface
    fun adLoaded() = tryListener { listener.adLoaded() }

    @JavascriptInterface
    fun adEmpty() = tryListener { listener.adEmpty() }

    @JavascriptInterface
    fun adFailedToLoad() = tryListener { listener.adFailedToLoad() }

    @JavascriptInterface
    fun adAlreadyLoaded() = tryListener { listener.adAlreadyLoaded() }

    @JavascriptInterface
    fun adShown() = tryListener { listener.adShown() }

    @JavascriptInterface
    fun adFailedToShow() = tryListener { listener.adFailedToShow() }

    @JavascriptInterface
    fun adClicked() = tryListener { listener.adClicked() }

    @JavascriptInterface
    fun adEnded() = tryListener { listener.adEnded() }

    @JavascriptInterface
    fun adClosed() = tryListener { listener.adClosed() }

    @JavascriptInterface
    fun adPlaying() = tryListener { listener.adPlaying() }

    @JavascriptInterface
    fun adPaused() = tryListener { listener.adPaused() }

    private fun tryListener(block: () -> Unit) {
        try {
            block()
        } catch (error: Exception) {
            logger.error("JSBridge Error", error)
        }
    }

    interface Listener {
        fun adLoaded()

        fun adEmpty()

        fun adFailedToLoad()

        fun adAlreadyLoaded()

        fun adShown()

        fun adFailedToShow()

        fun adClicked()

        fun adEnded()

        fun adClosed()

        fun adPlaying()

        fun adPaused()
    }
}
