package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context

interface OpenMeasurementJSInjectorType {
    fun injectJS(context: Context, adHtml: String): String
}

internal class DefaultOpenMeasurementJSInjector: OpenMeasurementJSInjectorType {
    override fun injectJS(context: Context, adHtml: String): String = ""
}
