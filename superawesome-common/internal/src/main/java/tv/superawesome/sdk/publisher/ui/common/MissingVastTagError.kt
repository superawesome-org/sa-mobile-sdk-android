package tv.superawesome.sdk.publisher.ui.common

internal class MissingVastTagError: Throwable() {
    override val message: String
        get() = "Missing Vast or tag parameter on ad response"
}
