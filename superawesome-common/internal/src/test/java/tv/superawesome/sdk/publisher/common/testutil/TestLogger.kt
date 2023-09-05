package tv.superawesome.sdk.publisher.common.testutil

import tv.superawesome.sdk.publisher.common.components.Logger

class TestLogger : Logger {
    override fun info(message: String) {
        println("INFO-$message")
    }

    override fun success(message: String) {
        println("SUCCESS-$message")
    }

    override fun error(message: String, error: Throwable?) {
        println("ERROR-$message")
        error?.printStackTrace()
    }
}
