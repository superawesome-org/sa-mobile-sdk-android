package tv.superawesome.sdk.publisher.common.openmeasurement.error

class AdSessionUnavailableFailureThrowable: Throwable() {
    override val message: String = "No ad session available"
}
