package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context

/**
 * Interface for JS Loader.
 */
internal interface OpenMeasurementJSLoaderType {
    fun loadJSLibrary(context: Context): String?
}
