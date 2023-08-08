package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import tv.superawesome.sdk.publisher.common.R
import tv.superawesome.sdk.publisher.common.components.Logger
import java.io.IOException
import java.nio.charset.Charset

/**
 * OpenMeasurementJSLoader - Utility for loading the OMID JavaScript resource
 */
internal class OpenMeasurementJSLoader(
    private val logger: Logger,
) : OpenMeasurementJSLoaderType {
    /**
     * loadJSLibrary - gets the Omid JS resource as a string
     * @param context - used to access the JS resource
     * @return String? - the Omid JS resource as a string
     */
    override fun loadJSLibrary(context: Context): String? {
        val res = context.resources
        try {
            // TODO: AASDK-499 Load Omid JS remotely and load from the app documents folder here.
            res.openRawResource(R.raw.omsdk_v1).use { inputStream ->
                val bytes = ByteArray(inputStream.available())
                val bytesRead = inputStream.read(bytes)
                return String(bytes, 0, bytesRead, Charset.forName("UTF-8"))
            }
        } catch (error: IOException) {
            logger.error("Unable to load OMSDK JS from local storage", error)
            return null
        }
    }
}
