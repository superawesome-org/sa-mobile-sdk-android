package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context

/**
 * Interface for OMID JavaScript injector
 */
interface OpenMeasurementJSInjectorType {
    fun injectJS(context: Context, adHtml: String): String
}
