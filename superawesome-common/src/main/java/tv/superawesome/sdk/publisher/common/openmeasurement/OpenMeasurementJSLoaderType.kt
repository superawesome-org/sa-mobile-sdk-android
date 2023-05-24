package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context

internal interface OpenMeasurementJSLoaderType {
    fun loadJSLibrary(context: Context): String?
}

internal class DefaultOpenMeasurementJSLoader: OpenMeasurementJSLoaderType {
    override fun loadJSLibrary(context: Context): String = ""
}
