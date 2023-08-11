package tv.superawesome.sdk.publisher.common.openmeasurement

/**
* Interface for OMID JavaScript injector
*/
interface OpenMeasurementJSInjectorType {
    fun injectJS(adHtml: String): String
}
