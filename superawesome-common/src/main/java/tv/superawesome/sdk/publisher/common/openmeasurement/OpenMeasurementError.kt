package tv.superawesome.sdk.publisher.common.openmeasurement

/**
 * Error classes for Open Measurement.
 */
sealed class OpenMeasurementError(
    open val exception: Exception?,
    override val message: String,
) : Exception() {
    class AdSessionCreationFailure(
        override val exception: Exception? = null,
    ) : OpenMeasurementError(exception, "Unable to create the ad session")

    class AdSessionStartFailure(
        override val exception: Exception? = null,
    ) : OpenMeasurementError(exception, "Unable to start the ad session")

    class AdSessionUnavailableFailure(
        override val exception: Exception? = null,
    ) : OpenMeasurementError(exception, "No ad session available")

    class OmidJSNotLoaded(
        override val exception: Exception? = null,
    ) : OpenMeasurementError(exception, "The omidJS was not loaded")

    class UpdateAdViewFailure(
        override val exception: Exception? = null,
    ) : OpenMeasurementError(exception, "Unable to update the ad view")
}
