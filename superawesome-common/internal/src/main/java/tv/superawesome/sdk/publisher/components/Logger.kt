package tv.superawesome.sdk.publisher.components

import android.util.Log

interface Logger {
    fun info(message: String)
    fun success(message: String)
    fun error(message: String, error: Throwable? = null)
}

class DefaultLogger(private val loggingEnabled: Boolean) : Logger {

    @Suppress("ThrowingExceptionsWithoutMessageOrCause")
    private val stackTrace: StackTraceElement?
        get() = Exception().stackTrace.first { it.className != DefaultLogger::class.java.name }

    private val callerInformation: String
        get() {
            val stackTrace = this.stackTrace ?: return ""
            return stackTrace.className.substringAfterLast('.')
        }

    override fun info(message: String) {
        if (!loggingEnabled) return
        Log.i(callerInformation, "⬜️ $message")
    }

    override fun success(message: String) {
        if (!loggingEnabled) return
        Log.i(callerInformation, "🟩 $message")
    }

    override fun error(message: String, error: Throwable?) {
        if (!loggingEnabled) return
        Log.i(callerInformation, "🟥 $message \n $error")
    }
}
