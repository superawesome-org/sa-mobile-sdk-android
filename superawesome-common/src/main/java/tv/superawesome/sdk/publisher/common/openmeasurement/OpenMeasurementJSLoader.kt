package tv.superawesome.sdk.publisher.common.openmeasurement

import tv.superawesome.sdk.publisher.common.components.Logger
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

/**
 * OpenMeasurementJSLoader - Utility for loading the OMID JavaScript resource
 */
internal class OpenMeasurementJSLoader(
    private val logger: Logger,
    private val jsInputStream: InputStream,
) : OpenMeasurementJSLoaderType {
    /**
     * loadJSLibrary - gets the Omid JS resource as a string
     * @return String? - the Omid JS resource as a string
     */
    override fun loadJSLibrary(): String? {
        try {
            // TODO: AASDK-499 Load Omid JS remotely and load from the app documents folder here.
            jsInputStream.use { inputStream ->
                val bytes = ByteArray(inputStream.available())
                val bytesRead = inputStream.read(bytes)
                return String(bytes, 0, bytesRead, Charset.forName("UTF-8"))
            }
        } catch (error: IOException) {
            logger.error("Unable to load OMSDK JS from local storage", error)
            return null
        } catch (error: StringIndexOutOfBoundsException) {
            logger.error("Unable to load OMSDK JS from local storage", error)
            return null
        }
    }
}
