package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context

/**
 * Utility for injecting the OMID JavaScript string into a HTML string.
 */
internal interface OpenMeasurementJSInjectorType {
    /**
     * Injects the JS string into the provided HTML string.
     * @param context Used to access the JS resource.
     * @param adHtml The HTML string for the ad.
     * @return The HTML string for the ad with the OMID JS injected into it.
     */
    fun injectJS(context: Context, adHtml: String): String
}
