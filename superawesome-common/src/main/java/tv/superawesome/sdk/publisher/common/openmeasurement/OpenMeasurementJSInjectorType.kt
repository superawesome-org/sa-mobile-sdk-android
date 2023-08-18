package tv.superawesome.sdk.publisher.common.openmeasurement

/**
 * Utility for injecting the OMID JavaScript string into a HTML string.
 */
internal interface OpenMeasurementJSInjectorType {
    /**
     * Injects the JS string into the provided HTML string.
     * @param adHtml The HTML string for the ad.
     * @return The HTML string for the ad with the OMID JS injected into it.
     */
    fun injectJS(adHtml: String): String
}
