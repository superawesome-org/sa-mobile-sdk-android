package tv.superawesome.sdk.publisher.managed

import android.util.Log
import android.webkit.JavascriptInterface

class AdViewJavaScriptBridge(private val listener: Listener) {
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

    /**
     * JS interface that enables calling `webSDKReady` from a web view.
     */
    @JavascriptInterface
    fun webSDKReady() = tryListener { listener.webSDKReady() }

    private fun tryListener(block: () -> Unit) {
        try {
            block()
        } catch (e: Exception) {
            Log.d("SuperAwesome", "JSBridge Error ${e.message}")
        }
    }

    @Suppress("TooManyFunctions")
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

        /**
         * Callback for when the WebSDK has finished loading and is ready.
         */
        fun webSDKReady()
    }
}
