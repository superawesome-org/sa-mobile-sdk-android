package tv.superawesome.sdk.publisher.managed

import android.webkit.JavascriptInterface

class AdViewJavaScriptBridge(private val listener: Listener) {
    @JavascriptInterface
    fun adLoaded() = listener.adLoaded()

    @JavascriptInterface
    fun adEmpty() = listener.adEmpty()

    @JavascriptInterface
    fun adFailedToLoad() = listener.adFailedToLoad()

    @JavascriptInterface
    fun adAlreadyLoaded() = listener.adAlreadyLoaded()

    @JavascriptInterface
    fun adShown() = listener.adShown()

    @JavascriptInterface
    fun adFailedToShow() = listener.adFailedToShow()

    @JavascriptInterface
    fun adClicked() = listener.adClicked()

    @JavascriptInterface
    fun adEnded() = listener.adEnded()

    @JavascriptInterface
    fun adClosed() = listener.adClosed()

    @JavascriptInterface
    fun adPlaying() = listener.adPlaying()

    @JavascriptInterface
    fun adPaused() = listener.adPaused()

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