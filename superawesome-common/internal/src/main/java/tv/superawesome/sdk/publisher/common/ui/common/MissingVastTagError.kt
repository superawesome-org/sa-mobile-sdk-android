package tv.superawesome.sdk.publisher.common.ui.common

internal class MissingVastTagError: Throwable() {
    override val message: String
        get() = "Missing Vast or tag parameter on ad response"
}
